package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;

public class UserArea extends Application {

    @Before
    static void checkAuthentification() {
        if(session.get("user") == null) Application.index();
    }

    public static void index() {
        render();
    }

    public static void  exercices(){

        render(connected());
    }

}
