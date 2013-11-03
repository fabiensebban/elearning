package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import javax.persistence.*;
import models.*;
import play.data.validation.*;


public class Register extends Controller{

    public static void index(){
        render();
    }

    public static void saveUser(@Required String fullname,
                                @Required @Email String email,
                                @Required String password,
                                @Required @Equals("password") String confirm_password,
                                @Required String DateOfBirth,
                                @Required String sex,
                                @Required String typeOfUser){

        // Handle errors
        if(validation.hasErrors()) {
            System.out.println("ERROR");
            render("@index");
        }

        else{
            boolean student;
            boolean teacher;
            boolean tutor;

            if(typeOfUser.equals("student")){
                 student = true;
            }
            else{
                student = false;
            }

            if(typeOfUser.equals("teacher")){
                teacher = true;
            }
            else{
                teacher = false;
            }

            if(typeOfUser.equals("tutor")){
                tutor = true;
            }
            else{
                tutor = false;
            }

            User user = User.find("byEmail",email).first();

            if(user == null)
            {
                System.out.println("Se ha registrado "+ email);

                new User(email, password, fullname, DateOfBirth, sex, student , teacher, tutor).save();

                User registrado = User.find("byEmail", email).first();
                /*
                System.out.println("Su email "+ registrado.email);
                System.out.println("Su password "+ registrado.password);
                System.out.println("Su fullname "+ registrado.fullname);
                System.out.println("Su DateOfBirth "+ registrado.DateOfBirth);
                System.out.println("Su sex "+ registrado.sex);
                System.out.println("Su student "+ registrado.isStudent);
                System.out.println("Su teacher "+ registrado.isTeacher);
                System.out.println("Su tutor "+ registrado.isTutor);    */
                Application.index();
            }

            else
            {
                System.out.println("Este nombre de usuario ya esta utilizado");
                render();
            }
        }
    }
}
