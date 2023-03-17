package io.zksync.methods.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.web3j.protocol.core.methods.response.EthBlock;


@AllArgsConstructor
@NoArgsConstructor
@Data

public class ZksBlock extends EthBlock {


    private String baseFeePerGas;
    private String l1BatchNumber;
    private String l1BatchTimestamp;

}