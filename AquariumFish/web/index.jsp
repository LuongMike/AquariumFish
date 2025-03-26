<%@page import="dto.CategoryDTO"%>
<%@page import="dao.categoryDAO"%>
<%@page import="dto.FishDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<FishDTO> list = (List<FishDTO>) request.getAttribute("fish");
    List<CategoryDTO> categories = (List<CategoryDTO>) request.getAttribute("categories");
    Integer currentPage = (Integer) request.getAttribute("currentPage");
    Integer totalPages = (Integer) request.getAttribute("totalPages");
    Integer totalFish = (Integer) request.getAttribute("totalFish");
    String categoryFilter = (String) request.getAttribute("categoryFilter");

    if (list == null) {
        response.sendRedirect("FishController?action=pagination");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Danh sách cá</title>
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
                text-align: center;
                color: #2c3e50;
                margin-bottom: 20px;
            }

            /* Filter Section */
            .filter-section {
                text-align: center;
                margin-bottom: 20px;
            }

            .filter-form {
                display: inline-block;
            }

            .filter-label {
                font-weight: bold;
                margin-right: 10px;
                color: #333;
            }

            .filter-select {
                padding: 8px;
                border-radius: 5px;
                border: 1px solid #ccc;
                font-size: 14px;
                background: url('data:image/svg+xml;utf8,<svg fill="gray" height="24" viewBox="0 0 24 24" width="24" xmlns="http://www.w3.org/2000/svg"><path d="M7 10l5 5 5-5z"/></svg>') no-repeat right 10px center;
                background-size: 16px;
                appearance: none;
                -webkit-appearance: none;
                -moz-appearance: none;
            }

            .filter-select:focus {
                outline: none;
                border-color: #0077cc;
            }

            /* Discount Container */
            .discount-container {
                margin-top: 20px;
            }

            .total-fish {
                text-align: center;
                font-weight: bold;
                color: #333;
                margin-bottom: 15px;
            }

            .table-wrapper {
                overflow-x: auto;
            }

            .discount-table {
                width: 100%;
                border-collapse: collapse;
                background-color: #fff;
                border-radius: 5px;
                overflow: hidden;
            }

            .discount-table th,
            .discount-table td {
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

            .discount-table .table-image {
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

            .discount-table .no-data {
                text-align: center;
                color: #666;
                font-style: italic;
                padding: 20px;
            }

            /* Pagination */
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

            .pagination-link.active {
                background-color: #005fa3;
                font-weight: bold;
            }

            .pagination-link.disabled {
                background-color: #ccc;
                pointer-events: none;
            }

            /* Back Link */
            .back-link {
                width: 15%;
                margin-left: 43%;
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
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp" />

        <div class="container">
            <h1>Danh Sách Cá</h1>

            <!-- Phần lọc theo danh mục -->
            <div class="filter-section">
                <form action="FishController" method="get" class="filter-form">
                    <input type="hidden" name="action" value="pagination">
                    <input type="hidden" name="page" value="<%= currentPage != null ? currentPage : 1%>">
                    <label for="categoryFilter" class="filter-label">Lọc theo danh mục:</label>
                    <select name="categoryFilter" id="categoryFilter" class="filter-select" onchange="this.form.submit()">

                        <%
                            if (categories != null) {
                                for (CategoryDTO category : categories) {
                        %>
                        <option value="<%= category.getCategoryID()%>" <%= categoryFilter != null && categoryFilter.equals(String.valueOf(category.getCategoryID())) ? "selected" : ""%>>
                            <%= category.getCategoryName()%>
                        </option>
                        <%
                                }
                            }
                        %>
                    </select>
                </form>
            </div>

            <!-- Bảng danh sách cá hiện tại -->
            <div class="discount-container">
                <p class="total-fish">Tổng số cá: <%= totalFish != null ? totalFish : 0%></p>
                <div class="table-wrapper">
                    <table class="discount-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Danh mục</th>
                                <th>Tên cá</th>
                                <th>Loại cá</th>
                                <th>Mô tả</th>
                                <th>Giá</th>
                                <th>Số lượng</th>
                                <th>Hình ảnh</th>
                                <th>Xem Chi Tiết</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                if (!list.isEmpty()) {
                                    for (FishDTO d : list) {
                            %>
                            <tr>
                                <td><%= d.getFishID()%></td>
                                <td><%= d.getCategoryName() != null ? d.getCategoryName() : d.getCategoryID()%></td>
                                <td><%= d.getFishName()%></td>
                                <td><%= d.getFishType()%></td>
                                <td><%= d.getFishDescription()%></td>
                                <td><%= String.format("%,.0f", d.getFishPrice())%> VND</td>
                                <td><%= d.getFishQuantity()%></td>
                                <td><img src="<%= d.getFishImg()%>" alt="Fish Image" class="table-image"></td>
                                <td><a href="FishController?action=details&id=<%= d.getFishID()%>">Xem Chi Tiết</a></td>
                            </tr>
                            <%
                                }
                            } else {
                            %>
                            <tr>
                                <td colspan="8" class="no-data">Không có cá nào.</td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>

                <!-- Phân trang -->
                <div class="pagination">
                    <%
                        if (totalPages != null && totalPages > 1) {
                            int prevPage = currentPage - 1;
                            int nextPage = currentPage + 1;
                            String filterParam = categoryFilter != null ? "&categoryFilter=" + categoryFilter : "";
                    %>
                    <a href="FishController?action=pagination&page=<%= prevPage%><%= filterParam%>" 
                       class="pagination-link <%= currentPage == 1 ? "disabled" : ""%>">Trước</a>
                    <%
                        for (int i = 1; i <= totalPages; i++) {
                    %>
                    <a href="FishController?action=pagination&page=<%= i%><%= filterParam%>" 
                       class="pagination-link <%= currentPage == i ? "active" : ""%>"><%= i%></a>
                    <%
                        }
                    %>
                    <a href="FishController?action=pagination&page=<%= nextPage%><%= filterParam%>" 
                       class="pagination-link <%= currentPage == totalPages ? "disabled" : ""%>">Sau</a>
                    <%
                        }
                    %>
                </div>

                <br>
                <a href="index.jsp" class="back-link">Quay lại</a>
            </div>
        </div>

        <jsp:include page="footer.jsp" />
        <script src="assets/js/index.js"></script>
    </body>
</html>