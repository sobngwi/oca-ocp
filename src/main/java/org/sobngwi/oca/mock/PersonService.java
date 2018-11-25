package org.sobngwi.oca.mock;

import org.sobngwi.oca.model.Person;

import java.util.List;

public class PersonService {

    private PersonDao personDao;

    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }

    public boolean checkIfPersonExists(String... personName) {
       return  personDao.getAllPersons().stream().anyMatch(p -> p.getLastName().equals(personName));
    }

    public Person findPersonById( int id) {
        return personDao.getPersonById(id);
    }

    public List<Person> findAllPersons(){
        return personDao.getAllPersons();
    }
}
