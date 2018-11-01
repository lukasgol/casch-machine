package lukasgol.state;

import lukasgol.BanknotesAmount;

public interface CashMachineStateService {

    int MIN_WARNING_BANKNOTES_STATE_VALUE = 1000;

    BanknotesAmount getBanknotesState();

    void updateBanknotesState(BanknotesAmount state);


    default boolean isRefillNeeded() {
        return getBanknotesState().getBanknotesValue() < MIN_WARNING_BANKNOTES_STATE_VALUE;
    }
}
