package com.itheima.bos.web.action.base;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.xml.resolver.helpers.PublicId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.FixedAreaService;
import com.itheima.bos.service.base.SubareaService;
import com.itheima.bos.web.action.CommonAction;
import com.itheima.crm.domain.Customer;

/**  
 * ClassName:FixedAreaAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 4, 2017 11:08:09 PM <br/>       
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class FixedAreaAction extends CommonAction<FixedArea> {

    /**  
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).  
     * @since JDK 1.6  
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private FixedAreaService fixedAreaService;
    //保存定区
    @Action(value="fixedAreaAction_save",results={@Result(name="success",location="/pages/base/fixed_area.html",type="redirect")})
    public String save(){
        getModel().setOperatingTime(new Date());
        fixedAreaService.save(getModel());
        return SUCCESS;
    }
    //分页查询定区
    @Action(value="fixedAreaAction_pageQuery")
    public String pageQuery() throws Exception{
        Pageable pageable = new PageRequest(page-1, rows);
        Page<FixedArea> page = fixedAreaService.pageQuery(pageable);
        page2json(page, new String[]{"subareas","couriers"});
        return NONE;
    }
    //*************定区关联客户信息查询****************
    //查询没有关联的客户信息
    @Action(value="fixedAreaAction_findMeiYouGuanLianDingQu")
    public String findMeiYouGuanLianDingQu() throws Exception{
        //List<Customer> list = fixedAreaService.findMeiYouGuanLianDingQu();
       // System.out.println("没有关联"+list);
        List<Customer> list = (List<Customer>) WebClient.create("http://localhost:8888/crm/webservice/customerService/findMeiYouGuanLianDingQu")
                //.accept(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);
        list2json(list, null);
        return NONE;
    }
    //查询已经关联的客户信息
    @Action(value="fixedAreaAction_findYiJingGuanLianDingQu")
    public String findYiJingGuanLianDingQu() throws Exception{
//        List<Customer> list = fixedAreaService.findYiJingGuanLianDingQu(getModel().getId());
//        System.out.println("已经关联"+list);
        List<Customer> list = (List<Customer>) WebClient.create("http://localhost:8888/crm/webservice/customerService/findYiJingGuanLianDingQu")
              .query("fixedAreaId", getModel().getId())
              .getCollection(Customer.class);
        list2json(list, null);
        return NONE;
    }
    
    //属性驱动获取，需要关联指定定区的客户id信息
    private List<Long> customerIds;
    public void setCustomerIds(List<Long> customerIds) {
        this.customerIds = customerIds;
    }
    //发起访问服务端crm的请求
    @Action(value="fixedAreaAction_assignCustomers2FixedArea",results={@Result(name="success",location="/pages/base/fixed_area.html",type="redirect")})
    public String assignCustomers2FixedArea(){
        if(customerIds == null){
            WebClient.create("http://localhost:8888/crm/webservice/customerService/assignCustomers2FixedArea")
                     .query("fixedAreaId", getModel().getId())
                     .put(null);
            return SUCCESS;
        }else{
            WebClient.create("http://localhost:8888/crm/webservice/customerService/assignCustomers2FixedArea")
            .query("fixedAreaId", getModel().getId())
            .query("customerIds", customerIds)
            .put(null);
            return SUCCESS;
        }
    }
    //使用属性驱动获取需要关联的快递员id和取派时间的id
    private Long courierId;
    private Long takeTimeId;
    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }
    public void setTakeTimeId(Long takeTimeId) {
        this.takeTimeId = takeTimeId;
    }
    //保存定区与快递员，取派时间的关联操作
    @Action(value="fixedAreaAction_associationCourierToFixedArea",results={@Result(name="success",location="/pages/base/fixed_area.html",type="redirect")})
    public String associationCourierToFixedArea(){
        //System.out.println("getModel().getId()="+getModel().getId()+"courierId="+courierId+"takeTimeId="+takeTimeId);
        fixedAreaService.associationCourierToFixedArea(getModel().getId(),courierId,takeTimeId);
        return SUCCESS;
    }
    
    //获取没有关联定区的分区
    @Action(value="fixedAreaAction_getMeiYouGuanLianDingQu")
    public String getMeiYouGuanLianDingQu() throws Exception{
        List<SubArea> list = fixedAreaService.getMeiYouGuanLianDingQu();
        list2json(list, new String[]{"subareas","couriers"});
        return NONE;
    }
   
    //获取已经关联定区的分区
   @Action(value="fixedAreaAction_getYiJingGuanLianDingQu")
    public String getYiJingGuanLianDingQu() throws Exception{
        List<SubArea> list = fixedAreaService.getYiJingGuanLianDingQu(getModel().getId());
        list2json(list, new String[]{"subareas","couriers"});
        return NONE;
    }
   
   //属性驱动获取页面传输的需要关联的分区id
   private List<Long> subareaIds;
   public void setSubareaIds(List<Long> subareaIds) {
       this.subareaIds = subareaIds;
   }
   //对需要关联的分区数据进行保存
   @Action(value="fixedAreaAction_associationSubareaToFixedArea",results={@Result(name="success",location="/pages/base/fixed_area.html",type="redirect")})
   public String associationSubareaToFixedArea(){
       fixedAreaService.associationSubareaToFixedArea(getModel().getId(),subareaIds);
       return SUCCESS;
   }
}
  
