package com.itlike.domain;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
//定义包装类。//包装分页信息 和 数据信息：
public class PageListRes {
    private Long total;//总记录数
    private List<?> rows = new ArrayList<>();//容纳多条记录信息
}
