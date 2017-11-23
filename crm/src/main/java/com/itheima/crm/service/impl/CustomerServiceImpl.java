package com.itheima.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.crm.dao.CustomerRepository;
import com.itheima.crm.domain.Customer;
import com.itheima.crm.service.CustomerService;

/**  
 * ClassName:CustomerServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 6, 2017 12:44:38 AM <br/>       
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    //test find all customer
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
    
    
    @Override
    //find unassociate2FixedAreaCustomer
    public List<Customer> findByFixedAreaIdIsNull() {
        return customerRepository.findByFixedAreaIdIsNull();
    }
    
    
    @Override
    //find associate2FixedAreaCustomer
    public List<Customer> findCustomerByFixedAreaId(String fixedAreaId) {
        return customerRepository.findByFixedAreaId(fixedAreaId);
    }
    
    
    @Override
    //associate customer to fixedarea
    public void assignCustomers2FixedArea(String fixedAreaId, List<Long> customerIds) {
        //1.set customer's fixedarea null
        customerRepository.setFixedAreaNullByFixedAreaId(fixedAreaId);
        //2.set customer's fixedarea as fixedarea 
        if(customerIds != null && customerIds.size() > 0){
            // do set
            for (Long customerId : customerIds) {
                customerRepository.updateCustomers2FixedArea(fixedAreaId, customerId);
            }
        }
    }
    
    
    @Override
    //customer registe
    public void regist(Customer customer) {
        customerRepository.save(customer);
    }
    
    
    @Override
    //find type of customer in database
    public Customer findByTelephone(String telephone) {
        Customer customer = customerRepository.findByTelephone(telephone);
        return customer;
    }
    
    
    @Override
    //active customer by telephone
    public void activeCustomer(String telephone) {
        customerRepository.updateByTelephone(telephone);
    }
    
    
    @Override
    //login
    public Customer login(String telephone, String password) {
        return customerRepository.findByTelephoneAndPassword(telephone, password);
    }
    
    
    @Override
    //checkTelephone
    public Customer checkTelephone(String telephone) {
        return customerRepository.findByTelephone(telephone);
    }


    @Override
    public Long findFixedAreaIdByAddress(String address) {
        System.out.println("地址："+address);
        return customerRepository.findFixedAreaIdByAddress(address);
    }


	@Override
	public List<Customer> customerFindAll() {
		 List<Customer> findAll = customerRepository.findAll();
        return findAll;
	}


    @Override
    public Integer findCustomerByFixedareaid(List<Long> fixedarea) {
        if(fixedarea!=null&&fixedarea.size()>0){
            int cconut=0;
            for (Long object : fixedarea) {
                Long l=customerRepository.findCustomerByFixedareaid(object);
                cconut+=l.intValue();
            }
            return cconut;
        } 
        return null;
    }

}
  
