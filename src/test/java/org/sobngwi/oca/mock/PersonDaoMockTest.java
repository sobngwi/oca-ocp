package org.sobngwi.oca.mock;


import org.junit.Test;
import org.sobngwi.oca.AbstractPersonDaoMockTest;
import org.sobngwi.oca.model.Person;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class PersonDaoMockTest extends AbstractPersonDaoMockTest {


    @Test
    public void shouldNotFoundNeitherRudyNorAG(){
        // Condition the Mock
        when(personService.findAllPersons()).thenReturn(allPersons);

        // Now the actual test, we know there is no such person in the actual Test

        boolean personExists = getPersonService().checkIfPersonExists("Alain", "Narcisse");

        // Test the expectations
        verify(personDaoMock, times(1)).getAllPersons();
        verify(personDaoMock, atLeast(1)).getAllPersons();
        verify(personDaoMock, never()).getPersonById(anyInt());

        // Now Asserts

        assertEquals(personExists,false);
        assertThat(personExists, equalTo(false));
    }

    @Test
    public void shouldFoundRudyAndAnneGaelle(){
        // Condition the Mock
        when(personService.findAllPersons()).thenReturn(allPersons);

        // Now the actual test, we know there is no such person in the actual Test

        List<Person> alllPersonsResult = getPersonService().findAllPersons();

        // Test the expectations

        verify(personDaoMock, times(1)).getAllPersons();
        verify(personDaoMock, atLeast(1)).getAllPersons();
        verify(personDaoMock, never()).getPersonById(anyInt());

        // Now Asserts

        assertThat(alllPersonsResult.size(), is(2));
        assertEquals(alllPersonsResult, allPersons);
        assertThat(alllPersonsResult, hasItems(rudy, anneGaelle));
        assertThat(alllPersonsResult,  not(hasItem(anonymous)));
    }

    @Test
    public void shouldNFoundOnlyRudy(){
        // Condition the Mock
        when(personService.findPersonById(anyInt())).thenReturn(rudy);

        // Now the actual test, we know there is no such person in the actual Test

        Person rudyExpectedResult = getPersonService().findPersonById(1000);

        // Test the expectations
        verify(personDaoMock, times(1)).getPersonById(anyInt());
        verify(personDaoMock, never()).getAllPersons();

        // Now Asserts

        assertEquals(rudyExpectedResult, rudy);
        assertThat(rudyExpectedResult, equalTo(rudy));
    }
}
