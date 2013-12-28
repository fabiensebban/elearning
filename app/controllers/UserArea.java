package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;
import play.data.validation.*;

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
        render(user);
    }

    public static void createExercice(String formulations){

        int f = 0;
        User user = connected();

        try{
            f = Integer.parseInt(formulations);

            int i = 0;
            List<Integer> formuls = new ArrayList<Integer>();

            while (i < f){

                formuls.add(i);
                i++;

            }
            render(user, formuls);
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

        Exercice exercice = new Exercice(identifier, params.get("maxTime"), params.get("maxMistakes"),
                formulation, solution, params.get("description"), user.email);

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

        // Handle errors
        if(validation.hasErrors()) {

            exercice.deleteAll();
            System.out.println("exercice " +exercice.description);
            //UserArea.createExercice(""+cnt+"");
            render("@createExercice",user, formuls);
        }

        else{
            exercice.save();
            exercices();
        }
    }
}














