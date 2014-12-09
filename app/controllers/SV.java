package controllers;

import models.StaffVacation;
import org.apache.commons.lang.StringUtils;

import static controllers.WrapRender.*;
/**
 * Created by uttp on 14-12-9.
 */
public class SV extends CommonRender {
    public static void index(){
        render("sv.html");
    }

    public static void saveStaff(String staffName, String staffId, int type, long startTime, long endTime){
        if(StringUtils.isEmpty(staffName)){
            RenderFailed("请输入用户名");
        }

        if(StringUtils.isEmpty(staffId)){
            RenderFailed("请输入员工号");
        }

        if(type != 1 && type != 2){
            RenderFailed("请选择员工类型！");
        }

        if(startTime <= 0 || endTime <= 0){
            RenderFailed("请选择开始时间和结束时间");
        }

        StaffVacation sv = new StaffVacation(staffName, staffId, type, startTime, endTime);

        boolean isSuccess = sv.jdbcSave();

        if(isSuccess){
            RenderSuccess();

        }else{
            RenderFailed("插入数据库失败！");
        }

    }

}
