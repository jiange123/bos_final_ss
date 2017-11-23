package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.Area;

/**  
 * ClassName:AreaRepository <br/>  
 * Function:  <br/>  
 * Date:     Nov 3, 2017 1:17:04 AM <br/>       
 */
public interface AreaRepository extends JpaRepository<Area, Long> {

    /**
     * 
     * 自定义模糊查询
     */
    @Query("from Area where province like ?1 or city like ?1 or district like ?1 or postcode like ?1 or citycode like ?1 or shortcode like ?1")
    List<Area> findByQ(String q);

    Area findByProvinceAndCityAndDistrict(String province, String city, String district);
    
    @Query("select a.district,count(*) from Area a inner join a.subareas s group by a.district")
    List<Object[]> findToColumn();
    
    //区域
    @Query(value="select t.c_province,count(*) from T_AREA t group by t.c_province",
            nativeQuery=true)
    List<Object[]> findBProvinceAndDistrict();
    
    //定区
    @Query(value="select distinct f.c_id from T_SUB_AREA s inner join T_AREA a on s.c_area_id=a.c_id inner join T_FIXED_AREA f on s.c_fixedarea_id=f.c_id where a.c_province=?",
            nativeQuery=true)
    List<Long> findFixedareaByProvince(String province);
    
    //分区
    @Query(value="select count(*) from T_SUB_AREA s inner join T_AREA a on s.c_area_id=a.c_id where a.c_province=?",
            nativeQuery=true)
    Long findSubareaByProvince(String province);
    
    //快递员
    @Query(value="select distinct o.c_id from T_COURIER o inner join t_fixedarea_courier i on o.c_id=i.c_courier_id inner join T_FIXED_AREA f on f.c_id=i.c_fixed_area_id inner join T_SUB_AREA s on s.c_fixedarea_id=f.c_id inner join T_AREA a on s.c_area_id=a.c_id where a.c_province=?",
            nativeQuery=true)
    List<Object> findCourierByProvince(String province);
    
}
  
