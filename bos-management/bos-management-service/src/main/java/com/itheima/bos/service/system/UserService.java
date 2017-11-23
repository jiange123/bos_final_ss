package com.itheima.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.system.User;

/**  
 * ClassName:UserService <br/>  
 * Function:  <br/>  
 * Date:     Nov 17, 2017 9:23:09 PM <br/>       
 */
public interface UserService {

    /**
     * 保存用户
     * save:. <br/>  
     *  
     * @param model
     * @param roleIds
     */
    void save(User model, List<Long> roleIds);

    /**
     * 分页查询所有用户
     * pageQuery:. <br/>  
     *  
     * @param pageable
     * @return
     */
    Page<User> pageQuery(Pageable pageable);

}
  
