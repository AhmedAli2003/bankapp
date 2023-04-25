package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {

    public static final String ACCOUNTS_FOLDER_PATH = "C:\\Users\\ahag1\\Git\\java\\BankApp\\accounts\\";
    public static final String TEXT_EXTENSION = ".txt";
    public static final String ACCOUNTS_FILE_NAME = "accounts";
    public static final String ACCOUNTS_FILE_PATH = ACCOUNTS_FOLDER_PATH + ACCOUNTS_FILE_NAME + TEXT_EXTENSION;

    public static String createFile(
            String accountId,
            String fullName,
            String ownerID,
            String accountType,
            String cardNumber,
            String password,
            double balance
    ) {

        final File mainFile = new File(ACCOUNTS_FILE_PATH);

        final String accountFilePath = getPath(accountId);
        final File accountFile = new File(accountFilePath);

        try {
            if (!mainFile.exists()) {
                mainFile.createNewFile();
            }
            final FileWriter mainFileWriter = new FileWriter(ACCOUNTS_FILE_PATH, true);
            mainFileWriter.write(Constants.ACCOUNT_ID + accountId + Constants.LINE);
            mainFileWriter.write(Constants.OWNER_ID + ownerID + Constants.LINE);
            mainFileWriter.write(Utils.getDashes(20) + Constants.LINE);
            mainFileWriter.close();

            final boolean isCreated = accountFile.createNewFile();
            if (isCreated) {

                final FileWriter fileWriter = new FileWriter(accountFilePath);
                String creationDate = DateAndTime.now();
                fileWriter.write(Constants.CREATION_DATE + creationDate + Constants.LINE);
                fileWriter.write(Constants.OWNER_NAME + fullName + Constants.LINE);
                fileWriter.write(Constants.OWNER_ID + ownerID + Constants.LINE);
                fileWriter.write(Constants.ACCOUNT_ID + accountId + Constants.LINE);
                fileWriter.write(Constants.ACCOUNT_TYPE + accountType + Constants.LINE);
                fileWriter.write(Constants.CARD_NUMBER + cardNumber + Constants.LINE);
                fileWriter.write(Constants.PASSWORD + password + Constants.LINE);
                fileWriter.write(Constants.BALANCE + balance + Constants.LINE);
                fileWriter.write(Utils.getDashes(50) + Constants.LINE);
                fileWriter.close();

                return accountFilePath;
            } else {
                return Constants.CANNOT_CREATE_FILE;
            }
        } catch (IOException e) {
            return Constants.CANNOT_CREATE_FILE;
        } catch (Exception e) {
            return Constants.UNKNOWN_EXCEPTION;
        }
    }

    public static String readFile(String fileName) {
        final String path = getPath(fileName);
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(path));
            String content = "";
            String line = reader.readLine();
            while (line != null) {
                content += line + Constants.LINE;
                line = reader.readLine();
            }
            reader.close();
            return content;
        } catch (IOException e) {
            return Constants.CANNOT_READ_FILE;
        } catch (Exception e) {
            return Constants.UNKNOWN_EXCEPTION;
        }
    }

    public static String changePassword(String accountId, String newPassword) {
        final String oldContent = readFile(accountId);
        final String newContent = getNewContent(oldContent, Constants.PASSWORD, newPassword);
        return write(accountId, newContent);
    }

    public static String changeAccountType(String accountId, String oldType, String newType) {
        final double value = Utils.getValueToDeduct(oldType, newType);
        changeBalance(accountId, value, oldType, newType);
        final String oldContent = readFile(accountId);
        final String newContent = getNewContent(oldContent, Constants.ACCOUNT_TYPE, newType);
        return write(accountId, newContent);
    }

    public static String changeBalance(String accoundId, double money) {
        double balance = getBalance(accoundId);
        balance += money;
        final String oldContent = readFile(accoundId);
        String newContent = getNewContent(
                oldContent,
                Constants.BALANCE,
                String.valueOf(balance)
        );
        final String transactionDate = DateAndTime.now();
        final String transactionStatement;
        if (money < 0) {
            transactionStatement = "Withdraw " + (-money) + "$ from the balance.";
        } else {
            transactionStatement = "Deposit " + money + "$ to the balance.";
        }
        newContent += Constants.TRANSACTION_DATE + transactionDate + Constants.LINE;
        newContent += transactionStatement + Constants.LINE;
        newContent += Utils.getDashes(50) + Constants.LINE;
        return write(accoundId, newContent);
    }

    public static String changeBalance(String accountId, double value, String oldType, String newType) {
        double balance = getBalance(accountId);
        balance -= value;
        final String oldContent = readFile(accountId);
        String newContent = getNewContent(
                oldContent,
                Constants.BALANCE,
                String.valueOf(balance)
        );
        final String upgradeDate = DateAndTime.now();
        final String upgradeStatement = "Upgrade the account from " + oldType + " to " + newType;
        newContent += Constants.UPGRADE_DATE + upgradeDate + Constants.LINE;
        newContent += upgradeStatement + Constants.LINE;
        newContent += Utils.getDashes(50) + Constants.LINE;
        return write(accountId, newContent);
    }

    public static String getPassword(String accountId) {
        return getSpecificInfo(accountId, Constants.PASSWORD);
    }

    public static double getBalance(String accountId) {
        return Double.parseDouble(getSpecificInfo(accountId, Constants.BALANCE));
    }

    public static String getOwnerName(String accountId) {
        return getSpecificInfo(accountId, Constants.OWNER_NAME);
    }

    public static String getOwnerId(String accountId) {
        return getSpecificInfo(accountId, Constants.OWNER_ID);
    }

    public static String getAccountType(String accountId) {
        return getSpecificInfo(accountId, Constants.ACCOUNT_TYPE);
    }

    public static String getCardNumber(String accountId) {
        return getSpecificInfo(accountId, Constants.CARD_NUMBER);
    }

    public static int getTransactionsTimes(String accountId) {
        final String content = readFile(accountId);
        return DateAndTime.getSameMonthTimes(content);
    }

    private static String getSpecificInfo(String accountId, String info) {
        final String content = readFile(accountId);
        final int start = content.indexOf(info) + info.length();
        final int end = content.indexOf(Constants.LINE, start);
        return content.substring(start, end);
    }

    private static String write(String accountId, String newContent) {
        try {
            final FileWriter writer = new FileWriter(getPath(accountId));
            writer.write(newContent);
            writer.close();
            return Constants.SUCCESS;
        } catch (IOException e) {
            return Constants.CANNOT_EDIT_FILE;
        } catch (Exception e) {
            return Constants.UNKNOWN_EXCEPTION;
        }
    }

    private static String getNewContent(String oldContent, String infoWantToChange, String newInfo) {
        String newContent = "";
        final int start = oldContent.indexOf(infoWantToChange) + infoWantToChange.length();
        final int end = oldContent.indexOf(Constants.LINE, start);
        newContent += oldContent.substring(0, start);
        newContent += newInfo;
        newContent += oldContent.substring(end);
        return newContent;
    }

    public static String getPath(String fileName) {
        return ACCOUNTS_FOLDER_PATH + fileName + TEXT_EXTENSION;
    }
}
