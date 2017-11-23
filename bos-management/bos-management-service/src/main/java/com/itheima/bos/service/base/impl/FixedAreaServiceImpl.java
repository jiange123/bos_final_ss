package com.itheima.bos.service.base.impl;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.dao.base.FixedAreaRepository;
import com.itheima.bos.dao.base.SubareaRepository;
import com.itheima.bos.dao.base.TakeTimeRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.FixedAreaService;
import com.itheima.crm.domain.Customer;

/**  
 * ClassName:FixedAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 4, 2017 11:09:39 PM <br/>       
 */
@Transactional
@Service
public class FixedAreaServiceImpl implements FixedAreaService {

    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    @Autowired
    private CourierRepository courierRepository;
    @Autowired
    private TakeTimeRepository takeTimeRepository;
    @Autowired
    private SubareaRepository subareaRepository;
    @Override
    //保存定区
    public void save(FixedArea model) {
          fixedAreaRepository.save(model);
    }
    @Override
    //分页查询定区
    public Page<FixedArea> pageQuery(Pageable pageable) {
        return fixedAreaRepository.findAll(pageable);
    }
    @Override
    //保存定区与快递员，取派时间的操作
    public void associationCourierToFixedArea(Long id, Long courierId, Long takeTimeId) {
          //使用hibernate特性，获取定区，快递员，取派时间的持久化对象，产生关联，经过与快照区比对，自动更新数据库
        //获取定区的持久化对象
        FixedArea fixedArea = fixedAreaRepository.findOne(id);
        //获取快递员的持久化对象
        Courier courier = courierRepository.findOne(courierId);
        //获取取派时间的持久化对象
        TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
        //将定区与快递员产生关联
        fixedArea.getCouriers().add(courier);
        //将快递员与取派时间产生关联
        courier.setTakeTime(takeTime);
    }
    
    @Override
    //查询所有没有关联定区的分区
    public List<SubArea> getMeiYouGuanLianDingQu() {
        return subareaRepository.findByFixedAreaIdIsNull();
    }
    
    
    @Override
    //查询所有没有关联定区的分区数据
    public List<SubArea> getYiJingGuanLianDingQu(Long fixedAreaId) {
        return subareaRepository.findByFixedAreaId(fixedAreaId);
    }
    
    
    @Override
    public void associationSubareaToFixedArea(Long id, List<Long> subareaIds) {
        //将数据库中当前定区关联的分区信息清空
        subareaRepository.updateSubareaByFixedareaId(id);
        //遍历集合，将集合中的分区与定区重新进行关联
        if(subareaIds != null && subareaIds.size() > 0){
            for (Long subareaId : subareaIds) {
                subareaRepository.updateSubareaByFixedareaId(id, subareaId);
            }
        }
    }
}
  
