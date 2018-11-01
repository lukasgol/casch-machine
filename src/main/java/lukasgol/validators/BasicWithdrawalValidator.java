package lukasgol.validators;

import lukasgol.confirmation.Response;

public class BasicWithdrawalValidator implements WithdrawalValidator {

    @Override
    public ValidatorResponse validate(int amount, double balance) {
        if (balance < amount) {
            return new ValidatorResponse(Response.NOT_ENOUGH_ON_ACCOUNT, false);
        } else if (isCorrectAmountToWithdraw(amount)) {
            return new ValidatorResponse(Response.BAD_AMOUNT, false);
        }
        return new ValidatorResponse(Response.OK, true);
    }

    private boolean isCorrectAmountToWithdraw(int amount) {
        return amount <= 0 || amount % 10 != 0;
    }
}
