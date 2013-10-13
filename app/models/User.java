package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;

@Entity
public class User extends Model{

    public String email;
    public String password;
    public String DateOfBirth;
    public String fullname;
    public String sex;
    public Date DateOfRegistration;

    //Set the type of user
    public boolean isStudent;
    public boolean isTeacher;
    public boolean isTutor;


    public String[] list_students_email;
    public String[] list_tutors_email;
    public String[] list_teachers_email;

    @ManyToMany
    public List<Exercice> exercices;

    public User(String email, String password, String fullname, String DateOfBirth, String sex, Boolean isStudent , Boolean isTeacher, Boolean isTutor) {

        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.DateOfBirth = DateOfBirth;
        this.sex = sex;
        Date d = new Date();
        this.DateOfRegistration = d;
        this.exercices = new ArrayList<Exercice>();
        this.isStudent = isStudent;
        this.isTeacher = isTeacher;
        this.isTutor = isTutor;
        this.list_students_email = new String[500];
        this.list_tutors_email = new String[500];
        this.list_teachers_email = new String[500];

    }

    public static User connect(String email, String password) {
        return find("byEmailAndPassword", email, password).first();
    }

    public User assignStudentToTeacher(String email_student, String email_teacher){

        User teacher = User.find("byEmail", email_teacher).first();
        User student = User.find("byEmail", email_student).first();
        teacher.list_students_email[teacher.list_students_email.length] = email_student;
        teacher.save();
        student.list_teachers_email[teacher.list_teachers_email.length] = email_teacher;
        student.save();
        return (teacher); //need to be changed when i'll implement sessions.
    }

    public User assignStudentToTutor(String email_student, String email_tutor){

        User tutor = User.find("byEmail", email_tutor).first();
        User student = User.find("byEmail", email_student).first();
        tutor.list_students_email[tutor.list_students_email.length] = email_student;
        tutor.save();
        student.list_tutors_email[tutor.list_tutors_email.length] = email_tutor;
        student.save();
        return (tutor); //need to be changed when i'll implement sessions.
    }


    public User addExercice(String email_student, String identifier){

        User student = User.find("byEmail", email_student).first();
        Exercice ex = Exercice.find("byIdentifier", identifier).first();
        student.exercices.add(ex);
        student.save();
        return student;

    }
}
