package com.itheima.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.system.MenuRepository;
import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.MenuService;

/**  
 * ClassName:MenuServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 16, 2017 12:18:35 PM <br/>       
 */
@Transactional
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    //分页查询所有菜单
    public Page<Menu> pageQuery(Pageable pageable) {
        return menuRepository.findAll(pageable);
    }

    @Override
    //查询所有菜单
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    @Override
    //查询所有一级菜单
    public List<Menu> findAllOneLevel() {
        return menuRepository.findByParentMenuIsNull();
    }

    @Override
    //增加菜单进行保存
    public void save(Menu model) {
        menuRepository.save(model);
    }

    @Override
    //根据当前登录用户动态获取菜单
    public List<Menu> findByUser(User user) {
        if("admin".equals(user.getUsername())){
            return menuRepository.findAll();
        }
        return menuRepository.findByUser(user.getId());
    }

    @Override
    //根据角色id查询已经关联的菜单
    public List<Menu> findByRoleId(Long id) {
        return menuRepository.findByRoleId(id);
    }
    
    @Override
    public List<Object[]> findByParentMenuIdIsNull() {
        return menuRepository.findByParentMenuIdIsNull();
    }
    
    @Override
    //通过父级菜单名字查询子菜单
    public List<Object[]> findSonMenu(Long id,String name) {
        return menuRepository.findOnMenu(id,name);
    }
    
    @Override
    public List<Object[]> findAssociatedLevelOneMenu(Long id) {
        return menuRepository.findAssociatedLevelOneMenu(id);
    }
    
    
}
  
