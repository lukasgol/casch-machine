package lukasgol.state;

import lukasgol.BanknotesAmount;

public interface CashMachineStateService {
    BanknotesAmount getBanknotesState();
    void updateBanknotesState(BanknotesAmount state);
}
