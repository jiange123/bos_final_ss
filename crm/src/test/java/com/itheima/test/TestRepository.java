package com.itheima.test;  
/**  
 * ClassName:TestRepository <br/>  
 * Function:  <br/>  
 * Date:     Nov 6, 2017 9:31:31 AM <br/>       
 */

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itheima.crm.dao.CustomerRepository;
import com.itheima.crm.domain.Customer;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestRepository {

    @Autowired
    private CustomerRepository customerRepository;
    @Test
    public void test01(){
        if(customerRepository != null){
            List<Customer> list = customerRepository.findAll();
            System.out.println(list);
        }
        
    }
    
    @Test
    public void test02(){
        Customer customer = WebClient
                .create("http://localhost:8888/crm/webservice/customerService/customer")
                .type(MediaType.APPLICATION_XML)
                .query("telephone", "13646464646")
                .get(Customer.class);
        System.out.println(customer);
    }
    @Test
    public void login() {
        Customer customer = customerRepository.findByTelephoneAndPassword("18723232323", "123");
        System.out.println(customer);
    }
    
    List<Customer> list;
    public void setList(List<Customer> list) {
        this.list = list;
    }
    @Test
    public void test03(){
        System.out.println(list);
    }
    
}
  
  
