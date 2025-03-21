<%@page import="dto.FishDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chi Tiết Cá</title>
        <link rel="stylesheet" href="assets/css/details.css">
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div class="container">
            <h1>Chi Tiết Cá</h1>
            <% 
                FishDTO fish = (FishDTO) request.getAttribute("fish");
                String message = (String) request.getAttribute("message");
                if (message != null) {
            %>
                <p class="message <%= message.contains("thành công") ? "success" : "error" %>"><%= message %></p>
            <% } %>
            <% 
                if (fish != null) {
            %>
            <div class="fish-details">
                <div class="fish-image">
                    <% if (fish.getFishImg() != null && !fish.getFishImg().isEmpty()) { %>
                        <img src="<%= fish.getFishImg() %>" alt="<%= fish.getFishName() %>">
                    <% } else { %>
                        <p>Không có hình ảnh</p>
                    <% } %>
                </div>
                <div class="fish-info">
                    <p><strong>ID:</strong> <%= fish.getFishID() %></p>
                    <p><strong>Loại Cá:</strong> <%= fish.getFishType() %></p>
                    <p><strong>Tên Cá:</strong> <%= fish.getFishName() %></p>
                    <p><strong>Giá:</strong> <%= fish.getFishPrice() %> VND</p>
                    <p><strong>Số Lượng Còn Lại:</strong> <%= fish.getFishQuantity() %></p>
                    <p><strong>Mô Tả:</strong> <%= fish.getFishDescription() %></p>
                    <p><strong>Danh Mục ID:</strong> <%= fish.getCategoryID() %></p>
                    <% 
                        Integer userId = (Integer) session.getAttribute("userId");
                        if (userId != null) {
                    %>
                    <form action="<%= request.getContextPath() %>/CartController" method="post">
                        <input type="hidden" name="fishId" value="<%= fish.getFishID() %>">
                        <input type="hidden" name="action" value="add">
                        <button type="submit" class="btn add-to-cart">Add to Cart</button>
                    </form>
                    <form action="<%= request.getContextPath() %>/CartController" method="post">
                        <input type="hidden" name="fishId" value="<%= fish.getFishID() %>">
                        <input type="hidden" name="action" value="buyNow">
                        <button type="submit" class="btn buy-now">Mua Ngay</button>
                    </form>
                    <% } else { %>
                    <p class="error">Vui lòng <a href="login.jsp">đăng nhập</a> để thêm vào giỏ hàng!</p>
                    <% } %>
                </div>
            </div>
            <% } else { %>
            <p class="no-data">Không tìm thấy thông tin cá.</p>
            <% } %>
            <a href="index.jsp" class="back-link">Quay lại</a>
        </div>
        <jsp:include page="footer.jsp" />
    </body>
</html>