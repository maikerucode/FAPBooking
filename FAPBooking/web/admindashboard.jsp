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
            if (account == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            ResultSet rs = (ResultSet) request.getAttribute("records");
            rs.first(); // return pointer to default position
            boolean checkLast = (Boolean) request.getAttribute("checkLast");
            int pageNumber = (Integer) request.getAttribute("pageNumber");
        %>

        <header class="w3-display-container w3-content w3-center" style="max-width:1600px">
            <img class="w3-image" src="https://i.imgur.com/CvGZnaN.jpg" alt="Me" width="1600" height="200" style="max-width: 100%">
            <div class="w3-display-middle w3-padding w3-border w3-wide w3-text-light-grey w3-center w3-hide-medium w3-hide-small">
                <h1 class="w3-hide-medium w3-hide-small w3-xxlarge w3-marcellus">University Inn</h1>
                <h3 class="w3-hide-medium w3-hide-small">yes. welcome.</h3>
            </div>

            <div class="w3-display-middle w3-padding-small w3-border w3-text-light-grey w3-center w3-hide-large">
                <h5 class="w3-hide-large w3-marcellus" style="white-space:nowrap; bottom:-50px">University Inn</h5>
            </div>

            <div class="w3-row w3-bar w3-display-bottommiddle w3-deep-orange w3-hide-small w3-hide-medium" style="bottom:-16px">
                <a href="welcome.jsp" class="w3-bar-item w3-button">Home</a>
                <a href="about.jsp" class="w3-bar-item w3-button">About</a>
                <a href="booking.jsp" class="w3-bar-item w3-button">Bookings</a>
                <a href="login.jsp" class="w3-bar-item w3-button">Login</a>
            </div>

            <div class="w3-center w3-deep-orange w3-hide-large" style="max-height:30px" style="bottom:-16px">
                <div class="w3-row w3-bar w3-deep-orange" style="max-height:30px">
                    <a href="welcome.jsp" class="w3-bar-item w3-button" style="font-size: 10px;">Home</a>
                    <a href="about.jsp" class="w3-bar-item w3-button" style="font-size: 10px;">About</a>
                    <a href="booking.jsp" class="w3-bar-item w3-button" style="font-size: 10px;">Bookings</a>
                    <a href="login.jsp" class="w3-bar-item w3-button" style="font-size: 10px;">Login</a>
                </div>
            </div>
        </header>

        <!--TBU...-->

        <div class="w3-content" style="max-width:700px;">

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
                        <td><%= rs.getString("email")%></td>
                        <td><%= rs.getString("room_no")%></td>
                        <td><%= rs.getDate("check_in")%></td>
                        <td><%= rs.getDate("check_out")%></td>
                        <td><%= rs.getObject("total_charge")%></td>
                        <td><%= rs.getString("reserve_status")%></td>
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
        </div>
        <footer class="w3-container w3-padding-32 w3-center w3-opacity w3-black w3-xlarge">
            <p class="w3-medium"><a href="#">Back to Top</a></p>
        </footer>
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