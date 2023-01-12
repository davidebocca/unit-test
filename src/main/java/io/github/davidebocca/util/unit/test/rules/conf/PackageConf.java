package io.github.davidebocca.util.unit.test.rules.conf;

public class PackageConf {

	private String name;
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

	public void setRecursive(boolean recursive) {
		this.recursive = recursive;
	}

	@Override
	public String toString() {
		return "UnitTestPackageConf [name=" + name + ", recursive=" + recursive + "]";
	}

	public boolean isExcludeNestedClasses() {
		return excludeNestedClasses;
	}

	public void setExcludeNestedClasses(boolean excludeNestedClasses) {
		this.excludeNestedClasses = excludeNestedClasses;
	}
}
