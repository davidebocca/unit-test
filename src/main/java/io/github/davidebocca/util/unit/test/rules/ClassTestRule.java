/**
 * 
 */
package io.github.davidebocca.util.unit.test.rules;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import io.github.davidebocca.util.unit.test.exception.ErrorCodeEnum;
import io.github.davidebocca.util.unit.test.exception.UnitTestException;
import io.github.davidebocca.util.unit.test.rules.conf.ClassConf;
import io.github.davidebocca.util.unit.test.rules.conf.PackageConf;
import io.github.davidebocca.util.unit.test.rules.conf.UnitTestClassConf;
import io.github.davidebocca.util.unit.test.rules.utils.AbstractRule;
import io.github.davidebocca.util.unit.test.rules.utils.RuleIdEnum;
import io.github.davidebocca.util.unit.test.utils.LoggingUtils;

/**
 * @author cr10248
 *
 */
public class ClassTestRule extends AbstractRule {

	private UnitTestClassConf testConf;

	/**
	 * 
	 */
	@Override
	public RuleIdEnum getRuleId() {
		return RuleIdEnum.CLASS;
	}

	public ClassTestRule withConfiguration(UnitTestClassConf testConf) throws UnitTestException {

		if (testConf.getClasses().size() == 0 && testConf.getPackages().size() == 0) {
			LoggingUtils.manageError(ErrorCodeEnum.CLASS_001);
			throw new UnitTestException(ErrorCodeEnum.CLASS_001);
		}

		this.testConf = testConf;

		return this;
	}

	/**
	 * 
	 */
	@Override
	public void executeTest() throws UnitTestException {

		try {

			for (PackageConf pack : testConf.getPackages()) {

				LoggingUtils.logTestStep(RuleIdEnum.CLASS, "Testing package ".concat(pack.toString()));

				callClassRulesPack(pack);
			}

			for (ClassConf cl : testConf.getClasses()) {
				callClassRulesClass(cl.getClazz());
			}

		} catch (AssertionError e) {
			LoggingUtils.manageError(ErrorCodeEnum.CLASS_003, ExceptionUtils.getRootCauseMessage(e));
			throw new UnitTestException(ErrorCodeEnum.CLASS_003, ExceptionUtils.getRootCauseMessage(e));
		}

	}

	private void callClassRulesPack(PackageConf pack) {

		List<String> classNames;

		try {
			ScanResult scanResult;
			ClassGraph classGraph = new ClassGraph()
					.enableAllInfo();

			if (pack.isRecursive()) {

				scanResult = classGraph
						.acceptPackages(pack.getName())
						.scan();

			} else {

				scanResult = classGraph
						.acceptPackagesNonRecursive(pack.getName())
						.scan();

			}

			classNames = scanResult.getAllClasses().getNames();

			for (String c : classNames) {
				callClassRulesClass(Class.forName(c));
			}

		} catch (Exception e) {
			LoggingUtils.logTestWarning(RuleIdEnum.CLASS, "Reflection error, skipping package");
		}

	}

	private void callClassRulesClass(Class<?> clazz) {

		LoggingUtils.logTestStep(RuleIdEnum.CLASS, "Testing class ".concat(clazz.getName()));

		Constructor<?>[] constructors = clazz.getDeclaredConstructors();

		for (Constructor<?> c : constructors) {

			try {
				if (!c.isAccessible()) {
					c.setAccessible(true);
				}

				int paramCount = c.getParameterCount();

				Object obj;
				Object obj2;

				if (paramCount != 0) {

					Object[] params = new Object[paramCount];

					for (int i = 0; i < paramCount; i++) {

						Parameter param = c.getParameters()[i];

						Class<?> paramClass = param.getType();

						params[i] = paramClass.newInstance();
					}

					obj = c.newInstance(params);
					obj2 = c.newInstance(params);

				} else {
					obj = c.newInstance();
					obj2 = c.newInstance();
				}

				// call equals
				obj.equals(obj2);
				obj.equals(obj);
				obj.equals(null);
				obj.equals(new Object());

				// call hashcode
				obj.hashCode();

				// call toString
				obj.toString();

			} catch (Exception e) {
				LoggingUtils.logTestWarning(RuleIdEnum.CLASS, "Reflection error, skipping constructor with params ".concat(StringUtils.join(c.getParameterTypes(), ", ")));
			}
		}

	}

}
