/**
 * 
 */
package io.github.davidebocca.util.unit.test.exception;

import java.text.MessageFormat;

import io.github.davidebocca.util.unit.test.utils.LoggingUtils;

/**
 * @author cr10248
 *
 */
public class UnitTestException extends Exception {

	private static final long serialVersionUID = 1L;

	public UnitTestException(ErrorCodeEnum errorCodeEnum) {
		super(generateMessage(errorCodeEnum));
	}

	public UnitTestException(ErrorCodeEnum errorCodeEnum, String... extraParams) {
		super(generateMessage(errorCodeEnum, extraParams));
	}

	public UnitTestException(ErrorCodeEnum errorCodeEnum, Throwable exception) {
		super(generateMessage(errorCodeEnum), exception);
	}

	private static String generateMessage(ErrorCodeEnum errorCodeEnum) {
		return MessageFormat.format("{0} - {1}", errorCodeEnum.getErrorCode(), errorCodeEnum.getErrorMessage());
	}

	private static String generateMessage(ErrorCodeEnum errorCodeEnum, String... extraParams) {
		String message = generateMessage(errorCodeEnum);
		return message.concat(LoggingUtils.getExtraParamsString(extraParams));
	}

}
