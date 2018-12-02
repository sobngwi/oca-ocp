package org.sobngwi.oca.collections.uselists.comparator;

import java.util.Comparator;

public class StudentGradeComparator implements Comparator<Student> {

    @Override
    public int compare(Student o1, Student o2) {
        System.out.println("StudentGradeComparator");
        return Float.compare(o1.getGpa(), o2.getGpa());
    }

}
