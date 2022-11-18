/**
 * 
 */
package io.github.davidebocca.util.unit.test.rules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
import io.github.davidebocca.util.unit.test.rules.conf.ClassConf;
import io.github.davidebocca.util.unit.test.rules.conf.PackageConf;
import io.github.davidebocca.util.unit.test.rules.conf.PojoExclusionConf;
import io.github.davidebocca.util.unit.test.rules.conf.UnitTestPojoConf;
import io.github.davidebocca.util.unit.test.rules.utils.AbstractRule;
import io.github.davidebocca.util.unit.test.rules.utils.RuleIdEnum;
import io.github.davidebocca.util.unit.test.utils.LoggingUtils;

/**
 * @author cr10248
 *
 */
public class PojoTestRule extends AbstractRule {

	private UnitTestPojoConf testConf;

	private List<PojoClass> pojoClazzList = new ArrayList<>();
	private Validator validator;

	@Override
	public RuleIdEnum getRuleId() {
		return RuleIdEnum.POJO;
	}

	public PojoTestRule withConfiguration(UnitTestPojoConf testConf)
			throws UnitTestException {

		if (testConf.getClasses().size() == 0 && testConf.getPackages().size() == 0) {
			LoggingUtils.manageError(ErrorCodeEnum.POJO_001);
			throw new UnitTestException(ErrorCodeEnum.POJO_001);
		}

		this.testConf = testConf;
		return this;
	}

	@Override
	public void executeTest() throws UnitTestException {

		try {

			// openPojo initialization
			initializeOpenPojoValidator();

			LoggingUtils.logTestStep(RuleIdEnum.POJO, "Build class list to test");

			// add classes from packages
			for (PackageConf pack : testConf.getPackages()) {

				LoggingUtils.logTestStep(RuleIdEnum.POJO, "Adding classes from package ".concat(pack.toString()));

				if (pack.isRecursive()) {
					pojoClazzList = PojoClassFactory.getPojoClassesRecursively(pack.getName(), null);
				} else {
					pojoClazzList.addAll(PojoClassFactory.getPojoClasses(pack.getName()));
				}
			}

			// add single classes
			for (ClassConf cl : testConf.getClasses()) {
				LoggingUtils.logTestStep(RuleIdEnum.POJO, "Adding single class ".concat(cl.getClazz().getName()));
				pojoClazzList.add(PojoClassFactory.getPojoClass(cl.getClazz()));
			}

			for (PojoClass clazz : pojoClazzList) {

				LoggingUtils.logTestStep(RuleIdEnum.POJO, "Test class ".concat(clazz.getClazz().getName()));

				boolean found = false;

				for (PojoExclusionConf exc : testConf.getExclusions()) {
					if (exc.getClazz().equals(clazz.getClazz())) {
						PojoClassExcludedFields tmp = new PojoClassExcludedFields(clazz, new HashSet<>(exc.getFieldsToExclude()));
						openPojoTestClass(tmp);
						found = true;
						break;
					}
				}

				if (!found) {
					openPojoTestClass(clazz);
				}
			}

		} catch (AssertionError e) {
			LoggingUtils.manageError(ErrorCodeEnum.POJO_002, ExceptionUtils.getRootCauseMessage(e));
			throw new UnitTestException(ErrorCodeEnum.POJO_002, ExceptionUtils.getRootCauseMessage(e));
		}

	}

	private void initializeOpenPojoValidator() {

		validator = ValidatorBuilder
				.create()
				.with(new GetterMustExistRule())
				.with(new SetterMustExistRule())
				.with(new SetterTester())
				.with(new GetterTester())
				.with(new SerializableMustHaveSerialVersionUIDRule())
				.build();

	}

	private void openPojoTestClass(PojoClass clazz) {
		validator.validate(clazz);
	}

}
