package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Constant;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.CourierService;
import com.itheima.bos.web.action.CommonAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:CourierAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 1, 2017 10:32:38 AM <br/>       
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class CourierAction extends CommonAction<Courier> {
    /**  
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).  
     * @since JDK 1.6  
     */
    private static final long serialVersionUID = 1L;
    
    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }
    
    @Autowired
    private CourierService courierService;
    
    
    //异步获取收件标准的值
    @Action(value="courierAction_findStandardName")
    public String findStandardName() throws Exception{
        List<Standard> list = courierService.findAll();
        //将list数据转换为json
        String json = JSONArray.fromObject(list).toString();
        //创建流将json写会前端
        HttpServletResponse response = ServletActionContext.getResponse();
        //设置编码集
        response.setContentType("application/json;charset=UTF-8");
        //写回数据
        response.getWriter().write(json);
        return NONE;
    }
    
    
    //获取取派员信息列表
    @Action(value="courierAction_pageQuery",results={@Result(type="redirect")})
    public String pageQuery() throws Exception{
        //***********对查询条件进行判断**************
     
        //System.out.println(courierNum+"  "+standard+"  "+company+"  "+type);
        //构造查询条件
        Specification<Courier> specification = new Specification<Courier>() {

            @Override
            //参数1：root对象，可以理解为泛型对象
            //参数2：一般用不到
            //参数3：cb 构造查询条件
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                String courierNum = getModel().getCourierNum();
                Standard standard = getModel().getStandard();
                String company = getModel().getCompany();
                String type = getModel().getType();
                //创建list集合，用来统一封装查询条件
                List<Predicate> list = new ArrayList<>();
                //构造工号查询的条件(等值)
                if(StringUtils.isNotEmpty(courierNum)){
                    Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courierNum);
                    list.add(p1);
                }
                //构造所属单位的条件（模糊）
                if(StringUtils.isNotEmpty(company)){
                    Predicate p2 = cb.like(root.get("company").as(String.class), "%"+company+"%");
                    list.add(p2);
                }
                //构造取派员类型条件（等值）
                if(StringUtils.isNotEmpty(type)){
                    Predicate p3 = cb.equal(root.get("type").as(String.class), type);
                    list.add(p3);
                }
                //构造收派标准的条件（等值）
                if(standard != null && StringUtils.isNotEmpty(standard.getName())){
                    //standard为关联对象首先获取关联对象
                    Join<Object, Object> join = root.join("standard");
                    Predicate p4 = cb.equal(join.get("name").as(Standard.class), standard.getName());
                    list.add(p4);
                }
                //对总得封装条件进行判断
                if(list.size() == 0){
                    
                    return null;
                }
                //如果查询条件不为空，将条件转换为数组
                Predicate[] arr = new Predicate[list.size()];
                //将集合转为数组
                list.toArray(arr);
                //将条件对象返回
                return cb.and(arr);
            }};
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Courier> page = courierService.pageQuery(specification,pageable);
        page2json(page, new String[]{"fixedAreas"});
        return NONE;
    }
    
    
    @Action(value="courierAction_save",results={@Result(name="success",location="/pages/base/courier.html",type="redirect")})
    public String save(){
        courierService.save(getModel());
        return SUCCESS;
    }
    
    
    //根据id作废快递员信息
    /*  @Action(value="courierAction_deleteById",results={@Result(name="success",location="courierAction_pageQuery",type="chain")})
    public String deleteById(){
        //如果用户选择将快递员信息作废，将deltag置为0
        //通过id获取选中的对象
        Long id = getModel().getId();
        System.out.println("id="+id);
        Courier courier = courierService.findById(id);
        //获取该用户的deltag值
        Character deltag = courier.getDeltag();
        System.out.println(deltag);
        if(deltag == '1'){
            courier.setDeltag(Constant.ACTIVE);
        }else{
            courier.setDeltag(Constant.NOT_ACTIVE);
        }
        //设置OK后将跟新数据库
        courierService.save(courier);
        return SUCCESS;
         }*/
    
    @Action(value = "courierAction_batchDel", results = {
            @Result(name = "success", location = "/pages/base/courier.html", type = "redirect")})
    public String batchDel() {
        System.out.println(ids);
        courierService.batchDel(ids);
        return SUCCESS;
    }
    
    
    //定区关联快递员，查询所有的快递员获取name属性值
    @Action(value="courierAction_findAllCustomer")
    public String findAllCustomer() throws Exception{
        List<Courier> list = courierService.findAllCustomer();
        list2json(list, new String[]{"fixedAreas"});
        return NONE;
    }
    
    @Action(value = "courierAction_restoreCourier", results = {
           @Result(name = "success", location = "/pages/base/courier.html", type = "redirect")})
    public String restoreCourier() {
        System.out.println(ids);
        courierService.restoreCourier(ids);
        return SUCCESS;
    }
    
    //get courier deltag by id
    @Action(value="courierAction_findById")
    public String findById() throws Exception{
        Courier courier = courierService.findById(getModel().getId());
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"fixedAreas"});
        String json = JSONObject.fromObject(courier,config).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
        return NONE;
    }
    
    private String fixedId;
    public void setFixedAreaId(String fixedId) {
        this.fixedId = fixedId;
    }
    
    @Action(value="courierAction_findByFixedAreaId")
    public String findByFixedAreaId() throws Exception{
        List<Courier> list = courierService.findByFixedAreaId(fixedId);
        list2json(list, new String[]{"fixedAreas"});
        return NONE;
    }
    
}
  
