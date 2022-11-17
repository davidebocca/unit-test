/**
 * 
 */
package it.davidebocca.util.unit.test.rules;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import it.davidebocca.util.unit.test.exception.UnitTestException;
import it.davidebocca.util.unit.test.exception.ErrorCodeEnum;
import it.davidebocca.util.unit.test.rules.utils.AbstractRule;
import it.davidebocca.util.unit.test.rules.utils.RuleIdEnum;
import it.davidebocca.util.unit.test.utils.LoggingUtils;

/**
 * @author cr10248
 *
 */
public class UtilClassTestRule extends AbstractRule {

	/**
	 * 
	 */
	private List<Class<?>> classList = new ArrayList<>();

	private static final String CLASS = "Class: ";

	/**
	 * 
	 */
	@Override
	public RuleIdEnum getRuleId() {
		return RuleIdEnum.UTIL_CLASS;
	}

	/**
	 * 
	 * @param classList
	 * @return
	 * @throws UnitTestException
	 */
	public UtilClassTestRule withUtilClassList(List<Class<?>> classList) throws UnitTestException {

		if (classList == null || classList.isEmpty()) {
			LoggingUtils.manageError(ErrorCodeEnum.UTIL_001);
			throw new UnitTestException(ErrorCodeEnum.UTIL_001);
		}

		this.classList = classList;
		return this;
	}

	/**
	 * 
	 */
	@Override
	public void executeTest() throws UnitTestException {

		try {
			for (Class<?> clazz : classList) {

				LoggingUtils.logTestStep(RuleIdEnum.UTIL_CLASS, "Testing class ".concat(clazz.getName()));

				if (!Modifier.isFinal(clazz.getModifiers())) {
					LoggingUtils.manageError(ErrorCodeEnum.UTIL_005, CLASS.concat(clazz.getName()));
					throw new UnitTestException(ErrorCodeEnum.UTIL_005, CLASS.concat(clazz.getName()));
				}

				if (1 != clazz.getDeclaredConstructors().length) {
					LoggingUtils.manageError(ErrorCodeEnum.UTIL_006, CLASS.concat(clazz.getName()));
					throw new UnitTestException(ErrorCodeEnum.UTIL_006, CLASS.concat(clazz.getName()));
				}

				final Constructor<?> constructor = clazz.getDeclaredConstructor();

				if (constructor.isAccessible() || !Modifier.isPrivate(constructor.getModifiers())) {
					LoggingUtils.manageError(ErrorCodeEnum.UTIL_002, CLASS.concat(clazz.getName()));
					throw new UnitTestException(ErrorCodeEnum.UTIL_002, CLASS.concat(clazz.getName()));
				}

				constructor.setAccessible(true);
				constructor.newInstance();
				constructor.setAccessible(false);

				for (final Method method : clazz.getMethods()) {
					if (!Modifier.isStatic(method.getModifiers()) && method.getDeclaringClass().equals(clazz)) {
						LoggingUtils.manageError(ErrorCodeEnum.UTIL_003,
								clazz.getName().concat(".").concat(method.getName()));
						throw new UnitTestException(ErrorCodeEnum.UTIL_003,
								clazz.getName().concat(".").concat(method.getName()));

					}
				}
			}
		} catch (UnitTestException e) {
			throw e;
		} catch (Exception e2) {
			LoggingUtils.manageErrorWithException(ErrorCodeEnum.UTIL_004, e2);
			throw new UnitTestException(ErrorCodeEnum.UTIL_004, e2);
		}

	}

}
