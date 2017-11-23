package com.itheima.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.User;

/**  
 * ClassName:MenuRepository <br/>  
 * Function:  <br/>  
 * Date:     Nov 16, 2017 12:19:19 PM <br/>       
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {

    /**
     * 查询所有一级菜单
     * findByParentMenuIsNull:. <br/>  
     *  
     * @return
     */
    List<Menu> findByParentMenuIsNull();

    /**
     * 根据当前登录的客户信息动态查询菜单
     * findByUser:. <br/>  
     *  
     * @param id
     * @return
     */
   @Query("select m from Menu m inner join m.roles r inner join r.users u where u.id = ?")
    List<Menu> findByUser(Long id);

   /**
    * 根据当前角色的id查询所有角色可以看到的
    * findByRoleId:. <br/>  
    *  
    * @param roleId
    * @return
    */
   @Query("select m from Menu m inner join m.roles r where r.id = ?")
   List<Menu> findByRoleId(Long roleId);

   /**
    * 查询所有一级菜单
    * findByParentMenuIdIsNull:. <br/>  
    *  
    * @return
    */
   @Query("select m.name,count(*) from Menu m where m.parentMenu.id is null group by m.name")
   List<Object[]> findByParentMenuIdIsNull();

   /**
    * //通过父级菜单名字查询子菜单
    * findOnMenu:. <br/>  
    *  
    * @param name
    * @return
    */
  // @Query(value="select m.c_name,count(*) from t_menu m where m.c_pid = (select m.c_id from t_menu m where m.c_name = ?) group by m.c_name",nativeQuery=true)
   @Query(value="select m.c_name,count(*) from t_menu m inner join t_role_menu trm on trm.c_menu_id = m.c_id inner join t_role r on trm.c_role_id = r.c_id where r.c_id = ? and m.c_pid is not null and m.c_pid =(select m.c_id from t_menu m where m.c_name = ?) group by m.c_name",nativeQuery=true)
   List<Object[]> findOnMenu(Long id,String name);

   /**
    * 根据当前角色id，查询已经关联的一级菜单
    * findAssociatedLevelOneMenu:. <br/>  
    *  
    * @param id
    * @return
    */
   @Query(value="select m.c_name,count(*) from t_menu m inner join t_role_menu trm on trm.c_menu_id = m.c_id inner join t_role r on trm.c_role_id = r.c_id where r.c_id = ? and m.c_pid is null group by m.c_name",nativeQuery=true)
   List<Object[]> findAssociatedLevelOneMenu(Long id);
}
  
