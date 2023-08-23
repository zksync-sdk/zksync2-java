package io.zksync.utils;

public enum AccountAbstractionVersion {
    NONE(0),
    Version1(1);

    private final int value;
    AccountAbstractionVersion(int value) {
        this.value = value;
    }

    public int getRawValue(){
        return this.value;
    }
}
