<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Product</title>
        <link rel="stylesheet" href="assets/css/header.css"/>
        <link rel="stylesheet" href="assets/css/update.css"/> <!-- Sử dụng CSS chung với update.jsp -->
    </head>
    <body class="update-page">
        <jsp:include page="header.jsp" />

        <div class="container">
            <h1>Add Product</h1>
            <%
                String message = (String) request.getAttribute("message");
                if (message != null) {
            %>
            <p class="message <%= message.contains("success") ? "success" : "error" %>"><%= message %></p>
            <%
                }
            %>
            <form action="FishController" method="post">
                <input type="hidden" name="action" value="add">

                <label for="fishType">Fish Type:</label><br>
                <input type="text" name="fishType" value="<%= request.getParameter("fishType") != null ? request.getParameter("fishType") : "" %>" required>
                <span class="error-message"><%= request.getAttribute("fishType_error") != null ? request.getAttribute("fishType_error") : "" %></span><br><br>

                <label for="fishName">Fish Name:</label><br>
                <input type="text" name="fishName" value="<%= request.getParameter("fishName") != null ? request.getParameter("fishName") : "" %>" required>
                <span class="error-message"><%= request.getAttribute("fishName_error") != null ? request.getAttribute("fishName_error") : "" %></span><br><br>

                <label for="fishPrice">Price ($):</label><br>
                <input type="number" step="0.01" name="fishPrice" value="<%= request.getParameter("fishPrice") != null ? request.getParameter("fishPrice") : "" %>" required>
                <span class="error-message"><%= request.getAttribute("fishPrice_error") != null ? request.getAttribute("fishPrice_error") : "" %></span><br><br>

                <label for="fishQuantity">Quantity:</label><br>
                <input type="number" name="fishQuantity" value="<%= request.getParameter("fishQuantity") != null ? request.getParameter("fishQuantity") : "" %>" required>
                <span class="error-message"><%= request.getAttribute("fishQuantity_error") != null ? request.getAttribute("fishQuantity_error") : "" %></span><br><br>

                <label for="fishDescription">Description:</label><br>
                <textarea name="fishDescription" required><%= request.getParameter("fishDescription") != null ? request.getParameter("fishDescription") : "" %></textarea>
                <span class="error-message"><%= request.getAttribute("fishDescription_error") != null ? request.getAttribute("fishDescription_error") : "" %></span><br><br>

                <label for="fishImg">Image URL:</label><br>
                <input type="text" name="fishImg" value="<%= request.getParameter("fishImg") != null ? request.getParameter("fishImg") : "" %>" required><br><br>

                <label for="categoryID">Category ID:</label><br>
                <input type="number" name="categoryID" value="<%= request.getParameter("categoryID") != null ? request.getParameter("categoryID") : "" %>" required><br><br>

                <input type="submit" value="Add Product">
            </form>
            <br>
            <a href="product.jsp" class="back-link">Back to Product List</a>
        </div>

        <jsp:include page="footer.jsp" />
        <script src="assets/js/index.js"></script>
    </body>
</html>