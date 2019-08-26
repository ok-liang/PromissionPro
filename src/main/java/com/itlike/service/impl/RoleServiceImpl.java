package com.itlike.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itlike.domain.*;
import com.itlike.mapper.RoleMapper;
import com.itlike.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional //事务//该类中的所有方法都是事务操作
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageListRes getRoles(QueryVo vo) {
        //开启分页查询
        Page<Object> page = PageHelper.startPage(vo.getPage(), vo.getRows());
        List<Role> roles = roleMapper.selectAll();

        //封装成PageListRes返回
        PageListRes res = new PageListRes();
        res.setTotal(page.getTotal()); //查询出来的总记录数。
        res.setRows(roles); //封装查询出来的数据。
        return res;
    }

    @Override
    public void saveRole(Role role) {
        /**1.保存角色（更新role表）*/
        roleMapper.insert(role);
        /**2.保存角色与权限之间的关系（更新关联表）*/
        //若：角色1 对应有2和3这俩权限。则应该这样进行存储 1，2    1，3
        for (Permission permission : role.getPermissions()) {
            roleMapper.insertRoleAndPermissionRel(role.getRid(),permission.getPid());
        }
    }

    @Override
    public void updateRole(Role role) {
        /**1.打破之前角色 与 权限之间的关系*/
        roleMapper.deletePermissionRel(role.getRid());
        /**2.更新角色（更新role表）*/
        roleMapper.updateByPrimaryKey(role);
        /**3.重新建立角色 与 权限之间的关系*/
        for(Permission permission : role.getPermissions()){
            roleMapper.insertRoleAndPermissionRel(role.getRid(),permission.getPid());
        }
    }

    @Override
    public void deleteRole(Long rid) {
        /**1.先删除 角色与权限的关联关系*/
        roleMapper.deletePermissionRel(rid);
        /**2.然后 在role表中删除对应的角色*/
        roleMapper.deleteByPrimaryKey(rid);
    }

    @Override
    public List<Role> roleList() {
        return roleMapper.selectAll();
    }
}
