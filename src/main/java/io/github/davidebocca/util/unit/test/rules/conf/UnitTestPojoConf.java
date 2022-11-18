package io.github.davidebocca.util.unit.test.rules.conf;

import java.util.ArrayList;
import java.util.List;

public class UnitTestPojoConf extends UnitTestConf {

	private List<FieldExclusionConf> fieldExclusions;

	public List<FieldExclusionConf> getFieldExclusions() {
		if (fieldExclusions == null) {
			fieldExclusions = new ArrayList<>();
		}
		return fieldExclusions;
	}

	public void setFieldExclusions(List<FieldExclusionConf> fieldExclusions) {
		this.fieldExclusions = fieldExclusions;
	}

}
