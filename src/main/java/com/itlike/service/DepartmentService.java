package com.itlike.service;

import com.itlike.domain.Department;

import java.util.List;

public interface DepartmentService {
    /**查询所有部门信息*/
    List<Department> getDepartmentList();
}
