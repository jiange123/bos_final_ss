package com.itheima.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.TakeTimeRepository;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.TakeTimeService;

/**  
 * ClassName:TakeTimeServiecImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 7, 2017 10:11:32 AM <br/>       
 */
@Service
@Transactional
public class TakeTimeServiecImpl implements TakeTimeService{

    @Autowired
    private TakeTimeRepository takeTimeRepository;
    @Override
    public List<TakeTime> findAll() {
        return takeTimeRepository.findAll();
    }
    @Override
    public void save(TakeTime model) {
        takeTimeRepository.save(model);
    }
    @Override
    public Page<TakeTime> pageQuery(Pageable pageable) {
          
        return takeTimeRepository.findAll(pageable);
    }

}
  
