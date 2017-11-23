package com.itheima.bos.web.action.base;

import java.util.List;

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

import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.TakeTimeService;
import com.itheima.bos.web.action.CommonAction;

/**  
 * ClassName:TakeTimeAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 7, 2017 10:07:05 AM <br/>       
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class TakeTimeAction extends CommonAction<TakeTime> {

    /**  
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).  
     * @since JDK 1.6  
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private TakeTimeService takeTimeService;
    @Action(value="takeTimeAction_findAll")
    public String findAll() throws Exception{
        List<TakeTime> list = takeTimeService.findAll();
        list2json(list, null);
        return NONE;
    }
    @Action(value="takeTimeAction_save",results={@Result(name="success",location="/take_time.html",type="redirect")})
    public String save(){
        takeTimeService.save(getModel());
        return SUCCESS;
    }
    
    @Action(value="takeTimeAction_pageQuery")
    public String pageQuery() throws Exception{
        Pageable pageable = new PageRequest(page-1, rows);
        Page<TakeTime> page = takeTimeService.pageQuery(pageable);
        page2json(page, null);
        return NONE;
    }
    
}
  
