package controller;

import dao.UserDAO;
import dto.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
//public class MainController extends HttpServlet {
//
//    private static final String LOGIN_PAGE = "login.jsp";
//    
//
//    public UserDTO getUser(String userId) {
//        UserDAO udao = new UserDAO();
//        UserDTO user = udao.readbyID(userId);
//        return user;
//    }
//
//    public boolean isValidLogin(String userId, String password) {
//        UserDTO user = getUser(userId);
//        if (user != null && user.getPassword().equals(password)) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        String url = LOGIN_PAGE;
//        try {
//            String action = request.getParameter("action");
//            System.out.println(action);
//            if (action == null) {
//                url = LOGIN_PAGE;
//            }
//
//            if (action != null && action.equals("login")) {
//                //login action
//                String strUserId = request.getParameter("userAccount");
//                String strPassword = request.getParameter("userPassword");
//                if (isValidLogin(strUserId, strPassword)) {
//                    url = "loginSuccessful.jsp";
//                    UserDTO user = getUser(strUserId);
//                    request.setAttribute("user", user);
//                } else {
//                    url = "invalidAccountInformation.jsp";
//                }
//            } else if (action != null && action.equals("logout")) {
//                url = "MainController";
//                PrintWriter OUT = response.getWriter();
//                request.setAttribute("user", null);
//                System.out.println("You Are Logged Out!");
//                out.println("<b>You are logged out!</b><br/>");
//                out.println("<a href='MainController'>Back to login page!</b>");
//            }
//        } catch (Exception e) {
//            log("Error at MainController: " + e.toString());
//        } finally {
//            RequestDispatcher rd = request.getRequestDispatcher(url);
//            if (!url.equals("MainController")) {
//                rd.forward(request, response);
//            }
//        }
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    private static final String LOGIN_PAGE = "login.jsp";
    private static final String SUCCESS_PAGE = "loginSuccessful.jsp";
    private static final String INVALID_PAGE = "invalidAccountInformation.jsp";
    private static final String MAIN_PAGE = "mainPage.jsp";

    public UserDTO getUser(String userId) {
        UserDAO udao = new UserDAO();
        return udao.readbyID(userId);
    }

    public boolean isValidLogin(String userId, String password) {
        UserDTO user = getUser(userId);
        return user != null && user.getPassword().equals(password);
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
                // Xử lý đăng nhập
                String userId = request.getParameter("userAccount");
                String password = request.getParameter("userPassword");

                if (isValidLogin(userId, password)) {
                    UserDTO user = getUser(userId);
                    request.getSession().setAttribute("username", user.getFullName()); // Lưu vào session
                    url = SUCCESS_PAGE;
                } else {
                    url = INVALID_PAGE;
                }
            } else if (action.equals("logout")) {
                // Xử lý đăng xuất
                request.getSession().invalidate(); // Hủy session
                url = MAIN_PAGE; // Chuyển về trang chủ
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

