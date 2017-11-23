package com.itheima.bos.web.action.take_rec;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
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

import com.itheima.bos.domain.take_rec.Promotion;
import com.itheima.bos.service.take_rec.PromotionService;
import com.itheima.bos.web.action.CommonAction;

/**
 * ClassName:PromotionAction <br/>
 * Function: <br/>
 * Date: Nov 19, 2017 4:48:56 PM <br/>
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class PromotionAction extends CommonAction<Promotion> {

    /**
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).
     * 
     * @since JDK 1.6
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private PromotionService promotionService;
    
    //属性驱动获取宣传图片
    private File titleImgFile;
    //获取图片名称
    private String titleImgFileFileName;
    public void setTitleImgFile(File titleImgFile) {
        this.titleImgFile = titleImgFile;
    }
    public void setTitleImgFileFileName(String titleImgFileFileName) {
        this.titleImgFileFileName = titleImgFileFileName;
    }
    @Action(value = "promotionAction_save", results = {@Result(name = "success",
            location = "/pages/take_delivery/promotion.html", type = "redirect")})
    public String save() {
        
        //获取模型对象
        Promotion promotion = getModel();
        
        try {
            //创建路径保存图片
            String saveDirPath = "upload";
            //获取绝对路径
            String saveDirRealPath = ServletActionContext.getServletContext().getRealPath(saveDirPath);
            //获取文件后缀
            String suffix = titleImgFileFileName.substring(titleImgFileFileName.lastIndexOf("."));
            //生成随机名
            String fileName = UUID.randomUUID().toString().replaceAll("-", "")+suffix;
            //保存图片
            FileUtils.copyFile(titleImgFile, new File(saveDirRealPath,fileName));
            //设置路径
            //String contextPath = ServletActionContext.getServletContext().getContextPath();
            promotion.setTitleImg("/upload/"+fileName);
        } catch (IOException e) {
            e.printStackTrace();  
            //如果没有上传图片，将路径置为空
            promotion.setTitleImg("");
        }
        //需要将状态status置为1：有效
        promotion.setStatus("1");
        promotionService.save(getModel());
        return SUCCESS;
    }
    
    @Action(value="promotionAction_pageQuery")
    public String pageQuery() throws Exception{
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Promotion> page= promotionService.pageQuery(pageable);
        page2json(page, null);
        return NONE; 
    }

}
