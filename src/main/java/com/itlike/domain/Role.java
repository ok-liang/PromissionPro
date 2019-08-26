package com.itlike.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Role角色类
 *
 * 一个role可以有多个权限
 */
@Setter@Getter@ToString
public class Role {
    private Long rid; //角色id

    private String rnum; //角色编号

    private String rname; //角色姓名

    /**一个角色可以有多个权限*/
    private List<Permission> permissions;
}