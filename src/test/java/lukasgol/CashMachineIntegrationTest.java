package lukasgol;

import lukasgol.account.Account;
import lukasgol.account.AccountService;
import lukasgol.banknotescalculator.SmartCalculator;
import lukasgol.confirmation.Response;
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
    private DistributingBanknotesService distributingBanknotesService;

    @Before
    public void setUp() {
        accountService = mock(AccountService.class);
        responseService = mock(ResponseService.class);
        distributingBanknotesService = mock(DistributingBanknotesService.class);
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

    @Test
    public void GivenTwoOperationShouldWithdrawalFive20AndNextOne50andOne20() {
        Account account = new Account(2000, "", "");
        WithdrawalService withdrawalService = new WithdrawalService(distributingBanknotesService, new BasicState(0, 2, 10, 0), new SmartCalculator());
        CashMachine cashMachine = new CashMachine(withdrawalService, responseService, accountService, new BasicWithdrawalValidator());
        cashMachine.withdrawal(account, 100);
        cashMachine.withdrawal(account, 70);
        verify(distributingBanknotesService).withdrawal(new BanknotesAmount(0,0,5,0));
        verify(distributingBanknotesService).withdrawal(new BanknotesAmount(0,1,1,0));
    }

    @Test
    public void GivenNotEnoughMoneyOnAccountShouldReturnThatResponse() {
        Account account = new Account(10, "", "");
        WithdrawalService withdrawalService = new WithdrawalService(distributingBanknotesService, new BasicState(0, 2, 10, 0), new SmartCalculator());
        CashMachine cashMachine = new CashMachine(withdrawalService, responseService, accountService, new BasicWithdrawalValidator());
        cashMachine.withdrawal(account, 100);
        cashMachine.withdrawal(account, 70);
        verifyZeroInteractions(distributingBanknotesService);
        verify(responseService,times(2)).processResponse(Response.NOT_ENOUGH_ON_ACCOUNT);
    }
}
