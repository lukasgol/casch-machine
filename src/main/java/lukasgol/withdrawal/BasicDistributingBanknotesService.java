package lukasgol.withdrawal;

import lukasgol.BanknotesAmount;

public class BasicDistributingBanknotesService implements DistributingBanknotesService {


    @Override
    public void withdrawal(BanknotesAmount banknotesAmount) {
        System.out.println("Withdrawing banknotes: " + banknotesAmount.toString());
    }
}
