package io.github.davidebocca.util.unit.test.rules.conf;

import java.util.ArrayList;
import java.util.List;

public class FieldExclusionConf {

	private Class<?> clazz;
	private List<String> fieldsToExclude;

	public FieldExclusionConf() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FieldExclusionConf(Class<?> clazz, List<String> fieldsToExclude) {
		super();
		this.clazz = clazz;
		this.fieldsToExclude = fieldsToExclude;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public List<String> getFieldsToExclude() {
		if (fieldsToExclude == null) {
			fieldsToExclude = new ArrayList<>();
		}
		return fieldsToExclude;
	}

	public void setFieldsToExclude(List<String> fieldsToExclude) {
		this.fieldsToExclude = fieldsToExclude;
	}

}
