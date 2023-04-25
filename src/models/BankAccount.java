package models;

import utils.Constants;
import utils.FileHandler;
import utils.Utils;

public abstract class BankAccount {

    protected final String accountId;
    protected final Owner owner;
    protected String accountType;
    protected final String cardNumber;
    protected String password;
    protected double balance;
    protected final String filePath;

    protected BankAccount(
            String ownerId,
            String ownerFirstName,
            String ownerLastName,
            String accountType,
            double balance
    ) {
        this.owner = new Owner(ownerId, ownerFirstName, ownerLastName);
        this.accountId = Utils.getRandomValue(Constants.ACCOUNT_ID_RANGE_START, Constants.ACCOUNT_ID_RANGE_END);
        this.accountType = accountType;
        this.cardNumber = createCardNumber();
        this.password = Utils.getRandomValue(Constants.PASSWORD_RANGE_START, Constants.PASSWORD_RANGE_END);
        this.balance = deductAccountPrice(balance, accountType);
        this.filePath = FileHandler.createFile(
                this.accountId,
                this.owner.getFullName(),
                this.owner.getId(),
                this.accountType,
                this.cardNumber,
                this.password,
                this.balance
        );
    }
    
    protected BankAccount(String accountId) {
        this.accountId = accountId;
        final String ownerId = FileHandler.getOwnerId(accountId);
        final String fullName = FileHandler.getOwnerName(accountId);
        final int indexOfSpace = fullName.indexOf(Constants.SPACE);
        final String firstName = fullName.substring(0, indexOfSpace);
        final String lastName = fullName.substring(indexOfSpace+1);
        this.owner = new Owner(ownerId, firstName, lastName);
        this.accountType = FileHandler.getAccountType(accountId);
        this.cardNumber = FileHandler.getCardNumber(accountId);
        this.password = FileHandler.getPassword(accountId);
        this.balance = FileHandler.getBalance(accountId);
        this.filePath = FileHandler.getPath(accountId);
    }

    public abstract String deposit(double money);

    public abstract String withdraw(double money);

    public String changePassword(String newPassword) {
        final String result = FileHandler.changePassword(accountId, newPassword);
        this.password = newPassword;
        return result;
    }

    public String getLastTransaction() {
        final String content = FileHandler.readFile(accountId);
        final int indexOfLastTransaction = content.lastIndexOf(Constants.TRANSACTION_DATE);
        return content.substring(indexOfLastTransaction);
    }

    public String getAllTransactions() {
        final String content = FileHandler.readFile(accountId);
        final int indexOfFirstTransaction = content.indexOf(Constants.TRANSACTION_DATE);
        return content.substring(indexOfFirstTransaction);
    }

    private String createCardNumber() {
        String card = "";
        for (int i = 0; i < 4; i++) {
            card += Utils.getRandomValue(Constants.CARD_NUMBER_RANGE_START, Constants.CARD_NUMBER_RANGE_END) + Constants.SPACE;
        }
        card = card.trim();
        return card;
    }

    private double deductAccountPrice(double balance, String accountType) {
        switch (accountType) {
            case BronzeAccount.TYPE ->
                balance -= BronzeAccount.PRICE;
            case SilverAccount.TYPE ->
                balance -= SilverAccount.PRICE;
            case GoldenAccount.TYPE ->
                balance -= GoldenAccount.PRICE;
            case PlatinumAccount.TYPE ->
                balance -= PlatinumAccount.PRICE;
        }
        return balance;
    }

    // Setters and Getters
    public String getAccountId() {
        return accountId;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Object class methods
    @Override
    public String toString() {
        return Constants.OWNER_NAME + owner.getFullName() + Constants.LINE
                + Constants.OWNER_ID + owner.getId() + Constants.LINE
                + Constants.ACCOUNT_ID + accountId + Constants.LINE
                + Constants.ACCOUNT_TYPE + accountType + Constants.LINE
                + Constants.CARD_NUMBER + cardNumber + Constants.LINE
                + Constants.PASSWORD + password + Constants.LINE
                + Constants.BALANCE + balance + Constants.LINE
                + Constants.FILE_PATH + filePath;

    }

}
