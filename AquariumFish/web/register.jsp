<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Đăng Ký</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f0f8ff;
                margin: 0;
                padding: 0;
            }

            .container {
                width: 400px;
                margin: 50px auto;
                background: #e0f7fa;
                padding: 20px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                border-radius: 10px;
            }

            h2 {
                color: #0077cc;
                text-align: center;
                margin-bottom: 20px;
            }

            .form-group {
                margin-bottom: 15px;
            }

            .form-group label {
                display: block;
                margin-bottom: 5px;
                color: #333;
                font-weight: bold;
            }

            .form-group input, .form-group textarea {
                width: 100%;
                padding: 8px;
                border: 1px solid #ddd;
                border-radius: 5px;
                box-sizing: border-box;
                font-size: 14px;
            }

            .form-group textarea {
                height: 80px;
                resize: vertical;
            }

            .form-group input[type="submit"] {
                background-color: #0077cc;
                color: white;
                border: none;
                padding: 10px;
                cursor: pointer;
                font-size: 16px;
                transition: background-color 0.3s;
            }

            .form-group input[type="submit"]:hover {
                background-color: #005fa3;
            }

            .message {
                font-weight: bold;
                margin: 10px 0;
                text-align: center;
            }

            .message.error {
                color: red;
            }

            .message.success {
                color: green;
            }

            .login-link {
                text-align: center;
                margin-top: 15px;
            }

            .login-link a {
                color: #0077cc;
                text-decoration: none;
                font-weight: bold;
            }

            .login-link a:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <%@include file="header.jsp"%>
        <div class="container">
            <h2>Đăng Ký</h2>
            <%            String registerMessage = (String) request.getAttribute("registerMessage");
                if (registerMessage != null) {
            %>
            <div class="message <%= registerMessage.contains("thành công") ? "success" : "error"%>">
                <%= registerMessage%>
            </div>
            <% }%>
            <form action="RegisterController" method="post">
                <div class="form-group">
                    <label for="userName">Tên người dùng:</label>
                    <input type="text" id="userName" name="userName" value="<%= request.getAttribute("userName") != null ? request.getAttribute("userName") : ""%>" required>
                </div>
                <div class="form-group">
                    <label for="account">Tài khoản:</label>
                    <input type="text" id="account" name="account" value="<%= request.getAttribute("account") != null ? request.getAttribute("account") : ""%>" required>
                </div>
                <div class="form-group">
                    <label for="password">Mật khẩu:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : ""%>" required>
                </div>
                <div class="form-group">
                    <label for="phone">Số điện thoại:</label>
                    <input type="text" id="phone" name="phone" value="<%= request.getAttribute("phone") != null ? request.getAttribute("phone") : ""%>">
                </div>
                <div class="form-group">
                    <label for="address">Địa chỉ:</label>
                    <textarea id="address" name="address"><%= request.getAttribute("address") != null ? request.getAttribute("address") : ""%></textarea>
                </div>
                <div class="form-group">
                    <input type="submit" value="Đăng Ký">
                </div>
            </form>
            <div class="login-link">
                <p>Already have an account? <a href="login.jsp">Login here!</a></p>
            </div>
        </div>
        <%@include file="footer.jsp"%>
    </body>
</html>