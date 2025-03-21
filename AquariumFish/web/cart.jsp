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
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <h1>Giỏ Hàng</h1>
        <%
            UserDTO user = (UserDTO) session.getAttribute("user");
            if (user == null) {
        %>
        <p>Vui lòng <a href="login.jsp">đăng nhập</a> để xem giỏ hàng.</p>
        <%
        } else {
            OrderDAO odao = new OrderDAO();
            OrderDetailDAO oddao = new OrderDetailDAO();
            OrderDTO order = odao.getPendingOrderByUserId(user.getUserId());
            if (order != null) {
                List<OrderDetailDTO> details = oddao.getOrderDetailsByOrderId(order.getOrderID());
                if (details != null && !details.isEmpty()) {
        %>
        <table border="1">
            <tr>
                <th>ID Sản Phẩm</th>
                <th>Số Lượng</th>
                <th>Giá</th>
                <th>Hành Động</th>
            </tr>
            <% for (OrderDetailDTO detail : details) {%>
            <tr>
                <td><%= detail.getFishID()%></td>
                <td><%= detail.getQuantity()%></td>
                <td><%= detail.getPrice()%></td>
                <td>
                    <form action="CartController" method="post">
                        <input type="hidden" name="orderDetailId" value="<%= detail.getOrderDetailID()%>">
                        <input type="hidden" name="action" value="remove">
                        <input type="submit" value="Xóa">
                    </form>
                </td>
            </tr>
            <% }%>
        </table>
        <form action="CartController" method="post">
            <input type="hidden" name="orderId" value="<%= order.getOrderID()%>">
            <input type="hidden" name="action" value="checkout">
            <input type="submit" value="Mua">
        </form>
        <%
        } else {
        %>
        <p>Giỏ hàng trống.</p>
        <%
            }
        } else {
        %>
        <p>Giỏ hàng trống.</p>
        <%
                }
            }
        %>
        <a href="index.jsp">Quay lại</a>
        <jsp:include page="footer.jsp" />
    </body>
</html>