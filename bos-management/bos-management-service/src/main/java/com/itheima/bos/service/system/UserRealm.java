package com.itheima.bos.service.system;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itheima.bos.dao.system.PermissionRepository;
import com.itheima.bos.dao.system.RoleRepository;
import com.itheima.bos.dao.system.UserRepository;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.domain.system.User;

/**  
 * ClassName:UserRealm <br/>  
 * Function:  <br/>  编写realm
 * Date:     Nov 14, 2017 9:38:18 PM <br/>       
 */
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleRepository roleRepository;
    
    @Override
    //授权方法
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //创建授权对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取当前登录的用户名
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //判断当前登录的用户,如果是管理员账号，则拥有所有权限和角色，其他根据相应的进行展示
        if("admin".equals(user.getUsername())){
            //查询所有权限
            List<Permission> permissions =  permissionRepository.findAll();
            for (Permission permission : permissions) {
                //添加所有权限
                info.addStringPermission(permission.getKeyword());
            }
            //查询所有角色
            List<Role> roles = roleRepository.findAll();
            for (Role role : roles) {
                //添加所有角色
                info.addRole(role.getKeyword());
            }
        }else{
            //根据当前用户id查询对应 的权限和角色
            List<Permission> permissions = permissionRepository.findByUid(user.getId());
            for (Permission permission : permissions) {
                info.addStringPermission(permission.getKeyword());
            }
            List<Role> roles = roleRepository.findByUid(user.getId());
            for (Role role : roles) {
                info.addRole(role.getKeyword());
            }
        }
        return info;
    }

    //注入dao
    @Autowired
    private UserRepository userRepository;
    @Override
    //认证方法
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
          //获取token中的用户数据查询数据库
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        User user = userRepository.findByUsername(username);
        if(user == null){
            //框架自行抛出异常信息
           return null;
        }
        //如果查询到了，返回查询信息
        /**
         * Object principal    参数1：身份，一般代表当前登录的用户
         * Object credentials  参数2：凭证，可以是密码（从数据库中获取的值）
         * String realmName    参数3：realm的名字，就是本类
         */
        AuthenticationInfo info = new SimpleAccount(user, user.getPassword(), this.getName());
        return info;
    }

}
  
