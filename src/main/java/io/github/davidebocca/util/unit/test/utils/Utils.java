package io.github.davidebocca.util.unit.test.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

	public static boolean isClassTest(Class<?> clazz) {
		String[] pathArray = clazz.getProtectionDomain().getCodeSource().getLocation().getFile().split("/");
		return pathArray[pathArray.length - 1].equals(Constants.CLASS_TARGET_PATH_TEST);
	}

	public static Object instantiateObject(Class<?> paramClass) throws InstantiationException, IllegalAccessException {

		Object obj = null;

		// primitives
		if (paramClass.isPrimitive()) {
			if (paramClass.equals(int.class)) {
				obj = 1;
			} else if (paramClass.equals(byte.class)) {
				obj = "a".getBytes()[0];
			} else if (paramClass.equals(short.class)) {
				obj = (short) 1;
			} else if (paramClass.equals(long.class)) {
				obj = 1l;
			} else if (paramClass.equals(float.class)) {
				obj = 1f;
			} else if (paramClass.equals(double.class)) {
				obj = 1d;
			} else if (paramClass.equals(boolean.class)) {
				obj = true;
			} else if (paramClass.equals(char.class)) {
				obj = 's';
			}
		} else if (paramClass.equals(Boolean.class)) {
			obj = Boolean.TRUE;
		} else if (paramClass.equals(List.class)) {
			obj = new ArrayList();
		} else if (paramClass.equals(Map.class)) {
			obj = new HashMap<Object, Object>();
		} else {
			obj = paramClass.newInstance();
		}

		return obj;
	}
}
