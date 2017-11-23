package com.itheima.test.quartz;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**  
 * ClassName:TestQuartz <br/>  
 * Function:  <br/>  
 * Date:     Nov 19, 2017 9:25:59 AM <br/>       
 */
public class TestQuartz {

    //服务器启动加载配置文件
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        String name = context.getApplicationName();
        System.out.println(name);
    }
}
  
