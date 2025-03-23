package controller;

import dao.UserDAO;
import dto.UserDTO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.EmailUtils;
import utils.PasswordUtils;

@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RegisterController.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");

        try {
            if ("register".equals(action)) {
                registerUser(request, response);
                return; // Dừng xử lý sau redirect
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error at RegisterController: {0}", e.toString());
            request.getSession().setAttribute("registerMessage", "Đã xảy ra lỗi: " + e.getMessage());
            response.sendRedirect("register.jsp");
        }
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Lấy thông tin từ form
            String userName = request.getParameter("userName");
            String account = request.getParameter("account");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmpassword");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");

            // Khởi tạo biến kiểm tra lỗi
            boolean hasError = false;

            // Validate userName
            if (userName == null || userName.trim().length() < 3) {
                request.setAttribute("userName_error", "Tên người dùng phải có ít nhất 3 ký tự");
                hasError = true;
            }

            // Validate account
            if (account == null || account.trim().length() < 3) {
                request.setAttribute("account_error", "Tài khoản phải có ít nhất 3 ký tự");
                hasError = true;
            }

            // Validate email
            if (email == null || !isValidEmail(email)) {
                request.setAttribute("email_error", "Vui lòng nhập email hợp lệ");
                hasError = true;
            }

            // Validate password
            if (password == null || password.length() < 6) {
                request.setAttribute("password_error", "Mật khẩu phải có ít nhất 6 ký tự");
                hasError = true;
            }

            // Validate confirmPassword
            if (!password.equals(confirmPassword)) {
                request.setAttribute("confirmpassword_error", "Mật khẩu xác nhận không khớp");
                hasError = true;
            }

            // Validate phone (nếu có)
            if (phone != null && !phone.trim().isEmpty() && !phone.matches("\\d{10,11}")) {
                request.setAttribute("phone_error", "Số điện thoại phải có 10-11 chữ số");
                hasError = true;
            }

            // Nếu có lỗi, lưu lại các giá trị đã nhập và quay lại trang đăng ký
            if (hasError) {
                request.setAttribute("userName", userName);
                request.setAttribute("account", account);
                request.setAttribute("email", email);
                request.setAttribute("phone", phone);
                request.setAttribute("address", address);
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // Kiểm tra xem tài khoản đã tồn tại chưa
            UserDAO userDAO = new UserDAO();
            UserDTO existingUser = userDAO.readbyAccount(account);
            if (existingUser != null) {
                request.setAttribute("account_error", "Tài khoản đã tồn tại. Vui lòng chọn tài khoản khác.");
                request.setAttribute("userName", userName);
                request.setAttribute("email", email);
                request.setAttribute("phone", phone);
                request.setAttribute("address", address);
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // Kiểm tra xem email đã tồn tại chưa
            UserDTO existingEmail = userDAO.findByEmail(email);
            if (existingEmail != null) {
                request.setAttribute("email_error", "Email đã được sử dụng. Vui lòng dùng email khác.");
                request.setAttribute("userName", userName);
                request.setAttribute("account", account);
                request.setAttribute("phone", phone);
                request.setAttribute("address", address);
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // Tạo người dùng mới
            UserDTO newUser = new UserDTO();
            newUser.setUserName(userName);
            newUser.setAccount(account);
            newUser.setEmail(email);
            newUser.setPassword(PasswordUtils.hashPassword(password)); // Mã hóa mật khẩu
            newUser.setPhone(phone);
            newUser.setAddress(address);
            newUser.setRole("customer"); // Mặc định là role USER
            newUser.setBalance(0.0); // Số dư mặc định là 0

            // Lưu người dùng vào database
            boolean result = userDAO.create(newUser);

            if (result) {
                // Đăng ký thành công, gửi email chúc mừng
                boolean emailSent = EmailUtils.sendRegistrationEmail(email, userName, account);
                if (emailSent) {
                    LOGGER.log(Level.INFO, "Congratulations email sent to {0}", email);
                    request.getSession().setAttribute("registerMessage", "Đăng ký thành công! Email chào mừng đã được gửi đến bạn. Bạn có thể đăng nhập ngay.");
                } else {
                    LOGGER.log(Level.WARNING, "Failed to send congratulations email to {0}", email);
                    request.getSession().setAttribute("registerMessage", "Đăng ký thành công! Bạn có thể đăng nhập ngay. (Lưu ý: Không thể gửi email chào mừng)");
                }
            } else {
                request.getSession().setAttribute("registerMessage", "Đăng ký thất bại. Vui lòng thử lại.");
            }

            // Redirect để áp dụng PRG pattern
            response.sendRedirect("register.jsp");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error at registerUser: {0}", e.toString());
            request.getSession().setAttribute("registerMessage", "Đã xảy ra lỗi khi đăng ký: " + e.getMessage());
            response.sendRedirect("register.jsp");
        }
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
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