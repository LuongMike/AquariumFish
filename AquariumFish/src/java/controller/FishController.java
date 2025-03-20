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
        if (!AuthenUtils.isAdmin(request.getSession())) {
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
        String url = MAIN_PAGE;
        if (!AuthenUtils.isAdmin(request.getSession())) {
            url = "update.jsp";
            String id = request.getParameter("id");
            FishDTO fish = fdao.readbyID(id);
            request.setAttribute("fish", fish);
        } else {
            response.getWriter().print("<h1>303 Error, ... </h1>");
        }

        return url;
    }

    private String processUpdateFish(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "update.jsp"; // Giữ lại trên trang update.jsp nếu có lỗi

        if (!AuthenUtils.isAdmin(request.getSession())) {

            try {
                String fishId = request.getParameter("fishId");
                String fishType = request.getParameter("fishType");
                String fishName = request.getParameter("fishName");
                double fishPrice = Double.parseDouble(request.getParameter("fishPrice"));
                int fishQuantity = Integer.parseInt(request.getParameter("fishQuantity"));
                String fishDescription = request.getParameter("fishDescription");
                String fishImg = request.getParameter("fishImg");
                int categoryID = Integer.parseInt(request.getParameter("categoryID"));

                // Khởi tạo các thông báo lỗi
                boolean checkError = false;
                if (fishType == null || fishType.trim().isEmpty()) {
                    request.setAttribute("fishType_error", "Fish Type cannot be empty.");
                    fishType = ""; // Gán giá trị rỗng để giữ form
                    checkError = true;
                }
                if (fishName == null || fishName.trim().isEmpty()) {
                    request.setAttribute("fishName_error", "Fish Name cannot be empty.");
                    fishName = ""; // Gán giá trị rỗng
                    checkError = true;
                }
                if (fishPrice <= 0) {
                    request.setAttribute("fishPrice_error", "Fish Price must be greater than 0.");
                    checkError = true;
                }
                if (fishQuantity <= 0) {
                    request.setAttribute("fishQuantity_error", "Fish Quantity must be greater than 0.");
                    checkError = true;
                }
                if (fishDescription == null || fishDescription.trim().isEmpty()) {
                    request.setAttribute("fishDescription_error", "Fish Description cannot be empty.");
                    fishDescription = ""; // Sửa lỗi gán fishName thành fishDescription
                    checkError = true;
                }

                FishDTO fish = new FishDTO(
                        Integer.parseInt(fishId), fishType, fishName, fishPrice, fishQuantity,
                        fishDescription, fishImg, categoryID // categoryName có thể null nếu không lấy từ DB
                );

                if (!checkError) {
                    boolean result = fdao.update(fish); // Gọi phương thức updateFish
                    if (result) {
                        url = "update.jsp";
                        request.setAttribute("message", "Product updated successfully!");
                    } else {
                        request.setAttribute("message", "Failed to update product!");
                        request.setAttribute("fish", fish); // Giữ dữ liệu để hiển thị lại
                    }
                } else {
                    request.setAttribute("fish", fish); // Giữ dữ liệu để hiển thị lại form
                }

            } catch (NumberFormatException e) {
                request.setAttribute("message", "Invalid numeric input: " + e.getMessage());
                log("Error at processUpdateFish - NumberFormatException: " + e.toString());
            } catch (Exception e) {
                request.setAttribute("message", "An error occurred: " + e.getMessage());
                log("Error at processUpdateFish: " + e.toString());
            }
        } else {
            response.getWriter().print("<h1>303 Error, ... </h1>");
        }
        return url;
    }

    private String processAddFish(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "add.jsp"; // Luôn giữ trên add.jsp
        if (!AuthenUtils.isAdmin(request.getSession())) {

            try {
                String fishType = request.getParameter("fishType");
                String fishName = request.getParameter("fishName");
                double fishPrice = Double.parseDouble(request.getParameter("fishPrice"));
                int fishQuantity = Integer.parseInt(request.getParameter("fishQuantity"));
                String fishDescription = request.getParameter("fishDescription");
                String fishImg = request.getParameter("fishImg");
                int categoryID = Integer.parseInt(request.getParameter("categoryID"));

                // Khởi tạo các thông báo lỗi
                boolean checkError = false;
                if (fishType == null || fishType.trim().isEmpty()) {
                    request.setAttribute("fishType_error", "Fish Type cannot be empty.");
                    checkError = true;
                }
                if (fishName == null || fishName.trim().isEmpty()) {
                    request.setAttribute("fishName_error", "Fish Name cannot be empty.");
                    checkError = true;
                }
                if (fishPrice <= 0) {
                    request.setAttribute("fishPrice_error", "Fish Price must be greater than 0.");
                    checkError = true;
                }
                if (fishQuantity <= 0) {
                    request.setAttribute("fishQuantity_error", "Fish Quantity must be greater than 0.");
                    checkError = true;
                }
                if (fishDescription == null || fishDescription.trim().isEmpty()) {
                    request.setAttribute("fishDescription_error", "Fish Description cannot be empty.");
                    checkError = true;
                }

                if (!checkError) {
                    FishDTO fish = new FishDTO(0, fishType, fishName, fishPrice, fishQuantity, fishDescription, fishImg, categoryID);
                    boolean result = fdao.create(fish); // Gọi phương thức tạo mới
                    if (result) {
                        request.setAttribute("message", "Product added successfully!");
                        // Không cần gán lại fish vì đây là thêm mới, form sẽ được reset
                        request.setAttribute("fishType", ""); // Reset form
                        request.setAttribute("fishName", "");
                        request.setAttribute("fishPrice", "");
                        request.setAttribute("fishQuantity", "");
                        request.setAttribute("fishDescription", "");
                        request.setAttribute("fishImg", "");
                        request.setAttribute("categoryID", "");
                    } else {
                        request.setAttribute("message", "Failed to add product!");
                    }
                } else {
                    // Giữ dữ liệu đã nhập để hiển thị lại
                    request.setAttribute("fishType", fishType);
                    request.setAttribute("fishName", fishName);
                    request.setAttribute("fishPrice", String.valueOf(fishPrice));
                    request.setAttribute("fishQuantity", String.valueOf(fishQuantity));
                    request.setAttribute("fishDescription", fishDescription);
                    request.setAttribute("fishImg", fishImg);
                    request.setAttribute("categoryID", String.valueOf(categoryID));
                }
            } catch (NumberFormatException e) {
                request.setAttribute("message", "Invalid numeric input: " + e.getMessage());
                log("Error at processAddFish - NumberFormatException: " + e.toString());
            } catch (Exception e) {
                request.setAttribute("message", "An error occurred: " + e.getMessage());
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
