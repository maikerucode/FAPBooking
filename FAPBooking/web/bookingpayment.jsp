<%-- 
    Author     : star
--%>

<%@page import="model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            User account = (User) session.getAttribute("user");
            if (account == null) {
                response.sendRedirect("login.jsp");
                return;
            }
        %>
        
        <h1>Scan the QR Code to Pay</h1>
        <!--online payment here (ex: gcash)-->
        
        <p>TBA...</p>
        
        <form method="post" action="Booking">
<!--            if successful payment, redirect to bookingsuccess.jsp
            then pass value to indicate confirmed payment-->
            
            <input type="submit" name="action" value="Submit Payment"/>
        </form>
    </body>
</html>
