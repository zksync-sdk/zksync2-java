package io.zksync.utils;

public final class ZkSyncAddresses {

    /**
     * The address of the L1 `ETH` token.
     * @constant
     */
    @Deprecated
    public static final String ETH_ADDRESS = "0x0000000000000000000000000000000000000000";
    /**
     * The address of the L1 `ETH` token.
     * @constant
     */
    public static final String LEGACY_ETH_ADDRESS = "0x0000000000000000000000000000000000000000";

    /**
     * In the contracts the zero address can not be used, use one instead
     * @constant
     */
    public static final String ETH_ADDRESS_IN_CONTRACTS = "0x0000000000000000000000000000000000000001";
    public static final String CONTRACT_DEPLOYER_ADDRESS = "0x0000000000000000000000000000000000008006";
    public static final String NONCE_HOLDER_ADDRESS = "0x0000000000000000000000000000000000008003";
    public static final String MESSENGER_ADDRESS = "0x0000000000000000000000000000000000008008";
    @Deprecated
    public static final String L2_ETH_TOKEN_ADDRESS = "0x000000000000000000000000000000000000800a";
    /**
     * The address of the base token.
     * @constant
     */
    public static final String L2_BASE_TOKEN_ADDRESS = "0x000000000000000000000000000000000000800a";
    public static final String BOOTLOADER_FORMAL_ADDRESS = "0x0000000000000000000000000000000000008001";

    public static boolean isEth(String token){
        return token.equalsIgnoreCase(ETH_ADDRESS_IN_CONTRACTS) ||
                token.equalsIgnoreCase(L2_BASE_TOKEN_ADDRESS) ||
                token.equalsIgnoreCase(LEGACY_ETH_ADDRESS);
    }
}
