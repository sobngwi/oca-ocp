package org.sobngwi.oca.mock;

import org.sobngwi.oca.model.Person;

import java.util.List;

public interface PersonDao {

    List<Person> getAllPersons();

    Person getPersonById(int id);
}
