package com.itheima.bos.service.jobs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itheima.bos.dao.take_rec.WorkBillRepository;
import com.itheima.bos.domain.take_rec.WorkBill;
import com.ithiema.bos.utils.MailUtils;

/**
 * ClassName:WorkBillJobs <br/>
 * Function: <br/>
 * Date: Nov 19, 2017 11:20:56 AM <br/>
 */
@Component
public class WorkBillJobs {

    @Autowired
    private WorkBillRepository workBillRepository;

    public void sendEmail() {

        // 查询所有的工单信息
        List<WorkBill> list = workBillRepository.findAll();

        String emailBody = "编号\t工单类型\t取件状态\t快递员<br/>";

        // 遍历集合，追加邮件信息
        for (WorkBill workBill : list) {
            emailBody += workBill.getId() + "\t" + workBill.getType() + "\t"
                    + workBill.getPickstate() + "\t" + workBill.getCourier().getName() + "<br/>";
        }
        // 发送邮件
        MailUtils.sendMail("xm@store.com", "工单信息汇总", emailBody);

    }
}
