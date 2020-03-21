package net.simplifiedcoding.moengagenews.ui;

import android.widget.Toast;

import net.simplifiedcoding.moengagenews.MyApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static void toast(String message) {
        Toast.makeText(MyApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static String getFormattedDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        try {
            Date date = sdf.parse(dateString);
            SimpleDateFormat sdf1 = new SimpleDateFormat("E, dd MMM yyyy", Locale.ENGLISH);
            if (date != null)
                return sdf1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
