<%--
    Document   : login
    Created on : 03 22, 22, 2:49:38 AM
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
            if (account != null) {
                response.sendRedirect("home.jsp");
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

        <br><br>
        <main>
            <div class="loginForm w3-text-light-grey w3-marcellus">
                <h1 class="w3-marcellus">Login</h1>

                <form method="post" action="Login">
                    <p style="font-size: 20px">email</p>
                    <input name="email" type="text" size="40" required/>

                    <p style="font-size: 20px">password</p>
                    <input name="password" type="password" size="40" required/>
                    <br>

                    <p style="font-size: 20px">captcha</p>
                    <img class="w3-border" id="captcha" src="captcha.png">
                    <br><br>

                    <input type="text" name="captchaAnswer" placeholder="Enter here..." required/>
                    <br><br>

                    <input type="submit" name="action" value="Login" class="w3-btn w3-round w3-deep-orange"/>
                    <br><br>
                </form>

                <a href="signup.jsp">Not registered yet? Sign up here</a>
                <br><br>
            </div>
        </main>
        <footer class="w3-container w3-padding-32 w3-center w3-opacity w3-black w3-xlarge" style="bottom: 0; width: 100%;">
            <p class="w3-medium"><a href="#">Back to Top</a></p>
        </footer>

    </body>
</html>

<!--refresh the page once if the back button is used-->
<script>
    window.addEventListener("pageshow", function (event) {
        var historyTraversal = event.persisted ||
                (typeof window.performance != "undefined" &&
                        window.performance.navigation.type === 2);
        if (historyTraversal) {
            // Handle page restore.
            window.location.reload();
        }
    });
</script>

<!--
reference:
https://stackoverflow.com/questions/43043113/how-to-force-reloading-a-page-when-using-browser-back-button
-->
