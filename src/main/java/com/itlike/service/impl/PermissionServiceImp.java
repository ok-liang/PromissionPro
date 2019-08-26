package com.itlike.service.impl;

import com.itlike.domain.Permission;
import com.itlike.mapper.PermissionMapper;
import com.itlike.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImp implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> permissionList() {
        List<Permission> permissions = permissionMapper.selectAll();

        return permissions;
    }

    @Override
    public List<Permission> getgetPermissionByRid(Long rid) {
        return permissionMapper.selectPermissionByRid(rid);
    }
}
