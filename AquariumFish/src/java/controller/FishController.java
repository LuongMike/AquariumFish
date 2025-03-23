package controller;

import dao.FishDAO;
import dao.categoryDAO;
import dto.CategoryDTO;
import dto.FishDTO;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.AuthenUtils;

@WebServlet(name = "FishController", urlPatterns = {"/FishController"})
public class FishController extends HttpServlet {

    private FishDAO fdao = new FishDAO();
    private categoryDAO cdao = new categoryDAO();
    final int FISH_PER_PAGE = 5; // Số cá mỗi trang
    private static final String MAIN_PAGE = "index.jsp";
    private static final String PRODUCT_PAGE = "product.jsp";
    private static final String LIST_FISH_PAGE = "listFish.jsp";

    private String processSearchFish(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // Gán giá trị mặc định là chuỗi rỗng nếu searchTerm là null
        String searchTerm = request.getParameter("searchTerm") != null ? request.getParameter("searchTerm").trim() : "";
        List<FishDTO> list;

        // Lấy số trang hiện tại từ request, mặc định là 1
        String pageStr = request.getParameter("page");
        int page = (pageStr == null || pageStr.isEmpty()) ? 1 : Integer.parseInt(pageStr);

        // Lấy danh sách cá (tất cả hoặc theo tìm kiếm)
        List<FishDTO> fullList;
        if (!searchTerm.isEmpty()) {
            fullList = fdao.searchByType(searchTerm); // Tìm kiếm theo loại nếu có từ khóa
        } else {
            fullList = fdao.readAll(); // Hiển thị tất cả nếu không có từ khóa
        }
        fullList.sort(Comparator.comparing(FishDTO::getFishType)); // Sắp xếp theo fishType

        // Tính tổng số trang
        int totalFish = fullList.size();
        int totalPages = (int) Math.ceil((double) totalFish / FISH_PER_PAGE);

        // Giới hạn page trong khoảng hợp lệ
        page = Math.max(1, Math.min(page, totalPages));

        // Tính chỉ số bắt đầu và kết thúc của danh sách phân trang
        int start = (page - 1) * FISH_PER_PAGE;
        int end = Math.min(start + FISH_PER_PAGE, totalFish);

        // Lấy danh sách cá cho trang hiện tại
        if (!fullList.isEmpty()) {
            list = fullList.subList(start, end);
        } else {
            list = fullList;
        }

        // Đặt các thuộc tính vào request
        request.setAttribute("fish", list);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalFish", totalFish);
        return PRODUCT_PAGE;
    }

//    private String processListFish(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        String url = MAIN_PAGE;
//        try {
//            // Lấy số trang hiện tại từ request, mặc định là 1
//            String pageStr = request.getParameter("page");
//            int page = (pageStr == null || pageStr.isEmpty()) ? 1 : Integer.parseInt(pageStr);
//
//            // Lấy danh sách tất cả cá
//            List<FishDTO> fullList = fdao.readAll();
//            //fullList.sort(Comparator.comparing(FishDTO::getFishType));
//            List<CategoryDTO> categoryList = cdao.readAll();
//            // Tính tổng số trang
//            int totalFish = fullList.size();
//            int totalPages = (int) Math.ceil((double) totalFish / FISH_PER_PAGE);
//
//            // Giới hạn page trong khoảng hợp lệ
//            page = Math.max(1, Math.min(page, totalPages));
//
//            // Tính chỉ số bắt đầu và kết thúc của danh sách phân trang
//            int start = (page - 1) * FISH_PER_PAGE;
//            int end = Math.min(start + FISH_PER_PAGE, totalFish);
//
//            // Lấy danh sách cá cho trang hiện tại
//            List<FishDTO> paginatedList = fullList.subList(start, end);
//
//            // Đặt các thuộc tính vào request
//            request.setAttribute("category", categoryList);
//            request.setAttribute("fish", paginatedList);
//            request.setAttribute("currentPage", page);
//            request.setAttribute("totalPages", totalPages);
//            request.setAttribute("totalFish", totalFish);
//        } catch (Exception e) {
//            log("Error at processListFish: " + e.toString());
//        }
//        return url;
//    }
    private String processListFish(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = MAIN_PAGE;
        try {
            String pageStr = request.getParameter("page");
            int page = (pageStr == null || pageStr.isEmpty()) ? 1 : Integer.parseInt(pageStr);

            // Lấy danh sách danh mục
            List<CategoryDTO> categoryList = cdao.readAll();
            request.setAttribute("categories", categoryList);

            // Lấy danh sách cá theo từng danh mục (cho phần hiển thị trên cùng)
            for (CategoryDTO category : categoryList) {
                List<FishDTO> fishList = fdao.getFishByCategory(Integer.parseInt(category.getCategoryID()));
                request.setAttribute("fishList_" + category.getCategoryID(), fishList);
            }

            // Lấy danh sách cá cho bảng (có phân trang và lọc theo danh mục)
            String categoryFilter = request.getParameter("categoryFilter");
            List<FishDTO> fullList;
            int totalFish;

            if (categoryFilter != null && !categoryFilter.isEmpty()) {
                int categoryID = Integer.parseInt(categoryFilter);
                fullList = fdao.getFishByCategory(categoryID);
                totalFish = fullList.size();
            } else {
                fullList = fdao.readAll();
                totalFish = fullList.size();
            }

            fullList.sort(Comparator.comparing(FishDTO::getFishType));

            int totalPages = (int) Math.ceil((double) totalFish / FISH_PER_PAGE);

            page = Math.max(1, Math.min(page, totalPages));

            int start = (page - 1) * FISH_PER_PAGE;
            int end = Math.min(start + FISH_PER_PAGE, totalFish);

            List<FishDTO> paginatedList = fullList.subList(start, end);

            request.setAttribute("fish", paginatedList);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalFish", totalFish);
            request.setAttribute("categoryFilter", categoryFilter);
        } catch (Exception e) {
            log("Error at processListFish: " + e.toString());
        }
        return url;
    }

    private String processDeleteFish(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "product.jsp";
        if (AuthenUtils.isAdmin(request.getSession())) {
            url = "product.jsp";
            String id = request.getParameter("id");
            fdao.updateQuantityToZero(id);
        } else {
            response.getWriter().print("<h1>303 Error, ... </h1>");
        }
        return url;
    }

    private String processEditFish(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "update.jsp";
        if (AuthenUtils.isAdmin(request.getSession())) { // Sửa điều kiện cho phù hợp với processUpdateFish
            String id = request.getParameter("id");
            try {
                FishDTO fish = fdao.readbyID(id); // Đảm bảo id là chuỗi số hợp lệ
                if (fish != null) {
                    request.setAttribute("fish", fish);
                } else {
                    request.setAttribute("message", "Không tìm thấy cá với ID: " + id);
                }
            } catch (Exception e) {
                request.setAttribute("message", "Lỗi khi lấy dữ liệu cá: " + e.getMessage());
                log("Error at processEditFish: " + e.toString());
            }
        } else {
            response.getWriter().print("<h1>303 Error, ... </h1>");
        }
        return url;
    }

    private String processUpdateFish(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "update.jsp";

        if (AuthenUtils.isAdmin(request.getSession())) {
            try {
                // Lấy dữ liệu từ form của update.jsp
                String fishId = request.getParameter("fishId");
                String fishType = request.getParameter("fishType");
                String fishName = request.getParameter("fishName");
                String fishPriceStr = request.getParameter("fishPrice");
                String fishQuantityStr = request.getParameter("fishQuantity");
                String fishDescription = request.getParameter("fishDescription");
                String base64Image = request.getParameter("txtImage");
                String categoryIDStr = request.getParameter("categoryID");

                // Kiểm tra fishId hợp lệ trước khi xử lý
                int fishIdInt;
                try {
                    fishIdInt = Integer.parseInt(fishId);
                } catch (NumberFormatException e) {
                    request.setAttribute("message", "ID cá không hợp lệ: " + fishId);
                    return url;
                }

                // Chuyển đổi dữ liệu
                double fishPrice = (fishPriceStr != null && !fishPriceStr.isEmpty()) ? Double.parseDouble(fishPriceStr) : -1;
                int fishQuantity = (fishQuantityStr != null && !fishQuantityStr.isEmpty()) ? Integer.parseInt(fishQuantityStr) : -1;
                int categoryID = (categoryIDStr != null && !categoryIDStr.isEmpty()) ? Integer.parseInt(categoryIDStr) : -1;

                // Giữ ảnh cũ nếu không có ảnh mới
                FishDTO existingFish = fdao.readbyID(fishId);
                if (existingFish == null) {
                    request.setAttribute("message", "Không tìm thấy cá với ID: " + fishId);
                    return url;
                }
                String fishImg = (base64Image != null && !base64Image.trim().isEmpty()) ? base64Image : existingFish.getFishImg();

                // Kiểm tra lỗi đầu vào
                boolean checkError = false;
                if (fishType == null || fishType.trim().isEmpty()) {
                    request.setAttribute("fishType_error", "Loại cá không được để trống.");
                    fishType = "";
                    checkError = true;
                }
                if (fishName == null || fishName.trim().isEmpty()) {
                    request.setAttribute("fishName_error", "Tên cá không được để trống.");
                    fishName = "";
                    checkError = true;
                }
                if (fishPrice <= 0) {
                    request.setAttribute("fishPrice_error", "Giá phải lớn hơn 0.");
                    checkError = true;
                }
                if (fishQuantity < 0) {
                    request.setAttribute("fishQuantity_error", "Số lượng phải lớn hơn hoặc bằng 0.");
                    checkError = true;
                }
                if (fishDescription == null || fishDescription.trim().isEmpty()) {
                    request.setAttribute("fishDescription_error", "Mô tả không được để trống.");
                    fishDescription = "";
                    checkError = true;
                }
                if (categoryID != 1 && categoryID != 2) {
                    request.setAttribute("categoryID_error", "Vui lòng chọn danh mục 1 hoặc 2.");
                    checkError = true;
                }

                // Tạo FishDTO với dữ liệu đã lấy
                FishDTO fish = new FishDTO(
                        fishIdInt,
                        fishType,
                        fishName,
                        fishPrice,
                        fishQuantity,
                        fishDescription,
                        fishImg,
                        categoryID
                );

                // Nếu không có lỗi, cập nhật vào database
                if (!checkError) {
                    boolean result = fdao.update(fish);
                    if (result) {
                        url = MAIN_PAGE;
                        request.setAttribute("message", "Cập nhật cá thành công!");
                    } else {
                        request.setAttribute("message", "Cập nhật cá thất bại!");
                        request.setAttribute("fish", fish);
                    }
                } else {
                    request.setAttribute("fish", fish);
                }

            } catch (NumberFormatException e) {
                request.setAttribute("message", "Dữ liệu số không hợp lệ: " + e.getMessage());
                log("Error at processUpdateFish - NumberFormatException: " + e.toString());
                String fishId = request.getParameter("fishId");
                try {
                    FishDTO fish = fdao.readbyID(fishId);
                    if (fish != null) {
                        request.setAttribute("fish", fish);
                    }
                } catch (Exception ex) {
                    log("Error retrieving fish after NumberFormatException: " + ex.toString());
                }
            } catch (Exception e) {
                request.setAttribute("message", "Đã xảy ra lỗi: " + e.getMessage());
                log("Error at processUpdateFish: " + e.toString());
                String fishId = request.getParameter("fishId");
                try {
                    FishDTO fish = fdao.readbyID(fishId);
                    if (fish != null) {
                        request.setAttribute("fish", fish);
                    }
                } catch (Exception ex) {
                    log("Error retrieving fish after Exception: " + ex.toString());
                }
            }
        } else {
            response.getWriter().print("<h1>303 Error, ... </h1>");
        }
        return url;
    }

    private String processAddFish(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "add.jsp";

        if (AuthenUtils.isAdmin(request.getSession())) {
            try {
                String fishType = request.getParameter("fishType");
                String fishName = request.getParameter("fishName");
                String fishPriceStr = request.getParameter("fishPrice");
                String fishQuantityStr = request.getParameter("fishQuantity");
                String fishDescription = request.getParameter("fishDescription");
                String base64Image = request.getParameter("txtImage"); // Base64 từ add.jsp
                String categoryIDStr = request.getParameter("categoryID");

                // Kiểm tra và chuyển đổi dữ liệu
                double fishPrice = (fishPriceStr != null && !fishPriceStr.isEmpty()) ? Double.parseDouble(fishPriceStr) : -1;
                int fishQuantity = (fishQuantityStr != null && !fishQuantityStr.isEmpty()) ? Integer.parseInt(fishQuantityStr) : -1;
                int categoryID = (categoryIDStr != null && !categoryIDStr.isEmpty()) ? Integer.parseInt(categoryIDStr) : -1;

                // Kiểm tra lỗi
                boolean checkError = false;
                if (fishType == null || fishType.trim().isEmpty()) {
                    request.setAttribute("fishType_error", "Loại cá không được để trống.");
                    checkError = true;
                }
                if (fishName == null || fishName.trim().isEmpty()) {
                    request.setAttribute("fishName_error", "Tên cá không được để trống.");
                    checkError = true;
                }
                if (fishPrice <= 0) {
                    request.setAttribute("fishPrice_error", "Giá phải lớn hơn 0.");
                    checkError = true;
                }
                if (fishQuantity <= 0) {
                    request.setAttribute("fishQuantity_error", "Số lượng phải lớn hơn 0.");
                    checkError = true;
                }
                if (fishDescription == null || fishDescription.trim().isEmpty()) {
                    request.setAttribute("fishDescription_error", "Mô tả không được để trống.");
                    checkError = true;
                }
                if (categoryID != 1 && categoryID != 2) {
                    request.setAttribute("categoryID_error", "Vui lòng chọn danh mục 1 hoặc 2.");
                    checkError = true;
                }

                // Nếu không có lỗi, tạo FishDTO và thêm vào database
                if (!checkError) {
                    FishDTO fish = new FishDTO(0, fishType, fishName, fishPrice, fishQuantity, fishDescription, base64Image, categoryID);
                    boolean result = fdao.create(fish);
                    if (result) {
                        url = MAIN_PAGE;
                        request.setAttribute("message", "Thêm cá thành công!");
                    } else {
                        request.setAttribute("message", "Thêm cá thất bại!");
                    }
                } else {
                    // Giữ lại dữ liệu khi có lỗi
                    request.setAttribute("fishType", fishType);
                    request.setAttribute("fishName", fishName);
                    request.setAttribute("fishPrice", fishPriceStr);
                    request.setAttribute("fishQuantity", fishQuantityStr);
                    request.setAttribute("fishDescription", fishDescription);
                    request.setAttribute("fishImg", base64Image);
                    request.setAttribute("categoryID", categoryIDStr);
                }

                // Gửi lại danh sách danh mục (nếu cần thiết cho các trang khác)
                List<CategoryDTO> categories = cdao.readAll();
                request.setAttribute("categories", categories);

            } catch (NumberFormatException e) {
                request.setAttribute("message", "Dữ liệu số không hợp lệ: " + e.getMessage());
                log("Error at processAddFish - NumberFormatException: " + e.toString());
            } catch (Exception e) {
                request.setAttribute("message", "Đã xảy ra lỗi: " + e.getMessage());
                log("Error at processAddFish: " + e.toString());
            }
        } else {
            response.getWriter().print("<h1>303 Error, ... </h1>");
        }
        return url;
    }

    private String processIndex(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = MAIN_PAGE;
        try {
            // Lấy danh sách danh mục
            List<CategoryDTO> categories = cdao.readAll();
            request.setAttribute("categories", categories);

            // Lấy danh sách cá theo từng danh mục
            for (CategoryDTO category : categories) {
                List<FishDTO> fishList = fdao.getFishByCategory(Integer.parseInt(category.getCategoryID()));
                request.setAttribute("fishList_" + category.getCategoryID(), fishList);
            }
        } catch (Exception e) {
            log("Error at processIndex: " + e.toString());
        }
        return url;
    }

    private String processDetailsFish(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "details.jsp";
        String id = request.getParameter("id");
        try {
            FishDTO fish = fdao.readbyID(id);
            if (fish != null) {
                request.setAttribute("fish", fish);
            } else {
                request.setAttribute("message", "Không tìm thấy cá với ID: " + id);
            }
        } catch (Exception e) {
            request.setAttribute("message", "Lỗi khi lấy thông tin cá: " + e.getMessage());
            log("Error at processDetailsFish: " + e.toString());
        }
        return url;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = MAIN_PAGE;
        try {
            String action = request.getParameter("action");
            if (action == null) {
                url = processIndex(request, response);
                url = processListFish(request, response);
            } else if (action.equals("viewProducts")) {
                url = processSearchFish(request, response);
            } else if (action.equals("pagination")) {
                url = processListFish(request, response);
            } else if (action.equals("delete")) {
                url = processDeleteFish(request, response);
            } else if (action.equals("edit")) {
                url = processEditFish(request, response);
            } else if (action.equals("update")) {
                url = processUpdateFish(request, response);
            } else if (action.equals("add")) {
                url = processAddFish(request, response);
            } else if (action.equals("details")) {
                url = processDetailsFish(request, response);
            }
        } catch (Exception e) {
            log("Error at FishController: " + e.toString());
        } finally {
            if (!response.isCommitted()) {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                if (!url.equals("FishController")) {
                    rd.forward(request, response);
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Fish Controller Servlet";
    }
}