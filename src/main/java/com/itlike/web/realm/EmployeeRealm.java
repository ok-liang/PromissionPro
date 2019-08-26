package com.itlike.web.realm;

import com.itlike.domain.Employee;
import com.itlike.service.EmployeeService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义登陆用户的Realm
 */
public class EmployeeRealm extends AuthorizingRealm {

    @Autowired
    private EmployeeService employeeService;

    /**认证*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("来到了Employee 认证");
        //1、验证有没有这个账户。有则去做认证。
        /**获取身份信息*/
        String username = (String)token.getPrincipal();

        /**在数据库中查询有没有当前用户*/
        Employee employee = employeeService.getEmployeeWithUsername(username);
        if(employee == null){
            return null;//没有这个用户，即：认证信息为null
        }

        /**2、认证*/
        //参数：主体、正确的密码、盐、当前realm的名字
        //employee放到参数当中，则shiro就会把employee对象放到Shiro Session当中，然后不管在哪里都能访问的到employee信息。
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(employee,
                employee.getPassword(),
                ByteSource.Util.bytes(employee.getUsername()),
                this.getName());
        return info;
    }

    /**授权
     * doGetAuthorizationInfo() 这个方法什么时候被调用？
     * 1、访问路径对应的Controller的方法上，若有授权的注解，，则调用doGetAuthorizationInfo()；
     * 2、若页面中有授权的标签，也会调用该方法。
     *
     * 根据用户对某个资源的请求操作 判断当前用户有没有该操作权限，有的话进行操作。没有的话，进行一个提示。
     *
     * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("进入到授权----------------------");

        //1、获取用户的身份信息
        Employee employee = (Employee) principalCollection.getPrimaryPrincipal();
        //2、根据当前用户的用户名查询对应的角色 和 权限
        List<String> roles = new ArrayList<>();
        List<String> permissions = new ArrayList<>();

        /**判断当前用户是否是管理员。是：拥有所有的权限。不是：则去数据库当中查询用户对应的权限*/
        if(employee.getAdmin()){
            /**拥有所有的权限*/
            permissions.add("*:*");
        }else{
            //3、查询权限
            roles = employeeService.getRolesById(employee.getId());
            //查询角色
            permissions = employeeService.getPermissionById(employee.getId());
        }
        //4、添加授权信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(permissions);
        return info;
    }
}
