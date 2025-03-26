<%@page import="dto.FishDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <title>Chi Tiết Cá</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f0f8ff;
                margin: 0;
                padding: 0;
            }

            .container {
                width: 90%;
                max-width: 1000px;
                margin: 50px auto;
                background: #e0f7fa;
                padding: 20px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                border-radius: 10px;
            }

            h1 {
                text-align: center;
                color: #2c3e50;
                margin-bottom: 20px;
            }

            .message {
                text-align: center;
                font-weight: bold;
                margin-bottom: 20px;
            }

            .message.success {
                color: green;
            }

            .message.error {
                color: red;
            }

            .fish-details {
                display: flex;
                flex-wrap: wrap;
                gap: 20px;
                margin-bottom: 20px;
            }

            .fish-image {
                flex: 1;
                min-width: 300px;
                text-align: center;
            }

            .fish-image img {
                max-width: 100%;
                height: auto;
                border-radius: 10px;
                border: 1px solid #ddd;
            }

            .fish-image p {
                color: #666;
                font-style: italic;
            }

            .fish-info {
                flex: 1;
                min-width: 300px;
                background: #fff;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
            }

            .fish-info p {
                margin: 10px 0;
                color: #333;
            }

            .fish-info p strong {
                color: #0077cc;
                display: inline-block;
                width: 150px;
            }

            .fish-info form {
                margin: 10px 0;
                display: inline-block;
            }

            .btn {
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 14px;
                transition: background-color 0.3s;
            }

            .add-to-cart {
                background-color: #0077cc;
                color: white;
                margin-right: 10px;
            }

            .add-to-cart:hover {
                background-color: #005fa3;
            }

            .buy-now {
                background-color: #28a745;
                color: white;
            }

            .buy-now:hover {
                background-color: #218838;
            }

            .error a {
                color: #0077cc;
                text-decoration: none;
                font-weight: bold;
            }

            .error a:hover {
                text-decoration: underline;
            }

            .no-data {
                text-align: center;
                color: #666;
                font-style: italic;
                margin: 20px 0;
            }

            .back-link {
                display: block;
                text-align: center;
                background-color: #0077cc;
                color: white;
                padding: 10px 20px;
                text-decoration: none;
                border-radius: 5px;
                font-weight: bold;
                margin-top: 20px;
                transition: background-color 0.3s;
            }

            .back-link:hover {
                background-color: #005fa3;
            }

            @media (max-width: 768px) {
                .fish-details {
                    flex-direction: column;
                    align-items: center;
                }

                .fish-info p strong {
                    width: 120px;
                }
            }

            .quantity-input {
                margin: 10px 0;
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .quantity-input label {
                font-weight: bold;
                color: #333;
            }

            .quantity-input input {
                width: 60px;
                padding: 5px;
                border: 1px solid #ddd;
                border-radius: 5px;
                font-size: 14px;
                text-align: center;
            }

            .quantity-input input::-webkit-outer-spin-button,
            .quantity-input input::-webkit-inner-spin-button {
                -webkit-appearance: none;
                margin: 0;
            }

            .quantity-input input[type=number] {
                -moz-appearance: textfield;
            }
        </style>
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
            <p class="message <%= message.contains("thành công") ? "success" : "error"%>"><%= message%></p>
            <% } %>
            <%
                if (fish != null) {
            %>
            <div class="fish-details">
                <div class="fish-image">
                    <% if (fish.getFishImg() != null && !fish.getFishImg().isEmpty()) {%>
                    <img src="<%= fish.getFishImg()%>" alt="<%= fish.getFishName()%>">
                    <% } else { %>
                    <p>Không có hình ảnh</p>
                    <% }%>
                </div>
                <div class="fish-info">
                    <p><strong>ID:</strong> <%= fish.getFishID()%></p>
                    <p><strong>Loại Cá:</strong> <%= fish.getFishType()%></p>
                    <p><strong>Tên Cá:</strong> <%= fish.getFishName()%></p>
                    <p><strong>Giá:</strong> <%= String.format("%,.0f", fish.getFishPrice())%> VND</p>
                    <p><strong>Số Lượng Còn Lại:</strong> <%= fish.getFishQuantity()%></p>
                    <p><strong>Mô Tả:</strong> <%= fish.getFishDescription()%></p>
                    <p><strong>Danh Mục ID:</strong> <%= fish.getCategoryID()%></p>
                    <%
                        Integer userId = (Integer) session.getAttribute("userId");
                        if (userId != null) {
                    %>
                    <form action="<%= request.getContextPath()%>/CartController" method="post">
                        <input type="hidden" name="fishId" value="<%= fish.getFishID()%>">
                        <input type="hidden" name="action" value="add">
                        <div class="quantity-input">
                            <label for="quantity-add">Số lượng:</label>
                            <button type="button" onclick="changeQuantity('quantity-add', -1)"><i class="fas fa-minus"></i></button>
                            <input type="number" id="quantity-add" name="quantity" value="1" min="1" max="<%= fish.getFishQuantity()%>" required readonly>
                            <button type="button" onclick="changeQuantity('quantity-add', 1)"><i class="fas fa-plus"></i></button>                        </div>
                        <button type="submit" class="btn add-to-cart">Thêm Sản Phẩm</button>
                    </form>
                    <form action="<%= request.getContextPath()%>/CartController" method="post">
                        <input type="hidden" name="fishId" value="<%= fish.getFishID()%>">
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
            <% }%>
            <a href="index.jsp" class="back-link">Quay lại</a>
        </div>
        <jsp:include page="footer.jsp" />
        <script>
            function changeQuantity(inputId, delta) {
                const input = document.getElementById(inputId);
                const min = parseInt(input.min);
                const max = parseInt(input.max);
                let value = parseInt(input.value) + delta;

                if (value < min) {
                    value = min;
                    alert("Số lượng không thể nhỏ hơn 1!");
                }
                if (value > max) {
                    value = max;
                    alert("Số lượng không thể vượt quá " + max + "!");
                }

                input.value = value;

                // Cập nhật trạng thái nút
                const decreaseBtn = document.getElementById('decrease-' + inputId.split('-')[1]);
                const increaseBtn = document.getElementById('increase-' + inputId.split('-')[1]);
                decreaseBtn.disabled = (value === min);
                increaseBtn.disabled = (value === max);
            }
        </script>
    </body>
</html>