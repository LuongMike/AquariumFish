<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Giới Thiệu Về Chúng Tôi</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f8ff;
            color: #333;
        }

        .container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
        }

        h1 {
            color: #0077cc;
            text-align: center;
            margin-bottom: 20px;
        }

        .section {
            background-color: #fff;
            padding: 20px;
            margin-bottom: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .section h2 {
            color: #005fa3;
            margin-bottom: 15px;
        }

        .section p {
            line-height: 1.6;
            margin-bottom: 10px;
        }

        .team {
            display: flex;
            justify-content: space-around;
            flex-wrap: wrap;
            margin-top: 20px;
        }

        .team-member {
            text-align: center;
            margin: 10px;
        }

        .team-member img {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            object-fit: cover;
            margin-bottom: 10px;
        }

        .team-member h3 {
            color: #0077cc;
            margin-bottom: 5px;
        }

        .team-member p {
            color: #666;
        }

        .contact-info {
            text-align: center;
            margin-top: 20px;
        }

        .contact-info p {
            margin: 5px 0;
        }

        .contact-info a {
            color: #0077cc;
            text-decoration: none;
        }

        .contact-info a:hover {
            text-decoration: underline;
        }

        .back-link {
            display: inline-block;
            margin: 20px auto;
            padding: 10px 20px;
            background-color: #0077cc;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
            text-align: center;
        }

        .back-link:hover {
            background-color: #005fa3;
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp" />

    <div class="container">
        <h1>Giới Thiệu Về Chúng Tôi</h1>

        <!-- Phần giới thiệu về trang web -->
        <div class="section">
            <h2>Chào Mừng Đến Với FishMart</h2>
            <p>
                FishMart là nền tảng trực tuyến hàng đầu chuyên cung cấp các loại cá cảnh và cá thực phẩm chất lượng cao. 
                Chúng tôi tự hào mang đến cho khách hàng những sản phẩm tươi ngon, đa dạng, được tuyển chọn kỹ lưỡng từ các nhà cung cấp uy tín.
            </p>
            <p>
                Sứ mệnh của chúng tôi là kết nối người yêu cá với những sản phẩm tốt nhất, đồng thời cung cấp trải nghiệm mua sắm trực tuyến tiện lợi, nhanh chóng và an toàn.
                Tại FishMart, bạn có thể dễ dàng tìm thấy các loại cá cảnh độc đáo để trang trí bể cá của mình, hoặc các loại cá tươi ngon để chế biến những bữa ăn tuyệt vời.
            </p>
        </div>

        <!-- Phần giá trị cốt lõi -->
        <div class="section">
            <h2>Giá Trị Cốt Lõi</h2>
            <p><strong>Chất lượng:</strong> Chúng tôi cam kết chỉ cung cấp những sản phẩm đạt tiêu chuẩn cao nhất.</p>
            <p><strong>Uy tín:</strong> Aquarium Fish luôn đặt sự hài lòng của khách hàng lên hàng đầu.</p>
            <p><strong>Đa dạng:</strong> Đáp ứng mọi nhu cầu của khách hàng với danh mục sản phẩm phong phú.</p>
            <p><strong>Tiện lợi:</strong> Mua sắm dễ dàng, thanh toán an toàn, giao hàng nhanh chóng.</p>
        </div>

        <!-- Phần đội ngũ (có thể tùy chỉnh) -->
        <div class="section">
            <h2>Đội Ngũ Của Chúng Tôi</h2>
            <p>
                Aquarium Fish được xây dựng bởi một đội ngũ đam mê với cá và công nghệ. Chúng tôi luôn nỗ lực để mang đến trải nghiệm tốt nhất cho khách hàng.
            </p>
            <div class="team">
                <div class="team-member">
                    <img src="img/hi.jpg" alt="Team Member 1">
                    <h3>Đoàn Văn Lương</h3>
                    <p>Nhà sáng lập & CEO</p>
                </div>

            </div>
        </div>

        <!-- Phần thông tin liên hệ -->
        <div class="section">
            <h2>Liên Hệ Với Chúng Tôi</h2>
            <div class="contact-info">
                <p><strong>Email:</strong> <a href="mailto:support@AquariumFish.com">support@AquariumFish.com</a></p>
                <p><strong>Hotline:</strong> 0123 456 789</p>
                <p><strong>Địa chỉ:</strong> 123 Đường Cá Cảnh, Quận 1, TP. Hồ Chí Minh</p>
                <p><strong>Thời gian làm việc:</strong> 8:00 - 17:00, Thứ 2 - Thứ 7</p>
            </div>
        </div>

        <a href="index.jsp" class="back-link">Quay lại trang chủ</a>
    </div>

    <jsp:include page="footer.jsp" />
</body>
</html>