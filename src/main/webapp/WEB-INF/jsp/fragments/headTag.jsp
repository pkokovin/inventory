<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <title>Inventory application</title>
    <base href="${pageContext.request.contextPath}/"/>

    <link rel="stylesheet" href="resources/css/bootstrap.min.css">

    <%--http://stackoverflow.com/a/24070373/548473--%>
    <script type="text/javascript" src="resources/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="resources/js/bootstrap.min.js" defer></script>
</head>