$(document).ready(function () {
    // Xử lý khi chọn file ảnh
    $('#imageUpload').change(function () {
        const file = this.files[0];
        if (file) {
            // Kiểm tra định dạng file
            if (!file.type.match('image.*')) {
                alert('Vui lòng chọn file hình ảnh (JPEG, PNG, GIF, v.v.)');
                this.value = '';
                $('#fileInfo').text('Chưa chọn file');
                $('#imagePreview').empty();
                $('#txtImage').val('');
                return;
            }

            // Hiển thị thông tin file
            const fileSize = (file.size / 1024).toFixed(2) + ' KB';
            $('#fileInfo').text(file.name + ' (' + fileSize + ')');

            // Hiển thị thanh tiến trình
            $('#progressContainer').show();

            const reader = new FileReader();

            // Cập nhật tiến trình
            reader.onprogress = function (event) {
                if (event.lengthComputable) {
                    const percentLoaded = Math.round((event.loaded / event.total) * 100);
                    $('#progressBar').css('width', percentLoaded + '%');
                }
            };

            // Khi đọc file thành công
            reader.onload = function (e) {
                $('#progressBar').css('width', '100%');
                const base64String = e.target.result;
                $('#txtImage').val(base64String);
                $('#imagePreview').html('<img src="' + base64String + '" alt="Xem trước">');

                // Ẩn thanh tiến trình sau 1 giây
                setTimeout(function () {
                    $('#progressContainer').hide();
                    $('#progressBar').css('width', '0%');
                }, 1000);
            };

            // Xử lý lỗi
            reader.onerror = function () {
                alert('Lỗi khi đọc file. Vui lòng thử lại.');
                $('#progressContainer').hide();
                $('#progressBar').css('width', '0%');
                $('#fileInfo').text('Chưa chọn file');
                $('#imagePreview').empty();
                $('#txtImage').val('');
            };

            reader.readAsDataURL(file);
        } else {
            $('#fileInfo').text('Chưa chọn file');
            $('#imagePreview').empty();
            $('#txtImage').val('');
        }
    });

    // Xử lý nút Reset
    $('#resetBtn').click(function () {
        $('#imageUpload').val('');
        $('#fileInfo').text('Chưa chọn file');
        $('#imagePreview').empty();
        $('#txtImage').val('');
        $('#progressContainer').hide();
        $('#progressBar').css('width', '0%');
    });

    // Hiển thị ảnh hiện tại (nếu có) khi tải trang
    const existingImageSrc = $('#txtImage').val();
    if (existingImageSrc && existingImageSrc.startsWith('data:image')) {
        $('#imagePreview').html('<img src="' + existingImageSrc + '" alt="Xem trước">');
    }
});