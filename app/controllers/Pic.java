package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by yehuizhang on 14/11/4.
 */
public class Pic extends CommonRender {
    private static final Logger log = LoggerFactory.getLogger(Pic.class);

    public static void index(){
        render("/picture/picturemain.html");
    }

    public static void main(String[] args){
        InputStream fileReader = null;
        BufferedReader br = null;
        try {
            fileReader = new FileInputStream("/Users/yehuizhang/test.txt");
            br = new BufferedReader(new InputStreamReader(fileReader));
            String line = null;
            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                while( line != null){
                    System.out.println(line);
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally{
            try {
                if(fileReader != null)
                    fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                if(br != null){
                    br.close();
                }
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
