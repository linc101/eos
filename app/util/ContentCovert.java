package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by uttp on 14-9-11.
 */
public class ContentCovert {
    public static String convertNewLineCharToBR(String content){
        String convertedString = content.replaceAll("\\n", "</br>");
        return convertedString;
    }
}
