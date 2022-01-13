<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>MAIN PAGE</title>
    <style>
        table {
            width:70%;
            margin-left:15%;
            margin-right:15%;
        }

        table, td, th {
            border-collapse : collapse;
            border : 1px solid black;
            text-align: center;
        }
    </style>
</head>
<body>
<table>
    <thead>
    <tr>
        <th>파일번호</th>
        <th>파일이름</th>
        <th>파일설명</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach var="file" items="${fileList}" varStatus="status">
        <tr>
            <td>${status.count}</td>
            <td><a href="/fileDownload?sn=${file.sn}">${file.fileName}</a></td>
            <td>${file.content}</td>
        </tr>
        </c:forEach>
    </tbody>
</table>

</body>
</html>
