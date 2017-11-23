package com.itheima.bos.service.system.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.system.MenuRepository;
import com.itheima.bos.dao.system.PermissionRepository;
import com.itheima.bos.dao.system.RoleRepository;
import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.domain.take_rec.Promotion;
import com.itheima.bos.service.system.RoleService;

/**  
 * ClassName:RoleServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 17, 2017 3:28:53 PM <br/>       
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;
    
    @Autowired
    private MenuRepository menuRepository;
    
    @Override
    //分页查询所有角色
    public Page<Role> pageQuery(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    //保存角色
    public void save(Role model, String menuIds, List<Long> permissionIds) {
        roleRepository.save(model);
        //处理menuIds
        if(StringUtils.isNotEmpty(menuIds)){
            String[] split = menuIds.split(",");
            Menu menu;
            for (String menuId : split) {
                //瞬时态
                menu = new Menu();
                //游离态有Id
                menu.setId(Long.parseLong(menuId));
                model.getMenus().add(menu);
            }
        }
        
        //处理permissionIds
        if(permissionIds != null && permissionIds.size() > 0){
            Permission permission;
            for (Long permissionId : permissionIds) {
                permission = new Permission();
                permission.setId(permissionId);
                model.getPermissions().add(permission);
            }   
        }
    }

    @Override
    //查询所有角色
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public void update(Role model, String menuIds, List<Long> permissionIds) {
        
        
        //1.根据角色ID将权限中该角色的权限置为空
        Long roleId = model.getId();
        //获取持久化角色
        Role role = roleRepository.findOne(roleId);
       roleRepository.save(model);
        
        //获取该角色当前所有的权限
       List<Permission> pmisList = permissionRepository.findByRoleId(roleId);
        
        //移除当前角色的权限
        if(pmisList != null && pmisList.size() > 0){
            for (Permission permission : pmisList) {
                role.getPermissions().remove(permission);
            }
        }
       
        
        //2.根据角色id重新给该角色赋予permissionIds中的权限
        if(permissionIds != null && permissionIds.size() > 0){
            Permission permission;
            for (Long permissionId : permissionIds) {
                permission = new Permission();
                permission.setId(permissionId);
                //model.getPermissions().add(permission);
                role.getPermissions().add(permission);
            }
        }
        
        
        //3.根据角色ID将menu中该角色对应的菜单全部删除
        List<Menu> menuList = menuRepository.findByRoleId(roleId);
        for (Menu menu : menuList) {
            role.getMenus().remove(menu);
        }
       
        
        
        //4.根据角色ID将menuIds中的菜单重新赋予给该角色
        if(StringUtils.isNotEmpty(menuIds)){
            String[] split = menuIds.split(",");
            Menu menu;
            for (String menuId : split) {
                menu=new Menu();
                menu.setId(Long.parseLong(menuId));
                role.getMenus().add(menu);
            }
        }
        
        
    }
    
    @Override
    //查询所有当前角色关联的权限
    public List<Object[]> getById(Long id) {
        return roleRepository.getById(id);
    }
    
    @Override
    //查询所有权限
    public List<Object[]> getAll(Long id) {
       List<Object[]> list = roleRepository.getAll(id,id);
      // List<Permission> list = permissionRepository.getAll(id);
        
        return list;
    }
    
    //查询当前角色关联的菜单
    @Override
    public List<Object[]> getMenuById(Long id) {
        return roleRepository.getMenuById(id);
    }
}
  
