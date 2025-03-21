<%@page import="dto.FishDTO"%>
<%@page import="dto.CategoryDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cập Nhật Cá</title>
        <link rel="stylesheet" href="assets/css/update.css">
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div class="container">
            <h1>Cập Nhật Cá</h1>
            <% String message = (String) request.getAttribute("message"); %>
            <% if (message != null) { %>
                <p class="message <%= message.contains("successfully") ? "success" : "error" %>"><%= message %></p>
            <% } %>
            <% 
                FishDTO fish = (FishDTO) request.getAttribute("fish");
                if (fish != null) {
            %>
            <form action="FishController" method="post" class="fish-form">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="fishId" value="<%= fish.getFishID() %>">

                <div class="form-group">
                    <label for="fishType">Loại cá:</label>
                    <input type="text" id="fishType" name="fishType" value="<%= fish.getFishType() != null ? fish.getFishType() : "" %>">
                    <% if (request.getAttribute("fishType_error") != null) { %>
                        <span class="error"><%= request.getAttribute("fishType_error") %></span>
                    <% } %>
                </div>

                <div class="form-group">
                    <label for="fishName">Tên cá:</label>
                    <input type="text" id="fishName" name="fishName" value="<%= fish.getFishName() != null ? fish.getFishName() : "" %>">
                    <% if (request.getAttribute("fishName_error") != null) { %>
                        <span class="error"><%= request.getAttribute("fishName_error") %></span>
                    <% } %>
                </div>

                <div class="form-group">
                    <label for="fishPrice">Giá:</label>
                    <input type="number" id="fishPrice" name="fishPrice" step="0.01" value="<%= fish.getFishPrice() %>">
                    <% if (request.getAttribute("fishPrice_error") != null) { %>
                        <span class="error"><%= request.getAttribute("fishPrice_error") %></span>
                    <% } %>
                </div>

                <div class="form-group">
                    <label for="fishQuantity">Số lượng:</label>
                    <input type="number" id="fishQuantity" name="fishQuantity" value="<%= fish.getFishQuantity() %>">
                    <% if (request.getAttribute("fishQuantity_error") != null) { %>
                        <span class="error"><%= request.getAttribute("fishQuantity_error") %></span>
                    <% } %>
                </div>

                <div class="form-group">
                    <label for="fishDescription">Mô tả:</label>
                    <textarea id="fishDescription" name="fishDescription"><%= fish.getFishDescription() != null ? fish.getFishDescription() : "" %></textarea>
                    <% if (request.getAttribute("fishDescription_error") != null) { %>
                        <span class="error"><%= request.getAttribute("fishDescription_error") %></span>
                    <% } %>
                </div>

                <div class="form-group">
                    <label for="categoryID">Danh mục:</label>
                    <select id="categoryID" name="categoryID">
                        <option value="">Chọn danh mục</option>
                        <option value="1" <%= fish.getCategoryID() == 1 ? "selected" : "" %>>Danh mục 1</option>
                        <option value="2" <%= fish.getCategoryID() == 2 ? "selected" : "" %>>Danh mục 2</option>
                    </select>
                    <% if (request.getAttribute("categoryID_error") != null) { %>
                        <span class="error"><%= request.getAttribute("categoryID_error") %></span>
                    <% } %>
                </div>

                <div class="form-group">
                    <label for="imageUpload">Hình ảnh:</label>
                    <input type="file" id="imageUpload" name="imageUpload" accept="image/*">
                    <input type="hidden" id="txtImage" name="txtImage" value="<%= fish.getFishImg() != null ? fish.getFishImg() : "" %>">
                    <span id="fileInfo">Chưa chọn file</span>
                    <div id="progressContainer" style="display: none;">
                        <div id="progressBar"></div>
                    </div>
                    <div id="imagePreview">
                        <% if (fish.getFishImg() != null && !fish.getFishImg().isEmpty()) { %>
                            <img src="<%= fish.getFishImg() %>" alt="Hình ảnh hiện tại">
                        <% } %>
                    </div>
                </div>

                <div class="form-group">
                    <button type="submit">Cập Nhật</button>
                    <button type="button" id="resetBtn">Reset Ảnh</button>
                </div>
            </form>
            <% } else { %>
            <p class="no-data">Không tìm thấy cá để cập nhật.</p>
            <% } %>
            <a href="index.jsp" class="back-link">Quay lại</a>
        </div>
        <jsp:include page="footer.jsp" />
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="assets/js/imageConverter.js"></script>
    </body>
</html>