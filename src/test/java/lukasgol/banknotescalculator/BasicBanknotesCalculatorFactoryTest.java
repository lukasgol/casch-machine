package lukasgol.banknotescalculator;

import lukasgol.BanknotesAmount;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BasicBanknotesCalculatorFactoryTest {

    BanknotesCalculatorFactory calculatorFactory;

    @Before
    public void setUp() throws Exception {
        calculatorFactory = new BasicBanknotesCalculatorFactory();
    }

    @Test
    public void GivenLargeAmountOfAllBanknotesShouldReturnBasic() {
        assertThat(calculatorFactory.chooseCalculator(new BanknotesAmount(100, 100, 100, 100)), is(new BasicBanknotesCalculator()));
    }
}
