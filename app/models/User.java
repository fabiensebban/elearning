package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Entity
public class User extends Model{

    @Required
    @Email
    public String email;
    @Required
    public String password;
    @Required
    public String DateOfBirth;
    @Required
    public String fullname;
    @Required
    @MaxSize(1)
    public String sex;
    @Required
    public Date DateOfRegistration;

    //Set the type of user
    @Required
    public boolean isStudent;
    @Required
    public boolean isTeacher;
    @Required
    public boolean isTutor;

    //Info about teachers
    public String workplace;

    //Info about students
    public String course;

    @Column
    @ElementCollection(targetClass=String.class)
    public List<String> list_students_email;
    @Column
    @ElementCollection(targetClass=String.class)
    public List<String> list_tutors_email;
    @Column
    @ElementCollection(targetClass=String.class)
    public List<String> list_teachers_email;

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
        this.list_students_email = new ArrayList<String>();
        this.list_tutors_email = new ArrayList<String>();
        this.list_teachers_email = new ArrayList<String>();
        this.workplace = null;
        this.course = null;

    }

    public static User connect(String email, String password) {
        return find("byEmailAndPassword", email, password).first();
    }

    public User assignStudentToTeacher(String email_student, String email_teacher){

        User teacher = User.find("byEmail", email_teacher).first();
        User student = User.find("byEmail", email_student).first();
        teacher.list_students_email.add(email_student);
        teacher.save();
        student.list_teachers_email.add(email_teacher);
        student.save();
        return (teacher); //need to be changed when i'll implement sessions.
    }

    public User assignStudentToTutor(String email_student, String email_tutor){

        User tutor = User.find("byEmail", email_tutor).first();
        User student = User.find("byEmail", email_student).first();
        tutor.list_students_email.add(email_student);
        tutor.save();
        student.list_tutors_email.add(email_tutor);
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
