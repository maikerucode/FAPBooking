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
        
        <h1>Table of Reservations</h1>

        <div>
            <table id="reserveTable">
                <tr class="text-center">
                    <th>Email</th>
                    <th>Room No.</th>
                    <th>Check-in</th>
                    <th>Check-out</th>
                    <th>Total Charge</th>
                    <th>Status</th>
                    <th>Action</th>
                    <th>Reference No.</th>
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
                                    <input name="email" type="hidden" value="<%=rs.getString("email")%>"/>
                                    <input type="hidden" name="tableName" value="Reserve"/>
                                </form>
                                
                                <form name="DeleteButton" method="post" action="Admin"/>
                                    <input name="action" type="submit" value="Delete"/>
                                    <input name="email" type="hidden" value="<%=rs.getString("email")%>"/>
                                    <input type="hidden" name="tableName" value="Reserve"/>
                                </form>
                            </div>
                        </td>
                        <td><%= rs.getString("ref_no") %></td>
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
                <input type="hidden" name="tableName" value="Reserve"/>
            </form>
            <br>
            
            <form method="post" action="Admin" id="nextButton">
                <input type="submit" name="action" value="Next"/>
                <% int valNext = pageNumber + 1; %>
                <input type="hidden" name="pageNumber" value="<%=valNext%>"/>
                <input type="hidden" name="tableName" value="Reserve"/>
            </form>   
        </div>
        <br><br><br>
                
        <!--button to jsp w/ room table -->
        <form name="ViewRoomsButton" method="post" action="Admin"/>
            <input type="submit" name="action" value="View Rooms"/>
            <input type="hidden" name="pageNumber" value="1"/>
            <input type="hidden" name="tableName" value="Room"/>
        </form>
        <br><br>
        
        <!--button to jsp w/ rate table -->
        <form name="ViewRoomsButton" method="post" action="Admin"/>
            <input type="submit" name="action" value="View Rates"/>
            <input type="hidden" name="pageNumber" value="1"/>
            <input type="hidden" name="tableName" value="Rate"/>
        </form>
        <br><br>
        
        <!--button to jsp w/ user table -->
        <form name="ViewUsersButton" method="post" action="Admin"/>
            <input type="submit" name="action" value="View Users"/>
            <input type="hidden" name="pageNumber" value="1"/>
            <input type="hidden" name="tableName" value="User"/>
        </form>
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

<!--refresh the page once if the back button is used-->
window.addEventListener( "pageshow", function ( event ) {
  var historyTraversal = event.persisted || 
                         ( typeof window.performance != "undefined" && 
                              window.performance.navigation.type === 2 );
  if ( historyTraversal ) {
    // Handle page restore.
    window.location.reload();
  }
});
</script>    

<!--
references:
pagination for DB records: https://www.javatpoint.com/pagination-in-jsp
-->