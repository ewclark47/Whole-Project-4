<!--
Name: Elliott Clark and Eric Ryu
AndrewID: elliottc and ericryu
index.jsp
-->

<%@ page import="com.example.project4task2.DatabaseHandling" %>
<%@ page import="com.mongodb.client.MongoCollection" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Trivia App Dashboard</title>
</head>
<body>
<h1> Dashboard </h1>
<%
    DatabaseHandling dbh = new DatabaseHandling();
    dbh.mongoDBConnect();
    String avgQ = dbh.averageNumQuestions();
    String avgT = dbh.averageTime();
    String mostC = dbh.mostPopularCategory();
%>
<h3> Analytics: </h3>
<table>
    <tr>
        <td>Average Number of Questions: </td>
        <td> <%=avgQ%></td>
    </tr>
    <tr>
        <td>Average Time Taken to Retrieve Questions: </td>
        <td> <%=avgT%></td>
    </tr>
    <tr>
        <td>Most Popular Category: </td>
        <td> <%=mostC%></td>
    </tr>
</table>
<br>
<h3> Logs: </h3>
<table class="log_table">
    <thead>
    <tr>
        <th>Category</th>
        <th>Number of Questions</th>
        <th>Type of Questions</th>
        <th>Question Difficulty</th>
        <th>Time Needed to Retrieve Questions</th>
        <th>Connection Method</th>
    </tr>
    </thead>
    <tbody>
        <%
            String allDocs = dbh.displayDocs( "Trivia App Info" );
            String[] sepDocs = allDocs.split( "\n" );
            for (String doc : sepDocs){
                String[] seperatedDoc = doc.split( "," );
                for(String field: seperatedDoc){
                    if (field.contains( "Category=" )){ %>
        <tr>
            <td> <%= field.replace("Category=","")%> </td>
                <%} else if(field.contains("Number of Questions Requested=")){ %>
            <td> <%= field.replace("Number of Questions Requested=", "")%> </td>
                <%} else if(field.contains("Type of Questions=")){ %>
            <td> <%= field.replace("Type of Questions=", "")%> </td>
                <%} else if(field.contains("Question Difficulty=")){ %>
            <td> <%= field.replace("Question Difficulty=", "")%> </td>
                <%} else if(field.contains("Time Needed to Retrieve Questions=")){ %>
            <td> <%= field.replace("Time Needed to Retrieve Questions=", "")%> </td>
                <%} else if(field.contains("App Connection Method=")){ %>
            <td> <%= field.replace("App Connection Method=", "").replace("}}","")%> </td>
        <tr>
    <% }
    }
}%>
    </tbody>
</table>
</body>
</html>