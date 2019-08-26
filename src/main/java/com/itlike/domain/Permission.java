package com.itlike.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
public class Permission {
    private Long pid; //权限id

    private String pname; //权限名称

    private String presource; //能操作的资源名称
}