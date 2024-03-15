package io.zksync.utils;

import io.zksync.helper.CounterContract;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.web3j.abi.datatypes.Address;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class ContractDeployerTest {

    @Test
    public void computeL2Create2AddressActual() {
        String expected = "0x0790aff699b38f40929840469a72fb40e9763716";
        byte[] salt = new byte[32];

        Address result = ContractDeployer.computeL2Create2Address(new Address("0xa909312acfc0ed4370b8bd20dfe41c8ff6595194"), Numeric.hexStringToByteArray(CounterContract.BINARY), new byte[] {}, salt);

        Assertions.assertEquals(expected, result.getValue());
    }

    @Test
    public void testApplyAlias(){
        String nonZeroPadded = WalletUtils.applyL1ToL2Alias("0x702942B8205E5dEdCD3374E5f4419843adA76Eeb");
        Assertions.assertEquals("0x813A42B8205E5DedCd3374e5f4419843ADa77FFC".toLowerCase(), nonZeroPadded.toLowerCase());
    }

    @Test
    public void testUndoAlias(){
        String nonZeroPadded = WalletUtils.applyL1ToL2Alias("0x702942B8205E5dEdCD3374E5f4419843adA76Eeb");
        Assertions.assertEquals("0x813A42B8205E5DedCd3374e5f4419843ADa77FFC".toLowerCase(), nonZeroPadded.toLowerCase());
    }

    @Test
    public void computeL2CreateAddressActual() {
        String expected = "0x5107b7154dfc1d3b7f1c4e19b5087e1d3393bcf4";

        Address result = ContractDeployer.computeL2CreateAddress(new Address("0x7e5f4552091a69125d5dfcb7b8c2659029395bdf"), BigInteger.valueOf(3));

        Assertions.assertEquals(expected, result.getValue());
    }

    @Test
    public void hashBytecode() {
        String expected = "0x010000517112c421df08d7b49e4dc1312f4ee62268ee4f5683b11d9e2d33525a";

        byte[] result = ContractDeployer.hashBytecode(Numeric.hexStringToByteArray(CounterContract.BINARY));
        String resultHex = Numeric.toHexString(result);

        Assertions.assertEquals(expected, resultHex);
    }
}