package com.itheima.bos.web.action.system;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.loader.plan.exec.process.spi.ReturnReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.system.Role;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.UserService;
import com.itheima.bos.web.action.CommonAction;

/**  
 * ClassName:UserAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 14, 2017 9:14:53 PM <br/>       
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class UserAction extends CommonAction<User> {

    /**  
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).  
     * @since JDK 1.6  
     */
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private UserService userService;
    
    //获取用户输入的验证码
    private String inputCode;
    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }
    @Action(value="userAction_login",results={@Result(name="success",location="/index.html",type="redirect"),@Result(name="login",location="/login.html",type="redirect")})
    public String login(){
        System.out.println("用户准备登录了");
      //获取服务器生成的验证码
        String  serviceCode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
        //比对验证码
        if(StringUtils.isNotEmpty(serviceCode) && StringUtils.isNotEmpty(inputCode) && serviceCode.equals(inputCode)){
            //如果验证码相等,查询数据库，判断用户是否存在
            //获取代表当前对象的subject
            Subject subject = SecurityUtils.getSubject();
            //创建令牌
            AuthenticationToken token = new UsernamePasswordToken(getModel().getUsername(), getModel().getPassword());
            //使用框架提供的登录方法进行登录
            try {
                //登录成功
                subject.login(token);
                //获取用户信息
                User user = (User) subject.getPrincipal();
                //保存登录信息
                ServletActionContext.getRequest().getSession().setAttribute("user", user);
                System.out.println("登录成功");
                return SUCCESS;
            } catch (AuthenticationException e) {
               //登录失败,重新登录
                e.printStackTrace();  
            }
        }
        //登录成功
        System.out.println("登录失败");
        return LOGIN;
    }
    
    @Action(value="userAction_logout",results={@Result(name="success",location="/login.html",type="redirect")})
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        //框架提供的注销方法，需要手动清空session
        ServletActionContext.getRequest().getSession().invalidate();
        System.out.println("注销成功");
        return SUCCESS;
    }
    
    //属性驱动获取roleIds
    private List<Long> roleIds;
    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }
    
    //保存用户
    @Action(value="userAction_save",results={@Result(name="success",location="/pages/system/userlist.html",type="redirect")})
    public String save(){
        userService.save(getModel(),roleIds);
        return SUCCESS;
    }
    
    //分页查询所有的用户
    @Action(value="userAction_pageQuery")
    public String pageQuery() throws Exception{
        Pageable pageable = new PageRequest(page-1, rows);
        Page<User> page = userService.pageQuery(pageable);
        page2json(page, new String[]{"roles"});
        return NONE;
    }

}
  
