<%-- 
    Document   : invalidAccountInformation
    Created on : Feb 19, 2025, 4:00:53 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lỗi Đăng Nhập</title>
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, #ff9a9e, #fad0c4); /* Gradient hồng cam nhẹ nhàng */
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background: white;
            padding: 40px;
            border-radius: 15px;
            text-align: center;
            box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.2);
            width: 350px;
            animation: fadeIn 1s ease-in-out;
        }
        .container h2 {
            color: #d32f2f;
            margin-bottom: 20px;
            font-size: 26px;
        }
        .container p {
            color: #333;
            font-size: 16px;
        }
        .btn {
            display: inline-block;
            margin-top: 15px;
            padding: 12px 25px;
            background: #ff5722;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 18px;
            font-weight: bold;
            cursor: pointer;
            text-decoration: none;
            transition: 0.3s;
        }
        .btn:hover {
            background: #e64a19;
            transform: scale(1.05);
        }
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-10px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>❌ Lỗi Đăng Nhập</h2>
        <p>Tài khoản hoặc mật khẩu không đúng. Vui lòng thử lại!</p>
        <a href="login.jsp" class="btn">🔄 Thử Lại</a>
    </div>
</body>
</html>
