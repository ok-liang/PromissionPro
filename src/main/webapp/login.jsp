<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>用户权限管理系统</title>
    <link href="./static/css/base.css" rel="stylesheet">
    <link href="./static/css/login.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/easyui/jquery.min.js"></script>
    <script>
        $(function () {
           $("#loginBtn").click(function () {
               /**发送请求，做登陆验证*/
               /**Ajax发送请求，是没有办法在服务器当中跳转请求的。如：request.请求转发。
                * 只能通过在浏览器当中来跳转。
                * */
               $.post("/login",$("#form").serialize(),function (data) {
                    /**把data json格式的字符串 转成 json格式数据*/
                    data = $.parseJSON(data);
                    if(data.success){
                        alert("认证成功");
                        /**跳转到首页当中*/
                        window.location.herf = "/index.jsp";
                    }else {
                        alert(data.msg);
                    }
               });
           });
        });
    </script>
</head>
<body class="white">
<div class="login-hd">
    <div class="left-bg"></div>
    <div class="right-bg"></div>
    <div class="hd-inner">
        <span class="logo"></span>
        <span class="split"></span>
        <span class="sys-name">用户权限管理系统</span>
    </div>
</div>
<div class="login-bd">
    <div class="bd-inner">
        <div class="inner-wrap">
            <div class="lg-zone">
                <div class="lg-box">
                    <div class="lg-label"><h4>用户登录</h4></div>

                    <form id="form">
                        <div class="lg-username input-item clearfix">
                            <i class="iconfont"></i>
                            <input type="text" value="阿泰2" name="username" placeholder="请用户名">
                        </div>
                        <div class="lg-password input-item clearfix">
                            <i class="iconfont"></i>
                            <input type="password" value="123" name="password"  placeholder="请输入密码">
                        </div>

                        <div class="enter">
                            <a href="javascript:;" class="purchaser" id="loginBtn">点击登录</a>
                        </div>

                    </form>
                    <div class="line line-y"></div>
                    <div class="line line-g"></div>
                </div>
            </div>
            <div class="lg-poster"></div>
        </div>
    </div>
</div>
<div class="login-ft">
    <div class="ft-inner">
        <div class="about-us">
            <a href="javascript:;">关于我们</a>
            <a href="https://damo.alibaba.com/">达摩院</a>
            <a href="javascript:;">服务条款</a>
            <a href="javascript:;">联系方式</a>
        </div>
        <div class="address"> 系统版权均归 Alibaba 所有 &nbsp;编号：210019&nbsp;&nbsp;Copyright&nbsp;©&nbsp;2019&nbsp;-&nbsp;2020&nbsp;Alibaba&nbsp;版权所有</div>
        <div class="other-info">建议使用IE8及以上版本浏览器&nbsp;沪ICP备&nbsp;18036896号&nbsp;E-mail：ok-liang@126.com</div>
    </div>
</div>


<script type="text/javascript">

</script>
</body>
</html>
