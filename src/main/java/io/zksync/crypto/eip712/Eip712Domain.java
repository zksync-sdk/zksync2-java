package io.zksync.crypto.eip712;

import io.zksync.protocol.core.ZkSyncNetwork;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;

@Data
@AllArgsConstructor
public class Eip712Domain implements Structurable {

  public static final String NAME = "zkSync";
  public static final String VERSION = "2";

  public static Eip712Domain defaultDomain(ZkSyncNetwork chainId) {
    return new Eip712Domain(
        new Utf8String(NAME), new Utf8String(VERSION), new Uint256(chainId.getChainId()), null);
  }

  public static Eip712Domain defaultDomain(Long chainId) {
    return new Eip712Domain(
        new Utf8String(NAME), new Utf8String(VERSION), new Uint256(chainId), null);
  }

  public Eip712Domain(String name, String version, Long chainId) {
    this(new Utf8String(name), new Utf8String(version), new Uint256(chainId), null);
  }

  public Eip712Domain(String name, String version, ZkSyncNetwork chainId, String address) {
    this(
        new Utf8String(name),
        new Utf8String(version),
        new Uint256(chainId.getChainId()),
        new Address(address));
  }

  public Eip712Domain(String name, String version, Long chainId, String address) {
    this(new Utf8String(name), new Utf8String(version), new Uint256(chainId), new Address(address));
  }

  private Utf8String name;

  private Utf8String version;

  private Uint256 chainId;

  private Address verifyingContract;

  @Override
  public String getTypeName() {
    return "EIP712Domain";
  }

  @Override
  public List<Pair<String, Type<?>>> eip712types() {
    return new ArrayList<Pair<String, Type<?>>>() {
      {
        add(Pair.of("name", name));
        add(Pair.of("version", version));
        add(Pair.of("chainId", chainId));
        if (verifyingContract != null) add(Pair.of("verifyingContract", verifyingContract));
      }
    };
  }
}
