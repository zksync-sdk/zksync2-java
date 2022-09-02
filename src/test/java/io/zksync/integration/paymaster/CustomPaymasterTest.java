package io.zksync.integration.paymaster;

import io.zksync.abi.TransactionEncoder;
import io.zksync.integration.BaseIntegrationEnv;
import io.zksync.methods.request.Eip712Meta;
import io.zksync.methods.request.PaymasterParams;
import io.zksync.methods.response.ZksTestnetPaymasterAddress;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.transaction.manager.ZkSyncTransactionManager;
import io.zksync.transaction.type.Transaction712;
import io.zksync.utils.Paymaster;
import io.zksync.wrappers.ERC20;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.web3j.abi.FunctionEncoder;
import org.web3j.crypto.Hash;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.*;

public class CustomPaymasterTest extends BaseIntegrationEnv {

    private static final byte[] SALT = Hash.sha3("TestPaymaster".getBytes(Charset.defaultCharset()));

    private static final Token USDC = new Token("0xd35cceead182dcee0f148ebac9447da2c4d449c4", "0x72c4f199cb8784425542583d345e7c00d642e345", "USDC", 6);

    public static String BINARY;

    @BeforeAll
    public static void loadBinary() throws IOException {
        try (FileInputStream fis = new FileInputStream("src/test/resources/customPaymasterBinary.hex")) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int count;
            byte[] bytes = new byte[16536];
            while ((count = fis.read(bytes)) != -1) {
                buffer.write(bytes, 0, count);
            }
            BINARY = buffer.toString("UTF-8");
        }
    }

    @BeforeEach
    public void checkBalance() throws IOException {
        EthGetBalance balance = zksync.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.PENDING).send();

        assertResponse(balance);

        if (balance.getBalance().compareTo(Convert.toWei("0.005", Convert.Unit.ETHER).toBigInteger()) <= 0) {
            fail("Not enough balance of the wallet (min 0.005 ETH): " + credentials.getAddress());
        }
    }

    @Test
    public void deployPaymaster() throws IOException, TransactionException {
        Assumptions.assumeFalse(isDeployed(), "Test paymaster already deployed. Skipping");
        BigInteger nonce = this.zksync
                .ethGetTransactionCount(this.credentials.getAddress(), DefaultBlockParameterName.PENDING).send()
                .getTransactionCount();

        String constructor = "";

        String precomputedAddress = paymasterAddress();

        io.zksync.methods.request.Transaction estimate = io.zksync.methods.request.Transaction.create2ContractTransaction(
                credentials.getAddress(),
                BigInteger.ZERO,
                BigInteger.ZERO,
                BINARY,
                constructor,
                SALT
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

        System.out.println("Deployed `CustomAccount as: `" + precomputedAddress);
        assertEquals(receipt.getContractAddress(), precomputedAddress);
    }

    @Test
    public void sendFundsForFee() throws Exception {
        Assumptions.assumeTrue(isDeployed(), "Test paymaster must be deployed. Skipping");

        String paymasterAddress = paymasterAddress();

        io.zksync.methods.request.Transaction estimate = io.zksync.methods.request.Transaction.createFunctionCallTransaction(
                credentials.getAddress(),
                paymasterAddress,
                BigInteger.ZERO,
                BigInteger.ZERO,
                "0x"
        );

        EthEstimateGas estimateGas = zksync.ethEstimateGas(estimate).send();

        EthGasPrice gasPrice = zksync.ethGasPrice().send();

        assertResponse(estimateGas);
        assertResponse(gasPrice);

        BigInteger fee = estimateGas.getAmountUsed().multiply(gasPrice.getGasPrice());

        BigInteger balanceInUsdc = wallet.getBalance(USDC).send();

        assertTrue(balanceInUsdc.compareTo(fee) >= 0, String.format("Not enough balance for pay fee %d (current) < %d (required", balanceInUsdc, fee));

        TransactionReceipt receipt = wallet.transfer(paymasterAddress, fee).send();

        assertTrue(receipt::isStatusOK);
    }

    @Test
    public void sendFundsWithPaymaster() throws Exception {
        Assumptions.assumeTrue(isDeployed(), "Test paymaster must be deployed. Skipping");

        String paymasterAddress = paymasterAddress();

        io.zksync.methods.request.Transaction estimate = io.zksync.methods.request.Transaction.createFunctionCallTransaction(
                credentials.getAddress(),
                credentials.getAddress(),
                BigInteger.ZERO,
                BigInteger.ZERO,
                "0x"
        );

        EthEstimateGas estimateGas = zksync.ethEstimateGas(estimate).send();

        EthGasPrice gasPrice = zksync.ethGasPrice().send();

        assertResponse(estimateGas);
        assertResponse(gasPrice);

        BigInteger fee = gasPrice.getGasPrice().multiply(estimateGas.getAmountUsed());

        EthGetBalance balance = zksync.ethGetBalance(paymasterAddress, DefaultBlockParameterName.LATEST).send();
        assertResponse(balance);

        Assumptions.assumeTrue(balance.getBalance().compareTo(fee) > 0);

        ERC20 erc20 = ERC20.load(USDC.getL2Address(), zksync,
                new ZkSyncTransactionManager(zksync, signer, feeProvider),
                feeProvider);

        if (erc20.allowance(credentials.getAddress(), paymasterAddress).send().compareTo(fee) < 0) {
            TransactionReceipt approve = erc20.approve(paymasterAddress, fee).send();

            assertTrue(approve::isStatusOK);
        }

        BigInteger balanceBefore = zksync.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.PENDING).send().getBalance();

        Eip712Meta meta = estimate.getEip712Meta();

        PaymasterParams paymasterParams = new PaymasterParams(paymasterAddress, Numeric.hexStringToByteArray(FunctionEncoder.encode(Paymaster.encodeApprovalBased(USDC.getL2Address(), fee, new byte[] {}))));

        meta.setPaymasterParams(paymasterParams);

        BigInteger nonce = this.zksync
                .ethGetTransactionCount(this.credentials.getAddress(), DefaultBlockParameterName.PENDING).send()
                .getTransactionCount();

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
                meta
        );

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, transaction)).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction sent = zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();

        assertResponse(sent);

        TransactionReceipt receipt = processor.waitForTransactionReceipt(sent.getResult());

        assertTrue(receipt::isStatusOK);

        BigInteger balanceAfter = zksync.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.PENDING).send().getBalance();

        assertEquals(balanceBefore, balanceAfter);
    }

    private boolean isDeployed() throws IOException {
        String paymasterAddress = paymasterAddress();
        EthGetCode code = zksync.ethGetCode(paymasterAddress, ZkBlockParameterName.COMMITTED).send();
        return Numeric.hexStringToByteArray(code.getCode()).length > 0;
    }

    private String paymasterAddress() {
//        return ContractDeployer.computeL2Create2Address(new Address(credentials.getAddress()), Numeric.hexStringToByteArray(BINARY), new byte[] {}, SALT).getValue();
        ZksTestnetPaymasterAddress paymasterAddress = zksync.zksGetTestnetPaymaster().sendAsync().join();

        assertResponse(paymasterAddress);
        return paymasterAddress.getResult();
    }

}
