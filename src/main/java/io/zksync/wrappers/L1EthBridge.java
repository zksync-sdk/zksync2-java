package io.zksync.wrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class L1EthBridge extends Contract {
    public static final String BINARY = "0x60a060405234801561001057600080fd5b506040516111f43803806111f483398101604081905261002f916100aa565b610037610048565b6001600160a01b03166080526100da565b7f8e94fed44239eb2314ab7a406345e6c5a8f0ccedf3b600de3d004e672c33abf48054600190915580156100a75760405162461bcd60e51b815260206004820152600260248201526118a160f11b604482015260640160405180910390fd5b50565b6000602082840312156100bc57600080fd5b81516001600160a01b03811681146100d357600080fd5b9392505050565b6080516110ea61010a60003960008181610369015281816104e6015281816106ed015261089401526110ea6000f3fe6080604052600436106100705760003560e01c80639181e55d1161004e5780639181e55d1461010557806393d2227e14610125578063ae1f6aaf14610145578063f5f151681461017d57600080fd5b8063439fab91146100755780634bed8212146100975780638340f549146100e4575b600080fd5b34801561008157600080fd5b50610095610090366004610b8d565b61019e565b005b3480156100a357600080fd5b506100cf6100b2366004610bcf565b600060208181529281526040808220909352908152205460ff1681565b60405190151581526020015b60405180910390f35b6100f76100f2366004610c0d565b61041c565b6040519081526020016100db565b34801561011157600080fd5b50610095610120366004610c8e565b6105fa565b34801561013157600080fd5b50610095610140366004610d11565b6107e6565b34801561015157600080fd5b50600254610165906001600160a01b031681565b6040516001600160a01b0390911681526020016100db565b34801561018957600080fd5b50610165610198366004610d93565b50600090565b6101a661098f565b6040805130602082015260009182910160408051601f198184030181526020601f870181900481028401810190925285835292506000916102019187908790819084018382808284376000920191909152506109e092505050565b90506000631415dae260e01b84836000866040516024016102259493929190610e11565b60408051601f19818403018152918152602080830180516001600160e01b03166001600160e01b03199095169490941790935285518684012081517f2020dba91b30cc0006188af794c2fb30dd8520db7e2c088b7fc7c103c00ca494818601523081840152606081018990526080810187905260a0808201929092528251808203909201825260c0019091528051920191909120909150600280546001600160a01b0319166001600160a01b0392909216919091179055604080516001808252818301909252600091602082015b60608152602001906001900390816102f357905050905086868080601f016020809104026020016040519081016040528093929190818152602001838380828437600092018290525085518694509092501515905061035457610354610e43565b60209081029190910101526001600160a01b037f00000000000000000000000000000000000000000000000000000000000000001663cfeddec761039b6180006006610e6f565b8462200000856040518563ffffffff1660e01b81526004016103c09493929190610e9a565b602060405180830381600087803b1580156103da57600080fd5b505af11580156103ee573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906104129190610f25565b5050505050505050565b600080516020611095833981519152546000906001811461043c57600080fd5b6002600080516020611095833981519152556001600160a01b0384161561046257600080fd5b600061046e8434610f3e565b604080513360248201526001600160a01b03891660448201526000606482018190526084820188905260a060a483015260c48083018290528351808403909101815260e49092019092526020810180516001600160e01b03166333f9ebdf60e21b179052919250906002549091506001600160a01b037f000000000000000000000000000000000000000000000000000000000000000081169163cfeddec7918591168462200000600060405190808252806020026020018201604052801561054b57816020015b60608152602001906001900390816105365790505b506040518663ffffffff1660e01b815260040161056b9493929190610e9a565b6020604051808303818588803b15801561058457600080fd5b505af1158015610598573d6000803e3d6000fd5b50505050506040513d601f19601f820116820180604052508101906105bd9190610f25565b3360009081526001602081815260408084208585529091529091209690965560008051602061109583398151915295909555509295945050505050565b600080516020611095833981519152546001811461061757600080fd5b60026000805160206110958339815191525560008781526020818152604080832089845290915290205460ff161561064e57600080fd5b6040805180820182526002546001600160a01b0316815281516020601f8801819004810282018101909352868152600092808301919089908990819084018382808284376000920182905250939094525050604080516020601f8b0181900481028201810190925289815293945090928392506106e5918a908a9081908401838280828437600092019190915250610a9092505050565b9150915060007f00000000000000000000000000000000000000000000000000000000000000006001600160a01b0316630f2b290e8c8c878b8b6040518663ffffffff1660e01b815260040161073f959493929190610f8b565b60206040518083038186803b15801561075757600080fd5b505afa15801561076b573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061078f9190610fe6565b90508061079b57600080fd5b60008b8152602081815260408083208d84529091529020805460ff191660011790556107c78383610adf565b5050505060016000805160206110958339815191525550505050505050565b600080516020611095833981519152546001811461080357600080fd5b6002600080516020611095833981519152556001600160a01b0387161561082957600080fd5b6001600160a01b03881660009081526001602090815260408083208984529091529020548061085757600080fd5b6000604051806060016040528061800060016108739190610e6f565b6001600160a01b031681526020018981526020016000801b815250905060007f00000000000000000000000000000000000000000000000000000000000000006001600160a01b031663f50ceca98989858a8a6040518663ffffffff1660e01b81526004016108e6959493929190611008565b60206040518083038186803b1580156108fe57600080fd5b505afa158015610912573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906109369190610fe6565b90508061094257600080fd5b6001600160a01b038b1660009081526001602090815260408083208c84529091528120556109708b84610adf565b5050506001600080516020611095833981519152555050505050505050565b6000805160206110958339815191528054600190915580156109dd5760405162461bcd60e51b815260206004820152600260248201526118a160f11b60448201526064015b60405180910390fd5b50565b600080602083516109f19190611056565b9050620100008110610a2a5760405162461bcd60e51b8152602060048201526002602482015261070760f41b60448201526064016109d4565b600283604051610a3a9190611078565b602060405180830381855afa158015610a57573d6000803e3d6000fd5b5050506040513d601f19601f82011682018060405250810190610a7a9190610f25565b6001600160f01b031660f09190911b1792915050565b6000808251603814610aa157600080fd5b60048381015190639181e55d60e01b6001600160e01b031960e084901b1614610ac957600080fd5b6014940193840151603490940151939492505050565b6000826001600160a01b03168260405160006040518083038185875af1925050503d8060008114610b2c576040519150601f19603f3d011682016040523d82523d6000602084013e610b31565b606091505b5050905080610b3f57600080fd5b505050565b60008083601f840112610b5657600080fd5b50813567ffffffffffffffff811115610b6e57600080fd5b602083019150836020828501011115610b8657600080fd5b9250929050565b60008060208385031215610ba057600080fd5b823567ffffffffffffffff811115610bb757600080fd5b610bc385828601610b44565b90969095509350505050565b60008060408385031215610be257600080fd5b50508035926020909101359150565b80356001600160a01b0381168114610c0857600080fd5b919050565b600080600060608486031215610c2257600080fd5b610c2b84610bf1565b9250610c3960208501610bf1565b9150604084013590509250925092565b60008083601f840112610c5b57600080fd5b50813567ffffffffffffffff811115610c7357600080fd5b6020830191508360208260051b8501011115610b8657600080fd5b60008060008060008060808789031215610ca757600080fd5b8635955060208701359450604087013567ffffffffffffffff80821115610ccd57600080fd5b610cd98a838b01610b44565b90965094506060890135915080821115610cf257600080fd5b50610cff89828a01610c49565b979a9699509497509295939492505050565b600080600080600080600060c0888a031215610d2c57600080fd5b610d3588610bf1565b9650610d4360208901610bf1565b955060408801359450606088013593506080880135925060a088013567ffffffffffffffff811115610d7457600080fd5b610d808a828b01610c49565b989b979a50959850939692959293505050565b600060208284031215610da557600080fd5b610dae82610bf1565b9392505050565b60005b83811015610dd0578181015183820152602001610db8565b83811115610ddf576000848401525b50505050565b60008151808452610dfd816020860160208601610db5565b601f01601f19169290920160200192915050565b84815283602082015260ff83166040820152608060608201526000610e396080830184610de5565b9695505050505050565b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052601160045260246000fd5b60006001600160a01b03828116848216808303821115610e9157610e91610e59565b01949350505050565b6001600160a01b038516815260806020808301829052600091610ebf90840187610de5565b85604085015283810360608501528085518083528383019150838160051b84010184880160005b83811015610f1457601f19868403018552610f02838351610de5565b94870194925090860190600101610ee6565b50909b9a5050505050505050505050565b600060208284031215610f3757600080fd5b5051919050565b600082821015610f5057610f50610e59565b500390565b81835260006001600160fb1b03831115610f6e57600080fd5b8260051b8083602087013760009401602001938452509192915050565b8581528460208201526080604082015260018060a01b03845116608082015260006020850151604060a0840152610fc560c0840182610de5565b90508281036060840152610fda818587610f55565b98975050505050505050565b600060208284031215610ff857600080fd5b81518015158114610dae57600080fd5b85815284602082015260018060a01b038451166040820152602084015160608201526040840151608082015260c060a0820152600061104b60c083018486610f55565b979650505050505050565b60008261107357634e487b7160e01b600052601260045260246000fd5b500490565b6000825161108a818460208701610db5565b919091019291505056fe8e94fed44239eb2314ab7a406345e6c5a8f0ccedf3b600de3d004e672c33abf4a264697066735822122024efe3448be7162ff191dd4130ec890103c1b3e19b9bcf37c3df3e78041e02cd64736f6c63430008090033";

    public static final String FUNC_CLAIMFAILEDDEPOSIT = "claimFailedDeposit";

    public static final String FUNC_DEPOSIT = "deposit";

    public static final String FUNC_FINALIZEWITHDRAWAL = "finalizeWithdrawal";

    public static final String FUNC_INITIALIZE = "initialize";

    public static final String FUNC_ISWITHDRAWALFINALIZED = "isWithdrawalFinalized";

    public static final String FUNC_L2BRIDGE = "l2Bridge";

    public static final String FUNC_L2TOKENADDRESS = "l2TokenAddress";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected L1EthBridge(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected L1EthBridge(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected L1EthBridge(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected L1EthBridge(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> claimFailedDeposit(String _depositSender, String _l1Token, byte[] _l2TxHash, BigInteger _l2BlockNumber, BigInteger _l2MessageIndex, List<byte[]> _merkleProof) {
        final Function function = new Function(
                FUNC_CLAIMFAILEDDEPOSIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_depositSender), 
                new org.web3j.abi.datatypes.Address(_l1Token), 
                new org.web3j.abi.datatypes.generated.Bytes32(_l2TxHash), 
                new org.web3j.abi.datatypes.generated.Uint256(_l2BlockNumber), 
                new org.web3j.abi.datatypes.generated.Uint256(_l2MessageIndex), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_merkleProof, org.web3j.abi.datatypes.generated.Bytes32.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> deposit(String _l2Receiver, String _l1Token, BigInteger _amount) {
        final Function function = new Function(
                FUNC_DEPOSIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_l2Receiver), 
                new org.web3j.abi.datatypes.Address(_l1Token), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, _amount);
    }

    public RemoteFunctionCall<TransactionReceipt> finalizeWithdrawal(BigInteger _l2BlockNumber, BigInteger _l2MessageIndex, byte[] _message, List<byte[]> _merkleProof) {
        final Function function = new Function(
                FUNC_FINALIZEWITHDRAWAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_l2BlockNumber), 
                new org.web3j.abi.datatypes.generated.Uint256(_l2MessageIndex), 
                new org.web3j.abi.datatypes.DynamicBytes(_message), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_merkleProof, org.web3j.abi.datatypes.generated.Bytes32.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> initialize(byte[] _l2BridgeBytecode) {
        final Function function = new Function(
                FUNC_INITIALIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_l2BridgeBytecode)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> isWithdrawalFinalized(BigInteger param0, BigInteger param1) {
        final Function function = new Function(FUNC_ISWITHDRAWALFINALIZED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.generated.Uint256(param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> l2Bridge() {
        final Function function = new Function(FUNC_L2BRIDGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> l2TokenAddress(String param0) {
        final Function function = new Function(FUNC_L2TOKENADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static L1EthBridge load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new L1EthBridge(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static L1EthBridge load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new L1EthBridge(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static L1EthBridge load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new L1EthBridge(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static L1EthBridge load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new L1EthBridge(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<L1EthBridge> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _mailbox) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_mailbox)));
        return deployRemoteCall(L1EthBridge.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<L1EthBridge> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _mailbox) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_mailbox)));
        return deployRemoteCall(L1EthBridge.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<L1EthBridge> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _mailbox) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_mailbox)));
        return deployRemoteCall(L1EthBridge.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<L1EthBridge> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _mailbox) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_mailbox)));
        return deployRemoteCall(L1EthBridge.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }
}
