<%-- 
    Document   : fishTankModel
    Created on : Mar 6, 2025, 9:42:03 AM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            /* Định dạng phần nội dung chính, không ảnh hưởng header/footer */
            .content {
                width: 100%;
                background: white;
                padding: 20px;
                box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
                border-radius: 10px;
                text-align: left;
                margin: 20px auto;  
            }

            /* Tiêu đề chính */
            .content h1 {
                color: #0077b6;
                font-size: 30px;
                margin-bottom: 20px;
            }

            /* Tiêu đề phụ */
            .content h3 {
                color: #023e8a;
                font-size: 16px;
                margin-top: 20px;
            }

            /* Định dạng đoạn văn */
            .content h5 {
                font-size: 15px;
                line-height: 1.6;
                color: #333;
                text-align: justify;
                margin-bottom: 15px;
                margin-top: 5px;
            }
            .incontent {
                width: 75%;
                margin-left: 13%;
            }

            .image-container {
                display: flex;
                justify-content: space-between; /* Căn cách đều ảnh */
                align-items: center;
                flex-wrap: wrap; /* Đảm bảo ảnh không bị tràn nếu không đủ không gian */
                margin-top: 20px;
            }

            .image-container div {
                text-align: center;
                width: 23%; /* Chia đều 4 phần, có thể điều chỉnh */
            }

            .image-container img {
                width: 100%; /* Ảnh tự động co giãn theo khung */
                height: auto;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            .image-container p {
                margin: 5px 0;
                font-size: 14px;
            }

            .price {
                font-weight: bold;
                color: #d62828;
            }

            .status {
                font-style: italic;
                color: #666;
            }

            .back-home {
                display: block;
                text-align: center;
                margin-top: 20px;
                margin-bottom: 50px; /* Tạo khoảng cách với footer */
            }


            .back-home a {
                padding: 10px 20px;
                background: #0077b6;
                color: white;
                text-decoration: none;
                border-radius: 5px;
                transition: 0.3s;
            }

            .back-home a:hover {
                background: #023e8a;
            }

        </style>
    </head>
    <body>
        <jsp:include page="header.jsp" />

        <div class="content">
            <div class="incontent">
                <h1>Bể Cá Cảnh</h1>
                <h3><strong>Hồ Cá – Điểm Nhấn Sống Động Cho Không Gian</strong></h3>
                <h5>Hồ cá không chỉ là một vật trang trí mà còn giúp tạo cảm giác thư giãn, mang lại vẻ đẹp tự nhiên và cải thiện phong thủy. Những chú cá đầy màu sắc bơi lội trong làn nước trong xanh sẽ trở thành điểm nhấn thu hút trong không gian của bạn.</h5>

                <h3><strong>Lợi Ích Của Hồ Cá</strong></h3>
                <h5>Nuôi cá cảnh giúp giảm căng thẳng, cải thiện chất lượng không khí và tăng tính thẩm mỹ cho không gian sống. Một số loại hồ cá phổ biến hiện nay gồm hồ cá thủy sinh, hồ cá nước mặn và hồ cá Koi – mỗi loại mang một vẻ đẹp và đặc trưng riêng biệt.</h5>

                <h3><strong>Chăm Sóc Hồ Cá Đúng Cách</strong></h3>
                <h5>Để cá phát triển khỏe mạnh, cần duy trì chất lượng nước tốt, sử dụng hệ thống lọc phù hợp và cung cấp chế độ ăn uống khoa học. Ngoài ra, việc bố trí cây thủy sinh và phụ kiện hợp lý cũng giúp hồ cá trở nên sinh động và tự nhiên hơn.</h5>

                <h3><strong>Tận Hưởng Không Gian Thư Giãn</strong></h3>
                <h5>Hồ cá không chỉ làm đẹp không gian mà còn mang lại sự bình yên, giúp bạn thư giãn sau những giờ làm việc căng thẳng. Dù bạn là người mới chơi hay đã có kinh nghiệm, hãy tận hưởng thế giới đầy màu sắc và phong phú của hồ cá cảnh!</h5>
            </div>
        </div>

        <div class="image-container">
            <div>
                <img src="img/beca1.jpeg" class="img-fluid" alt="Bể Iwagumi Đá Tự Nhiên">
                <p>Bể Iwagumi Đá Tự Nhiên</p>
                <p class="price">1.000.000đ</p>
                <p class="status">Hết Hàng</p>
            </div>

            <div>
                <img src="img/beca2.jpg" class="img-fluid" alt="Bể Thủy Sinh Mini Rừng Rậm">
                <p>Bể Thủy Sinh Mini Rừng Rậm</p>
                <p class="price">700.000đ</p>
                <p class="status">Hết Hàng</p>
            </div>

            <div>
                <img src="img/beca1.jpeg" class="img-fluid" alt="Bể Cá Cảnh Đá Núi Hùng Vĩ">
                <p>Bể Cá Cảnh Đá Núi Hùng Vĩ</p>
                <p class="price">2.600.000đ</p>
                <p class="status">Hết Hàng</p>
            </div>

            <div>
                <img src="img/beca1.jpeg" class="img-fluid" alt="Bể Thủy Sinh Mini Tròn Độc Đáo">
                <p>Bể Thủy Sinh Mini Tròn Độc Đáo</p>
                <p class="price">500.000đ</p>
                <p class="status">Hết Hàng</p>
            </div>
        </div>

        <div class="back-home">
            <a href="mainPage.jsp ">Quay lại trang chủ</a>
        </div>

        <jsp:include page="footer.jsp" />
    </body>
</html>
