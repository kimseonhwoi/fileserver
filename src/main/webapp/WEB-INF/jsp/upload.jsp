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
        <th>파일삭제</th>
        <th>파일번호</th>
        <th>파일이름</th>
        <th>파일설명</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach var="file" items="${fileList}" varStatus="status">
        <tr>
            <th><a href="javascript:void(0);" onclick="fnDelete(${file.sn});">X</a></th>
            <td>${status.count}</td>
            <td><a href="/fileDownload?sn=${file.sn}">${file.fileName}</a></td>
            <td>${file.content}</td>
        </tr>
        </c:forEach>
    </tbody>
    <tfoot>
    <tr>
        <td>파일: <input type="file" name="inputFile" id="inputFile"/></td>
        <td>파일 설명: <input type="text" name="inputText" id="inputText"/></td>
        <td colspan="2"><button onclick="fnUploadFile()" type="button" name="submitBtn" id="submitBtn">전송</button></td>
    </tr>
    </tfoot>
</table>

<script>
    function fnUploadFile(){
        let inputFile = document.getElementById("inputFile").files[0];
        let inputText = document.getElementById("inputText").value;
        let formData = new FormData();
        formData.append('file', inputFile);
        formData.append('content', inputText);
        fetch('/fileUpload', {
            method: 'POST',
            body: formData
        })
        .then(response => response.json())
        .then(data => {
            if(data === 200){
                location.reload();
            }
        })
        .catch(error => {
            console.error(error)
        })
    }
    function fnDelete(sn){
        let formData = new FormData();
        formData.append('sn', sn);
        fetch('/fileDelete', {
            method: 'POST',
            body: formData
        })
        .then(response => response.json())
        .then(data => {
            if(data === 200){
                location.reload();
            }
        })
        .catch(error => {
            console.error(error)
        })
    }
</script>
</body>
</html>
