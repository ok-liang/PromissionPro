package com.itlike.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itlike.domain.AjaxRes;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 定义一个认证过滤器。
 *      来监听认证成功 / 失败？
 *      成功的话：放行请求。
 *      失败的话，不放行请求，响应一个认证信息给前端。
 */
public class MyFormFilter extends FormAuthenticationFilter {
    /**当认证成功时，会调用执行*/
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        /**设置响应的内容的编码*/
        response.setCharacterEncoding("utf-8");

        /**响应给浏览器提示信息*/
        AjaxRes ajaxRes = new AjaxRes();
        ajaxRes.setSuccess(true);
        ajaxRes.setMsg("登陆成功");

        /**把ajaxRes对象，转成JSON格式的字符串，再response给客户端*/
        String jsonString = new ObjectMapper().writeValueAsString(ajaxRes);
        response.getWriter().print(jsonString);
        return false;
    }

    /**认证失败时，调用执行*/
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        /**响应给浏览器提示信息*/
        System.out.println("认证失败");

        AjaxRes ajaxRes = new AjaxRes();
        ajaxRes.setSuccess(false);
        //出现异常
        if(e != null){
            /**获取异常名称,根据不同的异常Name，返回不同的异常信息*/
            String name = e.getClass().getName();
            if(name.equals(UnknownAccountException.class.getName())){
                /**没有账号*/
                ajaxRes.setMsg("没有账号");
            }else if(name.equals(IncorrectCredentialsException.class.getName())){
                /**密码错误*/
                ajaxRes.setMsg("密码错误");
            }else {
                /**未知错误*/
                ajaxRes.setMsg("未知错误");
            }
        }
        try {
            /**设置响应的内容的编码*/
            response.setCharacterEncoding("utf-8");
            /**把ajaxRes对象，转成JSON格式的字符串，再response给客户端*/
            String jsonString = new ObjectMapper().writeValueAsString(ajaxRes);
            response.getWriter().print(jsonString);
        }catch (Exception e1){
            e1.printStackTrace();
        }

        return false;
    }
}
