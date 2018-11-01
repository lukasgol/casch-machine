package lukasgol.domain;

import lukasgol.BanknotesAmount;
import lukasgol.state.BasicState;
import lukasgol.state.RefillService;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class BasicStateTest {

    private RefillService refillService;

    @Before
    public void setUp() throws Exception {
        refillService = mock(RefillService.class);

    }

    @Test
    public void shouldHaveCorrectStateAfterUpdate() {
        BanknotesAmount newAmount = new BanknotesAmount(20, 20, 20, 20);
        BasicState state = new BasicState(new BanknotesAmount(10, 10, 10, 10), refillService);

        state.updateBanknotesState(newAmount);

        BanknotesAmount expectedStateAmount = new BanknotesAmount(20, 20, 20, 20);
        assertThat(state.getBanknotesState(), is(expectedStateAmount));
    }

    @Test
    public void givenSmallStateShouldInvokeRefill() {
        BanknotesAmount newAmount = new BanknotesAmount(0, 0, 0, 0);
        BasicState state = new BasicState(new BanknotesAmount(0, 0, 0, 0), refillService);

        state.updateBanknotesState(newAmount);

        verify(refillService).notifyRefill();
    }

    @Test
    public void givenLargeStateShouldNotInvokeRefill() {
        BanknotesAmount newAmount = new BanknotesAmount(10, 10, 10, 10);
        BasicState state = new BasicState(new BanknotesAmount(0, 0, 0, 0), refillService);

        state.updateBanknotesState(newAmount);

        verifyZeroInteractions(refillService);
    }
}
