package org.sobngwi.oca.general;

import org.junit.Test;
import org.sobngwi.oca.AbstractFileTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FilesystemTests extends AbstractFileTest {

	@Test
	public void testFileCreation() throws Exception {

		assertFalse(filename.exists());

		assertTrue(filename.createNewFile());

		assertTrue(filename.exists());
	}

}
