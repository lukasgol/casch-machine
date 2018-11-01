package lukasgol.validators;

import lukasgol.Configuration;
import lukasgol.confirmation.Response;

public class BasicWithdrawalValidator implements WithdrawalValidator {

    private static final int MINIMAL_BANKNOTE_VALUE = 10;

    @Override
    public ValidatorResponse validate(int amount, double balance) {
        if (amount > Configuration.MAX_AMOUNT_TO_WITHDRAWAL) {
            return new ValidatorResponse(Response.TO0_BIG_AMOUNT_TO_WITHDRAWAL, false);
        }
        if (balance < amount) {
            return new ValidatorResponse(Response.NOT_ENOUGH_ON_ACCOUNT, false);
        } else if (isCorrectAmountToWithdraw(amount)) {
            return new ValidatorResponse(Response.BAD_AMOUNT, false);
        }
        return new ValidatorResponse(Response.OK, true);
    }

    private boolean isCorrectAmountToWithdraw(int amount) {
        return amount <= 0 || amount % MINIMAL_BANKNOTE_VALUE != 0;
    }
}
