package lukasgol.account;

public interface AccountService {
    Account getAccount(int id);

    void updateBalance(double l);
}
