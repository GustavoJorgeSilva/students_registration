package com.example.studentsregistrations;

import com.example.studentsregistrations.DB.DB;
import com.example.studentsregistrations.DB.DbException;
import com.example.studentsregistrations.controller.DaoFactory;
import com.example.studentsregistrations.model.entities.*;
import com.example.studentsregistrations.model.entities.dao.CourseDao;
import com.example.studentsregistrations.model.entities.dao.StudentDao;
import com.example.studentsregistrations.model.entities.dao.TeacherDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class StudentsregistrationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentsregistrationsApplication.class, args);


        Scanner leitor = new Scanner(System.in);
        StudentDao studentDao = DaoFactory.createStudentDao();
        CourseDao courseDao = DaoFactory.createCourseDao();
        TeacherDao teacherDao = DaoFactory.createTeacherDao();

        boolean init = false;
        while (!init) {
            try {

                System.out.println("Hello, welcome to our registration system");
                System.out.println("1- to register.");
                System.out.println("2- for consultation.");
                System.out.println("3- to exit");
                int choice = leitor.nextInt();


                boolean aux = false;
                while (!aux) {
                    try {
                        switch (choice) {

                            case 1:
                                System.out.println("1- To register a teacher");
                                System.out.println("2- To register a course");
                                System.out.println("3- To register a student");
                                System.out.println("4- to return to the previous menu");
                                int choice1 = leitor.nextInt();


                                if (choice1 == 1) {
                                    System.out.print("What is the name of the teacher to be registered? : ");
                                    leitor.nextLine();
                                    String nameTeacher = leitor.nextLine();
                                    System.out.print("Age: ");
                                    int ageTeacher = leitor.nextInt();
                                    leitor.nextLine();
                                    System.out.print("Email: ");
                                    String emailTeacher = leitor.nextLine();

                                    Teacher teacher = new Teacher(nameTeacher, ageTeacher, emailTeacher);
                                    teacherDao.insert(teacher);
                                    System.out.println("Done! The teacher was registered with the ID: " + teacher.getId());


                                }

                                if (choice1 == 2) {
                                    System.out.print("What is the name of the course to be registered?: ");
                                    leitor.nextLine();
                                    String nameCourse = leitor.nextLine();
                                    System.out.print("Enter the id of the teacher responsible for the course: ");
                                    int idTeacherCourse = leitor.nextInt();
                                    leitor.nextLine();

                                    Teacher teacher = teacherDao.findById(idTeacherCourse);
                                    Course course = new Course(nameCourse, teacher);
                                    courseDao.insert(course);
                                    System.out.println("Done! The course was registered with the ID: " + course.getId());


                                }

                                if (choice1 == 3) {
                                    System.out.print("What is the name of the student to be registered?: ");
                                    leitor.nextLine();
                                    String nameStudent = leitor.nextLine();
                                    System.out.print("Age: ");
                                    int ageStudent = leitor.nextInt();
                                    leitor.nextLine();
                                    System.out.print("Email: ");
                                    String emailStudent = leitor.nextLine();


                                    System.out.print("Is the student already enrolled in any course? Y/N :");
                                    String enrolledCourse = leitor.nextLine().toLowerCase();

                                    if (enrolledCourse.equals("y")) {
                                        System.out.print("What is the id of the course in which the student is registered? :");
                                        int enrolledCourseId = leitor.nextInt();
                                        leitor.nextLine();

                                        Course course = courseDao.findById(enrolledCourseId);
                                        Student student = new Student(nameStudent, emailStudent, ageStudent, course);
                                        studentDao.insert(student);
                                        System.out.println("Done! The student was registered with the ID: " + student.getId());

                                    }

                                    if (enrolledCourse.equals("n")) {
                                        Student student = new Student(nameStudent, emailStudent, ageStudent);
                                        studentDao.insert(student);
                                        System.out.println("Done! The student was registered with the ID: " + student.getId());
                                    }


                                }

                                if (choice1 == 4) {
                                    aux = true;
                                }

                                break;

                            case 2:

                                System.out.println("1- To consult a teacher");
                                System.out.println("2- To consult a course");
                                System.out.println("3- To consult a student");
                                System.out.println("4- to return to the previous menu");
                                int choice2 = leitor.nextInt();

                                if (choice2 == 1) {

                                    List<Teacher> teachers = new ArrayList<>();
                                    teachers = teacherDao.findAll();
                                    for (Teacher t : teachers) {
                                        System.out.println(t);
                                    }

                                }

                                if (choice2 == 2) {

                                    List<Course> courses = new ArrayList<>();
                                    courses = courseDao.findAll();
                                    for (Course c : courses) {
                                        System.out.println(c);
                                    }
                                }

                                if (choice2 == 3) {

                                    List<Student> students = new ArrayList<>();
                                    students = studentDao.findAll();
                                    for (Student s : students) {
                                        System.out.println(s);
                                    }

                                }

                                if (choice2 == 4) {
                                    aux = true;
                                }
                                break;

                            case 3:
                                aux = true;
                                init = true;

                                break;

                        }
                    } catch (InputMismatchException e) {
                        System.out.println("An error has occurred, check the input by choosing the desired option!");
                        leitor.nextLine();
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("An error has occurred, check the input by choosing the desired option!");
                leitor.nextLine();
            }
        }
    }
}
