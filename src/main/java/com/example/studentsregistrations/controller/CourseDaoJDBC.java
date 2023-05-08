package com.example.studentsregistrations.controller;


import com.example.studentsregistrations.DB.DB;
import com.example.studentsregistrations.DB.DbException;
import com.example.studentsregistrations.model.entities.Course;
import com.example.studentsregistrations.model.entities.Teacher;
import com.example.studentsregistrations.model.entities.dao.CourseDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseDaoJDBC implements CourseDao {

    private Connection connection;

    public CourseDaoJDBC(Connection connection) {
        this.connection = connection;

    }

    @Override
    public void insert(Course obj) {

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("insert into courses (name,teacher_id) " +
                    "values (?,?)", Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, obj.getName());

            if (obj.getTeacher() != null && obj.getTeacher().getId() != null) {
                statement.setInt(2, obj.getTeacher().getId());
            } else {
                throw new DbException("Teacher ID is null");
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
                throw new DbException("Error unexpected! no rows affected");
            }


        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }


    }

    @Override
    public void update(Course obj) {

        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement("update courses " +
                    "set name = ?, teacher_id = ? " +
                    "where id = ?");

            statement.setString(1, obj.getName());
            statement.setInt(2, obj.getTeacher().getId());
            statement.setInt(3, obj.getId());

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

            statement = connection.prepareStatement("delete from courses " +
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
    public Course findById(Integer id) {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            statement = connection.prepareStatement("SELECT c.*, t.id as teacher_id, t.teacher_name as teacher_name, t.age as age, t.email as email " +
                    "FROM courses c " +
                    "INNER JOIN teacher t ON c.teacher_id = t.id " +
                    "WHERE c.id = ?");

            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Teacher teacher = instantiateTeacher(resultSet);
                Course course = instantiateCourse(resultSet, teacher);
                return course;


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
    public List<Course> findAll() {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            statement = connection.prepareStatement("select courses.*,teacher_name as teacher_name, teacher_id as teacher_id, age as age, email as email " +
                    "from courses inner join teacher t on courses.teacher_id = t.id " +
                    "order by name");
            resultSet = statement.executeQuery();
            List<Course> courses = new ArrayList<>();
            Map<Integer, Teacher> map = new HashMap<>();

            while (resultSet.next()) {

                Teacher teacher = map.get(resultSet.getInt("teacher_id"));

                if (teacher == null) {
                    teacher = instantiateTeacher(resultSet);
                    map.put(resultSet.getInt("teacher_id"), teacher);
                }

                Course obj = instantiateCourse(resultSet, teacher);
                courses.add(obj);

            }
            return courses;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }


    }

    private Course instantiateCourse(ResultSet resultSet, Teacher teacher) throws SQLException {
        Course course = new Course();
        course.setId(resultSet.getInt("id"));
        course.setName(resultSet.getString("name"));
        course.setTeacher(teacher);
        return course;
    }

    private Teacher instantiateTeacher(ResultSet resultSet) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setId(resultSet.getInt("teacher_id"));
        teacher.setAge(resultSet.getInt("age"));
        teacher.setEmail(resultSet.getString("email"));
        teacher.setName(resultSet.getString("teacher_name"));
        return teacher;
    }


}
