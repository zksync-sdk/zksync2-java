package io.zksync.protocol.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StorageProof {
    private String address;
    private StorageProofData storageProof;
}
