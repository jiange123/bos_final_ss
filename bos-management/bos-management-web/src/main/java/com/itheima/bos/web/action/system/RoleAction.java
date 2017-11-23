package com.itheima.bos.web.action.system;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.bouncycastle.jce.provider.JDKDSASigner.noneDSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.service.system.MenuService;
import com.itheima.bos.service.system.PermissionService;
import com.itheima.bos.service.system.RoleService;
import com.itheima.bos.web.action.CommonAction;

/**  
 * ClassName:RoleAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 17, 2017 3:29:56 PM <br/>       
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class RoleAction extends CommonAction<Role> {

    /**  
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).  
     * @since JDK 1.6  
     */
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private PermissionService permissionService;
    
    @Autowired
    private MenuService menuService;
    
    //角色的分页查询
    @Action(value="roleAction_pageQuery")
    public String pageQuery() throws Exception{
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Role> page =  roleService.pageQuery(pageable);
        page2json(page, new String[]{"users","permissions","menus"});
        return NONE;
    }

    //属性驱动获取menuIds,permissionIds
    private String menuIds;
    private List<Long> permissionIds;
    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }
    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }
    
    //保存角色
    @Action(value="roleAction_save",results={@Result(name="success",location="/pages/system/role.html",type="redirect")})
    public String save(){
        roleService.save(getModel(),menuIds,permissionIds);
        return SUCCESS;
    }
    
    //查询所有角色
    @Action(value="roleAction_findAll")
    public String findAll() throws Exception{
        List<Role> list =  roleService.findAll();
        list2json(list, new String[]{"users","permissions","menus"});
        return NONE;
    }
    
    //修改角色
    @Action(value="roleAction_update",results={@Result(name="success",location="/pages/system/role.html",type="redirect")})
    public String update(){
        Role role = getModel();
        System.out.println("miaoshu="+role.getDescription());
        roleService.update(getModel(),menuIds,permissionIds);
        return SUCCESS;
    }
    
  //根据角色id 查询关联 的权限
    @Action(value="roleAction_findById")
    public String findById() throws Exception{
        List<Permission> list = permissionService.findById(getModel().getId());
        list2json(list, new String[]{"roles"});
        return NONE;
    }
    
    
    @Action(value="menuAction_findByRoleId")
    public String findByRoleId() throws Exception{
        List<Menu> list =  menuService.findByRoleId(getModel().getId());
        list2json(list, new String[]{"roles","childrenMenus","parentMenu"});
        return NONE;
    }
    
  //导出图表数据查询,当前角色关联的权限
    @Action(value="roleAction_getById")
    public String getById() throws Exception{
        List<Object[]> list = roleService.getById(getModel().getId());
        list2json(list, null);
        return NONE;
    }
    
    //导出图表数据查询，所有的权限
    @Action(value="roleAction_getAll")
    public String getAll() throws Exception{
        Long id = getModel().getId();
        List<Object[]> list = roleService.getAll(getModel().getId());
        list2json(list, null);
        return NONE;
    }
    
    //导出图表数据查询,查询已经关联的菜单
    @Action(value="roleAction_getMenuById")
    public String getMenuById() throws Exception{
        List<Object[]> list = roleService.getMenuById(getModel().getId());
        list2json(list, null);
        return NONE;
    }

    
  //导出图表数据查询，所有的菜单
  /* @Action(value="roleAction_getAllMenuLevelOne")
    public String getAllMenuLevelOne() throws Exception{
        List<Object[]> list = menuService.findByParentMenuIdIsNull();
        list2json(list, new String[]{"roles","childrenMenus","parentMenu"});
        return NONE;
    }*/
   @Action(value="roleAction_getAllMenuLevelOne")
    public String getAllMenuLevelOne() throws Exception{
        List<Object[]> list = menuService.findAssociatedLevelOneMenu(getModel().getId());
        list2json(list, new String[]{"roles","childrenMenus","parentMenu"});
        return NONE;
    }
    
}
  
