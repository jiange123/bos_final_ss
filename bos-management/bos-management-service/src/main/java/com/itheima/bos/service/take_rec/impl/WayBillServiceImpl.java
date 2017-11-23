package com.itheima.bos.service.take_rec.impl;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.take_rec.WayBillRepository;
import com.itheima.bos.domain.take_rec.WayBill;
import com.itheima.bos.service.take_rec.WayBillService;

/**  
 * ClassName:WayBillServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 13, 2017 6:46:33 PM <br/>       
 */
@Service
@Transactional
public class WayBillServiceImpl implements WayBillService {

    @Autowired
    private WayBillRepository wayBillRepository;
    @Override
    //保存运单
    public void save(WayBill wayBill) {
        wayBillRepository.save(wayBill);
    }
    
    @Override
    //分页查询
    @RequiresPermissions("waybill")
    public Page<WayBill> pageQuery(Pageable pageable) {
        return wayBillRepository.findAll(pageable);
    }

	@Override
	public void save(List<WayBill> list) {
		wayBillRepository.save(list);
	}

}
  
