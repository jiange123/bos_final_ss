package com.itheima.bos.domain.base;  
/**  
 * ClassName:DataLabel <br/>  
 * Function:  <br/>  
 * Date:     2017年11月22日 下午12:00:58 <br/>       
 */
public class DataLabel {
    private boolean enabled=false;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "DataLabel [enabled=" + enabled + "]";
    }
    
}
  
