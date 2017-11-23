import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itheima.crm.domain.Customer;

/**  
 * ClassName:tttest <br/>  
 * Function:  <br/>  
 * Date:     Nov 9, 2017 4:23:27 PM <br/>       
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class tttest {
    @Test
    public void  sss(){
        Customer customer = WebClient
        .create("http://localhost:8888/crm/webservice/customerService/customer")
        .type(MediaType.APPLICATION_XML)
        .query("telephone", "18723232323")
        .get(Customer.class);
        System.out.println(customer);

    }
}
  
