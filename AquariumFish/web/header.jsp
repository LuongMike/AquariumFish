<%-- 
    Document   : header
    Created on : Feb 19, 2025, 4:46:52 PM
    Author     : PC
--%>

<%@page import="dto.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Header Cá Cảnh</title>
        <link rel="stylesheet" href="assets/css/header.css">
    </head>
    <body>
        <header>
            <div class="top-bar">
                <div class="logo">
                    <img src="img/aquarium_fish_logo.jpg" alt="Logo Cá Cảnh">
                    <h2>Aquarium Fish</h2>
                </div>
                <div class="search-bar">
                    <form action="FishController" method="get">
                        <input type="hidden" name="action" value="viewProducts">
                        <input type="text" name="searchTerm" placeholder="Search fish type..." required>
                        <input type="submit" value="Search"/>
                    </form>
                </div>
                <div class="user-options">
                    <%
                        // Lấy thông tin User từ session
                        UserDTO user = (UserDTO) session.getAttribute("user");

                        if (user != null) { // Nếu đã đăng nhập
                    %>
                    Xin chào, <a href="infor.jsp?accountUser=<%= user.getAccount()%>" style="color: #333"><%= user.getUserName()%></a>
                    <a href="logout.jsp" style="color: red;">Đăng Xuất</a>
                    <%
                    } else { // Nếu chưa đăng nhập
                    %>
                    <a href="login.jsp">Đăng nhập</a>
                    <%
                        }
                    %>
                    <div class="cart">
                        <span class="cart-icon">🛒</span>
                        <span>Giỏ hàng (0)</span>
                    </div>
                </div>
            </div>
            <nav class="menu">
                <a href="index.jsp">Trang chủ</a>
                <a href="product.jsp">Sản phẩm</a>
                <a href="discount.jsp">Giảm giá</a>
                <a href="fishTankModel.jsp">Mẫu hồ cá</a>
                <a href="knowledge.jsp">Kiến thức</a>
                <a href="#">Giới thiệu</a>
            </nav>
        </header>
    </body>
</html>