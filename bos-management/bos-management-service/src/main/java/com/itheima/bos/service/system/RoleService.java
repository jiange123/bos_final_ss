package com.itheima.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.system.Role;

/**  
 * ClassName:RoleService <br/>  
 * Function:  <br/>  
 * Date:     Nov 17, 2017 3:28:36 PM <br/>       
 */
public interface RoleService {

    /**
     * 分页查询所有角色
     * pageQuery:. <br/>  
     *  
     * @param pageable
     * @return
     */
    Page<Role> pageQuery(Pageable pageable);

    /**
     * 保存角色
     * save:. <br/>  
     *  
     * @param model
     * @param menuIds
     * @param permissionIds
     */
    void save(Role model, String menuIds, List<Long> permissionIds);

    /**
     * 查询所有角色
     * findAll:. <br/>  
     *  
     * @return
     */
    List<Role> findAll();

    /**
     * 修改角色
     * update:. <br/>  
     *  
     * @param model
     * @param menuIds
     * @param permissionIds
     */
    void update(Role model, String menuIds, List<Long> permissionIds);

    
    /**
     * 图表显示已经关联的角色查询
     * getById:. <br/>  
     *  
     * @param id
     * @return
     */
    List<Object[]> getById(Long id);

    /**
     * 图表显示全部角色
     * getAll:. <br/>  
     * @param id 
     *  
     * @return
     */
    List<Object[]> getAll(Long id);

    /**
     * 查询当前角色关联的菜单
     * getMenuById:. <br/>  
     *  
     * @param id
     * @return
     */
    List<Object[]> getMenuById(Long id);
}
  
