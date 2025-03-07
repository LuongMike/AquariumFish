package controller;

import dao.UserDAO;
import dto.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.AuthenUtils;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    private UserDAO udao = new UserDAO();

    private static final String LOGIN_PAGE = "login.jsp";
    private static final String SUCCESS_PAGE = "loginSuccessful.jsp";
    private static final String MAIN_PAGE = "mainPage.jsp";

    private String processLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = LOGIN_PAGE;
        // Xử lý đăng nhập
        String account = request.getParameter("userAccount");
        String password = request.getParameter("userPassword");

        if (AuthenUtils.isValidLogin(account, password)) {
            UserDTO user = AuthenUtils.getUser(account);
            request.getSession().setAttribute("username", user.getUserName());
            request.getSession().setAttribute("user", user);
            url = SUCCESS_PAGE;
        } else {
            request.setAttribute("loginError", "Tài khoản hoặc mật khẩu không đúng. Vui lòng thử lại!");
        }
        return url;
    }

    private String processLogout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = LOGIN_PAGE;
        // Xử lý đăng xuất
        request.getSession().invalidate(); // Hủy session
        url = MAIN_PAGE; // Chuyển về trang chủ
        return url;
    }
//                private String processLogin(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String url = LOGIN_PAGE;
//
//        return url;
//    }

    private String processUpdateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "infor.jsp"; // Trang thông tin cá nhân
        try {
            UserDTO user = (UserDTO) request.getSession().getAttribute("user");
            if (user != null) {
                String userName = request.getParameter("userName");
                String email = request.getParameter("userEmail");
                String phone = request.getParameter("userPhone");
                String address = request.getParameter("userAddress");
                String role = request.getParameter("userRole");

                // Kiểm tra dữ liệu đầu vào
                if (!AuthenUtils.isValidUserName(userName)) {
                    request.setAttribute("errorMessage", "Tên người dùng không hợp lệ!");
                    return url;
                }
                if (!AuthenUtils.isValidEmail(email)) {
                    request.setAttribute("errorMessage", "Email không hợp lệ!");
                    return url;
                }
                if (!AuthenUtils.isValidPhone(phone)) {
                    request.setAttribute("errorMessage", "Số điện thoại không hợp lệ!");
                    return url;
                }
                if (!AuthenUtils.isValidAddress(address)) {
                    request.setAttribute("errorMessage", "Địa chỉ không hợp lệ!");
                    return url;
                }
                if (!AuthenUtils.isValidRole(role)) {
                    request.setAttribute("errorMessage", "Vai trò không hợp lệ!");
                    return url;
                }

                // Nếu hợp lệ, cập nhật thông tin mới
                user.setUserName(userName);
                user.setEmail(email);
                user.setPhone(phone);
                user.setAddress(address);
                user.setRole(role);

                boolean updateSuccess = udao.update(user);

                if (updateSuccess) {
                    request.getSession().setAttribute("user", user);
                    request.setAttribute("successMessage", "Cập nhật thành công!");
                } else {
                    request.setAttribute("errorMessage", "Cập nhật thất bại, vui lòng thử lại!");
                }
            } else {
                request.setAttribute("errorMessage", "Không tìm thấy thông tin người dùng!");
            }
        } catch (Exception e) {
            log("Error at processUpdateUser: " + e.toString());
            request.setAttribute("errorMessage", "Đã xảy ra lỗi, vui lòng thử lại sau!");
        }
        return url;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LOGIN_PAGE;
        try {
            String action = request.getParameter("action");

            if (action == null) {
                url = LOGIN_PAGE;
            } else if (action.equals("login")) {
                url = processLogin(request, response);
            } else if (action.equals("logout")) {
                url = processLogout(request, response);
            } else if (action.equals("update")) {
                url = processUpdateUser(request, response);
            }
        } catch (Exception e) {
            log("Error at MainController: " + e.toString());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
}
