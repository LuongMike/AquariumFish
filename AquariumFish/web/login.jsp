<%-- 
    Document   : login
    Created on : Feb 19, 2025, 3:50:23 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng Nhập</title>
        <style>
            /* Reset mặc định */
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: 'Arial', sans-serif;
            }

            body {
                background-color: #f0f4f8; /* Màu nền nhẹ nhàng */
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh; /* Đảm bảo chiều cao tối thiểu bằng chiều cao màn hình */
            }

            .container {
                background-color: #ffffff; /* Màu nền trắng cho container */
                padding: 40px;
                border-radius: 15px; /* Bo góc */
                box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1); /* Đổ bóng nhẹ */
                width: 100%;
                max-width: 400px; /* Chiều rộng tối đa */
                text-align: center;
                margin: 60px 0px;
            }

            h2 {
                color: #0077cc; /* Màu xanh dương cho tiêu đề */
                font-size: 28px;
                margin-bottom: 30px;
                display: flex;
                align-items: center;
                justify-content: center;
                gap: 10px; /* Khoảng cách giữa icon và text */
            }

            .input-box {
                margin-bottom: 20px;
                position: relative;
            }

            .input-box input {
                width: 100%;
                padding: 12px 15px;
                border: 2px solid #ddd; /* Viền xám nhạt */
                border-radius: 8px;
                font-size: 16px;
                outline: none;
                transition: border-color 0.3s ease; /* Hiệu ứng chuyển màu viền */
            }

            .input-box input:focus {
                border-color: #0077cc; /* Viền xanh khi focus */
                box-shadow: 0 0 5px rgba(0, 119, 204, 0.2); /* Đổ bóng nhẹ khi focus */
            }

            .input-box input::placeholder {
                color: #999; /* Màu chữ placeholder */
            }

            .btn {
                width: 100%;
                padding: 12px;
                background-color: #0077cc; /* Màu xanh dương cho nút */
                color: #ffffff;
                border: none;
                border-radius: 8px;
                font-size: 16px;
                font-weight: bold;
                cursor: pointer;
                transition: background-color 0.3s ease, transform 0.1s ease; /* Hiệu ứng chuyển màu và nhấn */
            }

            .btn:hover {
                background-color: #005fa3; /* Màu xanh đậm hơn khi hover */
            }

            .btn:active {
                transform: scale(0.98); /* Hiệu ứng nhấn nút */
            }

            .error-message {
                color: #e74c3c; /* Màu đỏ cho thông báo lỗi */
                background-color: #fceae8; /* Màu nền nhạt */
                padding: 10px;
                border-radius: 5px;
                margin: 15px 0;
                font-size: 14px;
            }

            .register-link {
                margin-top: 20px;
                font-size: 14px;
                color: #666;
            }

            .register-link a {
                color: #0077cc; /* Màu xanh cho liên kết */
                text-decoration: none;
                font-weight: bold;
            }

            .register-link a:hover {
                text-decoration: underline; /* Gạch chân khi hover */
            }
        </style>
    </head>
    <body>
        <%@include file="header.jsp"%>
        <div class="container">
            <h2>🐠 Đăng Nhập</h2>
            <form action="MainController" method="post">
                <input type="hidden" name="action" value="login"/>
                <div class="input-box">
                    <input type="text" name="userAccount" placeholder="Tài khoản" required>
                </div>
                <div class="input-box">
                    <input type="password" name="userPassword" placeholder="Mật khẩu" required>
                </div>
                <button type="submit" value="login" class="btn">Đăng Nhập</button>
            </form>
            <% String loginError = (String) request.getAttribute("loginError"); %>
            <% if (loginError != null) {%>
            <div class="error-message">
                <%= loginError%>
            </div>
            <% }%>
            <div class="register-link">
                <p>If you don't have an account, <a href="register.jsp">register here!</a></p>
            </div>

        </div>
        <%@include file="footer.jsp"%>
    </body>
</html>
