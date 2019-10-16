package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具类
 *
 * @author lubiao
 *
 */
public class DateUtil {

	public static final ThreadLocal<DateFormat> DATE_FORMATTER1 = new ThreadLocal<DateFormat>() {
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};
	public static final ThreadLocal<DateFormat> DATE_FORMATTER2 = new ThreadLocal<DateFormat>() {
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy/MM/dd");
		}
	};
	public static final ThreadLocal<DateFormat> DATE_FORMATTER3 = new ThreadLocal<DateFormat>() {
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMdd");
		}
	};
	public static final ThreadLocal<DateFormat> DATETIME_FORMATTER1 = new ThreadLocal<DateFormat>() {
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};
	public static final ThreadLocal<DateFormat> DATETIME_FORMATTER2 = new ThreadLocal<DateFormat>() {
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		}
	};
	public static final ThreadLocal<DateFormat> DATETIME_FORMATTER3 = new ThreadLocal<DateFormat>() {
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMddHHmmss");
		}
	};
	public static final ThreadLocal<DateFormat> TIME_FORMATTER1 = new ThreadLocal<DateFormat>() {
		protected DateFormat initialValue() {
			return new SimpleDateFormat("HH:mm:ss");
		}
	};
	public static final ThreadLocal<DateFormat> TIME_FORMATTER2 = new ThreadLocal<DateFormat>() {
		protected DateFormat initialValue() {
			return new SimpleDateFormat("HHmmss");
		}
	};
	public static final ThreadLocal<DateFormat> TIME_FORMATTER3 = new ThreadLocal<DateFormat>() {
		protected DateFormat initialValue() {
			return new SimpleDateFormat("HHmmssSSS");
		}
	};

	public static String dateToDateFormat(Date date) {
		if (date == null) {
			return null;
		}

		try {
			return DATE_FORMATTER1.get().format(date);
		} catch (Exception e) {
			try {
				return DATE_FORMATTER2.get().format(date);
			} catch (Exception ex) {
				return DATE_FORMATTER3.get().format(date);
			}
		}
	}

	public static String dateToDateTimeFormat(Date date) {
		if (date == null) {
			return null;
		}

		try {
			return DATETIME_FORMATTER1.get().format(date);
		} catch (Exception e) {
			try {
				return DATETIME_FORMATTER2.get().format(date);
			} catch (Exception ex) {
				return DATETIME_FORMATTER3.get().format(date);
			}
		}
	}

	public static String dateToDateTimeFormat(Date date, DateFormat formator) {
		if (date == null) {
			return null;
		}
		return formator.format(date);
	}

	public static String dateToDateTimeFormat(Date date, String strformatDefine) {
		if (date == null || strformatDefine == null) {
			return null;
		}
		if ("yyyy-MM-dd".equals(strformatDefine)) {
			return DATE_FORMATTER1.get().format(date);
		} else if ("yyyy/MM/dd".equals(strformatDefine)) {
			return DATE_FORMATTER2.get().format(date);
		} else if ("yyyyMMdd".equals(strformatDefine)) {
			return DATE_FORMATTER3.get().format(date);
		} if ("yyyy-MM-dd HH:mm:ss".equals(strformatDefine)) {
			return DATETIME_FORMATTER1.get().format(date);
		} else if ("yyyyMMddHHmmss".equals(strformatDefine)) {
			return DATETIME_FORMATTER3.get().format(date);
		} else if ("HHmmss".equals(strformatDefine)) {
			return TIME_FORMATTER2.get().format(date);
		} else {
			return dateToTimeFormat(date);
		}

	}

	public static String dateToTimeFormat(Date date) {
		if (date == null) {
			return null;
		}
		try {
			return TIME_FORMATTER1.get().format(date);
		} catch (Exception e) {
			return TIME_FORMATTER2.get().format(date);
		}
	}

	public static String dateToObject(Date datetime) {
		if (datetime == null) {
			return null;
		}
		String time = dateToTimeFormat(datetime);
		if ("00:00:00".equals(time)) {
			return dateToDateFormat(datetime);
		} else {
			return dateToDateTimeFormat(datetime);
		}
	}

	public static Date dateFormatToDate(String formatStr) {
		if (formatStr == null) {
			return null;
		}

		try {
			return DATE_FORMATTER1.get().parse(formatStr);
		} catch (Exception e) {
			try {
				return DATE_FORMATTER2.get().parse(formatStr);
			} catch (Exception ex) {
				try {
					return DATE_FORMATTER3.get().parse(formatStr);
				} catch (ParseException pe) {
					throw new RuntimeException(pe);
				}
			}
		}
	}

	public static Date dateTimeFormatToDate(String formatStr) {
		if (formatStr == null) {
			return null;
		}

		try {
			return DATETIME_FORMATTER1.get().parse(formatStr);
		} catch (Exception e) {
			try {
				return DATETIME_FORMATTER2.get().parse(formatStr);
			} catch (Exception ex) {
				try {
					return DATETIME_FORMATTER3.get().parse(formatStr);
				} catch (ParseException pe) {
					throw new RuntimeException(pe);
				}
			}
		}
	}

	public static Date dateTimeFormatToDate(String formatStr, String strformatDefine) {
		if (formatStr == null || strformatDefine == null) {
			return null;
		}
		try {
			if (strformatDefine.equals("yyyy-MM-dd")) {
				return DATE_FORMATTER1.get().parse(formatStr);
			} else if (strformatDefine.equals("yyyy/MM/dd")) {
				return DATE_FORMATTER2.get().parse(formatStr);
			} else if (strformatDefine.equals("yyyyMMdd")) {
				return DATE_FORMATTER3.get().parse(formatStr);
			}
			if (strformatDefine.equals("yyyy-MM-dd HH:mm:ss")) {
				return DATETIME_FORMATTER1.get().parse(formatStr);
			} else if (strformatDefine.equals("yyyyMMdd HH:mm:ss")) {
				return DATETIME_FORMATTER2.get().parse(formatStr);
			} else if (strformatDefine.equals("yyyyMMddHHmmss")) {
				return DATETIME_FORMATTER3.get().parse(formatStr);
			} else {
				return timeFormatToDate(formatStr);
			}
		} catch (ParseException pe) {
			throw new RuntimeException(pe);
		}

	}

	public static Date timeFormatToDate(String formatStr) {
		if (formatStr == null) {
			return null;
		}
		try {
			return TIME_FORMATTER1.get().parse(formatStr);
		} catch (ParseException pe) {
			throw new RuntimeException(pe);
		}
	}

	public static Date objectToDate(String formatStr) {
		if (formatStr == null || "".equals(formatStr)) {
			return null;
		}
		if (formatStr.length() > 10) {
			return dateTimeFormatToDate(formatStr);
		} else {
			return dateFormatToDate(formatStr);
		}
	}

	public static Date getDate() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	public static Date addDate(int field, Date date, int num) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, num);
		return calendar.getTime();
	}

	public static Date addDays(Date date, int days) {
		return addDate(Calendar.DATE, date, days);
	}

	public static int getYear(Date date) {
		if (date == null) {
			return Calendar.getInstance().get(Calendar.YEAR);
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	public static Date setDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		date = calendar.getTime();
		return date;
	}

}