package com.itheima.bos.service.take_rec.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.take_rec.PromotionRepository;
import com.itheima.bos.domain.take_rec.Promotion;
import com.itheima.bos.service.take_rec.PromotionService;

/**  
 * ClassName:PromotionServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 19, 2017 4:55:00 PM <br/>       
 */
@Transactional
@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;
    
    @Override
    public void save(Promotion model) {
        promotionRepository.save(model);
    }

    @Override
    public Page<Promotion> pageQuery(Pageable pageable) {
        return promotionRepository.findAll(pageable);
    }

}
  
