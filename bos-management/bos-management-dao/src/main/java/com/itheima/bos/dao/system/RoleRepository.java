package com.itheima.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.itheima.bos.domain.system.Role;

/**
 * ClassName:RoleRepository <br/>
 * Function: <br/>
 * Date: Nov 17, 2017 3:28:10 PM <br/>
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * 根据当前用户的id查询对应的所有角色 findByUid:. <br/>
     * 
     * @param id
     * @return
     */
    @Query("select r from Role r inner join r.users u where u.id = ?")
    List<Role> findByUid(Long id);

    /**
     * 图表显示查询已经关联的角色 getById:. <br/>
     * 
     * @param id
     * @return
     */
    @Query("select p.name,count(*) from Permission p inner join p.roles r where r.id=? group by p.name")
    List<Object[]> getById(Long id);

    /**
     * 查询所有权限 getAll:. <br/>
     * 
     * @return
     */
    @Query(value ="select p.c_name,count(*) from t_Permission p inner join t_role_permission rp on p.c_id = rp.c_permission_id inner join t_role r on rp.c_role_id = r.c_id where r.c_id <> ? and p.c_name not in(select tp.c_name from t_Permission tp inner join t_role_permission trp on trp.c_permission_id = tp.c_id inner join t_role ro on ro.c_id = trp.c_role_id where ro.c_id = ?) group by p.c_name",nativeQuery=true)
    List<Object[]> getAll(Long id,Long id1);

    /**
     * 查询所有关联的菜单 getMenuById:. <br/>
     * 
     * @param id
     * @return
     */
    @Query("select m.name,count(*) from Menu m inner join m.roles r where r.id = ? group by m.name")
    List<Object[]> getMenuById(Long id);
}
