package edu.virginia.cs.hw7;

import java.util.List;

public class TestDatabaseImp {
    public static void main(String args[]){

        DatabaseManager manager = new DatabaseHelper();
        /*
        //manager.addCourse(1000, "CS");
        //System.out.println(manager.getReviews());
        List<Student> studentList = manager.getStudents();
        for (Student s:studentList){
            System.out.println(s);
        }
        List<Course> courses = manager.getCourses();
        for (Course c:courses){
            System.out.println(manager.averageReview(c));
        }*/

        //manager.addStudent("test", "testPass");
        System.out.println(manager.getStudentFromUsername("test").getComputingID());
        Student a = manager.getStudent("test");
        Course c = manager.getCourse(3140, "CS");
        //Review addRev = new Review(a, c, "AAAHHHH", 1);
        //manager.addReview2(addRev);
        System.out.println(manager.checkForReview(a,c));

    }

/*    public void testcheckforStudent(){

    }*/
}
