package edu.virginia.cs.hw7;

import java.util.List;

public interface DatabaseManager {

    void connect();

    double averageReview(Course course);

    Student addStudent(String computingId, String password);

    Student getStudent();

    Course getCourse(int catalogNumber, String subject);

    Course addCourse(int catalogNumber, String department);

    Review addReview(int rating, String review, Course course, Student student);

    List<Course> getCourses();

    Student getStudent(String username);

    List<Student> getStudents();

    List<Review> getReviews();

    List<Review> getStudentReviews(Student student);

    List<Review> getCourseReviews(Course course);

    boolean checkForStudent(String computingID);
    boolean checkPassword(String password, Student student);

    Student getStudentFromUsername(String username);


    boolean checkForCourse(int courseNumber, String department);

    boolean checkForReview(Student s, Course c);

    void disconnect();

    Review addReview2(Review addR);
}
