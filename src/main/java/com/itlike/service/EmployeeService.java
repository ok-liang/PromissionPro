package com.itlike.service;

import com.itlike.domain.Employee;
import com.itlike.domain.PageListRes;
import com.itlike.domain.QueryVo;
import com.itlike.domain.Role;

import java.util.List;

public interface EmployeeService {
    /**查询员工*/
    PageListRes getEmployee(QueryVo vo);

    /**保存员工*/
    void saveEmployee(Employee employee);

    /**更新员工*/
    void updateEmployee(Employee employee);

    /**更新员工状态*/
    void updateState(Long id);

    /**根据员工id，查询对应的所以角色id*/
    List<Long> getRoleByEid(Long id);

    /**根据用户名查询有没有当前这个用户*/
    Employee getEmployeeWithUsername(String username);

    /**4、根据用户id查询对应的角色编号名称*/
    List<String> getRolesById(Long id);

    /**根据用户id查询对应的资源权限名称*/
    List<String> getPermissionById(Long id);
}
