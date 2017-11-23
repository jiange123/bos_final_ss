package com.itheima.crm.dao.test;

import java.util.Collection;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import com.itheima.crm.domain.Customer;

/**  
 * ClassName:CustomerRepositoryTest <br/>  
 * Function:  <br/>  
 * Date:     Nov 6, 2017 1:01:29 AM <br/>       
 */
public class CustomerRepositoryTest {

    @Test
    public void findAll(){
        Collection<? extends Customer> collection = WebClient.create("http://localhost:8888/crm/webservice/customerService/customer")
                .accept(MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML)
                .type(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);
        for (Customer customer : collection) {
            System.out.println(customer);
        }
        
    }
}
  
