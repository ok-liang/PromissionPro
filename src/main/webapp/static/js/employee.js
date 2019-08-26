/**页面加载完成后，再执行function中的内容。*/
$(function () {

    /**1、员工数据列表*/
    /**把#dg元素作为 数据表格*/
    /**发送请求，获取JSON格式的数据进行展示到employee.jsp中*/
    $("#dg").datagrid({
        url:"employeeList", /**发送请求获取JSON数据，加载展示到数据表格中。*/
        columns:[[
            {field:'username',title:'姓名',width:100,align:'center'},
            {field:'inputtime',title:'入职时间',width:100,align:'center'},
            {field:'tel',title:'电话',width:100,align:'center'},
            {field:'email',title:'邮箱',width:100,align:'center'},
            /**该列对应的值是employee对象的department值，展示的是department.name的值。*/
            {field:'department',title:'部门',width:100,align:'center',formatter:function (value,row,index) {
                    if(value){
                    return value.name;
                }
                }},
            /**该列对应的值是employee对象的state值,类型为boolean。我们要展示的是不同boolean值对应的值*/
            {field:'state',title:'状态',width:100,align:'center',formatter:function (value,row,index) { /**数据格式化*/
                if(row.state){
                    return "在职";
                }else {
                    return "<font style='color: red'>离职</font>";
                }
                }},
            /**该列对应的值是employee对象的属性admin值,类型为boolean。我们要展示的是不同boolean值对应的值*/
            {field:'admin',title:'管理员',width:100,align:'center',formatter:function (value,row,index) { /**数据格式化*/
                    if(row.admin){
                        return "是";
                    }else {
                        return "<font style='color: red'>否</font>";
                    }
                }},
        ]],/**每一列展示的内容*/
        fit:true,/**让数据表格占满整个页面*/
        fitColumns:true,/*让宽度自动伸缩*/
        rownumbers:true,/*每条数据展示行号*/
        pagination:true,/*显示分页导航*/
        singleSelect:true,/*只能选择一行进行编辑*/
        striped:true,
        toolbar:"#tb",/*把一个Div当作数据表格的工具栏*/
        onClickRow:function (rowIndex,rowData) {
            //判断当前行是否是离职状态
            if(!rowData.state){
                //把离职的按钮禁用
                $("#delete").linkbutton("disable");
            }else{
                //启用离职按钮
                $("#delete").linkbutton("able");
            }
        }
    });


    /**2、1监听添加按钮的点击_*/
    $("#add").click(function () {
        //设置对话框的标题
        $("#dialog").dialog("setTitle","添加员工");
        //让密码<tr>标签展示出来
        $("#password").show();
        //开启密码验证
        $("[name='password']").validatebox({required:true});
        //每次弹出对话框之前，先清空表单数据。
        $("#employeeForm").form("clear");
        //打开对话框
        $("#dialog").dialog("open");
    });

    /**2、2监听编辑按钮的点击*/
    $("#edit").click(function () {
        /**获取当前选中的行，进行编辑*/
        var rowData = $("#dg").datagrid("getSelected");
        if(rowData == null){
            $.messager.alert("提示","选择一行数据进行编辑");
            return;
        }
        //取消密码验证
        $("[name='password']").validatebox({required:false});
        //让密码<tr>隐藏
        $("#password").hide();
        //每次弹出对话框之前，先清空表单数据。
        $("#employeeForm").form("clear");
        //弹出对话框
        $("#dialog").dialog("setTitle","编辑员工");
        $("#dialog").dialog("open");

        /**回显部门数据*/
        /**因为数据表格中只有name=department，没有name为department.id。//因此要给rowData中添加一个name为department.id的属性。
        //这样rowData中的数据才能 与对话框中的form表单匹配到。才能把数据表格中选中行的数据 正确的回显到对话框中。*/
        if(!rowData["department"]){
            rowData["department.id"] = "";
        }else {
            rowData["department.id"] = rowData["department"].id;
        }
        /**回显管理员*/
        /**因为rowData中name=admin的值为boolean类型，要把boolean类型转为String类型，才能去正确的显示数据。*/
        rowData["admin"] = rowData["admin"]+"";

        /**回显角色*/
        /**根据当前用户的id，查出对应的角色信息*/
        $.get("getRoleByEid?id=" + rowData.id,function (data) {
            /**设置下拉列表数据回显*/
            $("#role").combobox("setValues",data);
        });

        /**让选中的那行数据中的基础数据回显到form表单中*/
        $("#employeeForm").form("load",rowData);
    });


    /**2、3监听离职按钮的点击*/
    $("#delete").click(function () {
        /**获取当前选中的行，进行设置离职State*/
        var rowData = $("#dg").datagrid("getSelected");
        if(rowData == null){
            $.messager.alert("提示","选择一行数据进行设置");
            return;
        }

        //提醒用户是否做离职的操作  //res用来接收用户的输入
        $.messager.confirm("确认","是否做离职操作",function (res) {
            //离职操作
            if(res){
                $.get("updateState?id="+rowData.id,function (data) {
                    if(data.success){
                        $.messager.alert("温馨提示",data.msg);
                        /**重新加载一下数据表格 //更新表格数据展示*/
                        $("#dg").datagrid("reload");
                    }else {
                        $.messager.alert("温馨提示",data.msg);
                    }
                });
            }
        })
    });

    /**2、4监听搜索按钮的点击*/
    $("#searchbtn").click(function () {
        //获取文本框的内容
        var keyword = $("[name='keyword']").val();
        /**重新加载数据表格(即：重新发送一次查询请求),把参数keyword传过去*/
        $("#dg").datagrid("load",{keyword:keyword});
    });

    /**2、5监听刷新点击按钮*/
    $("#reload").click(function () {
        //清空搜索的内容
        $("[name='keyword']").val("");
        //重新加载数据
        $("#dg").datagrid("load",{});
    });

    /**3、保存和编辑对话框——————都用同一个对话框*/
    /**把#dialog元素当作对话框*/
    $("#dialog").dialog({
        width:350,
        height:380,
        closed:true, /**对话框默认情况下是：false：打开状态*/
        buttons:[{ /**给对话框添加按钮*/
            text:'保存',
            handler:function(){ /**点击保存按钮后，提交表单*/
            /**判断当前是插入 还是编辑*/
            var id = $("[name='id']").val();
            var url;
            if(id){
                /**编辑功能*/
                url = "updateEmployee";
            }else {
                /**插入功能*/
                url = "saveEmployee";
            }
            $("#employeeForm").form("submit",{
                url:url,
                onSubmit:function(param){ /**提交表单数据时，若想要顺带的提交额外的参数，就要这样写*/
                    /**获取选中的多个角色,进行封装*/
                    var values = $("#role").combobox("getValues");/**获取下拉列表中的多个项*/
                    for (var i = 0; i < values.length; i++) {
                        var rid = values[i]; /**获取每一个角色的id//因为我们指定过了展示的是name，而用的是rid。*/
                        param["roles["+i+"].rid"] = rid;
                    }
                },
                success:function (data) {
                    data = $.parseJSON(data); /**将JSON格式的字符串转为json对象*/
                    if(data.success){
                        $.messager.alert("温馨提示",data.msg);
                        /**关闭对话框*/
                        $("#dialog").dialog("close");
                        /**重新加载一下数据表格 更新表格数据展示*/
                        $("#dg").datagrid("reload");
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

    /**部门选择 下拉列表*/
    /**把#department 当作下拉框*/
    $("#department").combobox({
        width:150,
        panelHeight:'auto',
        editable:false,
        url:'departList',   /**发送请求从服务器获取数据展示到下拉列表中，且展示的是department的name，提交的是department的id*/
        textField:'name',
        valueField:'id',
        onLoadSuccess:function(){   /**下拉列表中的数据加载完毕后，触发此方法来展示placeholder*/
            $("#department").each(function(i){
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if(targetInput){
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        }
    });

    /**是否为管理员 下拉列表*/
    $("#state").combobox({
        width:150,
        panelHeight:'auto',
        textField:'label', /**把选项中的value属性的值 展示到下拉列表中*/
        valueField:'value', /**把选项中的value属性的值 传给服务器*/
        editable:false, /**让下拉框不能进行编辑。 //默认情况下是可以编辑的*/
        data:[{ /**展示的选项*/
            label:'是',
            value:'true'
        },{
            label:'否',
            value:'false'
        }],
        onLoadSuccess:function(){   /**下拉列表中的数据加载完毕后，触发此方法来展示placeholder*/
            $("#state").each(function(i){
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if(targetInput){
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        }
    });

    /**选择角色的下拉列表*/
    $("#role").combobox({
        width:150,
        panelHeight:'auto',
        editable:false,
        url:'roleList',   /**发送请求从服务器获取数据展示到下拉列表中，且展示的是角色的name，提交的是角色的id*/
        textField:'rname',//展示rname
        valueField:'rid',//提交和获取的是rid
        multiple:true, //支持多选
        onLoadSuccess:function(){   /**下拉4r列表中的数据加载完毕后，触发此方法来展示placeholder*/
            $("#role").each(function(i){
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if(targetInput){
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        }
    });
});