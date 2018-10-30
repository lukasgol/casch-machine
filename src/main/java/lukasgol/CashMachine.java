package lukasgol;

import lukasgol.account.Account;
import lukasgol.account.AccountService;
import lukasgol.confirmation.Response;
import lukasgol.confirmation.ResponseService;
import lukasgol.exceptions.NotEnoughProperBanknotesException;
import lukasgol.validators.ValidatorResponse;
import lukasgol.validators.WithdrawalValidator;

class CashMachine {

    private ResponseService responseService;
    private AccountService accountService;
    private WithdrawalService withdrawalService;
    private WithdrawalValidator validator;

    CashMachine(WithdrawalService withdrawalService, ResponseService responseService, AccountService accountService, WithdrawalValidator validator) {
        this.withdrawalService = withdrawalService;
        this.responseService = responseService;
        this.accountService = accountService;
        this.validator = validator;
    }

    void withdrawal(Account account, int amount) {

        ValidatorResponse validatorResponse = validator.validate(amount, account.getBalance());
        if (validatorResponse.isOk()) {
            try {
                withdrawalService.execute(amount);
                accountService.updateBalance(account.getBalance() - amount);
                responseService.processResponse(validatorResponse.getResponse());
            } catch (NotEnoughProperBanknotesException e) {
                responseService.processResponse(Response.NOT_ENOUGH_BANKNOTES);
            }
        } else {
            responseService.processResponse(validatorResponse.getResponse());
        }
    }
}
