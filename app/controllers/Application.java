package controllers;

import config.Config;
import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }

    public static void test(){
        render("/test.html");
    }
}