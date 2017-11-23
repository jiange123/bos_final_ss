package com.itheima.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.VehicleRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Vehicle;
import com.itheima.bos.service.base.VehicleService;

/**  
 * ClassName:VehicleServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2017年11月21日 下午8:22:46 <br/>       
 */
@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
    
    @Override
    public void save(Vehicle model) {
        vehicleRepository.save(model);
    }

    @Override
    public Page<Vehicle> pageQuery(Specification<Vehicle> specification,Pageable pageable) {
      
        return vehicleRepository.findAll(specification,pageable);
    }

    @Override
    public void deleteById(Long id) {
        vehicleRepository.delete(id);
    }

}
  
