package lukasgol.banknotescalculator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BanknotesCalculatorFactory {

    private final static Map<String, Supplier<BanknotesCalculator>> map = new HashMap<>();
    public static final String DEFAULT_CALCULATOR = "BASIC";

    static {
        map.put("BASIC", BasicBanknotesCalculator::new);
        map.put("SMART", SmartCalculator::new);
    }

    private BanknotesCalculator getCalculator(String calculatorType) {
        Supplier<BanknotesCalculator> calculator = map.get(calculatorType.toUpperCase());
        if (calculator != null) {
            return calculator.get();
        }
        throw new IllegalArgumentException("No such shape " + calculatorType.toUpperCase());
    }

    public BanknotesCalculator defaultCalculator() {
        return getCalculator(DEFAULT_CALCULATOR);
    }
}
