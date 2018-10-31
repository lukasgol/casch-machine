package lukasgol.banknotescalculator;

import lukasgol.BanknotesAmount;
import lukasgol.exceptions.NotEnoughProperBanknotesException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SmartCalculatorTest {
    private SmartCalculator calculator;

    @Before
    public void setUp() {
        calculator = new SmartCalculator();
    }

    @Test
    public void shouldReturnOneBanknoteOfEachValue() {
        BanknotesAmount state = new BanknotesAmount(10, 10, 10, 1);
        BanknotesAmount amount = calculator.calculateBanknotes(180, state);
        assertThat(amount, is(new BanknotesAmount(1, 1, 1, 1)));
    }

    @Test
    public void shouldReturnOneBanknoteOf20And10And3Of50Value() {
        BanknotesAmount state = new BanknotesAmount(0, 10, 10, 1);
        BanknotesAmount amount = calculator.calculateBanknotes(180, state);
        assertThat(amount, is(new BanknotesAmount(0, 3, 1, 1)));

    }

    @Test(expected = NotEnoughProperBanknotesException.class)
    public void shouldThrowNotEnoughProperBanknotesExceptionBecauseZero10BanknotesAmount() {
        BanknotesAmount state = new BanknotesAmount(10, 10, 10, 0);
        calculator.calculateBanknotes(180, state);
    }

    @Test(expected = NotEnoughProperBanknotesException.class)
    public void given70AmountShouldThrowNotEnoughProperBanknotesExceptionBecauseNotEnoughBanknotes() {
        BanknotesAmount state = new BanknotesAmount(0, 2, 0, 0);
        calculator.calculateBanknotes(70, state);
    }

    @Test
    public void given70AmountShouldReturnOne50AndOne20() {
        BanknotesAmount state = new BanknotesAmount(0, 2, 1, 5);
        BanknotesAmount amount = calculator.calculateBanknotes(70, state);
        assertThat(amount, is(new BanknotesAmount(0, 1, 0, 2)));
    }

    @Test
    public void given70AmountShouldReturnOne50AndTwo10() {
        BanknotesAmount state = new BanknotesAmount(0, 2, 0, 2);
        BanknotesAmount amount = calculator.calculateBanknotes(70, state);
        assertThat(amount, is(new BanknotesAmount(0, 1, 0, 2)));
    }


    @Test
    public void given70AmountAndSmallAmountOf20ValueBanknotesShouldReturnOne50AndTwo10() {
        BanknotesAmount state = new BanknotesAmount(0, 2, 1, 10);
        BanknotesAmount amount = calculator.calculateBanknotes(70, state);
        assertThat(amount, is(new BanknotesAmount(0, 1, 0, 2)));
    }

    @Test
    public void givenTwoWithdrawalShouldBeAbleToManageBanknotesCorrectly() {
        BanknotesAmount state = new BanknotesAmount(0, 2, 100, 0);
        BanknotesAmount amount = calculator.calculateBanknotes(100, state);
        assertThat(amount, is(new BanknotesAmount(0, 0, 5, 0)));
    }

    @Test
    public void givenLargeAmountOfBanknotes20ShouldUseThemFirst() {
        BanknotesAmount state = new BanknotesAmount(0, 2, 100, 1);
        BanknotesAmount amount = calculator.calculateBanknotes(110, state);
        assertThat(amount, is(new BanknotesAmount(0, 0, 5, 1)));
    }

    @Test(expected = NotEnoughProperBanknotesException.class)
    public void GivenZeroAmountOfBanknotes10And110AmountToWithdrawalShouldThrowNotEnoughProperBanknotesException() {
        BanknotesAmount state = new BanknotesAmount(0, 2, 100, 0);
        BanknotesAmount amount = calculator.calculateBanknotes(110, state);
    }
}