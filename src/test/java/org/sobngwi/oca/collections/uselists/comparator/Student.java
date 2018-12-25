package org.sobngwi.oca.collections.uselists.comparator;

import java.util.Comparator;
import java.util.Objects;

final public class Student {

    private String name;
    private float gpa;
    private Integer height;
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

    public static Comparator<Student> getHeightComparatorNullLast(){

        return (s1, s2) -> {
            if ( ( s1.getHeight() ==null ) && (s2.getHeight()== null))
            return ( new Integer("0")).compareTo(new Integer("0"));
            else if ( s1.getHeight() == null){
                return   s2.getHeight().compareTo(0);
            }
            else if ( s2.getHeight() == null) {
                return ( new Integer("0")).compareTo(s1.getHeight());
            }
            else return Integer.compare(s1.getHeight(), s2.getHeight());
        };
    }
    public static Comparator<Student> getHeightComparatorNullFirst(){

        return (s1, s2) -> {
            if ( ( s1.getHeight() ==null ) && (s2.getHeight()== null))
                return 0;
            else if ( s1.getHeight() == null){
                return  Integer.compare(0, s2.getHeight());
            }
            else if ( s2.getHeight() == null) {
                return Integer.compare( s1.getHeight(), 0);
            }
            else return Integer.compare(s1.getHeight(), s2.getHeight());
        };
    }

    public static Comparator<Student> getGradeComparator() {
        return (f1, f2) -> Float.compare(f1.getGpa(), f2.getGpa());
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

    public Integer getHeight() {
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
