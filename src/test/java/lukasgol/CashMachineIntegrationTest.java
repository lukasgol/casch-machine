package lukasgol;

import lukasgol.account.Account;
import lukasgol.account.AccountService;
import lukasgol.banknotescalculator.SmartCalculator;
import lukasgol.confirmation.ResponseService;
import lukasgol.state.BasicState;
import lukasgol.validators.BasicWithdrawalValidator;
import lukasgol.withdrawal.DistributingBanknotesService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CashMachineIntegrationTest {
    private ResponseService responseService;
    private AccountService accountService;

    @Before
    public void setUp() {
        accountService = mock(AccountService.class);
        responseService = mock(ResponseService.class);
    }

    @Test
    public void givenCashmachineShouldCorrectlyExecuteThreeOperations() {
        Account account = new Account(1000, "", "");
        accountService = mock(AccountService.class);
        DistributingBanknotesService distributingBanknotesService = mock(DistributingBanknotesService.class);
        WithdrawalService withdrawalService = new WithdrawalService(distributingBanknotesService, new BasicState(10, 10, 10, 10), new SmartCalculator());
        responseService = mock(ResponseService.class);
        CashMachine cashMachine = new CashMachine(withdrawalService, responseService, accountService, new BasicWithdrawalValidator());
        cashMachine.withdrawal(account, 100);
        cashMachine.withdrawal(account, 200);
        cashMachine.withdrawal(account, 300);
        verify(distributingBanknotesService, times(3)).withdrawal(any(BanknotesAmount.class));
    }
}
