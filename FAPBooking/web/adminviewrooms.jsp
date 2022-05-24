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

            th {
                padding-bottom: 10px;
                padding-top: 10px;
            }

            th {
                padding-bottom: 10px;
                padding-top: 10px;
            }

            table, th, td {
                border: 2px solid black;
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
        <header class="w3-display-container w3-content w3-center" style="max-width:1600px">
            <img class="w3-image" src="https://i.imgur.com/CvGZnaN.jpg" alt="Me" width="1600" height="200" style="max-height:450px">
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
                <a href="booking.jsp" class="w3-bar-item w3-button">Book Now</a>
                <a href="#" class="w3-bar-item w3-button w3-light-grey">Login/Dashboard</a>
            </div>

            <div class="w3-center w3-deep-orange w3-hide-large" style="max-height:30px" style="bottom:-16px">
                <div class="w3-row w3-bar w3-deep-orange" style="max-height:30px">
                    <a href="welcome.jsp" class="w3-bar-item w3-button" style="font-size: 10px;">Home</a>
                    <a href="about.jsp" class="w3-bar-item w3-button" style="font-size: 10px;">About</a>
                    <a href="booking.jsp" class="w3-bar-item w3-button" style="font-size: 10px;">Book Now</a>
                    <a href="#" class="w3-bar-item w3-button w3-light-grey" style="font-size: 10px;">Login/Dashboard</a>
                </div>
            </div>
        </header>
        <br><br>

        <div class="w3-content w3-center w3-text-light-grey">
            <h1>Table of Rooms</h1>
            <br>
            <table id="roomTable" class="w3-responsive w3-bordered w3-center w3-hide-small w3-hide-medium w3-white" align="center" style="max-width:450px;">
                <tr class="text-center w3-deep-orange">
                    <th style="padding-left:20px; padding-right:20px">Room Number</th>
                    <th style="padding-left:20px; padding-right:20px">Room Type</th>
                    <th style="padding-left:20px; padding-right:20px">Room Status</th>
                </tr>

                <%
                    while (rs.next()) {
                %>
                <tr class="text-center">
                    <td style="padding-left:20px; padding-right:20px"><%= rs.getString("room_no")%></td>
                    <td style="padding-left:20px; padding-right:20px"><%= rs.getString("room_type")%></td>
                    <td style="padding-left:20px; padding-right:20px"><%= rs.getString("room_status")%></td>
                </tr>
                <%
                    }
                %>
            </table>
            <div>
                <br><br>
                <!--return button-->
                <form method="post" action="Admin" id="returnButton">
                    <input name="returnButton" type="submit" value="Return" class="w3-btn w3-round w3-deep-orange"/>
                    <input name="action" type="hidden" value="Admin Dashboard"/>
                    <input type="hidden" name="pageNumber" value="1"/>
                    <input type="hidden" name="tableName" value="Reserve"/>
                </form>
            </div>
        </div>
        <br>  
        <footer class="w3-container w3-padding-32 w3-center w3-opacity w3-black w3-xlarge" style="bottom: 0; width: 100%;">
            <p class="w3-medium"><a href="#">Back to Top</a></p>
        </footer>
    </body>
</html>