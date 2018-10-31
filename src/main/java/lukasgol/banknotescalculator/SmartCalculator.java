package lukasgol.banknotescalculator;

import lukasgol.BanknotesAmount;
import lukasgol.exceptions.NotEnoughProperBanknotesException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SmartCalculator implements BanknotesCalculator {

    @Override
    public BanknotesAmount calculateBanknotes(int amount, BanknotesAmount banknotesState) {

        List<Banknote> banknotes = Stream.of(new Banknote(100, banknotesState.getNumberOf100()), new Banknote(50, banknotesState.getNumberOf50()), new Banknote(20, banknotesState.getNumberOf20()), new Banknote(10, banknotesState.getNumberOf10()))
                .filter(s -> s.getAmount() > 0)
                .sorted(Comparator.comparing(Banknote::getWeight).reversed())
                .collect(Collectors.toList());

        Map<Integer, Integer> map = tryToFindBanknote(banknotes, amount, new HashMap());
        return new BanknotesAmount(map.getOrDefault(100, 0), map.getOrDefault(50, 0), map.getOrDefault(20, 0), map.getOrDefault(10, 0));
    }

    private Map<Integer, Integer> tryToFindBanknote(List<Banknote> banknotes, int remainedAmount, Map<Integer, Integer> map) {

        if (remainedAmount > 0 && banknotes.isEmpty()) {
            throw new NotEnoughProperBanknotesException();
        } else if (remainedAmount > 0) {
            return findBanknote(banknotes, remainedAmount, map);
        } else {
            return map;
        }
    }

    private Map<Integer, Integer> findBanknote(List<Banknote> banknotes, int remainedAmount, Map<Integer, Integer> resultBanknotes) {
        Banknote banknoteHead = banknotes.subList(0, 1).get(0);
        List<Banknote> banknotesTail = banknotes.subList(1, banknotes.size());

        if (isBanknoteSuitable(remainedAmount, banknoteHead)) {
            return calculateSuitableBanknote(banknoteHead, remainedAmount, banknotesTail, resultBanknotes);
        } else {
            return tryToFindBanknote(banknotesTail, remainedAmount, resultBanknotes);
        }
    }

    private Map<Integer, Integer> calculateSuitableBanknote(Banknote suitableBanknote, int remainedAmount, List<Banknote> restOfBanknotes, Map<Integer, Integer> resultBanknotes) {
        Banknote updatedBanknote = createUpdatedBanknote(suitableBanknote);
        int updatedRemainedAmount = remainedAmount - suitableBanknote.getValue();
        resultBanknotes.put(updatedBanknote.getValue(), resultBanknotes.getOrDefault(updatedBanknote.getValue(), 0) + 1);
        return tryToFindBanknote(concatenateList(restOfBanknotes, updatedBanknote), updatedRemainedAmount, resultBanknotes);
    }

    private List<Banknote> concatenateList(List<Banknote> restOfBanknotes, Banknote updatedBanknote) {
        return Stream.of(restOfBanknotes, Collections.singletonList(updatedBanknote))
                .flatMap(Collection::stream)
                .filter(s -> s.getAmount() > 0)
                .sorted(Comparator.comparing(Banknote::getWeight).reversed())
                .collect(Collectors.toList());
    }

    private Banknote createUpdatedBanknote(Banknote oldBanknote) {
        return new Banknote(oldBanknote.getValue(), oldBanknote.getAmount() - 1);
    }

    private boolean isBanknoteSuitable(int remainedAmount, Banknote banknot) {
        return banknot.getValue() <= remainedAmount && banknot.getAmount() > 0;
    }
}

class Banknote {
    private final int value;
    private final int amount;

    Banknote(int value, int amount) {
        this.value = value;
        this.amount = amount;
    }

    int getWeight() {
        return value * amount;
    }

    int getValue() {
        return value;
    }

    int getAmount() {
        return amount;
    }
}
