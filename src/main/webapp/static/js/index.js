$(function () {
    /**默认让标签是全填充*/
    $("#tabs").tabs({
        fit:true
    });

    /**展示树状的菜单列表*/
    $('#tree').tree({
        url:'static/tree.json',
        lines:true,
        onSelect: function(node){
            /*在添加之前, 做判断  判断这个标签是否存在 */
            var exists = $("#tabs").tabs("exists",node.text);
            if(exists){
                /*存在,就让它选中*/
                $("#tabs").tabs("select",node.text);
            }else {
                if (node.url !=''&& node.url !=null){
                    /*如果不存在 ,添加新标签*/
                    $("#tabs").tabs("add",{
                        title:node.text,
                        content:"<iframe src="+node.url+" frameborder='0' width='100%' height='100%'></iframe>", /*发送请求*/
                        closable:true
                    })
                }
            }
        },

        /*页面成功加载后，我让第一个节点（员工管理）处于选中状态。//作用是：页面一加载就能看到的employee数据*/
        onLoadSuccess: function (node, data) {
            console.log(data[0].children[0].id);
            if (data.length > 0) {
                //找到第一个元素
                var n = $('#tree').tree('find', data[0].children[0].id);
                //调用选中事件的方法
                $('#tree').tree('select', n.target);
            }
        }
    });
});