package lukasgol.banknotescalculator;

import lukasgol.BanknotesAmount;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BasicBanknotesCalculatorFactory implements BanknotesCalculatorFactory {

    private final static Map<String, Supplier<BanknotesCalculator>> map = new HashMap<>();
    private static final String DEFAULT_CALCULATOR = "BASIC";

    static {
        map.put("BASIC", BasicBanknotesCalculator::new);
        map.put("SMART", SmartCalculator::new);
    }

    public BanknotesCalculator getCalculator(String calculatorType) {
        Supplier<BanknotesCalculator> calculator = map.get(calculatorType.toUpperCase());
        if (calculator != null) {
            return calculator.get();
        }
        throw new IllegalArgumentException("No such calculator " + calculatorType.toUpperCase());
    }

    public BanknotesCalculator defaultCalculator() {
        return getCalculator(DEFAULT_CALCULATOR);
    }

    @Override
    public BanknotesCalculator chooseCalculator(BanknotesAmount state) {
        return isAgileCalculateRequired() ? getCalculator("SMART") : defaultCalculator();
    }

    public boolean isAgileCalculateRequired() {
        return true;
    }
}
