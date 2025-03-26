<%@page import="utils.AuthenUtils"%>
<%@page import="java.util.List, dto.FishDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product List</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f0f8ff;
                margin: 0;
                padding: 0;
            }

            .container {
                width: 90%;
                max-width: 1200px;
                margin: 50px auto;
                background: #e0f7fa;
                padding: 20px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                border-radius: 10px;
            }

            h1 {
                color: #0077cc;
                text-align: center;
                margin-bottom: 20px;
            }

            h2 {
                color: #005fa3;
                margin: 20px 0 10px;
                font-size: 24px;
            }

            .error {
                color: red;
                font-weight: bold;
                text-align: center;
                margin-bottom: 15px;
            }

            .add-button {
                display: inline-block;
                background-color: #0077cc;
                color: white;
                padding: 10px 20px;
                text-decoration: none;
                border-radius: 5px;
                margin-bottom: 20px;
                font-weight: bold;
                transition: background-color 0.3s;
            }

            .add-button:hover {
                background-color: #005fa3;
            }

            form {
                display: flex;
                justify-content: center;
                margin-bottom: 20px;
            }

            form input[type="text"] {
                width: 300px;
                padding: 8px;
                border: 1px solid #ddd;
                border-radius: 5px 0 0 5px;
                font-size: 14px;
            }

            form input[type="submit"] {
                background-color: #0077cc;
                color: white;
                border: none;
                padding: 8px 15px;
                border-radius: 0 5px 5px 0;
                cursor: pointer;
                font-size: 14px;
                transition: background-color 0.3s;
            }

            form input[type="submit"]:hover {
                background-color: #005fa3;
            }

            .discount-table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
                background-color: #fff;
                border-radius: 5px;
                overflow: hidden;
            }

            .discount-table th, .discount-table td {
                padding: 12px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }

            .discount-table th {
                background-color: #0077cc;
                color: white;
                font-weight: bold;
            }

            .discount-table tr:hover {
                background-color: #f5f5f5;
            }

            .discount-table img {
                max-width: 80px;
                height: auto;
                border-radius: 5px;
            }

            .discount-table a {
                color: #0077cc;
                text-decoration: none;
                font-weight: bold;
            }

            .discount-table a:hover {
                text-decoration: underline;
            }

            .pagination {
                text-align: center;
                margin: 20px 0;
            }

            .pagination-link {
                display: inline-block;
                padding: 8px 12px;
                margin: 0 5px;
                background-color: #0077cc;
                color: white;
                text-decoration: none;
                border-radius: 5px;
                transition: background-color 0.3s;
            }

            .pagination-link:hover {
                background-color: #005fa3;
            }

            .pagination-link.disabled {
                background-color: #ccc;
                pointer-events: none;
            }

            .no-data {
                text-align: center;
                color: #666;
                font-style: italic;
                margin: 20px 0;
            }

            .back-link {
                display: inline-block;
                text-align: center;
                background-color: #0077cc;
                color: white;
                padding: 10px 20px;
                text-decoration: none;
                border-radius: 5px;
                font-weight: bold;
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
            <h1>Danh Sách Sản Phẩm</h1>
            <% if (request.getAttribute("message") != null) {%>
            <p class="error"><%= request.getAttribute("message")%></p>
            <% }%>
            <%if (AuthenUtils.isAdmin(session)) {
            %>
            <a href="add.jsp" class="add-button">Thêm Sản Phẩm</a> <!-- Nút thêm mới -->
            <%}%>
            <form action="FishController" method="get">
                <input type="hidden" name="action" value="viewProducts">
                <input type="text" name="searchTerm" placeholder="Tìm kiếm bằng tên, loại cá,....." value="<%= request.getParameter("searchTerm") != null ? request.getParameter("searchTerm") : ""%>">
                <input type="submit" value="Search"/>
            </form>

            <%
                List<FishDTO> fishList = (List<FishDTO>) request.getAttribute("fish");
                Integer currentPage = (Integer) request.getAttribute("currentPage");
                Integer totalPages = (Integer) request.getAttribute("totalPages");
                Integer totalFish = (Integer) request.getAttribute("totalFish");

                // Nếu fishList là null, chuyển hướng đến FishController để lấy tất cả sản phẩm
                if (fishList == null) {
                    response.sendRedirect("FishController?action=viewProducts&searchTerm=");
                    return;
                }

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
        <table class="discount-table">
            <tr>
                <th>Ảnh Sản Phẩm</th>
                <th>Tên Sản Phẩm</th>
                <th>Giá</th>
                <th>Số Lượng</th>
                <th>Mô Tả</th>
                <th>Xem Chi Tiết</th>
                    <%if (AuthenUtils.isAdmin(session)) {
                    %>
                <th>Hành Động</th>
                    <%}%>
            </tr>
            <%
                }
            %>
            <tr>
                <td><img src="<%= fish.getFishImg()%>" alt="Image"></td>
                <td><%= fish.getFishName()%></td>
                <td><%= String.format("%,.0f", fish.getFishPrice())%> VND</td>                
                <td><%= fish.getFishQuantity()%></td>
                <td><%= fish.getFishDescription()%></td>
                <td><a href="FishController?action=details&id=<%= fish.getFishID()%>">Xem Chi Tiết</a></td>
                <%if (AuthenUtils.isAdmin(session)) {
                %>
                <td><a href="FishController?action=delete&id=<%=fish.getFishID()%>">
                        <img src="img/delete-icon.png" style="height: 25px"/>
                    </a>|<a href="FishController?action=edit&id=<%=fish.getFishID()%>">
                        <img src="img/edit.png" style="height: 25px"/>
                    </a></td>
                    <%}%>
            </tr>
            <%
                }
                // Đóng bảng cuối cùng nếu cần
                if (isTableOpen) {
            %>
        </table>
        <%
            }
        %>
        <!-- Phân trang -->
        <div class="pagination">
            <input type="hidden" name="currentPage" value="<%= currentPage%>">
            <%
                if (totalPages != null && totalPages > 1) {
                    int prevPage = currentPage - 1;
                    int nextPage = currentPage + 1;
                    String searchTerm = request.getParameter("searchTerm") != null ? request.getParameter("searchTerm") : "";
            %>
            <a href="FishController?action=viewProducts&searchTerm=<%= searchTerm%>&page=<%= prevPage%>" 
               class="pagination-link <%= currentPage == 1 ? "disabled" : ""%>">Trước</a>
            <%
                for (int i = 1; i <= totalPages; i++) {
            %>
            <a href="FishController?action=viewProducts&searchTerm=<%= searchTerm%>&page=<%= i%>" 
               class="pagination-link <%= currentPage == i ? "disabled" : ""%>"><%= i%></a>
            <%
                }
            %>
            <a href="FishController?action=viewProducts&searchTerm=<%= searchTerm%>&page=<%= nextPage%>" 
               class="pagination-link <%= currentPage == totalPages ? "disabled" : ""%>">Sau</a>
            <%
                }
            %>
        </div>
        <%
            } else {
                out.println("<p class=\"no-data\">No products found.</p>");
            }
        %>
        <br>
        <a href="index.jsp" class="back-link">Back to Home</a>
    </div>

    <jsp:include page="footer.jsp" />
    <script src="assets/js/index.js"></script>
</body>

</html>
