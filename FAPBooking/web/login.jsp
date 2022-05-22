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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <%
            User account = (User) session.getAttribute("user");
            if (account != null) {
                response.sendRedirect("home.jsp");
                return;
            }
        %>
        
        <h1>Login</h1>
        
        <form method="post" action="Login">
<<<<<<< Updated upstream
            <p>email</p>
            <input name="email" type="text" size="40" required/>
            
            <p>password</p>
=======
            <p>Email</p>
            <input name="email" type="text" size="40" required/>
            
            <p>Password</p>
>>>>>>> Stashed changes
            <input name="password" type="password" size="40" required/>
            <br><br>
            
            <img id="captcha" src="captcha.png">
            <br>
            
            <input type="text" name="captchaAnswer" required/>
            <br><br>
            
            <input type="submit" name="action" value="Login"/>
            <br><br>
        </form>
        
        <a href="signup.jsp">Not registered yet? Sign up here</a>
    </body>
</html>

<!--refresh the page once if the back button is used-->
<script>
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
reference:
https://stackoverflow.com/questions/43043113/how-to-force-reloading-a-page-when-using-browser-back-button
-->
