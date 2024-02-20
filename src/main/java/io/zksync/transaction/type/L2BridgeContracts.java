package io.zksync.transaction.type;

import io.zksync.protocol.ZkSync;
import io.zksync.wrappers.IL1Bridge;
import io.zksync.wrappers.IL2Bridge;
import org.web3j.protocol.Web3j;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

public class L2BridgeContracts {
    public IL2Bridge erc20L2Bridge;
    public IL2Bridge wethL2Bridge;

    public L2BridgeContracts(IL2Bridge erc20L1Bridge, IL2Bridge wethL1Bridge) {
        this.erc20L2Bridge = erc20L1Bridge;
        this.wethL2Bridge = wethL1Bridge;
    }

    public L2BridgeContracts(String  erc20L2Bridge, String wethL2Bridge, ZkSync providerL2, TransactionManager manager, ContractGasProvider gasProvider) {
        this(IL2Bridge.load(erc20L2Bridge, providerL2, manager, gasProvider), IL2Bridge.load(wethL2Bridge, providerL2, manager, gasProvider));
    }
}
