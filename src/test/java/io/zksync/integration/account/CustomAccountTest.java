package io.zksync.integration.account;

import io.zksync.abi.TransactionEncoder;
import io.zksync.crypto.eip712.Eip712Domain;
import io.zksync.crypto.eip712.Eip712Encoder;
import io.zksync.helper.CounterContract;
import io.zksync.helper.CustomAccountContract;
import io.zksync.integration.BaseIntegrationEnv;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.transaction.type.Transaction712;
import io.zksync.utils.ContractDeployer;

import io.zksync.utils.ZkSyncAddresses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariables;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EnabledIfEnvironmentVariables({
        @EnabledIfEnvironmentVariable(named = "ZKSYNC2_JAVA_CI_L1_NODE_URL", matches = "http*"),
        @EnabledIfEnvironmentVariable(named = "ZKSYNC2_JAVA_CI_L2_NODE_URL", matches = "http*"),
})
public class CustomAccountTest extends BaseIntegrationEnv {

    static final SecureRandom random = new SecureRandom();

    private String customAccountAddress;

    @BeforeEach
    public void prepareBalance() throws IOException {
        EthGetBalance getBalance = zksync
                .ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST)
                .send();
        assertResponse(getBalance);
        if (getBalance.getBalance().compareTo(BigInteger.ZERO) <= 0) {
            sendTestMoney();
            deposit();
        }
    }

    @Test
    public void deployCustomAccount_Create2() throws IOException, TransactionException {
        BigInteger nonce = this.zksync
                .ethGetTransactionCount(this.credentials.getAddress(), DefaultBlockParameterName.PENDING).send()
                .getTransactionCount();

        String constructor = FunctionEncoder.encodeConstructor(Collections.singletonList(new Bool(false)));

        byte[] salt = random.generateSeed(32);

        String precomputedAddress = ContractDeployer.computeL2Create2Address(new Address(this.credentials.getAddress()), Numeric.hexStringToByteArray(CustomAccountContract.BINARY), Numeric.hexStringToByteArray(constructor), salt).getValue();

        io.zksync.methods.request.Transaction estimate = io.zksync.methods.request.Transaction.create2AccountTransaction(
                credentials.getAddress(),
                BigInteger.ZERO,
                BigInteger.ZERO,
                CustomAccountContract.BINARY,
                constructor,
                salt
        );

        EthEstimateGas estimateGas = zksync.ethEstimateGas(estimate).send();
        EthGasPrice gasPrice = zksync.ethGasPrice().send();

        assertResponse(estimateGas);
        assertResponse(gasPrice);

        System.out.printf("Fee for transaction is: %d\n", estimateGas.getAmountUsed().multiply(gasPrice.getGasPrice()));

        Transaction712 transaction = new Transaction712(
                chainId.longValue(),
                nonce,
                estimateGas.getAmountUsed(),
                estimate.getTo(),
                estimate.getValueNumber(),
                estimate.getData(),
                BigInteger.valueOf(100000000L),
                gasPrice.getGasPrice(),
                credentials.getAddress(),
                estimate.getEip712Meta()
        );

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, transaction)).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction sent = this.zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();

        assertResponse(sent);

        TransactionReceipt receipt = this.processor.waitForTransactionReceipt(sent.getResult());

        assertTrue(receipt::isStatusOK);

        this.customAccountAddress = receipt.getContractAddress();
        System.out.println("Deployed `CustomAccount as: `" + this.customAccountAddress);
        assertEquals(this.customAccountAddress, precomputedAddress);

        Transaction call = Transaction.createEthCallTransaction(
                credentials.getAddress(),
                customAccountAddress,
                FunctionEncoder.encode(CounterContract.encodeGet())
        );

        zksync.ethCall(call, ZkBlockParameterName.COMMITTED).send();
    }

    @Test
    public void isCustomAccount() {
        Function isAccount = new Function("isAccount", Collections.singletonList(new Address(customAccountAddress)), Collections.singletonList(new TypeReference<Bool>() {}));

        Transaction call = Transaction.createEthCallTransaction(
                null,
                ZkSyncAddresses.CONTRACT_DEPLOYER_ADDRESS,
                FunctionEncoder.encode(isAccount)
        );

        Boolean result = zksync.ethCall(call, ZkBlockParameterName.COMMITTED).sendAsync()
                .thenApply((ret) -> FunctionReturnDecoder.decode(ret.getValue(), isAccount.getOutputParameters()))
                .thenApply((ret) -> (Boolean) ret.get(0).getValue())
                .join();
        assertTrue(result);
    }

    @Test
    public void sendMoneyToCustomAccount() throws Exception {
        Transfer transfer = new Transfer(zksync, new RawTransactionManager(zksync, credentials, chainId.longValue()));

        EthGasPrice gasPrice = zksync.ethGasPrice().send();
        assertResponse(gasPrice);

        TransactionReceipt receipt = transfer.sendFunds(
                customAccountAddress,
                BigDecimal.valueOf(1.1),
                Convert.Unit.ETHER,
                gasPrice.getGasPrice(),
                BigInteger.valueOf(50_000L)
        ).send();

        assertTrue(receipt::isStatusOK);
    }

    @Test
    public void accountBalance() throws IOException {
        EthGetBalance getBalance = zksync
                .ethGetBalance(customAccountAddress, DefaultBlockParameterName.LATEST)
                .send();

        assertResponse(getBalance);

        System.out.printf("%s: %s ETH (%d)\n", customAccountAddress, Convert.fromWei(Numeric.toBigInt(getBalance.getResult()).toString(), Convert.Unit.ETHER), Numeric.toBigInt(getBalance.getResult()));
        assertTrue(getBalance.getBalance().compareTo(BigInteger.ZERO) > 0);
    }

    @Test
    public void transferNative() throws IOException, TransactionException {
        BigInteger nonce = zksync
                .ethGetTransactionCount(customAccountAddress, ZkBlockParameterName.COMMITTED).send()
                .getTransactionCount();

        io.zksync.methods.request.Transaction estimate = io.zksync.methods.request.Transaction.createFunctionCallTransaction(
                customAccountAddress,
                credentials.getAddress(),
                BigInteger.ZERO,
                BigInteger.ZERO,
                "0x"
        );

        EthEstimateGas estimateGas = zksync.ethEstimateGas(estimate).send();

        EthGasPrice gasPrice = zksync.ethGasPrice().send();

        assertResponse(estimateGas);
        assertResponse(gasPrice);

        System.out.printf("Fee for transaction is: %d\n", estimateGas.getAmountUsed().multiply(gasPrice.getGasPrice()));

        Transaction712 transaction = new Transaction712(
                chainId.longValue(),
                nonce,
                estimateGas.getAmountUsed(),
                estimate.getTo(),
                Convert.toWei(BigDecimal.valueOf(0.5), Convert.Unit.ETHER).toBigInteger(),
                estimate.getData(),
                BigInteger.valueOf(100000000L),
                gasPrice.getGasPrice(),
                customAccountAddress,
                estimate.getEip712Meta()
        );

        byte[] digest = Eip712Encoder.typedDataToSignedBytes(Eip712Domain.defaultDomain(chainId.longValue()), transaction);

        String signature = Numeric.toHexString(digest) + Numeric.cleanHexPrefix(transaction.getFrom());
        transaction.getMeta().setCustomSignature(Numeric.hexStringToByteArray(signature));
        byte[] message = TransactionEncoder.encode(transaction, null);

        EthSendTransaction sent = zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();

        assertResponse(sent);

        TransactionReceipt receipt = processor.waitForTransactionReceipt(sent.getResult());

        assertTrue(receipt::isStatusOK);
    }
}
