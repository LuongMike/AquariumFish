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
    private static final String INVALID_PAGE = "invalidAccountInformation.jsp";
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
            url = INVALID_PAGE;
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
