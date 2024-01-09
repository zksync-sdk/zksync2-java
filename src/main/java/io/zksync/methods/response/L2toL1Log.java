package io.zksync.methods.response;

import org.web3j.abi.datatypes.Address;

public class L2toL1Log {
  public String BlockNumber;
  public String BlockHash;
  public String L1BatchNumber;
  public String TransactionIndex;
  public String ShardId;
  public boolean IsService;
  public Address Sender;
  public String Key;
  public String Value;
  public String TxHash;
  public int Index;

  public String getBlockNumber() {
    return BlockNumber;
  }

  public void setBlockNumber(String blockNumber) {
    BlockNumber = blockNumber;
  }

  public String getBlockHash() {
    return BlockHash;
  }

  public void setBlockHash(String blockHash) {
    BlockHash = blockHash;
  }

  public String getL1BatchNumber() {
    return L1BatchNumber;
  }

  public void setL1BatchNumber(String l1BatchNumber) {
    L1BatchNumber = l1BatchNumber;
  }

  public String getTransactionIndex() {
    return TransactionIndex;
  }

  public void setTransactionIndex(String transactionIndex) {
    TransactionIndex = transactionIndex;
  }

  public String getShardId() {
    return ShardId;
  }

  public void setShardId(String shardId) {
    ShardId = shardId;
  }

  public boolean isService() {
    return IsService;
  }

  public void setService(boolean service) {
    IsService = service;
  }

  public Address getSender() {
    return Sender;
  }

  public void setSender(Address sender) {
    Sender = sender;
  }

  public String getKey() {
    return Key;
  }

  public void setKey(String key) {
    Key = key;
  }

  public String getValue() {
    return Value;
  }

  public void setValue(String value) {
    Value = value;
  }

  public String getTxHash() {
    return TxHash;
  }

  public void setTxHash(String txHash) {
    TxHash = txHash;
  }

  public int getIndex() {
    return Index;
  }

  public void setIndex(int index) {
    Index = index;
  }
}
