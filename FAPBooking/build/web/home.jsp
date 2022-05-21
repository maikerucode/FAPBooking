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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
    </head>
    <body>
        <%
            User account = (User) session.getAttribute("user");
            if (account == null) {
                response.sendRedirect("login.jsp");
                return;
            }
        %>
        
        <h1>Hello, <% out.print(account.getRole()); %> <% out.print(account.getEmail()); %>!</h1>
        
        <!--logout button-->
        <form method="post" action="Logout">
            <input type="submit" name="action" value="Logout"/>
        </form>
        <br>
        
        <!--get report PDF button-->
        <form method="post" action="Report">
            <input type="submit" name="action" value="Get Report"/>
        </form>
        <br>
        
        <!--booking button-->
        <form method="post" action="Booking">
            <input type="submit" name="action" value="Book a Room"/>
        </form>
    </body>
</html>

<script>
<!--refresh the page once if the back button is used-->
window.addEventListener( "pageshow", function ( event ) {
  var historyTraversal = event.persisted || 
                         ( typeof window.performance != "undefined" && 
                              window.performance.navigation.type === 2 );
  if ( historyTraversal ) {
    // Handle page restore.
    window.location.reload();
  }
});

<!--prevent form resubmission if page is refreshed-->
if (window.history.replaceState) {
  window.history.replaceState(null, null, window.location.href);
}
</script>

<!--
reference:
page refresh: https://stackoverflow.com/questions/43043113/how-to-force-reloading-a-page-when-using-browser-back-button
JS to prevent resubmission of form: https://stackoverflow.com/questions/6320113/how-to-prevent-form-resubmission-when-page-is-refreshed-f5-ctrlr
-->