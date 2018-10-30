package lukasgol.banknotescalculator;

import lukasgol.BanknotesAmount;
import lukasgol.exceptions.NotEnoughProperBanknotesException;

public class BasicBanknotesCalculator implements BanknotesCalculator {
    private final int[] BANKNOTE_VALUES = {100, 50, 20, 10};
    private final int BANKNOTES_VALUES_AMOUNT = BANKNOTE_VALUES.length;

    public BanknotesAmount calculateBanknotes(int amount, int[] banknotesNumber) {
        int[] requiredBanknotes = new int[4];
        int remainedAmount = amount;
        for (int i = 0; i < BANKNOTES_VALUES_AMOUNT; i++) {
            if (banknotesNumber[i] <= remainedAmount) {
                int neededBanknotesAmount= remainedAmount / BANKNOTE_VALUES[i];
                if (hasEnoughBanknotesOfCurrentValue(neededBanknotesAmount, banknotesNumber[i])) {
                    if (isChecking10ValueBanknotes(i)) {
                        throw new NotEnoughProperBanknotesException();
                    }
                    neededBanknotesAmount = banknotesNumber[i];
                }
                requiredBanknotes[i] = neededBanknotesAmount;
                remainedAmount -= neededBanknotesAmount * BANKNOTE_VALUES[i];
            }
        }
        return new BanknotesAmount(requiredBanknotes);
    }

    private boolean hasEnoughBanknotesOfCurrentValue(int neededBanknotesAmount, int banknotAmount) {
        return neededBanknotesAmount > banknotAmount;
    }

    private boolean isChecking10ValueBanknotes(int i) {
        return BANKNOTES_VALUES_AMOUNT - 1 == i;
    }

    @Override
    public BanknotesAmount calculateBanknotes(int amount, BanknotesAmount banknotesState) {
        return calculateBanknotes(amount, banknotesState.toArray());
    }
}
