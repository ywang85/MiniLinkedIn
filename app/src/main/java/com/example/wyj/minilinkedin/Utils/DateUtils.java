package com.example.wyj.minilinkedin.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wyj on 2018/2/11.
 */

public class DateUtils {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/YYYY", Locale.getDefault());
    public static String dateToString(Date date) {
        return simpleDateFormat.format(date);
    }
    public static Date stringToDate(String dateString) {
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date(0);
        }
    }
}
