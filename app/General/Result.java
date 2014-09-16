package General;
import net.sf.json.JSONArray;  
import net.sf.json.JSONObject;
import util.PageOffset;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yehuizhang on 14-9-1.
 */
public class Result<T>  implements Serializable{
    private boolean success;

    private String message;

    private int pn;

    private int ps;

    private int count;

    private int pnCount;

    private T res;

    public Result(T res){
        this.success = true;
        this.res = res;
    }

    public Result(List list, int count, PageOffset offset){
        this.success = true;
        this.count = count;
        this.pn = offset.getPn();
        this.ps = offset.getPs();
        this.pnCount = (count + ps -1)/ps;
        this.res = (T)list;
    }

    public Result(String message){
        this.message = message;
        this.success = false;
    }

    public int getPn(){
        return this.pn;
    }

    public void setPn(int pn){
        this.pn = pn;
    }

    public void setPs(int ps){
        this.ps = ps;
    }

    public int getPs(){
        return this.ps;
    }

    public int getCount(){
        return this.count;
    }

    public void setCount(int count){
        this.count = count;
    }

    public int getPnCount(){
        return this.pnCount;
    }

    public void setPnCount(int pnCount){
        this.pnCount = pnCount;
    }

    public Result(){
        this.success = true;
    }
    
    public boolean getSuccess(){
    	return success;
    }
    
    public void setSuccess(boolean success){
    	this.success = success;
    }
    
    public void setMessage(String message){
    	this.message = message;
    }
    
    public String getMessage(){
    	return this.message;
    }
    
    public T getRes(){
    	return res;
    }
    
    public void setRes(T res){
    	this.res = res;
    }
    public static void main(String[] args){
    	Result res = new Result("only for test!");
    	JSONObject jobj = JSONObject.fromObject(res);
    	System.out.println(jobj);
    }
}
