package com.example.studentsregistrations.model.entities;

public class Teacher {

    private Integer id;
    private String name;
    private Integer age;
    private String email;

    public Teacher() {
    }

    public Teacher(String name, Integer age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Id do professor: " + id + " ");
        sb.append("Nome do professor: " + this.getName() + " ");
        sb.append("Idade: " + age + " ");
        sb.append("Email: " + email);


        return sb.toString();
    }
}
