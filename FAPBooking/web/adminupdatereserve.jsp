<%-- 
    Author     : star
--%>

<%@page import="model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Dashboard</title>
    </head>
    <body>
        <%
            User account = (User) session.getAttribute("user");
            if (account == null || !account.getRole().equals("Admin")) {
                response.sendRedirect("login.jsp");
                return;
            }
            String email = (String) request.getAttribute("email");
        %>
        
        <h1>Update Reservation</h1>
        
        <div>
            <form name="UpdateRecordButton" method="post" action="Admin">
                <p>email</p>
                <input id="emailInput" type="text" size="40" disabled/> 

                <p>Reservation Status</p>
                <select name="reserveStatus">
                    <option value="Ongoing">Ongoing</option>
                    <option value="Done">Done</option>
                </select>
                <br><br>
                
                <p>Reference Number</p>
                <input name="refNumber" type="text" size="60" required/> 
                <br><br>
                
                <input name="action" type="submit" value="Update Reservation"/>
                <input type="hidden" name="email" value="<%=email%>"/>
                <input type="hidden" name="tableName" value="Reserve"/>
            </form>
        </div>
    </body>
</html>
<script>
var email = "<%=email%>";
document.getElementById("emailInput").value = email
</script>