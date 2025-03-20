<%@page import="java.util.List, dto.FishDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product List</title>
        <link rel="stylesheet" href="assets/css/product.css"/>
    </head>
    <body>
        <jsp:include page="header.jsp" />

        <div class="container">
            <h1>Product List</h1>
            <a href="add.jsp" class="add-button">Add Product</a> <!-- Nút thêm mới -->
            <form action="FishController" method="get">
                <input type="hidden" name="action" value="viewProducts">
                <input type="text" name="searchTerm" placeholder="Search by type, name..." value="<%= request.getParameter("searchTerm") != null ? request.getParameter("searchTerm") : ""%>">
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
                <th>Image</th>
                <th>Name</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Description</th>
                <th>action</th>
            </tr>
            <%
                }
            %>
            <tr>
                <td><img src="img/<%= fish.getFishImg()%>" alt="Image"></td>
                <td><%= fish.getFishName()%></td>
                <td>$<%= String.format("%,.2f", fish.getFishPrice())%></td>
                <td><%= fish.getFishQuantity()%></td>
                <td><%= fish.getFishDescription()%></td>
                <td><a href="FishController?action=delete&id=<%=fish.getFishID()%>">
                        <img src="img/delete-icon.png" style="height: 25px"/>
                    </a>|<a href="FishController?action=edit&id=<%=fish.getFishID()%>">
                        <img src="img/edit.png" style="height: 25px"/>
                    </a></td>
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
