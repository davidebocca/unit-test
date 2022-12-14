/**
 * 
 */
package io.github.davidebocca.util.unit.test;

import java.util.ArrayList;
import java.util.List;

import io.github.davidebocca.util.unit.test.exception.ErrorCodeEnum;
import io.github.davidebocca.util.unit.test.exception.UnitTestException;
import io.github.davidebocca.util.unit.test.rules.ClassTestRule;
import io.github.davidebocca.util.unit.test.rules.PojoTestRule;
import io.github.davidebocca.util.unit.test.rules.SpringBootTestRule;
import io.github.davidebocca.util.unit.test.rules.UtilClassTestRule;
import io.github.davidebocca.util.unit.test.rules.VertxTestRule;
import io.github.davidebocca.util.unit.test.rules.conf.SpringBootTestConfig;
import io.github.davidebocca.util.unit.test.rules.conf.UnitTestClassConf;
import io.github.davidebocca.util.unit.test.rules.conf.UnitTestPojoConf;
import io.github.davidebocca.util.unit.test.rules.conf.VertxTestRuleConfig;
import io.github.davidebocca.util.unit.test.rules.utils.AbstractRule;
import io.github.davidebocca.util.unit.test.rules.utils.RuleIdEnum;
import io.github.davidebocca.util.unit.test.utils.LoggingUtils;

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
	 * @param testConf
	 * @return
	 * @throws UnitTestException
	 */
	public UnitTestBuilder withPojoTest(UnitTestPojoConf testConf) throws UnitTestException {
		rules.add(((PojoTestRule) getRule(RuleIdEnum.POJO)).withConfiguration(testConf));
		return this;
	}

	/**
	 * 
	 * @param testConf
	 * @return
	 * @throws UnitTestException
	 */
	public UnitTestBuilder withClassTest(UnitTestClassConf testConf) throws UnitTestException {
		rules.add(((ClassTestRule) getRule(RuleIdEnum.CLASS)).withConfiguration(testConf));
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
	public UnitTestBuilder withSpringBootTest(SpringBootTestConfig springBootTestConfig) throws UnitTestException {
		rules.add(((SpringBootTestRule) getRule(RuleIdEnum.SPRING_BOOT)).withSpringBootTestConfig(springBootTestConfig));
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
