package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.crm.domain.Customer;

/**  
 * ClassName:FixedAreaService <br/>  
 * Function:  <br/>  
 * Date:     Nov 4, 2017 11:09:08 PM <br/>       
 */
public interface FixedAreaService {

    /**
     * 保存定区
     * save:. <br/>  
     *  
     * @param model
     */
    void save(FixedArea model);

    /**
     * 分页查询定区
     * pageQuery:. <br/>  
     *  
     * @param pageable
     * @return
     */
    Page<FixedArea> pageQuery(Pageable pageable);

    /**
     * 保存定区与快递员，取派时间的操作
     * associationCourierToFixedArea:. <br/>  
     *  
     * @param id
     * @param courierId
     * @param takeTimeId
     */
    void associationCourierToFixedArea(Long id, Long courierId, Long takeTimeId);

    /**
     * 查询所有没有关联的分区
     * getMeiYouGuanLianDingQu:. <br/>  
     *  
     * @return
     */
    List<SubArea> getMeiYouGuanLianDingQu();

    /**
     * 查询已经关联定区的分区数据
     * getYiJingGuanLianDingQu:. <br/>  
     *  
     * @param id
     * @return
     */
    List<SubArea> getYiJingGuanLianDingQu(Long fixedAreaId);

    /**
     * 将分区和定区进行关联
     * associationSubareaToFixedArea:. <br/>  
     *  
     * @param id
     * @param subareaIds
     */
    void associationSubareaToFixedArea(Long id, List<Long> subareaIds);
}
  
