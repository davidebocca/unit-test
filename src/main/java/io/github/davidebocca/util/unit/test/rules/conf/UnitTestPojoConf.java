package io.github.davidebocca.util.unit.test.rules.conf;

import java.util.ArrayList;
import java.util.List;

public class UnitTestPojoConf extends UnitTestConf {

	private List<PojoExclusionConf> pojoExclusions;

	public List<PojoExclusionConf> getExclusions() {
		if (pojoExclusions == null) {
			pojoExclusions = new ArrayList<>();
		}
		return pojoExclusions;
	}

	public void setExclusions(List<PojoExclusionConf> exclusions) {
		this.pojoExclusions = exclusions;
	}
}
