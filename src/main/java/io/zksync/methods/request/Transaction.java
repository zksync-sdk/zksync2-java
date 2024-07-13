package io.zksync.methods.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.zksync.transaction.type.Transaction712;
import io.zksync.utils.AccountAbstractionVersion;
import io.zksync.utils.ContractDeployer;
import io.zksync.utils.ZkSyncAddresses;
import lombok.AllArgsConstructor;
import org.web3j.abi.FunctionEncoder;
import org.web3j.protocol.core.methods.response.AccessListObject;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;

/**
 * Transaction request object used the below methods.
 *
 * <ol>
 *   <li>eth_call
 *   <li>eth_estimateGas
 *   <li>zks_estimateFee
 * </ol>
 */
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction {

    private String from;
    private String to;
    private BigInteger gas;
    private BigInteger gasPrice;
    private BigInteger maxFeePerGas;
    private BigInteger maxPriorityFeePerGas;
    private BigInteger value;
    private String data;

    private Long type;
    private AccessListObject accessList;
    private Eip712Meta eip712Meta;

    public Transaction(String from, String to, BigInteger gas, BigInteger gasPrice, BigInteger value, String data, Eip712Meta eip712Meta) {
        this.from = from;
        this.to = to;
        this.gas = gas;
        this.gasPrice = gasPrice;
        this.value = value;
        this.data = data;
        this.eip712Meta = eip712Meta;

        this.type = (long) Transaction712.EIP_712_TX_TYPE;
    }

    public Transaction(String from, String to, BigInteger gas, BigInteger maxFeePerGas, BigInteger maxPriorityFeePerGas, BigInteger value, String data, Eip712Meta eip712Meta) {
        this.from = from;
        this.to = to;
        this.gas = gas;
        this.maxFeePerGas = maxFeePerGas;
        this.maxPriorityFeePerGas = maxPriorityFeePerGas;
        this.value = value;
        this.data = data;
        this.eip712Meta = eip712Meta;

        this.type = (long) Transaction712.EIP_712_TX_TYPE;
    }

    public static Transaction createEtherTransaction(
            String from,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            BigInteger value) {

        Eip712Meta meta = new Eip712Meta(BigInteger.valueOf(160000L), null, null, null);
        return new Transaction(from, to, gasPrice, gasLimit, value, "0x", meta);
    }

    public static Transaction create2ContractTransaction(
            String from,
            BigInteger gasPrice,
            BigInteger gasLimit,
            BigInteger value,
            String bytecode
    ) {
        byte[] bytecodeBytes = Numeric.hexStringToByteArray(bytecode);
        String calldata = FunctionEncoder.encode(ContractDeployer.encodeCreate2(bytecodeBytes));
        Eip712Meta meta = new Eip712Meta(BigInteger.valueOf(160000L), null, new byte[][] {bytecodeBytes}, null);
        return new Transaction(from, ZkSyncAddresses.CONTRACT_DEPLOYER_ADDRESS, gasPrice, gasLimit, value, calldata, meta);
    }

    public static Transaction create2ContractTransaction(
            String from,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String bytecode
    ) {
        byte[] bytecodeBytes = Numeric.hexStringToByteArray(bytecode);
        String calldata = FunctionEncoder.encode(ContractDeployer.encodeCreate2(bytecodeBytes));
        Eip712Meta meta = new Eip712Meta(BigInteger.valueOf(160000L), null, new byte[][] {bytecodeBytes}, null);
        return new Transaction(from, ZkSyncAddresses.CONTRACT_DEPLOYER_ADDRESS, gasPrice, gasLimit, null, calldata, meta);
    }

    public static Transaction create2ContractTransaction(
            String from,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String bytecode,
            String calldata
    ) {
        byte[] bytecodeBytes = Numeric.hexStringToByteArray(bytecode);
        byte[] calldataBytes = Numeric.hexStringToByteArray(calldata);
        String calldataCreate = FunctionEncoder.encode(ContractDeployer.encodeCreate2(bytecodeBytes, calldataBytes));
        Eip712Meta meta = new Eip712Meta(BigInteger.valueOf(160000L), null, new byte[][] {bytecodeBytes}, null);
        return new Transaction(from, ZkSyncAddresses.CONTRACT_DEPLOYER_ADDRESS, gasPrice, gasLimit, null, calldataCreate, meta);
    }

    public static Transaction create2ContractTransaction(
            String from,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String bytecode,
            String calldata,
            byte[] salt
    ) {
        byte[] bytecodeBytes = Numeric.hexStringToByteArray(bytecode);
        byte[] calldataBytes = Numeric.hexStringToByteArray(calldata);
        String calldataCreate = FunctionEncoder.encode(ContractDeployer.encodeCreate2(bytecodeBytes, calldataBytes, salt));
        Eip712Meta meta = new Eip712Meta(BigInteger.valueOf(160000L), null, new byte[][] {bytecodeBytes}, null);
        return new Transaction(from, ZkSyncAddresses.CONTRACT_DEPLOYER_ADDRESS, gasPrice, gasLimit, null, calldataCreate, meta);
    }

    public static Transaction create2ContractTransaction(
            String from,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String bytecode,
            String calldata,
            List<String> deps
    ) {
        byte[] bytecodeBytes = Numeric.hexStringToByteArray(bytecode);
        byte[][] factoryDeps = new byte[deps.size() + 1][];
        for (int i = 0; i < deps.size(); i ++) {
            factoryDeps[i] = Numeric.hexStringToByteArray(deps.get(i));
        }
        factoryDeps[deps.size()] = bytecodeBytes;
        Eip712Meta meta = new Eip712Meta(BigInteger.valueOf(160000L), null, factoryDeps, null);
        return new Transaction(from, ZkSyncAddresses.CONTRACT_DEPLOYER_ADDRESS, gasPrice, gasLimit, null, calldata, meta);
    }

    public static Transaction create2ContractTransaction(
            String from,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String bytecode,
            List<String> deps,
            String calldata,
            byte[] salt
    ) {
        byte[] bytecodeBytes = Numeric.hexStringToByteArray(bytecode);
        byte[] calldataBytes = Numeric.hexStringToByteArray(calldata);
        byte[][] factoryDeps = new byte[deps.size() + 1][];
        for (int i = 0; i < deps.size(); i ++) {
            factoryDeps[i] = Numeric.hexStringToByteArray(deps.get(i));
        }
        factoryDeps[deps.size()] = bytecodeBytes;
        String calldataCreate = FunctionEncoder.encode(ContractDeployer.encodeCreate2(bytecodeBytes, calldataBytes, salt));
        Eip712Meta meta = new Eip712Meta(BigInteger.valueOf(160000L), null, factoryDeps, null);
        return new Transaction(from, ZkSyncAddresses.CONTRACT_DEPLOYER_ADDRESS, gasPrice, gasLimit, null, calldataCreate, meta);
    }

    public static Transaction create2ContractTransaction(
            String from,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String bytecode,
            String calldata,
            String customSignature
    ) {
        byte[] bytecodeBytes = Numeric.hexStringToByteArray(bytecode);
        byte[] calldataBytes = Numeric.hexStringToByteArray(calldata);
        byte[] customSignatureBytes = Numeric.hexStringToByteArray(customSignature);
        String calldataCreate = FunctionEncoder.encode(ContractDeployer.encodeCreate2(bytecodeBytes, calldataBytes));
        Eip712Meta meta = new Eip712Meta(BigInteger.valueOf(160000L), customSignatureBytes, new byte[][] {bytecodeBytes}, null);
        return new Transaction(from, ZkSyncAddresses.CONTRACT_DEPLOYER_ADDRESS, gasPrice, gasLimit, null, calldataCreate, meta);
    }

    public static Transaction create2ContractTransaction(
            String from,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String bytecode,
            String calldata,
            String customSignature,
            PaymasterParams paymasterParams
    ) {
        byte[] bytecodeBytes = Numeric.hexStringToByteArray(bytecode);
        byte[] calldataBytes = Numeric.hexStringToByteArray(calldata);
        byte[] customSignatureBytes = Numeric.hexStringToByteArray(customSignature);
        String calldataCreate = FunctionEncoder.encode(ContractDeployer.encodeCreate2(bytecodeBytes, calldataBytes));
        Eip712Meta meta = new Eip712Meta(BigInteger.valueOf(160000L), customSignatureBytes, new byte[][] {bytecodeBytes}, paymasterParams);
        return new Transaction(from, ZkSyncAddresses.CONTRACT_DEPLOYER_ADDRESS, gasPrice, gasLimit, null, calldataCreate, meta);
    }

    public static Transaction create2AccountTransaction(
            String from,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String bytecode
    ) {
        byte[] bytecodeBytes = Numeric.hexStringToByteArray(bytecode);
        String calldata = FunctionEncoder.encode(ContractDeployer.encodeCreate2Account(bytecodeBytes));
        Eip712Meta meta = new Eip712Meta(BigInteger.valueOf(160000L), null, new byte[][] {bytecodeBytes}, null);
        return new Transaction(from, ZkSyncAddresses.CONTRACT_DEPLOYER_ADDRESS, gasPrice, gasLimit, null, calldata, meta);
    }

    public static Transaction create2AccountTransaction(
            String from,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String bytecode,
            String calldata
    ) {
        byte[] bytecodeBytes = Numeric.hexStringToByteArray(bytecode);
        byte[] calldataBytes = Numeric.hexStringToByteArray(calldata);
        String calldataCreate = FunctionEncoder.encode(ContractDeployer.encodeCreate2Account(bytecodeBytes, calldataBytes));
        Eip712Meta meta = new Eip712Meta(BigInteger.valueOf(160000L), null, new byte[][] {bytecodeBytes}, null);
        return new Transaction(from, ZkSyncAddresses.CONTRACT_DEPLOYER_ADDRESS, gasPrice, gasLimit, null, calldataCreate, meta);
    }
    public static Transaction create2AccountTransaction(
            String from,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String bytecode,
            String calldata,
            byte[] salt
    ) {
        byte[] bytecodeBytes = Numeric.hexStringToByteArray(bytecode);
        byte[] calldataBytes = Numeric.hexStringToByteArray(calldata);
        String calldataCreate = FunctionEncoder.encode(ContractDeployer.encodeCreate2Account(bytecodeBytes, calldataBytes, salt, AccountAbstractionVersion.Version1));
        Eip712Meta meta = new Eip712Meta(BigInteger.valueOf(160000L), null, new byte[][] {bytecodeBytes}, null);
        return new Transaction(from, ZkSyncAddresses.CONTRACT_DEPLOYER_ADDRESS, gasPrice, gasLimit, null, calldataCreate, meta);
    }


    public static Transaction createContractTransaction(
            String from,
            BigInteger gasPrice,
            BigInteger gasLimit,
            BigInteger value,
            String bytecode
    ) {
        byte[] bytecodeBytes = Numeric.hexStringToByteArray(bytecode);
        String calldata = FunctionEncoder.encode(ContractDeployer.encodeCreate(bytecodeBytes));
        Eip712Meta meta = new Eip712Meta(BigInteger.valueOf(160000L), null, new byte[][] {bytecodeBytes}, null);
        return new Transaction(from, ZkSyncAddresses.CONTRACT_DEPLOYER_ADDRESS, gasPrice, gasLimit, value, calldata, meta);
    }

    public static Transaction createContractTransaction(
            String from,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String bytecode
    ) {
        byte[] bytecodeBytes = Numeric.hexStringToByteArray(bytecode);
        String calldata = FunctionEncoder.encode(ContractDeployer.encodeCreate(bytecodeBytes));
        Eip712Meta meta = new Eip712Meta(BigInteger.valueOf(160000L), null, new byte[][] {bytecodeBytes}, null);
        return new Transaction(from, ZkSyncAddresses.CONTRACT_DEPLOYER_ADDRESS, gasPrice, gasLimit, null, calldata, meta);
    }

    public static Transaction createContractTransaction(
            String from,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String bytecode,
            String calldata
    ) {
        byte[] bytecodeBytes = Numeric.hexStringToByteArray(bytecode);
        byte[] calldataBytes = Numeric.hexStringToByteArray(calldata);
        String calldataCreate = FunctionEncoder.encode(ContractDeployer.encodeCreate(bytecodeBytes, calldataBytes));
        Eip712Meta meta = new Eip712Meta(BigInteger.valueOf(160000L), null, new byte[][] {bytecodeBytes}, null);
        return new Transaction(from, ZkSyncAddresses.CONTRACT_DEPLOYER_ADDRESS, gasPrice, gasLimit, null, calldataCreate, meta);
    }

    public static Transaction createContractTransaction(
            String from,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String bytecode,
            List<String> deps,
            String calldata
    ) {
        byte[] bytecodeBytes = Numeric.hexStringToByteArray(bytecode);
        byte[] calldataBytes = Numeric.hexStringToByteArray(calldata);
        byte[][] factoryDeps = new byte[deps.size() + 1][];
        for (int i = 0; i < deps.size(); i ++) {
            factoryDeps[i] = Numeric.hexStringToByteArray(deps.get(i));
        }
        factoryDeps[deps.size()] = bytecodeBytes;
        String calldataCreate = FunctionEncoder.encode(ContractDeployer.encodeCreate(bytecodeBytes, calldataBytes));
        Eip712Meta meta = new Eip712Meta(BigInteger.valueOf(160000L), null, factoryDeps, null);
        return new Transaction(from, ZkSyncAddresses.CONTRACT_DEPLOYER_ADDRESS, gasPrice, gasLimit, null, calldataCreate, meta);
    }

    public static Transaction createFunctionCallTransaction(
            String from,
            String to,
            BigInteger value,
            String data,
            BigInteger gasPerPubData
    ) {
        Eip712Meta meta = new Eip712Meta(gasPerPubData, null, null, null);
        return new Transaction(from, to, null, null, value, data, meta);
    }

    public static Transaction createFunctionCallTransaction(
            String from,
            String to,
            BigInteger gasPrice,
            BigInteger gasLimit,
            BigInteger value,
            String data
    ) {
        Eip712Meta meta = new Eip712Meta(BigInteger.valueOf(160000L), null, null, null);
        return new Transaction(from, to, gasPrice, gasLimit, value, data, meta);
    }

    public static Transaction createFunctionCallTransaction(
            String from,
            String to,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String data
    ) {

        Eip712Meta meta = new Eip712Meta(BigInteger.valueOf(160000L), null, null, null);
        return new Transaction(from, to, gasPrice, gasLimit, null, data, meta);
    }

    public static Transaction createFunctionCallTransaction(
            String from,
            String to,
            BigInteger gasPrice,
            BigInteger gasLimit,
            BigInteger value,
            String data,
            String customSignature,
            PaymasterParams paymasterParams
    ) {
        Eip712Meta meta = new Eip712Meta(BigInteger.valueOf(160000L), Numeric.hexStringToByteArray(customSignature), null, paymasterParams);
        return new Transaction(from, to, gasPrice, gasLimit, value, data, meta);
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getGas() {
        return convert(gas);
    }

    public String getGasPrice() {
        return convert(gasPrice);
    }
    public BigInteger getMaxFeePerGas() {
        return maxFeePerGas;
    }
    public BigInteger getMaxPriorityFeePerGas() {
        return maxPriorityFeePerGas;
    }

    public String getValue() {
        return convert(value);
    }

    @JsonIgnore
    public BigInteger getGasNumber() {
        return gas;
    }

    @JsonIgnore
    public BigInteger getGasPriceNumber() {
        return gasPrice;
    }

    @JsonIgnore
    public BigInteger getValueNumber() {
        return value;
    }

    public String getData() {
        return data;
    }

    public String getType() {
        return convert(BigInteger.valueOf(type));
    }

    public AccessListObject getAccessList() {
        return accessList;
    }

    public Eip712Meta getEip712Meta() {
        return eip712Meta;
    }

    private static String convert(BigInteger value) {
        if (value != null) {
            return Numeric.encodeQuantity(value);
        } else {
            return null; // we don't want the field to be encoded if not present
        }
    }

}
