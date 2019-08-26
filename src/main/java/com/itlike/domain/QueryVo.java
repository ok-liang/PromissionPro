package com.itlike.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//查询条件的通用类
@Getter@Setter@ToString
public class QueryVo {
    private Integer page; //获取第几页的数据。
    private Integer rows; //每一页展示的数据条数。
    private String keyword; //搜索框的关键字。
}
