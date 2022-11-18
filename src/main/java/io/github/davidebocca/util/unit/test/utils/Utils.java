package io.github.davidebocca.util.unit.test.utils;

public class Utils {

	public static boolean isClassTest(Class<?> clazz) {
		String[] pathArray = clazz.getProtectionDomain().getCodeSource().getLocation().getFile().split("/");
		return pathArray[pathArray.length - 1].equals(Constants.CLASS_TARGET_PATH_TEST);
	}

}
