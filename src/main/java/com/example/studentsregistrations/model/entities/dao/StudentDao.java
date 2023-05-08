package com.example.studentsregistrations.model.entities.dao;


import com.example.studentsregistrations.model.entities.Student;

import java.util.List;

public interface StudentDao {

    public void insert(Student obj);

    public void update(Student obj);

    public void deleteById(Integer id);

    public Student findById(Integer id);

    public List<Student> findAll();

}
