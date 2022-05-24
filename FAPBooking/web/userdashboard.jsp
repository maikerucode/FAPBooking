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
            String refNo = (String) request.getAttribute("refNumber");
            if (refNo.equals("")) {
                refNo = "N/A";
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
        <div class="w3-content w3-center w3-text-light-grey" style="max-width:1300px;">
          <h1>Hello, <% out.print(account.getEmail()); %>!</h1>

          <h3 class="w3-marcellus">Full Name</h3>
          <p><% out.print(account.getFirstName()); %> <% out.print(account.getLastName()); %></p>

          <h3 class="w3-marcellus">Email</h3>
          <p><% out.print(account.getEmail()); %></p>

          <h3 class="w3-marcellus">Reference Number</h3>
          <p><%= refNo %></p>

          <h3>Update your payment's reference number</h3>
          <form name="UpdateRecordButton" method="post" action="User">
              <input name="refNumber" type="text" size="40" required/>
              <br><br>

                <input name="updateRefNumber" type="submit" value="Update" class="w3-btn w3-round w3-deep-orange"/>
                <input type="hidden" name="action" value="Update Reference Number"/>
            </form>

            <h3>Account Details</h3>
            <form name="GenerateAccountDetails" method="post" action="Report">
                <input name="action" type="submit" value="Get Report" class="w3-btn w3-round w3-deep-orange"/>
            </form>
            <br><br>
        </div>
            <footer class="w3-container w3-padding-32 w3-center w3-opacity w3-black w3-xlarge" style="bottom: 0; width: 100%;">
                <p class="w3-medium"><a href="#">Back to Top</a></p>
            </footer>
        </main>
    </body>
</html>
<script>
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
</script>

<!--
references:
page refresh: https://stackoverflow.com/questions/43043113/how-to-force-reloading-a-page-when-using-browser-back-button
-->
