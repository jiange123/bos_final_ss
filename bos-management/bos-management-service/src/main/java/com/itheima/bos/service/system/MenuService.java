package com.itheima.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.User;

/**  
 * ClassName:MenuService <br/>  
 * Function:  <br/>  
 * Date:     Nov 16, 2017 12:18:01 PM <br/>       
 */
public interface MenuService {

    /**
     * 分页查询菜单列表
     * pageQuery:. <br/>  
     *  
     * @param pageable
     * @return
     */
    Page<Menu> pageQuery(Pageable pageable);


    /**
     * 查询所有菜单
     * findAll:. <br/>  
     *
     */
    List<Menu> findAll();


    /**
     * 查询所有一级菜单
     * findAllOneLevel:. <br/>  
     *  
     * @return
     */
    List<Menu> findAllOneLevel();


    /**
     * 增加菜单
     * save:. <br/>  
     *  
     * @param model
     */
    void save(Menu model);


    /**
     * 根据当前登录用户动态获取菜单
     * findByUser:. <br/>  
     *  
     * @param user
     * @return
     */
    List<Menu> findByUser(User user);


    /**
     * 根据角色id查询已经关联的菜单
     * findByRoleId:. <br/>  
     *  
     * @param id
     * @return
     */
    List<Menu> findByRoleId(Long id);

    /**
     * 查询所有一级菜单
     * findByParentMenuIdIsNull:. <br/>  
     *  
     * @return
     */
    List<Object[]> findByParentMenuIdIsNull();


    /**
     *  //通过父级菜单名字查询子菜单
     * findSonMenu:. <br/>  
     *  
     * @param name
     * @return
     */
    List<Object[]> findSonMenu(Long id,String name);


    /**
     * 查询当前角色关联的一级菜单
     * findAssociatedLevelOneMenu:. <br/>  
     *  
     * @param id
     * @return
     */
    List<Object[]> findAssociatedLevelOneMenu(Long id);

    
}
  
