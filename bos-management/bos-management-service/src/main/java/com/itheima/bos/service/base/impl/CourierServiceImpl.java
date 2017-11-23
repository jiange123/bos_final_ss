package com.itheima.bos.service.base.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.dao.base.StandardRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.CourierService;

/**  
 * ClassName:CourierServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 1, 2017 10:38:31 AM <br/>       
 */
@Service
@Transactional
public class CourierServiceImpl implements CourierService {
    @Autowired
    private StandardRepository standardRepository;
    @Autowired
    private CourierRepository courierRepository;
    @Override
    public List<Standard> findAll() {
        return standardRepository.findAll();
    }
    @Override
    //分页查询取派员列表
    public Page<Courier> pageQuery(Pageable pageable) {
        return courierRepository.findAll(pageable);
    }
    @Override
    //保存快递员设置
    public void save(Courier courier) {
          courierRepository.save(courier);
    }
    @Override
    //根据id查询快递员信息
    public Courier findById(Long id) {
        return courierRepository.findOne(id);
    }
    @Override
    //条件查询取派员
    public Page<Courier> pageQuery(Specification<Courier> specification, Pageable pageable) {
        return courierRepository.findAll(specification, pageable);
    }
    @Override
    //查询所有取派员
    public List<Courier> findAllCustomer() {
        return courierRepository.findByDelTag();
    }
    
    @Override
    public void batchDel(String ids) {
        if(StringUtils.isNotEmpty(ids)){
            String[] split = ids.split(",");
            for (String id : split) {
                courierRepository.updateDelTagById(Long.parseLong(id));
            }
        }
        
    }
    @Override
    public void restoreCourier(String ids) {
          
        if(StringUtils.isNotEmpty(ids)){
            String[] split = ids.split(",");
            for (String id : split) {
                courierRepository.restoreById(Long.parseLong(id));
            }
        }
    }
    @Override
    public List<Courier> findByFixedAreaId(String fixedAreaId) {
         Long fixedId =  Long.parseLong(fixedAreaId);
        return courierRepository.findFixedAreas(fixedId);
    }
    
}
  
