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
            body {
                font-family: 'Poppins', sans-serif;
                background: linear-gradient(135deg, #00c6ff, #0072ff); /* Gradient xanh biển tươi sáng */
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
                width: 350px;
                animation: fadeIn 1s ease-in-out;
                margin-top: auto;
            }
            .container h2 {
                color: #0072ff;
                margin-bottom: 20px;
                font-size: 26px;
            }
            .input-box {
                width: 100%;
                margin: 10px 0;
            }
            .input-box input {
                width: 92%;
                padding: 12px;
                border: 2px solid #00c6ff;
                border-radius: 8px;
                outline: none;
                font-size: 16px;
                background: rgba(0, 198, 255, 0.1);
            }
            .btn {
                width: 100%;
                padding: 12px;
                background: #ffb400;
                color: white;
                border: none;
                border-radius: 8px;
                font-size: 18px;
                cursor: pointer;
                transition: 0.3s;
                font-weight: bold;
            }
            .btn:hover {
                background: #ff8c00;
                transform: scale(1.05);
            }
            @keyframes fadeIn {
                from { opacity: 0; transform: translateY(-10px); }
                to { opacity: 1; transform: translateY(0); }
            }
            .error-message {
                color: #d32f2f;
                background: #ffebee;
                border-left: 5px solid #d32f2f;
                padding: 10px;
                margin-top: 15px;
                font-weight: bold;
                border-radius: 5px;
                animation: fadeIn 0.5s ease-in-out;
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
