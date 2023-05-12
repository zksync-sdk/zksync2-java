package io.zksync.crypto.signer;

import io.zksync.ZkSyncWallet;
import io.zksync.crypto.eip712.Eip712Domain;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.protocol.provider.EthereumProvider;
import io.zksync.transaction.type.Transaction712;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Disabled
public class SelfTransferTest {

    private static Credentials credentials;
    private static PrivateKeyEthSigner key;
    private static Eip712Domain domain;
    private static Transaction712 message;
    private static ZkSync zksync;
    private static Web3j web3j;
    private static ZkSyncWallet wallet;
    private static BigInteger chainId;


    @BeforeAll
    public static void setUp() throws IOException {

        final String privateKey = "PRIVATE_KEY";
        zksync = ZkSync.build(new HttpService("https://zksync2-testnet.zksync.dev"));
        credentials = Credentials.create(privateKey);
        key = new PrivateKeyEthSigner(credentials, 280L);
        wallet = new ZkSyncWallet(zksync, key, Token.ETH);
        web3j = Web3j.build(new HttpService("https://goerli.infura.io/v3/API_KEY"));
        chainId = web3j.ethChainId().send().getChainId();
        domain = Eip712Domain.defaultDomain(280L);
    }

    @Test
    public void SelfTransfer() throws IOException {
        TransactionManager manager = new RawTransactionManager(web3j, credentials, chainId.longValue());
        BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
        ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, BigInteger.valueOf(300_000L));
        TransactionReceipt receipt = EthereumProvider
                .load(wallet.getZksync(), web3j, manager, gasProvider).join()
                .transfer(Token.ETH, Convert.toWei("0.117649", Convert.Unit.ETHER).toBigInteger(), credentials.getAddress()).join();

        System.out.println(receipt);

    }


    @Test
    public void withdrawTest() throws Exception {
        TransactionReceipt receipt = wallet.withdraw(credentials.getAddress(), Convert.toWei("0.117649", Convert.Unit.ETHER).toBigInteger()).send();
        System.out.println(receipt);
    }


    @Test
    public void deployTestContract() throws Exception {
        String bytecode = "BYTE_CODE";
        TransactionReceipt receipt = wallet.deploy(Numeric.hexStringToByteArray(bytecode)).send();
        System.out.println(receipt);

    }

    @Test
    public void deployTestContractWithConstructor() throws Exception {
        String name = "Test";
        String symbol = "COIN";
        BigInteger amount = new BigInteger("666234").multiply(new BigInteger("10").pow(18));

        List<Type> inputParameter = new ArrayList<>();
        inputParameter.add(new Utf8String(name));
        inputParameter.add(new Utf8String(symbol));
        inputParameter.add(new Uint256(amount));

        String bytecode = "CONTRACT_BYTECODE";
        String calldata = FunctionEncoder.encodeConstructor(inputParameter);
        TransactionReceipt receipt = wallet.deploy(Numeric.hexStringToByteArray(bytecode), Numeric.hexStringToByteArray(calldata)).send();

        System.out.println(receipt);
    }


    @Test
    public void executeContract() throws Exception {
        String contractAddress = "CONTRACT_ADDRESS";
        Uint256 amount = new Uint256(new BigInteger("1234").multiply(new BigInteger(String.valueOf(10)).pow(18)));
        String address = credentials.getAddress();
        Function contractFunction = new Function("mint", Arrays.asList(new Address(address), amount), Collections.emptyList());

        TransactionReceipt receipt = wallet.execute("0x2da10A1e27bF85cEdD8FFb1AbBe97e53391C0295", contractFunction, null).send();
        System.out.println(receipt);
    }


    @Test
    public void callTest() throws Exception {
        String contractAddress = "0x0d7a0d1097072c9fddb6f934159cfa8479e0d1b1";

        Function contractFunction = new Function("testCallString",
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Utf8String>() {
                }));

        org.web3j.protocol.core.methods.request.Transaction call = org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction(
                null,
                contractAddress,
                FunctionEncoder.encode(contractFunction)
        );

        EthCall ethCall = wallet.getZksync().ethCall(call, ZkBlockParameterName.COMMITTED).send();
        String fooName = (String) FunctionReturnDecoder.decode(ethCall.getValue(), contractFunction.getOutputParameters()).get(0).getValue();
        System.out.println(fooName);
        System.out.println(ethCall.getValue());
    }


    @Test
    public void testDeposit() throws IOException {
        TransactionManager manager = new RawTransactionManager(web3j, credentials, chainId.longValue());
        BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
        ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, BigInteger.valueOf(300_000L));
        TransactionReceipt receipt = EthereumProvider
                .load(wallet.getZksync(), web3j, manager, gasProvider).join()
                .deposit(Token.ETH, Convert.toWei("0.001", Convert.Unit.ETHER).toBigInteger(), BigInteger.ZERO, credentials.getAddress()).join();

        System.out.println(receipt);
    }


}
