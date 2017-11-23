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

import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.service.system.PermissionService;
import com.itheima.bos.web.action.CommonAction;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:PermissionAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 16, 2017 2:44:17 PM <br/>       
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class PermissionAction extends CommonAction<Permission> implements ModelDriven<Permission> {

    /**  
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).  
     * @since JDK 1.6  
     */
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private PermissionService permissionService;
    
    @Action(value="permissionAction_pageQuery")
    public String pageQuery() throws Exception{
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Permission> page = permissionService.pageQuery(pageable);
        page2json(page, new String[]{"roles"});
        return NONE;
    }
    
    @Action(value="permissionAction_save",results={@Result(name="success",location="/pages/system/permission.html",type="redirect")})
    public String save(){
        permissionService.save(getModel());
        return SUCCESS;
    }
    
    @Action(value="permissionAction_findAll")
    //查询所有权限
    public String findAll() throws Exception{
        List<Permission> list = permissionService.findAll();
        list2json(list, new String[]{"roles"});
        return NONE;
    }
    
    

}
  
