<%@page import="dto.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update User Information</title>
        <style>



            /* Thiết lập cơ bản */
            body {
                font-family: Arial, sans-serif;
                background-color: #e0f7fa; /* Màu xanh nhạt */
                margin: 0;
                padding: 0;
            }

            /* Container chính */
            .container {
                width: 50%;
                margin: 50px auto;
                padding: 20px;
                border: 1px solid #ccc;
                border-radius: 10px;
                box-shadow: 2px 2px 10px #aaa;
                background-color: #ffffff;
            }

            /* Tiêu đề */
            h1 {
                text-align: center;
                color: #2BCADB; /* Màu xanh đậm */
            }

            /* Nhãn và input */
            label {
                font-weight: bold;
                color: #2BCADB;
                display: block;
                margin-top: 10px;
            }

            input {
                width: 100%;
                padding: 10px;
                margin-top: 5px;
                border: 1px solid #ccc;
                border-radius: 5px;
            }

            /* Input readonly */
            input[readonly] {
                background-color: #ddd;
                cursor: not-allowed;
            }

            /* Nút bấm */
            button {
                width: 100%;
                padding: 10px;
                margin-top: 15px;
                border: none;
                border-radius: 5px;
                background-color: #2BCADB;
                color: white;
                font-size: 16px;
                cursor: pointer;
                transition: 0.3s;
            }

            button:hover {
                background-color: #1483BA;
            }

            /* Link quay lại */
            a {
                display: block;
                text-align: center;
                margin-top: 15px;
                color: #2BCADB;
                text-decoration: none;
                font-weight: bold;
            }

            a:hover {
                color: #1483BA;
            }

            /* Thiết kế cho dropdown (select) */
            select {
                width: 100%;
                padding: 10px;
                margin-top: 5px;
                border: 1px solid #ccc;
                border-radius: 5px;
                background-color: white;
                font-size: 16px;
                color: #333;
                cursor: pointer;
            }

            /* Khi người dùng focus vào select */
            select:focus {
                border-color: #2BCADB;
                outline: none;
                box-shadow: 0px 0px 5px rgba(43, 202, 219, 0.5);
            }

            /* Thiết lập màu cho option */
            option {
                font-size: 16px;
                color: #333;
            }

        </style>
    </head>
    <body>
        <jsp:include page="header.jsp" />        
        <div class="container">
            <h1>Update User Information</h1>
            <%
                UserDTO user = (UserDTO) session.getAttribute("user");
            %>

            <form action="MainController" method="post">
                <input type="hidden" name="action" value="update"/>

                <label>User ID:</label>
                <input type="text" name="userId" value="<%= user.getUserId()%>" readonly/>

                <label>User Name:</label>
                <input type="text" name="userName" value="<%= user.getUserName()%>" required/>

                <label>Account:</label>
                <input type="text" name="userAccount" value="<%= user.getAccount()%>" readonly/>

                <label>Password</label>
                <input type="text" name="userPassword" value="<%= user.getPassword()%>" readonly/>

                <label>Email:</label>
                <input type="email" name="userEmail" value="<%= user.getEmail()%>" required/>

                <label>Phone:</label>
                <input type="text" name="userPhone" value="<%= user.getPhone()%>" required/>

                <label>Address</label>
                <input type="text" name="userAddress" value="<%= user.getAddress()%>" required/>

                <label>Role</label>
                <select name="userRole" required>
                    <option value="admin" <%= user.getRole().equals("admin") ? "selected" : ""%>>Admin</option>
                    <option value="customer" <%= user.getRole().equals("customer") ? "selected" : ""%>>Customer</option>
                </select>

                <button type="submit">Update</button>
            </form>
            <a href="infor.jsp">Quay Lại</a>
        </div>
        <%@include file="footer.jsp"%>
    </body>
</html>
