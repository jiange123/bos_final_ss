package com.itheima.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.itheima.crm.domain.Customer;

/**  
 * ClassName:CustomerService <br/>  
 * Function:  <br/>  
 * Date:     Nov 6, 2017 12:39:58 AM <br/>       
 */
public interface CustomerService {

    @GET
    @Path("/customer")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    List<Customer> findAll();
    
    @GET
    @Path("/customerFindAll")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    List<Customer> customerFindAll();
    
    @GET
    @Path("/findMeiYouGuanLianDingQu")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    List<Customer> findByFixedAreaIdIsNull();
    
    @GET
    @Path("/findYiJingGuanLianDingQu")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    List<Customer> findCustomerByFixedAreaId(@QueryParam("fixedAreaId")String fixedAreaId);
    
    @PUT
    @Path("/assignCustomers2FixedArea")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    void assignCustomers2FixedArea(@QueryParam("fixedAreaId")String fixedAreaId,@QueryParam("customerIds")List<Long> customerIds);
    
    @POST
    @Path("regist")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    void regist(Customer customer);
    
    @GET
    @Path("/customer")
    @Consumes({MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_XML})
    Customer findByTelephone(@QueryParam("telephone") String telephone);
    
    @PUT
    @Path("/active")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    void activeCustomer(@QueryParam("telephone")String telephone);
    
    @GET
    @Path("/login")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    Customer login(@QueryParam("telephone")String telephone,@QueryParam("password")String password);
    
    @GET
    @Path("/checkTelephone")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    Customer checkTelephone(@QueryParam("telephone")String telephone);
    
    @GET
    @Path("/findFixedAreaIdByAddress")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    Long findFixedAreaIdByAddress(@QueryParam("address")String address);
    
    @GET
    @Path("/findCustomerByFixedareaid")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    Integer findCustomerByFixedareaid(@QueryParam("fixedarea") List<Long> fixedarea);
}
  
