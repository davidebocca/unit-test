/**
 * 
 */
package io.github.davidebocca.util.unit.test.utils;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.davidebocca.util.unit.test.exception.ErrorCodeEnum;
import io.github.davidebocca.util.unit.test.rules.utils.RuleIdEnum;

/**
 * @author cr10248
 *
 */
public class LoggingUtils {

	private LoggingUtils() {
		// do nothing
	}

	private static final Logger LOGGER = LogManager.getLogger(LoggingUtils.class.getName());

	public static void logTestStep(RuleIdEnum ruleIdEnum, String message) {
		LOGGER.debug(generateTestStepMessage(ruleIdEnum, message));
	}

	public static void logTestWarning(RuleIdEnum ruleIdEnum, String message, String... extraParams) {
		LOGGER.warn(generateGenericMessage(ruleIdEnum, message, extraParams));
	}

	public static void manageError(ErrorCodeEnum errorCode, String... extraParams) {
		String message = generateErrorMessage(errorCode, null, extraParams);
		LOGGER.error(message);
	}

	public static void manageErrorWithException(ErrorCodeEnum errorCode, Throwable exception, String... extraParams) {

		String message = generateErrorMessage(errorCode, exception, extraParams);

		if (exception == null) {
			LOGGER.error(message);
		} else {
			LOGGER.error(message, exception);
		}

	}

	private static String generateGenericMessage(RuleIdEnum ruleIdEnum, String message, String... extraParams) {
		String msg = MessageFormat.format("Rule {0}, Message: {1}", ruleIdEnum.name(), message);
		return msg.concat(getExtraParamsString(extraParams));
	}

	private static String generateTestStepMessage(RuleIdEnum ruleIdEnum, String message) {
		return MessageFormat.format("Test Step - Rule {0}, Message: {1}", ruleIdEnum.name(), message);
	}

	private static String generateErrorMessage(ErrorCodeEnum errorCode, Throwable exception, String... extraParams) {

		String message;

		if (errorCode.getTest() == null) {
			message = MessageFormat.format("Error code: {0} - {1}",
					errorCode.getErrorCode(), errorCode.getErrorMessage());
		} else {
			message = MessageFormat.format("Error code: {0} - Test: {1} - {2}",
					errorCode.getErrorCode(), errorCode.getTest().name(), errorCode.getErrorMessage());
		}

		message = message.concat(getExtraParamsString(extraParams));

		if (exception != null) {
			message = message.concat(ExceptionUtils.getStackTrace(exception));
		}

		return message;

	}

	public static String getExtraParamsString(String... extraParams) {

		String tmp = StringUtils.EMPTY;

		for (int i = 1; i - 1 < extraParams.length; i++) {

			String p = extraParams[i - 1];

			if (StringUtils.isNotBlank(p)) {
				tmp = tmp.concat(MessageFormat.format(" - Param {0}: {1}", i, p));
			}
		}

		return tmp;

	}

}
