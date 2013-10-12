import org.junit.*;

import java.util.*;

import play.test.*;
import models.*;

public class BasicTest extends UnitTest {
	
	 @Before
	    public void setup() {
	        Fixtures.deleteDatabase();
	    }
	 
    @Test
    public void aVeryImportantThingToTest() {
        assertEquals(2, 1 + 1);
    }
	
 @Test
 public void createAndRetrieveStudent() {
	    // Create a new student and save it
	    new Student("fa.sebban@gmail.com", "pass", "Fabian Sebban", "15/08/1991", "m", "3ESO").save();
	   	    
	    // Retrieve the student with e-mail address fa.sebban@gmail.com
	    Student fab = Student.find("byEmail", "fa.sebban@gmail.com").first();
	    
	    // Test 
	    assertNotNull(fab);
	    assertEquals("pass", fab.password);
	    assertEquals("Fabian Sebban", fab.fullname);
	    assertEquals("15/08/1991", fab.DateOfBirth);
	    assertEquals("m", fab.sex);
	    assertEquals("3ESO", fab.course);
	}
 
 
 @Test
 public void tryConnectAsStudent() {
     // Create a new user and save it
	    new Student("fa.sebban@gmail.com", "pass", "Fabian Sebban", "15/08/1991", "m", "3ESO").save();
     
     // Test 
     assertNotNull(Student.connect("fa.sebban@gmail.com", "pass"));
     assertNull(Student.connect("fa.sebban@gmail.com", "badpass"));
     assertNull(Student.connect("tom@gmail.com", "pass"));
 }

 @Test
 public void createAndRetrieveExercice() {
	    // Create a new exercice and save it
	 	String identifier = java.util.UUID.randomUUID().toString();
	 	String[] formulation = {"formulation1" , "formulation2", "formulation3"}; 
	 	String[] solution = {"solution1" , "solution2", "solution3"};
	    new Exercice(identifier, "300", "5", formulation, solution, "description").save(); 
	   	
	    
	    // Retrieve the exercice with the identifier
	    Exercice ex = Exercice.find("byIdentifier", identifier).first();
	    
	    // Test 
	    assertNotNull(ex);
	    assertEquals("300", ex.maxTime);
	    assertEquals("5", ex.maxMistakes);
	    assertEquals("formulation1", ex.formulation[0]);
	    assertEquals("solution1", ex.solution[0]);
	    assertEquals("formulation2", ex.formulation[1]);
	    assertEquals("solution2", ex.solution[1]);
	    assertEquals("formulation3", ex.formulation[2]);
	    assertEquals("solution3", ex.solution[2]);
	    assertEquals("description", ex.description);
	    
	}

@Test
public void AssociateExercicesToStudent(){
	
			//EXERCICE
			// Create new exercices and save them
		 	String identifier = java.util.UUID.randomUUID().toString();
		 	String[] formulation = {"formulation1" , "formulation2", "formulation3"}; 
		 	String[] solution = {"solution1" , "solution2", "solution3"};
		    new Exercice(identifier, "300", "5", formulation, solution, "description").save(); 
		    
		    String identifier1 = java.util.UUID.randomUUID().toString();
		    String[] formulation1 = {"formulation1.1" , "formulation1.2", "formulation1.3"}; 
		 	String[] solution1 = {"solution1.1" , "solution1.2", "solution1.3"};
		    new Exercice(identifier1, "354", "3", formulation1, solution1, "description1").save(); 

		    
		    //STUDENT
		    // Create a new student and save it
		    new Student("fa.sebban@gmail.com", "pass", "Fabian Sebban", "15/08/1991", "m", "3ESO").save();
	     
		    // Retrieve the student with e-mail address fa.sebban@gmail.com
		    Student fab = Student.find("byEmail", "fa.sebban@gmail.com").first();
		
		    assertNotNull(fab);
		    
		    fab.addExercice(fab.email, identifier);
		    fab.addExercice(fab.email, identifier1);

	
		    //Test
		    
		    assertNotNull(fab.exercices);
		    assertEquals("300", fab.exercices.get(0).maxTime);
			 

		    assertEquals("5", fab.exercices.get(0).maxMistakes);
		    assertEquals("formulation1", fab.exercices.get(0).formulation[0]);
		    assertEquals("solution1", fab.exercices.get(0).solution[0]);
		    assertEquals("formulation2", fab.exercices.get(0).formulation[1]);
		    assertEquals("solution2", fab.exercices.get(0).solution[1]);
		    assertEquals("formulation3", fab.exercices.get(0).formulation[2]);
		    assertEquals("solution3", fab.exercices.get(0).solution[2]);
		    assertEquals("description", fab.exercices.get(0).description);

		    
		    assertEquals("354", fab.exercices.get(1).maxTime);
		    assertEquals("3", fab.exercices.get(1).maxMistakes);
		    assertEquals("formulation1.1", fab.exercices.get(1).formulation[0]);
		    assertEquals("solution1.1", fab.exercices.get(1).solution[0]);
		    assertEquals("formulation1.2", fab.exercices.get(1).formulation[1]);
		    assertEquals("solution1.2", fab.exercices.get(1).solution[1]);
		    assertEquals("formulation1.3", fab.exercices.get(1).formulation[2]);
		    assertEquals("solution1.3", fab.exercices.get(1).solution[2]);
		    assertEquals("description1", fab.exercices.get(1).description);

		   
}

@Test
public void createAndRetrieveTeacher() {
	    // Create a new student and save it
	    new Teacher("fa.sebban@gmail.com", "pass", "Fabian Sebban", "15/08/1991", "m", "LFB", false).save();
	   	    
	    // Retrieve the teacher with e-mail address fa.sebban@gmail.com
	    Teacher fab = Teacher.find("byEmail", "fa.sebban@gmail.com").first();
	    
	    // Test 
	    assertNotNull(fab);
	    assertEquals("pass", fab.password);
	    assertEquals("Fabian Sebban", fab.fullname);
	    assertEquals("15/08/1991", fab.DateOfBirth);
	    assertEquals("m", fab.sex);
	    assertEquals("LFB", fab.workplace);
	    assertEquals(false, fab.isAdmin);

	}

@Test
public void tryConnectAsTeacher() {
    // Create a new user and save it
	    new Teacher("fa.sebban@gmail.com", "pass", "Fabian Sebban", "15/08/1991", "m", "LFB", false).save();
    
    // Test 
    assertNotNull(Teacher.connect("fa.sebban@gmail.com", "pass"));
    assertNull(Teacher.connect("fa.sebban@gmail.com", "badpass"));
    assertNull(Teacher.connect("tom@gmail.com", "pass"));
}

@Test 
public void AssciateStudentsToTeacher(){
	
	//TEACHER
	// Create a new teacher and save it
    new Teacher("fa.sebban@gmail.com", "pass", "Fabian Sebban", "15/08/1991", "m", "LFB", false).save();
   	    
    Teacher fab_t = Teacher.find("byEmail", "fa.sebban@gmail.com").first();
    assertEquals(1,Teacher.count());
    
    // Create an other teacher and save it
    new Teacher("teacher@gmail.com", "pass", "Teacher Teache", "15/08/1981", "f", "LFBCN", false).save();
   	    
    Teacher teacher_t = Teacher.find("byEmail", "teacher@gmail.com").first();
    assertEquals(2,Teacher.count());
    //STUDENT
    // Create a new student and save it
    new Student("fa.seb@gmail.com", "pass", "Fabian Sebban", "15/08/1990", "m", "3ESO").save();
	
    Student fab_s = Student.find("byEmail", "fa.seb@gmail.com").first();
    assertEquals(1,Student.count());
    
    // Create an other student and save it
    new Student("jon@gmail.com", "pass", "Jon Lopez", "12/12/1994", "m", "4ESO").save();
	
    Student jon_s = Student.find("byEmail", "jon@gmail.com").first();
    assertEquals(2,Student.count());
    
    // Asociating teachers to the students
    fab_s.addTeacher(fab_s.email, fab_t.email);
    jon_s.addTeacher(jon_s.email, fab_t.email);
    
    fab_s.addTeacher(fab_s.email, teacher_t.email);
    jon_s.addTeacher(jon_s.email, teacher_t.email);

    // Test
    
    assertNotNull(fab_s.teachers);
    assertEquals(2,fab_s.teachers.size());
    assertEquals(2,jon_s.teachers.size());
    
    assertEquals("fa.sebban@gmail.com",fab_s.teachers.get(0).email);
    assertEquals("pass",fab_s.teachers.get(0).password); 
    assertEquals("Fabian Sebban", fab_s.teachers.get(0).fullname);
    assertEquals("15/08/1991", fab_s.teachers.get(0).DateOfBirth);
    assertEquals("m", fab_s.teachers.get(0).sex);
    assertEquals("LFB", fab_s.teachers.get(0).workplace);
        
    assertEquals("teacher@gmail.com",fab_s.teachers.get(1).email);
    assertEquals("pass",fab_s.teachers.get(1).password); 
    assertEquals("Teacher Teache", fab_s.teachers.get(1).fullname);
    assertEquals("15/08/1981", fab_s.teachers.get(1).DateOfBirth);
    assertEquals("f", fab_s.teachers.get(1).sex);
    assertEquals("LFBCN", fab_s.teachers.get(1).workplace);
   
    assertEquals("fa.sebban@gmail.com",jon_s.teachers.get(0).email);
    assertEquals("pass",jon_s.teachers.get(0).password); 
    assertEquals("Fabian Sebban", jon_s.teachers.get(0).fullname);
    assertEquals("15/08/1991", jon_s.teachers.get(0).DateOfBirth);
    assertEquals("m", jon_s.teachers.get(0).sex);
    assertEquals("LFB", jon_s.teachers.get(0).workplace);
    
    assertEquals("teacher@gmail.com",jon_s.teachers.get(1).email);
    assertEquals("pass",jon_s.teachers.get(1).password); 
    assertEquals("Teacher Teache", jon_s.teachers.get(1).fullname);
    assertEquals("15/08/1981", jon_s.teachers.get(1).DateOfBirth);
    assertEquals("f", jon_s.teachers.get(1).sex);
    assertEquals("LFBCN", jon_s.teachers.get(1).workplace);
    
	}

@Test
public void createAndRetrieveTutor() {
	    // Create a new tutor and save it
	    new Tutor("tutor@mail.com","password","Marc Gonzalez", "15/08/1991", "m").save() ;

	   	    
	    // Retrieve the teacher with e-mail address fa.sebban@gmail.com
	    Tutor marc = Tutor.find("byEmail", "tutor@mail.com").first();
	    
	    // Test 
	    assertNotNull(marc);
	    assertEquals("password", marc.password);
	    assertEquals("Marc Gonzalez", marc.fullname);
	    assertEquals("15/08/1991", marc.DateOfBirth);
	    assertEquals("m", marc.sex);
	}

@Test
public void tryConnectAsTutor() {
	    // Create a new tutor and save it
	    new Tutor("tutor@mail.com","password","Marc Gonzalez", "15/08/1991", "m").save() ;
	    
	    // Test 
	    assertNotNull(Tutor.connect("tutor@mail.com", "password"));
	    assertNull(Tutor.connect("fa.sebban@gmail.com", "badpass"));
	    assertNull(Tutor.connect("tom@gmail.com", "pass"));
	}

@Test 
public void AssciateStudentsToTutor(){
	
		 //TUTOR
		 // Create a new tutor and save it
	    new Tutor("tutor@mail.com","password","Marc Gonzalez", "15/08/1991", "m").save() ;
	   
	    Tutor marc_t = Tutor.find("byEmail", "tutor@mail.com").first();
	    assertEquals(1,Tutor.count());
	    
		 // Create an other tutor and save it
	    new Tutor("tutor1@mail.com","password1","Laura Lopez", "14/07/1990", "w").save() ;
	   
	    Tutor laura_t = Tutor.find("byEmail", "tutor1@mail.com").first();
	    assertEquals(2,Tutor.count());
	    
	    
	    //STUDENT
	    // Create a new student and save it
	    new Student("fa.seb@gmail.com", "pass", "Fabian Sebban", "15/08/1990", "m", "3ESO").save();
		
	    Student fab_s = Student.find("byEmail", "fa.seb@gmail.com").first();
	    assertEquals(1,Student.count());
	    
	    // Create an other student and save it
	    new Student("jon@gmail.com", "pass", "Jon Lopez", "12/12/1994", "m", "4ESO").save();
		
	    Student jon_s = Student.find("byEmail", "jon@gmail.com").first();
	    assertEquals(2,Student.count());
	    
	    // Asociate the two students with Marc (tutor)
	    fab_s.addTutor(fab_s.email,marc_t.email);
	    jon_s.addTutor(jon_s.email,laura_t.email);
	   
	    // Asociate one student with Laura (tutor) 
	    fab_s.addTutor(fab_s.email,laura_t.email);
	    
	    // Test
	     
	    //"tutor1@mail.com","password1","Laura Lopez", "14/07/1990", "w"
	    
	    assertNotNull(fab_s.tutors);
	    assertNotNull(jon_s.tutors);	
	    
	    assertEquals("tutor@mail.com", fab_s.tutors.get(0).email);
	    assertEquals("password", fab_s.tutors.get(0).password);
	    assertEquals("Marc Gonzalez", fab_s.tutors.get(0).fullname);
	    assertEquals("15/08/1991", fab_s.tutors.get(0).DateOfBirth);
	    assertEquals("m", fab_s.tutors.get(0).sex);
	    
	    assertEquals("tutor1@mail.com", fab_s.tutors.get(1).email);
	    assertEquals("password1", fab_s.tutors.get(1).password);
	    assertEquals("Laura Lopez", fab_s.tutors.get(1).fullname);
	    assertEquals("14/07/1990", fab_s.tutors.get(1).DateOfBirth);
	    assertEquals("w", fab_s.tutors.get(1).sex);
	   
	    assertEquals("tutor1@mail.com", jon_s.tutors.get(0).email);
	    assertEquals("password1", jon_s.tutors.get(0).password);
	    assertEquals("Laura Lopez", jon_s.tutors.get(0).fullname);
	    assertEquals("14/07/1990", jon_s.tutors.get(0).DateOfBirth);
	    assertEquals("w", jon_s.tutors.get(0).sex);
	    
	}
@Test 
public void YmlTest(){
	
	 Fixtures.load("dataschool.yml");
		
     List<Student> student = Student.findAll();
     assertEquals(3, student.size());
     assertEquals("fa.sebban@gmail.com", student.get(0).email);
     assertEquals("pass", student.get(0).password);
     
    
	
	}
}

