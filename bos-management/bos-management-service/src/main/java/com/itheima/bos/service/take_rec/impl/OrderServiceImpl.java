package com.itheima.bos.service.take_rec.impl;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.AreaRepository;
import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.dao.base.FixedAreaRepository;
import com.itheima.bos.dao.take_rec.OrderRepository;
import com.itheima.bos.dao.take_rec.WorkBillRepository;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.domain.take_rec.Order;
import com.itheima.bos.domain.take_rec.WorkBill;
import com.itheima.bos.service.take_rec.OrderService;

/**
 * ClassName:OrderServiceImpl <br/>
 * Function: <br/>
 * Date: Nov 12, 2017 12:48:28 AM <br/>
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private FixedAreaRepository fixedAreaRepository;

    @Autowired
    private WorkBillRepository workBillRepository;

    @Override
    public void save(Order order) {
        System.out.println("进入保存订单方法");
        // 將收件區域和發件區域轉成持久化對象
        Area sendArea = order.getSendArea();
        Area recArea = order.getRecArea();
        if (sendArea != null) {
            Area sendAreaDB = areaRepository.findByProvinceAndCityAndDistrict(
                    sendArea.getProvince(), sendArea.getCity(), sendArea.getDistrict());
            System.out.println("查询出来的寄件区域"+sendAreaDB);
            order.setSendArea(sendAreaDB);
        }
        if (recArea != null) {
            Area recAreaDB = areaRepository.findByProvinceAndCityAndDistrict(recArea.getProvince(),
                    recArea.getCity(), recArea.getDistrict());
            System.out.println("查询出来的收件区域"+recAreaDB);
            order.setRecArea(recAreaDB);
        }
        order.setOrderNum(RandomStringUtils.randomNumeric(32));
        order.setOrderTime(new Date());
        orderRepository.save(order);
        System.out.println("订单已经保存");

        // 根据发件人详细地址实现自动分单
        // 1.获取发件人详细地址
        String sendAddress = order.getSendAddress();
        // 2.查询crm获取发件人所在定区ID
        if (sendAddress != null) {
            Long fixedAreaId = WebClient
                    .create("http://localhost:8888/crm/webservice/customerService/findFixedAreaIdByAddress")
                    .type(MediaType.APPLICATION_JSON).query("address", sendAddress).get(Long.class);
            // 2.根据定区ID获取定区
            if (fixedAreaId != null && fixedAreaId != 0) {
                FixedArea fixedArea = fixedAreaRepository.findOne(fixedAreaId);
                // 3.根据定区查询该定区所有的快递员
                Set<Courier> couriers = fixedArea.getCouriers();
                // 4.遍历获取符合条件的快递员
                Iterator<Courier> iterator = couriers.iterator();
                Courier courier = iterator.next();
                // 为该订单设定符合的快递员
                order.setCourier(courier);

                // 生成工单
                WorkBill workBill = new WorkBill();
                workBill.setAttachbilltimes(0); // 追单次数
                workBill.setBuildtime(new Date()); // 生成工单时间
                workBill.setCourier(courier); // 指定快递员
                workBill.setOrder(order); // 该工单所属的订单
                workBill.setPickstate("已取件"); // 设置取件状态
                workBill.setRemark("贵重物品，有保单");
                workBill.setSmsNumber("9527"); // 短信序号
                workBill.setType("新"); // 工单类型 新,追,销

                // 完成保存
                workBillRepository.save(workBill);
                System.out.println("选中详细地址进行保存订单"+workBill);
                // 给订单设定分单类型
                order.setOrderType("自动分单");
                order.setCourier(courier);
                order.setStatus("待取件");
                return;
            }
        }
        
        //根据分区关键字和辅助关键字完成自动分单
        //获取发件人输入的详细地址,包含关键字或辅助关键字
        String address = order.getSendAddress();
        //获取发件人区域信息
        Area sendAreaDB = order.getSendArea();
        //通过区域获取分区
        if(sendAreaDB!=null){
            Set<SubArea> subareas = sendAreaDB.getSubareas();
            for (SubArea subArea : subareas) {
                //获取分区中的关键字和辅助关键字
                String keyWords = subArea.getKeyWords();
                String assistKeyWords = subArea.getAssistKeyWords();
                if(address.contains(keyWords) || address.contains(assistKeyWords)){
                  //通过分区获取分区所属的定区
                    FixedArea fixedArea = subArea.getFixedArea();
                    //获取定区关联的所有快递员
                    if(fixedArea!=null){
                        Set<Courier> couriers = fixedArea.getCouriers();
                        Iterator<Courier> iterator = couriers.iterator();
                        Courier courier = iterator.next();
                        //绑定快递员
                        order.setCourier(courier);
                        
                     // 生成工单
                        WorkBill workBill = new WorkBill();
                        workBill.setAttachbilltimes(0); // 追单次数
                        workBill.setBuildtime(new Date()); // 生成工单时间
                        workBill.setCourier(courier); // 指定快递员
                        workBill.setOrder(order); // 该工单所属的订单
                        workBill.setPickstate("已取件"); // 设置取件状态
                        workBill.setRemark("贵重物品，有保单");
                        workBill.setSmsNumber("9527"); // 短信序号
                        workBill.setType("新"); // 工单类型 新,追,销
                        
                     // 完成保存
                        workBillRepository.save(workBill);
                        System.out.println("选择关键字和辅助关键字保存订单=="+workBill);
                        // 给订单设定分单类型
                        order.setOrderType("自动分单");
                        order.setCourier(courier);
                        order.setStatus("待取件");
                        return;
                    }
                }
            }
        }
        order.setOrderType("人工分单");
        System.out.println("选择人工分单方式");
    }
}
