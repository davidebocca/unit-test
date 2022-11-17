/**
 * 
 */
package it.davidebocca.util.unit.test.rules;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import it.davidebocca.util.unit.test.exception.UnitTestException;
import it.davidebocca.util.unit.test.exception.ErrorCodeEnum;
import it.davidebocca.util.unit.test.rules.utils.AbstractRule;
import it.davidebocca.util.unit.test.rules.utils.RuleIdEnum;
import it.davidebocca.util.unit.test.rules.utils.SpringBootTestConfig;
import it.davidebocca.util.unit.test.utils.LoggingUtils;

/**
 * @author cr10248
 *
 */
public class SpringBootTestRule extends AbstractRule {

	private SpringBootTestConfig testConfig;

	/**
	 * 
	 */
	@Override
	public RuleIdEnum getRuleId() {
		return RuleIdEnum.SPRING_BOOT;
	}

	public SpringBootTestRule withSpringBootTestConfig(SpringBootTestConfig springBootTestConfig)
			throws UnitTestException {

		if (springBootTestConfig == null) {
			LoggingUtils.manageError(ErrorCodeEnum.SPRING_001);
			throw new UnitTestException(ErrorCodeEnum.SPRING_001);
		}

		this.testConfig = springBootTestConfig;

		return this;
	}

	/**
	 * 
	 */
	@Override
	public void executeTest() throws UnitTestException {

		if (testConfig.getAutowiredBeanMap() != null) {

			for (String key : testConfig.getAutowiredBeanMap().keySet()) {

				LoggingUtils.logTestStep(RuleIdEnum.SPRING_BOOT, "Testing autowired bean ".concat(key));

				Object obj = testConfig.getAutowiredBeanMap().get(key);

				if (obj == null) {
					LoggingUtils.manageError(ErrorCodeEnum.SPRING_002, key);
					throw new UnitTestException(ErrorCodeEnum.SPRING_002, key);
				}
			}

		}

		if (testConfig.getPathList() != null) {

			for (String path : testConfig.getPathList()) {

				LoggingUtils.logTestStep(RuleIdEnum.SPRING_BOOT, "Testing path ".concat(path));

				try {
					testConfig.getMockMvc().perform(get(path)).andExpect(status().isOk());
				} catch (AssertionError | Exception e) {
					LoggingUtils.manageError(ErrorCodeEnum.SPRING_003, path);
					throw new UnitTestException(ErrorCodeEnum.SPRING_003, path);
				}

			}

		}

	}

}
