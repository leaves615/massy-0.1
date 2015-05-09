package org.smarabbit.massy.instrumentation;

import static org.junit.Assert.assertNotNull;

import java.lang.instrument.Instrumentation;

import org.junit.Test;

public class InstrumentationFactoryTest {

	@Test
	public void testGetInstrumentation() {
		Instrumentation inst = null;
		try {
			inst = InstrumentationFactory.getInstrumentation();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertNotNull(inst);
	}

}
