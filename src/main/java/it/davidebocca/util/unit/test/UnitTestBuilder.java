/**
 * 
 */
package it.davidebocca.util.unit.test;

import java.util.ArrayList;
import java.util.List;

import it.davidebocca.util.unit.test.exception.UnitTestException;
import it.davidebocca.util.unit.test.exception.ErrorCodeEnum;
import it.davidebocca.util.unit.test.rules.ClassTestRule;
import it.davidebocca.util.unit.test.rules.PojoTestRule;
import it.davidebocca.util.unit.test.rules.SpringBootTestRule;
import it.davidebocca.util.unit.test.rules.UtilClassTestRule;
import it.davidebocca.util.unit.test.rules.VertxTestRule;
import it.davidebocca.util.unit.test.rules.utils.AbstractRule;
import it.davidebocca.util.unit.test.rules.utils.RuleIdEnum;
import it.davidebocca.util.unit.test.rules.utils.SpringBootTestConfig;
import it.davidebocca.util.unit.test.rules.utils.VertxTestRuleConfig;
import it.davidebocca.util.unit.test.utils.LoggingUtils;

/**
 * @author cr10248
 *
 */
public class UnitTestBuilder {

	/**
	 * 
	 */
	private List<AbstractRule> rules = new ArrayList<AbstractRule>();

	/**
	 * 
	 * @return
	 * @throws UnitTestException
	 */
	public UnitTest build() throws UnitTestException {

		if (rules.isEmpty()) {
			LoggingUtils.manageError(ErrorCodeEnum.GENERIC_001);
			throw new UnitTestException(ErrorCodeEnum.GENERIC_001);
		}

		return new UnitTest(rules);
	}

	/**
	 * 
	 * @param pojoPackageRecursive
	 * @return
	 * @throws UnitTestException
	 */
	public UnitTestBuilder withPojoTestRecursively(String[] pojoPackages) throws UnitTestException {
		rules.add(((PojoTestRule) getRule(RuleIdEnum.POJO)).withPojoPackagesRecursively(pojoPackages));
		return this;

	}

	/**
	 * 
	 * @param pojoPackages
	 * @return
	 * @throws UnitTestException
	 */
	public UnitTestBuilder withPojoTest(String[] pojoPackages) throws UnitTestException {
		rules.add(((PojoTestRule) getRule(RuleIdEnum.POJO)).withPojoPackages(pojoPackages));
		return this;

	}

	/**
	 * 
	 * @param pojoClasses
	 * @return
	 * @throws UnitTestException
	 */
	public UnitTestBuilder withPojoTest(List<Class<?>> pojoClasses) throws UnitTestException {
		rules.add(((PojoTestRule) getRule(RuleIdEnum.POJO)).withPojoClasses(pojoClasses));
		return this;
	}

	/**
	 * 
	 * @param classPackages
	 * @return
	 * @throws UnitTestException
	 */
	public UnitTestBuilder withClassTest(String[] classPackages)
			throws UnitTestException {
		rules.add(((ClassTestRule) getRule(RuleIdEnum.CLASS)).withClassPackages(classPackages));
		return this;

	}

	/**
	 * 
	 * @param classClasses
	 * @return
	 * @throws UnitTestException
	 */
	public UnitTestBuilder withClassTest(List<Class<?>> classClasses)
			throws UnitTestException {
		rules.add(((ClassTestRule) getRule(RuleIdEnum.CLASS)).withClassList(classClasses));
		return this;
	}

	/**
	 * 
	 * @param utilClasses
	 * @return
	 * @throws UnitTestException
	 */
	public UnitTestBuilder withUtilClassTest(List<Class<?>> utilClasses) throws UnitTestException {
		rules.add(((UtilClassTestRule) getRule(RuleIdEnum.UTIL_CLASS)).withUtilClassList(utilClasses));
		return this;
	}

	/**
	 * 
	 * @param vertxTestRuleConfig
	 * @return
	 * @throws UnitTestException
	 */
	public UnitTestBuilder withVertxTest(VertxTestRuleConfig vertxTestRuleConfig) throws UnitTestException {
		rules.add(((VertxTestRule) getRule(RuleIdEnum.VERTX)).withVertxRuleConfig(vertxTestRuleConfig));
		return this;
	}

	/**
	 * 
	 * @param springBootTestConfig
	 * @return
	 * @throws UnitTestException
	 */
	public UnitTestBuilder withSpringBootTest(SpringBootTestConfig springBootTestConfig)
			throws UnitTestException {
		rules.add(
				((SpringBootTestRule) getRule(RuleIdEnum.SPRING_BOOT)).withSpringBootTestConfig(springBootTestConfig));
		return this;
	}

	/**
	 * 
	 * @param ruleIdEnum
	 * @return
	 * @throws UnitTestException
	 */
	private AbstractRule getRule(RuleIdEnum ruleIdEnum) throws UnitTestException {

		List<AbstractRule> rulesTmp = new ArrayList<>();

		for (AbstractRule rule : rules) {
			if (rule.getRuleId() != null && rule.getRuleId().equals(ruleIdEnum)) {
				rulesTmp.add(rule);
			}
		}

		if (rulesTmp.size() == 1) {
			return rulesTmp.get(0);
		}

		if (rulesTmp.size() > 1) {
			LoggingUtils.manageError(ErrorCodeEnum.GENERIC_002, ruleIdEnum.name());
			throw new UnitTestException(ErrorCodeEnum.GENERIC_002, ruleIdEnum.name());
		}

		switch (ruleIdEnum) {
		case POJO:
			return new PojoTestRule();
		case UTIL_CLASS:
			return new UtilClassTestRule();
		case VERTX:
			return new VertxTestRule();
		case SPRING_BOOT:
			return new SpringBootTestRule();
		case CLASS:
			return new ClassTestRule();
		default:
			LoggingUtils.manageError(ErrorCodeEnum.GENERIC_003, ruleIdEnum.name());
			throw new UnitTestException(ErrorCodeEnum.GENERIC_003, ruleIdEnum.name());
		}

	}

}
