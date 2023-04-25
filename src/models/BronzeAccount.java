package models;

import utils.Constants;
import utils.FileHandler;
import utils.Utils;

public class BronzeAccount extends BankAccount implements Upgradable {

    public static final String TYPE = "Bronze";
    public static final double PRICE = 0.0;
    public static final int TRANSACTIONS_PER_MONTH = 5;
    public static final double DEBT_LIMIT = 0.0;

    public BronzeAccount(String ownerId, String ownerFirstName, String ownerLastName, double balance) {
        super(ownerId, ownerFirstName, ownerLastName, TYPE, balance);
    }

    public BronzeAccount(String accountId) {
        super(accountId);
    }

    @Override
    public String deposit(double money) {
        final int transactionTimes = FileHandler.getTransactionsTimes(accountId);
        if (transactionTimes >= TRANSACTIONS_PER_MONTH) {
            return Constants.LIMIT_TRANSACTIONS_MESSAGE;
        }
        String result = FileHandler.changeBalance(accountId, money);
        this.balance += money;
        return result;
    }

    @Override
    public String withdraw(double money) {
        final int transactionTimes = FileHandler.getTransactionsTimes(accountId);
        if (transactionTimes >= TRANSACTIONS_PER_MONTH) {
            return Constants.LIMIT_TRANSACTIONS_MESSAGE;
        }
        final double oldBalance = FileHandler.getBalance(accountId);
        if (money > oldBalance + DEBT_LIMIT) {
            return Constants.NO_ENOUGH_MONEY_MESSAGE;
        }
        final String result = FileHandler.changeBalance(accountId, -money);
        this.balance -= money;
        return result;
    }

    @Override
    public String upgrade(String newType) {
        final double oldBalance = FileHandler.getBalance(accountId);
        final double value = Utils.getValueToDeduct(TYPE, newType);
        if (value > oldBalance) {
            return Constants.NO_ENOUGH_MONEY_MESSAGE;
        }
        final String result = FileHandler.changeAccountType(accountId, TYPE, newType);
        this.accountType = newType;
        this.balance -= value;
        return result;
    }

}
