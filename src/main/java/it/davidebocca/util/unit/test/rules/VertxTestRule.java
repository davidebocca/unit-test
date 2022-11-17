/**
 * 
 */
package it.davidebocca.util.unit.test.rules;

import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.junit5.Checkpoint;
import it.davidebocca.util.unit.test.exception.UnitTestException;
import it.davidebocca.util.unit.test.exception.ErrorCodeEnum;
import it.davidebocca.util.unit.test.rules.utils.AbstractRule;
import it.davidebocca.util.unit.test.rules.utils.RuleIdEnum;
import it.davidebocca.util.unit.test.rules.utils.VertxTestRuleConfig;
import it.davidebocca.util.unit.test.utils.LoggingUtils;

/**
 * @author cr10248
 *
 */
public class VertxTestRule extends AbstractRule {

	VertxTestRuleConfig testConfig;

	/**
	 * 
	 */
	@Override
	public RuleIdEnum getRuleId() {
		return RuleIdEnum.VERTX;
	}

	public VertxTestRule withVertxRuleConfig(VertxTestRuleConfig vertxTestConfig)
			throws UnitTestException {

		if (vertxTestConfig == null) {
			LoggingUtils.manageError(ErrorCodeEnum.VERTX_001);
			throw new UnitTestException(ErrorCodeEnum.VERTX_001);
		}

		if (vertxTestConfig.getPathList() == null || vertxTestConfig.getPathList().isEmpty()) {
			LoggingUtils.manageError(ErrorCodeEnum.VERTX_002);
			throw new UnitTestException(ErrorCodeEnum.VERTX_002);
		}

		if (vertxTestConfig.getVertx() == null || vertxTestConfig.getTestContext() == null
				|| vertxTestConfig.getVerticle() == null) {
			LoggingUtils.manageError(ErrorCodeEnum.VERTX_003);
			throw new UnitTestException(ErrorCodeEnum.VERTX_003);
		}

		this.testConfig = vertxTestConfig;

		return this;
	}

	/**
	 * 
	 */
	@Override
	public void executeTest() throws UnitTestException {

		try {
			WebClient webClient = WebClient.create(testConfig.getVertx());
			Checkpoint requestCheckpoint = testConfig.getTestContext().checkpoint(testConfig.getPathList().size());

			LoggingUtils.logTestStep(RuleIdEnum.VERTX,
					"Testing deploy Verticle ".concat(testConfig.getVerticle().getClass().getName()));

			testConfig.getVertx().deployVerticle(testConfig.getVerticle(),
					testConfig.getTestContext().succeeding(id -> {

						for (String path : testConfig.getPathList()) {

							LoggingUtils.logTestStep(RuleIdEnum.VERTX, "Testing path ".concat(path));

							webClient.get(testConfig.getPort(), "localhost", path)
									.as(BodyCodec.string())
									.send(testConfig.getTestContext().succeeding(resp -> {
										testConfig.getTestContext().verify(() -> {
											if (200 != resp.statusCode()) {
												LoggingUtils.manageError(ErrorCodeEnum.VERTX_005, path);
												throw new UnitTestException(ErrorCodeEnum.VERTX_005, path);
											}
											requestCheckpoint.flag();
										});
									}));
						}
					}));
		} catch (Exception e) {
			LoggingUtils.manageErrorWithException(ErrorCodeEnum.VERTX_004, e);
			throw new UnitTestException(ErrorCodeEnum.VERTX_004, e);
		}
	}

}
