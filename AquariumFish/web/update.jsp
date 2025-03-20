<%@page import="dto.FishDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Product</title>
        <link rel="stylesheet" href="assets/css/update.css"/>
    </head>
    <body>
        <jsp:include page="header.jsp" />

        <div class="container">
            <h1>Update Product</h1>
            <%
                String message = (String) request.getAttribute("message");
                if (message != null) {
            %>
            <p style="color: <%= message.contains("success") ? "green" : "red" %>;"><%= message %></p>
            <%
                }
                FishDTO fish = (FishDTO) request.getAttribute("fish");
                if (fish != null) {
            %>
            <form action="FishController" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="fishId" value="<%= fish.getFishID() %>">

                <label for="fishType">Fish Type:</label><br>
                <input type="text" name="fishType" value="<%= fish.getFishType() != null ? fish.getFishType() : "" %>" required>
                <span style="color: red;"><%= request.getAttribute("fishType_error") != null ? request.getAttribute("fishType_error") : "" %></span><br><br>

                <label for="fishName">Fish Name:</label><br>
                <input type="text" name="fishName" value="<%= fish.getFishName() != null ? fish.getFishName() : "" %>" required>
                <span style="color: red;"><%= request.getAttribute("fishName_error") != null ? request.getAttribute("fishName_error") : "" %></span><br><br>

                <label for="fishPrice">Price ($):</label><br>
                <input type="number" step="0.01" name="fishPrice" value="<%= fish.getFishPrice() %>" required>
                <span style="color: red;"><%= request.getAttribute("fishPrice_error") != null ? request.getAttribute("fishPrice_error") : "" %></span><br><br>

                <label for="fishQuantity">Quantity:</label><br>
                <input type="number" name="fishQuantity" value="<%= fish.getFishQuantity() %>" required>
                <span style="color: red;"><%= request.getAttribute("fishQuantity_error") != null ? request.getAttribute("fishQuantity_error") : "" %></span><br><br>

                <label for="fishDescription">Description:</label><br>
                <textarea name="fishDescription" required><%= fish.getFishDescription() != null ? fish.getFishDescription() : "" %></textarea>
                <span style="color: red;"><%= request.getAttribute("fishDescription_error") != null ? request.getAttribute("fishDescription_error") : "" %></span><br><br>

                <label for="fishImg">Image URL:</label><br>
                <input type="text" name="fishImg" value="<%= fish.getFishImg() != null ? fish.getFishImg() : "" %>" required><br><br>

                <label for="categoryID">Category ID:</label><br>
                <input type="number" name="categoryID" value="<%= fish.getCategoryID() %>" required><br><br>

                <input type="submit" value="Update Product">
            </form>
            <%
                } 
            %>
            <br>
            <a href="product.jsp" class="back-link">Back to Product List</a>
        </div>

        <jsp:include page="footer.jsp" />
        <script src="assets/js/index.js"></script>
    </body>
</html>