package org.sobngwi.oca.general;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.omg.CORBA.StringHolder;
import org.sobngwi.oca.service.MemberValidator;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class MemberValidatorEmailTest {
	
	private String email;
	private boolean expected;
	StringHolder messageHolder = new StringHolder();
	
	public MemberValidatorEmailTest(boolean expected, String email) {
		this.email = email;
		this.expected = expected;
	}
	
	@Parameters
	public static List<Object[]> getParams() {
		return Arrays.asList(new Object[][]{
				// Null or Empty are invalid for us, might not be for you.
				{false, null },
				{false, "" },
				// From short to long, roughly
				{true, "a@b.co"},
				{false, "a at b.co" },
				{true, "a.b.c@d.e.f.com"},
				{true, "ketchup@mcdonalds.infomaterial"},
				{false, "a-b@c.com"},
				{true, "sobngwi@gmail.com"},
				{false,"sobngwi@yahoo"}
		});
	}	
	
	@Test
	public void test() {
		assertSame(expected, MemberValidator.validateEmail(email));
		assertEquals(expected,MemberValidator.validateEmail(email));
		assertThat(expected, equalTo(MemberValidator.validateEmail(email)));
	}

}
