package lukasgol.banknotescalculator;

import lukasgol.BanknotesAmount;
import org.junit.Test;

import static org.junit.Assert.*;

public class AgileCalculateResolverTest {

    @Test
    public void givenLargeAmountOfBanknotesAnyKindShouldReturnFalse() {
        assertFalse(new AgileCalculateResolver(new BanknotesAmount(10,10,10,10)).resolve());
    }

    @Test
    public void givenAmountOfBanknotesUnderConfShouldReturnFalse() {
        assertTrue(new AgileCalculateResolver(new BanknotesAmount(1,1,1,1)).resolve());
    }

    @Test
    public void givenTen100AndOne10ShouldReturnFalse() {
        assertFalse(new AgileCalculateResolver(new BanknotesAmount(10,10,10,1)).resolve());
    }

    @Test
    public void givenTen10AndOne100ShouldReturnTrue() {
        assertTrue(new AgileCalculateResolver(new BanknotesAmount(1,10,10,100)).resolve());
    }
}
