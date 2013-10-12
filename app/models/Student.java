package models;

import java.util.*;

import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Student extends Model {

    public String email;
    public String password;
    public String DateOfBirth;
    public String fullname;
    public String sex;
    public String course;
    public Date DateOfRegistration;
    
    @ManyToMany
    public List<Exercice> exercices;
    
    @ManyToMany
    public List <Tutor> tutors;
    
    @ManyToMany
    public List<Teacher> teachers; 
    
    public Student(String email, String password, String fullname, String DateOfBirth, String sex, String course) {
        
    	this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.DateOfBirth = DateOfBirth;
        this.sex = sex;
        this.course = course;
        Date d = new Date();
        this.DateOfRegistration = d;
        this.exercices = new ArrayList<Exercice>();
        this.tutors = new ArrayList<Tutor>();
        this.teachers = new ArrayList<Teacher>();
        
        
    }
    

 public Student addExercice(String email, String identifier){
    	
    	Student student = Student.find("byEmail", email).first();
	    Exercice ex = Exercice.find("byIdentifier", identifier).first();
    	student.exercices.add(ex);
    	student.save();
    	return student; 
    	
    }
     
 public Student addTeacher(String email_student, String email_teacher){
    	
    	Student student = Student.find("byEmail", email_student).first();
    	Teacher teacher = Teacher.find("byEmail", email_teacher).first();
    	student.teachers.add(teacher);
    	student.save();
    	return student; 
    	
    }
 
 public Student addTutor(String email_student, String email_tutor){
 	
 	Student student = Student.find("byEmail", email_student).first();
 	Tutor tutor = Tutor.find("byEmail", email_tutor).first();
 	student.tutors.add(tutor);
 	student.save();
 	return student; 
 	
 }
    public static Student connect(String email, String password) {
        return find("byEmailAndPassword", email, password).first();
    }
	

}
