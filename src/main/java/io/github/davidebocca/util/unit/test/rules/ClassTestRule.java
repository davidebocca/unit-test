/**
 * 
 */
package io.github.davidebocca.util.unit.test.rules;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import io.github.davidebocca.util.unit.test.exception.ErrorCodeEnum;
import io.github.davidebocca.util.unit.test.exception.UnitTestException;
import io.github.davidebocca.util.unit.test.rules.utils.AbstractRule;
import io.github.davidebocca.util.unit.test.rules.utils.RuleIdEnum;
import io.github.davidebocca.util.unit.test.utils.LoggingUtils;

/**
 * @author cr10248
 *
 */
public class ClassTestRule extends AbstractRule {

	/**
	 * 
	 */
	private List<Class<?>> clazzList = new ArrayList<>();
	private String[] classPackages = new String[] {};

	private Map<String, List<String>> exclusionMap;

	/**
	 * 
	 */
	@Override
	public RuleIdEnum getRuleId() {
		return RuleIdEnum.CLASS;
	}

	public ClassTestRule withClassList(List<Class<?>> clazzList, Map<String, List<String>> exclusionMap) throws UnitTestException {

		if (clazzList == null || clazzList.isEmpty()) {
			LoggingUtils.manageError(ErrorCodeEnum.CLASS_001);
			throw new UnitTestException(ErrorCodeEnum.CLASS_001);
		}

		this.clazzList = clazzList;
		this.exclusionMap = exclusionMap;

		return this;
	}

	public ClassTestRule withClassPackages(String[] classPackages, Map<String, List<String>> exclusionMap) throws UnitTestException {

		if (classPackages == null || classPackages.length == 0) {
			LoggingUtils.manageError(ErrorCodeEnum.CLASS_002);
			throw new UnitTestException(ErrorCodeEnum.CLASS_002);
		}

		this.classPackages = classPackages;
		this.exclusionMap = exclusionMap;

		return this;
	}

	/**
	 * 
	 */
	@Override
	public void executeTest() throws UnitTestException {

		try {

			for (String pack : this.classPackages) {
				LoggingUtils.logTestStep(RuleIdEnum.CLASS, "Testing package ".concat(pack));

				callClassRulesPack(pack);
			}

			for (Class<?> clazz : this.clazzList) {
				callClassRulesClass(clazz);
			}

		} catch (AssertionError e) {
			LoggingUtils.manageError(ErrorCodeEnum.CLASS_003, ExceptionUtils.getRootCauseMessage(e));
			throw new UnitTestException(ErrorCodeEnum.CLASS_003, ExceptionUtils.getRootCauseMessage(e));
		}

	}

	private void callClassRulesPack(String pack) {

		List<String> classNames;

		try (ScanResult scanResult = new ClassGraph().whitelistPackages(pack).enableClassInfo().scan()) {
			classNames = scanResult.getAllClasses().getNames();

			for (String c : classNames) {
				callClassRulesClass(Class.forName(c));
			}

		} catch (Exception e) {
			LoggingUtils.logTestWarning(RuleIdEnum.CLASS, "Reflection error, skipping package", pack);
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

				// for (PropertyDescriptor pd : Introspector.getBeanInfo(clazz).getPropertyDescriptors()) {
				// if (pd.getReadMethod() != null && !"class".equals(pd.getName())) {
				// pd.getReadMethod().invoke(obj);
				// }
				// }
			} catch (Exception e) {
				LoggingUtils.logTestWarning(RuleIdEnum.CLASS, "Reflection error, skipping constructor with params ".concat(StringUtils.join(c.getParameterTypes(), ", ")));
			}
		}

	}

}
