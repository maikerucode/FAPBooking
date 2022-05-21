<%-- 
    Document   : welcome
    Created on : May 18, 2022, 4:06:48 PM
    Author     : michael
--%>

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
                <a href="#" class="w3-bar-item w3-button w3-light-grey">Home</a>
                <a href="about.jsp" class="w3-bar-item w3-button">About</a>
                <a href="#" class="w3-bar-item w3-button">Bookings</a>
                <a href="login.jsp" class="w3-bar-item w3-button">Login</a>
            </div>

            <div class="w3-center w3-deep-orange w3-hide-large" style="max-height:30px" style="bottom:-16px">
                <div class="w3-row w3-bar w3-deep-orange" style="max-height:30px">
                    <a href="#" class="w3-bar-item w3-button w3-light-grey" style="font-size: 10px;">Home</a>
                    <a href="about.jsp" class="w3-bar-item w3-button" style="font-size: 10px;">About</a>
                    <a href="#contact" class="w3-bar-item w3-button" style="font-size: 10px;">Bookings</a>
                    <a href="login.jsp" class="w3-bar-item w3-button" style="font-size: 10px;">Login</a>
                </div>
            </div>
        </header>
        <div class="w3-content" style="max-width:700px;">

            <div class="w3-container w3-margin-top w3-text-light-grey" id="rooms">
                <h3 class="w3-marcellus">Rooms and Services</h3>
                <p>Insert one-liner here</p>
            </div>

            <div class="w3-row-padding w3-padding-16 w3-text-light-grey">
                <div class="w3-half w3-margin-bottom">
                    <img class="w3-hide-small w3-hide-medium" src="http://drive.google.com/uc?export=view&id=117vU8v1NcQSv9YnyXzQj3092p8FhplvY" alt="Norway" style="width:100%">
                    <img class="w3-hide-large" src="http://drive.google.com/uc?export=view&id=1TpBVL0oKnw43ENgipoT-aoN-IHwS2eQr" alt="Norway" style="width:100%">
                    <div class="w3-container w3-white">
                        <h3>Single Room</h3>
<!--                        <h6 class="w3-opacity">From $99</h6>
                        <p>Single bed</p>
                        <p>15m<sup>2</sup></p>
                        <p class="w3-large"><i class="fa fa-bath"></i> <i class="fa fa-phone"></i> <i class="fa fa-wifi"></i></p>-->
                    </div>
                </div>
                <div class="w3-half w3-margin-bottom">
                    <img class="w3-hide-small w3-hide-medium" src="http://drive.google.com/uc?export=view&id=1C4AbynYIGc1r1VDGzNl7zaOgWJZRo16P" alt="Norway" style="width:100%">
                    <img class="w3-hide-large" src="http://drive.google.com/uc?export=view&id=1P7gPANp86Lkzih-fhfKlvc3WBjI5D9UO" alt="Norway" style="width:100%">
                    <div class="w3-container w3-white">
                        <h3>Double Room</h3>
<!--                        <h6 class="w3-opacity">From $149</h6>
                        <p>Queen-size bed</p>
                        <p>25m<sup>2</sup></p>
                        <p class="w3-large"><i class="fa fa-bath"></i> <i class="fa fa-phone"></i> <i class="fa fa-wifi"></i> <i class="fa fa-tv"></i></p>-->
                    </div>
                </div>
            </div>
            <div class="w3-row-padding w3-padding-16 w3-text-light-grey">
                <div class="w3-half w3-margin-bottom">
                    <img class="w3-hide-small w3-hide-medium" src="http://drive.google.com/uc?export=view&id=15Gup0nCKhPiYhOVY1ntBgnT3GKb-ab1z" alt="Norway" style="width:100%">
                    <img class="w3-hide-large" src="http://drive.google.com/uc?export=view&id=1kO43zpGtLpHvwUX7QiRkwrXQWcFmD2VZ" alt="Norway" style="width:100%">
                    <div class="w3-container w3-white">
                        <h3>Triple Room</h3>
<!--                        <h6 class="w3-opacity">From $99</h6>
                        <p>Single bed</p>
                        <p>15m<sup>2</sup></p>
                        <p class="w3-large"><i class="fa fa-bath"></i> <i class="fa fa-phone"></i> <i class="fa fa-wifi"></i></p>-->
                    </div>
                </div>
                <div class="w3-half w3-margin-bottom">
                    <img class="w3-hide-small w3-hide-medium" src="http://drive.google.com/uc?export=view&id=1pDTNFArffuBcQQdPKjg_irDWZ-3lIvXw" alt="Norway" style="width:100%; object-fit: cover">
                    <img class="w3-hide-large" src="http://drive.google.com/uc?export=view&id=1i3iWJsjQDM-bS-684b-AwZRn4UQ1He56" alt="Norway" style="width:100%; object-fit: cover">
                    <div class="w3-container w3-white">
                        <h3>Quad Room</h3>
<!--                        <h6 class="w3-opacity">From $149</h6>
                        <p>Queen-size bed</p>
                        <p>25m<sup>2</sup></p>
                        <p class="w3-large"><i class="fa fa-bath"></i> <i class="fa fa-phone"></i> <i class="fa fa-wifi"></i> <i class="fa fa-tv"></i></p>-->
                    </div>
                </div>
                <div class="w3-margin-bottom">
                    <img class="w3-hide-small w3-hide-medium" src="http://drive.google.com/uc?export=view&id=176fu03FzCzIuaKqS1hpiyhKk57-OKgUW" alt="Norway" style="width:100; max-width:100%">
                    <img class="w3-hide-large" src="http://drive.google.com/uc?export=view&id=176fu03FzCzIuaKqS1hpiyhKk57-OKgUW" alt="Norway" style="width:100; max-width:100%">
                    <div class="w3-container w3-white">
                        <h3>About Us</h3>
                        <h6 class="w3-opacity">Established in 2022</h6>
                        <p>Insert more thorough overview of univ inn</p>


                    </div>
                </div>
            </div>
        </div>

            <footer class="w3-container w3-padding-32 w3-center w3-opacity w3-black w3-xlarge">
                <p class="w3-medium"><a href="#">Back to Top</a></p>
            </footer>
    </body>
</html>
