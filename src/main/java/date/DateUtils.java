package date;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author peramdy on 2018/8/24.
 */
public class DateUtils {

    /**
     * date pattern
     */
    public static final String YMD_PATTERN = "yyyy-MM-dd";
    public static final String HMS_PATTERN = "HH:mm:ss";
    public static final String YMDHMS_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String ISO8601_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";
    public static final String YMDHMS_LIST_PATTERN = "yyyyMMddHHmmss";
    public static final String YMD_LIST_PATTERN = "yyyyMMdd";
    public static final String YMDHMS_2Y_PATTERN = "yyMMddHHmmss";
    public static final String YMDHMSS_LIST_PATTERN = "yyyyMMddHHmmssSSS";
    public static final String MDYKMSA_PATTERN = "MM/dd/yyyy KK:mm:ss a";
    public static final String MDYHMSA_PATTERN = "MM/dd/yyyy HH:mm:ss a";
    public static final String ZONE_GMT = "GMT";
    public static final String ZONE_DEFAULT = "GMT";
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd";
    private static Calendar c;


    static {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        c = Calendar.getInstance();
    }


    /**
     * current time
     *
     * @return
     */
    public static Date currentDate() {
        return new Date();
    }


    /**
     * calendar
     *
     * @return
     */
    public static Calendar getCurrentCalendar() {
        return Calendar.getInstance();
    }

    /**
     * current date begin
     *
     * @return
     */
    public static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * default format
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, null);
    }

    /**
     * custom format
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat;
        if (StringUtils.isBlank(pattern)) {
            simpleDateFormat = new SimpleDateFormat(DateUtils.DEFAULT_PATTERN);
        } else {
            simpleDateFormat = new SimpleDateFormat(pattern);
        }
        return simpleDateFormat.format(date);
    }

    /**
     * @return
     */
    public static String getCurrentDateDafaultString() {
        return format(currentDate(), null);
    }

    /**
     * @param pattern
     * @return
     */
    public static String getCurrentDateString(String pattern) {
        return format(currentDate(), pattern);
    }

    /**
     * @return
     */
    public static String getCurrentDateId() {
        return format(currentDate(), DateUtils.YMDHMS_LIST_PATTERN);
    }

    /**
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * @return
     */
    public static int getCurrentYear() {
        c.setTime(currentDate());
        return c.get(Calendar.YEAR);
    }

    /**
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        c.setTime(date);
        return c.get(Calendar.MONTH);
    }

    /**
     * @return
     */
    public static int getCurrentMonth() {
        c.setTime(currentDate());
        return c.get(Calendar.MONTH);
    }

    /**
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * @return
     */
    public static int getCurrentDay() {
        c.setTime(currentDate());
        return c.get(Calendar.DAY_OF_MONTH);
    }


    /**
     * get hour
     *
     * @param date
     * @return
     */
    public static int getHour(Date date) {
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * get current hour
     *
     * @return
     */
    public static int getCurrentHour() {
        c.setTime(currentDate());
        return c.get(Calendar.HOUR_OF_DAY);
    }


    /**
     * minute
     *
     * @param date
     * @return
     */
    public static int getMinute(Date date) {
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    /**
     * current minute
     *
     * @return
     */
    public static int getCurrentMinute() {
        c.setTime(currentDate());
        return c.get(Calendar.MINUTE);
    }


    /**
     * get second
     *
     * @param date
     * @return
     */
    public static int getSecond(Date date) {
        c.setTime(date);
        return c.get(Calendar.SECOND);
    }

    /**
     * current second
     *
     * @return
     */
    public static int getCurrentSecond() {
        c.setTime(currentDate());
        return c.get(Calendar.SECOND);
    }

    /**
     * add years
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addYears(Date date, int amount) {
        c.setTime(date);
        c.set(Calendar.YEAR, amount);
        return c.getTime();
    }

    /**
     * add months
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addMonths(Date date, int amount) {
        c.setTime(date);
        c.set(Calendar.MONTH, amount);
        return c.getTime();
    }


    /**
     * add minutes
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addMinutes(Date date, int amount) {
        c.setTime(date);
        c.set(Calendar.MINUTE, amount);
        return c.getTime();
    }

    /**
     * add seconds
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addSeconds(Date date, int amount) {
        c.setTime(date);
        c.set(Calendar.SECOND, amount);
        return c.getTime();
    }


    /**
     * add hours
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addHours(Date date, int amount) {
        c.setTime(date);
        c.set(Calendar.HOUR, amount);
        return c.getTime();
    }


    /**
     * beforeYears
     *
     * @param beforeDate
     * @param afterDate
     * @return
     */
    public static int beforeYears(Date beforeDate, Date afterDate) {
        Calendar beforeCalendar = c;
        beforeCalendar.setTime(beforeDate);
        beforeCalendar.set(Calendar.MONTH, 1);
        beforeCalendar.set(Calendar.DAY_OF_MONTH, 1);
        beforeCalendar.set(Calendar.HOUR, 0);
        beforeCalendar.set(Calendar.MINUTE, 0);
        beforeCalendar.set(Calendar.SECOND, 0);
        Calendar afterCalendar = Calendar.getInstance();
        afterCalendar.setTime(afterDate);
        afterCalendar.set(Calendar.MONTH, 1);
        afterCalendar.set(Calendar.DAY_OF_MONTH, 1);
        afterCalendar.set(Calendar.HOUR, 0);
        afterCalendar.set(Calendar.MINUTE, 0);
        afterCalendar.set(Calendar.SECOND, 0);
        boolean positive = true;
        if (beforeDate.after(afterDate)) {
            positive = false;
        }
        int beforeYears = 0;
        while (true) {
            boolean yearEqual = beforeCalendar.get(Calendar.YEAR) == afterCalendar.get(Calendar.YEAR);
            if (yearEqual) {
                return beforeYears;
            }
            if (positive) {
                ++beforeYears;
                beforeCalendar.add(Calendar.YEAR, 1);
            } else {
                --beforeYears;
                beforeCalendar.add(Calendar.YEAR, -1);
            }
        }
    }


    /**
     * beforeMonths
     *
     * @param beforeDate
     * @param afterDate
     * @return
     */
    public static int beforeMonths(Date beforeDate, Date afterDate) {
        Calendar beforeCalendar = c;
        beforeCalendar.setTime(beforeDate);
        beforeCalendar.set(Calendar.DAY_OF_MONTH, 1);
        beforeCalendar.set(Calendar.HOUR, 0);
        beforeCalendar.set(Calendar.MINUTE, 0);
        beforeCalendar.set(Calendar.SECOND, 0);
        Calendar afterCalendar = c;
        afterCalendar.setTime(afterDate);
        afterCalendar.set(Calendar.DAY_OF_MONTH, 1);
        afterCalendar.set(Calendar.HOUR, 0);
        afterCalendar.set(Calendar.MINUTE, 0);
        afterCalendar.set(Calendar.SECOND, 0);
        boolean positive = true;
        if (beforeDate.after(afterDate)) {
            positive = false;
        }

        int beforeMonths = 0;

        while (true) {
            boolean yearEqual = beforeCalendar.get(Calendar.YEAR) == afterCalendar.get(Calendar.YEAR);
            boolean monthEqual = beforeCalendar.get(Calendar.MONTH) == afterCalendar.get(Calendar.MONTH);
            if (yearEqual && monthEqual) {
                return beforeMonths;
            }
            if (positive) {
                ++beforeMonths;
                beforeCalendar.add(Calendar.MONTH, 1);
            } else {
                --beforeMonths;
                beforeCalendar.add(Calendar.MONTH, -1);
            }
        }
    }

    /**
     * beforeDays
     *
     * @param beforeDate
     * @param afterDate
     * @return
     */
    public static int beforeDays(Date beforeDate, Date afterDate) {
        Calendar beforeCalendar = c;
        beforeCalendar.setTime(beforeDate);
        beforeCalendar.set(Calendar.HOUR, 0);
        beforeCalendar.set(Calendar.MINUTE, 0);
        beforeCalendar.set(Calendar.SECOND, 0);
        Calendar afterCalendar = Calendar.getInstance();
        afterCalendar.setTime(afterDate);
        afterCalendar.set(Calendar.HOUR, 0);
        afterCalendar.set(Calendar.MINUTE, 0);
        afterCalendar.set(Calendar.SECOND, 0);
        boolean positive = true;
        if (beforeDate.after(afterDate)) {
            positive = false;
        }
        int beforeDays = 0;
        while (true) {
            boolean yearEqual = beforeCalendar.get(Calendar.YEAR) == afterCalendar.get(Calendar.YEAR);
            boolean monthEqual = beforeCalendar.get(Calendar.MONTH) == afterCalendar.get(Calendar.MONTH);
            boolean dayEqual = beforeCalendar.get(Calendar.DAY_OF_MONTH) == afterCalendar.get(Calendar.DAY_OF_MONTH);
            if (yearEqual && monthEqual && dayEqual) {
                return beforeDays;
            }

            if (positive) {
                ++beforeDays;
                beforeCalendar.add(Calendar.DAY_OF_MONTH, 1);
            } else {
                --beforeDays;
                beforeCalendar.add(Calendar.DAY_OF_MONTH, -1);
            }
        }
    }

    /**
     * beforeRoundYears
     *
     * @param beforeDate
     * @param afterDate
     * @return
     */
    public static int beforeRoundYears(Date beforeDate, Date afterDate) {
        Date bDate = beforeDate;
        Date aDate = afterDate;
        boolean positive = true;
        if (beforeDate.after(afterDate)) {
            positive = false;
            bDate = afterDate;
            aDate = beforeDate;
        }

        int beforeYears = beforeYears(bDate, aDate);
        int bMonth = getMonth(bDate);
        int aMonth = getMonth(aDate);
        if (aMonth < bMonth) {
            --beforeYears;
        } else if (aMonth == bMonth) {
            int bDay = getDay(bDate);
            int aDay = getDay(aDate);
            if (aDay < bDay) {
                --beforeYears;
            }
        }

        return positive ? beforeYears : (new BigDecimal(beforeYears)).negate().intValue();
    }

    /**
     * beforeRoundAges
     *
     * @param beforeDate
     * @param afterDate
     * @return
     */
    public static int beforeRoundAges(Date beforeDate, Date afterDate) {
        Date bDate = beforeDate;
        Date aDate = afterDate;
        boolean positive = true;
        if (beforeDate.after(afterDate)) {
            positive = false;
            bDate = afterDate;
            aDate = beforeDate;
        }

        int beforeYears = beforeYears(bDate, aDate);
        int bMonth = getMonth(bDate);
        int aMonth = getMonth(aDate);
        if (aMonth < bMonth) {
            --beforeYears;
        }

        return positive ? beforeYears : (new BigDecimal(beforeYears)).negate().intValue();
    }

    /**
     * beforeRoundMonths
     *
     * @param beforeDate
     * @param afterDate
     * @return
     */
    public static int beforeRoundMonths(Date beforeDate, Date afterDate) {
        Date bDate = beforeDate;
        Date aDate = afterDate;
        boolean positive = true;
        if (beforeDate.after(afterDate)) {
            positive = false;
            bDate = afterDate;
            aDate = beforeDate;
        }

        int beforeMonths = beforeMonths(bDate, aDate);
        int bDay = getDay(bDate);
        int aDay = getDay(aDate);
        if (aDay < bDay) {
            --beforeMonths;
        }

        return positive ? beforeMonths : (new BigDecimal(beforeMonths)).negate().intValue();
    }


    /**
     * getDate
     *
     * @param year
     * @param month
     * @param date
     * @return
     */
    public static Date getDate(int year, int month, int date) {
        c.set(year, month - 1, date);
        return c.getTime();
    }

    /**
     * format
     *
     * @param mills
     * @return
     */
    public static String format(long mills) {
        c.setTimeInMillis(mills);
        return format(c.getTime());
    }

    /**
     * format
     *
     * @param mills
     * @param pattern
     * @return
     */
    public static String format(long mills, String pattern) {
        c.setTimeInMillis(mills);
        return format(c.getTime(), pattern);
    }

    /**
     * parse
     *
     * @param dateStr
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date parse(String dateStr, String pattern) throws ParseException {
        DateFormat df = new SimpleDateFormat(pattern, Locale.ENGLISH);
        return df.parse(dateStr);
    }

    /**
     * parse
     *
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date parse(String dateStr) throws ParseException {
        return parse(dateStr, DateUtils.DEFAULT_PATTERN);
    }

    /**
     * parse
     *
     * @param dateString
     * @param dateFormat
     * @param targetResultType
     * @param <T>
     * @return
     */
    public static <T extends Date> T parse(String dateString, String dateFormat, Class<T> targetResultType) {
        if (StringUtils.isEmpty(dateString)) {
            return null;
        } else {
            if (dateString.length() == 10 && dateString.indexOf("/") > -1) {
                dateFormat = "MM/dd/yyyy";
            } else if (dateString.length() == 10 && dateString.indexOf("-") > -1) {
                dateFormat = "yyyy-MM-dd";
            }

            SimpleDateFormat df = new SimpleDateFormat(dateFormat);

            try {
                long time = df.parse(dateString).getTime();
                Date t = (Date) targetResultType.getConstructor(new Class[]{Long.TYPE}).newInstance(new Object[]{Long.valueOf(time)});
                return (T) t;
            } catch (ParseException var7) {
                String errorInfo = "cannot use dateformat:" + dateFormat + " parse datestring:" + dateString;
                throw new IllegalArgumentException(errorInfo, var7);
            } catch (Exception var8) {
                throw new IllegalArgumentException("error targetResultType:" + targetResultType.getName(), var8);
            }
        }
    }

    /**
     * isYearMonth
     *
     * @param yearMonth
     * @return
     */
    public static boolean isYearMonth(Integer yearMonth) {
        String yearMonthStr = yearMonth.toString();
        return isYearMonth(yearMonthStr);
    }

    /**
     * isYearMonth
     *
     * @param yearMonthStr
     * @return
     */
    public static boolean isYearMonth(String yearMonthStr) {
        if (yearMonthStr.length() != 6) {
            return false;
        } else {
            String yearStr = yearMonthStr.substring(0, 4);
            String monthStr = yearMonthStr.substring(4, 6);

            try {
                int year = Integer.parseInt(yearStr);
                int month = Integer.parseInt(monthStr);
                return year >= 1800 && year <= 3000 ? month >= 1 && month <= 12 : false;
            } catch (Exception var5) {
                return false;
            }
        }
    }

    /**
     * parseYearMonth
     *
     * @param yearMonth
     * @return
     * @throws ParseException
     */
    public static Date parseYearMonth(Integer yearMonth) throws ParseException {
        return parse(String.valueOf(yearMonth), "yyyyMM");
    }

    /**
     * getYearMonth
     *
     * @param date
     * @return
     */
    public static Integer getYearMonth(Date date) {
        return new Integer(format(date, "yyyyMM"));
    }

    /**
     * getYearMonths
     *
     * @param from
     * @param to
     * @return
     * @throws ParseException
     */
    public static List getYearMonths(Integer from, Integer to) throws ParseException {
        List yearMonths = new ArrayList();
        Date fromDate = parseYearMonth(from);
        Date toDate = parseYearMonth(to);
        if (fromDate.after(toDate)) {
            throw new IllegalArgumentException("'from' date should before 'to' date!");
        } else {
            for (Date tempDate = fromDate; tempDate.before(toDate); tempDate = addMonths((Date) tempDate, 1)) {
                yearMonths.add(getYearMonth(tempDate));
            }

            if (!from.equals(to)) {
                yearMonths.add(to);
            }

            return yearMonths;
        }
    }

    /**
     * getIntervalDays
     *
     * @param beforeMillsDate
     * @param afterMillsDate
     * @return
     */
    public static long getIntervalDays(long beforeMillsDate, long afterMillsDate) {
        long bDate = beforeMillsDate;
        long aDate = afterMillsDate;
        if (beforeMillsDate > afterMillsDate) {
            bDate = afterMillsDate;
            aDate = beforeMillsDate;
        }

        long mills = aDate - bDate;
        return mills / 86400000L;
    }

    /**
     * getIntervalDays
     *
     * @param beforeDate
     * @param afterDate
     * @return
     */
    public static long getIntervalDays(Date beforeDate, Date afterDate) {
        Date bDate = beforeDate;
        Date aDate = afterDate;
        if (beforeDate.after(afterDate)) {
            bDate = afterDate;
            aDate = beforeDate;
        }

        long mills = aDate.getTime() - bDate.getTime();
        return mills / 24L * 3600L * 1000L;
    }

    /**
     * getIntervalSeconds
     *
     * @param beforeDate
     * @param afterDate
     * @return
     */
    public static long getIntervalSeconds(Date beforeDate, Date afterDate) {
        Date bDate = beforeDate;
        Date aDate = afterDate;
        if (beforeDate.after(afterDate)) {
            bDate = afterDate;
            aDate = beforeDate;
        }

        long mills = aDate.getTime() - bDate.getTime();
        return mills / 1000L;
    }

    /**
     * fecthAllTimeZoneIds
     *
     * @return
     */
    public static String[] fecthAllTimeZoneIds() {
        Vector v = new Vector();
        String[] ids = TimeZone.getAvailableIDs();

        for (int i = 0; i < ids.length; ++i) {
            v.add(ids[i]);
        }

        Collections.sort(v, String.CASE_INSENSITIVE_ORDER);
        v.copyInto(ids);
        v = null;
        return ids;
    }

    /**
     * getDiffTimeZoneRawOffset
     *
     * @param timeZoneId
     * @return
     */
    public static int getDiffTimeZoneRawOffset(String timeZoneId) {
        return TimeZone.getDefault().getRawOffset() - TimeZone.getTimeZone(timeZoneId).getRawOffset();
    }

    /**
     * getTimeCost
     *
     * @param beginTime
     * @return
     */
    public static String getTimeCost(Date beginTime) {
        String returnStr = "";
        long curMills = currentDate().getTime();
        long cost = (curMills - beginTime.getTime()) / 1000L;
        if (cost > 0L) {
            returnStr = cost + "s ";
        } else {
            returnStr = cost + "s ";
        }

        if ((curMills - beginTime.getTime()) % 1000L > 0L) {
            cost = (curMills - beginTime.getTime()) % 1000L;
            returnStr = returnStr + cost + "ms";
        } else {
            returnStr = returnStr + "0ms";
        }

        return returnStr;
    }

    /**
     * parseDate
     *
     * @param time
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String time, String pattern) throws ParseException {
        DateFormat formatter = new SimpleDateFormat(pattern, Locale.ENGLISH);
        return formatter.parse(time);
    }

    /**
     * getTimeStamp
     *
     * @return
     */
    public static Long getTimeStamp() {
        return Long.valueOf(System.currentTimeMillis() / 1000L);
    }

    /**
     * getSystemTimeStamp
     *
     * @return
     */
    public static Long getSystemTimeStamp() {
        return Long.valueOf(System.currentTimeMillis());
    }

}
