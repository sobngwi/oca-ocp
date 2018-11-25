package org.sobngwi.oca.general;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class HamcrestDemoTest {

	@Test
	public void showSimpleHamcrestWorking() {
		assertThat("hello", equalTo("hello"));
		
		assertThat("a b c", allOf(
			equalTo("a b c"),
			instanceOf(String.class),
			not(containsString("x y z"))));

	}

	@Test(expected = IllegalArgumentException.class)
	public void should_throw_an_IllegalArgumentException() {
		Factorial.factorial(-1);
		fail();
	}
}
