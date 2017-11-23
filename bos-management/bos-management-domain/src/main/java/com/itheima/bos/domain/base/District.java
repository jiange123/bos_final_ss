package com.itheima.bos.domain.base;  
/**  
 * ClassName:District <br/>  
 * Function:  <br/>  
 * Date:     2017年11月22日 上午12:55:43 <br/>       
 */

    
public class District {
    private int y;
    private String name;
    
    public District(int y, String name) {
        this.y = y;
        this.name = name;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "District [y=" + y + ", name=" + name + "]";
    }
    
}
  
