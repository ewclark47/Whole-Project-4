<!--
Name: Elliott Clark and Eric Ryu
AndrewID: elliottc and ericryu
index.jsp
-->

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Trivia App</title>
</head>
<body>
<h1><%= "Eric and Elliott's Trivia Question Generator!" %>
</h1>
<br/>
<!-- Below linking method found at: https://stackoverflow.com/questions/22639896/from-one-jsp-link-to-another-jsp-file -->
<a href="${pageContext.request.contextPath}/trivia-app-dashboard.jsp" >Trivia Dashboard</a>
</body>
</html>