<%-- 
    Document   : home
    Created on : 04 6, 22, 4:04:34 PM
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

            String role = account.getRole();
            System.out.println("role is: " + role);
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
                <a href="#" class="w3-bar-item w3-button w3-light-grey">Login</a>
            </div>

            <div class="w3-center w3-deep-orange w3-hide-large" style="max-height:30px" style="bottom:-16px">
                <div class="w3-row w3-bar w3-deep-orange" style="max-height:30px">
                    <a href="welcome.jsp" class="w3-bar-item w3-button" style="font-size: 10px;">Home</a>
                    <a href="about.jsp" class="w3-bar-item w3-button" style="font-size: 10px;">About</a>
                    <a href="booking.jsp" class="w3-bar-item w3-button" style="font-size: 10px;">Book Now</a>
                    <a href="#" class="w3-bar-item w3-button w3-light-grey" style="font-size: 10px;">Login</a>
                </div>
            </div>
        </header>

        <br><br><br>
        <main>
            <div align="center">
                <!--logout button-->
                <form method="post" action="Logout">
                    <input type="submit" name="action" class="w3-btn w3-round w3-deep-orange" value="Logout" style="width:180px"/>
                </form>
                <br>

                <!--user dashboard button-->
                <form method="get" action="User">
                    <input type="submit" name="action" class="w3-btn w3-round w3-deep-orange" value="User Dashboard" style="width:180px"/>
                </form>
                <br>

                <!--booking button-->
                <form method="post" action="Booking">
                    <input type="submit" name="action" class="w3-btn w3-round w3-deep-orange" value="Book a Room" style="width:180px"/>
                </form>
                <br>

                <!--admin dashboard button-->
                <form method="post" action="Admin" id="adminForm">
                    <input type="submit" name="action" class="w3-btn w3-round w3-deep-orange" value="Admin Dashboard" style="width:180px"/>
                    <input type="hidden" name="pageNumber" value="1"/>
                    <input type="hidden" name="tableName" value="Reserve"/>
                </form>
                <br><br>
            </div>
        </main>
        <footer class="w3-container w3-padding-32 w3-center w3-opacity w3-black w3-xlarge" style="bottom: 0; width: 100%;">
            <p class="w3-medium"><a href="#">Back to Top</a></p>
        </footer>
    </body>

    <script>
        var role = "<%=role%>";
        var adminForm = document.getElementById("adminForm");
<!--document.write("JavaScript role:" + role);-->

<!--show admin dashboard button if user is an admin-->
        if (role === "Guest") {
            adminForm.style.display = "none";
        } else {
            adminForm.style.display = "block";
        }

<!--refresh the page once if the back button is used-->
        window.addEventListener("pageshow", function (event) {
            var historyTraversal = event.persisted ||
                    (typeof window.performance != "undefined" &&
                            window.performance.navigation.type === 2);
            if (historyTraversal) {
                // Handle page restore.
                window.location.reload();
            }
        });

<!--prevent form resubmission if page is refreshed-->
        if (window.history.replaceState) {
            window.history.replaceState(null, null, window.location.href);
        }
    </script>
</html>

<!--
references:
page refresh: https://stackoverflow.com/questions/43043113/how-to-force-reloading-a-page-when-using-browser-back-button
JS to prevent resubmission of form: https://stackoverflow.com/questions/6320113/how-to-prevent-form-resubmission-when-page-is-refreshed-f5-ctrlr
hide & show content: https://www.washington.edu/accesscomputing/webd2/student/unit5/module2/lesson5.html
-->