package io.github.davidebocca.util.unit.test.rules.conf;

import java.util.ArrayList;
import java.util.List;

public class ClassExclusionConf {

	private List<Class<?>> classesToExclude;
	private List<String> classesToExcludeStr;

	public List<Class<?>> getClassesToExclude() {
		if (classesToExclude == null) {
			classesToExclude = new ArrayList<>();
		}
		return classesToExclude;
	}

	public void setClassesToExclude(List<Class<?>> classesToExclude) {
		this.classesToExclude = classesToExclude;
	}

	public List<String> getClassesToExcludeStr() {
		if (classesToExcludeStr == null) {
			classesToExcludeStr = new ArrayList<>();
		}
		return classesToExcludeStr;
	}

	public void setClassesToExcludeStr(List<String> classesToExcludeStr) {
		this.classesToExcludeStr = classesToExcludeStr;
	}

}
