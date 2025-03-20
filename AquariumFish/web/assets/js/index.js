/* Reset mặc định và style cơ bản */
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    background-color: #e6f7ff;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    color: #333;
    line-height: 1.6;
}

/* Container chính */
.container {
    width: 90%;
    max-width: 1200px;
    margin: 40px auto;
    background: linear-gradient(135deg, #ffffff, #f0f9ff);
    padding: 30px;
    border-radius: 15px;
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
}

.container h1 {
    text-align: center;
    color: #00b7eb; /* Đồng bộ với màu chủ đạo từ JS */
    font-size: 36px;
    font-weight: 700;
    margin-bottom: 30px;
    text-transform: uppercase;
    letter-spacing: 2px;
}

/* Phần hiển thị theo danh mục */
.category-sections {
    display: flex;
    flex-wrap: wrap;
    gap: 30px;
    justify-content: space-between;
    margin-bottom: 40px;
}

.category-section {
    flex: 1;
    min-width: 300px;
    background-color: #f8f9fa;
    padding: 20px;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.category-section:hover {
    transform: translateY(-5px);
    box-shadow: 0 6px 15px rgba(0, 0, 0, 0.12);
}

.category-section h2 {
    color: #00a2d1; /* Đồng bộ với màu hover từ JS */
    font-size: 26px;
    font-weight: 600;
    margin-bottom: 20px;
    text-align: center;
    border-bottom: 2px solid #00b7eb;
    padding-bottom: 10px;
}

.fish-item {
    margin-bottom: 20px;
    padding: 15px;
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    text-align: center;
    transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.fish-item:hover {
    transform: scale(1.02);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.fish-item h3 {
    color: #00b7eb;
    font-size: 20px;
    font-weight: 500;
    margin-bottom: 10px;
}

.fish-item img {
    max-width: 100%;
    height: 150px;
    object-fit: cover;
    border-radius: 8px;
    margin-bottom: 10px;
    border: 1px solid #ddd;
}

.fish-item p {
    margin: 5px 0;
    font-size: 14px;
    color: #555;
}

/* Phần lọc theo danh mục (filter-section) */
.filter-section {
    margin: 30px 0;
    text-align: center;
    background-color: #f8f9fa;
    padding: 15px;
    border-radius: 10px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    transition: box-shadow 0.3s ease;
}

.filter-section:hover {
    box-shadow: 0 6px 15px rgba(0, 0, 0, 0.12);
}

.filter-section form {
    display: inline-flex;
    align-items: center;
    gap: 15px;
}

.filter-section label {
    font-weight: 600;
    font-size: 16px;
    color: #00a2d1; /* Đồng bộ với màu hover từ JS */
    text-transform: uppercase;
    letter-spacing: 1px;
}

.filter-section select {
    padding: 10px 20px;
    border: 2px solid #00b7eb; /* Đồng bộ với màu chủ đạo từ JS */
    border-radius: 8px;
    font-size: 16px;
    background-color: #fff;
    color: #333;
    outline: none;
    cursor: pointer;
    transition: border-color 0.3s ease, box-shadow 0.3s ease, background-color 0.3s ease;
    min-width: 200px;
}

.filter-section select:hover {
    border-color: #00a2d1; /* Đồng bộ với màu hover từ JS */
    background-color: #e6f7ff;
}

.filter-section select:focus {
    border-color: #00a2d1;
    box-shadow: 0 4px 10px rgba(0, 183, 235, 0.2);
    background-color: #fff;
}

.filter-section select option {
    padding: 10px;
    font-size: 16px;
    color: #333;
}

/* Bảng danh sách cá */
.discount-container {
    margin-top: 20px;
}

.discount-container p {
    font-size: 18px;
    font-weight: 500;
    color: #00a2d1;
    margin-bottom: 15px;
    text-align: center;
}

.discount-table {
    width: 100%;
    border-collapse: collapse;
    background-color: #fff;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    border-radius: 10px;
    overflow: hidden;
}

.discount-table th, .discount-table td {
    padding: 15px;
    text-align: center;
    border-bottom: 1px solid #e0e0e0;
}

.discount-table th {
    background: linear-gradient(90deg, #00b7eb, #00a2d1); /* Đồng bộ với màu từ JS */
    color: white;
    font-weight: 600;
    text-transform: uppercase;
    font-size: 14px;
}

.discount-table td {
    font-size: 14px;
    color: #333;
}

.discount-table tr:hover {
    background-color: #e6f7ff;
}

.discount-table img {
    border-radius: 5px;
    border: 1px solid #ddd;
}

.no-data {
    text-align: center;
    color: #ff4444;
    font-weight: 500;
    padding: 15px;
    background-color: #ffebee;
    border-radius: 8px;
    margin: 10px 0;
}

/* Phân trang */
.pagination {
    text-align: center;
    margin-top: 25px;
}

.pagination-link {
    display: inline-block;
    padding: 10px 15px;
    margin: 0 5px;
    background: #00b7eb; /* Đồng bộ với màu mặc định từ JS */
    color: white;
    text-decoration: none;
    border-radius: 8px;
    font-size: 14px;
    font-weight: 500;
    transition: all 0.3s ease;
}

.pagination-link:hover {
    background: #00a2d1; /* Đồng bộ với màu hover từ JS */
    transform: translateY(-2px);
    box-shadow: 0 4px 10px rgba(0, 183, 235, 0.3);
}

.pagination-link.disabled {
    background: #ccc;
    pointer-events: none;
    transform: none;
    box-shadow: none;
}

.pagination-link.active {
    background: #00a2d1; /* Màu khi được chọn (active) */
    font-weight: 700;
    transform: scale(1.1);
    box-shadow: 0 4px 10px rgba(0, 183, 235, 0.3);
}

/* Nút Quay Lại */
.back-link {
    display: inline-block;
    margin: 20px 0;
    padding: 12px 30px;
    background: linear-gradient(90deg, #00b7eb, #00a2d1);
    color: white;
    text-decoration: none;
    border-radius: 25px;
    font-weight: 500;
    font-size: 16px;
    transition: all 0.3s ease;
    box-shadow: 0 4px 10px rgba(0, 183, 235, 0.3);
}

.back-link:hover {
    background: linear-gradient(90deg, #00a2d1, #008bb5);
    transform: translateY(-2px);
    box-shadow: 0 6px 15px rgba(0, 183, 235, 0.4);
}

/* Responsive Design */
@media (max-width: 1024px) {
    .category-sections {
        flex-direction: column;
        gap: 20px;
    }

    .category-section {
        min-width: 100%;
    }

    .container h1 {
        font-size: 28px;
    }

    .discount-table th, .discount-table td {
        padding: 10px;
        font-size: 13px;
    }

    .fish-item img {
        height: 130px;
    }

    .filter-section form {
        flex-direction: column;
        gap: 10px;
    }

    .filter-section select {
        min-width: 100%;
    }
}

@media (max-width: 768px) {
    .container {
        padding: 20px;
    }

    .container h1 {
        font-size: 24px;
    }

    .category-section h2 {
        font-size: 22px;
    }

    .fish-item h3 {
        font-size: 18px;
    }

    .fish-item img {
        height: 120px;
    }

    .discount-table th, .discount-table td {
        padding: 8px;
        font-size: 12px;
    }

    .discount-table img {
        width: 40px;
    }

    .filter-section {
        padding: 10px;
    }

    .filter-section label {
        font-size: 14px;
    }

    .filter-section select {
        padding: 8px 12px;
        font-size: 14px;
    }

    .pagination-link {
        padding: 8px 12px;
        font-size: 12px;
    }

    .back-link {
        padding: 10px 20px;
        font-size: 14px;
    }
}

@media (max-width: 480px) {
    .container {
        padding: 15px;
    }

    .container h1 {
        font-size: 20px;
    }

    .category-section h2 {
        font-size: 20px;
    }

    .fish-item h3 {
        font-size: 16px;
    }

    .fish-item img {
        height: 100px;
    }

    .fish-item p {
        font-size: 12px;
    }

    .discount-table th, .discount-table td {
        padding: 6px;
        font-size: 11px;
    }

    .discount-table img {
        width: 30px;
    }

    .filter-section label {
        font-size: 12px;
    }

    .filter-section select {
        padding: 6px 10px;
        font-size: 12px;
    }

    .pagination-link {
        padding: 6px 10px;
        font-size: 11px;
    }

    .back-link {
        padding: 8px 15px;
        font-size: 12px;
    }
}