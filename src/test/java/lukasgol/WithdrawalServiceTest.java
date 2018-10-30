package lukasgol;

import lukasgol.banknotescalculator.BanknotesCalculator;
import lukasgol.state.CashMachineStateService;
import lukasgol.withdrawal.DistributingBanknotesService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class WithdrawalServiceTest {
    private BanknotesAmount banknotesState = new BanknotesAmount(100, 100, 100, 100);
    private BanknotesAmount returnedBanknotesAmount = new BanknotesAmount(1, 1, 1, 1);
    private BanknotesAmount newBanknotesAmount = banknotesState.reduceByAmount(returnedBanknotesAmount);

    private DistributingBanknotesService distributingBanknotesService;
    private CashMachineStateService stateService;
    private WithdrawalService withdrawalService;
    private BanknotesCalculator banknotesCalculator;

    @Before
    public void setUp() {
        distributingBanknotesService = mock(DistributingBanknotesService.class);
        stateService = mock(CashMachineStateService.class);
        when(stateService.getBanknotesState()).thenReturn(banknotesState);
        banknotesCalculator = mock(BanknotesCalculator.class);
        when(banknotesCalculator.calculateBanknotes(100, banknotesState)).thenReturn(returnedBanknotesAmount);
        withdrawalService = new WithdrawalService(distributingBanknotesService, stateService, banknotesCalculator);
    }

    @Test
    public void shouldExecuteServices() {
        withdrawalService.execute(100);
        verify(distributingBanknotesService, times(1)).withdrawal(returnedBanknotesAmount);
        verify(stateService, times(2)).getBanknotesState();
        verify(stateService, times(1)).updateBanknotesState(newBanknotesAmount);
        verify(banknotesCalculator, times(1)).calculateBanknotes(100, banknotesState);
    }
}
