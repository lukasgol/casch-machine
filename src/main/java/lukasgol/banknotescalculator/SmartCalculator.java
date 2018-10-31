package lukasgol.banknotescalculator;

import lukasgol.BanknotesAmount;
import lukasgol.exceptions.NotEnoughProperBanknotesException;

import java.util.*;
import java.util.stream.Collectors;

public class SmartCalculator implements BanknotesCalculator {
    private final int[] BANKNOTE_VALUES = {100, 50, 20, 10};
    private final int BANKNOTES_VALUES_AMOUNT = BANKNOTE_VALUES.length;

    public BanknotesAmount calculateBanknotes(int amount, int[] banknotesNumber) {
        /*        List<Item> banknotes = Collections.unmodifiableList(Arrays.asList(new Item(100, banknotesNumber[0]), new Item(50, banknotesNumber[1]), new Item(20, banknotesNumber[2]), new Item(10, banknotesNumber[3])));*/
        List<Item> banknotes = Arrays.asList(new Item(100, banknotesNumber[0]), new Item(50, banknotesNumber[1]), new Item(20, banknotesNumber[2]), new Item(10, banknotesNumber[3]));


        int remainedAmount = amount;
        Map<Integer, Integer> map = recursive(banknotes, remainedAmount, new HashMap());
        return new BanknotesAmount(map.getOrDefault(100,0), map.getOrDefault(50,0), map.getOrDefault(20,0), map.getOrDefault(10,0));
    }

    private Map<Integer, Integer> recursive(List<Item> banknotes, int remainedAmount, Map<Integer, Integer> map) {

        if (remainedAmount > 0) {
            if(banknotes.isEmpty()){
                throw new NotEnoughProperBanknotesException();
            }
            banknotes.sort(Comparator.comparing(Item::getWeight).reversed());
            Item head = (Item) banknotes.iterator().next();
            List<Item> tail = banknotes.subList(1, banknotes.size());
            if (head.getValue() <= remainedAmount&&head.getAmount()>0) {
                Item newItem = new Item(head.getValue(), head.getAmount() - 1, head.getRequiredAmountToWithdrawl() + 1);
                remainedAmount -= head.getValue();
                ArrayList<Item> a =  new ArrayList<Item>();
                a.addAll(tail);
                a.add(newItem);
                map.put(newItem.getValue(),map.getOrDefault(newItem.getValue(),0)+1);
                return recursive(a,remainedAmount, map);
            } else {
                return recursive(tail, remainedAmount, map);
            }
        }
        return map;
    }

    @Override
    public BanknotesAmount calculateBanknotes(int amount, BanknotesAmount banknotesState) {
        return calculateBanknotes(amount, banknotesState.toArray());
    }
}

class Item {
    private final int value;
    private final int amount;

    public int getRequiredAmountToWithdrawl() {
        return requiredAmountToWithdrawl;
    }

    private final int requiredAmountToWithdrawl;

    public Item(int value, int amount) {
        this.value = value;
        this.amount = amount;
        this.requiredAmountToWithdrawl = 0;
    }

    public Item(int value, int amount, int requiredAmountToWithdrawl) {
        this.value = value;
        this.amount = amount;
        this.requiredAmountToWithdrawl = requiredAmountToWithdrawl;
    }

    public int getWeight() {
        return value * amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return value == item.value &&
                amount == item.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, amount);
    }

    public int getValue() {
        return value;
    }

    public int getAmount() {
        return amount;
    }
}
