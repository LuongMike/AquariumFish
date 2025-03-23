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
                width: 90%;
                max-width: 500px;
                margin: 50px auto;
                background: #e0f7fa;
                padding: 30px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                border-radius: 10px;
            }

            h2 {
                color: #2c3e50;
                text-align: center;
                margin-bottom: 20px;
            }

            .form-group {
                margin-bottom: 20px;
            }

            .form-group label {
                display: block;
                margin-bottom: 5px;
                color: #333;
                font-weight: bold;
            }

            .form-group input,
            .form-group textarea {
                width: 100%;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 5px;
                box-sizing: border-box;
                font-size: 14px;
                transition: border-color 0.3s;
            }

            .form-group input:focus,
            .form-group textarea:focus {
                outline: none;
                border-color: #0077cc;
                box-shadow: 0 0 5px rgba(0, 119, 204, 0.3);
            }

            .form-group textarea {
                height: 100px;
                resize: vertical;
            }

            .form-group .error {
                color: red;
                font-size: 12px;
                margin-top: 5px;
                display: block;
            }

            .form-group input[type="submit"] {
                background-color: #0077cc;
                color: white;
                border: none;
                padding: 12px;
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
                margin-top: 20px;
            }

            .login-link a {
                color: #0077cc;
                text-decoration: none;
                font-weight: bold;
            }

            .login-link a:hover {
                text-decoration: underline;
            }

            @media (max-width: 480px) {
                .container {
                    width: 95%;
                    padding: 20px;
                }

                .form-group input,
                .form-group textarea {
                    font-size: 13px;
                    padding: 8px;
                }

                .form-group input[type="submit"] {
                    padding: 10px;
                    font-size: 14px;
                }
            }
        </style>
    </head>
    <body>
        <%@include file="header.jsp"%>
        <div class="container">
            <h2>Đăng Ký</h2>
            <%
                String registerMessage = (String) session.getAttribute("registerMessage");
                if (registerMessage != null) {
            %>
            <div class="message <%= registerMessage.contains("thành công") ? "success" : "error"%>">
                <%= registerMessage %>
            </div>
            <%
                    session.removeAttribute("registerMessage");
                }
            %>
            <form action="RegisterController" method="post">
                <input type="hidden" name="action" value="register">
                <div class="form-group">
                    <label for="userName">Tên người dùng:</label>
                    <input type="text" id="userName" name="userName" value="<%= request.getAttribute("userName") != null ? request.getAttribute("userName") : ""%>" required>
                    <% if (request.getAttribute("userName_error") != null) { %>
                    <span class="error"><%= request.getAttribute("userName_error")%></span>
                    <% } %>
                </div>
                <div class="form-group">
                    <label for="account">Tài khoản:</label>
                    <input type="text" id="account" name="account" value="<%= request.getAttribute("account") != null ? request.getAttribute("account") : ""%>" required>
                    <% if (request.getAttribute("account_error") != null) { %>
                    <span class="error"><%= request.getAttribute("account_error")%></span>
                    <% } %>
                </div>
                <div class="form-group">
                    <label for="password">Mật khẩu:</label>
                    <input type="password" id="password" name="password" required>
                    <% if (request.getAttribute("password_error") != null) { %>
                    <span class="error"><%= request.getAttribute("password_error")%></span>
                    <% } %>
                </div>
                <div class="form-group">
                    <label for="confirmpassword">Xác Nhận Mật khẩu:</label>
                    <input type="password" id="confirmpassword" name="confirmpassword" required>
                    <% if (request.getAttribute("confirmpassword_error") != null) { %>
                    <span class="error"><%= request.getAttribute("confirmpassword_error")%></span>
                    <% } %>
                </div>
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : ""%>" required>
                    <% if (request.getAttribute("email_error") != null) { %>
                    <span class="error"><%= request.getAttribute("email_error")%></span>
                    <% } %>
                </div>
                <div class="form-group">
                    <label for="phone">Số điện thoại:</label>
                    <input type="text" id="phone" name="phone" value="<%= request.getAttribute("phone") != null ? request.getAttribute("phone") : ""%>">
                    <% if (request.getAttribute("phone_error") != null) { %>
                    <span class="error"><%= request.getAttribute("phone_error")%></span>
                    <% } %>
                </div>
                <div class="form-group">
                    <label for="address">Địa chỉ:</label>
                    <textarea id="address" name="address"><%= request.getAttribute("address") != null ? request.getAttribute("address") : ""%></textarea>
                    <% if (request.getAttribute("address_error") != null) { %>
                    <span class="error"><%= request.getAttribute("address_error")%></span>
                    <% } %>
                </div>
                <div class="form-group">
                    <input type="submit" value="Đăng Ký">
                </div>
            </form>
            <div class="login-link">
                <p>Đã có tài khoản? <a href="login.jsp">Đăng nhập tại đây!</a></p>
            </div>
        </div>
        <%@include file="footer.jsp"%>
    </body>
</html>