package lukasgol;

import lukasgol.banknotescalculator.BanknotesCalculator;
import lukasgol.state.CashMachineStateService;
import lukasgol.withdrawal.DistributingBanknotesService;

public class WithdrawalService {
    private final DistributingBanknotesService distributingBanknotesService;
    private final CashMachineStateService stateService;
    private final BanknotesCalculator banknotesCalculator;

    WithdrawalService(DistributingBanknotesService distributingBanknotesService, CashMachineStateService stateService, BanknotesCalculator banknotesCalculator) {
        this.distributingBanknotesService = distributingBanknotesService;
        this.stateService = stateService;
        this.banknotesCalculator = banknotesCalculator;
    }

    public void execute(int amount) {
        BanknotesAmount banknotesAmount = banknotesCalculator.calculateBanknotes(amount, stateService.getBanknotesState());
        distributingBanknotesService.withdrawal(banknotesAmount);
        reduceBanknotesStateBy(banknotesAmount);
    }

    private void reduceBanknotesStateBy(BanknotesAmount banknotesAmount) {
        BanknotesAmount state = stateService.getBanknotesState();
        stateService.updateBanknotesState(state.reduceByAmount(banknotesAmount));
    }

}
