/**
 * 
 */
package io.github.davidebocca.util.unit.test.rules.conf;

import java.util.ArrayList;
import java.util.List;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxTestContext;

/**
 * @author cr10248
 *
 */
public class VertxTestRuleConfig {

	private List<String> pathList = new ArrayList<>();
	private Vertx vertx;
	private VertxTestContext testContext;
	private AbstractVerticle verticle;
	private int port;

	public VertxTestRuleConfig(List<String> pathList, Vertx vertx, VertxTestContext testContext,
			AbstractVerticle verticle, int port) {
		super();
		this.pathList = pathList;
		this.vertx = vertx;
		this.testContext = testContext;
		this.verticle = verticle;
		this.port = port;
	}

	/**
	 * @return the pathList
	 */
	public List<String> getPathList() {
		return pathList;
	}

	/**
	 * @param pathList the pathList to set
	 */
	public void setPathList(List<String> pathList) {
		this.pathList = pathList;
	}

	/**
	 * @return the vertx
	 */
	public Vertx getVertx() {
		return vertx;
	}

	/**
	 * @param vertx the vertx to set
	 */
	public void setVertx(Vertx vertx) {
		this.vertx = vertx;
	}

	/**
	 * @return the testContext
	 */
	public VertxTestContext getTestContext() {
		return testContext;
	}

	/**
	 * @param testContext the testContext to set
	 */
	public void setTestContext(VertxTestContext testContext) {
		this.testContext = testContext;
	}

	/**
	 * @return the verticle
	 */
	public AbstractVerticle getVerticle() {
		return verticle;
	}

	/**
	 * @param verticle the verticle to set
	 */
	public void setVerticle(AbstractVerticle verticle) {
		this.verticle = verticle;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

}
