package io.github.davidebocca.util.unit.test.rules.conf;

public class PackageConf {

	private String name;
	private boolean recursive;

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
}
