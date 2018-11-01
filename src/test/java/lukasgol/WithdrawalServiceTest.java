package lukasgol;

import lukasgol.banknotescalculator.BanknotesCalculator;
import lukasgol.banknotescalculator.BanknotesCalculatorFactory;
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
    private BanknotesCalculatorFactory banknotesCalculatorFactory;
    private BanknotesCalculator banknotesCalculator;

    @Before
    public void setUp() {
        distributingBanknotesService = mock(DistributingBanknotesService.class);
        stateService = mock(CashMachineStateService.class);
        when(stateService.getBanknotesState()).thenReturn(banknotesState);
        banknotesCalculatorFactory = mock(BanknotesCalculatorFactory.class);
        banknotesCalculator = mock(BanknotesCalculator.class);
        when(banknotesCalculatorFactory.chooseCalculator(any(BanknotesAmount.class))).thenReturn(banknotesCalculator);
        when(banknotesCalculator.calculateBanknotes(100, banknotesState)).thenReturn(returnedBanknotesAmount);
        withdrawalService = new WithdrawalService(distributingBanknotesService, stateService, banknotesCalculatorFactory);
    }

    @Test
    public void shouldExecuteServices() {
        withdrawalService.withdraw(100);
        verify(distributingBanknotesService, times(1)).withdrawal(returnedBanknotesAmount);
        verify(stateService, times(2)).getBanknotesState();
        verify(stateService, times(1)).updateBanknotesState(newBanknotesAmount);
        verify(banknotesCalculator, times(1)).calculateBanknotes(100, banknotesState);
    }
}
