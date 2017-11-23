package com.itheima.bos.web.action.take_rec;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.ibm.icu.text.SimpleDateFormat;
import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.web.action.CommonAction;

import net.sf.json.JSONObject;

/**  
 * ClassName:ImageAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 19, 2017 2:57:27 PM <br/>       
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class ImageAction extends CommonAction<Object> {

    /**  
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).  
     * @since JDK 1.6  
     */
    private static final long serialVersionUID = 1L;

    //属性驱动获取图片文件
    private File imgFile;
    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }
    
    //获取文件名，文件类型
    private String imgFileFileName;
    private String imgFileContentType;
    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }
    public void setImgFileContentType(String imgFileContentType) {
        this.imgFileContentType = imgFileContentType;
    }
    
    //上传文件
    @Action(value="imageAction_upload")
    
    public String upload() throws Exception {
        Map<String, Object> map = new HashMap<>();
        try {
            //创建保存文件的文件夹
            String saveDirPath = "upload";
            //获取绝对地址
            String saveDirRealPath = ServletActionContext.getServletContext().getRealPath(saveDirPath);
            
            //获取文件名后缀
            String suffix = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
            //生成随机文件名
            String fileName = UUID.randomUUID().toString().replace("-", "").toUpperCase()+suffix;
            
            //保存文件
            FileUtils.copyFile(imgFile, new File(saveDirRealPath,fileName));
            
            //拼接页面访问路径，应该是相对路径/bos-management-web/upload/a.jpg
            String contextPath = ServletActionContext.getServletContext().getContextPath();
            map.put("error", 0);
            map.put("url", contextPath+"/upload/"+fileName);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("error", 1);
            map.put("message", e.getMessage());
        }
        //将map写回页面
        String json = JSONObject.fromObject(map).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
        return NONE;
    }
    
  //图片空间
    @Action(value="imageAction_manage")
    public String manage() throws Exception {
        
        //创建保存文件的文件夹
        String saveDirPath = "upload";
        //获取绝对地址
        String saveDirRealPath = ServletActionContext.getServletContext().getRealPath(saveDirPath);
        
        // 保存上传的图片的文件夹
        File currentPathFile = new File(saveDirRealPath);
        // 图片扩展名
        String[] fileTypes = new String[] {"gif", "jpg", "jpeg", "png", "bmp"};
        
        // 遍历目录取的文件信息
        List<Hashtable> fileList = new ArrayList<Hashtable>();
        if (currentPathFile.listFiles() != null) {
            for (File file : currentPathFile.listFiles()) {
                Hashtable<String, Object> hash =
                        new Hashtable<String, Object>();
                String fileName = file.getName();
                if (file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else if (file.isFile()) {
                    String fileExt =
                            fileName.substring(fileName.lastIndexOf(".") + 1)
                                    .toLowerCase();
                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", file.length());
                    hash.put("is_photo",
                            Arrays.<String>asList(fileTypes).contains(fileExt));
                    hash.put("filetype", fileExt);
                }
                hash.put("filename", fileName);
                hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(file.lastModified()));
                fileList.add(hash);
            }
        }
        // 获取本项目的路径
        String contextPath =
                ServletActionContext.getServletContext().getContextPath();
        //构造map集合，封装数据
        Map<String, Object> map = new HashMap<>();
        //封装文件
        map.put("file_list", fileList);
        //图片的路径
        map.put("current_url", contextPath+"/upload/");
        
        String json = JSONObject.fromObject(map).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        return NONE;
    }
}
  
