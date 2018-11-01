package lukasgol.state;

import lukasgol.BanknotesAmount;

public interface CashMachineStateService {

    default int getMinState() {
        return 1000;
    }

    BanknotesAmount getBanknotesState();

    void updateBanknotesState(BanknotesAmount state);


    default boolean isRefillNeeded() {
        return getBanknotesState().getBanknotesValue() < getMinState();
    }
}
