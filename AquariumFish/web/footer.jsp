<%-- 
    Document   : footer
    Created on : Feb 19, 2025, 4:56:14 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Footer Cá Cảnh</title>
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: Arial, sans-serif;
            }
            body {
                display: flex;
                flex-direction: column;
                min-height: 100vh;
            }
            .content {
                flex: 1;
            }
            footer {
                background: linear-gradient(to right, #00f2fe, #4facfe);
                padding: 10px 5px;
                text-align: center;
                color: white;
                margin-top: auto;
                width: 100%;
            }
            .footer-content {
                display: flex;
                justify-content: space-around;
                flex-wrap: wrap;
                max-width: 1200px;
                margin: auto;
            }
            .footer-section {
                margin: 5px;
                max-width: 280px;
                text-align: left;
            }
            .footer-section h3 {
                margin-bottom: 10px;
                font-size: 18px;
                border-bottom: 2px solid white;
                padding-bottom: 5px;
            }
            .footer-section a {
                color: white;
                text-decoration: none;
                display: block;
                margin: 6px 0;
                transition: color 0.3s;
            }
            .footer-section a:hover {
                color: #FFD700;
            }
            .social-icons {
                display: flex;
                justify-content: center;
                gap: 12px;
                margin-top: 10px;
            }
            .social-icons a {
                color: white;
                font-size: 18px;
                transition: transform 0.3s;
            }
            .social-icons a:hover {
                transform: scale(1.2);
                color: #FFD700;
            }
            .newsletter input {
                width: 100%;
                padding: 6px;
                border: none;
                border-radius: 5px;
                margin-top: 8px;
                outline: none;
            }
            .newsletter button {
                margin-top: 8px;
                padding: 6px 12px;
                border: none;
                background: #FFD700;
                color: #005f73;
                font-weight: bold;
                cursor: pointer;
                border-radius: 5px;
                transition: background 0.3s;
            }
            .newsletter button:hover {
                background: #FFC107;
            }
            .copyright {
                margin-top: 15px;
                font-size: 14px;
                color: #fff;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <footer>
            <div class="footer-content">
                <div class="footer-section">
                    <h3>Về chúng tôi</h3>
                    <p>AquaFish - Cửa hàng cá cảnh hàng đầu, cung cấp các loại cá đẹp và phụ kiện hồ cá chất lượng.</p>
                </div>
                <div class="footer-section">
                    <h3>Liên hệ</h3>
                    <p>Email: support@aquafish.com</p>
                    <p>Điện thoại: 0123-456-789</p>
                    <div class="social-icons">
                        <a href="#">🔵 Facebook</a>
                        <a href="#">📸 Instagram</a>
                        <a href="#">🎥 YouTube</a>
                    </div>
                </div>
                <div class="footer-section">
                    <h3>Hỗ trợ</h3>
                    <a href="#">Chính sách bảo hành</a>
                    <a href="#">Hướng dẫn mua hàng</a>
                    <a href="#">Điều khoản sử dụng</a>
                </div>
                <div class="footer-section newsletter">
                    <h3>Nhận tin khuyến mãi</h3>
                    <p>Đăng ký để nhận các ưu đãi mới nhất.</p>
                    <input type="email" placeholder="Nhập email của bạn...">
                    <button>Đăng ký</button>
                </div>
            </div>
            <div class="copyright">
                © 2025 - Website Cá Cảnh | Thiên đường của những chú cá xinh đẹp 🐠
            </div>
        </footer>
    </body>
</html>