package com.itheima.bos.domain.base;

import java.util.Arrays;

/**  
 * ClassName:Chart <br/>  
 * Function:  <br/>  
 * Date:     2017年11月22日 上午12:53:20 <br/>       
 */
public class Chart {
    private String type="column";
    private String name;
    private int[] data;
    
    public Chart(String name, int[] is) {
        this.name = name;
        this.data = is;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
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
        return "Chart [type=" + type + ", name=" + name + ", data=" + Arrays.toString(data) + "]";
    }

    
}
  
