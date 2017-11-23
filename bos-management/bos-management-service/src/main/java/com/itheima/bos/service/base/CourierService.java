package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     Nov 1, 2017 10:38:04 AM <br/>       
 */
public interface CourierService {

    /**
     * 查询所有收派标准
     * findAll:. <br/>  
     *  
     * @return
     */
    List<Standard> findAll();

    /**
     * 分页查询快递员
     * pageQuery:. <br/>  
     *  
     * @param pageable
     * @return
     */
    Page<Courier> pageQuery(Pageable pageable);

    /**
     * 保存快递员设置
     * save:. <br/>  
     * @param courier 
     *
     */
    void save(Courier courier);

    /**
     * 根据id查询快递员信息
     * deleteById:. <br/>  
     *  
     * @param id
     */
    Courier findById(Long id);

    /**
     * 条件查询取派员
     * pageQuery:. <br/>  
     *  
     * @param specification
     * @param pageable
     * @return
     */
    Page<Courier> pageQuery(Specification<Courier> specification, Pageable pageable);

    /**
     * 定区关联快递员，查询所有快递员
     * findAllCustomer:. <br/>  
     *  
     * @return
     */
    List<Courier> findAllCustomer();
    
    void batchDel(String ids);
    
    void restoreCourier(String ids);

    List<Courier> findByFixedAreaId(String fixedAreaId);

}
  
