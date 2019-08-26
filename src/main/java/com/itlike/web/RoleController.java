package com.itlike.web;

import com.itlike.domain.AjaxRes;
import com.itlike.domain.PageListRes;
import com.itlike.domain.QueryVo;
import com.itlike.domain.Role;
import com.itlike.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 返回role.jsp页面
     * */
    @RequestMapping("/role")
    public String role(){
        return "role";
    }

    /**
     * 请求角色列表(因为有QueryVo参数，则要求是有分页的查询)
     * @param vo
     * @return
     */
    @RequestMapping("/getRoles")
    @ResponseBody
    public PageListRes getRoles(QueryVo vo){
        //调用业务方法
        PageListRes pageListRes = roleService.getRoles(vo);
        return pageListRes;
    }

    /**
     * 保存角色
     * @param role
     */
    @RequestMapping("/saveRole")
    @ResponseBody
    public AjaxRes saveRole(Role role){
        AjaxRes ajaxRes = new AjaxRes();
        try{
            roleService.saveRole(role);
            ajaxRes.setMsg("保存成功");
            ajaxRes.setSuccess(true);
        }catch (Exception e){
            ajaxRes.setMsg("保存失败");
            ajaxRes.setSuccess(false);
        }
        return ajaxRes;
    }

    /**
     * 更新角色
     * @param role
     * @return
     */
    @RequestMapping("/updateRole")
    @ResponseBody
    public AjaxRes updateRole(Role role){
        AjaxRes res = new AjaxRes();
        try{
            roleService.updateRole(role);
            res.setMsg("更新角色成功");
            res.setSuccess(true);
        }catch (Exception e){
            res.setMsg("更新角色失败");
            res.setSuccess(false);
        }
        return res;
    }

    /**
     * 接收删除角色的请求
     * @param rid
     * @return
     */
    @RequestMapping("/deleteRole")
    @ResponseBody
    public AjaxRes deleteRole(Long rid){
        AjaxRes res = new AjaxRes();
        try{
            roleService.deleteRole(rid);
            res.setMsg("删除角色成功");
            res.setSuccess(true);
        }catch (Exception e){
            res.setMsg("删除角色失败");
            res.setSuccess(false);
        }
        return res;
    }

    /**
     * 查询所有角色（不带分页的查询）
     * @return
     */
    @RequestMapping("/roleList")
    @ResponseBody
    public List<Role> roleList(){
        return roleService.roleList();
    }
}
