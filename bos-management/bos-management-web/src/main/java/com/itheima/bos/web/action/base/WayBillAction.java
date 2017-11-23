package com.itheima.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.ibm.icu.text.DecimalFormat;
import com.itheima.bos.domain.take_rec.WayBill;
import com.itheima.bos.service.take_rec.WayBillService;
import com.itheima.bos.web.action.CommonAction;

/**  
 * ClassName:WayBillAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 13, 2017 6:39:24 PM <br/>       
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class WayBillAction extends CommonAction<WayBill> {

    /**  
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).  
     * @since JDK 1.6  
     */
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private WayBillService wayBillService;
    
 // 自定义属性，接受文件
   private File file;

   public void setFile(File file) {
       this.file = file;
   }

   /**
    * 文件的上传
    * @return
    * @throws Exception
    */
   @Action(value = "WayBillAction_batchImport", results = { @Result(name = "success",
           location = "../../area.html", 
           type = "redirect") })
   public String batchImport() throws Exception {
       //加载文件，不同格式的文件对应不同的加载api
       HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
       
       //读取第一页内容==每个Excel文件中可能有好几个sheet 
       HSSFSheet sheetAt = hssfWorkbook.getSheetAt(0);
       //用于容纳 所有的数据对象
       List<WayBill> list = new ArrayList<>();
       
       //遍历这个页面中的  n   条数据
       for (Row row : sheetAt) {
           
           if (row.getRowNum() == 0) {
               continue;
           }
           
           /*两种方式获取 一行<条>数据的内容 */
           /*for (Cell cell : row) {
               System.out.println(cell);
           }
           System.out.println("---------------------方式---------------------");*/
           
           WayBill wayBill = new WayBill();
           
           //row 对应着每一行数据
           //分别获取着一条数据中  ---第几列的数据
           double cellValue = row.getCell(0).getNumericCellValue();   
           String wayBillNum = new DecimalFormat("#").format(cellValue);   
           
           String goodsType = row.getCell(1).getStringCellValue();
           String sendProNum = row.getCell(2).getStringCellValue();
           String sendName = row.getCell(3).getStringCellValue();
           //
           Double sendMobileDouble = row.getCell(4).getNumericCellValue();
           String sendMobile  = new DecimalFormat("#").format(sendMobileDouble);   
           
           String sendAddress  = row.getCell(5).getStringCellValue();
           String recName  = row.getCell(6).getStringCellValue();
           //
           Double recMobileDouble  = row.getCell(7).getNumericCellValue();
           String recMobile  = new DecimalFormat("#").format(recMobileDouble);   

           String recCompany  = row.getCell(8).getStringCellValue();
           String recAddress  = row.getCell(9).getStringCellValue();

           wayBill.setWayBillNum(wayBillNum);
           wayBill.setGoodsType(goodsType);
           wayBill.setSendProNum(sendProNum);
           wayBill.setSendName(sendName);
           wayBill.setSendMobile(sendMobile);
           wayBill.setSendAddress(sendAddress);
           wayBill.setRecName(recName);
           wayBill.setRecMobile(recMobile);
           wayBill.setRecCompany(recCompany);
           wayBill.setRecAddress(recAddress);
            
           list.add(wayBill);
       
       }
       wayBillService.save(list);
       
       System.out.println("---保存了---");
       
       HttpServletResponse response = ServletActionContext.getResponse();
       //处理乱码// 设置类型和字符集
       response.setContentType("application/json;charset=UTF-8");
       response.getWriter().write("success");
       
       
       return NONE ;
   }
    
    
    @Action(value="waybillAction_save")
    public String save() throws Exception{
        String flag = "";
        try {
           // int a = 1 / 0;
            wayBillService.save(getModel());
        } catch (Exception e) {
            e.printStackTrace();  
            flag = "0";
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(flag);
        return NONE;
    }//waybillAction_pageQuery
    
    @Action(value="waybillAction_pageQuery")
    public String pageQuery() throws Exception{
        Pageable pageable = new PageRequest(page-1, rows);
        Page<WayBill> page = wayBillService.pageQuery(pageable);
        page2json(page, null);
        return NONE;
    }
}
  
