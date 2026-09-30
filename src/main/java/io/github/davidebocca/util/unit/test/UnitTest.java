/**
 * 
 */
package io.github.davidebocca.util.unit.test;

import java.text.MessageFormat;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

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

		System.out.println("                 ");
		System.out.println("*****************");
		System.out.println("    UNIT TEST    ");
		System.out.println("*****************");
		System.out.println("                 ");

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

			System.out.println(MessageFormat.format("Executing rule {0} ({1} of {2})",
					rule.getRuleId().name(), j++, rules.size()));

			rule.executeTest();
		}

	}

}
