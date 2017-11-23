package com.itheima.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Vehicle;

/**  
 * ClassName:VehicleService <br/>  
 * Function:  <br/>  
 * Date:     2017年11月21日 下午8:22:01 <br/>       
 */
public interface VehicleService {

    void save(Vehicle model);

    

    void deleteById(Long id);

    Page<Vehicle> pageQuery(Specification<Vehicle> specification, Pageable pageable);

}
  
