package com.itlike.service;

import com.itlike.domain.PageListRes;
import com.itlike.domain.QueryVo;
import com.itlike.domain.Role;

import java.util.List;

public interface RoleService {
    /**
     * 查询角色列表
     * @param vo：封装的查询条件
     * @return：分页信息 和 数据
     */
    PageListRes getRoles(QueryVo vo);

    /**
     * 保存角色 及角色和权限的关联关系
     * @param role
     */
    void saveRole(Role role);

    /**
     * 更新角色
     * @param role
     */
    void updateRole(Role role);

    /**
     * 删除角色的业务
     * @param rid
     */
    void deleteRole(Long rid);

    /**
     * 获取所有的角色
     * @return
     */
    List<Role> roleList();
}
