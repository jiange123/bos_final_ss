package com.itheima.bos.service.take_rec;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.take_rec.WayBill;

/**  
 * ClassName:WayBillService <br/>  
 * Function:  <br/>  
 * Date:     Nov 13, 2017 6:45:23 PM <br/>       
 */
public interface WayBillService {
    /**
     * 快速保存运单
     * save:. <br/>  
     *  
     * @param wayBill
     */
    void save(WayBill wayBill);

    /**
     * 运单的分页查询
     * pageQuery:. <br/>  
     *  
     * @param pageable
     * @return
     */
    Page<WayBill> pageQuery(Pageable pageable);

	/**
	 * 批量导入运单
	 * @param list
	 */
	void save(List<WayBill> list);
    
   
}
  
