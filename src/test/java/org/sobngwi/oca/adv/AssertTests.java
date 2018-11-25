package org.sobngwi.oca.adv;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.hamcrest.core.CombinableMatcher;
import org.junit.Test;

public class AssertTests {
    @Test
    public void AssertArrayEquals() {
        byte[] expected = "trial".getBytes();
        byte[] actual = "trial".getBytes();
        assertArrayEquals("failure - byte arrays not same", expected, actual);
    }

    @Test
    public void AssertEquals() {
        assertEquals("failure - strings are not equal", "text", "text");
    }

    @Test
    public void AssertFalse() {
        assertFalse("failure - should be false", false);
    }

    @Test
    public void AssertNotNull() {
        assertNotNull("should not be null", new Object());
    }

    @Test
    public void AssertNotSame() {
        assertNotSame("should not be same Object", new Object(), new Object());
    }

    @Test
    public void AssertNull() {
        assertNull("should be null", null);
    }

    @Test
    public void AssertSame() {
        Integer aNumber = 768;
        assertSame("should be same", aNumber, aNumber);
    }

    // JUnit Matchers assertThat
    @Test
    public void AssertThatBothContainsString() {
        assertThat("albumen", both(containsString("a")).and(containsString("b")));
    }

    @Test
    public void AssertThatHasItems() {
        assertThat(Arrays.asList("one", "two", "three"), hasItems("one", "three"));
    }

    @Test
    public void AssertThatEveryItemContainsString() {
        assertThat(Arrays.asList("fun", "ban", "net"), everyItem(containsString("n")));
    }

    // Core Hamcrest Matchers with assertThat
    @Test
    public void AssertThatHamcrestCoreMatchers() {
        assertThat("good", allOf(equalTo("good"), startsWith("good")));
        assertThat("good", not(allOf(equalTo("bad"), equalTo("good"))));
        assertThat("good", anyOf(equalTo("bad"), equalTo("good")));
        assertThat(7, not(CombinableMatcher.<Integer> either(equalTo(3)).or(equalTo(4))));
        assertThat(new Object(), not(sameInstance(new Object())));
    }

    @Test
    public void AssertTrue() {
        assertTrue("failure - should be true", true);
    }
}