<%-- 
    Document   : mainPage
    Created on : Feb 19, 2025, 4:27:26 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Trang Chủ - Thế Giới Cá Cảnh</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background: #E0F7FA;
            }
            .fontText{
                font: revert;
                font-size: 40px;
            }
            .fontText1{
                font-family: cursive;
                padding-top: 5px;
            }
            .banner {
                position: relative;
                text-align: center;
            }
            .banner img {
                width: 100%;
                height: auto;
            }
            .fish-list {
                display: flex;
                justify-content: center;
                flex-wrap: wrap;
                padding: 20px 0;
            }
            .fish-item {
                text-align: center;
                margin: 10px;
            }
            .fish-item img {
                width: 100px;
                height: 100px;
                border-radius: 50%;
                object-fit: cover;
                border: 3px solid #007bff;
            }
            .fish-section {
                background: white;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
                margin-bottom: 30px;
                border: 1px solid black;
            }
            .fish-section h2 {
                margin-bottom: 20px;
                font-family: none;
            }
            .fish-card {
                border: 1px solid black;
                padding: 15px;
                border-radius: 10px;
                box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
                text-align: center;
                margin: 10px;
            }
            .fish-card img {
                width: 190px;
                height: 120px;
                object-fit: cover;
                border: 5px solid crimson;
                border-radius: 10px;
            }
            .fish-card p {
                margin: 5px 0;
            }
            .fish-card .price {
                font-weight: bold;
                color: red;
            }
            .fish-card .status {
                font-weight: bold;
                color: green;
            }
            .fish-card .out-of-stock {
                color: red;
            }
        </style>
    </head>
    <body>
        <%@include file="header.jsp"%>

        <div class="container mt-4">
            <!-- Banner -->
            <div class="banner">
                <img src="img/banner.jpg" alt="Banner Cá Cảnh">
            </div>

            <!-- Danh sách cá -->
            <div class="text-center mt-4 ">
                <h2 class="fontText">Danh Mục Cá Cảnh</h2>
            </div>
            <div class="fish-list">
                <div class="fish-item">
                    <img src="img/neon.jpg" alt="Cá Neon">
                    <p class="fontText1">Cá Neon</p>
                </div>
                <div class="fish-item">
                    <img src="img/cadia.jpg" alt="Cá Dĩa">
                    <p class="fontText1">Cá Dĩa</p>
                </div>
                <div class="fish-item">
                    <img src="img/than_tien.jpg" alt="Cá Thần Tiên">
                    <p class="fontText1">Cá Thần Tiên</p>
                </div>
                <div class="fish-item">
                    <img src="img/carong.jpg" alt="Cá Rồng">
                    <p class="fontText1">Cá Rồng</p>
                </div>
                <div class="fish-item">
                    <img src="img/guppy.jpg" alt="Cá Guppy(Cá 7 Màu)">
                    <p class="fontText1">Cá Guppy(Cá 7 Màu)</p>
                </div>
                <div class="fish-item">
                    <img src="img/cavang.jpg" alt="Cá Vàng">
                    <p class="fontText1">Cá Vàng</p>
                </div>
                <div class="fish-item">
                    <img src="img/chuot.jpg" alt="Cá La Hán">
                    <p class="fontText1">Cá La Hán</p>
                </div>
                <div class="fish-item">
                    <img src="img/betta.jpg" alt="Cá Betta">
                    <p class="fontText1">Cá Betta</p>
                </div>
            </div>

            <!-- Khu vực trưng bày cá -->
            <!-- Khu vực 1 -->
            <div class="fish-section">
                <h2>Cá Dĩa</h2>
                <div class="row justify-content-center">
                    <div class="col-md-3 fish-card" >
                        <img src="img/cavang.jpg" class="img-fluid" alt="Cá Dĩa">
                        <p>Cá Dĩa Bồ Câu Đỏ</p>
                        <p class="price">160.000đ</p>
                        <p class="status">Còn Hàng</p>
                    </div>
                    <div class="col-md-3 fish-card">
                        <img src="img/cavang.jpg" class="img-fluid" alt="Cá Dĩa">
                        <p>Cá Dĩa Bồ Câu Panda</p>
                        <p class="price">160.000đ</p>
                        <p class="status">Còn Hàng</p>
                    </div>
                    <div class="col-md-3 fish-card">
                        <img src="img/cavang.jpg" class="img-fluid" alt="Cá Dĩa">
                        <p>Cá Dĩa Bồ Câu Vàng</p>
                        <p class="price">160.000đ</p>
                        <p class="status">Còn Hàng</p>
                    </div>
                    <div class="col-md-3 fish-card">
                        <img src="img/cavang.jpg" class="img-fluid" alt="Cá Dĩa">
                        <p>Cá Dĩa Bông Xanh</p>
                        <p class="price">160.000đ</p>
                        <p class="status">Còn Hàng</p>
                    </div>
                    <div class="col-md-3 fish-card">
                        <img src="img/cavang.jpg" class="img-fluid" alt="Cá Dĩa">
                        <p>Cá Dĩa Bông Xanh</p>
                        <p class="price">160.000đ</p>
                        <p class="status">Còn Hàng</p>
                    </div>
                    <div class="col-md-3 fish-card">
                        <img src="img/cavang.jpg" class="img-fluid" alt="Cá Dĩa">
                        <p>Cá Dĩa Bông Xanh</p>
                        <p class="price">160.000đ</p>
                        <p class="status">Còn Hàng</p>
                    </div>
                </div>
            </div>

            <!-- Khu vực 2 -->
            <div class="fish-section">
                <h2>Cá Dĩa</h2>
                <div class="row justify-content-center">
                    <div class="col-md-3 fish-card" >
                        <img src="img/cavang.jpg" class="img-fluid" alt="Cá Dĩa">
                        <p>Cá Dĩa Bồ Câu Đỏ</p>
                        <p class="price">160.000đ</p>
                        <p class="status">Còn Hàng</p>
                    </div>
                    <div class="col-md-3 fish-card">
                        <img src="img/cavang.jpg" class="img-fluid" alt="Cá Dĩa">
                        <p>Cá Dĩa Bồ Câu Panda</p>
                        <p class="price">160.000đ</p>
                        <p class="status">Còn Hàng</p>
                    </div>
                    <div class="col-md-3 fish-card">
                        <img src="img/cavang.jpg" class="img-fluid" alt="Cá Dĩa">
                        <p>Cá Dĩa Bồ Câu Vàng</p>
                        <p class="price">160.000đ</p>
                        <p class="status">Còn Hàng</p>
                    </div>
                    <div class="col-md-3 fish-card">
                        <img src="img/cavang.jpg" class="img-fluid" alt="Cá Dĩa">
                        <p>Cá Dĩa Bông Xanh</p>
                        <p class="price">160.000đ</p>
                        <p class="status">Còn Hàng</p>
                    </div>
                    <div class="col-md-3 fish-card">
                        <img src="img/cavang.jpg" class="img-fluid" alt="Cá Dĩa">
                        <p>Cá Dĩa Bông Xanh</p>
                        <p class="price">160.000đ</p>
                        <p class="status">Còn Hàng</p>
                    </div>
                    <div class="col-md-3 fish-card">
                        <img src="img/cavang.jpg" class="img-fluid" alt="Cá Dĩa">
                        <p>Cá Dĩa Bông Xanh</p>
                        <p class="price">160.000đ</p>
                        <p class="status">Còn Hàng</p>
                    </div>
                </div>
            </div>

            <!-- Khu vực 3 -->
            <div class="fish-section">
                <h2>Cá Dĩa</h2>
                <div class="row justify-content-center">
                    <div class="col-md-3 fish-card" >
                        <img src="img/cavang.jpg" class="img-fluid" alt="Cá Dĩa">
                        <p>Cá Dĩa Bồ Câu Đỏ</p>
                        <p class="price">160.000đ</p>
                        <p class="status">Còn Hàng</p>
                    </div>
                    <div class="col-md-3 fish-card">
                        <img src="img/cavang.jpg" class="img-fluid" alt="Cá Dĩa">
                        <p>Cá Dĩa Bồ Câu Panda</p>
                        <p class="price">160.000đ</p>
                        <p class="status">Còn Hàng</p>
                    </div>
                    <div class="col-md-3 fish-card">
                        <img src="img/cavang.jpg" class="img-fluid" alt="Cá Dĩa">
                        <p>Cá Dĩa Bồ Câu Vàng</p>
                        <p class="price">160.000đ</p>
                        <p class="status">Còn Hàng</p>
                    </div>
                    <div class="col-md-3 fish-card">
                        <img src="img/cavang.jpg" class="img-fluid" alt="Cá Dĩa">
                        <p>Cá Dĩa Bông Xanh</p>
                        <p class="price">160.000đ</p>
                        <p class="status">Còn Hàng</p>
                    </div>
                    <div class="col-md-3 fish-card">
                        <img src="img/cavang.jpg" class="img-fluid" alt="Cá Dĩa">
                        <p>Cá Dĩa Bông Xanh</p>
                        <p class="price">160.000đ</p>
                        <p class="status">Còn Hàng</p>
                    </div>
                    <div class="col-md-3 fish-card">
                        <img src="img/cavang.jpg" class="img-fluid" alt="Cá Dĩa">
                        <p>Cá Dĩa Bông Xanh</p>
                        <p class="price">160.000đ</p>
                        <p class="status">Còn Hàng</p>
                    </div>
                </div>
            </div>

            <!-- Khu vực 4 -->
            <div class="fish-section">
                <h2>Cá Dĩa</h2>
                <div class="row justify-content-center">
                    <div class="col-md-3 fish-card" >
                        <img src="img/cavang.jpg" class="img-fluid" alt="Cá Dĩa">
                        <p>Cá Dĩa Bồ Câu Đỏ</p>
                        <p class="price">160.000đ</p>
                        <p class="status">Còn Hàng</p>
                    </div>
                    <div class="col-md-3 fish-card">
                        <img src="img/cavang.jpg" class="img-fluid" alt="Cá Dĩa">
                        <p>Cá Dĩa Bồ Câu Panda</p>
                        <p class="price">160.000đ</p>
                        <p class="status">Còn Hàng</p>
                    </div>
                    <div class="col-md-3 fish-card">
                        <img src="img/cavang.jpg" class="img-fluid" alt="Cá Dĩa">
                        <p>Cá Dĩa Bồ Câu Vàng</p>
                        <p class="price">160.000đ</p>
                        <p class="status">Còn Hàng</p>
                    </div>
                    <div class="col-md-3 fish-card">
                        <img src="img/cavang.jpg" class="img-fluid" alt="Cá Dĩa">
                        <p>Cá Dĩa Bông Xanh</p>
                        <p class="price">160.000đ</p>
                        <p class="status">Còn Hàng</p>
                    </div>
                    <div class="col-md-3 fish-card">
                        <img src="img/cavang.jpg" class="img-fluid" alt="Cá Dĩa">
                        <p>Cá Dĩa Bông Xanh</p>
                        <p class="price">160.000đ</p>
                        <p class="status">Còn Hàng</p>
                    </div>
                    <div class="col-md-3 fish-card">
                        <img src="img/cavang.jpg" class="img-fluid" alt="Cá Dĩa">
                        <p>Cá Dĩa Bông Xanh</p>
                        <p class="price">160.000đ</p>
                        <p class="status">Còn Hàng</p>
                    </div>
                </div>
            </div>

        </div>
    


    <%@include file="footer.jsp"%>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
