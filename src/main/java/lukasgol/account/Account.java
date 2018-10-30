package lukasgol.account;

import java.util.Objects;

public final class Account {

    private final long balance;
    private final String firstName;
    private final String lastName;

    public Account(long balance, String firstName, String lastName) {
        this.balance = balance;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return balance == account.balance &&
                Objects.equals(firstName, account.firstName) &&
                Objects.equals(lastName, account.lastName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(balance, firstName, lastName);
    }

    public long getBalance() {
        return balance;
    }
}
