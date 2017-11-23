package com.itheima.bos.domain.base;

import java.util.Arrays;

/**  
 * ClassName:Chart2 <br/>  
 * Function:  <br/>  
 * Date:     2017年11月22日 上午11:56:47 <br/>       
 */

public class Chart2 {
    private String type="pie";
    private String name="区域数";
    private Object[] data;
    private int[] center={500, 30};
    private int size=100;
    private boolean showInLegend=false;
    private DataLabel dataLabels=new DataLabel();
    
    
    public Chart2(Object[] data) {
        this.data = data;
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
    public Object[] getData() {
        return data;
    }
    public void setData(Object[] data) {
        this.data = data;
    }
    public int[] getCenter() {
        return center;
    }
    public void setCenter(int[] center) {
        this.center = center;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public boolean isShowInLegend() {
        return showInLegend;
    }
    public void setShowInLegend(boolean showInLegend) {
        this.showInLegend = showInLegend;
    }
    public DataLabel getDataLabels() {
        return dataLabels;
    }
    public void setDataLabels(DataLabel dataLabels) {
        this.dataLabels = dataLabels;
    }
    @Override
    public String toString() {
        return "Chart2 [type=" + type + ", name=" + name + ", data=" + Arrays.toString(data)
                + ", center=" + Arrays.toString(center) + ", size=" + size + ", showInLegend="
                + showInLegend + ", dataLabels=" + dataLabels + "]";
    }
    
    
    
    
}
  
