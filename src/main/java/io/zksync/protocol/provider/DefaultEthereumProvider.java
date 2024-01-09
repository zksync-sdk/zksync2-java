package io.zksync.protocol.provider;

import static io.zksync.utils.ZkSyncAddresses.L2_ETH_TOKEN_ADDRESS;
import static io.zksync.utils.ZkSyncAddresses.MESSENGER_ADDRESS;
import static io.zksync.wrappers.L1Messenger.L1MESSAGESENT_EVENT;

import io.zksync.methods.response.L2toL1Log;
import io.zksync.methods.response.ZkTransactionReceipt;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.L2ToL1MessageProof;
import io.zksync.protocol.core.Token;
import io.zksync.wrappers.ERC20;
import io.zksync.wrappers.IL1Bridge;
import io.zksync.wrappers.IL2Bridge;
import io.zksync.wrappers.ZkSyncContract;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.web3j.abi.EventValues;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.crypto.Hash;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert.Unit;
import org.web3j.utils.Numeric;

@RequiredArgsConstructor
public class DefaultEthereumProvider implements EthereumProvider {

  private static final HashMap<String, BigInteger> GAS_LIMITS;

  private static final BigInteger MAX_APPROVE_AMOUNT =
      BigInteger.valueOf(2).pow(256).subtract(BigInteger.ONE);
  private static final BigInteger DEFAULT_THRESHOLD = BigInteger.valueOf(2).pow(255);
  private static final ContractGasProvider DEFAULT_GAS_PROVIDER =
      new StaticGasProvider(BigInteger.ZERO, BigInteger.ZERO);

  private static final BigInteger L1_TO_L2_GAS_PER_PUBDATA = BigInteger.valueOf(800);

  private final Web3j web3j;
  private final ZkSync zkSync;
  private final TransactionManager transactionManager;
  private final ContractGasProvider gasProvider;
  private final ZkSyncContract contract;
  private final IL1Bridge l1ERC20Bridge;

  public CompletableFuture<BigInteger> getGasPrice() {
    return web3j.ethGasPrice().sendAsync().thenApply(EthGasPrice::getGasPrice);
  }

  @Override
  public CompletableFuture<TransactionReceipt> approveDeposits(
      Token token, Optional<BigInteger> limit) {
    ERC20 tokenContract = ERC20.load(token.getL1Address(), web3j, transactionManager, gasProvider);
    return tokenContract
        .approve(l1ERC20BridgeAddress(), limit.orElse(MAX_APPROVE_AMOUNT))
        .sendAsync();
  }

  @Override
  public CompletableFuture<TransactionReceipt> transfer(Token token, BigInteger amount, String to) {
    if (token.isETH()) {
      Transfer transfer = new Transfer(web3j, transactionManager);
      return transfer.sendFunds(to, new BigDecimal(amount), Unit.WEI).sendAsync();
    } else {
      ERC20 tokenContract =
          ERC20.load(token.getL1Address(), web3j, transactionManager, gasProvider);
      return tokenContract.transfer(to, amount).sendAsync();
    }
  }

  @Override
  public CompletableFuture<BigInteger> getBaseCost(
      BigInteger gasLimit, BigInteger gasPerPubdataByte, @Nullable BigInteger gasPrice) {
    if (gasPrice != null) {
      return contract.l2TransactionBaseCost(gasPrice, gasLimit, gasPerPubdataByte).sendAsync();
    } else {
      return getGasPrice()
          .thenCompose(
              g -> contract.l2TransactionBaseCost(g, gasLimit, gasPerPubdataByte).sendAsync());
    }
  }

  @Override
  public CompletableFuture<TransactionReceipt> requestExecute(
      String contractAddress,
      BigInteger l2Value,
      byte[] calldata,
      BigInteger gasLimit,
      @Nullable byte[][] factoryDeps,
      @Nullable BigInteger operatorTips,
      @Nullable BigInteger gasPrice,
      String refundRecipient) {
    return CompletableFuture.supplyAsync(
        () -> {
          BigInteger gasPriceValue = gasPrice == null ? getGasPrice().join() : gasPrice;
          List<byte[]> factoryDepsList =
              factoryDeps == null ? Collections.emptyList() : Arrays.asList(factoryDeps);
          BigInteger operatorTipsValue = operatorTips == null ? BigInteger.ZERO : operatorTips;
          BigInteger baseCost =
              getBaseCost(gasLimit, L1_TO_L2_GAS_PER_PUBDATA, gasPriceValue).join();
          BigInteger totalValue = l2Value.add(baseCost).add(operatorTipsValue);
          return contract
              .requestL2Transaction(
                  contractAddress,
                  l2Value,
                  calldata,
                  gasLimit,
                  L1_TO_L2_GAS_PER_PUBDATA,
                  factoryDepsList,
                  refundRecipient,
                  totalValue)
              .sendAsync()
              .join();
        });
  }

  @Override
  public CompletableFuture<TransactionReceipt> deposit(
      Token token, BigInteger amount, BigInteger operatorTips, String userAddress) {
    return CompletableFuture.supplyAsync(
        () -> {
          BigInteger baseCost = BigInteger.ZERO;
          if (token.isETH()) {
            BigInteger gasLimit = BigInteger.valueOf(10000000);
            return requestExecute(
                    userAddress,
                    amount,
                    DynamicBytes.DEFAULT.getValue(),
                    gasLimit,
                    null,
                    operatorTips,
                    null,
                    userAddress)
                .join();
          } else {
            BigInteger gasLimit =
                GAS_LIMITS.getOrDefault(token.getL1Address(), BigInteger.valueOf(300000));
            BigInteger totalAmount = operatorTips.add(baseCost);
            return l1ERC20Bridge
                .deposit(
                    userAddress,
                    token.getL1Address(),
                    gasLimit,
                    L1_TO_L2_GAS_PER_PUBDATA,
                    amount,
                    totalAmount)
                .sendAsync()
                .join();
          }
        });
  }

  @Override
  public CompletableFuture<TransactionReceipt> withdraw(
      Token token, BigInteger amount, String userAddress) {
    //        return contract.requestWithdraw(token.getAddress(), amount, userAddress,
    // PriorityQueueType.Deque.getValue(), PriorityOpTree.Full.getValue()).sendAsync();
    throw new UnsupportedOperationException();
  }

  @Override
  public TransactionReceipt finalizeWithdraw(String txHash, int index) throws Exception {
    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    ZkTransactionReceipt receipt =
        zkSync.zksGetTransactionReceipt(txHash).sendAsync().join().getResult();

    int logIndex = getWithdrawalLogIndex(receipt.getLogs(), index);
    Log log = receipt.getLogs().get(logIndex);

    int l2ToL1LogIndex = getWithdrawalL2ToL1LogIndex(receipt.getl2ToL1Logs(), index);
    L2ToL1MessageProof l2ToL1MessageProof =
        zkSync.zksGetL2ToL1LogProof(txHash, l2ToL1LogIndex).sendAsync().join().getResult();

    EventValues eventValues = Contract.staticExtractEventParameters(L1MESSAGESENT_EVENT, log);
    byte[] bytes_data = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();

    BigInteger l1BatchNumber = receipt.getL1BatchNumber();

    List<byte[]> merkle_proof = new ArrayList<>();
    for (int i = 0; i < l2ToL1MessageProof.getProof().size(); i++) {
      merkle_proof.add(Numeric.hexStringToByteArray(l2ToL1MessageProof.getProof().get(i)));
    }
    String sender = log.getTopics().get(1);

    if (sender.equals(L2_ETH_TOKEN_ADDRESS)) {
      return contract
          .finalizeEthWithdrawal(
              l1BatchNumber,
              BigInteger.valueOf(l2ToL1MessageProof.getId()),
              receipt.getL1BatchTxIndex(),
              bytes_data,
              merkle_proof)
          .sendAsync()
          .join();
    }
    IL2Bridge il2Bridge = IL2Bridge.load(sender, web3j, transactionManager, gasProvider);
    IL1Bridge il1Bridge =
        IL1Bridge.load(il2Bridge.l1Bridge().send(), web3j, transactionManager, gasProvider);

    return il1Bridge
        .finalizeWithdrawal(
            l1BatchNumber,
            BigInteger.valueOf(l2ToL1MessageProof.getId()),
            receipt.getL1BatchTxIndex(),
            bytes_data,
            merkle_proof)
        .sendAsync()
        .join();
  }

  public int getWithdrawalLogIndex(List<Log> logs, int index) {
    String topic = "L1MessageSent(address,bytes32,bytes)";
    List<Integer> logIndex = new ArrayList<>();

    for (int i = 0; i < logs.size(); i++) {
      if (logs.get(i).getAddress().equals(MESSENGER_ADDRESS)
          && Arrays.equals(
              logs.get(i).getTopics().get(0).getBytes(), Hash.sha3String(topic).getBytes())) {
        logIndex.add(i);
      }
    }

    return logIndex.get(index);
  }

  public int getWithdrawalL2ToL1LogIndex(List<L2toL1Log> logs, int index) {
    List<Integer> logIndex = new ArrayList<>();

    for (int i = 0; i < logs.size(); i++) {
      if (logs.get(i).getSender().getValue().equals(MESSENGER_ADDRESS)) {
        logIndex.add(i);
      }
    }

    return logIndex.get(index);
  }

  @Override
  public CompletableFuture<Boolean> isDepositApproved(
      Token token, String to, Optional<BigInteger> threshold) {
    ERC20 tokenContract =
        ERC20.load(token.getL1Address(), web3j, transactionManager, DEFAULT_GAS_PROVIDER);
    return tokenContract
        .allowance(to, l1ERC20BridgeAddress())
        .sendAsync()
        .thenApply(allowance -> allowance.compareTo(threshold.orElse(DEFAULT_THRESHOLD)) >= 0);
  }

  @Override
  public String l1ERC20BridgeAddress() {
    return l1ERC20Bridge.getContractAddress();
  }

  static {
    // {
    //    "0x0000000000095413afc295d19edeb1ad7b71c952": 140000,
    //    "0xeb4c2781e4eba804ce9a9803c67d0893436bb27d": 160000,
    //    "0xbbbbca6a901c926f240b89eacb641d8aec7aeafd": 140000,
    //    "0xb64ef51c888972c908cfacf59b47c1afbc0ab8ac": 140000,
    //    "0x1f9840a85d5af5bf1d1762f925bdaddc4201f984": 150000,
    //    "0x9ba00d6856a4edf4665bca2c2309936572473b7e": 270000,
    //    "0x8daebade922df735c38c80c7ebd708af50815faa": 140000,
    //    "0x0d8775f648430679a709e98d2b0cb6250d2887ef": 140000,
    //    "0xdac17f958d2ee523a2206206994597c13d831ec7": 140000,
    //    "0x6de037ef9ad2725eb40118bb1702ebb27e4aeb24": 150000,
    //    "0x056fd409e1d7a124bd7017459dfea2f387b6d5cd": 180000,
    //    "0x0f5d2fb29fb7d3cfee444a200298f468908cc942": 140000,
    //    "0x514910771af9ca656af840dff83e8264ecf986ca": 140000,
    //    "0x1985365e9f78359a9b6ad760e32412f4a445e862": 180000,
    //    "0x2260fac5e5542a773aa44fbcfedf7c193bc2c599": 140000,
    //    "0xe41d2489571d322189246dafa5ebde1f4699f498": 140000,
    //    "0x6b175474e89094c44da98b954eedeac495271d0f": 140000,
    //    "0xaaaebe6fe48e54f431b0c390cfaf0b017d09d42d": 150000,
    //    "0x2b591e99afe9f32eaa6214f7b7629768c40eeb39": 140000,
    //    "0x65ece136b89ebaa72a7f7aa815674946e44ca3f9": 140000,
    //    "0x0000000000085d4780b73119b644ae5ecd22b376": 150000,
    //    "0xdb25f211ab05b1c97d595516f45794528a807ad8": 180000,
    //    "0x408e41876cccdc0f92210600ef50372656052a38": 140000,
    //    "0x15a2b3cfafd696e1c783fe99eed168b78a3a371e": 160000,
    //    "0x38e4adb44ef08f22f5b5b76a8f0c2d0dcbe7dca1": 160000,
    //    "0x3108ccfd96816f9e663baa0e8c5951d229e8c6da": 140000,
    //    "0x56d811088235f11c8920698a204a5010a788f4b3": 240000,
    //    "0x57ab1ec28d129707052df4df418d58a2d46d5f51": 220000,
    //    "0x9f8f72aa9304c8b593d555f12ef6589cc3a579a2": 140000,
    //    "0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48": 150000,
    //    "0xc011a73ee8576fb46f5e1c5751ca3b9fe0af2a6f": 200000,
    //    "0x744d70fdbe2ba4cf95131626614a1763df805b9e": 230000,
    //    "0x0bc529c00c6401aef6d220be8c6ea1667f6ad93e": 140000,
    //    "0x4c7065bca76fe44afb0d16c2441b1e6e163354e2": 250000,
    //    "0xdd974d5c2e2928dea5f71b9825b8b646686bd200": 140000,
    //    "0x80fb784b7ed66730e8b1dbd9820afd29931aab03": 140000,
    //    "0xd56dac73a4d6766464b38ec6d91eb45ce7457c44": 140000,
    //    "0x4fabb145d64652a948d72533023f6e7a623c7c53": 150000,
    //    "0x38a2fdc11f526ddd5a607c1f251c065f40fbf2f7": 140000,
    //    "0x7dd9c5cba05e151c895fde1cf355c9a1d5da6429": 140000
    // }
    GAS_LIMITS =
        new HashMap<String, BigInteger>() {
          {
            put("0x0000000000095413afc295d19edeb1ad7b71c952", BigInteger.valueOf(140000));
            put("0xeb4c2781e4eba804ce9a9803c67d0893436bb27d", BigInteger.valueOf(160000));
            put("0xbbbbca6a901c926f240b89eacb641d8aec7aeafd", BigInteger.valueOf(140000));
            put("0xb64ef51c888972c908cfacf59b47c1afbc0ab8ac", BigInteger.valueOf(140000));
            put("0x1f9840a85d5af5bf1d1762f925bdaddc4201f984", BigInteger.valueOf(150000));
            put("0x9ba00d6856a4edf4665bca2c2309936572473b7e", BigInteger.valueOf(270000));
            put("0x8daebade922df735c38c80c7ebd708af50815faa", BigInteger.valueOf(140000));
            put("0x0d8775f648430679a709e98d2b0cb6250d2887ef", BigInteger.valueOf(140000));
            put("0xdac17f958d2ee523a2206206994597c13d831ec7", BigInteger.valueOf(140000));
            put("0x6de037ef9ad2725eb40118bb1702ebb27e4aeb24", BigInteger.valueOf(150000));
            put("0x056fd409e1d7a124bd7017459dfea2f387b6d5cd", BigInteger.valueOf(180000));
            put("0x0f5d2fb29fb7d3cfee444a200298f468908cc942", BigInteger.valueOf(140000));
            put("0x514910771af9ca656af840dff83e8264ecf986ca", BigInteger.valueOf(140000));
            put("0x1985365e9f78359a9b6ad760e32412f4a445e862", BigInteger.valueOf(180000));
            put("0x2260fac5e5542a773aa44fbcfedf7c193bc2c599", BigInteger.valueOf(140000));
            put("0xe41d2489571d322189246dafa5ebde1f4699f498", BigInteger.valueOf(140000));
            put("0x6b175474e89094c44da98b954eedeac495271d0f", BigInteger.valueOf(140000));
            put("0xaaaebe6fe48e54f431b0c390cfaf0b017d09d42d", BigInteger.valueOf(150000));
            put("0x2b591e99afe9f32eaa6214f7b7629768c40eeb39", BigInteger.valueOf(140000));
            put("0x65ece136b89ebaa72a7f7aa815674946e44ca3f9", BigInteger.valueOf(140000));
            put("0x0000000000085d4780b73119b644ae5ecd22b376", BigInteger.valueOf(150000));
            put("0xdb25f211ab05b1c97d595516f45794528a807ad8", BigInteger.valueOf(180000));
            put("0x408e41876cccdc0f92210600ef50372656052a38", BigInteger.valueOf(140000));
            put("0x15a2b3cfafd696e1c783fe99eed168b78a3a371e", BigInteger.valueOf(160000));
            put("0x38e4adb44ef08f22f5b5b76a8f0c2d0dcbe7dca1", BigInteger.valueOf(160000));
            put("0x3108ccfd96816f9e663baa0e8c5951d229e8c6da", BigInteger.valueOf(140000));
            put("0x56d811088235f11c8920698a204a5010a788f4b3", BigInteger.valueOf(240000));
            put("0x57ab1ec28d129707052df4df418d58a2d46d5f51", BigInteger.valueOf(220000));
            put("0x9f8f72aa9304c8b593d555f12ef6589cc3a579a2", BigInteger.valueOf(140000));
            put("0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48", BigInteger.valueOf(150000));
            put("0xc011a73ee8576fb46f5e1c5751ca3b9fe0af2a6f", BigInteger.valueOf(200000));
            put("0x744d70fdbe2ba4cf95131626614a1763df805b9e", BigInteger.valueOf(230000));
            put("0x0bc529c00c6401aef6d220be8c6ea1667f6ad93e", BigInteger.valueOf(140000));
            put("0x4c7065bca76fe44afb0d16c2441b1e6e163354e2", BigInteger.valueOf(250000));
            put("0xdd974d5c2e2928dea5f71b9825b8b646686bd200", BigInteger.valueOf(140000));
            put("0x80fb784b7ed66730e8b1dbd9820afd29931aab03", BigInteger.valueOf(140000));
            put("0xd56dac73a4d6766464b38ec6d91eb45ce7457c44", BigInteger.valueOf(140000));
            put("0x4fabb145d64652a948d72533023f6e7a623c7c53", BigInteger.valueOf(150000));
            put("0x38a2fdc11f526ddd5a607c1f251c065f40fbf2f7", BigInteger.valueOf(140000));
            put("0x7dd9c5cba05e151c895fde1cf355c9a1d5da6429", BigInteger.valueOf(140000));
          }
        };
  }
}
