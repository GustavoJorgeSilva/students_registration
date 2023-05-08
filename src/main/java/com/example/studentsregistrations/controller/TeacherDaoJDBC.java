package com.example.studentsregistrations.controller;

import com.example.studentsregistrations.DB.DB;
import com.example.studentsregistrations.DB.DbException;
import com.example.studentsregistrations.model.entities.Teacher;
import com.example.studentsregistrations.model.entities.dao.TeacherDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDaoJDBC implements TeacherDao {

    private Connection connection;

    public TeacherDaoJDBC(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void insert(Teacher obj) {

        PreparedStatement statement;

        try {
            statement = connection.prepareStatement("insert into teacher (teacher_name, age, email) " +
                    "values (?,?,?)", Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, obj.getName());
            statement.setInt(2, obj.getAge());
            statement.setString(3, obj.getEmail());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void update(Teacher obj) {

        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement("update teacher " +
                    "set teacher_name = ?, age = ?, email = ? " +
                    "where id = ?");

            statement.setString(1, obj.getName());
            statement.setInt(2, obj.getAge());
            statement.setString(3, obj.getEmail());
            statement.setInt(4, obj.getId());

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

            statement = connection.prepareStatement("delete from teacher where id = ?");
            statement.setInt(1, id);

            int row = statement.executeUpdate();

            if (row == 0) {
                throw new DbException("Id non-existent on dataBase");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }

    }

    @Override
    public Teacher findById(Integer id) {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            statement = connection.prepareStatement("select teacher.*, teacher.id as teacher_id, teacher.teacher_name as teacher_name, " +
                    "teacher.age as teacher_age, teacher.email as teacher_email " +
                    "from teacher " +
                    "where id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Teacher teacher = instantiateTeacher(resultSet);
                return teacher;
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
    public List<Teacher> findAll() {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            statement = connection.prepareStatement("SELECT id as teacher_id, teacher_name, email as teacher_email, age as teacher_age " +
                    "FROM teacher");

            resultSet = statement.executeQuery();
            List<Teacher> teachers = new ArrayList<>();

            while (resultSet.next()) {

                Teacher teacher = instantiateTeacher(resultSet);
                teachers.add(teacher);


            }
            return teachers;
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);

        }

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


