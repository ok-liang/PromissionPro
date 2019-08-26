package com.itlike.service;

import com.itlike.domain.Permission;

import java.util.List;

public interface PermissionService {
    /**查询所有的权限*/
    List<Permission> permissionList();

    /**根据角色id查询权限*/
    List<Permission> getgetPermissionByRid(Long rid);
}
