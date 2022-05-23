<%-- 
    Document   : signup
    Created on : 03 22, 22, 2:49:45 AM
    Author     : star
--%>

<%@page import="model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign-up Page</title>
    </head>
    <body>
        <%
            User account = (User) session.getAttribute("user");
            if (account != null) {
                response.sendRedirect("home.jsp");
                return;
            }
        %>
        
        <h1>Create a new account</h1>
        
        <form method="post" action="Login">
            <p>Email</p>
            <input name="email" type="text" size="40" required/>
            
            <p>First Name</p>
            <input name="firstName" type="text" size="40" required/>
            
            <p>Last Name</p>
            <input name="lastName" type="text" size="40" required/>
            
            <p>password</p>
            <input name="password" type="password" size="40" required/>
            
            <p>confirm password</p>
            <input name="confirmPass" type="password" size="40" required/> 
            <br><br>
            
            <img id="captcha" src="captcha.png">
            <br>
            
            <input type="text" name="captchaAnswer" required/>
            <br><br>

            <input type="submit" name="action" value="Sign-up"/>
            <input type="hidden" name="role" value="Guest"/>
            <br><br>
        </form>
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