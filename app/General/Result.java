package General;
import net.sf.json.JSONArray;  
import net.sf.json.JSONObject; 

/**
 * Created by yehuizhang on 14-9-1.
 */
public class Result<T> {
    private boolean success;

    private String message;

    private T res;

    public Result(T res){
        this.success = true;
        this.res = res;
    }

    public Result(String message){
        this.message = message;
        this.success = false;
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
