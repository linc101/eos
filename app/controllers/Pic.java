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
        System.out.println(Math.round(11.5) + " " + Math.round(-11.5));
        short s1 = 1;
        s1 += 1;

    }

    public int findMin(int[] num){
        int left = 0, right = num.length - 1;
        while(left <= right){

        }
        return 0;
    }
}
