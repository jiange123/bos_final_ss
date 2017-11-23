package com.itheima.bos.dao.base;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.hp.hpl.sparta.xpath.TrueExpr;
import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:StandardRepository <br/>  
 * Function:  <br/>  
 * Date:     Oct 31, 2017 9:58:07 AM <br/>    
 * 参数1：javabean的类型
 * 参数2：参数1的主键类型   
 */
public interface StandardRepository extends JpaRepository<Standard, Long> {

    /**
     * 根据姓名查找
     */
    Standard findByName(String name);
    
    /**
     * 自定义多条件查询,使用标准规范
     */
    Standard findByNameAndMaxLength(String name,Integer maxLength);
    
    /**
     * 自定义多条件查询，未使用标准规范
     */
    @Query(value="from Standard where name=? and maxLength=?")
    Standard findByMMP(String name,Integer maxLength);
    
    /**
     * 自定义查询，未使用标准规范,使用原生的sql
     */
    @Query(value="SELECT * FROM T_STANDARD WHERE C_NAME=?",nativeQuery=true)
    Standard findByNNP(String name);
    
    /**
     * 模糊查询
     */
    List<Standard> findByNameLike(String name);
    
    /**
     * 自定义删除
     */
    @Transactional
    @Modifying
    @Query(value="delete from Standard where name=?")
    void deleteByName(String name);
    
    /**
     * 自定义更新
     */
    @Transactional
    @Query(value="update Standard set name='街坊老大爷' where name = ?")
    @Modifying
    void updateByName(String name);
}
  
