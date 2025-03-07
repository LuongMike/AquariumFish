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
                font-family: Arial, sans-serif;
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
                flex-wrap: wrap; /* Đảm bảo không bị tràn */
            }

            .logo {
                display: flex;
                align-items: center;
            }
            .logo img {
                height: 70px;
                margin-right: 10px;
                border-radius: 33px;
            }
            .search-bar {
                flex: 1;
                margin: 0 20px;
            }
            .search-bar input {
                width: 100%;
                padding: 8px;
                border: none;
                border-radius: 20px;
                outline: none;
            }
            .user-options {
                display: flex;
                gap: 20px;
                align-items: center;
            }
            .user-options a {
                color: white;
                text-decoration: none;
                font-weight: bold;
            }
            .cart {
                background: white;
                padding: 5px 15px;
                border-radius: 20px;
                display: flex;
                align-items: center;
                gap: 5px;
                color: #0077b6;
                font-weight: bold;
            }
            .cart-icon {
                color: #0077b6;
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
            }

            .user-options a {
                margin: 0;
                padding: 5px 10px;
            }
            .user-options {
                display: flex;
                align-items: center;
                gap: 15px; /* Để các phần tử cách đều */
            }

            .user-options {
                white-space: nowrap;
            }

        </style>

    </head>
    <body>
        <header>
            <div class="top-bar">
                <div class="logo">
                    <img src="img/aquarium_fish_logo.jpg" alt="Logo Cá Cảnh">
                    <h2 style="color: white;">Aquarium Fish</h2>
                </div>
                <div class="search-bar">
                    <form action="FishController" method="get">
                        <input type="hidden" name="action" value="viewProducts">
                        <input type="text" name="searchTerm" placeholder="Search fish type..." required>
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
                <a href="mainPage.jsp">Trang chủ</a>
                <a href="product.jsp">Sản phẩm</a>
                <a href="discount.jsp">Giảm giá</a>
                <a href="fishTankModel.jsp">Mẫu hồ cá</a>
                <a href="knowledge.jsp">Kiến thức</a>
                <a href="#">Giới thiệu</a>
            </nav>

        </header>
    </body>
</html>
