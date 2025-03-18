<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Kiến thức về cá cảnh</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                text-align: left;
                background-color: #f0f8ff;
            }
            .container {
                max-width: 50%;
                margin: 20px auto;
                padding: 20px;
                background: white;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            h1 {
                color: #0073e6;
                margin: 10px 0px;
            }
            h2 {
                color: #005bb5;
                margin: 10px 0px;
            }
            p {
                text-align: left;
                margin: 10px 0px;
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

        <div class="container">
            <h1>Kiến thức về cá cảnh</h1>

            <h2>1. Lựa chọn loài cá và bể phù hợp</h2>
            <p>Việc lựa chọn cá và bể phù hợp giúp cá phát triển khỏe mạnh và dễ dàng chăm sóc.</p>
            <p>Nên chọn bể cá có kích thước phù hợp với loài cá bạn nuôi. Cá nhỏ như betta, neon có thể nuôi trong bể nhỏ, còn cá lớn như cá rồng, cá hải tượng cần bể rộng hơn.</p>
            <p>Đảm bảo hệ thống lọc nước hoạt động tốt để giữ cho môi trường nước luôn sạch.</p>

            <h2>2. Hướng dẫn chăm sóc cá cảnh</h2>
            <h3>2.1 Cá nước ngọt</h3>
            <p>Đảm bảo nguồn nước sạch, duy trì nhiệt độ và độ pH ổn định.</p>
            <p>Thay nước thường xuyên (khoảng 20-30% nước mỗi tuần) để giữ môi trường sống trong lành.</p>
            <p>Cung cấp chế độ dinh dưỡng hợp lý, tránh cho ăn quá nhiều.</p>

            <h3>2.2 Cá nước mặn</h3>
            <p>Yêu cầu hệ thống lọc nước tốt và duy trì độ mặn phù hợp.</p>
            <p>Các loài cá nước mặn cần độ mặn ổn định, nên sử dụng muối biển chuyên dụng.</p>
            <p>Cung cấp ánh sáng phù hợp để tạo điều kiện sống gần giống với môi trường tự nhiên.</p>

            <h2>3. Hướng dẫn chăm sóc bể cá</h2>
            <p>Vệ sinh bể thường xuyên, thay nước định kỳ và sử dụng hệ thống lọc phù hợp.</p>
            <p>Loại bỏ thức ăn thừa và cặn bẩn để tránh ô nhiễm nguồn nước.</p>
            <p>Kiểm tra hệ thống lọc, đèn chiếu sáng, nhiệt độ nước để đảm bảo điều kiện tốt nhất cho cá.</p>

            <h2>4. Các loại bệnh thường gặp trên cá cảnh</h2>
            <p>Các bệnh phổ biến như nấm, ký sinh trùng, bệnh đốm trắng,...</p>
            <p>Bệnh nấm thường xuất hiện khi nước bẩn, cá có các đốm trắng trên thân.</p>
            <p>Bệnh ký sinh trùng làm cá gầy yếu, bơi lờ đờ hoặc cọ xát vào vật trong bể.</p>

            <h2>5. Các loại thuốc phổ biến để điều trị</h2>
            <p>Các loại thuốc kháng sinh, thuốc diệt nấm, muối y tế giúp điều trị bệnh hiệu quả.</p>
            <p>Thuốc tím (KMnO4) giúp xử lý nấm và ký sinh trùng.</p>
            <p>Muối hột có thể dùng để sát khuẩn và hỗ trợ điều trị một số bệnh ngoài da cho cá.</p>
        </div>

        <div class="back-home">
            <a href="index.jsp ">Quay lại trang chủ</a>
        </div>
        <jsp:include page="footer.jsp" />
    </body>
</html>
