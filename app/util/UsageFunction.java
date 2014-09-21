package util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by uttp on 14-9-21.
 */
public class UsageFunction {
    private static final Logger log = LoggerFactory.getLogger(UsageFunction.class);

    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    public static boolean isEmail(String email){
        if(StringUtils.isEmpty(email)){
            return false;
        }

        Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+");
        Matcher m = p.matcher(email);
        return m.matches();

    }

    public static void main(String[] args){
        String email = "test@gmail.com";

        boolean isEmail = isEmail(email);
        if(isEmail){
            System.out.println("right!");
        }else{
            System.out.println("Error!");
        }
    }
}
