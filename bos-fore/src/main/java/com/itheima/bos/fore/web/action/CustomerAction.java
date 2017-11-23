package com.itheima.bos.fore.web.action;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import com.itheima.crm.domain.Customer;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * ClassName:CustomerAction <br/>
 * Function: <br/>
 * Date: Nov 8, 2017 8:46:06 PM <br/>
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {
    /**
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).
     * 
     * @since JDK 1.6
     */
    private static final long serialVersionUID = 1L;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private JmsTemplate jmsTemplate;
    private Customer customer;

    @Override
    public Customer getModel() {
        if (customer == null) {
            customer = new Customer();
        }
        return customer;
    }

    // send code to customer
    @Action(value = "customerAction_sendCode")
    public String sendCode() {
        // generate code
        String code = RandomStringUtils.randomNumeric(4);
        System.out.println("生成的验证码为：" + code);
        // save code to session
        ServletActionContext.getRequest().getSession().setAttribute(customer.getTelephone(), code);
        //send
        final String msg = "尊敬的客户你好，您本次获取的验证码为：" + code;
       // SmsUtils.sendSmsByWebService(customer.getTelephone(), msg);
        //将验证码信息发送至 消息中间件
        jmsTemplate.send("sms", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("telephone", customer.getTelephone());
                mapMessage.setString("msg", msg);
                return mapMessage;
            }
        });
        return NONE;
    }

    //get code from customer input
    private String checkcode;

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    // finish registe
    @Action(value = "customerAction_regist",
            results = {
                    @Result(name = "success", location = "/signup-success.html", type = "redirect"),
                    @Result(name = "error", location = "/signup-fail.html", type = "redirect")})
    public String regist() {
        // get serverCode
        String serverCode = (String) ServletActionContext.getRequest().getSession()
                .getAttribute(customer.getTelephone());
        // check serverCode and checkcode
        if (StringUtils.isNotEmpty(serverCode) && StringUtils.isNotEmpty(checkcode)
                && serverCode.equals(checkcode)) {
            // serverCode same as checkcode,then send request to crm for post customer
            WebClient.create("http://localhost:8888/crm/webservice/customerService/regist")
                    .type(MediaType.APPLICATION_JSON).post(customer);
            //************给用户发送激活邮件******************
            // generate random activeCode
            String activeCode = RandomStringUtils.randomNumeric(32);
            // set activedCode to redis, the key is telephone
            redisTemplate.opsForValue().set(customer.getTelephone(), activeCode);
            // emailbody
            final String emailBody =
                    "尊敬的用户您好，感谢您注册本公司，请在24小时内点击此<a href='http://localhost:8180/bos-fore/customerAction_activeCount.action?telephone="
                            + customer.getTelephone() + "&activeCode=" + activeCode
                            + "'>链接</a>进行激活账号...";
            // send email for active account
           // MailUtils.sendMail(customer.getEmail(), "激活邮件", emailBody);
            //将激活邮件发送到消息中间件
            
            jmsTemplate.send("mail", new MessageCreator() {
                
                @Override
                public Message createMessage(Session session) throws JMSException {
                    MapMessage mapMessage = session.createMapMessage();
                    mapMessage.setString("customerMail", customer.getEmail());
                    mapMessage.setString("topic", "激活邮件");
                    mapMessage.setString("emailBody", emailBody);
                    return mapMessage;
                }
            });
            
            return SUCCESS;
        }
        // registe fail
        return ERROR;
    }

    // get activedCode by attribute
    private String activeCode;

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    // active account
    @Action(value = "customerAction_activeCount", results = {
            @Result(name = "actived-success", location = "/actived-success.html",
                    type = "redirect"),
            @Result(name = "actived", location = "/actived.html", type = "redirect"),
            @Result(name = "actived-fail", location = "/actived-fail.html", type = "redirect")})
    public String activeCount() {
        // get redis telephone
        String telephone = customer.getTelephone();
        // get redis actived
        String redisCode = redisTemplate.opsForValue().get(telephone);
        // check activedCode
        if (StringUtils.isNotEmpty(telephone)) {
            if (StringUtils.isNotEmpty(redisCode) && StringUtils.isNotEmpty(activeCode)
                    && activeCode.equals(redisCode)) {
                // the activeCode same as redisCode then get customer
                Customer customer = WebClient
                        .create("http://localhost:8888/crm/webservice/customerService/customer")
                        // .accept(MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON)
                        .type(MediaType.APPLICATION_XML).query("telephone", telephone)
                        .get(Customer.class);
                if (customer != null) {
                    // judge the stauts of customer type
                    if (customer.getType() == null) {
                        // send request to active
                        WebClient
                                .create("http://localhost:8888/crm/webservice/customerService/active")
                                .query("telephone", telephone).put(null);
                        return "actived-success";
                    }
                    // the account has actived
                    return "actived";
                }
            }
        }
        return "actived-fail";
    }

    // finish login
    @Action(value = "customerAction_login",
            results = {@Result(name = "success", location = "/index.html", type = "redirect"),
                    @Result(name = "error", location = "/login.html",type="redirect")})
    public String login() {
        // get code from validatecode.jsp
        String serverCode = (String) ServletActionContext.getRequest().getSession()
                .getAttribute("validateCode");
        // check serverCode checkcode
        if (StringUtils.isNotEmpty(serverCode) && StringUtils.isNotEmpty(checkcode)
                && serverCode.equals(checkcode)) {
            // serverCode same as checkCode then get custoemr by telephone and password
            Customer cust = WebClient.create("http://localhost:8888/crm/webservice/customerService/login")
                    .query("telephone", customer.getTelephone())
                    .query("password", customer.getPassword()).get(Customer.class);
            if(cust != null && cust.getType()!=null&&cust.getType()==1){
                //save customer to session
                ServletActionContext.getRequest().getSession().setAttribute("customer", cust);
                return SUCCESS;
            }
        }
        return ERROR;
    }
    
    //checkTelephone
    @Action(value="customerAction_checkTelephone")
    public String checkTelephone() throws Exception{
        Customer cust = WebClient.create("http://localhost:8888/crm/webservice/customerService/checkTelephone")
                 .query("telephone", customer.getTelephone())
                 .get(Customer.class);
       if(cust != null && cust.getTelephone()!= null){
          //  System.out.println(cust.getTelephone());
           HttpServletResponse response = ServletActionContext.getResponse();
           response.setContentType("text/html;charset=utf-8");
           response.getWriter().write("<font color='red'>手机号已经被占用</font>");
        }
        System.out.println("没有改用户，可以注册");
     /*  String json = JSONObject.fromObject(cust).toString();
       HttpServletResponse response = ServletActionContext.getResponse();
       response.setContentType("application/josn;charset=utf-8");
       response.getWriter().write(json);*/
       return NONE;
    }

}
