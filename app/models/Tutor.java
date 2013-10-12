package models;

import java.util.*;
import javax.persistence.*; 
import play.db.jpa.*;

@Entity	
public class Tutor extends Model{

    public String email;
    public String password;
    public String DateOfBirth;
    public String fullname;
    public String sex;
    public Date DateOfRegistration;
    
    @ManyToMany
    public List<Student> students;

    
    public Tutor(String email, String password, String fullname, String DateOfBirth, String sex) {
        
    	this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.DateOfBirth = DateOfBirth;
        this.sex = sex;
        Date d = new Date();
        this.DateOfRegistration = d;	    
        this.students = new ArrayList<Student>();

    }
    
    public static Tutor connect(String email, String password) {
        return find("byEmailAndPassword", email, password).first();
    }
    
    public Tutor addStudent(String email_tutor, String email_student){
    	
    	Tutor tutor = Tutor.find("byEmail", email_tutor).first();
    	Student student = find("byEmail", email_student).first();
    	tutor.students.add(student);
    	tutor.save();
    	return tutor; 
    	
    }

}
