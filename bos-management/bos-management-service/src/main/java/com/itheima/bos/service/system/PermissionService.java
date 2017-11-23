package com.itheima.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.system.Permission;

/**  
 * ClassName:PermissionService <br/>  
 * Function:  <br/>  
 * Date:     Nov 16, 2017 2:45:44 PM <br/>       
 */
public interface PermissionService {

    /**
     * 分页查询所有当前 用户的所有权限
     * pageQuery:. <br/>  
     *  
     * @param pageable
     * @return
     */
    Page<Permission> pageQuery(Pageable pageable);

    /**
     * 保存权限
     * save:. <br/>  
     *  
     * @param model
     */
    void save(Permission model);

    /**
     * 查询所有权限
     * findAll:. <br/>  
     *  
     * @return
     */
    List<Permission> findAll();

    /**
     * 根据roleId查询关联的权限
     * findById:. <br/>  
     *  
     * @param id
     * @return
     */
    List<Permission> findById(Long id);

}
  
