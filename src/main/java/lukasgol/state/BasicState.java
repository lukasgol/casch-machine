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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicState that = (BasicState) o;
        return Objects.equals(banknotesState, that.banknotesState);
    }

    @Override
    public int hashCode() {

        return Objects.hash(banknotesState);
    }
}
