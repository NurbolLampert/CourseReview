package edu.virginia.cs.hw7;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@javax.persistence.Entity
@javax.persistence.Table(name = "Reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="Reviewer_ID", referencedColumnName="ID")
    private Student reviewer;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="Course_ID", referencedColumnName="ID")
    private Course reviewedCourse;


    @Column(name="Review", nullable = false)
    private String review;

    @Column(name="Rating", nullable = false)
    private int rating;

    public Review(int id, Student reviewer, Course reviewedCourse, String review, int rating){
        this.id = id;
        this.reviewer = reviewer;
        this.reviewedCourse = reviewedCourse;
        this.review = review;
        this.rating = rating;
    }
    public Review(Student reviewer, Course reviewedCourse, String review, int rating){
        this.reviewer = reviewer;
        this.reviewedCourse = reviewedCourse;
        this.review = review;
        this.rating = rating;
    }

    public Review(){}

    public int getId(){
        return  id;
    }

    public void setId(int id){
        this.id = id;
    }
    public Student getReviewer(){
        return reviewer;
    }

    public void setReviewer(Student reviewer){
        this.reviewer = reviewer;
    }

    public Course getReviewedCourse() {
        return reviewedCourse;
    }

    public void setReviewedCourse(Course reviewedCourse) {
        this.reviewedCourse = reviewedCourse;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}