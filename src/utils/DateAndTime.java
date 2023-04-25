package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAndTime {

    public static String now() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
        return formatter.format(date);
    }

    public static int getSameMonthTimes(String content) {
        final String cuurentDate = now();
        final String yearAndMonth = Constants.TRANSACTION_DATE + cuurentDate.substring(0, 7);
        int counter = 0;
        while (content.contains(yearAndMonth)) {
            counter++;
            content = content.replaceFirst(yearAndMonth, "");
        }
        return counter;
    }
}
