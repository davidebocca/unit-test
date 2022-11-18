/**
 * 
 */
package io.github.davidebocca.util.unit.test.exception;

import io.github.davidebocca.util.unit.test.rules.utils.RuleIdEnum;

/**
 * @author cr10248
 *
 */
public enum ErrorCodeEnum {

	GENERIC_001("GENERIC_001", null, "Rules not configured"),
	GENERIC_002("GENERIC_002", null, "Rule list configuration error, multiple rules with same id"),
	GENERIC_003("GENERIC_003", null, "Rule list configuration error, can't initialize rule"),
	POJO_001("POJO_001", RuleIdEnum.POJO, "Missing packages or classes"),
	POJO_002("POJO_002", RuleIdEnum.POJO, "Test error"),
	UTIL_001("UTIL_001", RuleIdEnum.UTIL_CLASS, "Util class list is empty or null"),
	UTIL_002("UTIL_002", RuleIdEnum.UTIL_CLASS, "Constructor is not private"),
	UTIL_003("UTIL_003", RuleIdEnum.UTIL_CLASS, "There exists a non-static method"),
	UTIL_004("UTIL_004", RuleIdEnum.UTIL_CLASS, "Generic exception"),
	UTIL_005("UTIL_005", RuleIdEnum.UTIL_CLASS, "Class is not final"),
	UTIL_006("UTIL_006", RuleIdEnum.UTIL_CLASS, "Class has more than one constructor"),
	VERTX_001("VERTX_001", RuleIdEnum.VERTX, "VertxTestConfig cannot be null"),
	VERTX_002("VERTX_002", RuleIdEnum.VERTX, "Path list is empty or null"),
	VERTX_003("VERTX_003", RuleIdEnum.VERTX, "Please initialize all parameters"),
	VERTX_004("VERTX_004", RuleIdEnum.VERTX, "Generic exception"),
	VERTX_005("VERTX_005", RuleIdEnum.VERTX, "Path not working"),
	SPRING_001("SPRING_001", RuleIdEnum.SPRING_BOOT, "SpringBootTestConfig cannot be null"),
	SPRING_002("SPRING_002", RuleIdEnum.SPRING_BOOT, "Autowired bean is null"),
	SPRING_003("SPRING_003", RuleIdEnum.SPRING_BOOT, "Path not working"),
	CLASS_001("CLASS_001", RuleIdEnum.CLASS, "Missing packages or classes"),
	CLASS_002("CLASS_002", RuleIdEnum.CLASS, "Test error"),
	;

	private String errorCode;
	private RuleIdEnum test;
	private String errorMessage;

	ErrorCodeEnum(String errorCode, RuleIdEnum test, String errorMessage) {
		this.errorCode = errorCode;
		this.test = test;
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the test
	 */
	public RuleIdEnum getTest() {
		return test;
	}

	/**
	 * @param test the test to set
	 */
	public void setTest(RuleIdEnum test) {
		this.test = test;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
