package com.itheima.bos.service.base.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.AreaRepository;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Chart;
import com.itheima.bos.domain.base.Chart2;
import com.itheima.bos.domain.base.District;
import com.itheima.bos.service.base.AreaService;
import com.itheima.crm.domain.Customer;

/**  
 * ClassName:AreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 3, 2017 1:13:56 AM <br/>       
 */
@Transactional
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaRepository areaRepository;
    
    
    @Override
    //保存区域数据
    public void save(List<Area> list) {
        areaRepository.save(list);
    }
    
    
    @Override
    //分页查询区域数据
    public Page<Area> pageQuery(Pageable pageable) {
        return areaRepository.findAll(pageable);
    }
    
    
    @Override
    //查询所有分区数据
    public List<Area> findAll() {
        return areaRepository.findAll();
    }
    
    
    @Override
    //模糊查询
    public List<Area> findAllByQ(String q) {
        //将q转换为大写
        q = q.toUpperCase();
        return areaRepository.findByQ("%"+q+"%");
        
    }
    
    
    @Override
    public void save(Area area) {
        areaRepository.save(area);
    }


    @Override
    public void deleteById(Long id) {
        areaRepository.delete(id);
    }


    @Override
    public List<Object> doCharts() {
        //获得所以省和区域数
        List<Object> list = new ArrayList<>();
        List<Object[]> list1=areaRepository.findBProvinceAndDistrict();
        Object[] object = new Object[list1.size()];
        int i=0;
        for (Object[] objects : list1) {
            String province=(String) objects[0];//省
            BigDecimal dcount=(BigDecimal) objects[1];//区域
            Long subarea = areaRepository.findSubareaByProvince(province);//分区
            List<Long> fixedarea = areaRepository.findFixedareaByProvince(province);//定区    
            List<Object> courier = areaRepository.findCourierByProvince(province);//快递员   
            //客户
            int customer=WebClient.create("http://localhost:8888/crm/webservice/customerService/findCustomerByFixedareaid")
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .query("fixedarea", fixedarea)
                .get(Integer.class);
            System.out.println("customer"+customer);
            District district = new District(dcount.intValue(), province);
            object[i]=district;
            //分区，定去，快递员，客户
            Chart chart = new Chart(province, new int[]{subarea.intValue(), fixedarea.size(), courier.size(), customer});
            list.add(chart);
            i++;
        }
        Chart2 chart2 = new Chart2(object);
        list.add(chart2);
        return list;
    }

}
  
