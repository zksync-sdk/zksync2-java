package io.zksync.integration;

import io.zksync.abi.TransactionEncoder;
import io.zksync.helper.ConstructorContract;
import io.zksync.helper.CounterContract;
import io.zksync.helper.Import;
import io.zksync.methods.request.Eip712Meta;
import io.zksync.methods.response.*;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.protocol.provider.EthereumProvider;
import io.zksync.transaction.fee.Fee;
import io.zksync.transaction.manager.ZkSyncTransactionManager;
import io.zksync.transaction.type.Transaction712;
import io.zksync.utils.ContractDeployer;
import io.zksync.utils.ZkSyncAddresses;
import io.zksync.wrappers.ERC20;
import io.zksync.wrappers.IL2Bridge;
import io.zksync.wrappers.NonceHolder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariables;
import org.junit.jupiter.api.function.Executable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.*;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

import static io.zksync.utils.ZkSyncAddresses.L2_ETH_TOKEN_ADDRESS;
import static org.junit.jupiter.api.Assertions.*;

@EnabledIfEnvironmentVariables({
        @EnabledIfEnvironmentVariable(named = "ZKSYNC2_JAVA_CI_L1_NODE_URL", matches = "^https://*"),
        @EnabledIfEnvironmentVariable(named = "ZKSYNC2_JAVA_CI_L2_NODE_URL", matches = "^https://*"),
})
public class IntegrationZkSyncWeb3RpcTest extends BaseIntegrationEnv {
    @Test
    public void printChainId() {
        System.out.println(chainId);
        System.out.println(credentials.getAddress());
    }

    @Test
    @Disabled
    @Deprecated
    public void sendTestMoney() {
        String account = l1Web3.ethAccounts().sendAsync().join().getAccounts().get(0);

        EthSendTransaction sent = l1Web3.ethSendTransaction(
                        Transaction.createEtherTransaction(account, null, Convert.toWei("1", Unit.GWEI).toBigInteger(), BigInteger.valueOf(21_000L),
                                credentials.getAddress(), Convert.toWei("1000000", Unit.ETHER).toBigInteger()))
                .sendAsync().join();

        assertResponse(sent);
    }

    @Test
    @Disabled
    public void testGetBalanceOfNativeL1() throws IOException {
        EthGetBalance getBalance = l1Web3
                .ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST)
                .send();

        assertResponse(getBalance);
        System.out.printf("%s: %d\n", credentials.getAddress(), Numeric.toBigInt(getBalance.getResult()));
        assertTrue(getBalance.getBalance().compareTo(Convert.toWei("0.2", Unit.ETHER).toBigInteger()) >= 0);
    }

    @Test
    @Disabled
    public void testDeposit() throws IOException {
        BigInteger chainId = l1Web3.ethChainId().send().getChainId();
        TransactionManager manager = new RawTransactionManager(l1Web3, credentials, chainId.longValue());
        BigInteger gasPrice = l1Web3.ethGasPrice().send().getGasPrice();
        ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, BigInteger.valueOf(170_000L));
        TransactionReceipt receipt = EthereumProvider
                .load(zksync, l1Web3, manager, gasProvider).join()
                .deposit(ETH, Convert.toWei("0.001", Unit.ETHER).toBigInteger(), BigInteger.ZERO, credentials.getAddress()).join();

        System.out.println(receipt);
    }

    @Test
    @Disabled
    public void testDepositToken() throws IOException {
        Token token = zksync.zksGetConfirmedTokens(0, (short) 100).send()
                .getResult().stream()
                .filter(t -> t.getSymbol().equalsIgnoreCase("USDC"))
                .findFirst().orElseThrow(IllegalArgumentException::new);
        BigInteger chainId = l1Web3.ethChainId().send().getChainId();
        BigInteger gasPrice = l1Web3.ethGasPrice().send().getGasPrice();
        TransactionManager manager = new RawTransactionManager(l1Web3, credentials, chainId.longValue());
        ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, BigInteger.valueOf(200_000L));
        EthereumProvider provider = EthereumProvider.load(zksync, l1Web3, manager, gasProvider).join();
        TransactionReceipt approveReceipt = provider.approveDeposits(token, Optional.of(token.toBigInteger(10000000000L))).join();
        System.out.println(approveReceipt);

        TransactionReceipt receipt = provider.deposit(token, token.toBigInteger(10000000000L), BigInteger.ZERO, credentials.getAddress()).join();

        System.out.println(receipt);
    }

    @Test
    public void testGetBalanceOfNative() throws IOException {
        EthGetBalance getBalance = zksync
                .ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST)
                .send();

        System.out.printf("%s: %d\n", credentials.getAddress(), Numeric.toBigInt(getBalance.getResult()));
    }

    @Test
    public void testGetNonce() throws IOException {
        EthGetTransactionCount getTransactionCount = zksync
                .ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST)
                .send();

        System.out.printf("%s: %d\n", credentials.getAddress(), Numeric.toBigInt(getTransactionCount.getResult()));
    }

    @Test
    public void testGetDeploymentNonce() throws Exception {
        NonceHolder nonceHolder = NonceHolder.load(ZkSyncAddresses.NONCE_HOLDER_ADDRESS, zksync, new ReadonlyTransactionManager(zksync, credentials.getAddress()), new DefaultGasProvider());

        BigInteger nonce = nonceHolder.getDeploymentNonce(credentials.getAddress()).send();

        System.out.printf("%s: %d\n", credentials.getAddress(), nonce);
    }

    @Test
    public void testGetTransactionReceipt() throws IOException {
        TransactionReceipt receipt = zksync
                .ethGetTransactionReceipt("0xea87f073bbb8826edf51abbbc77b5812848c92bfb8a825f82a74586ad3553309").send()
                .getResult();

        System.out.println(receipt);
    }

    @Test
    public void testGetTransaction() throws IOException {
        org.web3j.protocol.core.methods.response.Transaction receipt = zksync
                .ethGetTransactionByHash("0x60c05fffdfca5ffb5884f8dd0a80268f16ef768c71f6e173ed1fb58a50790e29").send()
                .getResult();

        System.out.println(receipt);
    }

    @Test
    public void testTransferNativeToSelf() throws IOException, TransactionException {
        final BigInteger value = Convert.toWei(BigDecimal.valueOf(0.01), Unit.ETHER).toBigInteger();

        BigInteger nonce = zksync
                .ethGetTransactionCount(credentials.getAddress(), ZkBlockParameterName.COMMITTED).send()
                .getTransactionCount();

        io.zksync.methods.request.Transaction estimate = io.zksync.methods.request.Transaction.createEtherTransaction(
                credentials.getAddress(),
                BigInteger.ZERO,
                BigInteger.ZERO,
                credentials.getAddress(),
                value
        );

        ZksEstimateFee estimateFee = zksync.zksEstimateFee(estimate).send();

        EthGasPrice gasPrice = zksync.ethGasPrice().send();

        assertResponse(gasPrice);
        assertResponse(estimateFee);

        Fee fee = estimateFee.getResult();

        Eip712Meta meta = estimate.getEip712Meta();
        meta.setGasPerPubdata(fee.getGasPerPubdataLimitNumber());

        Transaction712 transaction = new Transaction712(
                chainId.longValue(),
                nonce,
                fee.getGasLimitNumber(),
                estimate.getTo(),
                value,
                estimate.getData(),
                fee.getMaxPriorityFeePerErgNumber(),
                fee.getGasPriceLimitNumber(),
                credentials.getAddress(),
                meta
        );

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, transaction)).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction sent = zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();

        assertResponse(sent);

        TransactionReceipt receipt = processor.waitForTransactionReceipt(sent.getResult());

        assertTrue(receipt::isStatusOK);
    }

    @Test
    public void testTransferTokenToSelf() throws IOException, TransactionException {
        BigInteger nonce = zksync
                .ethGetTransactionCount(credentials.getAddress(), ZkBlockParameterName.COMMITTED).send()
                .getTransactionCount();

        Token token = zksync.zksGetConfirmedTokens(0, (short) 100).send()
                .getResult().stream()
                .filter(t -> t.getSymbol().equalsIgnoreCase("USDC"))
                .findFirst().orElseThrow(IllegalArgumentException::new);
        String tokenAddress = token.getL2Address();
        Function transfer = ERC20.encodeTransfer(credentials.getAddress(), token.toBigInteger(10));
        String calldata = FunctionEncoder.encode(transfer);

        io.zksync.methods.request.Transaction estimate = io.zksync.methods.request.Transaction.createFunctionCallTransaction(
                credentials.getAddress(),
                tokenAddress,
                BigInteger.ZERO,
                BigInteger.ZERO,
                calldata
        );

        ZksEstimateFee estimateFee = zksync.zksEstimateFee(estimate).send();

        EthGasPrice gasPrice = zksync.ethGasPrice().send();

        assertResponse(gasPrice);
        assertResponse(estimateFee);

        Fee fee = estimateFee.getResult();

        Eip712Meta meta = estimate.getEip712Meta();
        meta.setGasPerPubdata(fee.getGasPerPubdataLimitNumber());

        Transaction712 transaction = new Transaction712(
                chainId.longValue(),
                nonce,
                fee.getGasLimitNumber(),
                estimate.getTo(),
                estimate.getValueNumber(),
                estimate.getData(),
                fee.getMaxPriorityFeePerErgNumber(),
                fee.getGasPriceLimitNumber(),
                credentials.getAddress(),
                meta
        );

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, transaction)).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction sent = zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();

        assertResponse(sent);

        TransactionReceipt receipt = processor.waitForTransactionReceipt(sent.getResult());

        assertTrue(receipt::isStatusOK);
    }

    @Test
    public void testTransferTokenToSelfWeb3jContract() throws Exception {
        Token token = zksync.zksGetConfirmedTokens(0, (short) 100).send()
                .getResult().stream()
                .filter(t -> t.getSymbol().equalsIgnoreCase("USDC"))
                .findFirst().orElseThrow(IllegalArgumentException::new);

        ERC20 erc20 = ERC20.load(token.getL2Address(), zksync,
                new ZkSyncTransactionManager(zksync, signer, feeProvider),
                feeProvider);

        TransactionReceipt receipt = erc20.transfer(credentials.getAddress(), token.toBigInteger(10)).send();

        assertTrue(receipt::isStatusOK);
    }

    @Test
    public void testWithdraw() throws IOException, TransactionException {
        final Token token = ETH;
        final double amount = 0.01;

        BigInteger nonce = zksync
                .ethGetTransactionCount(credentials.getAddress(), ZkBlockParameterName.COMMITTED).send()
                .getTransactionCount();

        String l2EthBridge = L2_ETH_TOKEN_ADDRESS;

        final Function withdraw = new Function(
                IL2Bridge.FUNC_WITHDRAW,
                Arrays.asList(new Address(credentials.getAddress()),
                        new Address(token.getL2Address()),
                        new Uint256(token.toBigInteger(amount))),
                Collections.emptyList());

        String calldata = FunctionEncoder.encode(withdraw);

        io.zksync.methods.request.Transaction estimate = io.zksync.methods.request.Transaction.createFunctionCallTransaction(
                credentials.getAddress(),
                l2EthBridge,
                BigInteger.ZERO,
                BigInteger.ZERO,
                calldata
        );

        ZksEstimateFee estimateFee = zksync.zksEstimateFee(estimate).send();

        EthGasPrice gasPrice = zksync.ethGasPrice().send();

        assertResponse(estimateFee);
        assertResponse(gasPrice);

        Fee fee = estimateFee.getResult();

        Eip712Meta meta = estimate.getEip712Meta();
        meta.setGasPerPubdata(fee.getGasPerPubdataLimitNumber());

        Transaction712 transaction = new Transaction712(
                chainId.longValue(),
                nonce,
                fee.getGasLimitNumber(),
                estimate.getTo(),
                estimate.getValueNumber(),
                estimate.getData(),
                fee.getMaxPriorityFeePerErgNumber(),
                fee.getGasPriceLimitNumber(),
                credentials.getAddress(),
                meta
        );

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, transaction)).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction sent = zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();

        assertResponse(sent);

        TransactionReceipt receipt = processor.waitForTransactionReceipt(sent.getResult());

        assertTrue(receipt::isStatusOK);
    }

    @Test
    public void testWithdrawToken() throws IOException, TransactionException {
        Token token = zksync.zksGetConfirmedTokens(0, (short) 100).send()
                .getResult().stream()
                .filter(t -> t.getSymbol().equalsIgnoreCase("USDC"))
                .findFirst().orElseThrow(IllegalArgumentException::new);
        final double amount = 10;

        BigInteger nonce = zksync
                .ethGetTransactionCount(credentials.getAddress(), ZkBlockParameterName.COMMITTED).send()
                .getTransactionCount();

        String l2Erc20Bridge = zksync.zksGetBridgeContracts().send().getResult().getL2Erc20DefaultBridge();

        final Function withdraw = new Function(
                IL2Bridge.FUNC_WITHDRAW,
                Arrays.asList(new Address(credentials.getAddress()),
                        new Address(token.getL2Address()),
                        new Uint256(token.toBigInteger(amount))),
                Collections.emptyList());

        String calldata = FunctionEncoder.encode(withdraw);

        io.zksync.methods.request.Transaction estimate = io.zksync.methods.request.Transaction.createFunctionCallTransaction(
                credentials.getAddress(),
                l2Erc20Bridge,
                BigInteger.ZERO,
                BigInteger.ZERO,
                calldata
        );

        ZksEstimateFee estimateFee = zksync.zksEstimateFee(estimate).send();

        EthGasPrice gasPrice = zksync.ethGasPrice().send();

        assertResponse(estimateFee);
        assertResponse(gasPrice);

        Fee fee = estimateFee.getResult();

        Eip712Meta meta = estimate.getEip712Meta();
        meta.setGasPerPubdata(fee.getGasPerPubdataLimitNumber());

        Transaction712 transaction = new Transaction712(
                chainId.longValue(),
                nonce,
                fee.getGasLimitNumber(),
                estimate.getTo(),
                estimate.getValueNumber(),
                estimate.getData(),
                fee.getMaxPriorityFeePerErgNumber(),
                fee.getGasPriceLimitNumber(),
                credentials.getAddress(),
                meta
        );

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, transaction)).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction sent = zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();

        assertResponse(sent);

        TransactionReceipt receipt = processor.waitForTransactionReceipt(sent.getResult());

        assertTrue(receipt::isStatusOK);
    }

    @Test
    @Disabled
    public void testEstimateGas_Withdraw() throws IOException {
        String l2EthBridge = L2_ETH_TOKEN_ADDRESS;
        final Function withdraw = new Function(
                IL2Bridge.FUNC_WITHDRAW,
                Arrays.asList(new Address(credentials.getAddress()),
                        new Address(ETH.getL2Address()),
                        new Uint256(ETH.toBigInteger(1))),
                Collections.emptyList());

        String calldata = FunctionEncoder.encode(withdraw);

        EthEstimateGas estimateGas = zksync.ethEstimateGas(io.zksync.methods.request.Transaction.createFunctionCallTransaction(
                credentials.getAddress(),
                l2EthBridge,
                BigInteger.ZERO,
                BigInteger.ZERO,
                calldata
        )).send();

        assertResponse(estimateGas);
    }

    @Test
    @Disabled
    public void testEstimateGas_TransferNative() throws IOException {
        io.zksync.methods.request.Transaction estimate = io.zksync.methods.request.Transaction.createFunctionCallTransaction(
                credentials.getAddress(),
                credentials.getAddress(),
                BigInteger.ZERO,
                BigInteger.ZERO,
                "0x"
        );

        EthEstimateGas estimateGas = zksync.ethEstimateGas(estimate).send();

        assertResponse(estimateGas);
    }

    @Test
    @Disabled
    public void testEstimateFee_TransferNative() throws IOException {
        io.zksync.methods.request.Transaction estimate = io.zksync.methods.request.Transaction.createFunctionCallTransaction(
                credentials.getAddress(),
                credentials.getAddress(),
                BigInteger.ZERO,
                BigInteger.ZERO,
                "0x"
        );


        ZksEstimateFee estimateFee = zksync.zksEstimateFee(estimate).send();

        assertResponse(estimateFee);
        System.out.println(estimateFee.getRawResponse());
    }

    @Test
    @Disabled
    public void testEstimateGas_Execute() throws IOException {
        Function transfer = ERC20.encodeTransfer("0xe1fab3efd74a77c23b426c302d96372140ff7d0c", BigInteger.valueOf(1L));
        String calldata = FunctionEncoder.encode(transfer);

        EthEstimateGas estimateGas = zksync.ethEstimateGas(io.zksync.methods.request.Transaction.createFunctionCallTransaction(
                credentials.getAddress(),
                "0x79f73588fa338e685e9bbd7181b410f60895d2a3",
                BigInteger.ZERO,
                BigInteger.ZERO,
                calldata
        )).send();

        assertResponse(estimateGas);
    }

    @Test
    @Disabled
    public void testEstimateGas_DeployContract() throws IOException {
        EthEstimateGas estimateGas = zksync.ethEstimateGas(io.zksync.methods.request.Transaction.create2ContractTransaction(
                credentials.getAddress(),
                BigInteger.ZERO,
                BigInteger.ZERO,
                CounterContract.BINARY
        )).send();

        assertResponse(estimateGas);
    }

    @Test
    @Disabled
    public void testEstimateFee_DeployContract() throws IOException {
        ZksEstimateFee estimateFee = zksync.zksEstimateFee(io.zksync.methods.request.Transaction.create2ContractTransaction(
                credentials.getAddress(),
                BigInteger.ZERO,
                BigInteger.ZERO,
                CounterContract.BINARY
        )).send();

        assertResponse(estimateFee);
    }

    @Test
    public void testDeployWeb3jContract() {
        Executable execute = () -> {
            TransactionManager transactionManager = new ZkSyncTransactionManager(zksync, signer, feeProvider);
            CounterContract contract = CounterContract
                    .deploy(zksync, transactionManager, feeProvider).send();
        };

        assertThrows(RuntimeException.class, execute);
    }

    @Test
    public void testWeb3jContract() throws Exception {
        io.zksync.methods.request.Transaction deploy = io.zksync.methods.request.Transaction.createContractTransaction(
                credentials.getAddress(),
                BigInteger.ZERO,
                BigInteger.ZERO,
                CounterContract.BINARY
        );
        TransactionReceipt deployed = submitTransaction(deploy);
        CounterContract zkCounterContract = CounterContract.load(deployed.getContractAddress(), zksync, new ZkSyncTransactionManager(zksync, signer, feeProvider), feeProvider);

        {
            BigInteger result = zkCounterContract.get().send();

            System.out.println(result);

            assertEquals(BigInteger.ZERO, result);
        }

        {
            TransactionReceipt receipt = zkCounterContract.increment(BigInteger.TEN).send();

            assertTrue(receipt::isStatusOK);

            BigInteger result = zkCounterContract.get().send();

            assertEquals(BigInteger.TEN, result);
        }
    }

    @Test
    public void testDeployContract_Create() throws Exception {
        BigInteger nonce = zksync
                .ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.PENDING).send()
                .getTransactionCount();

        NonceHolder nonceHolder = NonceHolder.load(ZkSyncAddresses.NONCE_HOLDER_ADDRESS, zksync, new ReadonlyTransactionManager(zksync, credentials.getAddress()), new DefaultGasProvider());

        BigInteger deploymentNonce = nonceHolder.getDeploymentNonce(credentials.getAddress()).send();

        String precomputedAddress = ContractDeployer.computeL2CreateAddress(new Address(credentials.getAddress()), deploymentNonce).getValue();

        io.zksync.methods.request.Transaction estimate = io.zksync.methods.request.Transaction.createContractTransaction(
                credentials.getAddress(),
                BigInteger.ZERO,
                BigInteger.ZERO,
                CounterContract.BINARY
        );

        ZksEstimateFee estimateFee = zksync.zksEstimateFee(estimate).send();

        EthGasPrice gasPrice = zksync.ethGasPrice().send();

        assertResponse(estimateFee);
        assertResponse(gasPrice);

        Fee fee = estimateFee.getResult();

        Eip712Meta meta = estimate.getEip712Meta();
        meta.setGasPerPubdata(fee.getGasPerPubdataLimitNumber());

        Transaction712 transaction = new Transaction712(
                chainId.longValue(),
                nonce,
                fee.getGasLimitNumber(),
                estimate.getTo(),
                estimate.getValueNumber(),
                estimate.getData(),
                fee.getMaxPriorityFeePerErgNumber(),
                fee.getGasPriceLimitNumber(),
                credentials.getAddress(),
                meta
        );

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, transaction)).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction sent = zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();

        assertResponse(sent);

        TransactionReceipt receipt = processor.waitForTransactionReceipt(sent.getResult());

        assertTrue(receipt::isStatusOK);

        String contractAddress = receipt.getContractAddress();
        System.out.println("Deployed `CounterContract as: `" + contractAddress);
        assertEquals(contractAddress.toLowerCase(), precomputedAddress.toLowerCase());

        Transaction call = Transaction.createEthCallTransaction(
                credentials.getAddress(),
                contractAddress,
                FunctionEncoder.encode(CounterContract.encodeGet())
        );

        zksync.ethCall(call, ZkBlockParameterName.COMMITTED).send();
    }

    @Test
    public void testDeployContractWithConstructor_Create() throws Exception {
        BigInteger nonce = zksync
                .ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.PENDING).send()
                .getTransactionCount();

        NonceHolder nonceHolder = NonceHolder.load(ZkSyncAddresses.NONCE_HOLDER_ADDRESS, zksync, new ReadonlyTransactionManager(zksync, credentials.getAddress()), new DefaultGasProvider());

        BigInteger deploymentNonce = nonceHolder.getDeploymentNonce(credentials.getAddress()).send();

        String precomputedAddress = ContractDeployer.computeL2CreateAddress(new Address(credentials.getAddress()), deploymentNonce).getValue();

        String constructor = ConstructorContract.encodeConstructor(BigInteger.valueOf(42), BigInteger.valueOf(43), false);

        io.zksync.methods.request.Transaction estimate = io.zksync.methods.request.Transaction.createContractTransaction(
                credentials.getAddress(),
                BigInteger.ZERO,
                BigInteger.ZERO,
                ConstructorContract.BINARY,
                constructor
        );

        ZksEstimateFee estimateFee = zksync.zksEstimateFee(estimate).send();

        EthGasPrice gasPrice = zksync.ethGasPrice().send();

        assertResponse(estimateFee);
        assertResponse(gasPrice);

        Fee fee = estimateFee.getResult();

        Eip712Meta meta = estimate.getEip712Meta();
        meta.setGasPerPubdata(fee.getGasPerPubdataLimitNumber());

        Transaction712 transaction = new Transaction712(
                chainId.longValue(),
                nonce,
                fee.getGasLimitNumber(),
                estimate.getTo(),
                estimate.getValueNumber(),
                estimate.getData(),
                fee.getMaxPriorityFeePerErgNumber(),
                fee.getGasPriceLimitNumber(),
                credentials.getAddress(),
                meta
        );

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, transaction)).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction sent = zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();

        assertResponse(sent);

        TransactionReceipt receipt = processor.waitForTransactionReceipt(sent.getResult());

        assertTrue(receipt::isStatusOK);

        String contractAddress = receipt.getContractAddress();
        System.out.println("Deployed `ConstructorContract as: `" + contractAddress);
        assertContractDeployResponse(receipt, precomputedAddress);

        Transaction call = Transaction.createEthCallTransaction(
                credentials.getAddress(),
                contractAddress,
                FunctionEncoder.encode(ConstructorContract.encodeGet())
        );

        EthCall ethCall = zksync.ethCall(call, ZkBlockParameterName.COMMITTED).send();
        assertResponse(ethCall);
    }

    @Test
    public void testDeployContract_Create2() throws IOException, TransactionException {
        BigInteger nonce = zksync
                .ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.PENDING).send()
                .getTransactionCount();

        byte[] salt = SecureRandom.getSeed(32);

        String precomputedAddress = ContractDeployer.computeL2Create2Address(new Address(credentials.getAddress()), Numeric.hexStringToByteArray(CounterContract.BINARY), new byte[]{}, salt).getValue();

        io.zksync.methods.request.Transaction estimate = io.zksync.methods.request.Transaction.create2ContractTransaction(
                credentials.getAddress(),
                BigInteger.ZERO,
                BigInteger.ZERO,
                CounterContract.BINARY,
                "0x",
                salt
        );

        ZksEstimateFee estimateFee = zksync.zksEstimateFee(estimate).send();

        EthGasPrice gasPrice = zksync.ethGasPrice().send();

        assertResponse(estimateFee);
        assertResponse(gasPrice);

        Fee fee = estimateFee.getResult();

        Eip712Meta meta = estimate.getEip712Meta();
        meta.setGasPerPubdata(fee.getGasPerPubdataLimitNumber());

        Transaction712 transaction = new Transaction712(
                chainId.longValue(),
                nonce,
                fee.getGasLimitNumber(),
                estimate.getTo(),
                estimate.getValueNumber(),
                estimate.getData(),
                fee.getMaxPriorityFeePerErgNumber(),
                fee.getGasPriceLimitNumber(),
                credentials.getAddress(),
                meta
        );

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, transaction)).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction sent = zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();

        assertResponse(sent);

        TransactionReceipt receipt = processor.waitForTransactionReceipt(sent.getResult());

        assertTrue(receipt::isStatusOK);

        String contractAddress = receipt.getContractAddress();
        System.out.println("Deployed `CounterContract as: `" + contractAddress);
        assertContractDeployResponse(receipt, precomputedAddress);

        Transaction call = Transaction.createEthCallTransaction(
                credentials.getAddress(),
                contractAddress,
                FunctionEncoder.encode(CounterContract.encodeGet())
        );

        EthCall ethCall = zksync.ethCall(call, ZkBlockParameterName.COMMITTED).send();
        assertResponse(ethCall);
    }

    @Test
    public void testDeployContractWithDeps_Create() throws Exception {
        BigInteger nonce = zksync
                .ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.PENDING).send()
                .getTransactionCount();

        NonceHolder nonceHolder = NonceHolder.load(ZkSyncAddresses.NONCE_HOLDER_ADDRESS, zksync, new ReadonlyTransactionManager(zksync, credentials.getAddress()), new DefaultGasProvider());

        BigInteger deploymentNonce = nonceHolder.getDeploymentNonce(credentials.getAddress()).send();

        String precomputedAddress = ContractDeployer.computeL2CreateAddress(new Address(credentials.getAddress()), deploymentNonce).getValue();

        io.zksync.methods.request.Transaction estimate = io.zksync.methods.request.Transaction.createContractTransaction(
                credentials.getAddress(),
                BigInteger.ZERO,
                BigInteger.ZERO,
                Import.BINARY,
                Collections.singletonList(Import.FOO_BINARY),
                "0x"
        );

        ZksEstimateFee estimateFee = zksync.zksEstimateFee(estimate).send();

        EthGasPrice gasPrice = zksync.ethGasPrice().send();

        assertResponse(estimateFee);
        assertResponse(gasPrice);

        Fee fee = estimateFee.getResult();

        Eip712Meta meta = estimate.getEip712Meta();
        meta.setGasPerPubdata(fee.getGasPerPubdataLimitNumber());

        Transaction712 transaction = new Transaction712(
                chainId.longValue(),
                nonce,
                fee.getGasLimitNumber(),
                estimate.getTo(),
                estimate.getValueNumber(),
                estimate.getData(),
                fee.getMaxPriorityFeePerErgNumber(),
                fee.getGasPriceLimitNumber(),
                credentials.getAddress(),
                meta
        );

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, transaction)).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction sent = zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();

        assertResponse(sent);

        TransactionReceipt receipt = processor.waitForTransactionReceipt(sent.getResult());

        assertTrue(receipt::isStatusOK);

        String contractAddress = ContractDeployer.extractContractAddress(receipt).getValue();
        System.out.println("Deployed `Import as: `" + contractAddress);
        assertContractDeployResponse(receipt, precomputedAddress);

        Function getFooName = Import.encodeGetFooName();

        Transaction call = Transaction.createEthCallTransaction(
                null,
                contractAddress,
                FunctionEncoder.encode(getFooName)
        );

        EthCall ethCall = zksync.ethCall(call, ZkBlockParameterName.COMMITTED).send();
        assertResponse(ethCall);

        String fooName = (String) FunctionReturnDecoder.decode(ethCall.getValue(), getFooName.getOutputParameters()).get(0).getValue();
        assertEquals("Foo", fooName);
    }

    @Test
    public void testDeployContractWithDeps_Create2() throws IOException, TransactionException {
        BigInteger nonce = zksync
                .ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.PENDING).send()
                .getTransactionCount();

        byte[] salt = SecureRandom.getSeed(32);

        String precomputedAddress = ContractDeployer.computeL2Create2Address(new Address(credentials.getAddress()), Numeric.hexStringToByteArray(Import.BINARY), new byte[]{}, salt).getValue();

        io.zksync.methods.request.Transaction estimate = io.zksync.methods.request.Transaction.create2ContractTransaction(
                credentials.getAddress(),
                BigInteger.ZERO,
                BigInteger.ZERO,
                Import.BINARY,
                Collections.singletonList(Import.FOO_BINARY),
                "0x",
                salt
        );

        ZksEstimateFee estimateFee = zksync.zksEstimateFee(estimate).send();

        EthGasPrice gasPrice = zksync.ethGasPrice().send();

        assertResponse(estimateFee);
        assertResponse(gasPrice);

        Fee fee = estimateFee.getResult();

        Eip712Meta meta = estimate.getEip712Meta();
        meta.setGasPerPubdata(fee.getGasPerPubdataLimitNumber());

        Transaction712 transaction = new Transaction712(
                chainId.longValue(),
                nonce,
                fee.getGasLimitNumber(),
                estimate.getTo(),
                estimate.getValueNumber(),
                estimate.getData(),
                fee.getMaxPriorityFeePerErgNumber(),
                fee.getGasPriceLimitNumber(),
                credentials.getAddress(),
                meta
        );

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, transaction)).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction sent = zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();

        assertResponse(sent);

        TransactionReceipt receipt = processor.waitForTransactionReceipt(sent.getResult());

        assertTrue(receipt::isStatusOK);

        String contractAddress = ContractDeployer.extractContractAddress(receipt).getValue();
        System.out.println("Deployed `Import as: `" + contractAddress);
        assertContractDeployResponse(receipt, precomputedAddress);

        Function getFooName = Import.encodeGetFooName();

        Transaction call = Transaction.createEthCallTransaction(
                null,
                contractAddress,
                FunctionEncoder.encode(getFooName)
        );

        EthCall ethCall = zksync.ethCall(call, ZkBlockParameterName.COMMITTED).send();
        assertResponse(ethCall);

        String fooName = (String) FunctionReturnDecoder.decode(ethCall.getValue(), getFooName.getOutputParameters()).get(0).getValue();
        assertEquals("Foo", fooName);
    }

    @Test
    public void testExecuteContract() throws IOException, TransactionException {
        io.zksync.methods.request.Transaction deploy = io.zksync.methods.request.Transaction.createContractTransaction(
                credentials.getAddress(),
                BigInteger.ZERO,
                BigInteger.ZERO,
                CounterContract.BINARY
        );
        TransactionReceipt deployed = submitTransaction(deploy);
        String contractAddress = deployed.getContractAddress();
        BigInteger nonce = zksync
                .ethGetTransactionCount(credentials.getAddress(), ZkBlockParameterName.COMMITTED).send()
                .getTransactionCount();

        Transaction call = Transaction.createEthCallTransaction(
                credentials.getAddress(),
                contractAddress,
                FunctionEncoder.encode(CounterContract.encodeGet())
        );

        BigInteger value = Numeric.toBigInt(zksync.ethCall(call, ZkBlockParameterName.COMMITTED).send().getValue());

        Function increment = CounterContract.encodeIncrement(BigInteger.ONE);
        String calldata = FunctionEncoder.encode(increment);

        io.zksync.methods.request.Transaction estimate = io.zksync.methods.request.Transaction.createFunctionCallTransaction(
                credentials.getAddress(),
                contractAddress,
                BigInteger.ZERO,
                BigInteger.ZERO,
                calldata
        );

        ZksEstimateFee estimateFee = zksync.zksEstimateFee(estimate).send();

        EthGasPrice gasPrice = zksync.ethGasPrice().send();

        assertResponse(estimateFee);
        assertResponse(gasPrice);

        Fee fee = estimateFee.getResult();

        Eip712Meta meta = estimate.getEip712Meta();
        meta.setGasPerPubdata(fee.getGasPerPubdataLimitNumber());

        Transaction712 transaction = new Transaction712(
                chainId.longValue(),
                nonce,
                fee.getGasLimitNumber(),
                estimate.getTo(),
                estimate.getValueNumber(),
                estimate.getData(),
                fee.getMaxPriorityFeePerErgNumber(),
                fee.getGasPriceLimitNumber(),
                credentials.getAddress(),
                meta
        );

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, transaction)).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction sent = zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();

        assertResponse(sent);

        TransactionReceipt receipt = processor.waitForTransactionReceipt(sent.getResult());

        assertTrue(receipt::isStatusOK);

        BigInteger result = Numeric.toBigInt(zksync.ethCall(call, ZkBlockParameterName.COMMITTED).send().getValue());

        assertEquals(value.add(BigInteger.ONE), result);

    }

    @Test
    public void testGetAllAccountBalances() throws IOException {
        ZksAccountBalances response = zksync.zksGetAllAccountBalances(credentials.getAddress()).send();

        assertResponse(response);

        Map<String, BigInteger> balances = response.getBalances();

        System.out.println(balances);
    }

    @Test
    public void testGetConfirmedTokens() throws IOException {
        int offset = 0;
        short limit = 10; // Get first 10 confirmed tokens

        ZksTokens response = zksync.zksGetConfirmedTokens(offset, limit).send();

        assertResponse(response);
    }

    @Test
    public void testGetTokenPrice() throws IOException {
        ZksTokenPrice response = zksync.zksGetTokenPrice(ETH.getL2Address()).send();

        assertResponse(response);
    }

    @Test
    public void testGetL1ChainId() throws IOException {
        ZksL1ChainId response = zksync.zksL1ChainId().send();

        assertResponse(response);
    }

    @Test
    public void testGetBridgeAddresses() throws IOException {
        ZksBridgeAddresses response = zksync.zksGetBridgeContracts().send();

        assertResponse(response);
    }

    @Test
    public void testGetTestnetPaymaster() throws IOException {
        ZksTestnetPaymasterAddress response = zksync.zksGetTestnetPaymaster().send();

        assertResponse(response);
    }

    @Test
    public void testGetMainContract() throws IOException {
        ZksMainContract response = zksync.zksMainContract().send();

        assertResponse(response);
    }


    @Test
    public void testGetTransactionDetails() throws IOException {
        ZksGetTransactionDetails response = zksync.zksGetTransactionDetails("0x0898f4b225276625e1d5d2cc4dc5b7a1acb896daece7e46c8202a47da9a13a27").send();

        assertResponse(response);
    }


    @Test
    public void testGetTransactionByHash() throws IOException {
        ZksGetTransactionByHash response = zksync.zksGetTransactionByHash("0xd933be650bf97d92c591a88c993b702b368af5480adf019afddee7e8c1e5ce2e").send();

        assertResponse(response);
    }


    @Test
    public void testGetBlockByHash() throws IOException {
        ZksBlock response = zksync.zksGetBlockByHash("0xd933be650bf97d92c591a88c993b702b368af5480adf019afddee7e8c1e5ce2e", true).send();

        assertResponse(response);
    }

    @Test
    public void testGetBlockByNumber() throws IOException {
        ZksBlock response = zksync.zksGetBlockByNumber(DefaultBlockParameter.valueOf("0xb108e"), true).send();

        assertResponse(response);
    }
}
