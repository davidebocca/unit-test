package io.github.davidebocca.util.unit.test.rules.conf;

import java.util.ArrayList;
import java.util.List;

public class ClassExclusionConf {

	private List<Class<?>> classesToExclude;

	public List<Class<?>> getClassesToExclude() {
		if (classesToExclude == null) {
			classesToExclude = new ArrayList<>();
		}
		return classesToExclude;
	}

	public void setClassesToExclude(List<Class<?>> classesToExclude) {
		this.classesToExclude = classesToExclude;
	}

}
