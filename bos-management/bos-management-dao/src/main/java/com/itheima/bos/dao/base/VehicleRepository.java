package com.itheima.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itheima.bos.domain.base.Vehicle;

/**  
 * ClassName:VehicleRepository <br/>  
 * Function:  <br/>  
 * Date:     2017年11月21日 下午8:23:28 <br/>       
 */
public interface VehicleRepository extends JpaRepository<Vehicle, Long>,JpaSpecificationExecutor<Vehicle>{

}
  
