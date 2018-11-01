package lukasgol.banknotescalculator;

import lukasgol.BanknotesAmount;

public interface BanknotesCalculatorFactory {
    BanknotesCalculator chooseCalculator(BanknotesAmount state);

}
