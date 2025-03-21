package controller;

import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {

    private static final String REGISTER_PAGE = "/register.jsp";
    private UserDAO udao = new UserDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = REGISTER_PAGE;

        try {
            String userName = request.getParameter("userName");
            String account = request.getParameter("account");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");

            // Lưu dữ liệu đã nhập để hiển thị lại nếu có lỗi
            request.setAttribute("userName", userName);
            request.setAttribute("account", account);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.setAttribute("address", address);

            // Kiểm tra dữ liệu đầu vào
            if (userName == null || userName.trim().isEmpty() ||
                account == null || account.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                email == null || email.trim().isEmpty()) {
                request.setAttribute("registerMessage", "Vui lòng điền đầy đủ các trường bắt buộc!");
                return;
            }

            // Kiểm tra xem account hoặc email đã tồn tại chưa
            if (udao.isAccountOrEmailExists(account, email)) {
                request.setAttribute("registerMessage", "Tài khoản hoặc email đã tồn tại!");
                return;
            }

            // Đăng ký người dùng mới
            boolean registered = udao.registerUser(userName, account, password, email, phone, address);
            if (registered) {
                request.setAttribute("registerMessage", "Đăng ký thành công! Vui lòng đăng nhập.");
                url = REGISTER_PAGE; // Chuyển hướng về login.jsp sau khi đăng ký thành công
            } else {
                request.setAttribute("registerMessage", "Đăng ký thất bại! Vui lòng thử lại.");
            }
        } catch (Exception e) {
            request.setAttribute("registerMessage", "Lỗi khi đăng ký: " + e.getMessage());
            log("Error at RegisterController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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