package models;

import utils.Constants;
import utils.FileHandler;

public class PlatinumAccount extends BankAccount {

    public static final String TYPE = "Platinum";
    public static final double PRICE = 500.0;
    public static final int TRANSACTIONS_PER_MONTH = Integer.MAX_VALUE;
    public static final double DEBT_LIMIT = 2000.0;

    public PlatinumAccount(String ownerId, String ownerFirstName, String ownerLastName, double balance) {
        super(ownerId, ownerFirstName, ownerLastName, TYPE, balance);
    }

    public PlatinumAccount(String accountId) {
        super(accountId);
    }

    @Override
    public String deposit(double money) {
        String result = FileHandler.changeBalance(accountId, money);
        this.balance += money;
        return result;
    }

    @Override
    public String withdraw(double money) {
        final double oldBalance = FileHandler.getBalance(accountId);
        if (money > oldBalance + DEBT_LIMIT) {
            return Constants.NO_ENOUGH_MONEY_MESSAGE;
        }
        String result = FileHandler.changeBalance(accountId, -money);
        this.balance -= money;
        return result;
    }
}
