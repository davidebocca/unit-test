/**
 * 
 */
package io.github.davidebocca.util.unit.test;

import java.text.MessageFormat;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.davidebocca.util.unit.test.exception.UnitTestException;
import io.github.davidebocca.util.unit.test.rules.utils.AbstractRule;

/**
 * @author cr10248
 *
 */
public class UnitTest {

	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(UnitTest.class.getName());

	/**
	 * 
	 */
	private List<AbstractRule> rules;

	/**
	 * 
	 */
	boolean pojoTest = false;

	/**
	 * 
	 * @param rules
	 */
	public UnitTest(List<AbstractRule> rules) {

		LOGGER.debug("                 ");
		LOGGER.debug("*****************");
		LOGGER.debug("    UNIT TEST    ");
		LOGGER.debug("*****************");
		LOGGER.debug("                 ");

		this.rules = rules;
	}

	/**
	 * 
	 * @throws UnitTestException
	 */
	public void executeTests() throws UnitTestException {

		int j = 1;

		for (int i = 0; i < rules.size(); i++) {

			AbstractRule rule = rules.get(i);

			LOGGER.debug(MessageFormat.format("Executing rule {0} ({1} of {2})",
					rule.getRuleId().name(), j++, rules.size()));

			rule.executeTest();
		}

	}

}
