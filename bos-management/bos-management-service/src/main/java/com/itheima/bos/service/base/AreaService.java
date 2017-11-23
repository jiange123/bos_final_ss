package com.itheima.bos.service.base;

import java.util.List;

import javax.sound.midi.VoiceStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.Area;

/**  
 * ClassName:AreaService <br/>  
 * Function:  <br/>  
 * Date:     Nov 3, 2017 1:13:28 AM <br/>       
 */
public interface AreaService {

    /**
     * save:. <br/>  
     *  
     * @param list： 区域数据
     */
    void save(List<Area> list);
    

    /**
     * pageQuery:. <br/>  
     *  
     * @param pageable：分页查询条件
     * @return 
     */
    Page<Area> pageQuery(Pageable pageable);
    

    /**
     * findAll:. <br/>  
     *  
     * @return：所有分区数据
     */
    List<Area> findAll();
    

    /**
     * findAllByQ:. <br/>  
     *  
     * @param q：模糊查询关键字
     * @return
     */
    List<Area> findAllByQ(String q);
    
    
    /**
     * save:. <br/>  
     *  
     * @param area：保存区域数据
     */
    void save(Area area);


    /**
     * deleteById:. <br/>  
     *  
     * @param id :需要删除的区域的id
     */
    void deleteById(Long id);


    List<Object> doCharts();

}
  
