package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {


    public static void index() {
        render();
    }

    public static void login(String email, String password){

        User user = User.find("byEmailAndPassword",email, password).first();

        if(user == null)
        {
            System.out.println("Usuario no registrado");
            System.out.println("valor de username: "+email);
            System.out.println("valor de password: "+password);
            index();
        }

        else{

            System.out.println("Se acaba de logear "+email);
            session.put("user",user.email);
            UserArea.index();

        }
    }

    public static void register(){
        Register.index();
    }

    static User connected() {
        String email = session.get("user");
        if(email != null) {
            System.out.println("valor de email" + email);
            return User.find("byEmail", email).first();

        }
        return null;
    }

    public static void logOut(){
        session.remove("user");
        index();
    }

}