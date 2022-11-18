package io.github.davidebocca.util.unit.test.utils;

import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;

public class MyPojoFilter implements PojoClassFilter {

	private List<Class<?>> classesToExclude;

	public MyPojoFilter(List<Class<?>> classesToExclude) {
		super();
		this.classesToExclude = classesToExclude;
	}

	@Override
	public boolean include(PojoClass pojoClass) {
		return !classesToExclude.contains(pojoClass.getClazz());
	}

}
