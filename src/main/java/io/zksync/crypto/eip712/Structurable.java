package io.zksync.crypto.eip712;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.web3j.abi.datatypes.Type;

public interface Structurable {
    
    String getType();

    List<Pair<String, Type<?>>> eip712types();

    default Eip712Struct intoEip712Struct() {
        return new Eip712Struct(this);
    }

}
