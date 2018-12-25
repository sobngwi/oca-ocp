package org.sobngwi.oca.collections.uselists.comparator;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class StudentTest {

    private   List<Student> dummyStudents;

    @Before
    public void setUp(){
        dummyStudents = StudentOrders.getStudentsList();
    }

    @Test
    public void sortStudentsByname() {

        dummyStudents.sort(Student.getNameComparator());

        assertThat(dummyStudents.get(0).getName(), equalTo("Alison"));
        assertThat(dummyStudents.get(1).getName(), equalTo("Barney"));
        assertThat(dummyStudents.get(2).getName(), equalTo("Fred"));
        assertThat(dummyStudents.get(3).getName(), equalTo("Jim"));
        assertThat(dummyStudents.get(4).getName(), equalTo("Sheila"));
        assertThat(dummyStudents.get(5).getName(), equalTo("Toni"));
        assertThat(dummyStudents.get(6).getName(), equalTo("Vanessa"));
    }

    @Test
    public void sortsStudentByGrade() {

        dummyStudents.sort(Student.getGradeComparator());

        assertThat(dummyStudents.get(0).getName(), equalTo("Barney"));
        assertThat(dummyStudents.get(1).getName(), equalTo("Toni"));
        assertThat(dummyStudents.get(2).getName(), equalTo("Jim"));
        assertThat(dummyStudents.get(3).getName(), equalTo("Vanessa"));
        assertThat(dummyStudents.get(4).getName(), equalTo("Fred"));
        assertThat(dummyStudents.get(5).getName(), equalTo("Alison"));
        assertThat(dummyStudents.get(6).getName(), equalTo("Sheila"));
    }
}