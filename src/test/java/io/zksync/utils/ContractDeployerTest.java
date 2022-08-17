package io.zksync.utils;

import io.zksync.helper.CounterContract;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class ContractDeployerTest {

    @Test
    public void computeL2Create2AddressActual() {
        String expected = "0x14ba05281495657b009103686f366d7761e7535b";
        byte[] salt = new byte[32];

        Address result = ContractDeployer.computeL2Create2Address(new Address("0xa909312acfc0ed4370b8bd20dfe41c8ff6595194"), Numeric.hexStringToByteArray(CounterContract.BINARY), new byte[] {}, salt);

        Assertions.assertEquals(expected, result.getValue());
    }

    @Test
    public void computeL2CreateAddressActual() {
        String expected = "0x5107b7154dfc1d3b7f1c4e19b5087e1d3393bcf4";

        Address result = ContractDeployer.computeL2CreateAddress(new Address("0x7e5f4552091a69125d5dfcb7b8c2659029395bdf"), BigInteger.valueOf(3));

        Assertions.assertEquals(expected, result.getValue());
    }

    @Test
    public void hashBytecode() {
        String expected = "0x00379c09b5568d43b0ac6533a2672ee836815530b412f082f0b2e69915aa50fc";

        byte[] result = ContractDeployer.hashBytecode(Numeric.hexStringToByteArray(CounterContract.BINARY));
        String resultHex = Numeric.toHexString(result);

        Assertions.assertEquals(expected, resultHex);
    }
}