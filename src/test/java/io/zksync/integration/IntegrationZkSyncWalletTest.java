package io.zksync.integration;

import static org.junit.jupiter.api.Assertions.*;

import io.zksync.ZkSyncWallet;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.crypto.signer.PrivateKeyEthSigner;
import io.zksync.helper.ConstructorContract;
import io.zksync.helper.CounterContract;
import io.zksync.methods.response.ZkTransactionReceipt;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.L2ToL1MessageProof;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.protocol.provider.EthereumProvider;
import io.zksync.transaction.manager.ZkSyncTransactionManager;
import io.zksync.utils.Messenger;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.ContractUtils;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetCode;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

@Disabled
public class IntegrationZkSyncWalletTest {

  private static final String L1_NODE = "http://127.0.0.1:8545";
  private static final String L2_NODE = "http://127.0.0.1:3050";

  private static ZkSyncWallet wallet;
  private static Credentials credentials;

  @BeforeAll
  public static void setUp() {
    ZkSync zksync = ZkSync.build(new HttpService(L2_NODE));
    credentials = Credentials.create(ECKeyPair.create(BigInteger.ONE));

    BigInteger chainId = zksync.ethChainId().sendAsync().join().getChainId();

    EthSigner signer = new PrivateKeyEthSigner(credentials, chainId.longValue());

    wallet = new ZkSyncWallet(zksync, signer, Token.ETH);
  }

  @Test
  public void sendTestMoney() {
    Web3j web3j = Web3j.build(new HttpService(L1_NODE));

    String account = web3j.ethAccounts().sendAsync().join().getAccounts().get(0);

    EthSendTransaction sent =
        web3j
            .ethSendTransaction(
                Transaction.createEtherTransaction(
                    account,
                    null,
                    BigInteger.ZERO,
                    BigInteger.valueOf(21_000L),
                    credentials.getAddress(),
                    Convert.toWei("1000", Convert.Unit.ETHER).toBigInteger()))
            .sendAsync()
            .join();

    assertResponse(sent);
  }

  @Test
  public void testDeposit() throws IOException {
    Web3j web3j = Web3j.build(new HttpService(L1_NODE));
    BigInteger chainId = web3j.ethChainId().send().getChainId();
    TransactionManager manager = new RawTransactionManager(web3j, credentials, chainId.longValue());

    ContractGasProvider gasProvider = new DefaultGasProvider();
    TransactionReceipt receipt =
        EthereumProvider.load(wallet.getZksync(), web3j, manager, gasProvider)
            .join()
            .deposit(
                Token.ETH,
                Convert.toWei("1", Convert.Unit.GWEI).toBigInteger(),
                BigInteger.ZERO,
                credentials.getAddress())
            .join();

    System.out.println(receipt);
  }

  @Test
  public void testTransfer() throws Exception {
    BigInteger amount = Token.ETH.toBigInteger(0.5);
    BigInteger desiredFee =
        BigInteger.valueOf(10560L).multiply(BigInteger.valueOf(28572L)); // Only for test
    EthGetBalance balance =
        wallet
            .getZksync()
            .ethGetBalance(credentials.getAddress(), ZkBlockParameterName.COMMITTED)
            .send();

    assertResponse(balance);

    TransactionReceipt receipt = wallet.transfer(credentials.getAddress(), amount).send();

    assertTrue(receipt.isStatusOK());

    EthGetBalance balanceNew =
        wallet
            .getZksync()
            .ethGetBalance(credentials.getAddress(), ZkBlockParameterName.COMMITTED)
            .send();

    assertResponse(balanceNew);

    assertEquals(
        balance.getBalance().subtract(amount).subtract(desiredFee), balanceNew.getBalance());
  }

  @Test
  public void testWithdraw() throws Exception {
    BigInteger amount = Token.ETH.toBigInteger(0.5);
    BigInteger desiredFee =
        BigInteger.valueOf(10560L).multiply(BigInteger.valueOf(28572L)); // Only for test
    EthGetBalance balance =
        wallet
            .getZksync()
            .ethGetBalance(credentials.getAddress(), ZkBlockParameterName.COMMITTED)
            .send();

    assertResponse(balance);

    TransactionReceipt receipt = wallet.withdraw(credentials.getAddress(), amount).send();

    assertTrue(receipt.isStatusOK());

    EthGetBalance balanceNew =
        wallet
            .getZksync()
            .ethGetBalance(credentials.getAddress(), ZkBlockParameterName.COMMITTED)
            .send();

    assertResponse(balanceNew);

    assertEquals(
        balanceNew.getBalance(), balance.getBalance().subtract(amount).subtract(desiredFee));
  }

  @Test
  public void testDeploy() throws Exception {

    BigInteger nonce = wallet.getNonce().send();
    String contractAddress = ContractUtils.generateContractAddress(credentials.getAddress(), nonce);

    EthGetCode code =
        wallet.getZksync().ethGetCode(contractAddress, DefaultBlockParameterName.PENDING).send();

    assertResponse(code);
    assertEquals("0x", code.getCode());

    TransactionReceipt receipt =
        wallet.deploy(Numeric.hexStringToByteArray(CounterContract.BINARY)).send();

    assertTrue(receipt.isStatusOK());

    EthGetCode codeDeployed =
        wallet.getZksync().ethGetCode(contractAddress, DefaultBlockParameterName.PENDING).send();

    assertResponse(codeDeployed);
    assertNotEquals("0x", codeDeployed.getCode());
  }

  @Test
  public void testDeployWithConstructor() throws Exception {

    BigInteger nonce = wallet.getNonce().send();
    String contractAddress = ContractUtils.generateContractAddress(credentials.getAddress(), nonce);

    EthGetCode code =
        wallet.getZksync().ethGetCode(contractAddress, DefaultBlockParameterName.PENDING).send();

    assertResponse(code);
    assertEquals("0x", code.getCode());

    String constructor =
        ConstructorContract.encodeConstructor(
            BigInteger.valueOf(42), BigInteger.valueOf(43), false);
    TransactionReceipt receipt =
        wallet
            .deploy(
                Numeric.hexStringToByteArray(ConstructorContract.BINARY),
                Numeric.hexStringToByteArray(constructor))
            .send();

    assertTrue(receipt.isStatusOK());

    EthGetCode codeDeployed =
        wallet.getZksync().ethGetCode(contractAddress, DefaultBlockParameterName.PENDING).send();

    assertResponse(codeDeployed);
    assertNotEquals("0x", codeDeployed.getCode());

    ConstructorContract contract =
        ConstructorContract.load(
            contractAddress,
            wallet.getZksync(),
            new ReadonlyTransactionManager(wallet.getZksync(), wallet.getSigner().getAddress()),
            new DefaultGasProvider());

    BigInteger after = contract.get().send();
    assertEquals(BigInteger.valueOf(42).multiply(BigInteger.valueOf(43)), after);
  }

  @Test
  public void testExecute() throws Exception {
    TransactionReceipt deployed =
        wallet.deploy(Numeric.hexStringToByteArray(CounterContract.BINARY)).send();

    assertTrue(deployed.isStatusOK());
    String contractAddress = deployed.getContractAddress();

    TransactionManager transactionManager =
        new ZkSyncTransactionManager(
            wallet.getZksync(), wallet.getSigner(), wallet.getFeeProvider());
    CounterContract contract =
        CounterContract.load(
            contractAddress, wallet.getZksync(), transactionManager, new DefaultGasProvider());

    BigInteger before = contract.get().send();
    assertEquals(BigInteger.ZERO, before);

    TransactionReceipt receipt =
        wallet.execute(contractAddress, CounterContract.encodeIncrement(BigInteger.TEN)).send();
    assertTrue(receipt.isStatusOK());

    BigInteger after = contract.get().send();
    assertEquals(BigInteger.TEN, after);
  }

  @Test
  public void testSendMessageL2ToL1() throws Exception {
    String someMessage = "Some message from ZkSync2j client";
    byte[] messageBytes = someMessage.getBytes(StandardCharsets.UTF_8);

    TransactionReceipt receipt = wallet.sendMessageToL1(messageBytes).send();

    TimeUnit.SECONDS.sleep(5);

    ZkTransactionReceipt zkReceipt =
        wallet
            .getZksync()
            .zksGetTransactionReceipt(receipt.getTransactionHash())
            .send()
            .getTransactionReceipt()
            .get();

    L2ToL1MessageProof proof =
        wallet
            .getZksync()
            .zksGetL2ToL1MsgProof(
                zkReceipt.getBlockNumber().intValue(),
                wallet.getSigner().getAddress(),
                Numeric.toHexString(Hash.sha3(messageBytes)),
                null)
            .send()
            .getResult();

    byte[] messageHash =
        Messenger.getHashedMessage(
            new Address(wallet.getSigner().getAddress()),
            messageBytes,
            zkReceipt.getL1BatchTxIndex());

    Assertions.assertTrue(Messenger.verifyMessage(proof, messageHash));
  }

  private void assertResponse(Response<?> response) {
    if (response.hasError()) {
      System.out.println(response.getError().getMessage());
      System.out.println(response.getError().getData());
    } else {
      System.out.println(response.getResult());
    }

    assertFalse(response::hasError);
  }
}
