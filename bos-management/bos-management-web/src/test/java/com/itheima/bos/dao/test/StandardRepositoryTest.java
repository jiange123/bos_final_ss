package com.itheima.bos.dao.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itheima.bos.dao.base.StandardRepository;
import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:StandardRepositoryTest <br/>  
 * Function:  <br/>  
 * Date:     Oct 31, 2017 10:32:24 AM <br/>       
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardRepositoryTest {

    @Autowired
    private StandardRepository standardRepository;
    @Test
    //根据name查询
    public void testFindByName(){
        Standard standard = standardRepository.findByName("张三");
        System.out.println(standard.getMaxLength()+"  "+standard.getName());
    }
    
    @Test
    //添加
    public void testUpdate(){
        Standard standard = new Standard();
        standard.setName("街坊三大爷");
        standard.setMaxLength(60);
        standardRepository.save(standard);
    }
    @Test
    //查询所有
    public void testFindAll(){
        List<Standard> list = standardRepository.findAll();
        System.out.println(list);
    }
    @Test
    //修改
    public void testUpdate02(){
        Standard standard = standardRepository.findByName("张三");
        standard.setMaxLength(200);
        standardRepository.save(standard);
    }    
    
    @Test
    //更新，传入ID，没有就创建，有就更新
    public void testUpdate03(){
        Standard standard = new Standard();
         standard.setId(3L);       
         standard.setName("老干妈");
         standard.setMaxLength(300);
         standardRepository.save(standard);
    }
    
    @Test
    //根据name和maxlength查询
    public void testFind01(){
        Standard standard = standardRepository.findByNameAndMaxLength("张三", 200);
        System.out.println(standard);
    }
    
    @Test
    //使用标准sql查询
    public void testFind02(){
        Standard standard = standardRepository.findByMMP("老干妈", 300);
        System.out.println(standard);
    }
    
    @Test
    //使用原生sql查询
    public void testFind03(){
        Standard standard = standardRepository.findByNNP("张三");
        System.out.println(standard);
    }
    
    @Test
    //模糊查询
    public void testFind04(){
        List<Standard> list = standardRepository.findByNameLike("%三%");
        System.out.println(list);
    }
    
    @Test
    //删除
    public void testDelete(){
        standardRepository.deleteByName("街坊三大爷");
    }
    
    @Test
    //更新
    public void testUpdate04(){
        standardRepository.updateByName("街坊三大爷");
    }
    
}
  
