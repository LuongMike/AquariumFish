<%@page import="dao.PaymentDAO"%>
<%@page import="dto.DiscountDTO"%>
<%@page import="dto.InvoiceDTO"%>
<%@page import="dao.InvoiceDAO"%>
<%@page import="dao.DiscountDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hóa Đơn</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f8ff;
            margin: 0;
            padding: 0;
            text-align: center;
        }

        h1 {
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
        }

        .message {
            font-weight: bold;
            margin: 10px 0;
        }

        .message.success {
            color: green;
        }

        .message.error {
            color: red;
        }

        .discount-form {
            margin: 20px 0;
        }

        .discount-form input[type="text"] {
            padding: 8px;
            width: 200px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        .discount-form input[type="submit"] {
            padding: 8px 15px;
            background-color: #0077cc;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .discount-form input[type="submit"]:hover {
            background-color: #005fa3;
        }

        .pay-button {
            padding: 10px 20px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 20px;
        }

        .pay-button:hover {
            background-color: #218838;
        }

        .back-link {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #0077cc;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .back-link:hover {
            background-color: #005fa3;
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp" />
    <div class="container">
        <h1>Hóa Đơn</h1>
        <%
            // Lấy thông báo từ session và xóa sau khi hiển thị
            String message = (String) session.getAttribute("message");
            String discountMessage = (String) session.getAttribute("discountMessage");
            String appliedDiscountCode = (String) session.getAttribute("appliedDiscountCode");
            Double discountAmountFromSession = (Double) session.getAttribute("discountAmount"); // Lấy discountAmount từ session để kiểm tra
            Integer invoiceId = (Integer) session.getAttribute("invoiceId");
            InvoiceDAO idao = new InvoiceDAO();
            DiscountDAO ddao = new DiscountDAO();
            InvoiceDTO invoice = null;

            if (invoiceId != null) {
                invoice = idao.getInvoiceById(invoiceId);
            }

            if (message != null) {
        %>
        <div class="message <%= message.contains("thành công") ? "success" : "error" %>">
            <%= message %>
        </div>
        <%
                session.removeAttribute("message");
            }
        %>

        <% if (discountMessage != null) { %>
        <div class="message <%= discountMessage.contains("thành công") ? "success" : "error" %>">
            <%= discountMessage %>
        </div>
        <%
                session.removeAttribute("discountMessage");
            }
        %>

        <% if (invoice != null) { %>
        <table border="1">
            <tr><th>ID Hóa Đơn</th><td><%= invoice.getInvoiceID() %></td></tr>
            <tr><th>ID Đơn Hàng</th><td><%= invoice.getOrderID() %></td></tr>
            <tr><th>Tổng Giá</th><td><%= String.format("%,.0f", invoice.getTotalPrice()) %> VND</td></tr>
            <tr><th>Thuế</th><td><%= String.format("%,.0f", invoice.getTax()) %> VND</td></tr>
            <%
                String displayDiscountCode = appliedDiscountCode;
                if (displayDiscountCode == null && invoice.getDiscountID() != 0) {
                    DiscountDTO discount = ddao.getDiscountById(invoice.getDiscountID());
                    if (discount != null) {
                        displayDiscountCode = discount.getCode();
                    } else {
                        displayDiscountCode = "Unknown";
                    }
                }
                // Hiển thị "Mã Giảm Giá" và "Số Tiền Giảm" nếu discount_amount > 0
                if (invoice.getDiscount_amount() > 0) {
            %>
            <tr><th>Mã Giảm Giá</th><td><%= displayDiscountCode != null ? displayDiscountCode : "Unknown" %></td></tr>
            <tr><th>Số Tiền Giảm</th><td><%= String.format("%,.0f", invoice.getDiscount_amount()) %> VND</td></tr>
            <%
                } else if (displayDiscountCode != null && !displayDiscountCode.isEmpty()) {
                    // Nếu có mã giảm giá nhưng discount_amount = 0, hiển thị thông báo
            %>
            <tr><th>Mã Giảm Giá</th><td><%= displayDiscountCode %></td></tr>
            <tr><th>Số Tiền Giảm</th><td>0 VND (Mã giảm giá không áp dụng được)</td></tr>
            <%
                }
            %>
            <tr><th>Giá Cuối Cùng</th><td><%= String.format("%,.0f", invoice.getFinalPrice()) %> VND</td></tr>
            <tr><th>Ngày Phát Hành</th><td><%= invoice.getIssuedAt() %></td></tr>
        </table>

        <%
            // Kiểm tra xem hóa đơn đã thanh toán chưa
            PaymentDAO pdao = new PaymentDAO();
            if (!pdao.isInvoicePaid(invoice.getInvoiceID())) {
        %>
        <div class="discount-form">
            <form action="CartController" method="post">
                <input type="hidden" name="action" value="applyDiscount">
                <input type="hidden" name="invoiceId" value="<%= invoice.getInvoiceID() %>">
                <label for="discountCode">Nhập mã giảm giá:</label>
                <input type="text" name="discountCode" id="discountCode" placeholder="Mã giảm giá" value="<%= displayDiscountCode != null ? displayDiscountCode : "" %>">
                <input type="submit" value="Áp dụng">
            </form>
        </div>

        <form action="CartController" method="post">
            <input type="hidden" name="action" value="pay">
            <input type="hidden" name="invoiceId" value="<%= invoice.getInvoiceID() %>">
            <button type="submit" class="pay-button">Thanh Toán</button>
        </form>
        <%
            } else {
        %>
        <div class="message success">Hóa đơn đã được thanh toán!</div>
        <%
            }
        %>
        <% } else { %>
        <p style="color: red;">Không tìm thấy thông tin hóa đơn.</p>
        <% } %>

        <a href="cart.jsp" class="back-link">Quay lại giỏ hàng</a>
    </div>
    <jsp:include page="footer.jsp" />
</body>
</html>