<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dto.CategoryDTO"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thêm Cá Mới</title>
        <link rel="stylesheet" href="assets/css/index.css">
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div class="container">
            <h1>Thêm Cá Mới</h1>
            <% String message = (String) request.getAttribute("message"); %>
            <% if (message != null) { %>
                <p class="message <%= message.contains("successfully") ? "success" : "error" %>"><%= message %></p>
            <% } %>
            <form action="FishController" method="post" class="fish-form">
                <input type="hidden" name="action" value="add">

                <div class="form-group">
                    <label for="fishType">Loại cá:</label>
                    <input type="text" id="fishType" name="fishType" value="<%= request.getAttribute("fishType") != null ? request.getAttribute("fishType") : "" %>">
                    <% if (request.getAttribute("fishType_error") != null) { %>
                        <span class="error"><%= request.getAttribute("fishType_error") %></span>
                    <% } %>
                </div>

                <div class="form-group">
                    <label for="fishName">Tên cá:</label>
                    <input type="text" id="fishName" name="fishName" value="<%= request.getAttribute("fishName") != null ? request.getAttribute("fishName") : "" %>">
                    <% if (request.getAttribute("fishName_error") != null) { %>
                        <span class="error"><%= request.getAttribute("fishName_error") %></span>
                    <% } %>
                </div>

                <div class="form-group">
                    <label for="fishPrice">Giá:</label>
                    <input type="number" id="fishPrice" name="fishPrice" step="0.01" value="<%= request.getAttribute("fishPrice") != null ? request.getAttribute("fishPrice") : "" %>">
                    <% if (request.getAttribute("fishPrice_error") != null) { %>
                        <span class="error"><%= request.getAttribute("fishPrice_error") %></span>
                    <% } %>
                </div>

                <div class="form-group">
                    <label for="fishQuantity">Số lượng:</label>
                    <input type="number" id="fishQuantity" name="fishQuantity" value="<%= request.getAttribute("fishQuantity") != null ? request.getAttribute("fishQuantity") : "" %>">
                    <% if (request.getAttribute("fishQuantity_error") != null) { %>
                        <span class="error"><%= request.getAttribute("fishQuantity_error") %></span>
                    <% } %>
                </div>

                <div class="form-group">
                    <label for="fishDescription">Mô tả:</label>
                    <textarea id="fishDescription" name="fishDescription"><%= request.getAttribute("fishDescription") != null ? request.getAttribute("fishDescription") : "" %></textarea>
                    <% if (request.getAttribute("fishDescription_error") != null) { %>
                        <span class="error"><%= request.getAttribute("fishDescription_error") %></span>
                    <% } %>
                </div>

                <div class="form-group">
                    <label for="categoryID">Danh mục:</label>
                    <select id="categoryID" name="categoryID">
                        <option value="">Chọn danh mục</option>
                        <option value="1" <%= "1".equals(request.getAttribute("categoryID")) ? "selected" : "" %>>Danh mục 1</option>
                        <option value="2" <%= "2".equals(request.getAttribute("categoryID")) ? "selected" : "" %>>Danh mục 2</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="imageUpload">Hình ảnh:</label>
                    <input type="file" id="imageUpload" name="imageUpload" accept="image/*">
                    <input type="hidden" id="txtImage" name="txtImage" value="<%= request.getAttribute("fishImg") != null ? request.getAttribute("fishImg") : "" %>">
                    <span id="fileInfo">Chưa chọn file</span>
                    <div id="progressContainer" style="display: none;">
                        <div id="progressBar"></div>
                    </div>
                    <div id="imagePreview">
                        <% if (request.getAttribute("fishImg") != null) { %>
                            <img src="<%= request.getAttribute("fishImg") %>" alt="Xem trước">
                        <% } %>
                    </div>
                </div>

                <div class="form-group">
                    <button type="submit">Thêm Cá</button>
                    <button type="button" id="resetBtn">Reset Ảnh</button>
                </div>
            </form>
            <a href="index.jsp" class="back-link">Quay lại</a>
        </div>
        <jsp:include page="footer.jsp" />
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="assets/js/imageConverter.js"></script>
    </body>
</html>