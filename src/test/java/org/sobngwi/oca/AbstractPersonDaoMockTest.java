package org.sobngwi.oca;

import org.junit.Before;
import org.sobngwi.oca.mock.PersonService;
import org.sobngwi.oca.mock.PersonDao;
import org.sobngwi.oca.model.Person;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

public class AbstractPersonDaoMockTest {

    protected PersonDao personDaoMock;

    public PersonService getPersonService() {
        return personService;
    }

    protected PersonService personService; //aka testSubject
    protected Person rudy = new Person("Rudy", "SOBNGWI", LocalDate.of(2004, Month.JANUARY, 31));
    protected Person anneGaelle = new Person("Anne-Gaelle", "SOBNGWI", LocalDate.of(2007, Month.AUGUST, 10));
    protected Person anonymous = new Person("anonymous", "anonymous", null);
    protected List<Person> allPersons = Arrays.asList(rudy,anneGaelle);


    @Before
    public void setUp() {
        personDaoMock = mock(PersonDao.class);
        personService = new PersonService(personDaoMock);
    }

}
