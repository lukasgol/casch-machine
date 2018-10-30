package lukasgol;

import lukasgol.state.BasicState;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BasicStateTest {

    @Test
    public void shouldHaveCorrectStateAfterUpdate() {
        BanknotesAmount newAmount = new BanknotesAmount(20, 20, 20, 20);
        BasicState state = new BasicState(10, 10, 10, 10);

        state.updateBanknotesState(newAmount);

        BanknotesAmount expectedStateAmount = new BanknotesAmount(20, 20, 20, 20);
        assertThat(state.getBanknotesState(), is(expectedStateAmount));
    }
}
