package com.itheima.bos.service.take_rec;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.itheima.bos.domain.take_rec.Order;

/**  
 * ClassName:OrderService <br/>  
 * Function:  <br/>  
 * Date:     Nov 12, 2017 12:45:25 AM <br/>       
 */

public interface OrderService {

    @POST
    @Path("/save")
    
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public void save(Order order);
}
  
