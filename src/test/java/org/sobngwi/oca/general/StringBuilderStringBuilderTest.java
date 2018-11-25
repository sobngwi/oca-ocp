package org.sobngwi.oca.general;

import org.junit.Test;
import org.sobngwi.oca.AbstractStringBuilderTest;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class StringBuilderStringBuilderTest extends AbstractStringBuilderTest {

    @Test
    public void a_new_string_builder_is_zero_size(){

        assertEquals(0, aStringBuilder.length());
    }

    @Test
    public void shoud_append_strings_in_the_same_string_builder() {

        aStringBuilder.append("Hello");
        aStringBuilder.append(',');
        aStringBuilder.append(' ');
        aStringBuilder.append("World");

        assertEquals("Test Append", "Hello, World", aStringBuilder.toString());
    }
    
    @Test
    public void should_clear_the_string_builder() {

    	aStringBuilder.append("Hello world");
    	aStringBuilder.setLength(0);

    	assertEquals(0, aStringBuilder.length());
    	assertEquals("", aStringBuilder.toString());
    }

	@Test(expected=IndexOutOfBoundsException.class)
	public void should_throw_an_index_out_of_bound_exception() {

        aStringBuilder.insert(-1, "Where does this go?");
        fail();
	}

    @Test(expected=IndexOutOfBoundsException.class)
    public void should_throw_runtime_exception() {
        aStringBuilder.insert(-1, "Where does this go?");
        fail();
    }

    @Test
    public void stringBuilders_are_mutable(){
        final StringBuilder alain = aStringBuilder.append("Alain") ;
        final StringBuilder sobngwi = aStringBuilder.append(" SOBNGWI");

        assertSame(alain, sobngwi);
        assertEquals(alain.toString(),sobngwi.toString());
        assertThat(aStringBuilder.toString(), both(containsString("Alain")).and(containsString("SOBNGWI")));
        assertThat(aStringBuilder.toString(), allOf(equalTo("Alain SOBNGWI"), startsWith("Alain")));

    }

    @Test
    public void strings_are_immutable(){

        final String alain = immutableString.concat("Alain");
        final String sobngwi = immutableString.concat("SOBNGWI");

        assertNotSame(alain, sobngwi);
        assertNotEquals(alain,sobngwi);
        assertThat(alain, allOf(equalTo("Alain"), startsWith("Alain"), endsWith("Alain")));
        assertThat(sobngwi, allOf(equalTo("SOBNGWI"), startsWith("SOBNGWI"), endsWith("SOBNGWI")));
        assertThat(immutableString, both(not(containsString("Alain"))).and(not(containsString("SOBNGWI"))));

    }
}
