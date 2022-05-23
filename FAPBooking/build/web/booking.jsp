<%--
    Author     : star
--%>

<%@page import="java.time.Year"%>
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

            h1, h3, h5, h6, a, p {
                font-family: "Montserrat", serif;
            }

            .w3-marcellus {
                /* font-family: "Lobster", Sans-serif;*/
                font-family: 'Marcellus SC', serif;
            }

            input[type='text'] {
                font-family: "Montserrat", serif;
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

            .loginForm {
                text-align: center;
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
                <a href="#" class="w3-bar-item w3-button w3-light-grey">Book Now</a>
                <a href="login.jsp" class="w3-bar-item w3-button">Login</a>
            </div>

            <div class="w3-center w3-deep-orange w3-hide-large" style="max-height:30px" style="bottom:-16px">
                <div class="w3-row w3-bar w3-deep-orange" style="max-height:30px">
                    <a href="welcome.jsp" class="w3-bar-item w3-button" style="font-size: 10px;">Home</a>
                    <a href="about.jsp" class="w3-bar-item w3-button" style="font-size: 10px;">About</a>
                    <a href="#" class="w3-bar-item w3-button w3-light-grey" style="font-size: 10px;">Book Now</a>
                    <a href="login.jsp" class="w3-bar-item w3-button" style="font-size: 10px;">Login</a>
                </div>
            </div>
        </header>
        <!--booking button-->
        <br><br>
        <form method="post" action="Booking" class="w3-center w3-text-light-grey">
            <h3>length of stay</h3>

            <p>check-in date</p>
            <select name="inMonth">
                <%
                    String[] arrMo = new String[]{
                        "January", "February", "March", "April",
                        "May", "June", "July", "August",
                        "September", "October", "November", "December"
                    };
                    Year y = Year.now();
                    int i = 1;

                    for (String m : arrMo) {
                        // <option value='January'>January</option>
                        out.println("<option value='" + i + "'>"
                                + m + "</option>");
                        i++;
                    }
                %>
            </select>

            <select name="inDay">
                <%
                    for (int d = 1; d <= 31; d++) {
                        // <option value='24'>24</option>
                        out.println("<option value='" + d + "'>"
                                + d + "</option>");
                    }
                %>
            </select>

            <select name="inYear">
                <%
                    out.println("<option value='" + y + "'>"
                            + y + "</option>");
                    out.println("<option value='" + y.plusYears(1) + "'>"
                            + y.plusYears(1) + "</option>");
                %>
            </select>
            <br>
            <!-- =================================================== -->

            <p>check-out date</p>
            <select name="outMonth">
                <%
                    i = 1;
                    for (String m : arrMo) {
                        out.println("<option value='" + i + "'>"
                                + m + "</option>");
                        i++;
                    }
                %>
            </select>

            <select name="outDay">
                <%
                    for (int d = 1; d <= 31; d++) {
                        out.println("<option value='" + d + "'>"
                                + d + "</option>");
                    }
                %>
            </select>

            <select name="outYear">
                <%
                    out.println("<option value='" + y + "'>"
                            + y + "</option>");
                    out.println("<option value='" + y.plusYears(1) + "'>"
                            + y.plusYears(1) + "</option>");
                    out.println("<option value='" + y.plusYears(2) + "'>"
                            + y.plusYears(2) + "</option>");
                %>
            </select>
            <br><br>
            <!-- =================================================== -->

            <h3>room types</h3>

            <p>Single</p>
            <input name="roomSingle" type="number" min="0" max="10"
                   placeholder="0" value="0" step="1" required/>

            <p>Double</p>
            <input name="roomDouble" type="number" min="0" max="10"
                   placeholder="0" value="0" step="1" required/>

            <p>Triple</p>
            <input name="roomTriple" type="number" min="0" max="10"
                   placeholder="0" value="0" step="1" required/>

            <p>Quad</p>
            <input name="roomQuad" type="number" min="0" max="10"
                   placeholder="0" value="0" step="1" required/>
            <br><br><br>
        </form>

        <br><br>
        <footer class="w3-container w3-padding-32 w3-center w3-opacity w3-black w3-xlarge">
            <p class="w3-medium"><a href="#">Back to Top</a></p>
        </footer>
    </body>
</html>
