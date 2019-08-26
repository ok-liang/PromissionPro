package com.itlike.mapper;

import com.itlike.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long rid);

    int insert(Role record);

    Role selectByPrimaryKey(Long rid);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    /**
     * 保存角色 与 权限之间的关系
     * @param rid 角色id
     * @param pid 权限id
     * 当方法中传递的是两个参数时，要想在sql语句中使用参数名称，就要使用@Param注解。
     */
    void insertRoleAndPermissionRel(@Param("rid") Long rid, @Param("pid") Long pid);

    /**
     * 打破角色 与 权限之间的关联关系
     */
    void deletePermissionRel(Long rid);
}