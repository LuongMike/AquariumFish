<%@page import="utils.AuthenUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dto.CategoryDTO"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thêm Cá Mới</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f0f8ff;
                margin: 0;
                padding: 0;
            }

            .container {
                width: 90%;
                max-width: 600px;
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

            .message {
                font-weight: bold;
                margin: 10px 0;
                text-align: center;
            }

            .message.error {
                color: red;
            }

            .message.success {
                color: green;
            }

            .fish-form {
                display: flex;
                flex-direction: column;
            }

            .form-group {
                margin-bottom: 15px;
            }

            .form-group label {
                display: block;
                margin-bottom: 5px;
                color: #333;
                font-weight: bold;
            }

            .form-group input,
            .form-group textarea,
            .form-group select {
                width: 100%;
                padding: 8px;
                border: 1px solid #ddd;
                border-radius: 5px;
                box-sizing: border-box;
                font-size: 14px;
            }

            .form-group textarea {
                height: 100px;
                resize: vertical;
            }

            .form-group select {
                appearance: none;
                -webkit-appearance: none;
                -moz-appearance: none;
                background: url('data:image/svg+xml;utf8,<svg fill="gray" height="24" viewBox="0 0 24 24" width="24" xmlns="http://www.w3.org/2000/svg"><path d="M7 10l5 5 5-5z"/></svg>') no-repeat right 10px center;
                background-size: 16px;
            }

            .form-group select:focus {
                outline: none;
                border-color: #0077cc;
            }

            .form-group input[type="file"] {
                padding: 3px;
            }

            .form-group .error {
                color: red;
                font-size: 12px;
                margin-top: 5px;
                display: block;
            }

            #fileInfo {
                font-size: 12px;
                color: #666;
                margin-top: 5px;
                display: block;
            }

            #progressContainer {
                width: 100%;
                background-color: #f0f0f0;
                border-radius: 5px;
                margin-top: 10px;
            }

            #progressBar {
                width: 0%;
                height: 20px;
                background-color: #0077cc;
                border-radius: 5px;
                transition: width 0.3s;
            }

            #imagePreview {
                margin-top: 10px;
                text-align: center;
            }

            #imagePreview img {
                max-width: 100%;
                height: auto;
                border-radius: 5px;
                border: 1px solid #ddd;
            }

            .form-group button {
                background-color: #0077cc;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 5px;
                cursor: pointer;
                font-size: 14px;
                margin-right: 10px;
                transition: background-color 0.3s;
            }

            .form-group button:hover {
                background-color: #005fa3;
            }

            .form-group button#resetBtn {
                background-color: #ff4d4d;
            }

            .form-group button#resetBtn:hover {
                background-color: #e60000;
            }

            .back-link {
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
            .error-container {
                text-align: center;
                padding: 20px;
            }

            .error-container h1 {
                color: #ff4d4d;
                margin-bottom: 10px;
            }

            .error-container p {
                color: #666;
                font-size: 16px;
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div class="container">
            <% if (AuthenUtils.isAdmin(session)) { %>
            <h1>Thêm Cá Mới</h1>
            <% String message = (String) request.getAttribute("message"); %>
            <% if (message != null) {%>
            <p class="message <%= message.contains("successfully") ? "success" : "error"%>"><%= message%></p>
            <% }%>
            <form action="FishController" method="post" class="fish-form">
                <input type="hidden" name="action" value="add">

                <div class="form-group">
                    <label for="fishType">Loại cá:</label>
                    <input type="text" id="fishType" name="fishType" value="<%= request.getAttribute("fishType") != null ? request.getAttribute("fishType") : ""%>">
                    <% if (request.getAttribute("fishType_error") != null) {%>
                    <span class="error"><%= request.getAttribute("fishType_error")%></span>
                    <% }%>
                </div>

                <div class="form-group">
                    <label for="fishName">Tên cá:</label>
                    <input type="text" id="fishName" name="fishName" value="<%= request.getAttribute("fishName") != null ? request.getAttribute("fishName") : ""%>">
                    <% if (request.getAttribute("fishName_error") != null) {%>
                    <span class="error"><%= request.getAttribute("fishName_error")%></span>
                    <% }%>
                </div>

                <div class="form-group">
                    <label for="fishPrice">Giá:</label>
                    <input type="number" id="fishPrice" name="fishPrice" step="0.01" value="<%= request.getAttribute("fishPrice") != null ? request.getAttribute("fishPrice") : ""%>">
                    <% if (request.getAttribute("fishPrice_error") != null) {%>
                    <span class="error"><%= request.getAttribute("fishPrice_error")%></span>
                    <% }%>
                </div>

                <div class="form-group">
                    <label for="fishQuantity">Số lượng:</label>
                    <input type="number" id="fishQuantity" name="fishQuantity" value="<%= request.getAttribute("fishQuantity") != null ? request.getAttribute("fishQuantity") : ""%>">
                    <% if (request.getAttribute("fishQuantity_error") != null) {%>
                    <span class="error"><%= request.getAttribute("fishQuantity_error")%></span>
                    <% }%>
                </div>

                <div class="form-group">
                    <label for="fishDescription">Mô tả:</label>
                    <textarea id="fishDescription" name="fishDescription"><%= request.getAttribute("fishDescription") != null ? request.getAttribute("fishDescription") : ""%></textarea>
                    <% if (request.getAttribute("fishDescription_error") != null) {%>
                    <span class="error"><%= request.getAttribute("fishDescription_error")%></span>
                    <% }%>
                </div>

                <div class="form-group">
                    <label for="categoryID">Danh mục:</label>
                    <select id="categoryID" name="categoryID">
                        <option value="">Chọn danh mục</option>
                        <option value="1" <%= "1".equals(request.getAttribute("categoryID")) ? "selected" : ""%>>Danh mục 1</option>
                        <option value="2" <%= "2".equals(request.getAttribute("categoryID")) ? "selected" : ""%>>Danh mục 2</option>
                    </select>
                    <% if (request.getAttribute("categoryID_error") != null) {%>
                    <span class="error"><%= request.getAttribute("categoryID_error")%></span>
                    <% }%>
                </div>

                <div class="form-group">
                    <label for="imageUpload">Hình ảnh:</label>
                    <input type="file" id="imageUpload" name="imageUpload" accept="image/*">
                    <input type="hidden" id="txtImage" name="txtImage" value="<%= request.getAttribute("fishImg") != null ? request.getAttribute("fishImg") : ""%>">
                    <span id="fileInfo">Chưa chọn file</span>
                    <div id="progressContainer" style="display: none;">
                        <div id="progressBar"></div>
                    </div>
                    <div id="imagePreview">
                        <% if (request.getAttribute("fishImg") != null) {%>
                        <img src="<%= request.getAttribute("fishImg")%>" alt="Xem trước">
                        <% }%>
                    </div>
                    <% if (request.getAttribute("fishImg_error") != null) {%>
                    <span class="error"><%= request.getAttribute("fishImg_error")%></span>
                    <% }%>
                </div>

                <div class="form-group">
                    <button type="submit">Thêm Cá</button>
                    <button type="button" id="resetBtn">Reset Ảnh</button>
                </div>
            </form>
            <a href="index.jsp" class="back-link">Quay lại</a>
            <% } else { %>
            <div class="form-container error-container">
                <h1>403 Error</h1>
                <p>You do not have permission to access this content!</p>
                <a href="login.jsp" class="back-link">Back to Login</a>
            </div>
            <% }%>
        </div>
        <jsp:include page="footer.jsp" />
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="assets/js/imageConverter.js"></script>
    </body>
</html>