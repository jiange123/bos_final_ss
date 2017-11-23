package com.itheima.bos.service.take_rec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.take_rec.Promotion;

/**  
 * ClassName:PromotionService <br/>  
 * Function:  <br/>  
 * Date:     Nov 19, 2017 4:54:19 PM <br/>       
 */
public interface PromotionService {

    /**
     * 保存宣传任务
     * save:. <br/>  
     *  
     * @param model
     */
    void save(Promotion model);

    /**
     * 分页查询宣传任务
     * pageQuery:. <br/>  
     *  
     * @param pageable
     * @return
     */
    Page<Promotion> pageQuery(Pageable pageable);

}
  
