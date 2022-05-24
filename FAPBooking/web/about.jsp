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
                <a href="#" class="w3-bar-item w3-button w3-light-grey">About</a>
                <a href="booking.jsp" class="w3-bar-item w3-button">Book Now</a>
                <a href="login.jsp" class="w3-bar-item w3-button">Login/Dashboard</a>
            </div>

            <div class="w3-center w3-deep-orange w3-hide-large" style="max-height:30px" style="bottom:-16px">
                <div class="w3-row w3-bar w3-deep-orange" style="max-height:30px">
                    <a href="welcome.jsp" class="w3-bar-item w3-button" style="font-size: 10px;">Home</a>
                    <a href="#" class="w3-bar-item w3-button w3-light-grey" style="font-size: 10px;">About</a>
                    <a href="booking.jsp" class="w3-bar-item w3-button" style="font-size: 10px;">Book Now</a>
                    <a href="login.jsp" class="w3-bar-item w3-button" style="font-size: 10px;">Login/Dashboard</a>
                </div>
            </div>
        </header>
        <br><br>
        <main>
            <div class="w3-content" style="max-width:700px;">
                <div class="w3-row-padding w3-padding-16 w3-text-light-grey">
                    <div class="w3-margin-bottom" align="center">
                        <img class="w3-hide-small w3-hide-medium" src="http://drive.google.com/uc?export=view&id=176fu03FzCzIuaKqS1hpiyhKk57-OKgUW" alt="Norway" style="width:100; max-width:100%">
                        <img class="w3-hide-small w3-hide-large" src="http://drive.google.com/uc?export=view&id=176fu03FzCzIuaKqS1hpiyhKk57-OKgUW" alt="Norway" style="width:100; max-width:500px">
                        <img class="w3-hide-large w3-hide-medium" src="http://drive.google.com/uc?export=view&id=176fu03FzCzIuaKqS1hpiyhKk57-OKgUW" alt="Norway" style="width:100; max-width:400px">
                        <div class="w3-container w3-white w3-hide-small w3-hide-medium">
                            <h3>University Inn</h3>
                            <h6 class="w3-opacity">Established in 2022</h6>
                            <p>University Inn is committed to provide comfortable and hygienic accommodations, as students and guests remain the first and foremost priority. The hotel applies more stringent sanitation protocols to protect and secure the guests’ welfare.

                                Guests are assured of comfort, luxury and impeccable service at the University Inn. Stay and experience the renowned Fueled by Passion Service by the hotel's gracious associates.</p>


                        </div>
                        
                        <div class="w3-container w3-white w3-hide-small w3-hide-large" style="max-width:500px;">
                            <h3>University Inn</h3>
                            <h6 class="w3-opacity">Established in 2022</h6>
                            <p>University Inn is committed to provide comfortable and hygienic accommodations, as students and guests remain the first and foremost priority. The hotel applies more stringent sanitation protocols to protect and secure the guests’ welfare.

                                Guests are assured of comfort, luxury and impeccable service at the University Inn. Stay and experience the renowned Fueled by Passion Service by the hotel's gracious associates.</p>


                        </div>
                        
                        <div class="w3-container w3-white w3-small w3-hide-large w3-hide-medium" style="max-width:400px;">
                            <h3>University Inn</h3>
                            <h6 class="w3-opacity">Established in 2022</h6>
                            <p>University Inn is committed to provide comfortable and hygienic accommodations, as students and guests remain the first and foremost priority. The hotel applies more stringent sanitation protocols to protect and secure the guests’ welfare.

                                Guests are assured of comfort, luxury and impeccable service at the University Inn. Stay and experience the renowned Fueled by Passion Service by the hotel's gracious associates.</p>


                        </div>
                    </div>
                </div>
            </div>

            <div class="w3-content w3-hide-small w3-hide-medium" style="max-width:700px;">
                <div class="w3-row-padding w3-padding-16 w3-text-light-grey">
                    <div class="w3-container w3-white">
                        <h3>About Us</h3>
                    </div>
                    <div class="w3-border w3-light-grey w3-third">
                        <h4 align="center">Sophie Reginio</h4>
                    </div>
                    <div class="w3-border w3-light-grey w3-third">
                        <h4 align="center">Carlos Gabriel Diaz</h4>
                    </div>
                    <div class="w3-border w3-light-grey w3-third">
                        <h4 align="center">Michael Sarmiento</h4>
                    </div>
                    <div class="w3-border w3-light-grey w3-third w3-margin-bottom" style="height:275px">
                        <br>
                        <h5>Facebook: </h5>
                        <a href="https://www.facebook.com/sseuta">Sophie Reginio</a>
                        <br>
                    </div>
                    <div class="w3-border w3-light-grey w3-third w3-margin-bottom" style="height:275px">
                        <br>
                        <h5>Facebook: </h5>
                        <a href="https://www.facebook.com/CarlosDiazTo">Carlos Gabriel Diaz</a>
                        <br>
                        <h5>Twitter:</h5>
                        <a href="https://twitter.com/CarlosDiazTo">@CarlosDiazTo</a>
                        <br>
                        <h5>Instagram:</h5>
                        <a href="https://www.instagram.com/carlosdiazto/?hl=en">carlosdiazto</a>
                        <br><br>
                    </div>
                    <div class="w3-border w3-light-grey w3-third w3-margin-bottom" style="height:275px">
                        <br>
                        <h5>Facebook: </h5>
                        <a href="https://www.facebook.com/maikerumsg">Michael Joshua Sarmiento</a>
                    </div>
                </div>
            </div>

            <div class="w3-content w3-hide-large w3-hide-small w3-small" style="max-width:500px;">
                <div class="w3-row-padding w3-padding-16 w3-text-light-grey">
                    <div class="w3-container w3-white">
                        <h3>About Us</h3>
                    </div>
                    <div class="w3-border w3-light-grey w3-third" style="height:250px">
                        <br>
                        <h5>Facebook: </h5>
                        <a href="https://www.facebook.com/sseuta">Sophie Reginio</a>
                        <br>
                    </div>
                    <div class="w3-border w3-light-grey w3-third" style="height:250px">
                        <br>
                        <h5>Facebook: </h5>
                        <a href="https://www.facebook.com/CarlosDiazTo">Carlos Gabriel Diaz</a>
                        <br>
                        <h5>Twitter:</h5>
                        <a href="https://twitter.com/CarlosDiazTo">@CarlosDiazTo</a>
                        <br>
                        <h5>Instagram:</h5>
                        <a href="https://www.instagram.com/carlosdiazto/?hl=en">carlosdiazto</a>
                        <br><br>
                    </div>
                    <div class="w3-border w3-light-grey w3-third w3-margin-bottom" style="height:250px">
                        <br>
                        <h5>Facebook: </h5>
                        <a href="https://www.facebook.com/maikerumsg">Michael Joshua Sarmiento</a>
                    </div>
                </div>
            </div>

            <div class="w3-content w3-hide-large w3-hide-medium w3-tiny" style="max-width:400px;">
                <div class="w3-row-padding w3-padding-16 w3-text-light-grey">
                    <div class="w3-container w3-white">
                        <h3>About Us</h3>
                    </div>
                    <div class="w3-border w3-light-grey w3-third" style="height:150px">
                        <h4 align="center">Sophie Reginio</h4>
                        <br>
                        <h5>Facebook: </h5>
                        <a href="https://www.facebook.com/sseuta">Sophie Reginio</a>
                        <br>
                    </div>
                    <div class="w3-border w3-light-grey w3-third" style="height:300px">
                        <h4 align="center">Carlos Gabriel Diaz</h4>
                        <br>
                        <h5>Facebook: </h5>
                        <a href="https://www.facebook.com/CarlosDiazTo">Carlos Gabriel Diaz</a>
                        <br>
                        <h5>Twitter:</h5>
                        <a href="https://twitter.com/CarlosDiazTo">@CarlosDiazTo</a>
                        <br>
                        <h5>Instagram:</h5>
                        <a href="https://www.instagram.com/carlosdiazto/?hl=en">carlosdiazto</a>
                        <br><br>
                    </div>
                    <div class="w3-border w3-light-grey w3-third" style="height:150px">
                        <h4 align="center">Michael Sarmiento</h4>
                        <br>
                        <h5>Facebook: </h5>
                        <a href="https://www.facebook.com/maikerumsg">Michael Joshua Sarmiento</a>
                    </div>
                </div>
            </div>

        </main>
        <footer class="w3-container w3-padding-32 w3-center w3-opacity w3-black w3-xlarge">
            <p class="w3-medium"><a href="#">Back to Top</a></p>
        </footer>
    </body>
</html>
