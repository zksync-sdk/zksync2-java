package io.zksync.helper.eip712;

import java.util.ArrayList;
import java.util.List;

import org.web3j.abi.datatypes.*;
import org.apache.commons.lang3.tuple.Pair;

import io.zksync.crypto.eip712.Structurable;

public class Person implements Structurable {
    public Utf8String name;
    public Address wallet;

    public Person(String name, String wallet) {
        this.name = new Utf8String(name);
        this.wallet = new Address(wallet);
    }

    @Override
    public String getTypeName() {
        return "Person";
    }

    @Override
    public List<Pair<String, Type<?>>> eip712types() {
        return new ArrayList<Pair<String, Type<?>>>() {{
            add(Pair.of("name", name));
            add(Pair.of("wallet", wallet));
        }};
    }
}
