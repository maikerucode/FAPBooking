<%-- 
    Author     : star
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Dashboard</title>
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
        
        <h1>Hello, <% out.print(account.getEmail()); %>!</h1>
        
        <h3>Full Name</h3>
        <p><% out.print(account.getFirstName()); %> <% out.print(account.getLastName()); %></p>
        
        <h3>Email</h3>
        <p><% out.print(account.getEmail()); %></p>
        
        <h3>Reference Number</h3>
        <p><%= refNo%></p>
        
        <h3>Update your payment's reference number</h3>
        <form name="UpdateRecordButton" method="post" action="User">
            <input name="refNumber" type="text" size="40" required/> 
            <br><br>

            <input name="updateRefNumber" type="submit" value="Update"/>
            <input type="hidden" name="action" value="Update Reference Number"/>
        </form>
        
        <h3>Account Details</h3>
        <form name="GenerateAccountDetails" method="post" action="Report">
            <input name="action" type="submit" value="Get Report"/>
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
</script>

<!--
references:
page refresh: https://stackoverflow.com/questions/43043113/how-to-force-reloading-a-page-when-using-browser-back-button
-->