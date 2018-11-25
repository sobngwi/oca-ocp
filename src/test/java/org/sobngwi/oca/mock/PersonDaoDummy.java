package org.sobngwi.oca.mock;


import org.sobngwi.oca.model.Person;

import java.util.List;

public class PersonDaoDummy implements PersonDao {
    @Override
    public List<Person> getAllPersons() {
        return null;
    }

    @Override
    public Person getPersonById(int id) {
        return null;
    }
}
