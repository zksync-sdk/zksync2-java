package io.zksync.protocol;

import io.zksync.helper.ResponseTester;
import io.zksync.methods.response.*;
import io.zksync.protocol.core.Token;
import io.zksync.transaction.fee.Fee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZkSyncResponseTest extends ResponseTester {

    @Test
    void zksEstimateFee() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"result\":{\"gas_limit\":\"0x4be68\",\"gas_per_pubdata_limit\":\"0x27100\",\"max_fee_per_erg\":\"0x5f5e100\",\"max_priority_fee_per_erg\":\"0x5f5e100\"},\"id\":1}");

        ZksEstimateFee zksEstimateFee = deserialiseResponse(ZksEstimateFee.class);

        Fee fee = zksEstimateFee.getResult();
        Assertions.assertEquals(BigInteger.valueOf(310888), fee.getGasLimitNumber());
        Assertions.assertEquals(BigInteger.valueOf(100000000), fee.getGasPriceLimitNumber());
        Assertions.assertEquals(BigInteger.valueOf(160000), fee.getGasPerPubdataLimitNumber());
        Assertions.assertEquals(BigInteger.valueOf(100000000), fee.getMaxPriorityFeePerErgNumber());
    }

    @Test
    void zksMainContract() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"result\":\"0xdbf3be149a9d1aa76870f44670e9db4cfe041766\",\"id\":1}");

        ZksMainContract zksMainContract = deserialiseResponse(ZksMainContract.class);

        Assertions.assertEquals("0xdbf3be149a9d1aa76870f44670e9db4cfe041766", zksMainContract.getResult());
    }

    @Test
    void zksGetConfirmedTokens() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"result\":[{\"decimals\":18,\"l1Address\":\"0x0000000000000000000000000000000000000000\",\"l2Address\":\"0x0000000000000000000000000000000000000000\",\"name\":\"Ether\",\"symbol\":\"ETH\"}],\"id\":1}");

        ZksTokens zksTokens = deserialiseResponse(ZksTokens.class);

        List<Token> expected = Collections.singletonList(Token.ETH);

        Assertions.assertEquals(expected, zksTokens.getResult());
    }

    @Test
    void zksGetTokenPrice() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"result\":\"3500.00\",\"id\":1}");

        ZksTokenPrice zksTokenPrice = deserialiseResponse(ZksTokenPrice.class);

        Assertions.assertEquals(BigInteger.valueOf(3500), zksTokenPrice.getResult().toBigInteger());
    }

    @Test
    void zksL1ChainId() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"result\":\"0x9\",\"id\":1}");

        ZksL1ChainId zksL1ChainId = deserialiseResponse(ZksL1ChainId.class);

        Assertions.assertEquals(BigInteger.valueOf(9), zksL1ChainId.getChainId());
    }

    @Test
    void zksGetContractDebugInfo() {
    }

    @Test
    void zksGetTransactionTrace() {
    }

    @Test
    void zksGetAllAccountBalances() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"result\":{\"0x0000000000000000000000000000000000000000\":\"0xa6886f73b1e\"},\"id\":1}");

        ZksAccountBalances zksAccountBalances = deserialiseResponse(ZksAccountBalances.class);

        Map<String, BigInteger> expected = new HashMap<String, BigInteger>() {{
            put("0x0000000000000000000000000000000000000000", BigInteger.valueOf(11444057226014L));
        }};

        Assertions.assertEquals(expected, zksAccountBalances.getBalances());
    }

    @Test
    void zksGetBridgeContracts() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"result\":{\"l1Erc20DefaultBridge\":\"0x3f92815bec299f152eafa1dc9b1290b307b91cfb\",\"l1EthDefaultBridge\":\"0x20410058241d1c4c5f7c04753bd4713b04f19423\",\"l2Erc20DefaultBridge\":\"0x39971d3297eb5b99d2f1f3ee42a5abecce0b751c\",\"l2EthDefaultBridge\":\"0x127cd7c40b8df6a51a15983f9073c99bf05ea1e7\"},\"id\":1}");

        ZksBridgeAddresses zksBridgeAddresses = deserialiseResponse(ZksBridgeAddresses.class);

        Assertions.assertEquals("0x20410058241d1c4c5f7c04753bd4713b04f19423", zksBridgeAddresses.getResult().getL1EthDefaultBridge());
        Assertions.assertEquals("0x127cd7c40b8df6a51a15983f9073c99bf05ea1e7", zksBridgeAddresses.getResult().getL2EthDefaultBridge());
        Assertions.assertEquals("0x3f92815bec299f152eafa1dc9b1290b307b91cfb", zksBridgeAddresses.getResult().getL1Erc20DefaultBridge());
        Assertions.assertEquals("0x39971d3297eb5b99d2f1f3ee42a5abecce0b751c", zksBridgeAddresses.getResult().getL2Erc20DefaultBridge());
    }

    @Test
    void zksGetL2ToL1MsgProof() {
    }

    @Test
    void zksGetTestnetPaymaster() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"result\":\"0xd8128f25ec5446a89c223d1e546299183bc8b033\",\"id\":1}");

        ZksTestnetPaymasterAddress zksTestnetPaymasterAddress = deserialiseResponse(ZksTestnetPaymasterAddress.class);

        Assertions.assertEquals("0xd8128f25ec5446a89c223d1e546299183bc8b033", zksTestnetPaymasterAddress.getResult());
    }

    @Test
    void zksGetBlock() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"result\":{\"baseFeePerGas\":\"0x1dcd6500\",\"difficulty\":\"0x0\",\"extraData\":\"0x\",\"gasLimit\":\"0xffffffff\",\"gasUsed\":\"0x5deb6\",\"hash\":\"0xad7d3b645205e6651e447251f0f649d6d7a191a54e7140a4266e143e0de8e320\",\"l1BatchNumber\":\"0x730\",\"l1BatchTimestamp\":\"0x63eb7ae0\",\"logsBloom\":\"0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\",\"miner\":\"0x0000000000000000000000000000000000000000\",\"mixHash\":\"0x0000000000000000000000000000000000000000000000000000000000000000\",\"nonce\":\"0x0000000000000000\",\"number\":\"0x33450\",\"parentHash\":\"0xe47fa912bd7a769abb076cdf222e4fb643353b3cf4fccbe7b1562ace98bd9b29\",\"receiptsRoot\":\"0x0000000000000000000000000000000000000000000000000000000000000000\",\"sealFields\":[],\"sha3Uncles\":\"0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347\",\"size\":\"0x0\",\"stateRoot\":\"0x0000000000000000000000000000000000000000000000000000000000000000\",\"timestamp\":\"0x63eb7b59\",\"totalDifficulty\":\"0x0\",\"transactions\":[{\"blockHash\":\"0xad7d3b645205e6651e447251f0f649d6d7a191a54e7140a4266e143e0de8e320\",\"blockNumber\":\"0x33450\",\"chainId\":\"0x118\",\"from\":\"0x6b42ba9a6f3249779504fe5f3ee9869e74a185fc\",\"gas\":\"0x5de0b\",\"gasPrice\":\"0x1dcd6500\",\"hash\":\"0xcb7c744f0b0f04008cabd2cbe57ce81c173639df7e9a369140d1dc848518b122\",\"input\":\"0xfefe409d\",\"l1BatchNumber\":\"0x730\",\"l1BatchTxIndex\":\"0x6e\",\"maxFeePerGas\":\"0x1dcd6500\",\"maxPriorityFeePerGas\":\"0x1dcd6500\",\"nonce\":\"0x0\",\"r\":\"0x5a5f5e8ccdf2b105f3c59bd29881ccf8e1fab98ca5ac33a10fcc92be24074b80\",\"s\":\"0x44c5f61fcccca830b983713cc905a3c5bd653c104dd81c3aaf0ba328923f6f74\",\"to\":\"0x07a6ee401cb2c9e4d9c436fa65cfb800715fe6e9\",\"transactionIndex\":\"0x0\",\"type\":\"0x0\",\"v\":\"0x1\",\"value\":\"0x0\"},{\"blockHash\":\"0xad7d3b645205e6651e447251f0f649d6d7a191a54e7140a4266e143e0de8e320\",\"blockNumber\":\"0x33450\",\"chainId\":\"0x118\",\"from\":\"0x7f2a7868bfc7b4ffb3768f5abd1b68e9739cdd2e\",\"gas\":\"0x41448\",\"gasPrice\":\"0x1dcd6500\",\"hash\":\"0xcb75831c4ac88e5aca7437f48a58bfa701ffa6646d0e5495433c19dae543b33e\",\"input\":\"0xa72a241100000000000000000000000034bd12ea642608107ee573e956c3f5c789b6d2c50000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000053887e824808000\",\"l1BatchNumber\":\"0x730\",\"l1BatchTxIndex\":\"0x6f\",\"maxFeePerGas\":\"0x9502f900\",\"maxPriorityFeePerGas\":\"0x59682f00\",\"nonce\":\"0x11a\",\"r\":\"0xab9b8dedb64d2d9034c56245cee55d88ae4e867a05b9a94866dd208bb5a39fd6\",\"s\":\"0x27e447f696d7a2b4635eba277e501bd4c68b2d534c06f30eb1bdadc24b21afbd\",\"to\":\"0x2dd687e37323bd71a3b31b28dc6def2f2addd9b5\",\"transactionIndex\":\"0x1\",\"type\":\"0x2\",\"v\":\"0x0\",\"value\":\"0x0\"}],\"transactionsRoot\":\"0x0000000000000000000000000000000000000000000000000000000000000000\",\"uncles\":[]},\"id\":53}\n");

        ZksGetBlock zksBlock = deserialiseResponse(ZksGetBlock.class);

        Assertions.assertEquals("0x1dcd6500", zksBlock.getResult().getBaseFeePerGas());
        Assertions.assertEquals("0x730", zksBlock.getResult().getL1BatchNumber());
        Assertions.assertEquals("0x63eb7ae0", zksBlock.getResult().getL1BatchTimestamp());

    }


}
