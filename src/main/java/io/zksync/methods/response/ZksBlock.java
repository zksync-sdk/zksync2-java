package io.zksync.methods.response;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.response.AccessListObject;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ZksBlock extends Response<ZksBlock.Block> {

    @Override
    @JsonDeserialize(using = ZksBlock.ResponseDeserialiser.class)
    public void setResult(ZksBlock.Block result) {
        super.setResult(result);
    }

    public ZksBlock.Block getBlock() {
        return getResult();
    }

    public static class Block extends EthBlock.Block {
        private String l1BatchNumber;
        private String l1BatchTimestamp;

        public Block() {}

        public Block(String number, String hash, String parentHash, String nonce, String sha3Uncles, String logsBloom,
                     String transactionsRoot, String stateRoot, String receiptsRoot, String author, String miner,
                     String mixHash, String difficulty, String totalDifficulty, String extraData, String size,
                     String gasLimit, String gasUsed, String timestamp, List<EthBlock.TransactionResult> transactions,
                     List<String> uncles, List<String> sealFields, String baseFeePerGas, String l1BatchNumber,
                     String l1BatchTimestamp) {
            super(number, hash, parentHash, nonce, sha3Uncles, logsBloom, transactionsRoot, stateRoot, receiptsRoot,
                    author, miner, mixHash, difficulty, totalDifficulty, extraData, size, gasLimit, gasUsed, timestamp,
                    transactions, uncles, sealFields, baseFeePerGas);
            this.l1BatchNumber = l1BatchNumber;
            this.l1BatchTimestamp = l1BatchTimestamp;
        }

        public BigInteger getL1BatchNumber() {
            return Numeric.decodeQuantity(l1BatchNumber);
        }

        public void setL1BatchNumber(String l1BatchNumber) {
            this.l1BatchNumber = l1BatchNumber;
        }

        public BigInteger getL1BatchTimestamp() {
            return Numeric.decodeQuantity(l1BatchTimestamp);
        }

        public void setL1BatchTimestamp(String l1BatchTimestamp) {
            this.l1BatchTimestamp = l1BatchTimestamp;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof Block)) {
                return false;
            }

            if (!super.equals(o)) {
                return false;
            }

            Block block = (Block) o;

            if (getL1BatchNumber() != null
                    ? !getL1BatchNumber().equals(block.getL1BatchNumber())
                    : block.getL1BatchNumber() != null) {
                return false;
            }

            return getL1BatchTimestamp() != null
                    ? getL1BatchTimestamp().equals(block.getL1BatchTimestamp())
                    : block.getL1BatchTimestamp() == null;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (getL1BatchTimestamp() != null ? getL1BatchTimestamp().hashCode() : 0);
            result = 31 * result + (getL1BatchNumber() != null ? getL1BatchNumber().hashCode() : 0);
            return result;
        }
    }

    public interface TransactionResult<T> {
        T get();
    }

    public static class TransactionHash implements ZksBlock.TransactionResult<String> {
        private String value;

        public TransactionHash() {}

        public TransactionHash(String value) {
            this.value = value;
        }

        @Override
        public String get() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ZksBlock.TransactionHash)) {
                return false;
            }

            ZksBlock.TransactionHash that = (ZksBlock.TransactionHash) o;

            return Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return value != null ? value.hashCode() : 0;
        }
    }


    public static class TransactionObject extends ZkTransaction
            implements ZksBlock.TransactionResult<ZkTransaction> {
        public TransactionObject(
                String hash,
                String nonce,
                String blockHash,
                String blockNumber,
                String chainId,
                String transactionIndex,
                String from,
                String to,
                String value,
                String gasPrice,
                String gas,
                String input,
                String creates,
                String publicKey,
                String raw,
                String r,
                String s,
                long v,
                String type,
                String maxFeePerGas,
                String maxPriorityFeePerGas,
                List<AccessListObject> accessList,
                String l1BatchNumber,
                String l1BatchTxIndex) {
            super(
                    hash,
                    nonce,
                    blockHash,
                    blockNumber,
                    chainId,
                    transactionIndex,
                    from,
                    to,
                    value,
                    gas,
                    gasPrice,
                    input,
                    creates,
                    publicKey,
                    raw,
                    r,
                    s,
                    v,
                    type,
                    maxFeePerGas,
                    maxPriorityFeePerGas,
                    accessList,
                    l1BatchNumber,
                    l1BatchTxIndex);
        }

        @Override
        public ZkTransaction get() {
            return this;
        }
    }

    public static class ResultTransactionDeserialiser
            extends JsonDeserializer<List<ZksBlock.TransactionResult>> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public List<ZksBlock.TransactionResult> deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {

            List<ZksBlock.TransactionResult> transactionResults = new ArrayList<>();
            JsonToken nextToken = jsonParser.nextToken();

            if (nextToken == JsonToken.START_OBJECT) {
                Iterator<ZksBlock.TransactionObject> transactionObjectIterator =
                        objectReader.readValues(jsonParser, ZksBlock.TransactionObject.class);
                while (transactionObjectIterator.hasNext()) {
                    transactionResults.add(transactionObjectIterator.next());
                }
            } else if (nextToken == JsonToken.VALUE_STRING) {
                jsonParser.getValueAsString();

                Iterator<ZksBlock.TransactionHash> transactionHashIterator =
                        objectReader.readValues(jsonParser, ZksBlock.TransactionHash.class);
                while (transactionHashIterator.hasNext()) {
                    transactionResults.add(transactionHashIterator.next());
                }
            }

            return transactionResults;
        }
    }

    public static class ResponseDeserialiser extends JsonDeserializer<ZksBlock.Block> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public ZksBlock.Block deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, ZksBlock.Block.class);
            } else {
                return null; // null is wrapped by Optional in above getter
            }
        }
    }
}