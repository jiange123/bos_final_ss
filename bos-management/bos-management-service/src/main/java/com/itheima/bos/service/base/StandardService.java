package com.itheima.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:StandardService <br/>  
 * Function:  <br/>  
 * Date:     Oct 31, 2017 9:11:13 PM <br/>       
 */
public interface StandardService {

    /**
     * 保存收件标准
     * save:. <br/>  
     *  
     * @param standard
     */
    void save(Standard standard);

    /**
     * 分页查询收件标准
     * pageQuery:. <br/>  
     *  
     * @param pageable
     * @return
     */
    Page<Standard> pageQuery(Pageable pageable);

    /**
     * 根据id删除收件标准
     * deleteById:. <br/>  
     *  
     * @param id
     */
    void deleteById(Long id);


}
  
