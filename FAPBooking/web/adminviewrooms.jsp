<%-- 
    Author     : star
--%>

<%@page import="java.sql.ResultSet"%>
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
            
            ResultSet rs = (ResultSet) request.getAttribute("records");
            rs.beforeFirst(); // return pointer to default position
            boolean checkLast = (Boolean) request.getAttribute("checkLast");
            int pageNumber = (Integer) request.getAttribute("pageNumber");
        %>
        
        <h1>Table of Rooms</h1>
        
        <div>
            <table id="roomTable">
                <tr class="text-center">
                    <th>Room Number</th>
                    <th>Room Type</th>
                    <th>Room Status</th>
                </tr>
                
                <%
                    while (rs.next()) {
                %>
                    <tr class="text-center">
                        <td><%= rs.getString("room_no") %></td>
                        <td><%= rs.getString("room_type") %></td>
                        <td><%= rs.getString("room_status") %></td>
                    </tr>
                <%
                    }
                %>
            </table>
            <br>  
        </div>      
        
        <!--return button-->
        <div>
            <form method="post" action="Admin" id="returnButton">
                <input name="returnButton" type="submit" value="Return"/>
                <input name="action" type="hidden" value="Admin Dashboard"/>
                <input type="hidden" name="pageNumber" value="1"/>
                <input type="hidden" name="tableName" value="Reserve"/>
            </form>
        </div>
    </body>
</html>