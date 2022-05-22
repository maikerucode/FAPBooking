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
        
        <h1>Table of Users</h1>
        
        <div>
            <table id="userTable">
                <tr class="text-center">
                    <th>Email</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Role</th>
                    <th>Reference Number</th>
                </tr>
                
                <%
                    while (rs.next()) {
                %>
                    <tr class="text-center">
                        <td><%= rs.getString("email") %></td>
                        <td><%= rs.getString("firstName") %></td>
                        <td><%= rs.getString("lastName") %></td>
                        <td><%= rs.getString("role") %></td>
                        <td><%= rs.getString("refNumber") %></td>
                        <td>
                            <div>
                                <form name="DeleteButton" method="post" action="Admin"/>
                                    <input name="action" type="submit" value="Delete"/>
                                    <input name="email" type="hidden" value="<%=rs.getString("email")%>"/>
                                    <input type="hidden" name="tableName" value="User"/>
                                </form>
                            </div>
                        </td>
                    </tr>
                <%
                    }
                %>
            </table>
            <br>
            
            <form method="post" action="Admin" id="backButton">
                <input type="submit" name="action" value="Back"/>
                <% int valBack = pageNumber - 1; %>
                <input type="hidden" name="pageNumber" value="<%=valBack%>"/>
                <input type="hidden" name="tableName" value="User"/>
            </form>
            <br>
            
            <form method="post" action="Admin" id="nextButton">
                <input type="submit" name="action" value="Next"/>
                <% int valNext = pageNumber + 1; %>
                <input type="hidden" name="pageNumber" value="<%=valNext%>"/>
                <input type="hidden" name="tableName" value="User"/>
            </form>   
        </div>
        
        <h2>Add an admin account</h2>        
                
        <!--add an admin button-->
        <form name="Add Button" method="post" action="Admin"/>
            <p>Email</p>
            <input name="email" type="text" size="40" required/>
            
            <p>First Name</p>
            <input name="firstName" type="text" size="40" required/>
            
            <p>Last Name</p>
            <input name="lastName" type="text" size="40" required/>
            
            <p>Password</p>
            <input name="password" type="password" size="40" required/>
            <br><br>
            
            <input name="action" type="submit" value="Add an Admin"/>
            <input type="hidden" name="tableName" value="User"/>
        </form>        
        <br><br>
        
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
<script>
var pageNumber = "<%=pageNumber%>";
var checkLast = "<%=checkLast%>";
console.log("script checkLast: " + checkLast);
var backButton = document.getElementById("backButton");
var nextButton = document.getElementById("nextButton");
<!--document.write("pageNumber: " + pageNumber);-->
<!--document.write("checkLast: " + checkLast);-->

<!--hide backButton if current page number is 1-->
if (pageNumber == "1") {
    backButton.style.display = "none";
} else {
    backButton.style.display = "block";
}

<!--hide nextButton if last page-->
if (checkLast === "true") {
    nextButton.style.display = "none";
} else {
    nextButton.style.display = "block";
}
</script>    

<!--
references:
pagination for DB records: https://www.javatpoint.com/pagination-in-jsp
-->