package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.AreaSub;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.SubareaService;
import com.itheima.bos.web.action.CommonAction;
import com.itheima.crm.domain.Customer;
import com.ithiema.bos.utils.FileUtils;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:SubareaAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 4, 2017 4:46:06 PM <br/>       
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class SubareaAction extends CommonAction<SubArea> implements ModelDriven<SubArea> {

    /**  
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).  
     * @since JDK 1.6  
     */
    private static final long serialVersionUID = 1L;
    
    
    @Autowired
    private SubareaService subareaService;
    
    
    @Action(value="subareaAction_save",results={@Result(name="success",location="/pages/base/sub_area.html",type="redirect")})
    public String save(){
        subareaService.save(getModel());
        return SUCCESS;
    }
    
    private Long fixedId;
    public void setFixedId(Long fixedId) {
        this.fixedId = fixedId;
    }
         
    @Action("subArea_findByFixedId")
    public String findByFixedId() throws Exception{
        List<SubArea> list  = subareaService.findByFixedId(fixedId);
        list2json(list,new String[]{"fixedArea","subareas"});
        return NONE;
    }
    
    @Action(value="subAreaAction_findToColumn")
    public String findToColumn() throws Exception{
        List<AreaSub> list = subareaService.findToColumn();
        list2json(list, null);
        return NONE;
    }
    
    @Action("subArea_findCustomerByFixedId")
    public String findCustomerByFixedId() throws Exception{
        String fixedAreaId = fixedId + "";
       @SuppressWarnings("unchecked")
      List<Customer> list =
               (List<Customer>)WebClient
                    .create("http://localhost:8888/crm/webservice/customerService/findYiJingGuanLianDingQu")
                    .type(MediaType.APPLICATION_JSON)
                    .query("fixedAreaId", fixedAreaId)
                    .getCollection(Customer.class);
       
       list2json(list, null);
        return NONE;
    }
    
    //分页查询
    //@RequiresPermissions("subarea_pageQuery")
    @Action(value="subareaAction_pageQuery",results={@Result(type="redirect")})
    public String pageQuery() throws Exception{
        //构造查询条件
        Specification<SubArea> specification = new Specification<SubArea>() {
            //参数1：root对象，可以理解为泛型对象
            //参数2：一般用不到
            //参数3：cb 构造查询条件
            @Override
            public Predicate toPredicate(Root<SubArea> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                String keyWords = getModel().getKeyWords();
                Area area = getModel().getArea();
                //FixedArea fixedArea = getModel().getFixedArea();
                List<Predicate> list = new ArrayList<>();
                if(StringUtils.isNotEmpty(keyWords)){
                    Predicate p1 = cb.like(root.get("keyWords").as(String.class), "%"+keyWords+"%");
                    list.add(p1);
                }
               
                if(area != null && StringUtils.isNotEmpty(area.getProvince())){
                    Join<Object, Object> join = root.join("area");
                    Predicate p2 = cb.equal(join.get("province").as(Area.class), area.getProvince());
                    list.add(p2);
                }
                if(area != null && StringUtils.isNotEmpty(area.getCity())){
                    Join<Object, Object> join = root.join("area");
                    Predicate p3 = cb.equal(join.get("city").as(Area.class), area.getCity());
                    list.add(p3);
                }
                if(area != null && StringUtils.isNotEmpty(area.getDistrict())){
                    Join<Object, Object> join = root.join("area");
                    Predicate p4 = cb.equal(join.get("district").as(Area.class),area.getDistrict());
                    list.add(p4);
                }
                if(list.size() == 0){
                    return null;
                }
                Predicate[] arr = new Predicate[list.size()];
                list.toArray(arr);
                return cb.and(arr);
            }};
        Pageable pageable = new PageRequest(page-1, rows);
        Page<SubArea> page = subareaService.pageQuery(specification,pageable);
        page2json(page, new String[]{"subareas","couriers"});
        return NONE;
    }
    
    
    //定区关联分区，查询所有分区
    @Action(value="subareaAction_findAll")
    public String findAll() throws Exception{
        List<SubArea> list = subareaService.findAll();
        System.out.println(list);
        list2json(list, new String[]{"subareas","couriers"});
        return NONE;
    }
    
    
    //删除分区
    @Action(value="subareaAction_deleteById",results={@Result(name="success",location="/pages/base/sub_area.html",type="redirect")})
    public String deleteById(){
        subareaService.deleteById(getModel().getId());
        return SUCCESS;
    }
   
    
 // 导出Excel
    @Action("subareaAction_exportExcel")
    public String exportExcel() throws IOException{
        List<SubArea> list = subareaService.findAll();
        
     // 在内存中创建了一个Excel文件
        HSSFWorkbook workbook=new HSSFWorkbook();
     // 创建一个sheet(名字)
        HSSFSheet sheet=workbook.createSheet("分区数据");
     // 创建标题行
        HSSFRow titleRow=sheet.createRow(0);
     // 创建列
        titleRow.createCell(0).setCellValue("分拣编号");
        titleRow.createCell(1).setCellValue("省");
        titleRow.createCell(2).setCellValue("市");
        titleRow.createCell(3).setCellValue("区");
        titleRow.createCell(4).setCellValue("关键字");
        titleRow.createCell(5).setCellValue("起始号");
        titleRow.createCell(6).setCellValue("终止号");
        titleRow.createCell(7).setCellValue("单双号");
        titleRow.createCell(8).setCellValue("辅助关键字");
        for(SubArea subArea:list){
            HSSFRow dataRow=sheet.createRow(sheet.getLastRowNum()+1);
         // 创建列
            dataRow.createCell(0).setCellValue(subArea.getId());
            dataRow.createCell(1).setCellValue(subArea.getArea().getProvince());
            dataRow.createCell(2).setCellValue(subArea.getArea().getCity());
            dataRow.createCell(3).setCellValue(subArea.getArea().getDistrict());
            dataRow.createCell(4).setCellValue(subArea.getKeyWords());
            dataRow.createCell(5).setCellValue(subArea.getStartNum());
            dataRow.createCell(6).setCellValue(subArea.getEndNum());
            dataRow.createCell(7).setCellValue(subArea.getSingle());
            dataRow.createCell(8).setCellValue(subArea.getAssistKeyWords());
        }
        String fileName="区域数据.xls";
        HttpServletRequest request=ServletActionContext.getRequest();
        String agent=request.getHeader("User-Agent");
     // 对文件名重新进行编码
        fileName=FileUtils.encodeDownloadFilename(fileName, agent);
        
     // 一个流两个头
        HttpServletResponse response=ServletActionContext.getResponse();
     // 设置文件的类型
        response.setContentType(ServletActionContext.getServletContext().getMimeType(fileName));
     // 设置Content-Disposition头信息
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
     // 输出流
        ServletOutputStream outputStream = response.getOutputStream();
     // 写出文件
        workbook.write(outputStream);
     // 释放资源
        workbook.close();
        return NONE;
}

    

}
  
