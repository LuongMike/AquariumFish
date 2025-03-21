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
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: 'Segoe UI', Arial, sans-serif;
            }

            header {
                background: linear-gradient(to right, #4facfe, #00f2fe);
                padding: 10px 20px;
                display: flex;
                flex-direction: column;
                align-items: center;
                width: 100%;
            }

            .top-bar {
                width: 100%;
                display: flex;
                align-items: center;
                justify-content: space-between;
                flex-wrap: wrap;
            }

            .logo {
                display: flex;
                align-items: center;
            }

            .logo img {
                height: 70px;
                margin-right: 10px;
                border-radius: 33px;
                transition: transform 0.3s ease;
            }

            .logo img:hover {
                transform: scale(1.1);
            }

            .logo h2 {
                color: white;
                font-size: 24px;
                font-weight: 700;
            }

            .search-bar {
                flex: 1;
                margin: 0 20px;
            }

            .search-bar form {
                display: flex;
                align-items: center;
                justify-content: center;
            }

            .search-bar input[type="text"] {
                width: 100%;
                max-width: 300px;
                padding: 10px;
                border: none;
                border-radius: 20px 0 0 20px;
                outline: none;
                font-size: 14px;
                transition: box-shadow 0.3s ease;
            }

            .search-bar input[type="text"]:focus {
                box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
            }

            .search-bar input[type="submit"] {
                padding: 10px 20px;
                font-size: 14px;
                background: linear-gradient(to right, #007BFF, #0056b3);
                color: white;
                border: none;
                border-radius: 0 20px 20px 0;
                cursor: pointer;
                transition: background 0.3s ease, transform 0.3s ease;
                box-shadow: 0 2px 5px rgba(0, 123, 255, 0.3);
            }

            .search-bar input[type="submit"]:hover {
                background: linear-gradient(to right, #0056b3, #003d7a);
                transform: translateY(-2px);
                box-shadow: 0 4px 10px rgba(0, 123, 255, 0.4);
            }

            .user-options {
                display: flex;
                align-items: center;
                gap: 15px;
                white-space: nowrap;
            }

            .user-options a {
                color: red;
                text-decoration: none;
                font-weight: bold;
                padding: 5px 10px;
                transition: color 0.3s ease;
            }

            .user-options a:hover {
                color: #ffeb3b;
            }

            .user-options a[style*="color: red"] {
                color: red !important;
            }

            .user-options a[style*="color: #333"] {
                color: #333 !important;
                background: white;
                border-radius: 15px;
                padding: 5px 15px;
                transition: background 0.3s ease;
            }

            .user-options a[style*="color: #333"]:hover {
                background: #e0e0e0;
            }

            .cart {
                background: white;
                padding: 5px 15px;
                border-radius: 20px;
                display: flex;
                align-items: center;
                gap: 5px;
                color: white;
                font-weight: bold;
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }

            .cart:hover {
                transform: scale(1.05);
                box-shadow: 0 4px 10px rgba(255, 0, 0, 0.3);
            }

            .cart-icon {
                color: white;
            }

            .menu {
                display: flex;
                gap: 20px;
                justify-content: center;
                width: 100%;
                margin-top: 10px;
            }

            .menu a {
                color: white;
                text-decoration: none;
                font-weight: bold;
                padding: 5px 15px;
                border-radius: 20px;
                transition: background 0.3s ease, color 0.3s ease;
            }

            .menu a:hover {
                background: rgba(255, 255, 255, 0.2);
                color: #ffeb3b;
            }


        </style>
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
                    <span class="balance">Số dư: <%= user.getBalance() %> VND</span>
                    <a href="logout.jsp" style="color: red;">Đăng Xuất</a>
                    <%
                    } else { // Nếu chưa đăng nhập
                    %>
                    <a href="login.jsp">Đăng nhập</a>
                    <%
                        }
                    %>
                    <a href="cart.jsp" class="cart">
                        <span class="cart-icon">🛒</span>
                        <span>Giỏ hàng (0)</span>
                    </a>

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