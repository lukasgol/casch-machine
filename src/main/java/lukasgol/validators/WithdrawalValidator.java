package lukasgol.validators;

public interface WithdrawalValidator {

    ValidatorResponse validate(int amount, long balance);
}
