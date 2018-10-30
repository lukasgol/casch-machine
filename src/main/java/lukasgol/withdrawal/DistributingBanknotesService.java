package lukasgol.withdrawal;

import lukasgol.BanknotesAmount;

public interface DistributingBanknotesService {
    void withdrawal(BanknotesAmount banknotesAmount);
}
