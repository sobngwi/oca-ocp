package org.sobngwi.oca.collections.uselists.comparator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sobngwi.oca.exceptions.Rules;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class StudentTest extends Rules {

    private   List<Student> dummyStudents;

    @Before
    public void setUp(){
        dummyStudents = StudentOrders.getStudentsList();
    }

    @Test
    public void sortStudentsByName() {

        dummyStudents.sort(Student.getNameComparator());

        assertThat(dummyStudents.get(0).getName(), equalTo("Alain"));
        assertThat(dummyStudents.get(1).getName(), equalTo("Alison"));
        assertThat(dummyStudents.get(2).getName(), equalTo("Barney"));
        assertThat(dummyStudents.get(3).getName(), equalTo("Fred"));
        assertThat(dummyStudents.get(4).getName(), equalTo("Jim"));
        assertThat(dummyStudents.get(5).getName(), equalTo("Sheila"));
        assertThat(dummyStudents.get(6).getName(), equalTo("Toni"));
        assertThat(dummyStudents.get(7).getName(), equalTo("Vanessa"));
    }

    @Test
    public void sortsStudentByGrade() {

        dummyStudents.sort(Student.getGradeComparator());

        assertThat(dummyStudents.get(0).getName(), equalTo("Barney"));
        assertThat(dummyStudents.get(1).getName(), equalTo("Toni"));
        assertThat(dummyStudents.get(2).getName(), equalTo("Jim"));
        assertThat(dummyStudents.get(3).getName(), equalTo("Vanessa"));
        assertThat(dummyStudents.get(4).getName(), equalTo("Fred"));
        assertThat(dummyStudents.get(5).getName(), equalTo("Alain"));
        assertThat(dummyStudents.get(6).getName(), equalTo("Alison"));
        assertThat(dummyStudents.get(7).getName(), equalTo("Sheila"));
    }

    @Test
    public void sortStudentsByNameReverseOrder() {
        Comparator<Student> studentComparator = Student.getNameComparator();

        dummyStudents.sort(studentComparator.reversed());

        assertThat(dummyStudents.get(6).getName(), equalTo("Alison"));
        assertThat(dummyStudents.get(5).getName(), equalTo("Barney"));
        assertThat(dummyStudents.get(4).getName(), equalTo("Fred"));
        assertThat(dummyStudents.get(3).getName(), equalTo("Jim"));
        assertThat(dummyStudents.get(2).getName(), equalTo("Sheila"));
        assertThat(dummyStudents.get(1).getName(), equalTo("Toni"));
        assertThat(dummyStudents.get(0).getName(), equalTo("Vanessa"));
    }


    @Test
    public void sortStudentByGradeReverseOrder() {

        Comparator<Student> reversedGradeComparator = Collections.reverseOrder(Student.getGradeComparator());
        dummyStudents.sort(reversedGradeComparator);

        assertThat(dummyStudents.get(7).getName(), equalTo("Barney"));
        assertThat(dummyStudents.get(6).getName(), equalTo("Toni"));
        assertThat(dummyStudents.get(5).getName(), equalTo("Jim"));
        assertThat(dummyStudents.get(4).getName(), equalTo("Vanessa"));
        assertThat(dummyStudents.get(3).getName(), equalTo("Fred"));
        assertThat(dummyStudents.get(2).getName(), equalTo("Alison"));
        assertThat(dummyStudents.get(1).getName(), equalTo("Alain"));
        assertThat(dummyStudents.get(0).getName(), equalTo("Sheila"));
    }


    @Test
    public void sortStudentsByGradeAndThenByName() {
        Comparator<Student> gradeAndNamesComparator = Student.getGradeComparator().thenComparing(Student.getNameComparator());
        dummyStudents.sort(gradeAndNamesComparator);
        assertThat(dummyStudents.get(0).getName(), equalTo("Barney"));
        assertThat(dummyStudents.get(1).getName(), equalTo("Toni"));
        assertThat(dummyStudents.get(2).getName(), equalTo("Jim"));
        assertThat(dummyStudents.get(3).getName(), equalTo("Vanessa"));
        assertThat(dummyStudents.get(4).getName(), equalTo("Fred"));
        assertThat(dummyStudents.get(5).getName(), equalTo("Alain"));
        assertThat(dummyStudents.get(6).getName(), equalTo("Alison"));
        assertThat(dummyStudents.get(7).getName(), equalTo("Sheila"));

        dummyStudents.sort(gradeAndNamesComparator.reversed());

        assertThat(dummyStudents.get(0).getName(), equalTo("Sheila"));
        assertThat(dummyStudents.get(1).getName(), equalTo("Alison"));
        assertThat(dummyStudents.get(2).getName(), equalTo("Alain"));
        assertThat(dummyStudents.get(7).getName(), equalTo("Barney"));
    }


    @Test
    public void sortStudentByHeightShouldThrowAnExceptionAsTonyHeightIsNull() {

        thrown.expect(RuntimeException.class);
        dummyStudents.sort(Comparator.comparing(Student::getHeight));
    }

    @Test
    public void sortStudentByHeightNullLast() {
        dummyStudents.sort((Student.getHeightComparatorNullLast()));

        assertThat(dummyStudents.get(0).getName(), equalTo("Sheila"));
        assertThat(dummyStudents.get(1).getName(), equalTo("Alain"));
        assertThat(dummyStudents.get(6).getName(), equalTo("Toni"));
        assertThat(dummyStudents.get(7).getName(), equalTo("Vanessa"));

    }

    @Test
    public void sortStudentByHeightNullFirst() {
        dummyStudents.sort((Student.getHeightComparatorNullFirst()));

        assertThat(dummyStudents.get(0).getName(), equalTo("Toni"));
        assertThat(dummyStudents.get(1).getName(), equalTo("Vanessa"));
        assertThat(dummyStudents.get(2).getName(), equalTo("Sheila"));
        assertThat(dummyStudents.get(3).getName(), equalTo("Alain"));
        assertThat(dummyStudents.get(6).getName(), equalTo("Jim"));
        assertThat(dummyStudents.get(7).getName(), equalTo("Alison"));
    }

    @Test
    public void sortStudentByHeightNullFirstByApi() {
       Comparator<Student> sortStudentByHeightNullFirst =
        Comparator.comparing(s-> s.getHeight(), Comparator.nullsFirst(Comparator.naturalOrder()));

        dummyStudents.sort((sortStudentByHeightNullFirst));
        assertThat(dummyStudents.get(0).getName(), equalTo("Toni"));
        assertThat(dummyStudents.get(1).getName(), equalTo("Vanessa"));
        assertThat(dummyStudents.get(2).getName(), equalTo("Sheila"));
        assertThat(dummyStudents.get(3).getName(), equalTo("Alain"));
        assertThat(dummyStudents.get(6).getName(), equalTo("Jim"));
        assertThat(dummyStudents.get(7).getName(), equalTo("Alison"));

    }

    @Test
    public void sortStudentByHeightNullLastByApi() {

        Comparator<Student> sortStudentByHeightNullLast =
                Comparator.comparing(s-> s.getHeight(), Comparator.nullsLast(Comparator.naturalOrder()));
        dummyStudents.sort(sortStudentByHeightNullLast);

        assertThat(dummyStudents.get(0).getName(), equalTo("Sheila"));
        assertThat(dummyStudents.get(1).getName(), equalTo("Alain"));
        assertThat(dummyStudents.get(6).getName(), equalTo("Toni"));
        assertThat(dummyStudents.get(7).getName(), equalTo("Vanessa"));

    }
}