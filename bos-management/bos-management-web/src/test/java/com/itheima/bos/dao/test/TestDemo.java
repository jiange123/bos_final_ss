package com.itheima.bos.dao.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

/**  
 * ClassName:TestDemo <br/>  
 * Function:  <br/>  
 * Date:     Nov 2, 2017 11:36:20 AM <br/>       
 */
public class TestDemo {

    @Test
    public void test01(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        System.out.println(format);
    }
}
  
