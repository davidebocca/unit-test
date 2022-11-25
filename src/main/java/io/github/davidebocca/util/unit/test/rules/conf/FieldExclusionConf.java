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

	/**
	 * 
	 * Use this constructor to apply the exclusions to a certain class
	 * 
	 * @param clazz
	 * @param fieldsToExclude
	 */
	public FieldExclusionConf(Class<?> clazz, List<String> fieldsToExclude) {
		super();
		this.clazz = clazz;
		this.fieldsToExclude = fieldsToExclude;
	}

	/**
	 * 
	 * Use this constructor to exclude the fields from all the classes tested
	 * 
	 * @param fieldsToExclude
	 * 
	 */
	public FieldExclusionConf(List<String> fieldsToExclude) {
		super();
		this.fieldsToExclude = fieldsToExclude;
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

	public Class<?> getClazz() {
		return clazz;
	}

}
