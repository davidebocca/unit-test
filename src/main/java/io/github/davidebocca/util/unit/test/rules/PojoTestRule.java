/**
 * 
 */
package io.github.davidebocca.util.unit.test.rules;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.filters.FilterPackageInfo;
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
	private List<PojoClass> clazzListRecursively = new ArrayList<>();
	private List<Class<?>> clazzList = new ArrayList<>();

	private Validator validator;

	@Override
	public RuleIdEnum getRuleId() {
		return RuleIdEnum.POJO;
	}

	public PojoTestRule withPojoPackagesRecursively(String[] pojoPackages) throws UnitTestException {
		this.pojoPackageRecursively = pojoPackages;
		return this;
	}

	public PojoTestRule withPojoPackages(String[] pojoPackages) throws UnitTestException {

		if (pojoPackages == null || pojoPackages.length == 0) {
			LoggingUtils.manageError(ErrorCodeEnum.POJO_001);
			throw new UnitTestException(ErrorCodeEnum.POJO_001);
		}

		this.pojoPackages = pojoPackages;
		return this;
	}

	public PojoTestRule withPojoClasses(List<Class<?>> pojoClasses) throws UnitTestException {

		if (pojoClasses == null || pojoClasses.isEmpty()) {
			LoggingUtils.manageError(ErrorCodeEnum.POJO_002);
			throw new UnitTestException(ErrorCodeEnum.POJO_002);
		}

		this.clazzList = pojoClasses;

		return this;
	}

	@Override
	public void executeTest() throws UnitTestException {

		try {

			// openPojo initialization
			initializeOpenPojoValidator();

			// package validation

			for (String pack : this.pojoPackageRecursively) {

				clazzListRecursively = PojoClassFactory.getPojoClassesRecursively(pack, null);

				for (PojoClass clazz : clazzListRecursively) {
					openPojoTestClass(clazz);
				}

			}

			for (String pack : this.pojoPackages) {

				LoggingUtils.logTestStep(RuleIdEnum.POJO, "Testing package ".concat(pack));

				openPojoTestPack(pack);
			}

			for (Class<?> clazz : this.clazzList) {

				LoggingUtils.logTestStep(RuleIdEnum.POJO, "Testing class ".concat(clazz.getName()));

				openPojoTestClass(clazz);
			}

		} catch (AssertionError e) {
			LoggingUtils.manageError(ErrorCodeEnum.POJO_003, ExceptionUtils.getRootCauseMessage(e));
			throw new UnitTestException(ErrorCodeEnum.POJO_003, ExceptionUtils.getRootCauseMessage(e));
		}

	}

	private void initializeOpenPojoValidator() {

		validator = ValidatorBuilder.create()
				.with(new GetterMustExistRule())
				.with(new SetterMustExistRule())
				.with(new SetterTester())
				.with(new GetterTester())
				.with(new SerializableMustHaveSerialVersionUIDRule())
				.build();

	}

	private void openPojoTestPack(String pack) {
		validator.validate(pack, new FilterPackageInfo());
	}

	private void openPojoTestClass(Class<?> clazz) {
		openPojoTestClass(PojoClassFactory.getPojoClass(clazz));
	}

	private void openPojoTestClass(PojoClass clazz) {
		validator.validate(clazz);
	}

}
