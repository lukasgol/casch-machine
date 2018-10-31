package lukasgol;

import lukasgol.exceptions.NegativeBanknotesAmountException;

import java.util.Objects;

public class BanknotesAmount {
    private final int numberOf100;
    private final int numberOf50;
    private final int numberOf20;
    private final int numberOf10;

    public BanknotesAmount(int numberOf100, int numberOf50, int numberOf20, int numberOf10) {
        if (numberOf10 < 0 || numberOf20 < 0 || numberOf50 < 0 || numberOf100 < 0) {
            throw new NegativeBanknotesAmountException();
        }
        this.numberOf100 = numberOf100;
        this.numberOf50 = numberOf50;
        this.numberOf20 = numberOf20;
        this.numberOf10 = numberOf10;
    }

    public BanknotesAmount(int[] banknotes) {
        this(banknotes[0], banknotes[1], banknotes[2], banknotes[3]);
    }

    public int getNumberOf10() {
        return numberOf10;
    }

    public int getNumberOf20() {
        return numberOf20;
    }

    public int getNumberOf50() {
        return numberOf50;
    }

    public int getNumberOf100() {
        return numberOf100;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BanknotesAmount that = (BanknotesAmount) o;
        return numberOf100 == that.numberOf100 &&
                numberOf50 == that.numberOf50 &&
                numberOf20 == that.numberOf20 &&
                numberOf10 == that.numberOf10;
    }

    @Override
    public int hashCode() {

        return Objects.hash(numberOf100, numberOf50, numberOf20, numberOf10);
    }

    @Override
    public String toString() {
        return "BanknotesAmount{" +
                "numberOf100=" + numberOf100 +
                ", numberOf50=" + numberOf50 +
                ", numberOf20=" + numberOf20 +
                ", numberOf10=" + numberOf10 +
                '}';
    }

    public int[] toArray() {
        return new int[]{numberOf100, numberOf50, numberOf20, numberOf10};
    }

    public BanknotesAmount reduceByAmount(BanknotesAmount amount) {
        int numberOf100 = this.numberOf100 - amount.getNumberOf100();
        int numberOf50 = this.numberOf50 - amount.getNumberOf50();
        int numberOf20 = this.numberOf20 - amount.getNumberOf20();
        int numberOf10 = this.numberOf10 - amount.getNumberOf10();
        return new BanknotesAmount(numberOf100, numberOf50, numberOf20, numberOf10);
    }

    public int getBanknotesValue() {
        return numberOf100 * 100 + numberOf50 * 50 + numberOf20 * 20 + numberOf10 * 10;
    }
}
