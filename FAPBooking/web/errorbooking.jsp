<%-- 
    Author     : star
--%>

<%@page import="model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
    </head>
    <body>
        <%
            User account = (User) session.getAttribute("user");
            if (account == null) {
                response.sendRedirect("login.jsp");
                return;
            }
        %>
        
        <h1 style="color:red">There are not enough rooms to satisfy
            your booking requirements...</h1>
        <p style="color:red">You may try other dates instead</p>
        <br>
        
        <a href="booking.jsp">Return to Booking Page</a>
    </body>
</html>
