package controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import models.Exercice;
import models.User;
import play.mvc.*;
import play.data.validation.*;

import models.*;


//This is the controller for the Android app
public class Android extends Controller {

    public static void login(String email, String password) {
        System.out.println("Login controller, username: " + email + ", password: " + password);
        User user = User.find("byEmailAndPassword",email, password).first();

        if(user!=null){
            session.put("user",user.email);
            if(user.isStudent){
                renderText("okStudent");
            }
            else if (user.isTeacher || user.isTutor){
                renderText("okTutorTeacher");
            }
        }
        else{
            renderText("error");
        }
    }

    public static void exercicesList(String email_student){

        if(email_student != null){
            User student = User.find("byEmail", email_student).first();
            System.out.println("exercicesList controller, student_email: " + email_student);
            System.out.println("exercicesList controller, exercice 1 (desc): " + student.exercices.get(0).description);

            List<Exercice> exercices = new ArrayList<Exercice>();
            int i=0;

            while (i<student.exercices.size()){

                exercices.add(student.exercices.get(i));
                i++;

            }

            renderXml(exercices);
        }

        else {
            System.out.println("exercicesList controller, student_email is defenitly null");
        }
    }

    public static void test(){

        User student = User.find("byEmail", "demo_student").first();
        List<Exercice> exercices = new ArrayList<Exercice>();
        int i=0;

        while (i<student.exercices.size()){

            exercices.add(student.exercices.get(i));
            i++;
            System.out.println("valor de i " + i);

        }


        renderXml(exercices);
    }

}
