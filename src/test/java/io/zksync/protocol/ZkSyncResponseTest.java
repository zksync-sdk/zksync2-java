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
        buildResponse("{\"jsonrpc\":\"2.0\",\"result\":{\"ergs_limit\":\"0x4be68\",\"ergs_per_pubdata_limit\":\"0x27100\",\"max_fee_per_erg\":\"0x5f5e100\",\"max_priority_fee_per_erg\":\"0x5f5e100\"},\"id\":1}");

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

}
