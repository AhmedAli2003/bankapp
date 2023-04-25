package bankapp;

import java.util.Scanner;
import models.BankAccount;
import models.BronzeAccount;
import models.GoldenAccount;
import models.PlatinumAccount;
import models.SilverAccount;
import utils.Constants;
import utils.FileHandler;

public class BankApp {

    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Bank App");
        System.out.println("Enter 1 to log in.");
        System.out.println("Enter 2 to sign up.");
        final String $1or2 = getInput();
        if ($1or2.equals("1")) {
            System.out.print("Enter your account id: ");
            final String accountId = getInput();
            final String mainFileContent = FileHandler.readFile(FileHandler.ACCOUNTS_FILE_NAME);
            if (mainFileContent.contains(Constants.ACCOUNT_ID + accountId)) {
                final boolean isPasswordTrue = checkPassword(accountId);
                if (isPasswordTrue) {
                    System.out.println("Login successfully");
                    final String accountType = FileHandler.getAccountType(accountId);
                    final String service = chooseService(accountType);
                    final BankAccount account = getAccount(accountId, accountType);
                    doService(service, account);
                } else {
                    System.out.println("You can't login!");
                }
            } else {
                System.out.println("Please enter a valid account id");
            }
        } else if ($1or2.equals("2")) {
            System.out.print("Enter your first name: ");
            final String firstName = getInput();
            System.out.print("Enter your last name: ");
            final String lastName = getInput();
            System.out.print("Enter your id: ");
            final String ownerId = getInput();
            System.out.println("Choose an account type");
            System.out.println("1 - Bronze (Free)");
            System.out.println("2 - Silver (50$)");
            System.out.println("3 - Golden (100$)");
            System.out.println("4 - Platinum (500$)");
            System.out.print("Account type: ");
            String accountType = getInput();
            if (accountType.length() != 1 || !"1234".contains(accountType)) {
                accountType = "1";
            }
            System.out.print("Enter initial balance:");
            double balance = scanner.nextDouble();
            balance = balance < 0 ? 0 : balance;
            createAccount(ownerId, firstName, lastName, accountType, balance);
        } else {
            System.out.println("Please enter 1 or 2.");
        }
    }

    private static String getInput() {
        return scanner.next().trim();
    }

    private static void createAccount(
            String ownerId,
            String firstName,
            String lastName,
            String accountType,
            double balance
    ) {
        switch (accountType) {
            case "1":
                final BronzeAccount bronze = new BronzeAccount(
                        ownerId,
                        firstName,
                        lastName,
                        balance
                );
                System.out.println(bronze);
                break;
            case "2":
                if (balance >= SilverAccount.PRICE) {
                    final SilverAccount silver = new SilverAccount(
                            ownerId,
                            firstName,
                            lastName,
                            balance
                    );
                    System.out.println(silver);
                } else {
                    System.out.println(Constants.NO_ENOUGH_MONEY_MESSAGE);
                }
                break;
            case "3":
                if (balance >= GoldenAccount.PRICE) {
                    final GoldenAccount golden = new GoldenAccount(
                            ownerId,
                            firstName,
                            lastName,
                            balance
                    );
                    System.out.println(golden);
                } else {
                    System.out.println(Constants.NO_ENOUGH_MONEY_MESSAGE);
                }
                break;
            case "4":
                if (balance >= PlatinumAccount.PRICE) {
                    final PlatinumAccount platinum = new PlatinumAccount(
                            ownerId,
                            firstName,
                            lastName,
                            balance
                    );
                    System.out.println(platinum);
                } else {
                    System.out.println(Constants.NO_ENOUGH_MONEY_MESSAGE);
                }
                break;
        }
    }

    private static boolean checkPassword(String accountId) {
        int counter = 0;
        System.out.print("Enter your password: ");
        String inputPassword = getInput();
        final String realPassword = FileHandler.getPassword(accountId);
        while (!inputPassword.equals(realPassword)) {
            if (counter == 3) {
                return false;
            }
            System.out.print("Password is wrong, try again: ");
            inputPassword = getInput();
            counter++;
        }
        return true;
    }

    private static String chooseService(String accountType) {
        boolean condition;
        do {
            System.out.println("Choose one of these services:");
            System.out.println("1 - Show account info");
            System.out.println("2 - Deposit money to your account");
            System.out.println("3 - Withdraw money from your account");
            System.out.println("4 - Show last transaction");
            System.out.println("5 - Show all transactions");
            System.out.println("6 - Change password");
            final boolean notPlatinum = !accountType.equals(PlatinumAccount.TYPE);
            if (notPlatinum) {
                System.out.println("7 - Upgrade your account");
            }
            System.out.println("0 - Exit");
            System.out.print("Service: ");
            final String service = getInput();
            condition = service.length() == 1 && ("0123456".contains(service) || (service.equals("7") && notPlatinum));
            if (condition) {
                return service;
            } else {
                System.out.println("Enter a valid service!");
            }
        } while (!condition);
        return "-1";
    }

    private static BankAccount getAccount(String accountId, String accountType) {
        switch (accountType) {
            case BronzeAccount.TYPE:
                return new BronzeAccount(accountId);
            case SilverAccount.TYPE:
                return new SilverAccount(accountId);
            case GoldenAccount.TYPE:
                return new GoldenAccount(accountId);
            case PlatinumAccount.TYPE:
                return new PlatinumAccount(accountId);
        }
        return null;
    }

    private static void doService(String service, BankAccount account) {
        switch (service) {
            case "1":
                System.out.println(account);
                break;
            case "2":
                deposit(account);
                break;
            case "3":
                withdraw(account);
                break;
            case "4":
                System.out.println(account.getLastTransaction());
                break;
            case "5":
                System.out.println(account.getAllTransactions());
                break;
            case "6":
                changePassword(account);
                break;
            case "7":
                upgrade(account);
                break;
            case "0":
                System.exit(0);
        }
    }

    private static void deposit(BankAccount account) {
        System.out.print("Enter the money to deposit: ");
        try {
            final double money = scanner.nextDouble();
            System.out.println(account.deposit(money));
        } catch (Exception e) {
            System.out.println("Not a valid value");
        }
    }

    private static void withdraw(BankAccount account) {
        System.out.print("Enter the money to withdraw: ");
        try {
            final double money = scanner.nextDouble();
            System.out.println(account.withdraw(money));
        } catch (Exception e) {
            System.out.println("Not a valid value");
        }
    }

    private static void changePassword(BankAccount account) {
        System.out.println("Enter the new password:");
        String password1 = getInput();
        while (password1.length() < 4) {
            System.out.println("Password length must be four at least");
            password1 = getInput();
        }
        System.out.println("Enter the new password again:");
        String password2 = getInput();
        while (!password2.equals(password1)) {
            System.out.println("Enter the new password again:");
            password2 = getInput();
        }
        System.out.println(account.changePassword(password2));
    }

    private static void upgrade(BankAccount account) {
        System.out.println("Choose the upgrade:");
        String upgrade;
        switch (account.getAccountType()) {
            case BronzeAccount.TYPE:
                BronzeAccount bronze = (BronzeAccount) account;
                System.out.println("1 - Silver");
                System.out.println("2 - Golden");
                System.out.println("3 - Platinum");
                upgrade = getInput();
                switch (upgrade) {
                    case "1" -> System.out.println(bronze.upgrade(SilverAccount.TYPE));
                    case "2" -> System.out.println(bronze.upgrade(GoldenAccount.TYPE));
                    case "3" -> System.out.println(bronze.upgrade(PlatinumAccount.TYPE));
                    default -> System.out.println("Not a valid value");
                }
                break;

            case SilverAccount.TYPE:
                SilverAccount silver = (SilverAccount) account;
                System.out.println("1 - Golden");
                System.out.println("2 - Platinum");
                upgrade = getInput();
                switch (upgrade) {
                    case "1" -> System.out.println(silver.upgrade(GoldenAccount.TYPE));
                    case "2" -> System.out.println(silver.upgrade(PlatinumAccount.TYPE));
                    default -> System.out.println("Not a valid value");
                }
                break;
            case GoldenAccount.TYPE:
                GoldenAccount golden = (GoldenAccount) account;
                System.out.println("1 - Platinum");
                upgrade = getInput();
                switch (upgrade) {
                    case "1" -> System.out.println(golden.upgrade(PlatinumAccount.TYPE));
                    default -> System.out.println("Not a valid value");
                }
                break;
        }
    }

}
