/**
 * 
 */
package it.davidebocca.util.unit.test.rules.utils;

import it.davidebocca.util.unit.test.exception.UnitTestException;

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
