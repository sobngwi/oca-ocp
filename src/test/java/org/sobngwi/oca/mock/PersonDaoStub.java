package org.sobngwi.oca.mock;

import org.sobngwi.oca.model.Person;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class PersonDaoStub implements PersonDao {

   private List<Person> persons = Arrays.asList(
           new Person("Rudy", "SOBNGWI", LocalDate.of(2004, Month.JANUARY, 31)),
           new Person("Anne-Gaelle", "SOBNGWI", LocalDate.of(2007, Month.AUGUST, 10))
   );

    @Override
    public List<Person> getAllPersons() {
        return persons;
    }

    @Override
    public Person getPersonById(int id) {
        if ( id <= 0 || id >  2) return null;
        return id==1  ? persons.get(0):persons.get(1);
    }
}
