package org.sobngwi.oca.collections.uselists.comparator;

import java.util.Comparator;

public class Student {

    private String name;
    private float gpa;
    private int height;
    private int debt;

    private Student(String name, float gpa, int height, int debt) {
        this.name = name;
        this.gpa = gpa;
        this.height = height;
        this.debt = debt;
    }

    private Student(String name, float gpa){
        this.name = name;
        this.gpa = gpa;
    }
    public static Student fromNameGpaHeigthDebt(String name, float gpa, int height, int debt){
        return new Student(name, gpa, height, debt );
    }

    public static Student fromNameAndGpa(String name, float gpa) {
        return new Student(name, gpa);
    }


    public static Comparator<Student> getGradeComparator() {
        return gradeComparator;
    }
    public static Comparator<Student> getNameComparator()  { return nameComparator; }


    private static class GradeComparator
            implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            return Float.compare(o1.getGpa(), o2.getGpa());
        }
    }

    private static class NameComparator
            implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            return o1.name.compareTo(o2.name);
        }
    }

    private static final Comparator<Student> gradeComparator = new GradeComparator();
    private static final Comparator<Student> nameComparator = new NameComparator();

    public String getName() {
        return name;
    }

    public float getGpa() {
        return gpa;
    }

    public int getHeight() {
        return height;
    }

    public int getDebt() {
        return debt;
    }

    @Override
    public String toString() {
        return "Student{" + "name=" + name + ", gpa=" + gpa
                + ", height=" + height + ", debt=" + debt + '}';
    }

}
