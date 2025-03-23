<%@page import="dto.UserDTO"%>
<%@page import="dto.OrderDetailDTO"%>
<%@page import="dto.OrderDTO"%>
<%@page import="dao.OrderDAO"%>
<%@page import="dao.OrderDetailDAO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Giỏ Hàng</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f0f8ff;
                margin: 0;
                padding: 0;
            }

            .container {
                width: 90%;
                max-width: 800px;
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

            .cart-table {
                width: 100%;
                border-collapse: collapse;
                background-color: #fff;
                border-radius: 5px;
                overflow: hidden;
                margin-bottom: 20px;
            }

            .cart-table th,
            .cart-table td {
                padding: 12px;
                text-align: center;
                border-bottom: 1px solid #ddd;
            }

            .cart-table th {
                background-color: #0077cc;
                color: white;
                font-weight: bold;
            }

            .cart-table tr:hover {
                background-color: #f5f5f5;
            }

            .cart-table td form {
                margin: 0;
            }

            .cart-table .action-btn {
                background-color: #ff4d4d;
                color: white;
                border: none;
                padding: 6px 12px;
                border-radius: 5px;
                cursor: pointer;
                font-size: 14px;
                transition: background-color 0.3s;
            }

            .cart-table .action-btn:hover {
                background-color: #e60000;
            }

            .checkout-form {
                text-align: center;
                margin-bottom: 20px;
            }

            .checkout-btn {
                background-color: #0077cc;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 5px;
                cursor: pointer;
                font-size: 14px;
                transition: background-color 0.3s;
            }

            .checkout-btn:hover {
                background-color: #005fa3;
            }

            .empty-cart {
                text-align: center;
                color: #666;
                font-style: italic;
                margin: 20px 0;
            }

            .links {
                text-align: center;
            }

            .link-btn {
                display: inline-block;
                background-color: #0077cc;
                color: white;
                padding: 10px 20px;
                text-decoration: none;
                border-radius: 5px;
                font-weight: bold;
                margin: 10px 10px;
                transition: background-color 0.3s;
            }

            .link-btn:hover {
                background-color: #005fa3;
            }

            .login-message {
                text-align: center;
                color: #333;
            }

            .login-message a {
                color: #0077cc;
                text-decoration: none;
                font-weight: bold;
            }

            .login-message a:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div class="container">
            <h1>Giỏ Hàng</h1>
            <%
                UserDTO user = (UserDTO) session.getAttribute("user");
                if (user == null) {
            %>
            <p class="login-message">Vui lòng <a href="login.jsp">đăng nhập</a> để xem giỏ hàng.</p>
            <%
            } else {
                OrderDAO odao = new OrderDAO();
                OrderDetailDAO oddao = new OrderDetailDAO();
                OrderDTO order = odao.getPendingOrderByUserId(user.getUserId());
                if (order != null) {
                    List<OrderDetailDTO> details = oddao.getOrderDetailsByOrderId(order.getOrderID());
                    if (details != null && !details.isEmpty()) {
            %>
            <table class="cart-table">
                <thead>
                    <tr>
                        <th>ID Sản Phẩm</th>
                        <th>Số Lượng</th>
                        <th>Giá</th>
                        <th>Hành Động</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (OrderDetailDTO detail : details) {%>
                    <tr>
                        <td><%= detail.getFishID()%></td>
                        <td><%= detail.getQuantity()%></td>
                        <td><%= String.format("%,.0f", detail.getPrice())%> VND</td>
                        <td>
                            <form action="CartController" method="post">
                                <input type="hidden" name="orderDetailId" value="<%= detail.getOrderDetailID()%>">
                                <input type="hidden" name="action" value="remove">
                                <input type="submit" value="Xóa" class="action-btn">
                            </form>
                        </td>
                    </tr>
                    <% }%>
                </tbody>
            </table>
            <form action="CartController" method="post" class="checkout-form">
                <input type="hidden" name="orderId" value="<%= order.getOrderID()%>">
                <input type="hidden" name="action" value="checkout">
                <input type="submit" value="Mua" class="checkout-btn">
            </form>
            <%
            } else {
            %>
            <p class="empty-cart">Giỏ hàng trống.</p>
            <%
                }
            } else {
            %>
            <p class="empty-cart">Giỏ hàng trống.</p>
            <%
                    }
                }
            %>
            <div class="links">
                <a href="product.jsp" class="link-btn">Tiếp Tục Mua Sắm</a> <br/>

                <a href="index.jsp" class="link-btn">Quay lại</a>
            </div>
        </div>
        <jsp:include page="footer.jsp" />
    </body>
</html>