package io.github.davidebocca.util.unit.test.rules.conf;

import java.util.ArrayList;
import java.util.List;

public class PackageConf {

	private String name;
	private List<String> excludedList;
	private boolean recursive;
	private boolean excludeNestedClasses;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isRecursive() {
		return recursive;
	}

	public List<String> getExcludedList() {
		if (excludedList == null) {
			excludedList = new ArrayList<String>();
		}
		return excludedList;
	}

	public void setRecursive(boolean recursive) {
		this.recursive = recursive;
	}

	public boolean isExcludeNestedClasses() {
		return excludeNestedClasses;
	}

	public void setExcludeNestedClasses(boolean excludeNestedClasses) {
		this.excludeNestedClasses = excludeNestedClasses;
	}

	@Override
	public String toString() {
		return "PackageConf [name=" + name + ", excludedList=" + excludedList + ", recursive=" + recursive + ", excludeNestedClasses=" + excludeNestedClasses + "]";
	}

}
