package org.sobngwi.oca.collections.usesets;

import org.junit.Test;
import org.sobngwi.oca.collections.AbstractCollections;

import java.util.Collections;
import java.util.Objects;
import java.util.SortedSet;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class Sets extends AbstractCollections {

    @Test
    public void firstElementofSet(){

        Integer result = integerTreeSet.first();

        assertThat(result, equalTo(1));

    }

    @Test
    public void lastElementofSet(){

        Integer result = integerTreeSet.first();

        assertThat(result, equalTo(1));

    }

    @Test
    public void headFirstFiveElementofSet(){

        SortedSet<Integer> result = integerTreeSet.headSet(5, true);

        assertThat(result.first(), equalTo(1));
        assertThat(result.last(),  equalTo(5));
        assertThat(result.size(), equalTo(5));

    }

    @Test
    public void tailLastFiveElementofSet(){

        SortedSet<Integer> result = integerTreeSet.tailSet(5, false);
        result.forEach(System.out::println);
        assertThat(result.first(), equalTo(6));
        assertThat(result.last(),  equalTo(10));
        assertThat(result.size(), equalTo(5));

    }

    @Test
    public void lowerElementofSet(){

        Integer result = integerTreeSet.lower(5);

        assertThat(result, equalTo(4));

    }

    @Test
    public void floorElementofSet(){

        Integer result = integerTreeSet.floor(5);

        assertThat(result, equalTo(5));

    }

    @Test
    public void higherElementofSet(){

        Integer result = integerTreeSet.higher(5);

        assertThat(result, equalTo(6));

    }
    @Test
    public void ceillinElementofSet(){

        Integer result = integerTreeSet.ceiling(5);

        assertThat(result, equalTo(5));

    }

    @Test
    public void maxElementofTheSet(){
        Integer result = Collections.max(integerTreeSet);

        assertThat(result, equalTo(10));
    }

    @Test
    public void minElementofTheSet(){
        Integer result = Collections.min(integerTreeSet);

        assertThat(result, equalTo(1));
    }

}
