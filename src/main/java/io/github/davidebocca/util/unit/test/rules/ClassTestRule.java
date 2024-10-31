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
import io.github.davidebocca.util.unit.test.utils.Utils;

/**
 * @author cr10248
 *
 */
public class ClassTestRule extends AbstractRule {

	private UnitTestClassConf testConf;

	private int counter = 1;

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
				callClassRulesPack(pack);
			}

			for (ClassConf cl : testConf.getClasses()) {
				callClassRulesClass(cl.getClazz());
			}

		} catch (AssertionError e) {
			LoggingUtils.manageError(ErrorCodeEnum.CLASS_002, ExceptionUtils.getRootCauseMessage(e));
			throw new UnitTestException(ErrorCodeEnum.CLASS_002, ExceptionUtils.getRootCauseMessage(e));
		}

	}

	private void callClassRulesPack(PackageConf pack) {

		LoggingUtils.logTestStep(RuleIdEnum.CLASS, "Testing package ".concat(pack.toString()));

		List<String> classNames;

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

			LoggingUtils.logTestStep(RuleIdEnum.CLASS, "Scanning class ".concat(c));

			try {

				for (String cl : testConf.getClassExclusion().getClassesToExcludeStr()) {
					if (cl.equals(c)) {
						LoggingUtils.logTestStep(RuleIdEnum.CLASS, "Exclude class from string ".concat(c));
						return;
					}
				}

				Class<?> clazz = Class.forName(c);

				callClassRulesClass(clazz);

			} catch (Exception e) {
				LoggingUtils.logTestWarning(RuleIdEnum.CLASS, "Reflection error, skipping class", c);
			}
		}

	}

	private void callClassRulesClass(Class<?> clazz) {

		// apply manual exclusions
		if (testConf.getClassExclusion().getClassesToExclude().contains(clazz)) {
			LoggingUtils.logTestStep(RuleIdEnum.CLASS, "Exclude class ".concat(clazz.getName()));
			return;
		}

		// exclude test classes
		if (!testConf.isIncludeTestClasses() && Utils.isClassTest(clazz)) {
			LoggingUtils.logTestStep(RuleIdEnum.CLASS, "Skip test class ".concat(clazz.getName()));
			return;
		}

		LoggingUtils.logTestStep(RuleIdEnum.CLASS, "Test " + counter + ") class ".concat(clazz.getName()));
		counter++;

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

						LoggingUtils.logTestStep(RuleIdEnum.CLASS, "Param " + param + " - Class " + paramClass);

						params[i] = Utils.instantiateObject(paramClass);
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

				try {
					// call hashcode
					obj.hashCode();
				} catch (Exception e) {
					LoggingUtils.logTestWarning(RuleIdEnum.CLASS, "hashCode error, skipping", clazz.getName());
				}

				// call toString
				obj.toString();

			} catch (Exception e) {
				LoggingUtils.logTestWarning(RuleIdEnum.CLASS, "Reflection error, skipping constructor", clazz.getName(), StringUtils.join(c.getParameterTypes(), ", "));
			}
		}

	}

}
