package lukasgol.state;

import lukasgol.BanknotesAmount;

public class BasicState implements CashMachineStateService {
    private BanknotesAmount banknotesState;
    private RefillService refillService;

    public BasicState(BanknotesAmount banknotesAmount, RefillService refillService) {
        this.banknotesState = banknotesAmount;
        this.refillService = refillService;
    }

    @Override
    public BanknotesAmount getBanknotesState() {
        return banknotesState;
    }

    @Override
    public void updateBanknotesState(BanknotesAmount state) {
        this.banknotesState = state;
        if (isRefillNeeded()) {
            refillService.notifyRefill();
        }
    }

}
