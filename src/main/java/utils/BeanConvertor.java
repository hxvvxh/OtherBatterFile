package utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BeanConvertor {
	private static final String GETMETHOD_PREFIX = "get";
	private static final String SETMETHOD_PREFIX = "set";
	private static final String BOOLEANMETHOD_PREFIX = "is";
	private static final String BOOLEANMETHOD_SUFFIX = "yn";

	public static void parseBeanToBean(Object orig, Object dest) {
		try {
			BeanUtils.copyProperties(orig, dest);
		} catch (Exception e) {
			log.debug("Util.parseBeanToBean ", e);
		}
	}

	public static List<Map<String, Object>> parseListBeansToMap(List<?> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		if (data != null) {
			for (Object datum : data) {
				list.add(parseBeanToMap(datum));
			}
		}

		return list;
	}

	public static Map<String, Object> parseBeanToMap(Object data) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (data != null) {
			Method[] methods = data.getClass().getMethods();
			Method method;
			String methodName;
			for (int i = 0; i < methods.length; i++) {
				method = methods[i];
				methodName = method.getName();
				if (methodName.startsWith(GETMETHOD_PREFIX)) {
					try {
						if (method.getReturnType().getName()
								.equals("java.util.List")) {
							setMapData(map, methodName,
									parseListBeansToMap((List<?>) method
											.invoke(data)));
						} else {
							setMapData(map, methodName, method.invoke(data));
						}
					} catch (Exception e) {
						log.debug("BeanConvertor.parseBeanToMap ", e);
					}
				}
			}
		}

		return map;
	}

	/**
	 * 根据Bean的属性名称，以Map的形式存储Bean的值
	 * 
	 * @param data
	 * @return
	 */
	public static Map<String, Object> parseBeanPropertiesToMap(Object data) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (data != null) {
			Class clazz = data.getClass();
			while (!clazz.equals(Object.class)) {
				for (Field field : clazz.getDeclaredFields()) {
					field.setAccessible(true);
					try {
						map.put(field.getName(), field.get(data));
					} catch (Exception e) {
						log.error("BeanConvertor.parseBeanPropertiesToMap ", e);
					}
				}
				clazz = clazz.getSuperclass();
			}
		}

		return map;
	}

	/**
	 * 根据Bean的属性名称，用Map中相应的值填充Bean的属性
	 * 
	 * @param data
	 * @param clazz
	 */
	public static <T> T parseMapToBeanProperties(Map data, Class<T> clazz) {
		T obj = null;
		try {
			obj = clazz.newInstance();

			if (data != null) {
				Class cls = clazz;
				while (!cls.equals(Object.class)) {
					for (Field field : cls.getDeclaredFields()) {
						field.setAccessible(true);
						try {
							if(!Modifier.isStatic(field.getModifiers())){
								field.set(obj, data.get(field.getName()));
							}
						} catch (Exception e) {
							log.error(
									"BeanConvertor.parseMapToBeanProperties ",
									e);
						}
					}
					cls = cls.getSuperclass();
				}
			}
		} catch (Exception e) {
			log.error("BeanConvertor.parseMapToBeanProperties ", e);
		}

		return obj;
	}

	private static void setMapData(Map<String, Object> dataMap,
			String methodName, Object obj) {
//		String key = methodName.substring(GETMETHOD_PREFIX.length())    //gaoyu TODO为保持报文的小驼峰格式 折衷方式
//				.toLowerCase();
		String key = methodName.substring(GETMETHOD_PREFIX.length());
		key = key.substring(0, 1).toLowerCase() + key.substring(1);
		// 报文中的总记录数要求是totalNum
		if ("totalnum".equals(key)) {
			key = "totalNum";
		}
		if (key.endsWith(BOOLEANMETHOD_SUFFIX)) {
			dataMap.put(parseYNMethodName(key), parseYNToBoolean(obj));
		} else {
			dataMap.put(key, obj);
		}
	}

	private static String parseYNMethodName(String methodName) {
		return BOOLEANMETHOD_PREFIX
				+ methodName.substring(0, methodName.length()
						- BOOLEANMETHOD_SUFFIX.length());
	}

	public static Boolean parseYNToBoolean(Object yn) {
		return yn == null ? null : Const.YN_Y.equalsIgnoreCase(yn.toString());
//		return yn == null ? null : Const.YN_Y.equals(Integer.parseInt(yn.toString()));
	}
	
	public static <T> List<T> parseListMapToBeanProperties(List<Map<String, Object>> data, Class<T> clazz) {
		List<T> list = new ArrayList<T>();

		if (data != null) {
			for (Map<String, Object> datum : data) {
				list.add(parseMapToBeanProperties(datum, clazz));
			}
		}

		return list;
	}
}
