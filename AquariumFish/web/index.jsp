<%@page import="dto.FishDTO"%>
<%@page import="dto.DiscountDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Lấy danh sách discount từ request
    List<FishDTO> list = (List<FishDTO>) request.getAttribute("fish");

    // Nếu danh sách bị null (truy cập trực tiếp JSP), tự động chuyển hướng đến DiscountController
    if (list == null) {
        response.sendRedirect("FishController");
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
            
            <table class="discount-table">
                <tr>
                    <th>ID</th>
                    <th>Code</th>
                    <th>Phần trăm giảm</th>
                    <th>Số tiền giảm tối đa</th>
                    <th>Ngày bắt đầu</th>
                    <th>Ngày kết thúc</th>
                    <th>Trạng thái</th>
                    <th>Trạng thái</th>
                </tr>
                <%
                    if (!list.isEmpty()) {
                        for (FishDTO d : list) {
                %>
                <tr>
                    <td><%= d.getFishID()%></td>
                    <td><%= d.getFishName()%></td>
                    <td><%= d.getFishType()%>%</td>
                    <td><%= d.getFishDescription()%> VND</td>
                    <td><%= d.getFishPrice()%></td>
                    <td><%= d.getFishQuantity()%></td>
                    <td><img src="img/<%= d.getFishImg()%>" alt="Fish Image"></td>
                    <td><%= d.getCategoryID()%></td>
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
