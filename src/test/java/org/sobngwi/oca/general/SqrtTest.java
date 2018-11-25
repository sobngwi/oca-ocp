package org.sobngwi.oca.general;

import static java.lang.Math.sqrt;
import static org.junit.Assert.*;

import org.junit.Test;

public class SqrtTest {
	

	@Test
	public void should_compute_the_square_value_with_a_delta() {

		assertNotEquals("without delta ?", 1.414213562D, sqrt(2), 0);
		assertEquals("with delta ?", 1.414213562D, sqrt(2), 0.00000001D);

	}
}