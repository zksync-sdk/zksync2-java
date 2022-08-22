package io.zksync.helper.eip712;

import java.util.ArrayList;
import java.util.List;

import org.web3j.abi.datatypes.*;
import org.apache.commons.lang3.tuple.Pair;

import io.zksync.crypto.eip712.Structurable;

public class Mail implements Structurable {
    public Person from;
    public Person to;
    public Utf8String contents;

    public Mail() {
        this(
            new Person("Cow", "0xCD2a3d9F938E13CD947Ec05AbC7FE734Df8DD826"),
            new Person("Bob", "0xbBbBBBBbbBBBbbbBbbBbbbbBBbBbbbbBbBbbBBbB"),
            "Hello, Bob!"
        );
    }

    public Mail(Person from, Person to, String contents) {
        this.from = from;
        this.to = to;
        this.contents = new Utf8String(contents);
    }

    @Override
    public String getTypeName() {
        return "Mail";
    }

    @Override
    public List<Pair<String, Type<?>>> eip712types() {
        return new ArrayList<Pair<String, Type<?>>>() {{
            add(Pair.of("from", from.intoEip712Struct()));
            add(Pair.of("to", to.intoEip712Struct()));
            add(Pair.of("contents", contents));
        }};
    }
}
