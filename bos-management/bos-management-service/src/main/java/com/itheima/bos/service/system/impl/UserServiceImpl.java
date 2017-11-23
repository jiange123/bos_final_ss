package com.itheima.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.system.UserRepository;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.UserService;

/**  
 * ClassName:UserServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 17, 2017 9:23:30 PM <br/>       
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    //保存用户
    public void save(User model, List<Long> roleIds) {
        userRepository.save(model);
        if(roleIds != null && roleIds.size() > 0){
            Role role;
            for (Long roleId : roleIds) {
                role = new Role();
                role.setId(roleId);
                model.getRoles().add(role);
            }
        }
    }

    @Override
    //分页查询所有哦用户
    public Page<User> pageQuery(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
  
