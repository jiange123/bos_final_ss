package com.itheima.bos.web.action.take_delivery;


import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.take_rec.Order;
import com.itheima.bos.service.take_rec.OrderService;
import com.itheima.bos.web.action.CommonAction;
import com.itheima.crm.domain.Customer;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:OrderAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 11, 2017 11:34:27 PM <br/>       
 */
@SuppressWarnings("all")
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class OrderAction extends CommonAction<Order> {

    /**  
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).  
     * @since JDK 1.6  
     */
    private static final long serialVersionUID = 1L;
    private Order order;
    @Override
    public Order getModel() {
        if(order == null){
            order = new Order();
        }
        return order;
    }
    
    @Autowired
    private OrderService OrderService;
    
    @Action(value="orderAction_findCustomer")
    public String findCustomer() throws Exception{
       List<Customer> list = (List<Customer>) WebClient
        		.create("http://localhost:8888/crm/webservice/customerService/customerFindAll")
                .getCollection(Customer.class);
       list2json(list, new String[] {""});
       return NONE;
    }
    
    
    //属性驱动获取寄件人和收件人区域信息，页面传递过来的的格式：安徽省/芜湖市/弋江区
    private String sendAreaInfo;      
    private String recAreaInfo;
    public void setSendAreaInfo(String sendAreaInfo) {
        this.sendAreaInfo = sendAreaInfo;
    }
    public void setRecAreaInfo(String recAreaInfo) {
        this.recAreaInfo = recAreaInfo;
    }
    @Action(value="orderAction_save",results={@Result(name="success",location="/pages/take_delivery/successOrder.html",type="redirect")})
    public String save(){
        //封装寄件人区域信息
        if(StringUtils.isNotEmpty(sendAreaInfo)){
            String[] split = sendAreaInfo.split("/");
            String province = split[0];
            String city = split[1];
            String district = split[2];
            //去掉省市区三个字
            province = province.substring(0, province.length()-1);
            city = city.substring(0, city.length()-1);
            district = district.substring(0, district.length()-1);
            //封装
            Area sendArea = new Area();
            sendArea.setProvince(province);
            sendArea.setCity(city);
            sendArea.setDistrict(district);
            order.setSendArea(sendArea);
        }
        //封装收件人区域信息
        if(StringUtils.isNotEmpty(recAreaInfo)){
            String[] split = recAreaInfo.split("/");
            String province = split[0];
            String city = split[1];
            String district = split[2];
            //去掉省市区三个字
            province = province.substring(0, province.length()-1);
            city = city.substring(0, city.length()-1);
            district = district.substring(0, district.length()-1);
            //封装
            Area recArea = new Area();
            recArea.setProvince(province);
            recArea.setCity(city);
            recArea.setDistrict(district);
            order.setRecArea(recArea);
        }
        OrderService.save(order);
        return SUCCESS;
    }

}
  
