package com.itheima.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.crm.domain.Customer;


/**  
 * ClassName:CustomerRepository <br/>  
 * Function:  <br/>  
 * Date:     Nov 6, 2017 12:05:31 AM <br/>       
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //查询没有关联的定区的客户
    List<Customer> findByFixedAreaIdIsNull();
    
    //查询已经关联定区的客户
    List<Customer> findByFixedAreaId(String fixedAreaId);
    
    //自定义将指定定区关联的客户信息进行解绑
    @Modifying
    @Query("update Customer set fixedAreaId = null where fixedAreaId = ?")
    void setFixedAreaNullByFixedAreaId(String fixedAreaId);
    
    //自定义将需要关联指定定区的客户进行绑定
    @Modifying
    @Query("update Customer set fixedAreaId = ? where id = ?")
    void updateCustomers2FixedArea(String fixedAreaId, Long id);
    
    //get customer by telephone
    @Query("from Customer where telephone = ?")
    Customer findByTelephone(String telephone);
    
    //active customer
    @Modifying
    @Query("update Customer set type = 1 where telephone = ?")
    void updateByTelephone(String telephone);
    
    //find customer by telephone and password
    Customer findByTelephoneAndPassword(String telephone,String password);

    @Query("select fixedAreaId from Customer where address = ?")
    Long findFixedAreaIdByAddress(String address);
    
    @Query(value="select count(*) from T_CUSTOMER c where c.c_fixed_area_id=?",
            nativeQuery=true)
    Long findCustomerByFixedareaid(Long fixedareaid);
    
    
}
  
