<%-- 
    Author     : star
--%>

<%@page import="java.time.Year"%>
<%@page import="model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking Page</title>
    </head>
    <body>
        <%
            User account = (User) session.getAttribute("user");
            if (account == null) {
                response.sendRedirect("login.jsp");
                return;
            }
        %>
        
        <h1>Let's book a room, <% out.print(account.getEmail()); %>!</h1>
        
        <!--booking button-->
        <form method="post" action="Booking">
            <h3>length of stay</h3>
            
            <p>check-in date</p>
            <select name="inMonth">
                <%
                    String[] arrMo = new String[] {
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
                    out.println("<option value='" + y + "'>"
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
                    out.println("<option value='" + y + "'>"
                                    + y.plusYears(1) + "</option>");
                    out.println("<option value='" + y + "'>"
                                    + y.plusYears(2) + "</option>");
                %>
            </select>
            <br><br>
            <!-- =================================================== -->
            
            <h3>room types</h3>
            <!--nts: retrieve available number of rooms for each room type from DB
                then display here
            --> 
            
            <p>Single</p>
            <input name="roomSingle" type="number" min="0" max="10"
                   placeholder="0" value="0" step="1"/>
            
            <p>Double</p>
            <input name="roomDouble" type="number" min="0" max="10"
                   placeholder="0" value="0" step="1"/>
            
            <p>Triple</p>
            <input name="roomTriple" type="number" min="0" max="10"
                   placeholder="0" value="0" step="1"/>
            
            <p>Quad</p>
            <input name="roomQuad" type="number" min="0" max="10"
                   placeholder="0" value="0" step="1"/>            
            <br><br><br>

            <input type="submit" name="action" value="Confirm Booking"/>
        </form>        
    </body>
</html>
