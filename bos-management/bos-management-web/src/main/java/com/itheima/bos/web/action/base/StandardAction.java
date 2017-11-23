package com.itheima.bos.web.action.base;

import java.util.Date;


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

import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.StandardService;
import com.itheima.bos.web.action.CommonAction;

/**  
 * ClassName:StandardAction <br/>  
 * Function:  <br/>  
 * Date:     Oct 31, 2017 8:43:16 PM <br/>       
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class StandardAction extends CommonAction<Standard> {

    /**  
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).  
     * @since JDK 1.6  
     */
    private static final long serialVersionUID = 1L;
    @Autowired
    private StandardService standardService;
    @Action(value="standardAction_save",results={@Result(name="success",location="/pages/base/standard.html",type="redirect")})
    //保存收件标准
    public String save(){
        
        getModel().setOperatingTime(new Date());
        standardService.save(getModel());
        return SUCCESS;
    }
    //属性驱动获取page和rows
    private int page;
    private int rows;
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    //分页查询收件标准
    @Action(value="standardAction_pageQuery",results={@Result(type="redirect")})
    public String pageQuery() throws Exception{
        //使用JPA封装OK的分页查询
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Standard> page = standardService.pageQuery(pageable);
        page2json(page, null);
        return NONE;
    }
    //根据id删除收件标准
    @Action(value="standardAction_deleteById",results={@Result(name="success",location="standardAction_pageQuery",type="chain")})
    public String deleteById(){
        //获取id
        System.out.println(getModel().getId());
        standardService.deleteById(getModel().getId());
        return SUCCESS;
    }

}
  
