package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;
import play.data.validation.*;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import org.joda.time.*;
import play.templates.JavaExtensions;
import javax.xml.datatype.Duration;

public class UserArea extends Application {

    @Before
    static void checkAuthentification() {
        if (session.get("user") == null) Application.index();
    }

    public static void index() {
        User user = connected();
        render(user);
    }

    public static void exercices() {
        User user = connected();

        if (user.isTeacher) {

            //We render only the exercice which has flase as deleted atribute
            List<Exercice> created_exercices = Exercice.find(
                    "select e from Exercice e where e.deleted = false and e.copied = false and e.createdBy like ?", user.email).fetch();
            List<User> students = new ArrayList<User>();
            int i = 0;
            while (i < user.list_students_email.size()) {

                String studentEmail = user.list_students_email.get(i);
                User student = User.find("byEmail", studentEmail).first();
                students.add(student);
                i++;

            }

            render(user, created_exercices, students);
        }

        int i = 0 ;
        List<Exercice> exercices = new ArrayList<Exercice>();
        while(i<user.exercices.size()){

            if( user.exercices.get(i).done == false || user.exercices.get(i).deleted == false)
            {
                exercices.add(user.exercices.get(i));
            }
            i++;
            System.out.println("He pasado or aqui veces: " + user.exercices.size());
        }

        render(user, exercices);
    }

    public static void createExercice(String formulations) {

        int f = 0;
        User user = connected();

        try {
            f = Integer.parseInt(formulations);

            if (f == 0) {
                UserArea.exercices();
            }

            int i = 0;
            List<Integer> formuls = new ArrayList<Integer>();

            while (i < f) {

                formuls.add(i);
                i++;

            }
            render(user, formuls, formulations);
        } catch (Exception e) {
            UserArea.exercices();
        }
    }

    public static void saveExercice() {

        User user = connected();

        //validation rules
        validation.required(params.get("description"));
        validation.required(params.get("maxMistakes"));
        validation.required(params.get("maxTime"));

        try {

            Integer.parseInt(params.get("maxMistakes"));
            Integer.parseInt(params.get("maxTime"));

        } catch (Exception e) {

            validation.equals(1, 2);
        }

        String identifier = java.util.UUID.randomUUID().toString();
        int cnt = 0;

        List<Integer> formuls = new ArrayList<Integer>();
        //This while counts how many formulation the tutor wants in his exercice.
        while (params.get("solution" + cnt) != null) {
            formuls.add(cnt);
            cnt++;

        }

        String[] formulation = new String[cnt];
        String[] solution = new String[cnt];

        int i = 0;

        //Put all formulations and solutions in an string array for be save in the database afterwards
        while (params.get("solution" + i) != null) {

            //validation rules
            validation.required(params.get("formulation" + i));
            validation.required(params.get("solution") + i);

            formulation[i] = params.get("formulation" + i);
            solution[i] = params.get("solution" + i);
            i++;

        }

        Exercice exercice = new Exercice(identifier, params.get("maxTime"), params.get("maxMistakes"),
                formulation, solution, params.get("description"), user.email);


        // Handle errors
        if (validation.hasErrors()) {

            exercice.delete();
            System.out.println("Error de " + connected().fullname + " al guardar el ejercicio");
            render("@createExercice", user, formuls);
        } else {
            exercice.save();

            //Everytime someone creates an exercice, we put it in admin's list.
            User admin = User.find("byEmail", "admin").first();
            admin.addExercice("admin", identifier);

            exercices();
        }
    }

    public static void doExercice(Long id) {

        User user = connected();
        Exercice exercice = Exercice.findById(id);
        DateTime startTime = new DateTime();

        List<String> formulations = new ArrayList<String>();
        int i = 0;

        while (i < exercice.formulation.length){

            formulations.add(exercice.formulation[i]);
            i++;

        }

        render(user,exercice, formulations, startTime);

    }

    public static void editExercice(Long id) {

        User user = connected();

        //finding the exercice...
        Exercice exercice = Exercice.findById(id);

        //...ordering the formulations and solutions...

        int i = 0;
        boolean continu = true;

        List<String> formulations = new ArrayList<String>();
        List<String> solutions = new ArrayList<String>();
        while (continu) {

            try {

                formulations.add(exercice.formulation[i]);
                solutions.add(exercice.solution[i]);
                i++;
            } catch (Exception e) {

                continu = false;

            }
        }

        //...and render it
        render(user, exercice, formulations, solutions);

    }

    public static void deleteExercice(Long id) {

        Exercice exercice = Exercice.findById(id);
        exercice.deleted = true;
        exercice.save();
        UserArea.exercices();
    }

    public static void studentList() {

        User user = connected();

        List<User> students = new ArrayList<User>();
        int i = 0;

        while (i < user.list_students_email.size()) {

            User student = User.find("byEmail", user.list_students_email.get(i)).first();
            students.add(student);
            i++;

        }

        render(user, students);

    }

    public static void addStudent(String studentEmail) {

        if(connected().isTeacher){
        connected().assignStudentToTeacher(studentEmail, connected().email);
        }
        else{
            connected().assignStudentToTutor(studentEmail, connected().email);
        }
        UserArea.studentList();
    }

    public static void assignExerciceToStudent(Long idExercice, List<String> selectedStudents) {

        int i = 0;

        while (i < selectedStudents.size()) {

            User student = User.find("byEmail", selectedStudents.get(i)).first();
            Exercice exercice = Exercice.findById(idExercice);

            //we copie the exercice for asssign it to a student.
            Exercice copied_exercice = new Exercice(exercice.identifier, exercice.maxTime, exercice.maxMistakes,
                    exercice.formulation, exercice.solution, exercice.description, exercice.createdBy);
            copied_exercice.copied = true;
            copied_exercice.save();
            student.addExercice(student.email, copied_exercice.identifier);
            student.save();
            i++;
        }

        UserArea.exercices();
    }

    public static void assignedExercices(Long user_id){

        int i=0;
        User user = connected();
        User student = User.findById(user_id);
        List<Exercice> exercices = new ArrayList<Exercice>();

        while (i<student.exercices.size()){

            if(!student.exercices.get(i).deleted){
                exercices.add(student.exercices.get(i));
            }
            i++;

        }

        render(user, exercices);

    }

    public static void validateExercice(String startTime){

        Long exerciceId = Long.parseLong(params.get("exerciceId"));
        Exercice exercice = Exercice.findById(exerciceId);
        DateTime et = new DateTime();
        DateTime st = new DateTime(startTime);
        Period period = new Period(st, et);

        int mistakes = 0;
        int i = 0;

        //Check how many mistakes the student did.
        while(i < exercice.formulation.length){
            if(!params.get("solution"+i).equalsIgnoreCase(exercice.solution[i])){
                mistakes++;
            }
            i++;
        }


        exercice.time = ""+period.getMinutes()+"";

        exercice.mistakes = ""+mistakes+"";

        //We consider that an exercice is done only if complish the specification the teacher set previously
        if(Integer.parseInt(exercice.time) <= Integer.parseInt(exercice.maxTime) && Integer.parseInt(exercice.mistakes) <= Integer.parseInt(exercice.maxMistakes)){
            exercice.done = true;
        }

        exercice.save();
        UserArea.exercices();
    }
}














