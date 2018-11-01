package lukasgol;

import lukasgol.account.Account;
import lukasgol.account.AccountService;
import lukasgol.confirmation.Response;
import lukasgol.confirmation.ResponseService;
import lukasgol.exceptions.NotEnoughProperBanknotesException;
import lukasgol.validators.ValidatorResponse;
import lukasgol.validators.WithdrawalValidator;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;


public class CashMachineTest {
    private Account account;
    private WithdrawalService withdrawalService;
    private ResponseService responseService;
    private AccountService accountService;
    private WithdrawalValidator validator;

    private CashMachine cashMachine;

    @Before
    public void setUp() {
        account = new Account(100, "", "");
        accountService = mock(AccountService.class);
        withdrawalService = mock(WithdrawalService.class);
        responseService = mock(ResponseService.class);
        validator = mock(WithdrawalValidator.class);
        cashMachine = new CashMachine(withdrawalService, responseService, accountService, validator);
    }

    @Test
    public void givenValidateResponseErrorShouldReturnResponseError() {
        when(validator.validate(anyInt(), anyDouble())).thenReturn(new ValidatorResponse(Response.BAD_AMOUNT, false));
        int amount = 100;
        cashMachine.withdrawal(account, amount);
        verify(validator, times(1)).validate(anyInt(), anyDouble());
        verifyZeroInteractions(withdrawalService);
        verify(responseService).processResponse(Response.BAD_AMOUNT);
    }

    @Test
    public void givenValidateCorrectResponseShouldReturnCorrectResponse() {
        when(validator.validate(anyInt(), anyDouble())).thenReturn(new ValidatorResponse(Response.OK, true));
        int amount = 100;
        cashMachine.withdrawal(account, amount);
        verify(withdrawalService, times(1)).withdraw(anyInt());
        verify(validator, times(1)).validate(anyInt(), anyDouble());
        verify(accountService, times(1)).updateBalance(anyDouble());
        verify(responseService).processResponse(Response.OK);
    }

    @Test
    public void whenWithdrawalServiceThrowsExceptionShouldReturnErrorResponse() {
        when(validator.validate(anyInt(), anyDouble())).thenReturn(new ValidatorResponse(Response.OK, true));
        int amount = 100;
        doThrow(new NotEnoughProperBanknotesException()).when(withdrawalService).withdraw(anyInt());
        cashMachine.withdrawal(account, amount);
        verify(withdrawalService, times(1)).withdraw(anyInt());
        verify(validator, times(1)).validate(anyInt(), anyDouble());
        verifyZeroInteractions(accountService);
        verify(responseService).processResponse(Response.NOT_ENOUGH_BANKNOTES);
    }
}
