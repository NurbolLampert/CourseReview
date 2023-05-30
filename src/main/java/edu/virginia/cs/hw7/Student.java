package edu.virginia.cs.hw7;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@javax.persistence.Entity
@javax.persistence.Table(name = "Students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name="ComputingID", unique = true, nullable = false)
    private String computingID;

    @Column(name="Password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "reviewer")
    private List<Review> reviews;


    public Student(int id, String computingID, String password){
        this.id = id;
        this.computingID = computingID;
        this.password = password;
    }

    public Student(int id, String computingID, String password, List<Review> reviews){
        this.id = id;
        this.computingID = computingID;
        this.password = password;
        this.reviews = reviews;
    }

    public Student(String computingID, String password){
        this.id = id;
        this.computingID = computingID;
        this.password = password;
    }

    public Student(String computingID, String password, List<Review> reviews){
        this.id = id;
        this.computingID = computingID;
        this.password = password;
        this.reviews = reviews;
    }

    public Student(){ reviews =  new ArrayList<>();}

    public int getID(){
        return id;
    }

    public void setID(int id){
        this.id = id;
    }

    public String getComputingID(){
        return computingID;
    }

    public void setComputingID(String id){
        this.computingID = id;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public List<Review> getReviews(){ return this.reviews; }

    public void setReviews(List<Review> reviews){
        this.reviews = reviews;
    }


}