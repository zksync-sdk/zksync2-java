package io.zksync.protocol;

import io.zksync.helper.CounterContract;
import io.zksync.helper.RequestTester;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import org.web3j.protocol.http.HttpService;

class ZkSyncRequestTest extends RequestTester {

  private ZkSync zkSync;

  @Override
  protected void initWeb3Client(HttpService httpService) {
    zkSync = ZkSync.build(httpService);
  }

  @Test
  void zksEstimateFee() throws Exception {
    io.zksync.methods.request.Transaction estimate =
        io.zksync.methods.request.Transaction.createFunctionCallTransaction(
            "0x7e5f4552091a69125d5dfcb7b8c2659029395bdf",
            "0x7e5f4552091a69125d5dfcb7b8c2659029395bdf",
            BigInteger.ZERO,
            BigInteger.ZERO,
            "0x");

    zkSync.zksEstimateFee(estimate).send();

    verifyResult(
        "{\"jsonrpc\":\"2.0\",\"method\":\"zks_estimateFee\",\"params\":[{\"from\":\"0x7e5f4552091a69125d5dfcb7b8c2659029395bdf\",\"to\":\"0x7e5f4552091a69125d5dfcb7b8c2659029395bdf\",\"gas\":\"0x0\",\"gasPrice\":\"0x0\",\"data\":\"0x\",\"transactionType\":\"0x71\",\"eip712Meta\":{\"gasPerPubdata\":\"0x27100\"}}],\"id\":1}");
  }

  @Test
  void ethEstimateFee_DeployContract() throws Exception {
    io.zksync.methods.request.Transaction estimate =
        io.zksync.methods.request.Transaction.create2ContractTransaction(
            "0x7e5f4552091a69125d5dfcb7b8c2659029395bdf",
            BigInteger.ZERO,
            BigInteger.ZERO,
            CounterContract.BINARY);

    zkSync.zksEstimateFee(estimate).send();

    verifyResult(
        "{\"jsonrpc\":\"2.0\",\"method\":\"zks_estimateFee\",\"params\":[{\"from\":\"0x7e5f4552091a69125d5dfcb7b8c2659029395bdf\",\"to\":\"0x0000000000000000000000000000000000008006\",\"gas\":\"0x0\",\"gasPrice\":\"0x0\",\"data\":\"0x3cda33510000000000000000000000000000000000000000000000000000000000000000010000517112c421df08d7b49e4dc1312f4ee62268ee4f5683b11d9e2d33525a00000000000000000000000000000000000000000000000000000000000000600000000000000000000000000000000000000000000000000000000000000000\",\"transactionType\":\"0x71\",\"eip712Meta\":{\"gasPerPubdata\":\"0x27100\",\"factoryDeps\":[[0,2,0,0,0,0,0,2,0,1,0,0,0,1,3,85,0,0,0,96,1,16,2,112,0,0,0,65,0,16,1,157,0,0,0,1,1,32,1,143,0,0,0,0,1,16,0,76,0,0,0,8,0,0,193,61,0,253,0,24,0,0,4,15,0,253,0,9,0,0,4,15,0,0,0,128,1,0,0,57,0,0,0,64,2,0,0,57,0,0,0,0,0,18,4,53,0,0,0,0,1,0,4,22,0,0,0,0,1,16,0,76,0,0,0,22,0,0,193,61,0,0,0,32,1,0,0,57,0,0,1,0,2,0,0,57,0,0,0,0,0,18,4,57,0,0,1,32,1,0,0,57,0,0,0,0,0,1,4,57,0,0,0,66,1,0,0,65,0,0,0,254,0,1,4,46,0,0,0,0,1,0,0,25,0,0,0,255,0,1,4,48,0,4,0,0,0,0,0,2,0,0,0,0,1,0,4,16,0,0,128,2,2,16,0,140,0,0,0,51,0,0,97,61,0,0,0,0,2,0,4,17,0,0,128,1,2,32,0,140,0,0,0,51,0,0,97,61,0,0,0,67,2,0,0,65,0,0,0,0,0,32,4,57,0,0,0,4,2,0,0,57,0,0,0,0,0,18,4,57,0,0,0,68,1,0,0,65,0,0,128,2,2,0,0,57,0,0,0,0,3,0,4,21,0,0,0,4,3,48,0,138,0,0,0,32,3,48,0,201,0,253,0,224,0,0,4,15,0,0,0,255,1,0,0,57,0,0,0,3,1,16,2,79,0,0,0,0,1,16,0,76,0,0,0,86,0,0,97,61,0,0,0,4,1,0,3,95,0,0,0,0,1,1,4,59,0,0,0,0,1,16,0,76,0,0,0,51,0,0,193,61,0,0,0,0,1,0,0,25,0,0,0,254,0,1,4,46,0,0,0,128,1,0,0,57,0,0,0,64,6,0,0,57,0,0,0,0,0,22,4,53,0,0,0,0,1,0,0,49,0,0,0,3,2,16,0,140,0,0,0,84,0,0,161,61,0,0,0,1,2,0,3,103,0,0,0,0,3,2,4,59,0,0,0,224,3,48,2,112,0,0,0,69,4,48,0,156,0,0,0,108,0,0,97,61,0,0,0,70,2,48,0,156,0,0,0,88,0,0,97,61,0,0,0,71,2,48,0,156,0,0,0,84,0,0,193,61,0,0,0,0,2,0,4,22,0,0,0,0,2,32,0,76,0,0,0,128,0,0,193,61,0,0,0,4,1,16,0,138,0,0,0,72,2,0,0,65,0,0,0,31,3,16,0,140,0,0,0,0,3,0,0,25,0,0,0,0,3,2,32,25,0,0,0,72,1,16,1,151,0,0,0,0,4,16,0,76,0,0,0,0,2,0,128,25,0,0,0,72,1,16,0,156,0,0,0,0,1,3,0,25,0,0,0,0,1,2,96,25,0,0,0,0,1,16,0,76,0,0,0,142,0,0,193,61,0,0,0,0,1,0,0,25,0,0,0,255,0,1,4,48,0,0,0,0,1,0,0,25,0,0,0,255,0,1,4,48,0,0,0,0,1,0,0,25,0,0,0,255,0,1,4,48,0,0,0,0,2,0,4,22,0,0,0,0,2,32,0,76,0,0,0,126,0,0,193,61,0,0,0,4,1,16,0,138,0,0,0,1,2,0,0,138,0,0,0,72,3,0,0,65,0,0,0,0,2,33,0,75,0,0,0,0,2,0,0,25,0,0,0,0,2,3,32,25,0,0,0,72,1,16,1,151,0,0,0,72,4,16,0,156,0,0,0,0,3,0,128,25,0,0,0,72,1,16,1,103,0,0,0,72,1,16,0,156,0,0,0,0,1,2,0,25,0,0,0,0,1,3,96,25,0,0,0,0,1,16,0,76,0,0,0,132,0,0,193,61,0,0,0,0,1,0,0,25,0,0,0,255,0,1,4,48,0,0,0,0,3,0,4,22,0,0,0,0,3,48,0,76,0,0,0,130,0,0,193,61,0,0,0,4,1,16,0,138,0,0,0,72,3,0,0,65,0,0,0,63,4,16,0,140,0,0,0,0,4,0,0,25,0,0,0,0,4,3,32,25,0,0,0,72,1,16,1,151,0,0,0,0,5,16,0,76,0,0,0,0,3,0,128,25,0,0,0,72,1,16,0,156,0,0,0,0,1,4,0,25,0,0,0,0,1,3,96,25,0,0,0,0,1,16,0,76,0,0,0,162,0,0,193,61,0,0,0,0,1,0,0,25,0,0,0,255,0,1,4,48,0,0,0,0,1,0,0,25,0,0,0,255,0,1,4,48,0,0,0,0,1,0,0,25,0,0,0,255,0,1,4,48,0,0,0,0,1,0,0,25,0,0,0,255,0,1,4,48,0,0,0,0,1,0,0,25,0,2,0,0,0,6,0,29,0,253,0,251,0,0,4,15,0,0,0,2,2,0,0,41,0,0,0,0,2,2,4,51,0,0,0,0,0,18,4,53,0,0,0,64,1,32,2,16,0,0,0,73,1,16,1,151,0,0,0,76,1,16,1,199,0,0,0,254,0,1,4,46,0,2,0,0,0,6,0,29,0,0,0,0,1,0,0,25,0,253,0,251,0,0,4,15,0,0,0,1,2,0,3,103,0,0,0,4,2,32,3,112,0,0,0,0,2,2,4,59,0,0,0,0,1,18,0,25,0,0,0,0,2,33,0,75,0,0,0,0,2,0,0,25,0,0,0,1,2,0,64,57,0,0,0,1,2,32,1,143,0,0,0,0,2,32,0,76,0,0,0,190,0,0,97,61,0,0,0,74,1,0,0,65,0,0,0,0,0,16,4,53,0,0,0,17,1,0,0,57,0,0,0,4,2,0,0,57,0,0,0,0,0,18,4,53,0,0,0,75,1,0,0,65,0,0,0,255,0,1,4,48,0,0,0,36,1,32,3,112,0,0,0,0,2,1,4,59,0,0,0,0,1,32,0,76,0,0,0,0,1,0,0,25,0,0,0,1,1,0,192,57,0,0,0,0,1,18,0,75,0,0,0,197,0,0,193,61,0,1,0,0,0,2,0,29,0,2,0,0,0,6,0,29,0,0,0,0,1,0,0,25,0,253,0,251,0,0,4,15,0,0,0,1,2,0,3,103,0,0,0,4,2,32,3,112,0,0,0,0,2,2,4,59,0,0,0,0,1,18,0,25,0,0,0,0,2,33,0,75,0,0,0,0,2,0,0,25,0,0,0,1,2,0,64,57,0,0,0,1,2,32,1,143,0,0,0,0,2,32,0,76,0,0,0,199,0,0,97,61,0,0,0,74,1,0,0,65,0,0,0,0,0,16,4,53,0,0,0,17,1,0,0,57,0,0,0,4,2,0,0,57,0,0,0,0,0,18,4,53,0,0,0,75,1,0,0,65,0,0,0,255,0,1,4,48,0,0,0,0,2,0,0,25,0,253,0,249,0,0,4,15,0,0,0,2,1,0,0,41,0,0,0,0,1,1,4,51,0,0,0,64,1,16,2,16,0,0,0,73,1,16,1,151,0,0,0,254,0,1,4,46,0,0,0,0,1,0,0,25,0,0,0,255,0,1,4,48,0,0,0,0,2,0,0,25,0,253,0,249,0,0,4,15,0,0,0,2,1,0,0,41,0,0,0,0,1,1,4,51,0,0,0,1,2,0,0,41,0,0,0,0,2,32,0,76,0,0,0,209,0,0,193,61,0,0,0,64,1,16,2,16,0,0,0,73,1,16,1,151,0,0,0,254,0,1,4,46,0,0,0,68,2,16,0,57,0,0,0,77,3,0,0,65,0,0,0,0,0,50,4,53,0,0,0,36,2,16,0,57,0,0,0,26,3,0,0,57,0,0,0,0,0,50,4,53,0,0,0,78,2,0,0,65,0,0,0,0,0,33,4,53,0,0,0,4,2,16,0,57,0,0,0,32,3,0,0,57,0,0,0,0,0,50,4,53,0,0,0,64,1,16,2,16,0,0,0,73,1,16,1,151,0,0,0,79,1,16,1,199,0,0,0,255,0,1,4,48,0,2,0,0,0,0,0,2,0,2,0,0,0,3,0,29,0,0,0,32,3,48,0,57,0,1,0,0,0,3,0,29,0,0,0,239,0,33,4,35,0,0,0,2,3,0,0,41,0,0,0,32,2,48,1,26,0,0,0,0,2,1,3,85,0,0,0,72,1,0,0,65,0,0,0,1,2,0,0,41,0,0,0,32,2,32,1,26,0,0,0,0,2,18,1,189,0,0,0,0,1,3,0,25,0,0,0,2,0,0,0,5,0,0,0,0,0,1,4,45,0,0,0,2,3,0,0,41,0,0,0,32,2,48,1,26,0,0,0,0,2,1,3,85,0,0,0,80,1,0,0,65,0,0,0,1,2,0,0,41,0,0,0,32,2,32,1,26,0,0,0,0,2,18,1,141,0,0,0,0,1,3,0,25,0,0,0,2,0,0,0,5,0,0,0,0,0,1,4,45,0,0,0,0,0,18,4,27,0,0,0,0,0,1,4,45,0,0,0,0,1,1,4,26,0,0,0,0,0,1,4,45,0,0,0,253,0,0,4,50,0,0,0,254,0,1,4,46,0,0,0,255,0,1,4,48,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,255,255,255,255,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,64,0,0,1,0,0,0,0,0,0,0,0,0,24,6,170,24,150,187,242,101,104,232,132,167,55,75,65,224,2,80,9,98,202,186,106,21,2,58,141,144,232,80,139,131,2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,36,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,54,218,214,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,109,76,230,60,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,124,245,218,176,128,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,255,255,255,255,0,0,0,0,0,0,0,0,78,72,123,113,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,36,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,32,0,0,0,0,0,0,0,0,0,0,0,0,84,104,105,115,32,109,101,116,104,111,100,32,97,108,119,97,121,115,32,114,101,118,101,114,116,115,0,0,0,0,0,0,8,195,121,160,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,100,0,0,0,0,0,0,0,0,0,0,0,0,127,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255]]}}],\"id\":1}");
  }

  @Test
  void zksMainContract() throws Exception {
    zkSync.zksMainContract().send();

    verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"zks_getMainContract\",\"params\":[],\"id\":1}");
  }

  @Test
  void zksGetConfirmedTokens() throws Exception {
    zkSync.zksGetConfirmedTokens(0, (short) 10).send();

    verifyResult(
        "{\"jsonrpc\":\"2.0\",\"method\":\"zks_getConfirmedTokens\",\"params\":[0,10],\"id\":1}");
  }

  @Test
  void zksGetTokenPrice() throws Exception {
    zkSync.zksGetTokenPrice("0x0000000000000000000000000000000000000000").send();

    verifyResult(
        "{\"jsonrpc\":\"2.0\",\"method\":\"zks_getTokenPrice\",\"params\":[\"0x0000000000000000000000000000000000000000\"],\"id\":1}");
  }

  @Test
  void zksL1ChainId() throws Exception {
    zkSync.zksL1ChainId().send();

    verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"zks_L1ChainId\",\"params\":[],\"id\":1}");
  }

  @Test
  void zksGetContractDebugInfo() {}

  @Test
  void zksGetTransactionTrace() {}

  @Test
  void zksGetAllAccountBalances() throws Exception {
    zkSync.zksGetAllAccountBalances("0x7e5f4552091a69125d5dfcb7b8c2659029395bdf").send();

    verifyResult(
        "{\"jsonrpc\":\"2.0\",\"method\":\"zks_getAllAccountBalances\",\"params\":[\"0x7e5f4552091a69125d5dfcb7b8c2659029395bdf\"],\"id\":1}");
  }

  @Test
  void zksGetBridgeContracts() throws Exception {
    zkSync.zksGetBridgeContracts().send();

    verifyResult(
        "{\"jsonrpc\":\"2.0\",\"method\":\"zks_getBridgeContracts\",\"params\":[],\"id\":1}");
  }

  @Test
  void zksGetL2ToL1MsgProof() {}

  @Test
  void ethEstimateGas() throws Exception {
    io.zksync.methods.request.Transaction estimate =
        io.zksync.methods.request.Transaction.createFunctionCallTransaction(
            "0x7e5f4552091a69125d5dfcb7b8c2659029395bdf",
            "0x7e5f4552091a69125d5dfcb7b8c2659029395bdf",
            BigInteger.ZERO,
            BigInteger.ZERO,
            "0x");

    zkSync.ethEstimateGas(estimate).send();

    verifyResult(
        "{\"jsonrpc\":\"2.0\",\"method\":\"eth_estimateGas\",\"params\":[{\"from\":\"0x7e5f4552091a69125d5dfcb7b8c2659029395bdf\",\"to\":\"0x7e5f4552091a69125d5dfcb7b8c2659029395bdf\",\"gas\":\"0x0\",\"gasPrice\":\"0x0\",\"data\":\"0x\",\"transactionType\":\"0x71\",\"eip712Meta\":{\"gasPerPubdata\":\"0x27100\"}}],\"id\":1}");
  }

  @Test
  void ethEstimateGas_DeployContract() throws Exception {
    io.zksync.methods.request.Transaction estimate =
        io.zksync.methods.request.Transaction.create2ContractTransaction(
            "0x7e5f4552091a69125d5dfcb7b8c2659029395bdf",
            BigInteger.ZERO,
            BigInteger.ZERO,
            CounterContract.BINARY);

    zkSync.ethEstimateGas(estimate).send();

    verifyResult(
        "{\"jsonrpc\":\"2.0\",\"method\":\"eth_estimateGas\",\"params\":[{\"from\":\"0x7e5f4552091a69125d5dfcb7b8c2659029395bdf\",\"to\":\"0x0000000000000000000000000000000000008006\",\"gas\":\"0x0\",\"gasPrice\":\"0x0\",\"data\":\"0x3cda33510000000000000000000000000000000000000000000000000000000000000000010000517112c421df08d7b49e4dc1312f4ee62268ee4f5683b11d9e2d33525a00000000000000000000000000000000000000000000000000000000000000600000000000000000000000000000000000000000000000000000000000000000\",\"transactionType\":\"0x71\",\"eip712Meta\":{\"gasPerPubdata\":\"0x27100\",\"factoryDeps\":[[0,2,0,0,0,0,0,2,0,1,0,0,0,1,3,85,0,0,0,96,1,16,2,112,0,0,0,65,0,16,1,157,0,0,0,1,1,32,1,143,0,0,0,0,1,16,0,76,0,0,0,8,0,0,193,61,0,253,0,24,0,0,4,15,0,253,0,9,0,0,4,15,0,0,0,128,1,0,0,57,0,0,0,64,2,0,0,57,0,0,0,0,0,18,4,53,0,0,0,0,1,0,4,22,0,0,0,0,1,16,0,76,0,0,0,22,0,0,193,61,0,0,0,32,1,0,0,57,0,0,1,0,2,0,0,57,0,0,0,0,0,18,4,57,0,0,1,32,1,0,0,57,0,0,0,0,0,1,4,57,0,0,0,66,1,0,0,65,0,0,0,254,0,1,4,46,0,0,0,0,1,0,0,25,0,0,0,255,0,1,4,48,0,4,0,0,0,0,0,2,0,0,0,0,1,0,4,16,0,0,128,2,2,16,0,140,0,0,0,51,0,0,97,61,0,0,0,0,2,0,4,17,0,0,128,1,2,32,0,140,0,0,0,51,0,0,97,61,0,0,0,67,2,0,0,65,0,0,0,0,0,32,4,57,0,0,0,4,2,0,0,57,0,0,0,0,0,18,4,57,0,0,0,68,1,0,0,65,0,0,128,2,2,0,0,57,0,0,0,0,3,0,4,21,0,0,0,4,3,48,0,138,0,0,0,32,3,48,0,201,0,253,0,224,0,0,4,15,0,0,0,255,1,0,0,57,0,0,0,3,1,16,2,79,0,0,0,0,1,16,0,76,0,0,0,86,0,0,97,61,0,0,0,4,1,0,3,95,0,0,0,0,1,1,4,59,0,0,0,0,1,16,0,76,0,0,0,51,0,0,193,61,0,0,0,0,1,0,0,25,0,0,0,254,0,1,4,46,0,0,0,128,1,0,0,57,0,0,0,64,6,0,0,57,0,0,0,0,0,22,4,53,0,0,0,0,1,0,0,49,0,0,0,3,2,16,0,140,0,0,0,84,0,0,161,61,0,0,0,1,2,0,3,103,0,0,0,0,3,2,4,59,0,0,0,224,3,48,2,112,0,0,0,69,4,48,0,156,0,0,0,108,0,0,97,61,0,0,0,70,2,48,0,156,0,0,0,88,0,0,97,61,0,0,0,71,2,48,0,156,0,0,0,84,0,0,193,61,0,0,0,0,2,0,4,22,0,0,0,0,2,32,0,76,0,0,0,128,0,0,193,61,0,0,0,4,1,16,0,138,0,0,0,72,2,0,0,65,0,0,0,31,3,16,0,140,0,0,0,0,3,0,0,25,0,0,0,0,3,2,32,25,0,0,0,72,1,16,1,151,0,0,0,0,4,16,0,76,0,0,0,0,2,0,128,25,0,0,0,72,1,16,0,156,0,0,0,0,1,3,0,25,0,0,0,0,1,2,96,25,0,0,0,0,1,16,0,76,0,0,0,142,0,0,193,61,0,0,0,0,1,0,0,25,0,0,0,255,0,1,4,48,0,0,0,0,1,0,0,25,0,0,0,255,0,1,4,48,0,0,0,0,1,0,0,25,0,0,0,255,0,1,4,48,0,0,0,0,2,0,4,22,0,0,0,0,2,32,0,76,0,0,0,126,0,0,193,61,0,0,0,4,1,16,0,138,0,0,0,1,2,0,0,138,0,0,0,72,3,0,0,65,0,0,0,0,2,33,0,75,0,0,0,0,2,0,0,25,0,0,0,0,2,3,32,25,0,0,0,72,1,16,1,151,0,0,0,72,4,16,0,156,0,0,0,0,3,0,128,25,0,0,0,72,1,16,1,103,0,0,0,72,1,16,0,156,0,0,0,0,1,2,0,25,0,0,0,0,1,3,96,25,0,0,0,0,1,16,0,76,0,0,0,132,0,0,193,61,0,0,0,0,1,0,0,25,0,0,0,255,0,1,4,48,0,0,0,0,3,0,4,22,0,0,0,0,3,48,0,76,0,0,0,130,0,0,193,61,0,0,0,4,1,16,0,138,0,0,0,72,3,0,0,65,0,0,0,63,4,16,0,140,0,0,0,0,4,0,0,25,0,0,0,0,4,3,32,25,0,0,0,72,1,16,1,151,0,0,0,0,5,16,0,76,0,0,0,0,3,0,128,25,0,0,0,72,1,16,0,156,0,0,0,0,1,4,0,25,0,0,0,0,1,3,96,25,0,0,0,0,1,16,0,76,0,0,0,162,0,0,193,61,0,0,0,0,1,0,0,25,0,0,0,255,0,1,4,48,0,0,0,0,1,0,0,25,0,0,0,255,0,1,4,48,0,0,0,0,1,0,0,25,0,0,0,255,0,1,4,48,0,0,0,0,1,0,0,25,0,0,0,255,0,1,4,48,0,0,0,0,1,0,0,25,0,2,0,0,0,6,0,29,0,253,0,251,0,0,4,15,0,0,0,2,2,0,0,41,0,0,0,0,2,2,4,51,0,0,0,0,0,18,4,53,0,0,0,64,1,32,2,16,0,0,0,73,1,16,1,151,0,0,0,76,1,16,1,199,0,0,0,254,0,1,4,46,0,2,0,0,0,6,0,29,0,0,0,0,1,0,0,25,0,253,0,251,0,0,4,15,0,0,0,1,2,0,3,103,0,0,0,4,2,32,3,112,0,0,0,0,2,2,4,59,0,0,0,0,1,18,0,25,0,0,0,0,2,33,0,75,0,0,0,0,2,0,0,25,0,0,0,1,2,0,64,57,0,0,0,1,2,32,1,143,0,0,0,0,2,32,0,76,0,0,0,190,0,0,97,61,0,0,0,74,1,0,0,65,0,0,0,0,0,16,4,53,0,0,0,17,1,0,0,57,0,0,0,4,2,0,0,57,0,0,0,0,0,18,4,53,0,0,0,75,1,0,0,65,0,0,0,255,0,1,4,48,0,0,0,36,1,32,3,112,0,0,0,0,2,1,4,59,0,0,0,0,1,32,0,76,0,0,0,0,1,0,0,25,0,0,0,1,1,0,192,57,0,0,0,0,1,18,0,75,0,0,0,197,0,0,193,61,0,1,0,0,0,2,0,29,0,2,0,0,0,6,0,29,0,0,0,0,1,0,0,25,0,253,0,251,0,0,4,15,0,0,0,1,2,0,3,103,0,0,0,4,2,32,3,112,0,0,0,0,2,2,4,59,0,0,0,0,1,18,0,25,0,0,0,0,2,33,0,75,0,0,0,0,2,0,0,25,0,0,0,1,2,0,64,57,0,0,0,1,2,32,1,143,0,0,0,0,2,32,0,76,0,0,0,199,0,0,97,61,0,0,0,74,1,0,0,65,0,0,0,0,0,16,4,53,0,0,0,17,1,0,0,57,0,0,0,4,2,0,0,57,0,0,0,0,0,18,4,53,0,0,0,75,1,0,0,65,0,0,0,255,0,1,4,48,0,0,0,0,2,0,0,25,0,253,0,249,0,0,4,15,0,0,0,2,1,0,0,41,0,0,0,0,1,1,4,51,0,0,0,64,1,16,2,16,0,0,0,73,1,16,1,151,0,0,0,254,0,1,4,46,0,0,0,0,1,0,0,25,0,0,0,255,0,1,4,48,0,0,0,0,2,0,0,25,0,253,0,249,0,0,4,15,0,0,0,2,1,0,0,41,0,0,0,0,1,1,4,51,0,0,0,1,2,0,0,41,0,0,0,0,2,32,0,76,0,0,0,209,0,0,193,61,0,0,0,64,1,16,2,16,0,0,0,73,1,16,1,151,0,0,0,254,0,1,4,46,0,0,0,68,2,16,0,57,0,0,0,77,3,0,0,65,0,0,0,0,0,50,4,53,0,0,0,36,2,16,0,57,0,0,0,26,3,0,0,57,0,0,0,0,0,50,4,53,0,0,0,78,2,0,0,65,0,0,0,0,0,33,4,53,0,0,0,4,2,16,0,57,0,0,0,32,3,0,0,57,0,0,0,0,0,50,4,53,0,0,0,64,1,16,2,16,0,0,0,73,1,16,1,151,0,0,0,79,1,16,1,199,0,0,0,255,0,1,4,48,0,2,0,0,0,0,0,2,0,2,0,0,0,3,0,29,0,0,0,32,3,48,0,57,0,1,0,0,0,3,0,29,0,0,0,239,0,33,4,35,0,0,0,2,3,0,0,41,0,0,0,32,2,48,1,26,0,0,0,0,2,1,3,85,0,0,0,72,1,0,0,65,0,0,0,1,2,0,0,41,0,0,0,32,2,32,1,26,0,0,0,0,2,18,1,189,0,0,0,0,1,3,0,25,0,0,0,2,0,0,0,5,0,0,0,0,0,1,4,45,0,0,0,2,3,0,0,41,0,0,0,32,2,48,1,26,0,0,0,0,2,1,3,85,0,0,0,80,1,0,0,65,0,0,0,1,2,0,0,41,0,0,0,32,2,32,1,26,0,0,0,0,2,18,1,141,0,0,0,0,1,3,0,25,0,0,0,2,0,0,0,5,0,0,0,0,0,1,4,45,0,0,0,0,0,18,4,27,0,0,0,0,0,1,4,45,0,0,0,0,1,1,4,26,0,0,0,0,0,1,4,45,0,0,0,253,0,0,4,50,0,0,0,254,0,1,4,46,0,0,0,255,0,1,4,48,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,255,255,255,255,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,64,0,0,1,0,0,0,0,0,0,0,0,0,24,6,170,24,150,187,242,101,104,232,132,167,55,75,65,224,2,80,9,98,202,186,106,21,2,58,141,144,232,80,139,131,2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,36,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,54,218,214,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,109,76,230,60,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,124,245,218,176,128,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,255,255,255,255,0,0,0,0,0,0,0,0,78,72,123,113,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,36,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,32,0,0,0,0,0,0,0,0,0,0,0,0,84,104,105,115,32,109,101,116,104,111,100,32,97,108,119,97,121,115,32,114,101,118,101,114,116,115,0,0,0,0,0,0,8,195,121,160,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,100,0,0,0,0,0,0,0,0,0,0,0,0,127,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255]]}}],\"id\":1}");
  }

  @Test
  void zksGetTestnetPaymaster() throws Exception {
    zkSync.zksGetTestnetPaymaster().send();

    verifyResult(
        "{\"jsonrpc\":\"2.0\",\"method\":\"zks_getTestnetPaymaster\",\"params\":[],\"id\":1}");
  }
}
