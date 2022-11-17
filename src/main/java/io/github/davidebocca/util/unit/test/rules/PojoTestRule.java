/**
 * 
 */
package io.github.davidebocca.util.unit.test.rules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SerializableMustHaveSerialVersionUIDRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

import io.github.davidebocca.util.unit.test.exception.ErrorCodeEnum;
import io.github.davidebocca.util.unit.test.exception.UnitTestException;
import io.github.davidebocca.util.unit.test.pojo.PojoClassExcludedFields;
import io.github.davidebocca.util.unit.test.rules.utils.AbstractRule;
import io.github.davidebocca.util.unit.test.rules.utils.RuleIdEnum;
import io.github.davidebocca.util.unit.test.utils.LoggingUtils;

/**
 * @author cr10248
 *
 */
public class PojoTestRule extends AbstractRule {

	private String[] pojoPackageRecursively = new String[] {};
	private String[] pojoPackages = new String[] {};
	private List<PojoClass> pojoClazzList = new ArrayList<>();
	private List<Class<?>> clazzList = new ArrayList<>();

	private Map<String, List<String>> exclusionMap;

	private Validator validator;

	@Override
	public RuleIdEnum getRuleId() {
		return RuleIdEnum.POJO;
	}

	public PojoTestRule withPojoPackagesRecursively(String[] pojoPackages, Map<String, List<String>> exclusionMap)
			throws UnitTestException {
		this.pojoPackageRecursively = pojoPackages;
		this.exclusionMap = exclusionMap;
		return this;
	}

	public PojoTestRule withPojoPackages(String[] pojoPackages, Map<String, List<String>> exclusionMap)
			throws UnitTestException {

		if (pojoPackages == null || pojoPackages.length == 0) {
			LoggingUtils.manageError(ErrorCodeEnum.POJO_001);
			throw new UnitTestException(ErrorCodeEnum.POJO_001);
		}

		this.pojoPackages = pojoPackages;
		this.exclusionMap = exclusionMap;

		return this;
	}

	public PojoTestRule withPojoClasses(List<Class<?>> pojoClasses, Map<String, List<String>> exclusionMap)
			throws UnitTestException {

		if (pojoClasses == null || pojoClasses.isEmpty()) {
			LoggingUtils.manageError(ErrorCodeEnum.POJO_002);
			throw new UnitTestException(ErrorCodeEnum.POJO_002);
		}

		this.clazzList = pojoClasses;
		this.exclusionMap = exclusionMap;

		return this;
	}

	@Override
	public void executeTest() throws UnitTestException {

		try {

			// openPojo initialization
			initializeOpenPojoValidator();

			LoggingUtils.logTestStep(RuleIdEnum.POJO, "Build class list to test");

			// package validation
			for (String pack : this.pojoPackageRecursively) {

				LoggingUtils.logTestStep(RuleIdEnum.POJO, "Adding classes recursively from package ".concat(pack));

				pojoClazzList = PojoClassFactory.getPojoClassesRecursively(pack, null);
			}

			for (String pack : this.pojoPackages) {

				LoggingUtils.logTestStep(RuleIdEnum.POJO, "Adding classes from package ".concat(pack));

				pojoClazzList.addAll(PojoClassFactory.getPojoClasses(pack));
			}

			for (Class<?> clazz : this.clazzList) {
				LoggingUtils.logTestStep(RuleIdEnum.POJO, "Adding single class ".concat(clazz.getName()));

				pojoClazzList.add(PojoClassFactory.getPojoClass(clazz));
			}

			for (PojoClass clazz : pojoClazzList) {

				if (exclusionMap != null && exclusionMap.containsKey(clazz.getName())) {
					PojoClassExcludedFields tmp = new PojoClassExcludedFields(clazz, new HashSet<>(exclusionMap.get(clazz.getName())));
					openPojoTestClass(tmp);
				} else {
					openPojoTestClass(clazz);
				}

			}

		} catch (AssertionError e) {
			LoggingUtils.manageError(ErrorCodeEnum.POJO_003, ExceptionUtils.getRootCauseMessage(e));
			throw new UnitTestException(ErrorCodeEnum.POJO_003, ExceptionUtils.getRootCauseMessage(e));
		}

	}

	private void initializeOpenPojoValidator() {

		validator = ValidatorBuilder.create().with(new GetterMustExistRule()).with(new SetterMustExistRule())
				.with(new SetterTester()).with(new GetterTester()).with(new SerializableMustHaveSerialVersionUIDRule())
				.build();

	}

	private void openPojoTestClass(PojoClass clazz) {
		validator.validate(clazz);
	}

}
