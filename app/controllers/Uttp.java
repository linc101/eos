package controllers;

import models.Experience;

import java.util.ArrayList;
import java.util.List;

import static controllers.WrapRender.*;

/**
 * Created by uttp on 14-11-6.
 */
public class Uttp extends CommonRender{
    public static void index(){
        render("/uttp/index.html");
    }

    public static void findAllMyExp(){
        List<Experience> allMyExps = Experience.findExpByUsername();

        if(allMyExps == null){
            RenderFailed("数据库出错！");
        }else{
            RenderSuccess(allMyExps);
        }

    }
}
