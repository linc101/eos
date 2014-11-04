package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by yehuizhang on 14/11/4.
 */
public class Pic extends CommonRender {
    private static final Logger log = LoggerFactory.getLogger(Pic.class);

    public static void index(){
        render("/picture/picturemain.html");
    }

}
