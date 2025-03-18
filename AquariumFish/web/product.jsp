<%@page import="java.util.List, dto.FishDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product List</title>
        <style>
            .container {
                width: 80%;
                margin: 20px auto;
                background: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
            }

            .container h1 {
                text-align: center;
                color: #007BFF;
                font-size: 28px;
            }

            .container form {
                text-align: center;
                margin-bottom: 20px;
            }

            .container input[type="text"] {
                width: 50%;
                padding: 10px;
                font-size: 16px;
                border: 2px solid #007BFF;
                border-radius: 5px;
                outline: none;
            }

            .container table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
                background: white;
            }

            .container th, .container td {
                padding: 12px;
                text-align: center;
                border: 1px solid #ddd;
            }

            .container th {
                background-color: #007BFF;
                color: white;
                font-weight: bold;
            }

            .container tr:nth-child(even) {
                background-color: #f2f2f2;
            }

            .container img {
                width: 100px;
                height: auto;
                border-radius: 5px;
            }

            .container h2 {
                margin-top: 20px;
                color: #007BFF;
                border-bottom: 2px solid #007BFF;
                padding-bottom: 5px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp" />

        <div class="container">
            <h1>Product List</h1>

            <form action="FishController" method="get">
                <input type="hidden" name="action" value="viewProducts">
                <input type="text" name="searchTerm" placeholder="Search by type, name..." value="<%= request.getParameter("searchTerm") != null ? request.getParameter("searchTerm") : ""%>">
                <input type="submit" value="Search"/>
            </form>

            <%
                List<FishDTO> fishList = (List<FishDTO>) request.getAttribute("fish");
                if (fishList != null && !fishList.isEmpty()) {
                    String currentFishType = "";
                    boolean isTableOpen = false; // Biến kiểm tra xem bảng đã mở chưa

                    for (FishDTO fish : fishList) {
                        if (!fish.getFishType().equals(currentFishType)) {
                            // Đóng bảng trước đó nếu có
                            if (isTableOpen) {
            %>
        </table>
        <%
            }
            // Cập nhật loại cá mới
            currentFishType = fish.getFishType();
            isTableOpen = true;
        %>
        <h2><%= currentFishType%></h2>
        <table>
            <tr>
                <th>Image</th>
                <th>Name</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Description</th>
            </tr>
            <%
                }
            %>
            <tr>
                <td><img src="img/<%= fish.getFishImg()%>" alt="Image"></td>
                <td><%= fish.getFishName()%></td>
                <td>$<%= fish.getFishPrice()%></td>
                <td><%= fish.getFishQuantity()%></td>
                <td><%= fish.getFishDescription()%></td>
            </tr>
            <%
                }
                // Đóng bảng cuối cùng nếu cần
                if (isTableOpen) {
            %>
        </table>
        <%
                }
            } else {
                out.println("<p>No products found.</p>");
            }
        %>
    </div>
</body>
</html>
