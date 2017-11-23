package com.itheima.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
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

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.base.AreaService;
import com.itheima.bos.web.action.CommonAction;
import com.ithiema.bos.utils.FileUtils;
import com.ithiema.bos.utils.PinYin4jUtils;

import freemarker.core.ReturnInstruction.Return;


/**  
 * ClassName:AreaAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 3, 2017 12:44:25 AM <br/>       
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class AreaAction extends CommonAction<Area> {
    private static final long serialVersionUID = 1L;

    @Autowired
    private AreaService areaService;
    
    //使用属性驱动获取文件
    private File file;
    public void setFile(File file) {
        this.file = file;
    }
    
    @Action(value="areaAction_importXLS",results={@Result(name="success",location="/pages/base/area.html",type="redirect")})
    public String importXLS() throws Exception{
        //File file = new File("D:\\a.xls");
        //加载文件
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
        //获取工作簿
        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
        //创建list集合用来存储数据，避免过多的操作数据库
        List<Area> list = new ArrayList<>();
        //遍历获取每一行
        for (Row row : sheet) {
            //将第一行数据排除
            if(row.getRowNum() == 0){
                continue;
            }
            //获取每一行的列字段的值，将第一列的编号排除
           String province = row.getCell(1).getStringCellValue();
           String city = row.getCell(2).getStringCellValue();
           String district = row.getCell(3).getStringCellValue();
           String postcode = row.getCell(4).getStringCellValue();
           /**
            *对shortcode和citycode进行解析封装
            *举例：广东省深圳市宝安区
            *shortcode：GDSZBA
            *citycode：SHENZHEN
            */
           //1.切掉省市区最后一位
           province = province.substring(0, province.length()-1);
           city = city.substring(0, city.length()-1);
           district = district.substring(0, district.length()-1);
           //2.获取shortcode
           String[] headByString = PinYin4jUtils.getHeadByString(province+city+district, true);
           String shortcode = StringUtils.join(headByString);
           //3.获取citycode,去除空格
           String citycode = PinYin4jUtils.hanziToPinyin(city,"").toUpperCase();
           //通过Area的有参构造进行封装以上字段的值
           Area area = new Area(province, city, district, postcode, citycode, shortcode);
           //将对象添加进集合
           list.add(area);
        }
        //保存数据
        areaService.save(list);
        //关闭资源
        hssfWorkbook.close();
        return SUCCESS;
    }
    
    //分页查询区域数据
    @Action(value="areaAction_pageQuery",results={@Result(type="redirect")})
    public String pageQuery() throws Exception{
        //使用JPA方式进行分页查询
        Pageable pageable = new PageRequest(page-1, rows);
        //调用业务进行查询
        Page<Area> page = areaService.pageQuery(pageable);
        page2json(page, new String[]{"subareas"});
        return NONE;
    }
    
    
    @Action("areaAction_doCharts")
    public String doCharts() throws Exception{
        //调用业务进行查询
        List<Object> list = areaService.doCharts();
        list2json(list, null);
        return NONE;
    }
    
    
    //属性驱动获取页面模糊查询时传过来的q参数
    private String  q;
    public void setQ(String q) {
        this.q = q;
    }
    
    
    @Action(value="AreaAction_findAll")
    public String findAll() throws Exception{
        //对q进行判断
        List<Area> list;
        if(StringUtils.isEmpty(q)){
            list = areaService.findAll();
        }else{
            list = areaService.findAllByQ(q);
        }
        list2json(list,new String[]{"subareas"});
        return NONE;
    }
    
    
    @Action(value="AreaAction_save",results={@Result(name="success",location="/pages/base/area.html",type="redirect")})
    public String save(){
        areaService.save(getModel());
        return SUCCESS;
    }
    
    
    @Action(value="AreaAction_deleteById",results={@Result(name="success",location="/pages/base/area.html",type="redirect")})
    public String deleteById(){
        areaService.deleteById(getModel().getId());
        System.out.println("区域已经删除");
        return SUCCESS;
    }
    
    @Action(value="areaAction_exportXLS")
    public String exportXLS() throws Exception{
        //查询所有分区
        List<Area> list = areaService.findAll();
        //创建表格
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("区域数据");
        //创建第一行：标题行
        HSSFRow titleRow = sheet.createRow(0);
        //创建列
        titleRow.createCell(0).setCellValue("省");
        titleRow.createCell(1).setCellValue("市");
        titleRow.createCell(2).setCellValue("区");
        titleRow.createCell(3).setCellValue("邮编");
        titleRow.createCell(4).setCellValue("简码");
        titleRow.createCell(5).setCellValue("城市编码");
        
        //遍历集合，获取数据
        for (Area area : list) {
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
            dataRow.createCell(0).setCellValue(area.getProvince());
            dataRow.createCell(1).setCellValue(area.getCity());
            dataRow.createCell(2).setCellValue(area.getDistrict());
            dataRow.createCell(3).setCellValue(area.getPostcode());
            dataRow.createCell(4).setCellValue(area.getShortcode());
            dataRow.createCell(5).setCellValue(area.getCitycode());
        }
        
        //定义文件名
        String fileName = "区域数据.xls";
        HttpServletRequest request = ServletActionContext.getRequest();
        //获取头信息
        String header = request.getHeader("User-Agent");
        //对文件名重新进行编码
        fileName = FileUtils.encodeDownloadFilename(fileName, header);
        
        //获取response对象
        HttpServletResponse response = ServletActionContext.getResponse();
        //设置文件的类型
        response.setContentType(ServletActionContext.getServletContext().getMimeType(fileName));
        // 设置Content-Disposition头信息
        response.setHeader("Content-Disposition","attachment; filename=" + fileName);
        //创建流
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return NONE;
    }
    
}
  
