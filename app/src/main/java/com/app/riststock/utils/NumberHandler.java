package com.app.riststock.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by wokman on 4/2/2017.
 */

public class NumberHandler {

    public static String arabicToDecimal(String number) {
        char[] chars = new char[number.length()];
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }

    public static String roundDouble(Object value) {


//        NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
//        DecimalFormat df = (DecimalFormat)nf;

        Locale currentLocale = Locale.GERMAN;
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(',');
        DecimalFormat df = new DecimalFormat("####0.00", otherSymbols);

//        DecimalFormat df = new DecimalFormat("####0.00");
//        System.out.println("Value: " + df.format(value));

        return df.format(value);
    }

    public static String roundFloat(Object value) {


//        NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
//        DecimalFormat df = (DecimalFormat)nf;

        Locale currentLocale = Locale.GERMAN;
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(',');
        DecimalFormat df = new DecimalFormat("####0.0", otherSymbols);

//        DecimalFormat df = new DecimalFormat("####0.00");
//        System.out.println("Value: " + df.format(value));

        return df.format(value);
    }

    public static String roundDouble2(Object value) {

        String res = value.toString();
        try {

            res = res.substring(0, res.indexOf(".") + 2);
        } catch (Exception e) {

        }
        return res;
    }


    public static float getFloat(String val) {

        try {
            float f = Float.parseFloat(val);

            return (Float.isNaN(f) ? 0 : f);
        } catch (Exception e) {
            return 0;
        }
    }

    public static Double getDouble(String val) {

        try {

            Double f = Double.parseDouble(val);

            return (Double.isNaN(f) ? 0 : f);
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static int getInt(String val) {

        try {
            int f = Integer.parseInt(val);

            return f;
        } catch (Exception e) {
            return 0;
        }
    }

    public static String formatFloat(double d) {

        d = round(d, 4);

        return ClearFloat(String.format(Locale.US, "%.3f", d));
    }

    private static String ClearFloat(String Val) {

        return Val.replaceAll("\\.0*$", "");
    }

    public static String formatFloat(double d, int r) {

        d = round(d, r);

        return String.format(Locale.US, "%." + (r - 1) + "f", d);
    }


    public static String formatFloat_Q(double d) {
        d = round(d, 4);
        return String.format(Locale.US, "%.2f", d);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static String formatDouble(double value) {
//        DecimalFormat df = new DecimalFormat("####0.00");
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(',');
        DecimalFormat df = new DecimalFormat("#0.00", otherSymbols);
//        DecimalFormat df = new DecimalFormat("#0.00");
//        df.setRoundingMode(RoundingMode.CEILING);
        return arabicToDecimal(df.format(value));
//        System.out.println("Value: " + df.format(value));
    }

}
