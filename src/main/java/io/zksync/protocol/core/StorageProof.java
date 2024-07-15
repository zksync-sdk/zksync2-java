package io.zksync.protocol.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StorageProof {
    private String address;
    private StorageProofData storageProof;
}
