package com.itheima.activemq;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

import com.ithiema.bos.utils.MailUtils;

/**  
 * ClassName:MailConsumer <br/>  
 * Function:  <br/>  
 * Date:     Nov 13, 2017 8:35:44 PM <br/>       
 */
@Component
public class MailConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            MapMessage mapMessage = (MapMessage) message; 
            String email = mapMessage.getString("customerMail");
            String topic = mapMessage.getString("topic");
            String emailBody = mapMessage.getString("emailBody");
            System.out.println(email+"  "+topic+"  "+emailBody);
            MailUtils.sendMail(email, topic, emailBody);
            System.out.println("邮件已经成功发送，请及时登录邮箱完成激活");
        } catch (JMSException e) {
            e.printStackTrace();  
            
        }
    }

}
  
