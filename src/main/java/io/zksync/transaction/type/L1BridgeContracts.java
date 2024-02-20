package io.zksync.transaction.type;

import io.zksync.wrappers.IL1Bridge;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

public class L1BridgeContracts {
    public IL1Bridge erc20L1Bridge;
    public IL1Bridge wethL1Bridge;

    public L1BridgeContracts(IL1Bridge erc20L1Bridge, IL1Bridge wethL1Bridge) {
        this.erc20L1Bridge = erc20L1Bridge;
        this.wethL1Bridge = wethL1Bridge;
    }

    public L1BridgeContracts(String  erc20L1Bridge, String wethL1Bridge, Web3j providerL1, TransactionManager manager, ContractGasProvider gasProvider) {
        this(IL1Bridge.load(erc20L1Bridge, providerL1, manager, gasProvider), IL1Bridge.load(wethL1Bridge, providerL1, manager, gasProvider));
    }
}
