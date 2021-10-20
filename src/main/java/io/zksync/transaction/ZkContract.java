package io.zksync.transaction;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.util.Arrays;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.Contract;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Numeric;

import io.zksync.abi.TransactionEncoder;
import io.zksync.abi.ZkFunctionEncoder;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.crypto.signer.PrivateKeyEthSigner;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.AccountType;
import io.zksync.protocol.core.TimeRange;
import io.zksync.protocol.core.ZkSyncNetwork;
import io.zksync.protocol.core.domain.token.Token;
import io.zksync.transaction.fee.DefaultTransactionFeeProvider;
import io.zksync.transaction.fee.ZkTransactionFeeProvider;
import io.zksync.wrappers.ZkSyncL2Proto;

public class ZkContract extends Contract {

    protected ZkSync zksync;
    protected ZkTransactionFeeProvider txFeeProvider;
    protected EthSigner signer;

    protected TimeRange timeRange = new TimeRange();

    protected ZkContract(String contractBinary, String contractAddress, ZkSync zksync,
            TransactionManager transactionManager, ZkTransactionFeeProvider feeProvider, EthSigner signer) {
        super(contractBinary, contractAddress, zksync, transactionManager, new StaticGasProvider(BigInteger.ZERO, BigInteger.ZERO));
        this.zksync = zksync;
        this.txFeeProvider = feeProvider;
        this.signer = signer;
    }

    protected ZkContract(String contractAddress, ZkSync zksync,
            TransactionManager transactionManager, ZkTransactionFeeProvider feeProvider, EthSigner signer) {
        this("", contractAddress, zksync, transactionManager, feeProvider, signer);
    }

    protected ZkContract(String contractAddress, ZkSync zksync,
            TransactionManager transactionManager, ZkTransactionFeeProvider feeProvider, Credentials credentials, Long chainId) {
        this("", contractAddress, zksync, transactionManager, feeProvider, new PrivateKeyEthSigner(credentials, chainId));
    }

    public void setTimeRange(TimeRange timeRange) {
        this.timeRange = timeRange;
    }

    public TimeRange getTimeRange() {
        return timeRange;
    }

    public ZkTransactionFeeProvider getFeeProvider() {
        return txFeeProvider;
    }

    private static <T extends ZkContract> T create(
            T contract, String binary, String encodedConstructor)
            throws IOException, TransactionException {

        BigInteger nonce = contract.zksync.ethGetTransactionCount(contract.signer.getAddress(), contract.defaultBlockParameter).send().getTransactionCount();
        DeployContract zkDeploy = contract.packDeployContract(AccountType.ZkRollup, binary, encodedConstructor, nonce.intValue());
        zkDeploy.setFee(contract.getFeeProvider().getTotalFee(zkDeploy));

        Function encoded = TransactionEncoder.encodeToFunction(zkDeploy, contract.signer);
        TransactionReceipt transactionReceipt = contract.executeTransaction(encoded);

        String contractAddress = transactionReceipt.getContractAddress();
        if (contractAddress == null) {
            throw new RuntimeException("Empty contract address returned");
        }
        contract.setContractAddress(contractAddress);
        contract.setTransactionReceipt(transactionReceipt);

        return contract;
    }

    protected static <T extends ZkContract> T deploy(
            Class<T> type,
            ZkSync web3j,
            Credentials credentials,
            ZkTransactionFeeProvider feeProvider,
            String binary,
            String encodedConstructor,
            BigInteger value)
            throws RuntimeException, TransactionException {

        try {
            Constructor<T> constructor =
                    type.getDeclaredConstructor(
                            String.class,
                            ZkSync.class,
                            TransactionManager.class,
                            ZkTransactionFeeProvider.class,
                            EthSigner.class);
            constructor.setAccessible(true);

            Long chainId = (long) ZkSyncNetwork.Localhost.getChainId(); // FIXME: Add correct getting chain id of the network
            // we want to use null here to ensure that "to" parameter on message is not populated
            T contract = constructor.newInstance(null, web3j, new RawTransactionManager(web3j, credentials), feeProvider, new PrivateKeyEthSigner(credentials, chainId));

            return create(contract, binary, encodedConstructor);
        } catch (TransactionException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected static <T extends ZkContract> T deploy(
            Class<T> type,
            ZkSync web3j,
            TransactionManager transactionManager,
            ZkTransactionFeeProvider feeProvider,
            String binary,
            String encodedConstructor,
            BigInteger value)
            throws RuntimeException, TransactionException {

        throw new UnsupportedOperationException("Not supported deploying contract without signer");
    }

    @Deprecated
    protected static <T extends ZkContract> T deploy(
            Class<T> type,
            ZkSync web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String binary,
            String encodedConstructor,
            BigInteger value)
            throws RuntimeException, TransactionException {

        return deploy(
                type,
                web3j,
                credentials,
                new DefaultTransactionFeeProvider(web3j, Token.ETH),
                binary,
                encodedConstructor,
                value);
    }

    @Deprecated
    protected static <T extends ZkContract> T deploy(
            Class<T> type,
            ZkSync web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String binary,
            String encodedConstructor,
            BigInteger value)
            throws RuntimeException, TransactionException {

        throw new UnsupportedOperationException("Not supported deploying contract without signer");
    }

    public static <T extends ZkContract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            ZkSync web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String binary,
            String encodedConstructor,
            BigInteger value) {
        return new RemoteCall<>(
                () ->
                        deploy(
                                type,
                                web3j,
                                credentials,
                                gasPrice,
                                gasLimit,
                                binary,
                                encodedConstructor,
                                value));
    }

    public static <T extends ZkContract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            ZkSync web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String binary,
            String encodedConstructor) {
        return deployRemoteCall(
                type,
                web3j,
                credentials,
                gasPrice,
                gasLimit,
                binary,
                encodedConstructor,
                BigInteger.ZERO);
    }

    public static <T extends ZkContract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            ZkSync web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider,
            String binary,
            String encodedConstructor,
            BigInteger value) {
        return new RemoteCall<>(
                () ->
                        deploy(
                                type,
                                web3j,
                                credentials,
                                contractGasProvider,
                                binary,
                                encodedConstructor,
                                value));
    }

    public static <T extends ZkContract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            ZkSync web3j,
            Credentials credentials,
            ZkTransactionFeeProvider feeProvider,
            String binary,
            String encodedConstructor) {
        return new RemoteCall<>(
                () ->
                        deploy(
                                type,
                                web3j,
                                credentials,
                                feeProvider,
                                binary,
                                encodedConstructor,
                                BigInteger.ZERO));
    }

    public static <T extends ZkContract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            ZkSync web3j,
            TransactionManager transactionManager,
            ZkTransactionFeeProvider feeProvider,
            String binary,
            String encodedConstructor,
            BigInteger value) {
        return new RemoteCall<>(
                () ->
                        deploy(
                                type,
                                web3j,
                                transactionManager,
                                feeProvider,
                                binary,
                                encodedConstructor,
                                value));
    }

    public static <T extends ZkContract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            ZkSync web3j,
            TransactionManager transactionManager,
            ZkTransactionFeeProvider feeProvider,
            String binary,
            String encodedConstructor) {
        return new RemoteCall<>(
                () ->
                        deploy(
                                type,
                                web3j,
                                transactionManager,
                                feeProvider,
                                binary,
                                encodedConstructor,
                                BigInteger.ZERO));
    }

    @Override
    protected TransactionReceipt executeTransaction(Function function) throws IOException, TransactionException {
        switch (function.getName()) {
            case ZkSyncL2Proto.FUNC_DEPLOYCONTRACT:
            case ZkSyncL2Proto.FUNC_EXECUTE:
            case ZkSyncL2Proto.FUNC_MIGRATETOPORTER:
            case ZkSyncL2Proto.FUNC_TRANSFER:
            case ZkSyncL2Proto.FUNC_WITHDRAW:
                return super.executeTransaction(function);
            default:
            BigInteger nonce = zksync.ethGetTransactionCount(this.transactionManager.getFromAddress(), this.defaultBlockParameter).send().getTransactionCount();
            Execute zkExecute = packContractFunctionForCall(function, nonce.intValue());
            zkExecute.setFee(this.getFeeProvider().getTotalFee(zkExecute));
            Function encoded = TransactionEncoder.encodeToFunction(zkExecute, this.signer);
    
            return super.executeTransaction(new Function(
                encoded.getName(),
                encoded.getInputParameters(),
                Arrays.<TypeReference<?>>asList(function.getOutputParameters().toArray(new TypeReference<?>[0]))
            ));
        }
    }

    protected Execute packContractFunctionForCall(Function function, Integer nonce) {
        String encodedFunction = Numeric.toHexString(ZkFunctionEncoder.encodeCalldata(function));
        Execute zkExecute = new Execute(
            this.getContractAddress(),
            encodedFunction,
            this.transactionManager.getFromAddress(),
            this.txFeeProvider.getFeeToken().getAddress(),
            BigInteger.ZERO,
            nonce,
            timeRange
        );

        return zkExecute;
    }

    protected DeployContract packDeployContract(AccountType accountType, String binary, String encodedConstructor, Integer nonce) {
        DeployContract zkDeploy;

        if (encodedConstructor == null || encodedConstructor.isEmpty()) {
            zkDeploy = new DeployContract(
                accountType,
                binary,
                this.transactionManager.getFromAddress(),
                this.txFeeProvider.getFeeToken().getAddress(),
                BigInteger.ZERO,
                nonce,
                new TimeRange()
            );
        } else {
            zkDeploy = new DeployContract(
                accountType,
                binary,
                encodedConstructor,
                this.transactionManager.getFromAddress(),
                this.txFeeProvider.getFeeToken().getAddress(),
                BigInteger.ZERO,
                nonce,
                new TimeRange()
            );
        }

        return zkDeploy;
    }
    
}
