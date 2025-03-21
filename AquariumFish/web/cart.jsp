<%@page import="dto.FishDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Giỏ Hàng</title>
        <link rel="stylesheet" href="assets/css/index.css">
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div class="container">
            <h1>Giỏ Hàng</h1>
            <% String message = (String) request.getAttribute("message"); %>
            <% if (message != null) { %>
                <p class="message success"><%= message %></p>
            <% } %>
            <% 
                List<FishDTO> cart = (List<FishDTO>) session.getAttribute("cart");
                if (cart != null && !cart.isEmpty()) {
            %>
            <table class="fish-table">
                <thead>
                    <tr>
                        <th>Loại Cá</th>
                        <th>Tên Cá</th>
                        <th>Giá</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (FishDTO fish : cart) { %>
                    <tr>
                        <td><%= fish.getFishType()%></td>
                        <td><%= fish.getFishName() %></td>
                        <td><%= fish.getFishPrice() %></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
            <% } else { %>
            <p class="no-data">Giỏ hàng trống.</p>
            <% } %>
            <a href="index.jsp" class="back-link">Tiếp tục mua sắm</a>
        </div>
        <jsp:include page="footer.jsp" />
    </body>
</html>