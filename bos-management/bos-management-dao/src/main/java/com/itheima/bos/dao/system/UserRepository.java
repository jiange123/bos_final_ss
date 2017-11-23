package com.itheima.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.system.User;

/**  
 * ClassName:UserRepository <br/>  
 * Function:  <br/>  
 * Date:     Nov 14, 2017 9:34:39 PM <br/>       
 */
public interface UserRepository extends JpaRepository<User, Long> {

    //自定义查询方法
    User findByUsername(String username);
}
  
