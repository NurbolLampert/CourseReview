package edu.virginia.cs.hw7;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.logging.Level;

public class DatabaseHelper implements DatabaseManager{

    private Session session;
    @Override
    public void connect() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
    }

    @Override
    public double averageReview(Course course) {

        List<Review> courseReviews = getCourseReviews(course);
        int size = courseReviews.size();
        double rating = 0;
        for (Review r:courseReviews){
            rating += r.getRating();
        }
        return rating/size;

    }

    @Override
    public Student addStudent(String computingId, String password) {
        connect();
        Student newStudent = new Student();
        newStudent.setComputingID(computingId);
        newStudent.setPassword(password);
        //session.save(newStudent);

        session.persist(newStudent);
        session.getTransaction().commit();

        //disconnect();
        //Transaction t=session.beginTransaction();
        //t.commit();
        return newStudent;
    }

    @Override
    public Course addCourse(int catalogNumber, String department) {
        connect();
        Course newCourse = new Course();
        newCourse.setCatalogNumber(catalogNumber);
        newCourse.setDepartment(department);
        session.persist(newCourse);
        session.getTransaction().commit();
        disconnect();
        return newCourse;
    }

    @Override
    public Review addReview(int rating, String review, Course course, Student student) {
        connect();
        Review newReview = new Review();
        newReview.setRating(rating);
        newReview.setReview(review);
        newReview.setReviewedCourse(course);
        newReview.setReviewer(student);
        session.persist(newReview);
        session.getTransaction().commit();
        disconnect();
        return newReview;
    }

    @Override
    public Review addReview2(Review review){
        connect();
        session.persist(review);
        session.getTransaction().commit();
        disconnect();
        return review;
    }


    @Override
    public Course getCourse(int catalogNumber, String subject) {
        connect();
        List<Course> courses = getCourses();
        for(int i = 0; i<courses.size(); i++){
            if(courses.get(i).getCatalogNumber() == catalogNumber && courses.get(i).getDepartment().equals(subject)){
                return courses.get(i);
            }
        }
        disconnect();
        return null;
    }

    @Override
    public Student getStudentFromUsername(String username) {
        connect();
        List<Student> students = getStudents();
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getComputingID().equals(username)) {
                return getStudents().get(i);
            }
        }
        disconnect();
        return null; // ERROR during search
    }

    @Override
    public boolean checkPassword(String password, Student student) {
        connect();
        List<Student> students = getStudents();
        for (int i = 0; i < students.size(); i++) {
            if (student.getPassword().equals(password)) {
                return true;
            }
        }
        disconnect();
        return false;
    }


    @Override
    public List<Course> getCourses() {
        connect();
        String allCourses = "FROM Course";
        Query<Course> query = session.createQuery(allCourses, Course.class);
        List<Course> courses = query.getResultList();
        disconnect();
        return courses;
    }

    //needs to pass in something to add in SubmitReviewDialog
    //nvm don't touch it yet
    @Override
    public Student getStudent(){
        connect();
        String allStudents = "FROM Student";
        Query<Student> query = session.createQuery(allStudents, Student.class);
        Student student = query.uniqueResult();
        disconnect();
        return student;
    }

    @Override
    public Student getStudent(String username){
        connect();
        List<Student> allStudents = getStudents();
        for(Student s:allStudents){
            if (s.getComputingID().equals(username)){
                return s;
            }
        }
        return null;
    }

    @Override
    public List<Student> getStudents() {

        connect();
        String allStudents = "FROM Student";
        Query<Student> query = session.createQuery(allStudents, Student.class);
        List<Student> student = query.getResultList();
        disconnect();
        return student;
    }

    @Override
    public List<Review> getReviews() {
        connect();
        String allReviews = "FROM Review";
        Query<Review> query = session.createQuery(allReviews, Review.class);
        List<Review> reviews = query.getResultList();
        disconnect();
        return reviews;
    }

    @Override
    public List<Review> getStudentReviews(Student student) {
        connect();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Review> criteria = builder.createQuery(Review.class);
        Root<Review> root = criteria.from(Review.class);
        Predicate studentID = builder.equal(root.get("reviewer"), student.getID());
        criteria.select(root).where(builder.and(studentID));

        Query<Review> thisQuery = session.createQuery(criteria);
        List<Review> sReviews = thisQuery.getResultList();
        disconnect();
        return sReviews;

    }

    @Override
    public List<Review> getCourseReviews(Course course) {
        connect();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Review> criteria = builder.createQuery(Review.class);
        Root<Review> root = criteria.from(Review.class);
        Predicate courseID = builder.equal(root.get("reviewedCourse"), course.getId());
        criteria.select(root).where(builder.and(courseID));

        Query<Review> thisQuery = session.createQuery(criteria);
        List<Review> sReviews = thisQuery.getResultList();
        disconnect();
        return sReviews;
    }

    @Override
    public boolean checkForStudent (String computingID){
            List<Student> allStudents = getStudents();
            for (Student s: allStudents){

                if(s.getComputingID().equals(computingID)){
                    return true;
                }
            }
            return false;
    }

    @Override
    public boolean checkForCourse (int courseNumber, String department){

        String[] arrof = department.split(" ");
        String newdep = arrof[0];
        List<Course> allCourses = getCourses();
        for (Course c: allCourses){
            if(c.getCatalogNumber() == courseNumber && c.getDepartment().equals(newdep)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkForReview (Student s, Course c){
        List<Review> allReviews = getReviews();
        for (Review r: allReviews){
            if(r.getReviewer().getComputingID().equals(s.getComputingID()) && r.getReviewedCourse().getCatalogNumber()==(c.getCatalogNumber()) && r.getReviewedCourse().getDepartment().equals(c.getDepartment())){
                return true;
            }
        }
        return false;
    }

    @Override
    public void disconnect() {
        session.close();
        //HibernateUtil.shutdown();
    }


}
