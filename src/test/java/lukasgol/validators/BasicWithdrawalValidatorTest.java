package lukasgol.validators;

import lukasgol.confirmation.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BasicWithdrawalValidatorTest {
    private WithdrawalValidator validator;

    @Before
    public void setUp() {
        validator = new BasicWithdrawalValidator();

    }

    @Test
    public void givenGreaterAmountThanBalanceShouldReturnFalse() {
        ValidatorResponse validatorResponse = validator.validate(10, 1);
        assertThat(validatorResponse, is(new ValidatorResponse(Response.NOT_ENOUGH_ON_ACCOUNT, false)));
    }

    @Test
    public void givenEqualsAmountAndBalanceShouldReturnTrue() {
        ValidatorResponse validatorResponse = validator.validate(10, 10);
        assertThat(validatorResponse, is(new ValidatorResponse(Response.OK, true)));
    }

    @Test
    public void givenGraterBalanceThanAmountShouldReturnTrue() {
        ValidatorResponse validatorResponse = validator.validate(10, 100);
        assertThat(validatorResponse, is(new ValidatorResponse(Response.OK, true)));
    }

    @Test
    public void givenNegativeAmountShouldReturnFalse() {
        ValidatorResponse validatorResponse = validator.validate(-10, 100);
        assertThat(validatorResponse, is(new ValidatorResponse(Response.BAD_AMOUNT, false)));
    }

    @Test
    public void givenZeroAmountShouldReturnFalse() {
        ValidatorResponse validatorResponse = validator.validate(0, 100);
        assertThat(validatorResponse, is(new ValidatorResponse(Response.BAD_AMOUNT, false)));
    }

    @Test
    public void givenAmountNotDividedBy10ShouldReturnFalse() {
        ValidatorResponse validatorResponse = validator.validate(123, 1000);
        assertThat(validatorResponse, is(new ValidatorResponse(Response.BAD_AMOUNT, false)));
    }
}
