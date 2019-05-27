<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
<#include "${request.contextPath}/common/meta.ftl">
    <title>Hello fremarker!</title>
</head>
<body>
<p>
    <!--获取后台传过来的数据-->
    <#if request.test?exists>
    </#if>
    <p id=''>Hello fremarker!${request.test!"default value"}</p>
</p>
</body>
</html>