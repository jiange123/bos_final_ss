package com.itheima.activemq;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

import com.ithiema.bos.utils.SmsUtils;



/**  
 * ClassName:SmsConsumer <br/>  
 * Function:  <br/>  
 * Date:     Nov 13, 2017 2:47:20 PM <br/>       
 */
@Component
public class SmsConsumer implements MessageListener {
    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;
        try {
            String telephone = mapMessage.getString("telephone");
            String msg = mapMessage.getString("msg");
            System.out.println("手机号："+telephone+"===="+msg);
            SmsUtils.sendSmsByWebService(telephone, msg);
            System.out.println("验证码已经发送到手机，请及时查看");
        } catch (JMSException e) {
            e.printStackTrace();  
        }
    }
}
  
