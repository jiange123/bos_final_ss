package com.itheima.bos.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.AreaRepository;
import com.itheima.bos.dao.base.SubareaRepository;
import com.itheima.bos.domain.base.AreaSub;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.SubareaService;

/**  
 * ClassName:SubareaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 4, 2017 4:51:23 PM <br/>       
 */
@Service
@Transactional
public class SubareaServiceImpl implements SubareaService {

    @Autowired
    private SubareaRepository subareaRepository;
    
    @Autowired
    private AreaRepository areaRepository;
    
    @Override
    public void save(SubArea model) {
        subareaRepository.save(model);
    }
    
    
    @Override
    //分页查询
    public Page<SubArea> pageQuery(Pageable pageable) {
        return subareaRepository.findAll(pageable);
    }
    
    
    @Override
    //查询所有分区
    public List<SubArea> findAll() {
        return subareaRepository.findAll();
    }
    
    @Override
    public List<SubArea> findByFixedId(Long fixedId) {
        return subareaRepository.xxxxxx(fixedId);
    }
    
    @Override
    public List<AreaSub> findToColumn() {
        List<Object[]> listoo= areaRepository.findToColumn(); 
        List<AreaSub> list = new ArrayList<>();
        for (Object[] objects : listoo) {
            String s=(String) objects[0];
            Long i=(Long) objects[1];
            AreaSub areaSub = new AreaSub(s, new int[]{i.intValue()});
            list.add(areaSub);
        }
        return list;
    }
    
    
    @Override
    //根据分区id删除分区
    public void deleteById(Long id) {
        subareaRepository.delete(id);
    }


    @Override
    //条件查询分区
    public Page<SubArea> pageQuery(Specification<SubArea> specification, Pageable pageable) {
        return subareaRepository.findAll(specification, pageable);
    }
    

}
  
