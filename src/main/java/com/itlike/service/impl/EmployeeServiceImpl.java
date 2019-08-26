package com.itlike.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itlike.domain.Employee;
import com.itlike.domain.PageListRes;
import com.itlike.domain.QueryVo;
import com.itlike.domain.Role;
import com.itlike.mapper.EmployeeMapper;
import com.itlike.service.EmployeeService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public PageListRes getEmployee(QueryVo vo) {
        /**调用mapper查询员工*/
        Page<Object> page = PageHelper.startPage(vo.getPage(), vo.getRows());
        List<Employee> employees = employeeMapper.selectAll(vo);

        /**把查询的结果封装成pageListRes*/
        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(page.getTotal());
        pageListRes.setRows(employees);

        return pageListRes;
    }

    @Override
    public void saveEmployee(Employee employee) {
        /**对密码进行散列加密*/
        Md5Hash md5Hash = new Md5Hash(employee.getPassword(),2);
        employee.setPassword(md5Hash.toString());
        /**保存员工*/
        employeeMapper.insert(employee);
        /**保存员工跟角色的关联关系*/
        for (Role role : employee.getRoles()) {
            employeeMapper.insertEmployeeAncRoleRel(employee.getId(),role.getRid());
        }
    }

    @Override
    public void updateEmployee(Employee employee) {
        /**打破员工跟角色的关联关系*/
        employeeMapper.deleteRoleRel(employee.getId());
        /**更新员工*/
        employeeMapper.updateByPrimaryKey(employee);
        /**建立新的关联关系*/
        for (Role role : employee.getRoles()) {
            employeeMapper.insertEmployeeAncRoleRel(employee.getId(),role.getRid());
        }
    }

    @Override
    public void updateState(Long id) {
        employeeMapper.updateState(id);
    }

    @Override
    public List<Long> getRoleByEid(Long id) {
        return employeeMapper.getRoleByEid(id);
    }

    @Override
    public Employee getEmployeeWithUsername(String username) {
        return employeeMapper.selectEmployeeWithUsername(username);
    }

    @Override
    public List<String> getRolesById(Long id) {
        return employeeMapper.getRolesById(id);
    }

    @Override
    public List<String> getPermissionById(Long id) {
        return employeeMapper.getPermissionById(id);
    }
}
