package com.itheima.bos.web.action.system;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.MenuService;
import com.itheima.bos.web.action.CommonAction;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:MenuAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 16, 2017 12:10:38 PM <br/>       
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class MenuAction extends CommonAction<Menu> implements ModelDriven<Menu> {

    /**  
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).  
     * @since JDK 1.6  
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private MenuService menuService;
    
    @Action(value="menuAction_pageQuery")
    public String pageQuery() throws Exception{
        //从model中获取page的值
        Pageable pageable = new PageRequest(Integer.parseInt(getModel().getPage())-1, rows);
        Page<Menu> page = menuService.pageQuery(pageable);
        page2json(page, new String[]{"roles","childrenMenus","parentMenu"});
        return NONE;
    }
    
   /* @Action(value="menuAction_findAll")
    *   查询所有菜单
    public String findAll() throws Exception{
        List<Menu> list =  menuService.findAll();
        list2json(list, new String[]{"roles","childrenMenus","parentMenu"});
        return NONE;
    }*/
    
    @Action(value="menuAction_findAllOneLevel")
    //查询所有一级菜单，通过一级菜单级联查询子菜单，避免子菜单重复查询
    public String findAllOneLevel() throws Exception{
        List<Menu> list =  menuService.findAllOneLevel();
        list2json(list, new String[]{"roles","childrenMenus","parentMenu"});
        return NONE;
    }
    
    @Action(value="menuAction_save",results={@Result(name="success",location="/pages/system/menu.html",type="redirect")})
    //增加菜单
    public String save(){
        //判断要添加 的菜单是不是顶级菜单，如果是顶级菜单，则将他的父级菜单设为null
        //获取驱动中的父级值
        Menu parentMenu = getModel().getParentMenu();
        if(parentMenu.getId() == null || parentMenu.getId() == 0){
            getModel().setParentMenu(null);
        }
        menuService.save(getModel());
        return SUCCESS;
    }
    
    //通过当前登录的用户动态显示菜单
    @Action(value="menuAction_findByUser")
    public String findByUser() throws Exception{
        //获取当前登录的用户
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Menu> list =  menuService.findByUser(user);
        list2json(list, new String[]{"roles","childrenMenus","parentMenu","children"});
        return NONE;
    }
    
    
    //通过父级菜单名字查询当前角色关联的子菜单
    @Action(value="menuAction_findSonMenu")
    public String findSonMenu() throws Exception{
        //获取当前登录的用户
        List<Object[]> list =  menuService.findSonMenu(getModel().getId(),getModel().getName());
        list2json(list, new String[]{"roles","childrenMenus","parentMenu"});
        return NONE;
    }
    
    
    
    
}
  
