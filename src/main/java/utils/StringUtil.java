package utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class StringUtil {
	public static boolean isObjectEmpty(Object obj) {
		return null == obj;
	}

	public static boolean isStringEmpty(String str) {
		return null == str || str.trim().length() == 0;
	}

	public static String contactString(Object value1, Object value2) {
		StringBuffer buffer = new StringBuffer();

		if (!isObjectEmpty(value1))
			buffer.append(value1);
		if (!isObjectEmpty(value2))
			buffer.append(value2);

		return buffer.toString();
	}

	public static String parseObjectToString(Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof String) {
			return (String) object;
		} else {
			return object.toString();
		}
	}

	public static String buildString(Object... args) {
		if (args == null)
			return null;

		StringBuffer buffer = new StringBuffer();

		for (Object object : args) {
			if (object != null) {
				buffer.append(object);
			}
		}

		return buffer.toString();
	}

	public static BigDecimal parseBigDecimal(Object value) {

		if (value instanceof BigDecimal) {
			try {
				return (BigDecimal) value;
			} catch (Exception e) {
				return null;
			}
		} else {
			try {
				return new BigDecimal(value.toString());
			} catch (Exception e) {
				return null;
			}
		}

	}

	public static Integer parseInteger(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Integer) {
			try {
				return (Integer) value;
			} catch (Exception e) {
				return null;
			}
		} else {
			try {
				return new Integer(value.toString());
			} catch (Exception e) {
				return null;
			}
		}

	}

	public static Long parseLong(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Long) {
			try {
				return (Long) value;
			} catch (Exception e) {
				return null;
			}
		} else {
			try {
				return new Long(value.toString());
			} catch (Exception e) {
				return null;
			}
		}

	}

	public static String replace(String str, String replaced, String replaceWith) {
		if (str == null) {
			return null;
		}
		if (replaced == null || replaceWith == null) {
			return str;
		}
		StringBuffer buf = new StringBuffer();

		int pos = str.indexOf(replaced);
		if (pos > -1) {
			String left = str.substring(0, pos);
			String right = str.substring(pos + replaced.length());
			buf.append(left);
			buf.append(replaceWith);
			buf.append(replace(right, replaced, replaceWith));
		} else {
			buf.append(str);
		}

		return buf.toString();
	}

	public static final boolean toBoolean(String str) {
		return toBoolean(str, false);
	}

	public static final boolean toBoolean(String str, boolean defaultValue) {
		if (isStringEmpty(str)) {
			return defaultValue;
		} else
			return Boolean.valueOf(str.trim());

	}

	public static final int toInt(String str) {
		return toInt(str, 0);
	}

	public static final int toInt(String str, int defaultValue) {
		if (isStringEmpty(str)) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(str.trim());
		} catch (Throwable t) {
			return defaultValue;
		}
	}

	/**
	 * 将字符串填充到len长,不足len长前面加0,超过截断
	 *
	 * @param str
	 * @param len
	 *            是指字节长度
	 * @return
	 */
	public static String formatLeftStr(Object object, int len) {
		String str = (object == null) ? "" : object.toString().trim();

		while (str.getBytes().length > len) {
			str = str.substring(0, str.length() - 1);
		}

		int strLen = str.getBytes().length;

		str = repeat("0", len - strLen) + str;
		return str;
	}

	public static String repeat(String str, int num) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < num; i++) {
			buf.append(str);
		}
		return buf.toString();
	}

	/**
	 *
	 * 金额格式化
	 *
	 * @author csii_xujin
	 * @param s
	 * @param len
	 * @return
	 * @since JDK 1.6
	 */
	public static String formatMoney(BigDecimal amt, int len) {
		NumberFormat fmt = null;
		if (len == 0) {
			fmt = new DecimalFormat("###,###");
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("###,###.");
			for (int i = 0; i < len; i++) {
				sb.append("#");
			}
			fmt = new DecimalFormat(sb.toString());
		}
		String result = fmt.format(amt);
		if (result.indexOf(".") == -1) {
			result = result + ".00";
		}
		return result;

	}

	/*
	 * SHA1签名
	 */
	public static String getSHA1(String decript, String charset) throws UnsupportedEncodingException {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
			digest.update(decript.getBytes(charset));
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString().toUpperCase();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 验证字符串是否有sum个连续相同的数值； Y--存在sum个连续相同数值，N--相反
	 * 
	 * @param data
	 * @param sum
	 * @return
	 */
	public static String checkSameSumData(String data, int sum) {
//	public static int checkSameSumData(String data, int sum) {
		int back;
		int add = 1;
		char[] dataArray = data.toCharArray();
		back = dataArray[0];
		for (int j = 1; j < dataArray.length; j++) {
			if (back == dataArray[j]) {
				add += 1;
				if (add >= sum) {
					return Const.YN_Y;
				}
			} else {
				add = 1;
			}
			back = dataArray[j];
		}
		return Const.YN_N;
	}

	/*
	*公有截取方法
	* */

	public static List<String> subString(String subNo ){
		String[] split = subNo.split(",");
		List<String> subNoList = new ArrayList<String>();
		for (String str : split) {
			subNoList.add(str);
		}
     return subNoList;
	}

}
