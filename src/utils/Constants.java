package utils;

public class Constants {

    public static final String OWNER_NAME = "Owner name: ";
    public static final String OWNER_ID = "Owner ID: ";
    public static final String ACCOUNT_ID = "Account ID: ";
    public static final String ACCOUNT_TYPE = "Account type: ";
    public static final String CARD_NUMBER = "Card number: ";
    public static final String PASSWORD = "Password: ";
    public static final String BALANCE = "Balance: ";
    public static final String FILE_PATH = "File path: ";
    public static final String CREATION_DATE = "Creation date: ";
    public static final String TRANSACTION_DATE = "Transaction date: ";
    public static final String UPGRADE_DATE = "Upgrade date: ";
    public static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
    
    //---------------------------------------------------------------------------
    public static final String SUCCESS = "Success";
    public static final String CANNOT_CREATE_FILE = "Can't create the file";
    public static final String CANNOT_READ_FILE = "Can't read the file";
    public static final String CANNOT_EDIT_FILE = "Can't edit the file.";
    public static final String UNKNOWN_EXCEPTION = "Unknown exception.";
    public static final String NO_ENOUGH_MONEY_MESSAGE = "You don't have enough money.";
    public static final String LIMIT_TRANSACTIONS_MESSAGE = "You have reached your monthly limit of transactions.\nYou can Upgrade your account to have more transactions.";

    //---------------------------------------------------------------------------
    public static final String SPACE = " ";
    public static final String LINE = "\n";

    //---------------------------------------------------------------------------
    public static final int ACCOUNT_ID_RANGE_START = (int) Math.pow(10, 6);
    public static final int ACCOUNT_ID_RANGE_END = (int) Math.pow(10, 7);
    public static final int PASSWORD_RANGE_START = (int) Math.pow(10, 3);
    public static final int PASSWORD_RANGE_END = (int) Math.pow(10, 4);
    public static final int CARD_NUMBER_RANGE_START = (int) Math.pow(10, 3);
    public static final int CARD_NUMBER_RANGE_END = (int) Math.pow(10, 4);
}
