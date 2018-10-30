package lukasgol.banknotescalculator;

import lukasgol.BanknotesAmount;

public interface BanknotesCalculator {

    BanknotesAmount calculateBanknotes(int amount, BanknotesAmount banknotesState);
}
