package com.qingdao.marathon.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.qingdao.marathon.base.Pagination;
import com.qingdao.marathon.user.model.OrderInfo;
import com.qingdao.marathon.user.service.impl.OrderServiceImpl;

public class MarathonUtil {

	public static boolean checkObjFieldIsNull(Object obj) throws IllegalAccessException {

		boolean flag = false;
		for (Field f : obj.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			if (f.get(obj) == null) {
				flag = true;
				return flag;
			}
		}
		return flag;
	}

	public static List<String> getDeclaredFields(Field[] fields) {
		List<String> list = new ArrayList<String>();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				if (field.getType().getName().startsWith("java.lang")) {
					list.add(field.getName());
				} else if (field.getType().isAssignableFrom(List.class)) {
					Type genericType = field.getGenericType();
					if (genericType == null)
						continue;
					if (genericType instanceof ParameterizedType) {
						ParameterizedType pt = (ParameterizedType) genericType;
						Class genericClazz = (Class) pt.getActualTypeArguments()[0];
						list.addAll(getDeclaredFields(genericClazz.getDeclaredFields()));
					}
				}
			}
		}
		return list;
	}

	public static Method[] getMethod(Class clazz) throws IntrospectionException {
		Field[] fields = clazz.getDeclaredFields();// 获得属性
		Method[] methods = new Method[fields.length];
		PropertyDescriptor pd;
		for (int i=0;i<fields.length;i++) {
			pd = new PropertyDescriptor(fields[i].getName(), clazz);
			Method getMethod = pd.getReadMethod();// 获得get方法
			methods[i] = getMethod;
		}
		
		return methods;
	}

	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		Method methods = OrderServiceImpl.class.getDeclaredMethod("queryList" , Pagination.class,Map.class,Boolean.class);
		System.out.println(methods.getName());
	}
}
