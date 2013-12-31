package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;
import play.data.validation.*;
import java.util.ArrayList;

public class UserArea extends Application {

    @Before
    static void checkAuthentification() {
        if(session.get("user") == null) Application.index();
    }

    public static void index() {
        User user = connected();
        render(user);
    }

    public static void  exercices(){
        User user = connected();

        if(user.isTeacher){

            //We render only the exercice which has flase as deleted atribute
            List<Exercice> created_exercices = Exercice.find(
                    "select e from Exercice e where e.deleted = false and e.createdBy like ?", user.email).fetch();

            render(user,created_exercices);
        }
        render(user);
    }

    public static void createExercice(String formulations){

        int f = 0;
        User user = connected();

        try{
            f = Integer.parseInt(formulations);

            if(f==0)
            {UserArea.exercices();}

            int i = 0;
            List<Integer> formuls = new ArrayList<Integer>();

            while (i < f){

                formuls.add(i);
                i++;

            }
            render(user, formuls, formulations);
        }
        catch (Exception e){
            UserArea.exercices();
        }
    }

    public static void saveExercice(){

        User user = connected();

        //validation rules
        validation.required(params.get("description"));
        validation.required(params.get("maxMistakes"));
        validation.required(params.get("maxTime"));

        try{

            Integer.parseInt(params.get("maxMistakes"));
            Integer.parseInt(params.get("maxTime"));

        }
        catch (Exception e){

            validation.equals(1,2);
        }

        String identifier = java.util.UUID.randomUUID().toString();
        int cnt = 0;

        List<Integer> formuls = new ArrayList<Integer>();
        //This while counts how many formulation the tutor wants in his exercice.
        while(params.get("solution"+cnt) != null){
            formuls.add(cnt);
            cnt++;

        }

        String [] formulation = new String[cnt];
        String [] solution = new String[cnt];

        int i = 0;

        //Put all formulations and solutions in an string array for be save in the database afterwards
        while(params.get("solution"+i) != null){

            //validation rules
            validation.required(params.get("formulation"+i));
            validation.required(params.get("solution")+i);

            formulation[i] = params.get("formulation"+i);
            solution[i] = params.get("solution"+i);
            i++;

        }

        Exercice exercice = new Exercice(identifier, params.get("maxTime"), params.get("maxMistakes"),
                formulation, solution, params.get("description"), user.email);


        // Handle errors
        if(validation.hasErrors()) {

            exercice.delete();
            System.out.println("Error de "+connected().fullname+" al guardar el ejercicio");
            render("@createExercice",user, formuls);
        }

        else{
            exercice.save();

            //Everytime someone creates an exercice, we put it in admin's list.
            User admin = User.find("byEmail", "demo_tutor").first();
            admin.addExercice("demo_tutor", identifier);

            exercices();
        }
    }

    public static void doExecice(String id){

    }

    public static void editExercice(Long id) {

        User user = connected();

        //finding the exercice...
        Exercice exercice = Exercice.findById(id);

        //...ordering the formulations and solutions...

        int i=0;
        boolean continu = true;

        List<String> formulations = new ArrayList<String>();
        List<String> solutions = new ArrayList<String>();
        while (continu){

            try{

                formulations.add(exercice.formulation[i]);
                solutions.add(exercice.solution[i]);
                i++;
            }

            catch (Exception e){

                continu = false;

            }
        }

        //...and render it
        render(user, exercice, formulations, solutions);

    }

    public static void deleteExercice(Long id){

        Exercice exercice = Exercice.findById(id);
        exercice.deleted = true;
        exercice.save();
        UserArea.exercices();
    }

    public static void studentList(){

        User user = connected();

        List<User> students = new ArrayList<User>();
        boolean continu = true;
        int i=0;

        while (continu) {

            try{
                User student = User.find("byEmail",user.list_students_email.get(i)).first();
                students.add(student);
            }
            catch (Exception e){
                continu = false;

            }
        }
    }
}














