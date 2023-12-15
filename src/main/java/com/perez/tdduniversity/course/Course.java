package com.perez.tdduniversity.course;

import com.perez.tdduniversity.student.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course implements Serializable {

    @Id
    private String courseId;
    private String name;
    private String description;
    private Integer credits;
    private Integer weeklyHours;

    @ManyToMany
    private List<Student> students = new ArrayList<>();

    public Course() {
    }

    public Course(String courseId, String name, String description, Integer credits, Integer weeklyHours, List<Student> students){
        this.courseId = courseId;
        this.name = name;
        this.description = description;
        this.credits = credits;
        this.weeklyHours = weeklyHours;
        this.students = students;
    }

    public Course(String courseId, String name, String description, Integer credits, Integer weeklyHours) {
        this.courseId = courseId;
        this.name = name;
        this.description = description;
        this.credits = credits;
        this.weeklyHours = weeklyHours;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(Integer weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", credits=" + credits +
                ", weeklyHours=" + weeklyHours +
                ", students=" + students +
                '}';
    }
}
