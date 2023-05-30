package edu.virginia.cs.hw7;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@javax.persistence.Entity
@javax.persistence.Table(name = "Courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name="Department", nullable = false)
    private String department;

    @Column(name="Catalog_Number", nullable = false)
    private int catalogNumber;

    @OneToMany(mappedBy = "reviewedCourse")
    private List<Review> reviews;

    public Course(int id, String department, int catalogNumber){
        this.id = id;
        this.department = department;
        this.catalogNumber = catalogNumber;
    }

    public Course(int id, String department, int catalogNumber, List<Review> reviews){
        this.id = id;
        this.department = department;
        this.catalogNumber = catalogNumber;
        this.reviews = reviews;
    }

    public Course(String department, int catalogNumber){
        this.id = id;
        this.department = department;
        this.catalogNumber = catalogNumber;
    }

    public Course(String department, int catalogNumber, List<Review> reviews){
        this.department = department;
        this.catalogNumber = catalogNumber;
        this.reviews = reviews;
    }

    public Course(){reviews =  new ArrayList<>();}

    public int getId(){
        return  id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getDepartment(){
        return department;
    }

    public void setDepartment(String department){
        this.department = department;
    }

    public int getCatalogNumber(){
        return catalogNumber;
    }

    public void setCatalogNumber(int catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public List<Review> getReviews(){ return this.reviews; }

    public void setReviews(List<Review> reviews){
        this.reviews = reviews;
    }
}