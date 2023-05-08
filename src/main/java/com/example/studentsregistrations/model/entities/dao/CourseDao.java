package com.example.studentsregistrations.model.entities.dao;

import com.example.studentsregistrations.model.entities.Course;
import com.example.studentsregistrations.model.entities.Student;

import java.util.List;

public interface CourseDao {

    public void insert(Course obj);

    public void update(Course obj);

    public void deleteById(Integer id);

    public Course findById(Integer id);

    public List<Course> findAll();


}
