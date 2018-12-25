package org.sobngwi.oca.collections.uselists.comparator;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class StudentOrders {
    public static void showAll(List<Student> ls) {
        for (Student s : ls) {
            System.out.println(s);
        }
        System.out.println("---------------------------");
    }

    public static void main(String[] args) {
        List<Student> ls = getStudentsList();
        ls.sort(Student.getGradeComparator());
        showAll(ls);
        ls.sort(Student.getNameComparator());
        showAll(ls);
    }

    public static List<Student> getStudentsList() {
        return Arrays.asList(
                Student.fromNameGpaHeigthDebt("Fred", 3.2F, 72, 12_000),
                Student.fromNameGpaHeigthDebt("Alain", 3.6F, 70, 1_000),
                Student.fromNameGpaHeigthDebt("Jim", 2.9F, 73, 4_000),
                Student.fromNameGpaHeigthDebt("Sheila", 4.0F, 58, 2_000),
                Student.fromNameGpaHeigthDebt("Alison", 3.6F, 73, 6_000),
                Student.fromNameGpaHeigthDebt("Barney", 1.2F, 70, 21_000),
                Student.fromNameAndGpa("Toni", 2.8F),
                Student.fromNameAndGpa("Vanessa", 3.1F));
    }

    public static Map<String, List<Student>>  getStudentsMap( ){ ;
        return  getStudentsList().stream().collect(Collectors.groupingByConcurrent(s -> s.getName()));
    }

   /* public static Map<String, List<Student>>  getStudentsMapConsTructByhand( ) {
        Map<String, List<Student>> maps = new HashMap<>();
        return
    }*/
    /*
    new ArrayList<>(Arrays.asList(
                Student.fromNameGpaHeigthDebt("Fred", 3.2F, 72, 12_000),
                Student.fromNameGpaHeigthDebt("Jim", 2.9F, 73, 4_000),
                Student.fromNameGpaHeigthDebt("Sheila", 4.0F, 58, 2_000),
                Student.fromNameGpaHeigthDebt("Alison", 3.6F, 73, 6_000),
                Student.fromNameGpaHeigthDebt("Barney", 1.2F, 70, 21_000),
                Student.fromNameGpaHeigthDebt("Toni", 2.8F, 67, 18_000),
                Student.fromNameGpaHeigthDebt("Vanessa", 3.1F, 65, 9_000)
        ));
     */
}
