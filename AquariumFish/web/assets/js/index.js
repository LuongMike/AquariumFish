// assets/js/index.js
document.addEventListener("DOMContentLoaded", function() {
    // Lấy tất cả các liên kết phân trang
    const paginationLinks = document.querySelectorAll(".pagination-link");



    // Thêm sự kiện nhấp chuột cho các liên kết phân trang
    paginationLinks.forEach(link => {
        link.addEventListener("click", function(e) {
            if (this.classList.contains("disabled")) {
                e.preventDefault(); // Ngăn chặn nhấp vào trang bị vô hiệu hóa
            } else {
                // Thêm hiệu ứng cuộn lên đầu bảng
                const table = document.querySelector(".discount-table");
                if (table) {
                    table.scrollIntoView({ behavior: "smooth" });
                }
            }
        });

        // Thêm hiệu ứng hover
        link.addEventListener("mouseover", function() {
            if (!this.classList.contains("disabled") && !this.classList.contains("active")) {
                this.style.backgroundColor = "#00a2d1";
            }
        });

        link.addEventListener("mouseout", function() {
            if (!this.classList.contains("disabled") && !this.classList.contains("active")) {
                this.style.backgroundColor = "#00b7eb";
            }
        });
    });

    // Gọi hàm làm nổi bật khi tải trang
    highlightCurrentPage();

    // Cập nhật trang hiện tại khi nhấp (nếu cần thêm logic phía client)
    paginationLinks.forEach(link => {
        link.addEventListener("click", function(e) {
            if (!this.classList.contains("disabled")) {
                document.querySelectorAll(".pagination-link").forEach(l => l.classList.remove("active"));
                this.classList.add("active");
            }
        });
    });
});