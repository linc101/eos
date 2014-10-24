package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static controllers.WrapRender.*;

/**
 * Created by yehuizhang on 14/10/23.
 */
public class WeiBo extends CommonRender{
    private static Logger log = LoggerFactory.getLogger(WeiBo.class);

    public static void index(){
        render("/weibo/index.html");
    }


}
