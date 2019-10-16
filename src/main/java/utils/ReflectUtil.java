package utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectUtil {

	/**
	 * 获取入参类及父类的所有属性字段
	 * 
	 * @param object
	 * @return Field数组
	 */
	public static Field[] getAllFields(Object object) {
		List<Field> fieldList = getAllFieldList(object);
		Field[] fields = new Field[fieldList.size()];
		fieldList.toArray(fields);

		return fields;
	}

	/**
	 * 获取入参类及父类的所有属性字段
	 * 
	 * @param object
	 * @return FieldList
	 */
	public static List<Field> getAllFieldList(Object object) {
		Class clazz = object.getClass();
		List<Field> fieldList = new ArrayList<>();
		while (clazz != null) {
			fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
			clazz = clazz.getSuperclass();
		}
		return fieldList;
	}

	/**
	 * 获取入参类及父类的所有方法
	 * 
	 * @param object
	 * @return Method数组
	 */
	public static Method[] getAllMehods(Object object) {
		List<Method> methodList = getAllMehodList(object);
		Method[] methods = new Method[methodList.size()];
		methodList.toArray(methods);

		return methods;

	}

	/**
	 * 获取入参类及父类的所有方法
	 * 
	 * @param object
	 * @return MethodList
	 */
	public static List<Method> getAllMehodList(Object object) {
		Class clazz = object.getClass();
		List<Method> methodList = new ArrayList<>();
		while (clazz != null) {
			methodList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredMethods())));
			clazz = clazz.getSuperclass();
		}
		return methodList;
	}

	/**
	 * 对比两个对象的内容是否相同
	 * 
	 * @param o1
	 *            对象1
	 * @param o2
	 *            对象2
	 * @return boolean类型
	 */
	public static boolean compareTwo(Object o1, Object o2) {
		if (o1 == null && o2 == null) {
			return true;
		}
		if (o1 == null && o2 != null) {
			return false;
		}
		if (o1.equals(o2)) {
			return true;
		}
		return false;
	}
}
