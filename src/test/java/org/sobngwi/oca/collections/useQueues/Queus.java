package org.sobngwi.oca.collections.useQueues;

import org.junit.Test;
import org.sobngwi.oca.collections.AbstractCollections;

import java.util.ArrayDeque;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class Queus extends AbstractCollections {

    @Test (expected = RuntimeException.class)
    public void removeElementOnEmptyQueue(){
        stringQueue.remove();
        fail();
    }

    @Test (expected = RuntimeException.class)
    public void lookElementOnEmptyQueue(){
        stringQueue.element();
        fail();
    }
    @Test (expected = AssertionError.class)
    public void addElementOnFullQueue(){
        stringQueue = new ArrayDeque<>(2);
        stringQueue.add("Alain");
        stringQueue.add("Alain");
        stringQueue.offer("Alain"); // offer is a variant of add

        fail();
    }

    @Test
    public void peekElementOnEmptyQueue(){
        String result = stringQueue.peek();

        assertNull(result);
        assertEquals(result, null);
        assertThat(result, equalTo(null));
    }



}
