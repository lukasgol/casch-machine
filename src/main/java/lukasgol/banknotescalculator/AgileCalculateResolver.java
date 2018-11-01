package lukasgol.banknotescalculator;

import lukasgol.BanknotesAmount;
import lukasgol.Configuration;

class AgileCalculateResolver {
    private final BanknotesAmount state;

    AgileCalculateResolver(BanknotesAmount state) {
        this.state = state;
    }

    boolean resolve() {
        return state.getBanknotesValue() < Configuration.MIN_BANKNOTES_VALUE_REQUIRED || isBanknoteStateUnbalanced(state);
    }

    private boolean isBanknoteStateUnbalanced(BanknotesAmount state) {
        int valueOf10 = state.getNumberOf10() * 10;
        int valueOf20 = state.getNumberOf20() * 20;
        int valueOf50 = state.getNumberOf50() * 50;
        int valueOf100 = state.getNumberOf100() * 100;

        return valueOf100*10 < valueOf10  || valueOf100*5 < valueOf20 || valueOf100*4 < valueOf50 || valueOf50*3 < valueOf20 || valueOf50*5 < valueOf10 || valueOf20*2 < valueOf10 ;
    }
}
