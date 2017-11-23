package com.itheima.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.StandardRepository;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.StandardService;

/**  
 * ClassName:StandardServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Oct 31, 2017 9:13:46 PM <br/>       
 */
@Transactional
@Service
public class StandardServiceImpl implements StandardService {

    @Autowired
    private StandardRepository standardRepository;
    @Override
    //保存收件标准
    public void save(Standard standard) {
        standardRepository.save(standard);
    }
    @Override
    //分页查询收件标准
    public Page<Standard> pageQuery(Pageable pageable) {
        return standardRepository.findAll(pageable);
    }
    @Override
    //根据id删除收件标准
    public void deleteById(Long id) {
          standardRepository.delete(id);
    }

}
  
