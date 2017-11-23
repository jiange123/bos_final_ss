package com.itheima.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.domain.take_rec.Promotion;

/**  
 * ClassName:PermissionRepository <br/>  
 * Function:  <br/>  
 * Date:     Nov 16, 2017 2:46:44 PM <br/>       
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    /**
     * 根据当前用户的id查询对应的权限
     * findByUid:. <br/>  
     *  
     * @param id
     * @return
     */
   @Query("select p from Permission p inner join p.roles r inner join r.users u where u.id = ?")
    List<Permission> findByUid(Long id);

   /**
    * 根据角色ID获取当前角色所有的权限
    * findByRoleId:. <br/>  
    *  
    * @param roleId
    */
   @Query("select p from Permission p inner join p.roles r where r.id = ?")
    List<Permission> findByRoleId(Long roleId);

   @Query("from Permission p inner join p.roles r where r.id <> ? ")
   List<Permission> getAll(Long id);

  

}
  
