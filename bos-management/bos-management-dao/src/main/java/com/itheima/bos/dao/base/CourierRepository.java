package com.itheima.bos.dao.base;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.Courier;

/**  
 * ClassName:CourierRepository <br/>  
 * Function:  <br/>  
 * Date:     Nov 1, 2017 10:50:30 AM <br/>  
 * 参数1：Javabean的类型
 * 参数2：  参数1的主键类型
 */
public interface CourierRepository extends JpaRepository<Courier, Long>,JpaSpecificationExecutor<Courier> {

    //自定义查询deltag标志为0的快递员
    @Query("FROM Courier WHERE deltag = 0")
    List<Courier> findByDelTag();
    
    @Modifying
    @Query("update Courier set deltag = '1' where id = ?")
    void updateDelTagById(long id);
    
    @Modifying
    @Query("update Courier set deltag = '0' where id = ?")
    void restoreById(long id);
    
    @Query("select c from Courier c inner join c.fixedAreas f where f.id = ?")
    List<Courier> findFixedAreas(Long fixedAreaId);
}  
