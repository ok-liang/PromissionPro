<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>角色页面</title>
    <!-- 引入公共的标签 。因为是file属性，它是在服务器的项目中把静态资源直接包含进来，然后再响应给客户端，因此不需要加上"$[{}]。 " -->
    <%@include file="/static/common/common.jsp"%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/role.js"></script>
    <style>
        #dialog #myform .panel-header{
            height: 25px;
        }

        #dialog #myform .panel-title{
            color: black;
            margin-top: -5px;
        }
    </style>
</head>
<body>

    <!--工具栏-->
    <div id="toolbar">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="add">添加</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="edit">编辑</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" id="remove">删除</a>
    </div>

    <!--数据表格-->
    <div id="role_dg"></div>

    <!--添加 / 编辑 的对话框-->
    <div id="dialog">
        <form id="myform">
            <table align="center" style="border-spacing: 20px 30px">
                <input type="hidden" name="rid">
                <tr align="center">
                    <td>角色编号: <input type="text" name="rnum" class="easyui-validatebox" ></td>
                    <td>角色名称: <input type="text" name="rname" class="easyui-validatebox" ></td>
                </tr>
                <tr>
                    <!--待选 | 已选权限数据表格-->
                    <td><div id="role_data1"></div></td>
                    <td><div id="role_data2"></div></td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>
