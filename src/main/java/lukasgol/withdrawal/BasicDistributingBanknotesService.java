package lukasgol.withdrawal;

import lukasgol.BanknotesAmount;

public class BasicDistributingBanknotesService implements DistributingBanknotesService {


    @Override
    public void withdrawal(BanknotesAmount banknotesAmount) {
        System.out.print("Withdrawing banknotes: " + banknotesAmount.toString());
    }
}
