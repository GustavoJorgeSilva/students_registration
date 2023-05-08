package com.example.studentsregistrations.model.entities;

import java.io.Serializable;


public class Course implements Serializable {

    private Integer id;
    private String name;
    private Teacher teacher;

    public Course() {
    }

    public Course(String name, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;

    }

    public Integer getId() {
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Id do curso: " + id + ", ");
        sb.append("Curso: " + this.name + ", ");
        sb.append("Id professor: " + teacher.getId() + ", ");
        sb.append("Professor: " + teacher.getName() + ", ");
        sb.append("Idade: " + teacher.getAge() + ", ");
        sb.append("Email do professor: " + teacher.getEmail() + " ");

        return sb.toString();
    }
}
