package org.sobngwi.oca.general;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.*;
import org.junit.Test;

public class HamcrestCollectionsDemoTest {

	@Test
	public void showSimpleHamcrestWorking() {
		
		List<String> actual = Arrays.asList("One", "Two", "Three", "Four");
		assertThat(actual, hasItems("Two", "Four"));
		assertThat(actual, hasItem("Three"));
		assertThat(actual, everyItem(not(containsString("zz"))));
	}
}
