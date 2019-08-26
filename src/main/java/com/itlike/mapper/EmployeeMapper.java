package com.itlike.mapper;

import com.itlike.domain.Employee;
import com.itlike.domain.QueryVo;
import com.itlike.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    Employee selectByPrimaryKey(Long id);

    List<Employee> selectAll(QueryVo vo);

    int updateByPrimaryKey(Employee record);

    void updateState(Long id);

    /** 保存员工跟角色的关系*/
    void insertEmployeeAncRoleRel(@Param("id") long id, @Param("rid") Long rid);

    /**根据员工id，查询对应的所以角色id*/
    List<Long> getRoleByEid(Long id);

    /**打破员工跟角色的关联关系*/
    void deleteRoleRel(Long id);

    /**根据用户名查询有没有当前这个用户*/
    Employee selectEmployeeWithUsername(String username);

    /**4、根据用户id查询对应的角色编号名称*/
    List<String> getRolesById(Long id);
    /**根据用户id查询对应的资源权限名称*/
    List<String> getPermissionById(Long id);
}