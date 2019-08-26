<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>菜单页面</title>
    <!-- 引入公共的标签 。因为是file属性，它是在服务器的项目中把静态资源直接包含进来，然后再响应给客户端，因此不需要加上"$[{}]。 " -->
    <%@include file="/static/common/common.jsp"%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/menu.js"></script>
</head>
<body>
    <h1>菜单页面</h1>
</body>
</html>
