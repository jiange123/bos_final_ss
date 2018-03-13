package com.icarbonx;

import com.icarbonx.growup.utils.DateUtil;
import org.junit.Test;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class test {

    @Test
    public void test1(){
        String age="1988-10-10";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = format.parse(age);
            String date = DateUtil.getPersonAgeByBirthDate(parse);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
//        String s = new DecimalFormat("#.00").format(50.099998474121094);
        Date dateByStr1 = DateUtil.getDateByStr("2018-01-01", "yyyy-MM-dd");
        Date dateByStr2 = DateUtil.getDateByStr("2018-03-01", "yyyy-MM-dd");
        int i = DateUtil.getDaysBetween(dateByStr1, dateByStr2);
        System.out.println(i);
    }

    @Test
    public void test3(){
        Date dateByStr1 = DateUtil.getDateByStr("2018-01-01", "yyyy-MM-dd");
        Date dateByStr2 = DateUtil.getDateByStr("2018-03-01", "yyyy-MM-dd");
        int i = DateUtil.getDaysBetween(dateByStr1, dateByStr2);
        System.out.println(i);
    }
}
