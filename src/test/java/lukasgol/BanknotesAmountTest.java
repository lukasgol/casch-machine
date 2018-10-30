package lukasgol;

import lukasgol.exceptions.NegativeBanknotesAmountException;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class BanknotesAmountTest {
    @Test
    public void shouldHaveZeroAmountAfterReduce() {
        BanknotesAmount banknotesAmount = new BanknotesAmount(10, 10, 10, 10);

        BanknotesAmount amountAfterReduce = banknotesAmount.reduceByAmount(new BanknotesAmount(10, 10, 10, 10));

        assertThat(amountAfterReduce, is(new BanknotesAmount(0, 0, 0, 0)));
    }

    @Test(expected = NegativeBanknotesAmountException.class)
    public void shouldThrowExceptionWhenBanknotes10AmountIsLessThanZero() {
        BanknotesAmount banknotesAmount = new BanknotesAmount(10, 10, 10, 10);

        banknotesAmount.reduceByAmount(new BanknotesAmount(10, 10, 10, 20));
    }

    @Test(expected = NegativeBanknotesAmountException.class)
    public void shouldThrowExceptionWhenBanknotes20AmountIsLessThanZero() {
        BanknotesAmount banknotesAmount = new BanknotesAmount(10, 10, 10, 10);

        banknotesAmount.reduceByAmount(new BanknotesAmount(10, 10, 20, 10));
    }

    @Test(expected = NegativeBanknotesAmountException.class)
    public void shouldThrowExceptionWhenBanknotes50AmountIsLessThanZero() {
        BanknotesAmount banknotesAmount = new BanknotesAmount(10, 10, 10, 10);

        banknotesAmount.reduceByAmount(new BanknotesAmount(10, 20, 10, 10));
    }

    @Test(expected = NegativeBanknotesAmountException.class)
    public void shouldThrowExceptionWhenBanknotes100AmountIsLessThanZero() {
        BanknotesAmount banknotesAmount = new BanknotesAmount(10, 10, 10, 10);

        banknotesAmount.reduceByAmount(new BanknotesAmount(20, 10, 10, 10));
    }


    @Test
    public void shouldReturnCorrectBanknotesValueSum() {
        BanknotesAmount banknotesAmount = new BanknotesAmount(10, 1, 3, 10);

        int sum = banknotesAmount.getBanknotesValue();

        assertEquals(sum, 1210);
    }
}
