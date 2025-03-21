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
        <link rel="stylesheet" href="assets/css/index.css">
        <style>
            h1 {
                text-align: center;
                color: #2c3e50;
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
            }

            .filter-select {
                padding: 5px;
                border-radius: 4px;
                border: 1px solid #ccc;
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
                        <option value="">Tất cả danh mục</option>
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
                                <td><a href="FishController?action=details&id=<%= d.getFishID() %>">Xem Chi Tiết</a></td>
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