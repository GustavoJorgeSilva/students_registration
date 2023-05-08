package com.example.studentsregistrations.controller;


import com.example.studentsregistrations.DB.DB;
import com.example.studentsregistrations.DB.DbException;
import com.example.studentsregistrations.model.entities.Course;
import com.example.studentsregistrations.model.entities.Student;
import com.example.studentsregistrations.model.entities.dao.StudentDao;
import com.example.studentsregistrations.model.entities.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDaoJDBC implements StudentDao {

    private Connection connection;

    public StudentDaoJDBC(Connection connection) {
        this.connection = connection;

    }

    @Override
    public void insert(Student obj) {

        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement("INSERT INTO students (name,email,age,courseId) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, obj.getName());
            statement.setString(2, obj.getEmail());
            statement.setDouble(3, obj.getAge());
            if (obj.getCourse() != null) {
                statement.setInt(4, obj.getCourse().getId());
            } else {
                statement.setNull(4, java.sql.Types.INTEGER);
            }

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(resultSet);
            } else {
                throw new DbException("Unexpected error! no rows affected");
            }

        } catch (SQLException e) {
            throw new DbException("erro");
        } finally {
            DB.closeStatement(statement);
        }

    }

    @Override
    public void update(Student obj) {

        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement("update students " +
                    "set name = ?, email = ?, age = ?, courseId = ? " +
                    "where id = ?");

            statement.setString(1, obj.getName());
            statement.setString(2, obj.getEmail());
            statement.setInt(3, obj.getAge());
            statement.setInt(4, obj.getCourse().getId());
            statement.setInt(5, obj.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }

    }

    @Override
    public void deleteById(Integer id) {

        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement("delete from students " +
                    "where id = ?");

            statement.setInt(1, id);

            int row = statement.executeUpdate();

            if (row == 0) {
                throw new DbException("ID non-existent in the dataBase");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }

    }

    @Override
    public Student findById(Integer id) {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            statement = connection.prepareStatement("SELECT s.id, s.name AS student_name, s.email, s.age, c.id AS course_id, c.name AS course_name, t.id AS teacher_id, t.teacher_name AS teacher_name, t.email AS teacher_email, t.age AS teacher_age " +
                    "FROM students s " +
                    "INNER JOIN courses c ON s.courseId = c.id " +
                    "INNER JOIN teacher t ON c.teacher_id = t.id " +
                    "WHERE s.id = ?");

            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Teacher teacher = instantiateTeacher(resultSet);
                Course course = instantiateCourse(resultSet, teacher);
                Student student = instantiateStudent(resultSet, course);
                return student;
            }
            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }


    }

    @Override
    public List<Student> findAll() {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            statement = connection.prepareStatement("SELECT students.id, students.name AS student_name, students.email, students.age, c.id AS course_id, c.name AS course_name, \n" +
                    "    t.id AS teacher_id, t.teacher_name, t.email AS teacher_email, t.age AS teacher_age \n" +
                    "FROM students \n" +
                    "LEFT JOIN courses c ON students.courseId = c.id \n" +
                    "LEFT JOIN teacher t ON c.teacher_id = t.id;");

            resultSet = statement.executeQuery();
            List<Student> students = new ArrayList<>();
            Map<Integer, Course> map = new HashMap<>();

            while (resultSet.next()) {
                Teacher teacher = instantiateTeacher(resultSet);
                Integer courseId = resultSet.getInt("course_id");
                Course course = null;
                if (!resultSet.wasNull()) {
                    course = map.get(courseId);
                    if (course == null) {
                        course = instantiateCourse(resultSet, teacher);
                        map.put(courseId, course);
                    }
                }

                Student student = instantiateStudent(resultSet, course);
                students.add(student);
            }
            return students;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }


    }


    private Student instantiateStudent(ResultSet resultSet, Course course) throws SQLException {
        Student obj = new Student();
        obj.setId(resultSet.getInt("id"));
        obj.setName(resultSet.getString("student_name"));
        obj.setEmail(resultSet.getString("email"));
        obj.setAge(resultSet.getInt("age"));
        obj.setCourse(course);
        return obj;
    }

    private Course instantiateCourse(ResultSet resultSet, Teacher teacher) throws SQLException {
        Course course = new Course();
        course.setId(resultSet.getInt("course_id"));
        course.setName(resultSet.getString("course_name"));
        course.setTeacher(teacher);
        return course;
    }

    private Teacher instantiateTeacher(ResultSet resultSet) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setId(resultSet.getInt("teacher_id"));
        teacher.setAge(resultSet.getInt("teacher_age"));
        teacher.setEmail(resultSet.getString("teacher_email"));
        teacher.setName(resultSet.getString("teacher_name"));
        return teacher;
    }


}
