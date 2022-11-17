/**
 * 
 */
package io.github.davidebocca.util.unit.test.rules.utils;

import io.github.davidebocca.util.unit.test.exception.UnitTestException;

/**
 * @author cr10248
 *
 */
public abstract class AbstractRule {

	/**
	 * 
	 * @return
	 */
	public abstract RuleIdEnum getRuleId();

	/**
	 * 
	 * @throws UnitTestException
	 */
	public abstract void executeTest() throws UnitTestException;

}
