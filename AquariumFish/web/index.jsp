<%@page import="dto.CategoryDTO"%>
<%@page import="dao.categoryDAO"%>
<%@page import="dto.FishDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<FishDTO> list = (List<FishDTO>) request.getAttribute("fish");
    List<CategoryDTO> category = (List<CategoryDTO>) request.getAttribute("category");
    Integer currentPage = (Integer) request.getAttribute("currentPage");
    Integer totalPages = (Integer) request.getAttribute("totalPages");
    Integer totalFish = (Integer) request.getAttribute("totalFish");

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
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div class="discount-container">
            <p>Tổng số cá: <%= totalFish != null ? totalFish : 0%></p>
            <table class="discount-table">
                <tr>
                    <th>ID</th>
                    <th>Danh mục</th>
                    <th>Tên cá</th>
                    <th>Loại cá</th>
                    <th>Mô tả</th>
                    <th>Giá</th>
                    <th>Số lượng</th>
                    <th>Hình ảnh</th>
                    
                </tr>
                <%
                    if (!list.isEmpty()) {
                        for (FishDTO d : list) {
                %>
                <tr>
                    <td><%= d.getFishID()%></td>
                    <td><%= d.getCategoryID()%></td>
                    <td><%= d.getFishName()%></td>
                    <td><%= d.getFishType()%></td>
                    <td><%= d.getFishDescription()%></td>
                    <td><%= String.format("%,.0f", d.getFishPrice())%> VND</td>
                    <td><%= d.getFishQuantity()%></td>
                    <td><img src="img/<%= d.getFishImg()%>" alt="Fish Image" width="50"></td>

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
            </table>

            <!-- Phân trang -->
            <div class="pagination">
                <%
                    if (totalPages != null && totalPages > 1) {
                        int prevPage = currentPage - 1;
                        int nextPage = currentPage + 1;
                %>
                <a href="FishController?action=pagination&page=<%= prevPage%>" 
                   class="pagination-link <%= currentPage == 1 ? "disabled" : ""%>">Trước</a>
                <%
                    for (int i = 1; i <= totalPages; i++) {
                %>
                <a href="FishController?action=pagination&page=<%= i%>" 
                   class="pagination-link <%= currentPage == i ? "disabled" : ""%>"><%= i%></a>
                <%
                    }
                %>
                <a href="FishController?action=pagination&page=<%= nextPage%>" 
                   class="pagination-link <%= currentPage == totalPages ? "disabled" : ""%>">Sau</a>
                <%
                    }
                %>
            </div>

            <br>
            <a href="index.jsp" class="back-link">Quay lại</a>
        </div>
        <jsp:include page="footer.jsp" />
        <script src="assets/js/index.js"></script>
    </body>
</html>