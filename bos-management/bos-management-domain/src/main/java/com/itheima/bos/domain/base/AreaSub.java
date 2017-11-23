package com.itheima.bos.domain.base;

import java.util.Arrays;

/**  
 * ClassName:AreaSub <br/>  
 * Function:  <br/>  
 * Date:     2017年11月21日 上午1:48:00 <br/>       
 */
public class AreaSub {
    private String name;//区域
    private int[] data;//区域下的分区数
    public AreaSub(String name, int[] data) {
        super();
        this.name = name;
        this.data = data;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int[] getData() {
        return data;
    }
    public void setData(int[] data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return "AreaSub [name=" + name + ", data=" + Arrays.toString(data) + "]";
    }
    
}
  
