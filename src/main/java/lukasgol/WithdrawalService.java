package lukasgol;

import lukasgol.banknotescalculator.BanknotesCalculatorFactory;
import lukasgol.exceptions.TooManyBanknotesToWithdrawalException;
import lukasgol.state.CashMachineStateService;
import lukasgol.withdrawal.DistributingBanknotesService;

class WithdrawalService {
    public static final int MAX_BANKNOTES_TO_WITHDRAWAL = 100;
    private final DistributingBanknotesService distributingBanknotesService;
    private final CashMachineStateService stateService;
    private final BanknotesCalculatorFactory basicBanknotesCalculatorFactory;

    WithdrawalService(DistributingBanknotesService distributingBanknotesService, CashMachineStateService stateService, BanknotesCalculatorFactory basicBanknotesCalculatorFactory) {
        this.distributingBanknotesService = distributingBanknotesService;
        this.stateService = stateService;
        this.basicBanknotesCalculatorFactory = basicBanknotesCalculatorFactory;
    }

    void execute(int amount) {
        BanknotesAmount state = stateService.getBanknotesState();
        BanknotesAmount banknotesAmount = calculateBanknotes(amount, state);
        if (banknotesAmount.banknotesAmount() > MAX_BANKNOTES_TO_WITHDRAWAL) {
            throw new TooManyBanknotesToWithdrawalException();
        }
        distributingBanknotesService.withdrawal(banknotesAmount);
        reduceBanknotesStateBy(banknotesAmount);

    }

    private BanknotesAmount calculateBanknotes(int amount, BanknotesAmount state) {
        return basicBanknotesCalculatorFactory.chooseCalculator(state).calculateBanknotes(amount, state);
    }

    private void reduceBanknotesStateBy(BanknotesAmount banknotesAmount) {
        BanknotesAmount state = stateService.getBanknotesState();
        stateService.updateBanknotesState(state.reduceByAmount(banknotesAmount));
    }

    public BanknotesAmount getState() {
        return stateService.getBanknotesState();
    }
}
