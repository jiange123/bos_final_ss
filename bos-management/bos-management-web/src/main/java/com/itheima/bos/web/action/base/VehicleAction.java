package com.itheima.bos.web.action.base;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
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

import com.itheima.bos.domain.base.Vehicle;
import com.itheima.bos.service.base.VehicleService;
import com.itheima.bos.web.action.CommonAction;

/**  
 * ClassName:VehicleAction <br/>  
 * Function:  <br/>  
 * Date:     2017年11月21日 下午8:10:36 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope
@Controller
public class VehicleAction extends CommonAction<Vehicle>{

    @Autowired
    private VehicleService vehicleService;
    
    @Action(value="vehicleAction_save",results={@Result(name="success",location="/pages/base/vehicle.html",type="redirect")})
    public String save(){
        vehicleService.save(getModel());
        return SUCCESS;
    }
    
  @Action("vehicleAction_pageQuery")
    public String pageQuery() throws Exception{
      
      Specification<Vehicle> specification = new Specification<Vehicle>() {

        @Override
        public Predicate toPredicate(Root<Vehicle> root, CriteriaQuery<?> query,
                CriteriaBuilder cb) {
              String driver = getModel().getDriver();
              String shipper = getModel().getShipper();
              String vehicleType = getModel().getVehicleType();
              Integer ton = getModel().getTon();
              List<Predicate> list = new ArrayList<>();
              if(StringUtils.isNotEmpty(driver)){
                  Predicate p1 = cb.equal(root.get("driver").as(String.class),driver);
                  list.add(p1);
              }
              if(StringUtils.isNotEmpty(shipper)){
                  Predicate p2 = cb.equal(root.get("shipper").as(String.class),shipper);
                  list.add(p2);
              }
              if(StringUtils.isNotEmpty(vehicleType)){
                  Predicate p3 = cb.equal(root.get("vehicleType").as(String.class),vehicleType);
                  list.add(p3);
              }
              if(ton != null&& ton != 0){
                  Predicate p4 = cb.equal(root.get("ton").as(Integer.class),ton);
                  list.add(p4);
              }
              
              if(list.size() == 0){
                  return null;
              }
              Predicate[] arr = new Predicate[list.size()];
              list.toArray(arr);
              return cb.and(arr);
        }};
      
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Vehicle> page = vehicleService.pageQuery(specification,pageable);
        page2json(page, null);
        return NONE;
    }
  
  @Action(value="vehicleAction_deleteById",results={@Result(name="success",location="vehicleAction_pageQuery",type="chain")})
    public String deleteById(){
        vehicleService.deleteById(getModel().getId());
        return SUCCESS;
    }
}
  
