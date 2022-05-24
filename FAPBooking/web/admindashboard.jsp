<%--
    Author     : star
--%>

<%@page import="java.sql.ResultSet"%>
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

        <style>
            body {
                background-color: #54240C;
                /*654827*/
                /*4d371d*/
            }

            h1, h3, h5, h6, a {
                font-family: "Montserrat", serif;
            }

            .w3-marcellus {
                /* font-family: "Lobster", Sans-serif;*/
                font-family: 'Marcellus SC', serif;
            }

            .navbar {
                justify-content: space-between;
            }

            .flexMain {
                display: flex;
                height: 768px;
                width: 90vw;
                margin: auto;
                flex-wrap:wrap;
            }

            .flexItem {
                background-color: green;
                width: 50%;
                height:33%;
            }

        </style>    

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
                        <td><%= rs.getString("GROUP_CONCAT(room_no SEPARATOR ', ')") %></td>
                        <td><%= rs.getDate("MAX(check_in)") %></td>
                        <td><%= rs.getDate("MAX(check_out)") %></td>
                        <td><%= rs.getObject("MAX(total_charge)") %></td>
                        <td><%= rs.getString("MAX(reserve_status)") %></td>
                        <td>
                            <div>
                                <form name="UpdateButton" method="post" action="Admin">
                                    <input name="action" type="submit" value="Update"/>
                                    <input name="email" type="hidden" value="<%=rs.getString("email")%>"/>
                                    <input type="hidden" name="tableName" value="Reserve"/>
                                </form>

                                <form name="DeleteButton" method="post" action="Admin"/>
                                    <input name="action" type ="submit" value="Delete"/>
                                    <input name="email" type="hidden" value="<%=rs.getString("email")%>"/>
                                    <input type="hidden" name="tableName" value="Reserve"/>
                                </form>
                            </div>
                        </td>
                        <td><%= rs.getString("MAX(ref_no)") %></td>
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
        </div>
        <footer class="w3-container w3-padding-32 w3-center w3-opacity w3-black w3-xlarge">
            <p class="w3-medium"><a href="#">Back to Top</a></p>
        </footer>
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
