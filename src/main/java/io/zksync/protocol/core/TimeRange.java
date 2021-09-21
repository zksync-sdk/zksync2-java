package io.zksync.protocol.core;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import org.web3j.abi.datatypes.generated.Uint64;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeRange {

    @JsonIgnore
    private Uint64 from;

    @JsonIgnore
    private Uint64 until;

    @JsonGetter("from")
    public BigInteger getFromNumber() {
        return from.getValue();
    }

    @JsonGetter("until")
    public BigInteger getUntilNumber() {
        return until.getValue();
    }

    @JsonSetter("from")
    public void setFrom(BigInteger from) {
        this.from = new Uint64(from);
    }

    @JsonSetter("until")
    public void setUntil(BigInteger until) {
        this.until = new Uint64(until);
    }

    public TimeRange() {
        this.from = new Uint64(0);
        this.until = new Uint64(4294967295L);
    }
}
