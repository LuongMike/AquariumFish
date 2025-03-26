<%@page import="dto.DiscountDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Lấy danh sách discount từ request
    List<DiscountDTO> list = (List<DiscountDTO>) request.getAttribute("discount");

    // Nếu danh sách bị null (truy cập trực tiếp JSP), tự động chuyển hướng đến DiscountController
    if (list == null) {
        response.sendRedirect("DiscountController");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Danh sách mã giảm giá</title>
        <style>
            /* ===== Chỉ áp dụng cho bảng danh sách mã giảm giá ===== */
            .discount-container {
                text-align: center;
                margin: 30px auto;
                width: 90%;
                max-width: 1200px;
                background: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            .discount-table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
                background: #fff;
            }

            .discount-table th, 
            .discount-table td {
                border: 1px solid #ddd;
                padding: 10px;
                text-align: center;
            }

            .discount-table th {
                background-color: #4CAF50;
                color: white;
                font-weight: bold;
            }

            .discount-table td {
                background-color: #fafafa;
            }

            .discount-table tr:nth-child(even) {
                background-color: #f2f2f2;
            }

            /* ===== Trạng thái "Không có mã giảm giá" ===== */
            .no-data {
                text-align: center;
                color: red;
                font-weight: bold;
            }

            /* ===== Nút quay lại ===== */
            .back-link {
                display: inline-block;
                margin-top: 20px;
                padding: 10px 15px;
                background: #4CAF50;
                color: white;
                text-decoration: none;
                border-radius: 5px;
            }

            .back-link:hover {
                background: #45a049;
            }

            .discount-container p {
                font-size: 16px;
                font-weight: bold;
                color: #333;
                margin: 10px 0;
            }


        </style>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div class="discount-container">
            <h1>Danh sách mã giảm giá</h1>
            <p>Hướng dẫn sử dụng:</p>
            <p>Sao chép mã Code còn hạn sử dụng và áp dụng vào đơn hàng của bạn.</p>
            <p>(Lưu ý: Mỗi mã chỉ sử dụng trên 1 đơn hàng!)</p>
            <table class="discount-table">
                <tr>
                    <th>ID</th>
                    <th>Code</th>
                    <th>Phần trăm giảm</th>
                    <th>Số tiền giảm tối đa</th>
                    <th>Ngày bắt đầu</th>
                    <th>Ngày kết thúc</th>
                    <th>Trạng thái</th>
                </tr>
                <%
                    if (!list.isEmpty()) {
                        for (DiscountDTO d : list) {
                %>
                <tr>
                    <td><%= d.getDiscoutID()%></td>
                    <td><%= d.getCode()%></td>
                    <td><%= d.getDiscount_percentage()%>%</td>
                    <td><%= String.format("%,.0f", d.getDiscount_amount())%> VND</td>
                    <td><%= d.getStart_date()%></td>
                    <td><%= d.getEnd_date()%></td>
                    <td><%= d.getStatus()%></td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="7" class="no-data">Không có mã giảm giá nào.</td>
                </tr>
                <%
                    }
                %>
            </table>
            <br>
            <a href="index.jsp" class="back-link">Quay lại</a>
        </div>

        <jsp:include page="footer.jsp" />

    </body>
</html>
