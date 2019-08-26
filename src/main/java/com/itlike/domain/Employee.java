package com.itlike.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter@Getter@ToString
public class Employee {
    private Long id;//员工id

    private String username;//员工name

    private String password;//员工密码

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8") //把Date类型 转为指定的时间格式展示。
    @DateTimeFormat(pattern = "yyyy-MM-dd") //提交表单时，把指定时间格式的数据 转为Date类型。
    private Date inputtime;//入职时间

    private String tel;//电话

    private String email;//

    private Boolean state;//离职状态 1：在职   0：离职

    private Boolean admin;//是否是管理员  1：是   0：否

    private Department department;//关联的部门信息

    private List<Role> roles = new ArrayList<>(); //关联的角色信息 （多对多的关系，一个员工对应多个角色）
}