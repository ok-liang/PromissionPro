package com.itlike.web;

import com.itlike.domain.Permission;
import com.itlike.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**查询权限列表*/
    //因为权限列表的数据是有限个的，所以我们不用返回分页的结果类PageListRes,只需要返回数据集合List即可。
    @RequestMapping("/permissionList")
    @ResponseBody
    public List<Permission> permissionList(){
        List<Permission> permissions = permissionService.permissionList();
        return permissions;
    }

    /**根据角色查询对应的权限*/
    @RequestMapping("/getPermissionByRid")
    @ResponseBody
    public List<Permission> getPermissionByRid(Long rid){
        List<Permission> permissions =  permissionService.getgetPermissionByRid(rid);
        return permissions;
    }
}
