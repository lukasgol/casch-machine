package lukasgol;

import lukasgol.account.Account;
import lukasgol.account.AccountService;
import lukasgol.banknotescalculator.BanknotesCalculatorFactory;
import lukasgol.banknotescalculator.SmartCalculator;
import lukasgol.confirmation.Response;
import lukasgol.confirmation.ResponseService;
import lukasgol.state.BasicState;
import lukasgol.state.CashMachineStateService;
import lukasgol.state.RefillService;
import lukasgol.validators.BasicWithdrawalValidator;
import lukasgol.withdrawal.DistributingBanknotesService;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CashMachineIntegrationTest {
    private ResponseService responseService;
    private AccountService accountService;
    private RefillService refillService;
    private DistributingBanknotesService distributingBanknotesService;
    private BanknotesCalculatorFactory calculatorFactory;

    @Before
    public void setUp() {
        accountService = mock(AccountService.class);
        responseService = mock(ResponseService.class);
        distributingBanknotesService = mock(DistributingBanknotesService.class);
        accountService = mock(AccountService.class);
        responseService = mock(ResponseService.class);
        refillService = mock(RefillService.class);
        calculatorFactory = mock(BanknotesCalculatorFactory.class);
        when(calculatorFactory.chooseCalculator(any(BanknotesAmount.class))).thenReturn(new SmartCalculator());
    }

    @Test
    public void givenCashmachineShouldCorrectlyExecuteThreeOperations() {
        Account account = new Account(1000, "", "");
        accountService = mock(AccountService.class);
        DistributingBanknotesService distributingBanknotesService = mock(DistributingBanknotesService.class);
        WithdrawalService withdrawalService = new WithdrawalService(distributingBanknotesService, new BasicState(new BanknotesAmount(10, 10, 10, 10), refillService), calculatorFactory);
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
        WithdrawalService withdrawalService = new WithdrawalService(distributingBanknotesService, new BasicState(new BanknotesAmount(0, 2, 10, 0), refillService), calculatorFactory);
        CashMachine cashMachine = new CashMachine(withdrawalService, responseService, accountService, new BasicWithdrawalValidator());
        cashMachine.withdrawal(account, 100);
        cashMachine.withdrawal(account, 70);
        verify(distributingBanknotesService).withdrawal(new BanknotesAmount(0, 0, 5, 0));
        verify(distributingBanknotesService).withdrawal(new BanknotesAmount(0, 1, 1, 0));
    }

    @Test
    public void GivenNotEnoughMoneyOnAccountShouldReturnThatResponse() {
        Account account = new Account(10, "", "");
        WithdrawalService withdrawalService = new WithdrawalService(distributingBanknotesService, new BasicState(new BanknotesAmount(0, 2, 10, 0), refillService), calculatorFactory);
        CashMachine cashMachine = new CashMachine(withdrawalService, responseService, accountService, new BasicWithdrawalValidator());
        cashMachine.withdrawal(account, 100);
        cashMachine.withdrawal(account, 70);
        verifyZeroInteractions(distributingBanknotesService);
        verify(responseService, times(2)).processResponse(Response.NOT_ENOUGH_ON_ACCOUNT);
    }


    @Test
    public void x() {
        Account account = new Account(10000000, "", "");
        WithdrawalService withdrawalService = new WithdrawalService(distributingBanknotesService, new BasicState(new BanknotesAmount(0, 0, 0, 10), refillService), calculatorFactory);
        CashMachine cashMachine = new CashMachine(withdrawalService, responseService, accountService, new BasicWithdrawalValidator());
        cashMachine.withdrawal(account, 90);
        cashMachine.withdrawal(account, 100);
        cashMachine.withdrawal(account, 10);
        verify(responseService, times(1)).processResponse(Response.NOT_ENOUGH_BANKNOTES);
        verify(distributingBanknotesService).withdrawal(new BanknotesAmount(0, 0, 0, 9));
        verify(distributingBanknotesService).withdrawal(new BanknotesAmount(0, 0, 0, 1));
    }

    @Test
    public void y() {
        Account account = new Account(90, "", "");
        Account account2 = new Account(2, "", "");
        WithdrawalService withdrawalService = new WithdrawalService(distributingBanknotesService, new BasicState(new BanknotesAmount(0, 0, 0, 10), refillService), calculatorFactory);
        CashMachine cashMachine = new CashMachine(withdrawalService, responseService, accountService, new BasicWithdrawalValidator());
        cashMachine.withdrawal(account, 90);
        cashMachine.withdrawal(account, 100);
        cashMachine.withdrawal(account2, 10);
        cashMachine.withdrawal(new Account(10, "", ""), 10);
        cashMachine.withdrawal(new Account(10, "", ""), 10);
        verify(responseService, times(2)).processResponse(Response.OK);
        verify(responseService, times(2)).processResponse(Response.NOT_ENOUGH_ON_ACCOUNT);
        verify(responseService, times(1)).processResponse(Response.NOT_ENOUGH_BANKNOTES);
        verify(distributingBanknotesService).withdrawal(new BanknotesAmount(0, 0, 0, 9));
        verify(distributingBanknotesService).withdrawal(new BanknotesAmount(0, 0, 0, 1));
    }

    @Test
    public void z() {
        Account account = new Account(1000, "", "");
        CashMachineStateService state = new BasicState(new BanknotesAmount(100, 100, 100, 100), refillService);
        WithdrawalService withdrawalService = new WithdrawalService(distributingBanknotesService, state, calculatorFactory);
        CashMachine cashMachine = new CashMachine(withdrawalService, responseService, accountService, new BasicWithdrawalValidator());
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int amount = random.nextInt(100) * 10;
            cashMachine.withdrawal(account, amount);
            System.out.println("amount: " + amount);
            System.out.println("state: " + state.getBanknotesState());
            if (state.isRefillNeeded()) {
                //state.updateBanknotesState(new BanknotesAmount(100,100,100,100));
                System.out.println("Refill");
            }
        }
    }
}
