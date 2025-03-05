<%-- 
    Document   : logout
    Created on : Feb 19, 2025, 9:23:15 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            session.invalidate(); // Hủy session để đăng xuất
            response.sendRedirect("mainPage.jsp"); // Chuyển hướng về trang chủ
        %>    
    </body>
</html>
