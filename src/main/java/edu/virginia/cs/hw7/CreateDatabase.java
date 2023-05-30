package edu.virginia.cs.hw7;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.logging.Level;
public class CreateDatabase {


    //private static Session session;
    public static void main(String... args) {
        try
        {
            java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
            Configuration config=new Configuration();
            config.configure();
            SessionFactory sessionFactory=config.buildSessionFactory();
            Session session=sessionFactory.openSession();

            Student first = new Student(1, "x12345", "password1");
            Student second = new Student(2, "tree13", "password2");
            Student third = new Student(3, "id2468", "password3");
            Student fourth = new Student(4, "44iii4", "password4");


            Course one = new Course(1, "CS", 3140);
            Course two = new Course(2, "CS", 2100);
            Course three = new Course(3, "CS", 2120);
            Course four = new Course(4, "SYS", 2001);
            Course five = new Course(4, "SYS", 2202);

            Review r1 = new Review(1, first, one, "I liked the projects in this class", 4);
            Review r2 = new Review(2, first, two, "This class is super easy", 5);
            Review r3 = new Review(3, second, one, "I had such a hard time in this class. It has an emphasis on group coding, so make sure you find a good group of people to work with", 2);
            Review r4 = new Review(4, third, three, "There are sooo many quizzes in this class. You won't pass if you don't study", 3);
            Review r5 = new Review(5, third, one, "I really dislike coding, so this class wasn't for me. If you like coding, you'll like this class", 3);

            ArrayList<Review> sR1 = new ArrayList<>();
            sR1.add(r1);
            sR1.add(r2);
            first.setReviews(sR1);

            ArrayList<Review> sR2 = new ArrayList<>();
            sR2.add(r3);
            first.setReviews(sR2);

            ArrayList<Review> sR3 = new ArrayList<>();
            sR3.add(r4);
            sR3.add(r5);
            first.setReviews(sR3);

            ArrayList<Review> cR1 = new ArrayList<>();
            cR1.add(r1);
            cR1.add(r3);
            cR1.add(r5);
            one.setReviews(cR1);

            ArrayList<Review> cR2 = new ArrayList<>();
            cR2.add(r2);
            two.setReviews(cR2);

            ArrayList<Review> cR3 = new ArrayList<>();
            cR3.add(r4);
            three.setReviews(cR3);

            session.save(first);
            session.save(second);
            session.save(third);
            session.save(fourth);

            session.save(one);
            session.save(two);
            session.save(three);
            session.save(four);
            session.save(five);

            session.save(r1);
            session.save(r2);
            session.save(r3);
            session.save(r4);
            session.save(r5);


            Transaction t=session.beginTransaction();
            t.commit();
            HibernateUtil.shutdown();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}