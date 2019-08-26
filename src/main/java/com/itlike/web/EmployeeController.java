package com.itlike.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itlike.domain.*;
import com.itlike.service.EmployeeService;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**返回一个 .jsp页面*/
    @RequestMapping("/employee")
    @RequiresPermissions("employee:index")
    public String employee(){
        return "employee";
    }

    /**
     * 查询员工（要求：分页查询）
     * @param vo
     * @return
     */
    @RequestMapping("/employeeList")
    @ResponseBody
    public PageListRes employeeList(QueryVo vo){
        //调用业务层查询员工
        PageListRes pageListRes = employeeService.getEmployee(vo);
        return pageListRes;
    }

    /**保存员工表单*/
    @RequestMapping("/saveEmployee")
    @ResponseBody
    @RequiresPermissions("employee:add")
    public AjaxRes saveEmployee(Employee employee){
        AjaxRes ajaxRes = new AjaxRes();
        try{
            //调用业务层插入员工
            employee.setState(true);
            employeeService.saveEmployee(employee);
            ajaxRes.setMsg("保存成功");
            ajaxRes.setSuccess(true);
        }catch (Exception e){
            ajaxRes.setMsg("保存失败");
            ajaxRes.setSuccess(false);
        }
        return ajaxRes;
    }

    /**更新员工*/
    @RequestMapping("/updateEmployee")
    @ResponseBody
    @RequiresPermissions("employee:edit")
    public AjaxRes updateEmployee(Employee employee){
        AjaxRes ajaxRes = new AjaxRes();
        try{
            //调用业务层更新员工
            employeeService.updateEmployee(employee);
            ajaxRes.setMsg("更新成功");
            ajaxRes.setSuccess(true);
        }catch (Exception e){
            ajaxRes.setMsg("更新失败");
            ajaxRes.setSuccess(false);
        }
        return ajaxRes;
    }


    /**接收离职操作的请求*/
    @RequestMapping("/updateState")
    @ResponseBody
    @RequiresPermissions("employee:delete")
    public AjaxRes updateState(Long id){
        AjaxRes ajaxRes = new AjaxRes();
        try{
            //调用业务层查询员工
            employeeService.updateState(id);
            ajaxRes.setMsg("更新成功");
            ajaxRes.setSuccess(true);
        }catch (Exception e){
            System.out.println(e.getStackTrace());
            ajaxRes.setMsg("更新失败");
            ajaxRes.setSuccess(false);
        }
        return ajaxRes;
    }

    /**根据用户id查询对应的角色id*/
    @RequestMapping("/getRoleByEid")
    @ResponseBody
    public List<Long> getRoleByEid(Long id){
        return employeeService.getRoleByEid(id);
    }

    /**当控制器当中出现异常时，就进到当前Handle中进行处理
     * method：代表的就是出现异常的方法对象。
     * */
    @ExceptionHandler(AuthorizationException.class)
    public void handleShiroException(HandlerMethod method, HttpServletResponse response) throws Exception {
        /**跳转到一个界面，提示  没有权限*/
        /**判断当前的请求是不是AjAX请求   如果是,返回json数据给浏览器   让它自己做跳转*/

        /**获取方法上的注解*/
        ResponseBody responseBody = method.getMethodAnnotation(ResponseBody.class);

        if(responseBody != null){
            //是Ajax请求————通过响应传输一个json格式字符串
            AjaxRes ajaxRes = new AjaxRes();
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("没有权限操作");
            String s = new ObjectMapper().writeValueAsString(ajaxRes);

            response.setCharacterEncoding("utf-8");
            response.getWriter().print(s);
        }else {
            //不是Ajax请求
            response.sendRedirect("noPermission.jsp");
        }
    }
}
