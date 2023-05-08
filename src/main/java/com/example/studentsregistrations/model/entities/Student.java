package com.example.studentsregistrations.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student implements Serializable {

    private Integer id;
    private String name;
    private String email;
    private Integer age;
    private Course course;

    public Student() {
    }

    public Student(String name, String email, Integer age, Course course) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.course = course;
    }

    public Student(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;

    }


    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Id do aluno: " + id + ", ");
        sb.append("Nome do aluno: " + name + ", ");
        sb.append("Idade: " + age + ", ");
        sb.append("Email: " + email + ", ");
        sb.append(course);


        return sb.toString();
    }


}
