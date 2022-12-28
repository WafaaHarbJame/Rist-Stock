package com.app.riststock.utils

import android.app.Activity
import android.util.Log
import com.app.riststock.utils.UtilityApp.language
import com.app.riststock.R
import com.app.riststock.classes.Constants
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateHandler {
    //    String lang = UtilityApp.language;
    fun GetDate(d: Any): Date {
        val parser: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        parser.timeZone = TimeZone.getTimeZone(Constants.TIME_ZONE)
        var date = Date()
        try {
            date = parser.parse(d.toString())
        } catch (e: Exception) {
        }
        return date
    }


    fun convertToLong(string_date: String?, format: String?): Long {
        var longDate: Long = 0
        val f = SimpleDateFormat(format)
        try {
            val d = f.parse(string_date)
            val milliseconds = d.time
            longDate = d.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return longDate
    }

    fun GetDateNow(): Date? {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(Constants.TIME_ZONE))
        return calendar.time
    }

    fun GetTimeString(d: Date?): String? {
        val parser: DateFormat = SimpleDateFormat("hh:mm aa", Locale.US)
        try {
            return parser.format(d)
        } catch (e: java.lang.Exception) {
        }
        return ""
    }

    fun GetDateOnlyNowLong(): Long {

//        DateFormat parser = new SimpleDateFormat("yyyyMMddHHmmss");
        val date = Date()
        val dateStr = GetDateOnlyString(date.time)
        // Date s = parser.parse("");
        return GetDateOnlyLong(dateStr)
    }

    fun GetDateFromDateHour(d: Any): Date {
        val parser: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
        var date = Date()
        try {
            date = parser.parse(d.toString())
        } catch (e: Exception) {
        }
        return date
    }

    fun GetLongToDateString(time_stamp: Long): String {
        try {
            val parser: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
            parser.timeZone = TimeZone.getTimeZone(Constants.TIME_ZONE)
            val time = Date(time_stamp * 1000)
            return parser.format(time)
        } catch (e: Exception) {
        }
        return ""
    }

    fun GetDateOnlyString(time_stamp: Long): String {
        try {
            val parser: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            parser.timeZone = TimeZone.getTimeZone(Constants.TIME_ZONE)
            val time = Date(time_stamp)
            return parser.format(time)
        } catch (e: Exception) {
        }
        return ""
    }

    fun GetDayMonthOnlyString(time_stamp: Long): String {
        try {
            val parser: DateFormat = SimpleDateFormat("MMM-dd")
            parser.timeZone = TimeZone.getTimeZone(Constants.TIME_ZONE)
            val time = Date(time_stamp * 1000)
            return parser.format(time)
        } catch (e: Exception) {
        }
        return ""
    }

    fun GetTimeOnlyString(time_stamp: Long): String {
        try {
            val parser: DateFormat = SimpleDateFormat("HH:mm")
            parser.timeZone = TimeZone.getTimeZone(Constants.TIME_ZONE)
            val time = Date(time_stamp * 1000)
            return parser.format(time)
        } catch (e: Exception) {
        }
        return ""
    }

    fun GetTimeWithSecOnlyString(time_stamp: Long): String {
        try {
            val parser: DateFormat = SimpleDateFormat("HH:mm:ss")
            parser.timeZone = TimeZone.getTimeZone(Constants.TIME_ZONE)
            val time = Date(time_stamp)
            return parser.format(time)
        } catch (e: Exception) {
        }
        return ""
    }

    fun GetDateTimeLong(data: String): Long {
        Log.i("DateHandle", "Log date $data")
        try {
            val parser: DateFormat =
                SimpleDateFormat("yyyy-MM-dd HH:mm", Locale(language))
            parser.timeZone = TimeZone.getTimeZone(Constants.TIME_ZONE)
            val dateLong = parser.parse(data).time
            return dateLong / 1000
        } catch (e: Exception) {
        }
        return 0
    }

    fun GetDateTimeSecLong(data: String): Long {
        Log.i("DateHandle", "Log date $data")
        try {
            val parser: DateFormat =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale(language))
            parser.timeZone = TimeZone.getTimeZone(Constants.TIME_ZONE)
            val dateLong = parser.parse(data).time
            return dateLong / 1000
        } catch (e: Exception) {
        }
        return 0
    }

    fun GetDateTimeLongCalender(year: Int, month: Int, day: Int, hour: Int, min: Int): Long {
        try {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone(Constants.TIME_ZONE))
            calendar[year, month, day, hour, min] = 0

//            DateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            val dateLong = calendar.timeInMillis
            //            long dateLong = parser.parse(data).getTime();
            return dateLong / 1000
        } catch (e: Exception) {
        }
        return 0
    }

    fun GetDateOnlyLong(data: String?): Long {
        try {
            val parser: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            parser.timeZone = TimeZone.getTimeZone(Constants.TIME_ZONE)
            val dateLong = parser.parse(data).time
            return dateLong / 1000
        } catch (e: Exception) {
        }
        return 0
    }

    fun GetMonthName(activity: Activity?, d: Any): String {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(Constants.TIME_ZONE))
        calendar.time = GetDate(d)
        return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale(language))
    }

    fun GetDayOfMonth(activity: Activity?, d: Any): String {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(Constants.TIME_ZONE))
        calendar.time = GetDate(d)
        val monthNumber = calendar[Calendar.DAY_OF_MONTH]
        return monthNumber.toString() + ""
    }

    fun ConvertSecondsToDayHourFormat(seconds: Int): String {
        val sec = seconds % 60
        val min = seconds / 60 % 60
        val hours = seconds / 60 / 60
        return "$hours:$min:$sec"
    }

    fun GetDayOfWeek(activity: Activity, d: Any): String {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(Constants.TIME_ZONE))
        calendar.time = GetDate(d)
        val days = arrayOf(
            activity.getString(R.string.sunday),
            activity.getString(R.string.monday),
            activity.getString(R.string.thusday),
            activity.getString(R.string.wednesday),
            activity.getString(R.string.thursday),
            activity.getString(R.string.friday),
            activity.getString(R.string.satday)
        )
        return days[calendar[Calendar.DAY_OF_WEEK] - 1]
    }

    fun GetDateString(date: Date?, format: String?): String {
        val parser: DateFormat = SimpleDateFormat(format)
        try {
            return parser.format(date)
        } catch (e: Exception) {
        }
        return ""
    }

    fun GetDateAndTimeString(date: String): Array<String> {
        return date.split(" ".toRegex()).toTypedArray()
    }

    fun GetDateString(d: Date?): String {
        val parser: DateFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.US)
        try {
            return parser.format(d)
        } catch (e: Exception) {
        }
        return ""
    }

    fun GetDateNowString(): String {
        val parser: DateFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale(language))
        val date = Date()
        try {
            // Date s = parser.parse("");
            return parser.format(date)
        } catch (e: Exception) {
        }
        return ""
    }

    fun GetDateNowLong(): Long {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(Constants.TIME_ZONE))
        return calendar.timeInMillis
    }

    /**
     * format string date as yyyy-MM-dd
     *
     * @param o
     * @return
     */
    fun FormatDate(o: Any): String {
        val parser = SimpleDateFormat("yyyy-MM-dd" /*, new Locale(UtilityApp.language)*/)
        try {
            val date = parser.parse(o.toString())
            return parser.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * format date as yyyy-MM-dd HH:mm aa
     *
     * @param o
     * @return
     */
    fun FormatDate2(o: Any): String {
        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale(language))
        parser.timeZone = TimeZone.getTimeZone(Constants.TIME_ZONE)
        val formater = SimpleDateFormat("yyyy-MM-dd hh:mm aa", Locale(language))
        formater.timeZone = TimeZone.getTimeZone(Constants.TIME_ZONE)
        try {
            val date = parser.parse(o.toString())
            return formater.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * format date as MMM-dd
     *
     * @param o
     * @return
     */
    fun FormatDate3(o: Any): String {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale(language))
        parser.timeZone = TimeZone.getTimeZone(Constants.TIME_ZONE)
        val formater = SimpleDateFormat("MMM-dd", Locale(language))
        formater.timeZone = TimeZone.getTimeZone(Constants.TIME_ZONE)
        try {
            val date = parser.parse(o.toString())
            return formater.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * format date as a any input to any output
     *
     * @param o
     * @return
     */
    open fun FormatDate4(
        o: Any,
        inPattern: String?,
        outPattern: String?,
        locale: String?
    ): String {
        val parser = SimpleDateFormat(inPattern)
        val parser2 = SimpleDateFormat(outPattern, Locale(locale ?: language))
        //        parser.setTimeZone(TimeZone.getTimeZone());
        try {
            val date = parser.parse(o.toString())
            return NumberHandler.arabicToDecimal(parser2.format(date))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }


    /**
     * format time from HH:mm to hh:mm aa
     *
     * @param o
     * @return
     */
    fun FormatTime(o: Any): String {
        val parser = SimpleDateFormat("HH:mm" /*, new Locale(Constants.English)*/)
        parser.timeZone = TimeZone.getTimeZone(Constants.TIME_ZONE)
        val formater = SimpleDateFormat("hh:mm aa", Locale(language))
        formater.timeZone = TimeZone.getTimeZone(Constants.TIME_ZONE)
        try {
            val date = parser.parse(o.toString())
            return NumberHandler.arabicToDecimal(formater.format(date))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * format time from HH:mm to HH:mm
     *
     * @param o
     * @return
     */
    fun FormatTime2(o: Any): String {
        val parser = SimpleDateFormat("HH:mm")
        parser.timeZone = TimeZone.getTimeZone(Constants.TIME_ZONE)
        val formater = SimpleDateFormat("HH:mm")
        formater.timeZone = TimeZone.getTimeZone(Constants.TIME_ZONE)
        try {
            val date = parser.parse(o.toString())
            return NumberHandler.arabicToDecimal(formater.format(date))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * format time from HH:mm to hh:mm aa
     *
     * @param o
     * @return
     */
    fun FormatTime3(o: Any): String {
        val parser = SimpleDateFormat("HH:mm")
        parser.timeZone = TimeZone.getTimeZone(Constants.TIME_ZONE)
        val formater = SimpleDateFormat("hh:mm aa", Locale(language))
        formater.timeZone = TimeZone.getTimeZone(Constants.TIME_ZONE)
        try {
            val date = parser.parse(o.toString())
            return NumberHandler.arabicToDecimal(formater.format(date))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * format time from hh:mm aa to HH:mm
     *
     * @param o
     * @return
     */
    fun FormatTime4(o: Any): String {
        val parser = SimpleDateFormat("hh:mm aa", Locale(language))
        parser.timeZone = TimeZone.getTimeZone(Constants.TIME_ZONE)
        val formater = SimpleDateFormat("HH:mm")
        formater.timeZone = TimeZone.getTimeZone(Constants.TIME_ZONE)
        try {
            val date = parser.parse(o.toString())
            return NumberHandler.arabicToDecimal(formater.format(date))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * format time from HH aa to HH
     *
     * @param o
     * @return
     */
    fun FormatTime5(o: Any): String {
        val parser = SimpleDateFormat("HH:mm")
        parser.timeZone = TimeZone.getTimeZone(Constants.TIME_ZONE)
        val formater = SimpleDateFormat("hh:mm aa", Locale(language))
        formater.timeZone = TimeZone.getTimeZone(Constants.TIME_ZONE)
        try {
            val date = parser.parse(o.toString())
            return NumberHandler.arabicToDecimal(formater.format(date))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    fun GetTimeFromDateString(timeStamp: Long): String {
        val date1 = Date(timeStamp * 1000)
        val cal = Calendar.getInstance()
        cal.time = date1
        val hour = cal[Calendar.HOUR_OF_DAY]
        val minute = cal[Calendar.MINUTE]
        return FormatTime("$hour:$minute")
    }



    fun FormatHoursToString(activity: Activity, hours: Int): String {
        var dayText = ""
        var hourText = ""
        val restHour: Double
        val day = hours / 24
        restHour = hours % 24.toDouble()
        val hour = (restHour * 60).toInt()
        if (day > 0) {
            dayText = day.toString() + " " + activity.getString(R.string.day)
            //            dayText = day < 3 ? "" : (day + " ");
//            dayText += day == 1 ? "يوم" : day == 2 ? "يومان" : day < 11 ? "ايام" : "يوم";
        }
        if (hour > 0) {
            hourText = if (day > 0) " " + activity.getString(R.string.and) + " " else ""
            hourText = hour.toString() + " " + activity.getString(R.string.hour)
            //            hourText += hour < 3 ? "" : (hour + " ");
//            hourText += hour == 1 ? "ساعة" : hour == 2 ? "ساعتين" : hour < 11 ? "ساعات" : "ساعة";
        }
        return dayText + hourText
    }

    fun ConvertMinutesToString(minutes: Double, IsHour: Boolean): String {
        var minutes = minutes
        if (IsHour) {
            minutes = minutes * 60
        }
        var dayText = ""
        var hourText = ""
        var minuteText = ""
        val day = minutes.toInt() / 1440
        minutes = minutes % 1440
        val hour = minutes.toInt() / 60
        minutes = minutes % 60
        if (day > 0) {
            dayText = if (day < 3) "" else "$day "
            dayText += if (day == 1) "يوم" else if (day == 2) "يومان" else if (day < 11) "ايام" else "يوم"
        }
        if (hour > 0) {
            hourText = if (day > 0) " و " else ""
            hourText += if (hour < 3) "" else "$hour "
            hourText += if (hour == 1) "ساعة" else if (hour == 2) "ساعتين" else if (hour < 11) "ساعات" else "ساعة"
        }
        if (minutes > 0) {
            minuteText = if (day > 0 || hour > 0) " و " else ""
            minuteText += if (minutes < 3) "" else "$minutes "
            minuteText += if (minutes == 1.0) "دقيقة" else if (minutes == 2.0) "دقيقتين" else if (minutes < 11) "دقائق" else "دقيقة"
        }
        return "$dayText $hourText $minuteText"
    }

    fun ConvertMinutesToString2(minutes: Double, IsHour: Boolean): String {
        var minutes = minutes
        if (IsHour) {
            minutes = minutes * 60
        }
        var dayText = ""
        var hourText = ""
        var minuteText = ""
        val day = minutes.toInt() / 1440
        minutes = minutes % 1440
        val hour = minutes.toInt() / 60
        minutes = minutes % 60
        val minutesInt = minutes.toInt()
        if (day > 0) {
            dayText = if (day < 1) "" else "$day ي"
        }
        if (hour > 0) {
            hourText = if (day > 0) " و " else ""
            hourText += if (hour < 1) "" else "$hour س"
        }
        if (minutes > 0) {
            minuteText = if (day > 0 || hour > 0) " و " else ""
            minuteText += if (minutesInt < 1) "" else "$minutesInt د"
        }
        return "$dayText $hourText $minuteText"
    }

    fun formatStringToAgo(activity: Activity, dateText: String): String {
        val time = GetDateTimeLong(dateText)
        return formatToAgo(activity, time)
    }

    fun formatToAgo(activity: Activity, dateText: Long): String {
        val yearInSec = 31104000
        val monthInSec = 2592000
        val weekInSec = 604800
        val dayInSec = 68400
        val hourInSec = 3600
        val minInSec = 60
        val currentTime = System.currentTimeMillis() / 1000

//        System.out.println("Log diff "+ (currentTime - oldTime));
        val diff = currentTime - dateText
        var yearText = ""
        var monthText = ""
        var weekText = ""
        var dayText = ""
        var hourText = ""
        var minuteText = ""
        val year = diff.toInt() / yearInSec
        //        diff = diff % yearInSec;
        if (year > 0) {
            yearText = year.toString() + " " + activity.getString(R.string.year)
            return yearText
        }
        val month = diff.toInt() / monthInSec
        //        diff = diff % monthInSec;
        if (month > 0) {
            monthText = month.toString() + " " + activity.getString(R.string.month)
            return monthText
        }
        val week = diff.toInt() / weekInSec
        //        diff = diff % weekInSec;
        if (week > 0) {
            weekText = week.toString() + " " + activity.getString(R.string.week)
            return weekText
        }
        val day = diff.toInt() / dayInSec
        //        diff = diff % dayInSec;
        if (day > 0) {
            dayText = day.toString() + " " + activity.getString(R.string.day)
            return dayText
        }
        val hour = diff.toInt() / hourInSec
        //        diff = diff % hourInSec;
        if (hour > 0) {
            hourText = hour.toString() + " " + activity.getString(R.string.hour)
            return hourText
        }
        val minutes = diff.toInt() / minInSec
        if (minutes > 0) {
            minuteText = minutes.toString() + " " + activity.getString(R.string.minute)
            return minuteText
        }


//        if (day > 0) {
//            dayText = day + " " + activity.getString(R.string.d);
//        }
//
//        if (hour > 0) {
//            hourText = day > 0 ? " " + activity.getString(R.string.and) + " " : "";
//            hourText += hour + " " + activity.getString(R.string.h);
//        }
//
//        if (minutesInt > 0) {
//            minuteText = day > 0 || hour > 0 ? " " + activity.getString(R.string.and) + " " : "";
//            minuteText += minutesInt + " " + activity.getString(R.string.m);
//        }

//        return dayText + " " + hourText + " " + minuteText;
        return "0"
    }

    fun ConvertSecondsToMinutes(dateText: Long): Double {
        val str = ""
        val dateFormat = SimpleDateFormat("yyyyMMddHHmmss")
        var d: Date? = null
        try {
            d = dateFormat.parse(dateText.toString() + "")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val oldTime = d!!.time
        val currentTime = System.currentTimeMillis()
        val diff = currentTime - oldTime
        val diffMinutes = diff / (60 * 1000)
        return diffMinutes.toDouble()
    }

    fun FormatDate5(o: Any, inPattern: String?, outPattern: String?, locale: String?): String {
        val parser = SimpleDateFormat(inPattern)
        val parser2 = SimpleDateFormat(
            outPattern,
            Locale(locale ?: UtilityApp.language)
        )
        //        parser.setTimeZone(TimeZone.getDefault());
        parser.timeZone = TimeZone.getTimeZone("UTC")
        try {
            val date = parser.parse(o.toString())
            return NumberHandler.arabicToDecimal(parser2.format(date))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }
}
