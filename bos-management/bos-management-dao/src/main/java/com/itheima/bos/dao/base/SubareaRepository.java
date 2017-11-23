package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.crm.domain.Customer;

/**  
 * ClassName:SubareaRepository <br/>  
 * Function:  <br/>  
 * Date:     Nov 4, 2017 4:59:17 PM <br/>       
 */
public interface SubareaRepository extends JpaRepository<SubArea, Long>,JpaSpecificationExecutor<SubArea> {

    /**
     * 自定义查询没有关联定区的分区
     * findSubareaByFixedAreaIsNull:. <br/>  
     *  
     * @return
     */
    List<SubArea> findByFixedAreaIdIsNull();
    
    
    /**
     * 查询所有已经关联定区的分区数据
     * findSubareaByFixedAreaId:. <br/>  
     *  
     * @param id
     * @return
     */
    List<SubArea> findByFixedAreaId(Long fixedAreaId);
    
    
    /**
     * 将已经关联的解绑
     * updateSubareaByFixedareaId:. <br/>  
     *
     */
    @Modifying
    @Query("update SubArea set fixedArea.id = null where fixedArea.id = ?")
    void updateSubareaByFixedareaId(Long id);
    
    
    /**
     * 将集合中的分区与定区进行关联
     * updateSubareaByFixedareaId:. <br/>  
     *  
     * @param fixedAreaId
     * @param subareaId
     */
    @Modifying
    @Query("update SubArea set fixedArea.id = ? where id = ?")
    void updateSubareaByFixedareaId(Long fixedAreaId,Long subareaId);


    @Query("from SubArea where fixedArea.id = ?")
    List<SubArea> xxxxxx(Long fixedId);
}
  
