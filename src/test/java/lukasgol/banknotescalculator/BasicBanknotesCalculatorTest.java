package lukasgol.banknotescalculator;

import lukasgol.BanknotesAmount;
import lukasgol.exceptions.NotEnoughProperBanknotesException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BasicBanknotesCalculatorTest {
    private BasicBanknotesCalculator calculator;

    @Before
    public void setUp() {
        calculator = new BasicBanknotesCalculator();
    }

    @Test
    public void shouldReturnOneBanknoteOfEachValue() {
        int[] state = new int[]{10, 10, 10, 1};
        BanknotesAmount amount = calculator.calculateBanknotes(180, state);
        assertThat(amount, is(new BanknotesAmount(1, 1, 1, 1)));
    }

    @Test
    public void shouldReturnOneBanknoteOf20And10And3Of50Value() {
        int[] state = new int[]{0, 10, 10, 1};
        BanknotesAmount amount = calculator.calculateBanknotes(180, state);
        assertThat(amount, is(new BanknotesAmount(0, 3, 1, 1)));

    }

    @Test(expected = NotEnoughProperBanknotesException.class)
    public void shouldThrowNotEnoughProperBanknotesExceptionBecauseZero10BanknotesAmount() {
        int[] state = new int[]{10, 10, 10, 0};
        calculator.calculateBanknotes(180, state);
    }

    @Test(expected = NotEnoughProperBanknotesException.class)
    public void given70AmountShouldThrowNotEnoughProperBanknotesExceptionBecauseNotEnoughBanknotes() {
        int[] state = new int[]{0, 2, 0, 0};
        calculator.calculateBanknotes(70, state);
    }

    @Test()
    public void given70AmountShouldReturnOne50AndOne20() {
        int[] state = new int[]{0, 2, 1, 5};
        BanknotesAmount amount = calculator.calculateBanknotes(70, state);
        assertThat(amount, is(new BanknotesAmount(0, 1, 1, 0)));
    }

    @Test()
    public void given70AmountShouldReturnOne50AndTwo10() {
        int[] state = new int[]{0, 2, 0, 2};
        BanknotesAmount amount = calculator.calculateBanknotes(70, state);
        assertThat(amount, is(new BanknotesAmount(0, 1, 0, 2)));
    }

    @Test()
    public void given70AmountAndSmallAmountOf20ValueBanknotesShouldReturnOne50AndTwo10() {
        int[] state = new int[]{0, 2, 1, 0};
        BanknotesAmount amount = calculator.calculateBanknotes(70, state);
        assertThat(amount, is(new BanknotesAmount(0, 1, 1, 0)));
    }

    @Test()
    public void givenAmountOfBanknoteShouldReturnCorrectAmount() {
        int[] state = new int[]{0, 2, 100, 0};
        BanknotesAmount amount = calculator.calculateBanknotes(70, state);
        assertThat(amount, is(new BanknotesAmount(0, 1, 1, 0)));
    }
}
