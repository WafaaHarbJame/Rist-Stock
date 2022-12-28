package com.app.riststock.utils;

import android.text.TextUtils;
import android.util.Patterns;

import com.app.riststock.RootApplication;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

/**
 * Created by wokman on 11/11/2016.
 */

public class PhoneHandler {

    public static boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }
        return false;
    }

    public static boolean validateUsing_libphonenumber(String countryCode, String phNumber) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.createInstance(RootApplication.Companion.getInstance());
        String isoCode = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode));
        Phonenumber.PhoneNumber phoneNumber = null;
        try {
            //phoneNumber = phoneNumberUtil.parse(phNumber, "IN");  //if you want to pass region code
//            System.out.println("Log isoCode " + isoCode);
            phoneNumber = phoneNumberUtil.parse(phNumber, isoCode);
        } catch (NumberParseException e) {
            System.err.println(e);
        }

        boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);
        return isValid;
//        if (isValid) {
////            String internationalFormat = phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
////            Toast.makeText(this, "Phone Number is Valid " + internationalFormat, Toast.LENGTH_SHORT).show();
//            return true;
//        } else {
////            Toast.makeText(this, "Phone Number is Invalid " + phoneNumber, Toast.LENGTH_SHORT).show();
//            return false;
//        }
    }

    public static int getCountyCode(String phNumber) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.createInstance(RootApplication.Companion.getInstance());

        try {
            // phone must begin with '+'
            Phonenumber.PhoneNumber numberProto = phoneNumberUtil.parse(phNumber, "");
            int countryCode = numberProto.getCountryCode();
            return countryCode;
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }

//        return Constants.COUNTRY_CODE_PLUS;
        return 0;

    }

}
