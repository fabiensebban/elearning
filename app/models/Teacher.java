package models;

import java.util.*;

import javax.persistence.*; 
import play.db.jpa.*;

@Entity	 
public class Teacher extends Model{


	    public String email;
	    public String password;
	    public String DateOfBirth;
	    public String fullname;
	    public String sex;
	    public String workplace;
	    public Date DateOfRegistration;
	    public Boolean isAdmin;
	    
	    @ManyToMany
	    public List<Student> students;
	    
	    
	    public Teacher(String email, String password, String fullname, String DateOfBirth, String sex, String workplace, Boolean isAdmin) {
	        
	    	this.email = email;
	        this.password = password;
	        this.fullname = fullname;
	        this.DateOfBirth = DateOfBirth;
	        this.sex = sex;
	        this.workplace = workplace;
	        Date d = new Date();
	        this.DateOfRegistration = d;	    
	        this.isAdmin = isAdmin;
	        this.students = new ArrayList<Student>();
	    }
	    
	    public static Teacher connect(String email, String password) {
	        return find("byEmailAndPassword", email, password).first();
	    }
	    /*
	    public Teacher addStudents(String email_teacher, String email_student){
	    	
	    	Teacher teacher = find("byEmail", email).first();
	    	Student student = find("byEmail", email).first();
	    	teacher.students.add(student);
	    	teacher.save();
	    	return teacher; 
	    }*/
}
