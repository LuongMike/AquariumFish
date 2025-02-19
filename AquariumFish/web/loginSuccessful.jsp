<%-- 
    Document   : loginSuccessful
    Created on : Feb 19, 2025, 4:30:13 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String userAccount = (String) session.getAttribute("username");
    if (userAccount == null) {
        response.sendRedirect("login.jsp"); // Nếu chưa đăng nhập, quay về trang login
        return;
    }
%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="refresh" content="3;url=mainPage.jsp"> <!-- Tự động chuyển về trang chính -->

        <title>Đăng Nhập Thành Công</title>
        <style>
            body {
                font-family: 'Poppins', sans-serif;
                background: linear-gradient(135deg, #00dbde, #fc00ff); /* Gradient xanh ngọc - tím */
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }
            .container {
                background: white;
                padding: 40px;
                border-radius: 15px;
                text-align: center;
                box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.2);
                width: 400px;
                animation: fadeIn 1s ease-in-out;
            }
            .container h2 {
                color: #00b894;
                margin-bottom: 20px;
                font-size: 28px;
            }
            .container p {
                color: #333;
                font-size: 18px;
            }
            .btn {
                display: inline-block;
                margin-top: 20px;
                padding: 12px 25px;
                background: #00b894;
                color: white;
                border: none;
                border-radius: 8px;
                font-size: 18px;
                font-weight: bold;
                cursor: pointer;
                text-decoration: none;
                transition: 0.3s;
            }
            .btn:hover {
                background: #00997b;
                transform: scale(1.05);
            }
            @keyframes fadeIn {
                from { opacity: 0; transform: translateY(-10px); }
                to { opacity: 1; transform: translateY(0); }
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>✅ Xin chào, <%= userAccount %>! Bạn đã đăng nhập thành công.</h2>
            <p>Chào mừng bạn đến với hệ thống. Chúc bạn một ngày tuyệt vời! 🎉</p>
            <a href="mainPage.jsp" class="btn">🏠 Về Trang Chủ</a>
        </div>
    </body>
</html>
