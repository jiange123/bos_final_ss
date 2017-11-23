package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.base.AreaSub;
import com.itheima.bos.domain.base.SubArea;

/**  
 * ClassName:SubareaService <br/>  
 * Function:  <br/>  
 * Date:     Nov 4, 2017 4:50:45 PM <br/>       
 */
public interface SubareaService {

    /**
     * 
     * save:. <br/>  
     *  
     * @param model 分区对象
     */
    void save(SubArea model);

    /**
     * 
     * pageQuery:. <br/>  
     *  
     * @param pageable  分页查询对象
     * @return
     */
    Page<SubArea> pageQuery(Pageable pageable);

    /**
     * 
     * findAll:. <br/>  
     *  
     * @return  返回所有分区
     */
    List<SubArea> findAll();

    /**
     * 
     * deleteById:. <br/>  
     *  
     * @param id  根据分区id删除分区
     */
    void deleteById(Long id);

    //条件查询分区
    Page<SubArea> pageQuery(Specification<SubArea> specification, Pageable pageable);
    
    List<SubArea> findByFixedId(Long fixedId);

    List<AreaSub> findToColumn();
    

}
  
