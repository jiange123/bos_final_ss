package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.TakeTime;

/**  
 * ClassName:TakeTimeService <br/>  
 * Function:  <br/>  
 * Date:     Nov 7, 2017 10:09:57 AM <br/>       
 */
public interface TakeTimeService {

    /**
     * 查询所有收派快件时间
     * findAll:. <br/>  
     *  
     * @return
     */
    List<TakeTime> findAll();

    /**
     * 保存取派时间
     * save:. <br/>  
     *  
     * @param model
     */
    void save(TakeTime model);

    /**
     * 分页查询取派时间
     * pageQuery:. <br/>  
     *  
     * @param pageable
     * @return
     */
    Page<TakeTime> pageQuery(Pageable pageable);
}
  
