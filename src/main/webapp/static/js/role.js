$(function () {
    /**角色数据列表*/
    $("#role_dg").datagrid({
        url:"/getRoles", /*发送请求获取JSON数据，把JSON数据解析后展示到数据表格中。*/
        columns:[[
            {field:'rnum',title:'角色编号',width:100,align:'center'},
            {field:'rname',title:'角色名称',width:100,align:'center'}
        ]],/*每一列展示的内容*/
        fit:true,/*让数据表格占满整个页面*/
        fitColumns:true,/*让宽度自动伸缩*/
        rownumbers:true,/*每条数据展示行号*/
        pagination:true,/*显示分页导航*/
        singleSelect:true,/*只能选择一行进行编辑*/
        striped:true,
        toolbar:"#toolbar"/*工具栏*/
    });


    /**添加/编辑对话框（默认是：open的 ）
     * 问题：
     *      因为添加 和 编辑功能使用的是同一个dialog。因此，在点击保存按钮时，要判断一下 当前的操作是 添加还是 编辑。
     *      然后才能发送对应正确的请求进行处理。
     * 解决：
     *      添加功能的 <input type="hidden" name="rid"> 中是没有值的。值为 ""。
     *      编辑功能的 <input type="hidden" name="rid"> 中是有rid值的。
     *      因此，可以获取input的值来做一个判断。
     * */
    $("#dialog").dialog({
        width:600,
        height:600,
        closed:true, /**对话框默认情况下是：false：打开状态。true：关闭*/
        buttons:[{ /**给对话框添加按钮*/
            text:'保存',
            handler:function(){ /**点击保存按钮后，提交表单*/
                /**判断当前是保存操作还是编辑操作*/
                var rid = $("[name='rid']").val();
                var url;
                if(rid){
                    /**编辑功能*/
                    url = "updateRole";
                }else {
                    /**保存操作*/
                    url = "saveRole";
                }

                /**提交表单保存*/
                $("#myform").form("submit",{
                    url:url,
                    onSubmit:function(param){/**提交表单数据，若想要传递额外的参数。就可以写一个onSubmit，参数为param*/
                        /**获取所有的已选权限*/
                        var allRows = $("#role_data2").datagrid("getRows");
                        /**遍历每一个已选权限*/
                        for (var i = 0; i < allRows.length; i++) {
                            /**取出每一个权限*/
                            var row = allRows[i];
                            /**给它封装到集合中去*/
                            param["permissions["+i+"].pid"] = row.pid;
                        }
                    },
                    success:function (data) {
                        data = $.parseJSON(data);//将JSON格式的字符串转为JavaScript对象。
                        if(data.success){
                            $.messager.alert("温馨提示",data.msg);
                            /**关闭对话框*/
                            $("#dialog").dialog("close");
                            /**重新加载一下数据表格 //更新表格数据展示*/
                            $("#role_dg").datagrid("reload");
                        }else {
                            $.messager.alert("温馨提示",data.msg);
                        }
                    }
                });
            }
        },{
            text:'关闭',
            handler:function(){
                $("#dialog").dialog("close");
            }
        }]
    });

    /** 监听添加角色按钮*/
    $("#add").click(function () {
        /**清空表单数据*/
        $("#myform").form("clear");
        /**清空已选权限的数据表格（做法是：让数据表格加载一个本地的空的记录行）*/
        $("#role_data2").datagrid("loadData",{rows:[]});
        /**设置标题*/
        $("#dialog").dialog("setTitle","添加角色");
        /**打开对话框*/
        $("#dialog").dialog("open");
    });

    /**待选权限列表*/
    $("#role_data1").datagrid({
        url:"permissionList",
        title:"所有权限",
        width:250,
        height:400,
        fitColumns:true,/**让宽度自动伸缩*/
        singleSelect:true,/**只能选中一行*/
        columns:[[
            {field:'pname',title:'权限名称',width:100,align:'center'}/*展示到数据表格上的列*/
        ]],
        /**监听每一行的点击。点击一行内容，执行回调*/
        onClickRow:function (rowIndex,rowData) {/**rowIndex:当前点击的是哪一行,rowData:点击的这一行的数据*/
            /**在向已选权限列表中追加权限时，要先做判断，判断是否已经追加过了该权限？*/
            //1.取出所有的已选权限
            var allRows = $("#role_data2").datagrid("getRows");
            //2.取出每一个已选权限进行判断
            for (var i = 0; i < allRows.length; i++) {
                var row = allRows[i];
                if(rowData.pid == row.pid){//已选列表中 已经存在该权限。
                    /**让已存在的权限，变为选中状态*/
                    //获取已经成为选中状态的当前角标
                    var index = $("#role_data2").datagrid("getRowIndex",row);
                    //让该行称为选中状态
                    $("#role_data2").datagrid("selectRow",index);
                    return;
                }
            }
            /**3.把当前选中的一行，添加到已选权限列表中*/
            $("#role_data2").datagrid("appendRow",rowData);
        }
    });

    /**已选权限列表*/
    $("#role_data2").datagrid({
        title:"已选权限",
        width:250,
        height:400,
        fitColumns:true,/**让宽度自动伸缩*/
        singleSelect:true,/**只能选中一行*/
        columns:[[
            {field:'pname',title:'权限名称',width:100,align:'center'}
        ]],/*每一列展示的内容*/
        onClickRow:function (rowIndex,rowData) {
            /**删除当前选中的行*/
            $("#role_data2").datagrid("deleteRow",rowIndex);
        }
    });

    /**监听编辑按钮的点击*/
    $("#edit").click(function () {
        /**获取当前选中的行，进行编辑*/
        var rowData = $("#role_dg").datagrid("getSelected");
        /**判断是否选中了一行*/
        if(rowData == null){
            $.messager.alert("提示","选择一行数据进行编辑");
            return;
        }
        /**回显角色数据。回显选中的那一行数据到form表单中*/
        $("#myform").form("load",rowData);

        /**回显权限数据。根据角色id，查询已选权限，进行数据回显*/
        /**加载当前角色下的权限*/
        var options = $("#role_data2").datagrid("options");//获取数据表格的所有属性
        options.url = "getPermissionByRid?rid=" + rowData.rid;
        /**重新加载数据（即：发一次新的请求）*/
        $("#role_data2").datagrid("load");

        /**设置对话框标题*/
        $("#dialog").dialog("setTitle","编辑角色");
        /**打开对话框*/
        $("#dialog").dialog("open");
    });
    
    /**监听删除按钮的点击*/
    $("#remove").click(function () {
        /**获取当前选中的行，进行删除*/
        var rowData = $("#role_dg").datagrid("getSelected");
        /**判断是否选中了一行*/
        if(rowData == null){
            $.messager.alert("提示","选择一行数据进行删除");
            return;
        }
        //提醒用户是否做删除的操作  //res用来接收用户的输入
        $.messager.confirm("确认","是否做删除操作",function (res) {
            //删除操作
            if(res){
                //发送删除请求，得到处理结果
                $.get("deleteRole?rid=" + rowData.rid,function (data) {
                    if(data.success){
                        $.messager.alert("温馨提示",data.msg);
                        /**关闭对话框*/
                        $("#dialog").dialog("close");
                        /**重新加载一下数据表格 //重新发送请求查询并展示当前页的数据*/
                        $("#role_dg").datagrid("reload");
                    }else {
                        $.messager.alert("温馨提示",data.msg);
                    }
                });
            }
        });
    });
});