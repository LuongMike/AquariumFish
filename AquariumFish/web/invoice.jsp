<%@page import="dto.InvoiceDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hóa Đơn</title>
        <link rel="stylesheet" href="assets/css/invoice.css">
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div class="container">
            <h1>Hóa Đơn</h1>
            <% 
                String message = (String) request.getAttribute("message"); 
                if (message != null) { 
            %>
                <p class="message <%= message.contains("thành công") ? "success" : "error" %>"><%= message %></p>
            <% } %>
            <% 
                InvoiceDTO invoice = (InvoiceDTO) request.getAttribute("invoice");
                if (invoice != null) {
            %>
            <div class="invoice-details">
                <p><strong>ID Hóa Đơn:</strong> <%= invoice.getInvoiceID() %></p>
                <p><strong>ID Đơn Hàng:</strong> <%= invoice.getOrderID() %></p>
                <p><strong>Tổng Giá:</strong> <%= invoice.getTotalPrice() %> VND</p>
                <p><strong>Thuế:</strong> <%= invoice.getTax() %> VND</p>
                <p><strong>Tổng Cuối Cùng:</strong> <%= invoice.getFinalPrice() %> VND</p>
                <p><strong>Ngày Phát Hành:</strong> <%= invoice.getIssuedAt() %></p>
            </div>
            <form action="<%= request.getContextPath() %>/CartController" method="post">
                <input type="hidden" name="action" value="pay">
                <input type="hidden" name="invoiceId" value="<%= invoice.getInvoiceID() %>">
                <button type="submit" class="btn pay-btn">Thanh Toán</button>
            </form>
            <% } else { %>
            <p class="no-data">Không tìm thấy thông tin hóa đơn.</p>
            <% } %>
            <a href="index.jsp" class="back-link">Quay lại cửa hàng</a>
        </div>
        <jsp:include page="footer.jsp" />
    </body>
</html>