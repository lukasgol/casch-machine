package lukasgol;

import lukasgol.account.Account;
import lukasgol.account.AccountService;
import lukasgol.banknotescalculator.BanknotesCalculatorFactory;
import lukasgol.banknotescalculator.BasicBanknotesCalculatorFactory;
import lukasgol.confirmation.BasicResponseService;
import lukasgol.confirmation.ResponseService;
import lukasgol.state.BasicState;
import lukasgol.state.CashMachineStateService;
import lukasgol.state.RefillService;
import lukasgol.validators.BasicWithdrawalValidator;
import lukasgol.withdrawal.BasicDistributingBanknotesService;
import lukasgol.withdrawal.DistributingBanknotesService;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.mockito.Mockito.mock;

public class ManyWithdrawalTest {
    private ResponseService responseService;
    private AccountService accountService;
    private RefillService refillService;
    private DistributingBanknotesService distributingBanknotesService;
    private BanknotesCalculatorFactory calculatorFactory;


    @Before
    public void setUp() {
        accountService = mock(AccountService.class);
        responseService = new BasicResponseService();
        distributingBanknotesService = new BasicDistributingBanknotesService();
        accountService = mock(AccountService.class);
        refillService = () -> System.out.println("Notify Refill");
        calculatorFactory = new BasicBanknotesCalculatorFactory();
    }

    @Test
    public void shouldSimulateWithdrawal() {
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
                state.updateBanknotesState(new BanknotesAmount(100, 100, 100, 100));
                System.out.println("Refiling...");
            }
        }
    }

    @Test
    public void shouldSimulateWithdrawalWithRandomAccount() {
        CashMachineStateService state = new BasicState(new BanknotesAmount(100, 100, 100, 100), refillService);
        WithdrawalService withdrawalService = new WithdrawalService(distributingBanknotesService, state, calculatorFactory);
        CashMachine cashMachine = new CashMachine(withdrawalService, responseService, accountService, new BasicWithdrawalValidator());
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int amount = random.nextInt(100) * 10;
            Account account = new Account(random.nextDouble() * 10000, "", "");
            System.out.println("amount: " + account.toString());
            System.out.println("amount: " + amount);
            System.out.println("state: " + state.getBanknotesState());
            cashMachine.withdrawal(account, amount);
            if (state.isRefillNeeded()) {
                state.updateBanknotesState(new BanknotesAmount(100, 100, 100, 100));
                System.out.println("Refiling...");
            }
        }
    }
}
