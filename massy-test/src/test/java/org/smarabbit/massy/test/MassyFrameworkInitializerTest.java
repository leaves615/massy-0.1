package org.smarabbit.massy.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.smarabbit.massy.MassyContext;
import org.smarabbit.massy.MassyUtils;

public class MassyFrameworkInitializerTest {

	@Test
	public void testLaunch() {
		boolean launched =  MassyFrameworkInitializer.launch(null);
		assertTrue(launched);
		
		MassyContext context = MassyUtils.getDefaultContext();
		
		
		/*try {
			if (System.in.read() != 0){
				return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
