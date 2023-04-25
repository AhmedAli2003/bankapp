package utils;

import java.util.Random;
import models.BronzeAccount;
import models.GoldenAccount;
import models.PlatinumAccount;
import models.SilverAccount;

public class Utils {
    
    public static final Random random = new Random();
    
    public static String getRandomValue(int start, int end) {
        return String.valueOf(random.nextInt(start, end));
    }
    
    public static String getValidUserInput(String input) {
        input = input.trim();
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
    
    public static String getDashes(int n) {
        return "-".repeat(n);
    }
    
    public static double getValueToDeduct(String oldType, String newType) {
        double value = 0.0;
        switch (newType) {
            case SilverAccount.TYPE -> value += SilverAccount.PRICE;
            case GoldenAccount.TYPE -> value += GoldenAccount.PRICE;
            case PlatinumAccount.TYPE -> value += PlatinumAccount.PRICE;
        }
        
        switch (oldType) {
            case BronzeAccount.TYPE -> value -= BronzeAccount.PRICE;
            case SilverAccount.TYPE -> value -= SilverAccount.PRICE;
            case GoldenAccount.TYPE -> value -= GoldenAccount.PRICE;
        }
        return value;
    }
}
