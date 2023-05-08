package com.example.studentsregistrations.controller;

import com.example.studentsregistrations.DB.DB;
import com.example.studentsregistrations.model.entities.dao.CourseDao;
import com.example.studentsregistrations.model.entities.dao.StudentDao;
import com.example.studentsregistrations.model.entities.dao.TeacherDao;

public class DaoFactory {

    public static StudentDao createStudentDao() {
        return new StudentDaoJDBC(DB.getConnection());
    }

    public static CourseDao createCourseDao() {
        return new CourseDaoJDBC(DB.getConnection());
    }

    public static TeacherDao createTeacherDao() {
        return new TeacherDaoJDBC(DB.getConnection());
    }
}
