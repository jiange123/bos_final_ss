package com.itheima.bos.web.action;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:CommonAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 3, 2017 9:56:00 AM <br/>       
 */
public class CommonAction<T> extends ActionSupport implements ModelDriven<T> {
    //假如AreaAction继承了本类
    public CommonAction(){
        //获取运行时子类的字节码
        Class<? extends CommonAction> childClass = this.getClass();
        //通过子类获取父类类型,获取到的是顶级接口
        Type genericSuperclass = childClass.getGenericSuperclass();
        //将顶级接口进行强制类型转换
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        //获取类型上的泛型数组
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        //此处只有一个泛型，即获取数组中第一个元素,就是运行时的泛型
        Class realClass =  (Class) actualTypeArguments[0];
        try {
            t = (T) realClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();  
        }
    }
    //抽取运行时类型的创建对象方法
    private T t;
    @Override
    public T getModel() {
        return t;
    }
    
    //抽取分页查询的方法
    //属性驱动获取页面传递的参数page，rows    //其默认值均为0，如果实体类中有同名的字段，struts封装是优先封装给model对象，此时要想获取值，只能从model中获取，属性驱动无法使用
    public int page;
    public int rows;
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    public void page2json(Page<T> page,String[] excludes) throws Exception{
        List<T> rows = page.getContent();
        long total = page.getTotalElements();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", rows);
        //设置忽略字段
        JsonConfig config = new JsonConfig();
        config.setExcludes(excludes);
        String json = JSONObject.fromObject(map,config).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/josn;charset=utf-8");
        response.getWriter().write(json);
    }
    
    //将list集合转换为json数据
    public void list2json(List list,String[] excludes) throws Exception{
        JsonConfig config = new JsonConfig();
        config.setExcludes(excludes);
        String json = JSONArray.fromObject(list,config).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/josn;charset=utf-8");
        response.getWriter().write(json);
    }
    
}
  
