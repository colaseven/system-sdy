package com.sdy.core.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {


    private final static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final long TIME_MILLIS_OF_ONE_DAY = 86400000L;
    public static final long TIME_MILLIS_OF_18991230 = -2209190400000L;
    private static final Object[][] ARY_PARSE_DATE_FORMAT_PATTERN1 = new Object[][]{{"^\\d{1,2}-\\d{1,2}月-\\d{1,2}$", "d-M月-yy", null}, {"^\\d{1,2}-\\d{1,2}月\\s-\\d{1,2}$", "d-M月 -yy", null}, {"^\\d{4}年\\d{1,2}月\\d{1,2}日$", "yyyy年M月d日", null}, {"^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-M-d", null}, {"^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/M/d", null}, {"^\\d{2}-\\d{1,2}-\\d{1,2}", "yy-M-d", null}, {"^\\d{4}-\\d{1,2}-\\d{1,2}\\s上午\\s\\d{1,2}:\\d{1,2}:\\d{1,2}$", "yyyy-M-d 上午 h:m:s", null}, {"^\\d{4}-\\d{1,2}-\\d{1,2}\\s下午\\s\\d{1,2}:\\d{1,2}:\\d{1,2}$", "yyyy-M-d 下午 h:m:s", new Long(43200000L)}, {"^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{1,3}$", "yyyy-M-d HH:mm:ss.SSS", null}, {"^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}$", "yyyy-M-d HH:mm:ss", null}, {"^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}$", "yyyy-M-d HH:mm", null}, {"^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-M-d", null}, {"^\\d{4}\\.\\d{1,2}\\.\\d{1,2}$", "yyyy.M.d", null}, {"^\\d{4}-\\d{1,2}$", "yyyy-M", null}, {"\\d{1,2}\\-[A-Za-z]{3}\\-\\d{4}\\s\\d{1,2}\\:\\d{1,2}\\:\\d{1,2}", "dd-MMM-yyyy HH:mm:ss", null}, {"\\d{1,2}\\-[A-Za-z]{3}\\-\\d{4}\\s\\d{1,2}\\:\\d{1,2}", "dd-MMM-yyyy HH:mm", null}, {"\\d{1,2}\\-[A-Za-z]{3}\\-\\d{4}", "dd-MMM-yyyy", null}, {"^\\d{4}\\d{2}\\d{2}\\d{2}\\d{2}\\d{2}$", "yyyyMMddHHmmss", null}, {"^\\d{4}\\d{2}\\d{2}\\d{2}\\d{2}$", "yyyyMMddHHmm", null}, {"^\\d{4}\\d{2}\\d{2}$", "yyyyMMdd", null}, {"^\\d{4}年$", "yyyy年", null}, {"^\\d{4}$", "yyyy", null}, {"^\\d{2}\\d{2}\\d{2}$", "yyMMdd", null}};
    private static final Map<String, DateUtil.DateParserByPattern> MAP_PARSE_DATE_FORMAT_PATTERN2 = new LinkedHashMap();
    public static final String TIME_MIN = " 00:00:00";
    public static final String TIME_MAX = " 23:59:59";

    static {
        MAP_PARSE_DATE_FORMAT_PATTERN2.put("\\d{4}-\\d{2}-\\d{2}T\\d{2}\\:\\d{2}\\:\\d{2}\\.\\d{7}\\+\\d{2}\\:\\d{2}", new DateUtil.DateParserByPattern() {
            public Date parseDateFormat(String strdate) {
                int p = strdate.indexOf(".");
                strdate = strdate.substring(0, p).replaceAll("T", " ");
                return DateUtil.parseToDate(strdate);
            }
        });
        MAP_PARSE_DATE_FORMAT_PATTERN2.put("\\d{5}", new DateUtil.DateParserByPattern() {
            public Date parseDateFormat(String strdate) {
                long delta = Long.parseLong(strdate);
                return new Date(-2209190400000L + delta * 86400000L);
            }
        });
        MAP_PARSE_DATE_FORMAT_PATTERN2.put("\\d{5}.\\d{12}", new DateUtil.DateParserByPattern() {
            public Date parseDateFormat(String strdate) {
                int p = strdate.indexOf(".");
                long delta = Long.parseLong(strdate.substring(0, p));
                double delta2 = Double.parseDouble("0" + strdate.substring(p));
                return new Date(-2209190400000L + (long) (((double) delta + delta2) * 8.64E7D));
            }
        });
    }


    public static Date parseToDate(String strdate) {
        if (strdate != null && !strdate.equals("")) {
            try {
                strdate = strdate.replaceAll(" am ", " 上午 ").replaceAll(" AM ", " 上午 ").replaceAll(" pm ", " 下午 ").replaceAll(" PM ", " 下午 ").replaceAll("/", "-").trim();
                Object[][] var4 = ARY_PARSE_DATE_FORMAT_PATTERN1;
                int var3 = ARY_PARSE_DATE_FORMAT_PATTERN1.length;

                for (int var2 = 0; var2 < var3; ++var2) {
                    Object[] element = var4[var2];
                    String strregexp = (String) element[0];
                    String strpattern = (String) element[1];
                    Long addtime = (Long) element[2];
                    Pattern pattern = Pattern.compile(strregexp);
                    Matcher matcher = pattern.matcher(strdate);
                    if (matcher.find()) {
                        SimpleDateFormat sdf = new SimpleDateFormat(strpattern, Locale.ENGLISH);
                        Date date = sdf.parse(strdate);
                        if (addtime != null) {
                            date.setTime(date.getTime() + addtime.longValue());
                        }

                        return date;
                    }
                }

                Iterator var14 = MAP_PARSE_DATE_FORMAT_PATTERN2.entrySet().iterator();

                while (var14.hasNext()) {
                    Map.Entry<String, DateUtil.DateParserByPattern> entry = (Map.Entry) var14.next();
                    if (strdate.matches((String) entry.getKey())) {
                        return ((DateUtil.DateParserByPattern) entry.getValue()).parseDateFormat(strdate);
                    }
                }
            } catch (Exception var12) {
                var12.printStackTrace();
                return null;
            }

            logger.info("DateUtil.formatDate('" + strdate + "')，无法匹配日期格式，请予以关注。");
            return null;
        } else {
            return null;
        }
    }


    /**
     * 获取YYYY格式
     *
     * @return
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 获取YYYY格式
     *
     * @return
     */
    public static String getYear(Date date) {
        return formatDate(date, "yyyy");
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDay() {
        return formatDate(new Date(), "yyyy-MM-dd");
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDay(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getDays() {
        return formatDate(new Date(), "yyyyMMdd");
    }

    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getDays(Date date) {
        return formatDate(date, "yyyyMMdd");
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss.SSS格式
     *
     * @return
     */
    public static String getMsTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
    }

    /**
     * 获取YYYYMMDDHHmmss格式
     *
     * @return
     */
    public static String getAllTime() {
        return formatDate(new Date(), "yyyyMMddHHmmss");
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDate(Date date, String pattern) {
        String formatDate = null;
        if (StringUtils.isNotBlank(pattern)) {
            formatDate = DateFormatUtils.format(date, pattern);
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * @param s
     * @param e
     * @return boolean
     * @throws
     * @Title: compareDate
     * @Description:(日期比较，如果s>=e 返回true 否则返回false)
     */
    public static boolean compareDate(String s, String e) {
        if (parseDate(s) == null || parseDate(e) == null) {
            return false;
        }
        return parseDate(s).getTime() >= parseDate(e).getTime();
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static Date parseDate(String date) {
        return parse(date, "yyyy-MM-dd");
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static Date parseTime(String date) {
        return parse(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static Date parse(String date, String pattern) {
        try {
            return DateUtils.parseDate(date, pattern);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static String convertDateFormat(String strDate, String toFormat) {
        Date date = parseToDate(strDate);
        if (date == null) {
            return "";
        } else {
            if (toFormat == null) {
                toFormat = "yyyy-MM-dd HH:mm:ss";
            }

            SimpleDateFormat sdf2 = new SimpleDateFormat(toFormat);
            return sdf2.format(date);
        }
    }

    /**
     * 把日期转换为Timestamp
     *
     * @param date
     * @return
     */
    public static Timestamp format(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * 校验日期是否合法
     *
     * @return
     */
    public static boolean isValidDate(String s) {
        return parse(s, "yyyy-MM-dd HH:mm:ss") != null;
    }

    /**
     * 校验日期是否合法
     *
     * @return
     */
    public static boolean isValidDate(String s, String pattern) {
        return parse(s, pattern) != null;
    }

    public static int getDiffYear(String startTime, String endTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(
                    startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
            return years;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }

    /**
     * <li>功能描述：时间相减得到天数
     *
     * @param beginDateStr
     * @param endDateStr
     * @return long
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;

        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        // System.out.println("相隔的天数="+day);

        return day;
    }

    /**
     * 得到n天之后的日期
     *
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);

        return dateStr;
    }

    /**
     * 得到n天之后是周几
     *
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);

        return dateStr;
    }


//	public static String buildDateValue(Object value){
//		if(Func.isOracle()){
//			return "to_date('"+ value +"','yyyy-mm-dd HH24:MI:SS')";
//		}else{
//			return Func.toStr(value);
//		}
//	}

    public static void main(String[] args) {
        System.out.println(getTime(new Date()));
        System.out.println(getAfterDayWeek("3"));
    }

    interface DateParserByPattern {
        Date parseDateFormat(String var1);
    }

}
