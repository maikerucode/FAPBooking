<%-- 
    Author     : star
--%>

<%@page import="model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>University Inn</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lobster&effect=shadow-multiple">

        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Marcellus+SC&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="errorStyles.css">
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
