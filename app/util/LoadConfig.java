package util;

/**
 * Created by yehuizhang on 14/10/25.
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class LoadConfig {
    public static Logger log = LoggerFactory.getLogger(LoadConfig.class);

    private static Properties props = new Properties();

    static {
        try {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("thirdpart.properties"));
        } catch (FileNotFoundException e) {
            log.error("thirdpart properties not foundred!");
        } catch (IOException e) {
            log.error("thirdpart properties not foundred!");
        }
    }

    public static String getValue(String key){
        return props.getProperty(key);
    }

    public static void updateProperties(String value, String key){
        props.setProperty(key, value);
    }

    public static void main(String[] args){
        Properties ps = props;
        System.out.println(ps);
    }

}
