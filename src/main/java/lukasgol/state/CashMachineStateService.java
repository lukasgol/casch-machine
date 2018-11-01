package lukasgol.state;

import lukasgol.BanknotesAmount;
import lukasgol.Configuration;

public interface CashMachineStateService {

    default int getMinState() {
        return Configuration.MIN_BANKNOTES_VALUE_REQUIRED;
    }

    BanknotesAmount getBanknotesState();

    void updateBanknotesState(BanknotesAmount state);


    default boolean isRefillNeeded() {
        return getBanknotesState().getBanknotesValue() < getMinState();
    }
}
