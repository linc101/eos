package controllers;

import General.Result;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.mvc.Controller;
import util.PageOffset;

import java.io.IOException;
import java.util.List;

/**
 * Created by yehuizhang on 14/10/24.
 */
public class WrapRender extends Controller{
    private static final Logger log = LoggerFactory.getLogger(WrapRender.class);

    protected static void RenderFailed(String message){
        Result res = new Result(message);
        renderJSON(wrapObject(res));
    }

    protected static void RenderSuccess(){
        Result res = new Result();
        renderJSON(wrapObject(res));
    }

    protected static void RenderSuccess(Object obj){
        Result<Object> res = new Result<Object>(obj);
        renderJSON(wrapObject(res));
    }

    protected static void RenderSuccess(List list, int count, PageOffset offset){
        Result<List> res = new Result<List>(list,count,offset);
        renderJSON(wrapObject(res));
    }

    protected static void RenderSuccess(List<Object> listObj){
        Result<List<Object>> res = new Result<List<Object>>();
        renderJSON(wrapObject(res));
    }


    protected static String wrapObject(Object obj){
        ObjectMapper mapper = new ObjectMapper();
        try{
            String s = mapper.writeValueAsString(obj);
            return s;
        } catch(IOException e){
            log.error(e.getMessage(), e);
            return null;
        }
    }


}
