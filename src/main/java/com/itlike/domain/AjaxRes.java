package com.itlike.domain;

import lombok.Getter;
import lombok.Setter;
//统一的请求的响应对象
@Getter@Setter
public class AjaxRes {
    private boolean success;
    private String msg;
}
