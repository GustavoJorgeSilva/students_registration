package com.example.studentsregistrations.model.entities.dao;


import com.example.studentsregistrations.model.entities.Teacher;

import java.util.List;

public interface TeacherDao {
    public void insert(Teacher obj);

    public void update(Teacher obj);

    public void deleteById(Integer id);

    public Teacher findById(Integer id);

    public List<Teacher> findAll();

}
