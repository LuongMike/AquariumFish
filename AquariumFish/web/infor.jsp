<%@page import="dto.InvoiceDTO"%>
<%@page import="dto.OrderDetailDTO"%>
<%@page import="dto.FishDTO"%>
<%@page import="dto.UserDTO"%>
<%@page import="dao.UserDAO"%>
<%@page import="dao.InvoiceDAO"%>
<%@page import="dao.OrderDetailDAO"%>
<%@page import="dao.FishDAO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Thông Tin Cá Nhân</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f0f8ff;
                margin: 0;
                padding: 0;
                text-align: center;
            }

            h1, h3 {
                color: #0077cc;
                margin-top: 20px;
            }

            .container {
                width: 60%;
                margin: 20px auto;
                background: #e0f7fa;
                padding: 20px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                border-radius: 10px;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }

            th, td {
                padding: 12px;
                border: 1px solid #ddd;
                text-align: left;
            }

            th {
                background-color: #0077cc;
                color: white;
                text-transform: uppercase;
            }

            tr:nth-child(even) {
                background-color: #f9f9f9;
            }

            .back-link {
                display: block;
                width: 150px;
                margin: 20px auto;
                padding: 10px 0;
                background-color: #0077cc;
                color: white;
                text-align: center;
                text-decoration: none;
                font-weight: bold;
                border-radius: 5px;
                transition: background 0.3s, transform 0.2s;
            }

            .back-link:hover {
                background-color: #005fa3;
                transform: scale(1.05);
            }

            .back-link:active {
                background-color: #004885;
                transform: scale(0.98);
            }

            .history-section {
                margin-top: 40px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div class="container">
            <h1>Thông Tin Cá Nhân</h1>
            <h3>Quản lý thông tin hồ sơ để bảo mật tài khoản</h3>

            <%
                String errorMessage = (String) request.getAttribute("errorMessage");
                String successMessage = (String) request.getAttribute("successMessage");
            %>

            <% if (errorMessage != null) {%>
            <div style="color: red; font-weight: bold; margin-bottom: 10px;">
                <%= errorMessage%>
            </div>
            <% } %>

            <% if (successMessage != null) {%>
            <div style="color: green; font-weight: bold; margin-bottom: 10px;">
                <%= successMessage%>
            </div>
            <% } %>

            <% 
                UserDTO user = (UserDTO) session.getAttribute("user");
            %>

            <% if (user != null) {%>
            <table border="1">
                <tr><th>User ID</th><td><%= user.getUserId()%></td></tr>
                <tr><th>User Name</th><td><%= user.getUserName()%></td></tr>
                <tr><th>Account</th><td><%= user.getAccount()%></td></tr>
                <tr><th>Email</th><td><%= user.getEmail()%></td></tr>
                <tr><th>Phone</th><td><%= user.getPhone()%></td></tr>
                <tr><th>Address</th><td><%= user.getAddress()%></td></tr>
                <tr><th>Role</th><td><%= user.getRole()%></td></tr>
                <tr><th>Số Dư</th><td><%= user.getBalance() %> VND</td></tr>
            </table>

            <div class="history-section">
                <h3>Lịch Sử Mua Hàng Đã Thanh Toán</h3>
                <% 
                    InvoiceDAO idao = new InvoiceDAO();
                    OrderDetailDAO oddao = new OrderDetailDAO();
                    FishDAO fdao = new FishDAO();
                    List<InvoiceDTO> invoices = idao.getPaidInvoicesByUserId(user.getUserId());
                    if (invoices != null && !invoices.isEmpty()) {
                %>
                <table border="1">
                    <thead>
                        <tr>
                            <th>ID Hóa Đơn</th>
                            <th>ID Đơn Hàng</th>
                            <th>Tổng Giá</th>
                            <th>Ngày Phát Hành</th>
                            <th>Chi Tiết</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                            for (InvoiceDTO invoice : invoices) {
                        %>
                        <tr>
                            <td><%= invoice.getInvoiceID() %></td>
                            <td><%= invoice.getOrderID() %></td>
                            <td><%= invoice.getFinalPrice() %></td>
                            <td><%= invoice.getIssuedAt() %></td>
                            <td>
                                <ul style="list-style-type: none; padding: 0;">
                                <% 
                                    List<OrderDetailDTO> details = oddao.getOrderDetailsByOrderId(invoice.getOrderID());
                                    for (OrderDetailDTO detail : details) {
                                        FishDTO fish = fdao.readbyID(String.valueOf(detail.getFishID()));
                                        if (fish != null) {
                                %>
                                    <li><%= fish.getFishName() %> - <%= detail.getQuantity() %> x <%= detail.getPrice() %> VND</li>
                                <% } } %>
                                </ul>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
                <% } else { %>
                <p style="color: #e74c3c;">Bạn chưa có hóa đơn nào đã thanh toán.</p>
                <% } %>
            </div>
            <% } else { %>
            <p style="color:red;">Không tìm thấy thông tin người dùng.</p>
            <% }%>

            <a href="updateUser.jsp" class="back-link">Cập Nhật</a>
            <a href="index.jsp" class="back-link">Quay lại</a>
        </div>
        <jsp:include page="footer.jsp" />
    </body>
</html>