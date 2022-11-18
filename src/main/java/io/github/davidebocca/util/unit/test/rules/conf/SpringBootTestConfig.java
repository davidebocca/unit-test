/**
 * 
 */
package io.github.davidebocca.util.unit.test.rules.conf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.test.web.servlet.MockMvc;

/**
 * @author cr10248
 *
 */
public class SpringBootTestConfig {

	private MockMvc mockMvc;
	private Map<String, Object> autowiredBeanMap = new HashMap<>();
	private List<String> pathList;

	/**
	 * 
	 * @param controllerClassList
	 * @param mockMvc
	 */
	public SpringBootTestConfig(Map<String, Object> autowiredBeanMap, MockMvc mockMvc, List<String> pathlist) {
		this.autowiredBeanMap = autowiredBeanMap;
		this.mockMvc = mockMvc;
		this.pathList = pathlist;
	}

	/**
	 * @return the mockMvc
	 */
	public MockMvc getMockMvc() {
		return mockMvc;
	}

	/**
	 * @param mockMvc the mockMvc to set
	 */
	public void setMockMvc(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
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
	 * @return the autowiredBeanMap
	 */
	public Map<String, Object> getAutowiredBeanMap() {
		return autowiredBeanMap;
	}

	/**
	 * @param autowiredBeanMap the autowiredBeanMap to
	 *                         set
	 */
	public void setAutowiredBeanMap(Map<String, Object> autowiredBeanMap) {
		this.autowiredBeanMap = autowiredBeanMap;
	}

}
