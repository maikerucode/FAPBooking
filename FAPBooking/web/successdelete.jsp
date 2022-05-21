<%-- 
    Author     : star
--%>

<%@page import="model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Message Page</title>
    </head>
    <body>
        <%
            User account = (User) session.getAttribute("user");
            if (account == null || !account.getRole().equals("Admin")) {
                response.sendRedirect("login.jsp");
                return;
            }
        %>
        
        <h1>Deletion of record is successful!</h1>
        
        <br>
        <form method="post" action="Admin" id="adminForm">
            <input name="returnButton" type="submit" value="Return"/>
            <input name="action" type="hidden" value="Admin Dashboard"/>
            <input type="hidden" name="pageNumber" value="1"/>
            <input type="hidden" name="tableName" value="Reserve"/>
        </form>
    </body>
</html>
