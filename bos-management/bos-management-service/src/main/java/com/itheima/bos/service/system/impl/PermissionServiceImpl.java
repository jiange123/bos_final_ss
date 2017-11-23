package com.itheima.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.system.PermissionRepository;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.service.system.PermissionService;

/**  
 * ClassName:PermissionServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 16, 2017 2:46:13 PM <br/>       
 */
@Transactional
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;
    @Override
    //分页查询当前用户的所有权限
    public Page<Permission> pageQuery(Pageable pageable) {
        return permissionRepository.findAll(pageable);
    }
    @Override
    //保存用户的权限
    public void save(Permission model) {
        permissionRepository.save(model);
    }
    @Override
    //查询所有权限
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }
    @Override
    // 根据roleId查询关联的权限
    public List<Permission> findById(Long id) {
        return permissionRepository.findByRoleId(id);
    }

}
  
