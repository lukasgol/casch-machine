package lukasgol.state;

import lukasgol.BanknotesAmount;

import java.util.Objects;

public class BasicState implements CashMachineStateService {
    private BanknotesAmount banknotesState;

    public BasicState(int numberOf100, int numberOf50, int numberOf20, int numberOf10) {
        this.banknotesState = new BanknotesAmount(numberOf100, numberOf50, numberOf20, numberOf10);
    }

    @Override
    public BanknotesAmount getBanknotesState() {
        return banknotesState;
    }

    @Override
    public void updateBanknotesState(BanknotesAmount state) {
        this.banknotesState = state;
    }
}
