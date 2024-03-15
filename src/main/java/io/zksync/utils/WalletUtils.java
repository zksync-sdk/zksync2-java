package io.zksync.utils;

import io.zksync.abi.ZkTypeEncoder;
import io.zksync.methods.response.ZkTransactionReceipt;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.L2ToL1MessageProof;
import io.zksync.transaction.type.L1BridgeContracts;
import io.zksync.transaction.type.TransactionOptions;
import io.zksync.wrappers.ERC20;
import io.zksync.wrappers.IL1Bridge;
import io.zksync.wrappers.IL2Bridge;
import io.zksync.wrappers.IZkSync;
import org.jetbrains.annotations.Nullable;
import org.web3j.abi.EventValues;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.zksync.utils.ZkSyncAddresses.L2_ETH_TOKEN_ADDRESS;
import static io.zksync.wrappers.IL1Messenger.L1MESSAGESENT_EVENT;

public class WalletUtils {
    private static final BigInteger L1_FEE_ESTIMATION_COEF_NUMERATOR = BigInteger.valueOf(12);
    private static final BigInteger L1_FEE_ESTIMATION_COEF_DENOMINATOR = BigInteger.valueOf(10);


    public static TransactionOptions insertGasPriceInTransactionOptions(TransactionOptions options, Web3j provider){
        if (options == null){
            options = new TransactionOptions();
        }
        if (options.getGasPrice() == null && options.getMaxFeePerGas() == null){
            BigInteger baseFeePerGas = isL1ChainLondonReady(provider);
            if (baseFeePerGas != null){
                options.setMaxPriorityFeePerGas(provider.ethMaxPriorityFeePerGas().sendAsync().join().getMaxPriorityFeePerGas());
                options.setMaxFeePerGas(options.getMaxPriorityFeePerGas().add((baseFeePerGas.multiply(BigInteger.valueOf(3/2)))));

                return options;
            }
            options.setGasPrice(provider.ethGasPrice().sendAsync().join().getGasPrice());
        }
        return options;
    }

    public static BigInteger isL1ChainLondonReady(Web3j provider){
        EthBlock.Block block = provider.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).sendAsync().join().getBlock();
        if (block.getBaseFeePerGas() != null){
            return block.getBaseFeePerGas();
        }
        return null;
    }

    public static BigInteger scaleGasLimit(BigInteger gas){
        return gas.multiply(L1_FEE_ESTIMATION_COEF_NUMERATOR).divide(L1_FEE_ESTIMATION_COEF_DENOMINATOR);
    }

    public static String getERC20BridgeCalldata(String l1TokenAddress, String l1Sender, String receiver, BigInteger amount, byte[] bridgeData) {
        Function function = new Function(
                IL2Bridge.FUNC_FINALIZEDEPOSIT,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(l1Sender),
                        new org.web3j.abi.datatypes.Address(receiver),
                        new org.web3j.abi.datatypes.Address(l1TokenAddress),
                        new org.web3j.abi.datatypes.generated.Uint256(amount),
                        new org.web3j.abi.datatypes.DynamicBytes(bridgeData)),
                Collections.<TypeReference<?>>emptyList());

        return FunctionEncoder.encode(function);
    }

    public static BigInteger estimateDefaultBridgeDepositL2Gas(String tokenAddress, BigInteger amount, String to, ZkSync providerL2, L1BridgeContracts bridgeContracts, @Nullable Web3j providerl1, @Nullable Credentials credentials, @Nullable ContractGasProvider gasProvider, @Nullable String from, @Nullable BigInteger gasPerPubDataByte){
        if (tokenAddress == ZkSyncAddresses.ETH_ADDRESS){
            return providerL2.estimateL1ToL2Execute(to, Numeric.hexStringToByteArray("0x"), from, null, amount, null, null, gasPerPubDataByte, null).sendAsync().join().getAmountUsed();
        }
        BigInteger value = BigInteger.ZERO;
        String l1BridgeAddress;
        String l2BridgeAddress;
        String bridgeData;
        String l2WethToken = ZkSyncAddresses.ETH_ADDRESS;
        try {
            l2WethToken = bridgeContracts.wethL1Bridge.l2TokenAddress(tokenAddress).sendAsync().join();
        } catch (Exception e) {}
        if (l2WethToken != ZkSyncAddresses.ETH_ADDRESS){
            value = amount;
            l1BridgeAddress = bridgeContracts.wethL1Bridge.getContractAddress();
            l2BridgeAddress = providerL2.zksGetBridgeContracts().sendAsync().join().getResult().getL2wETHBridge();
            bridgeData = "0x";
        }else{
            l1BridgeAddress = bridgeContracts.erc20L1Bridge.getContractAddress();
            l2BridgeAddress = providerL2.zksGetBridgeContracts().sendAsync().join().getResult().getL2Erc20DefaultBridge();
            bridgeData = getERC20DefaultBridgeData(tokenAddress, providerl1, credentials, gasProvider);
        }

        return estimateCustomBridgeDepositL2Gas(
                l1BridgeAddress,
                l2BridgeAddress,
                tokenAddress,
                amount,
                to,
                bridgeData,
                from,
                gasPerPubDataByte,
                value,
                providerL2);
    }

    public static BigInteger estimateCustomBridgeDepositL2Gas(String l1BridgeAddress, String l2BridgeAddress, String tokenAddress, BigInteger amount, String to, String bridgeData, String from, BigInteger gasPerBudDataByte, BigInteger value, ZkSync provider){
        String calldata = getERC20BridgeCalldata(tokenAddress, from, to,amount, Numeric.hexStringToByteArray(bridgeData));
        return provider.estimateL1ToL2Execute(
                l2BridgeAddress, Numeric.hexStringToByteArray(calldata), applyL1ToL2Alias(l1BridgeAddress), null, value, null, null, gasPerBudDataByte, null).sendAsync().join().getAmountUsed();
    }

    public static String applyL1ToL2Alias(String address){
        return Numeric.toHexStringWithPrefixZeroPadded(Numeric.toBigInt(address).add(Numeric.toBigInt("0x1111000000000000000000000000000000001111")).mod(BigInteger.valueOf(2).pow(160)), 40);
    }

    public static String undoL1ToL2Alias(String address){
        BigInteger result = Numeric.toBigInt(address).subtract(Numeric.toBigInt("0x1111000000000000000000000000000000001111"));
        if (result.compareTo(BigInteger.ZERO) < 0){
            result.add(BigInteger.valueOf(2).pow(160));
        }
        return Numeric.toHexStringWithPrefixZeroPadded(result, 40);
    }

    public static String getERC20DefaultBridgeData(String l1TokenAddress, Web3j provider, Credentials credentials, ContractGasProvider gasProvider){
        ERC20 token = ERC20.load(l1TokenAddress, provider, credentials, gasProvider);

        String name = token.name().sendAsync().join();
        String symbol = token.symbol().sendAsync().join();
        BigInteger decimals = token.decimals().sendAsync().join();

        String nameEncoded = encode(name);
        String symbolEncoded = encode(symbol);
        String decimalsEncoded = ZkTypeEncoder.encode(new Uint256(decimals));

        byte[] nameEncodedBytes = Numeric.hexStringToByteArray(nameEncoded);
        byte[] symbolEncodedBytes = Numeric.hexStringToByteArray(symbolEncoded);
        byte[] decimalsEncodedBytes = Numeric.hexStringToByteArray(decimalsEncoded);

        Function f = new Function(null,
                Arrays.asList(new DynamicBytes(nameEncodedBytes), new DynamicBytes(symbolEncodedBytes), new DynamicBytes(decimalsEncodedBytes)),
                Collections.emptyList());

        return Numeric.prependHexPrefix(FunctionEncoder.encode(f));
    }

    static String encode(String name){
        byte[] offset = Numeric.toBytesPadded(BigInteger.valueOf(32), 32);

        // Length of the string, padded to 32 bytes
        byte[] length = Numeric.toBytesPadded(BigInteger.valueOf(name.length()), 32);

        // Encode the string
        Utf8String utf8String = new Utf8String(name);
        byte[] stringData = utf8String.getValue().getBytes();

        // Padding for string data to make its length a multiple of 32 bytes
        int paddingLength = 32 - (stringData.length % 32);
        byte[] padding = new byte[paddingLength];

        // Combine offset, length, string data, and padding
        ByteBuffer combined = ByteBuffer.allocate(offset.length + length.length + stringData.length + paddingLength);
        combined.put(offset);
        combined.put(length);
        combined.put(stringData);
        combined.put(padding); // Add padding at the end

        // Convert to hexadecimal string
        return Numeric.toHexString(combined.array());
    }

    public static boolean checkBaseCost(BigInteger baseCost, BigInteger value){
        if (baseCost.compareTo(value) > 0) {
            throw new RuntimeException(
                    "The base cost of performing the priority operation is higher than the provided value parameter " +
                            "for the transaction: baseCost: " + baseCost + ", provided value: " + value
            );
        }
        return true;
    }

}
