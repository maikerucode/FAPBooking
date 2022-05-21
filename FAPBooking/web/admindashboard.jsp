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
            if (account == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            ResultSet rs = (ResultSet) request.getAttribute("records");
            rs.first(); // return pointer to default position
            boolean checkLast = (Boolean) request.getAttribute("checkLast");
            int pageNumber = (Integer) request.getAttribute("pageNumber");
        %>
        
        <!--TBU...-->
        
        <h1>Table of Reservations</h1>
        <!--display table of reservations w/ edit/delete button-->
        <!--when edit button click, go to another JSP-->
        
        <div>
            <table id="reserveTable">
                <tr class="text-center">
                    <th>Email</th>
                    <th>Room Number</th>
                    <th>Check-in</th>
                    <th>Check-out</th>
                    <th>Total Charge</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                
                <%
                    while (rs.next()) {
                %>
                    <tr class="text-center">
                        <td><%= rs.getString("email") %></td>
                        <td><%= rs.getString("room_no") %></td>
                        <td><%= rs.getDate("check_in") %></td>
                        <td><%= rs.getDate("check_out") %></td>
                        <td><%= rs.getObject("total_charge") %></td>
                        <td><%= rs.getString("reserve_status") %></td>
                        <td>
                            <div>
                                <form name="UpdateButton" method="post" action="Admin">
                                    <input name="action" type="submit" value="Update"/>
                                    <input name="email" type="hidden" value="reserveTable"/>
                                    <input name="jspName" type="hidden" value="reserveTable"/>
                                </form>
                                
                                <form name="DeleteButton" method="post" action="Admin"/>
                                    <input name="action" type="submit" value="Delete"/>
                                    <input name="email" type="hidden" value="<%=rs.getString("email")%>"/>
                                    <input name="totalCharge" type="hidden" value="<%=rs.getString("total_charge")%>"/>
                                    <input name="jspName" type="hidden" value="reserveTable"/>
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
                <% int valBack = pageNumber - 1;%>
                <input type="hidden" name="pageNumber" value="<%=valBack%>"/>
                <input type="hidden" name="tableName" value="Reserve"/>
            </form>
            <br>
            
            <form method="post" action="Admin" id="nextButton">
                <input type="submit" name="action" value="Next"/>
                <% int valNext = pageNumber + 1;%>
                <input type="hidden" name="pageNumber" value="<%=valNext%>"/>
                <input type="hidden" name="tableName" value="Reserve"/>
            </form>   
        </div>
        
        <!--button to jsp w/ display prices w/ edit buttons-->
        
        <!--button to jsp w/ display available rooms-->
        
        <!--(??) button to create new admin account-->
    </body>
</html>

<!--hide back button if page 1-->
<!--hide next button if last page-->
<!--use counters-->

<script>
var pageNumber = "<%=pageNumber%>";
var checkLast = "<%=checkLast%>";
console.log("script checkLast: " + checkLast);
var backButton = document.getElementById("backButton");
var nextButton = document.getElementById("nextButton");
<!--document.write("pageNumber: " + pageNumber);-->
<!--document.write("checkLast: " + checkLast);-->

<!--hide backButton if current page number is 1-->
if (pageNumber === "1") {
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