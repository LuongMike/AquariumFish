<%-- 
    Document   : newjsp
    Created on : Mar 18, 2025, 12:14:33 PM
    Author     : PC
--%>

<%@page import="java.util.List"%>
<%@page import="dto.FishDTO"%>
<%@page import="dao.FishDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            FishDAO fdao = new FishDAO();
            List<FishDTO> f = fdao.readAll();
            for (FishDTO fish : f) {
                    out.println(fish);
                }
            int totalFish = f.size();
            out.println(totalFish);
        %>
    </body>
</html>
