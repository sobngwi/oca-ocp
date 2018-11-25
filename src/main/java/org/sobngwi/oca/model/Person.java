package org.sobngwi.oca.model;

import java.time.LocalDate;
import java.util.Random;

public class Person {


    private int personId;
    private String firstName;



    private String lastName;
    private LocalDate dateOdbirth;
    private Random random = new Random();

    public Person(String firstName, String lastName, LocalDate dateOdbirth) {
        this.personId = random.nextInt(100);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOdbirth = dateOdbirth;
    }

    public int getPersonId() {
        return personId;
    }
    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId='" + personId + '\'' +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOdbirth=" + dateOdbirth +
                '}';
    }
}
