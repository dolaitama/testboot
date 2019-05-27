/**
 * 
 */
package com.sugon.controller;

import org.junit.Before;
import org.junit.Test;

/**
 * @author pc
 * @date 2017年11月24日 上午11:54:23
 */
public class KindeditorControllerTest {
	
	private KindeditorController k = new KindeditorController();

	/**
	 * @author pc
	 * @date 2017年11月24日 上午11:54:23
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link com.sugon.controller.KindeditorController#getError(java.lang.String)}.
	 */
	@Test
	public void testGetError() {
		k.getError("Not yet implemented");
	}

}
