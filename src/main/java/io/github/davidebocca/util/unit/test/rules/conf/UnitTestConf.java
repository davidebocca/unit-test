package io.github.davidebocca.util.unit.test.rules.conf;

import java.util.ArrayList;
import java.util.List;

public class UnitTestConf {

	private List<PackageConf> packages;
	private List<ClassConf> classes;

	private boolean includeTestClasses;

	public List<PackageConf> getPackages() {
		if (packages == null) {
			packages = new ArrayList<>();
		}
		return packages;
	}

	public void setPackages(List<PackageConf> packages) {
		this.packages = packages;
	}

	public List<ClassConf> getClasses() {
		if (classes == null) {
			classes = new ArrayList<>();
		}
		return classes;
	}

	public void setClasses(List<ClassConf> classes) {
		this.classes = classes;
	}

	public boolean isIncludeTestClasses() {
		return includeTestClasses;
	}

	public void setIncludeTestClasses(boolean includeTestClasses) {
		this.includeTestClasses = includeTestClasses;
	}

}
