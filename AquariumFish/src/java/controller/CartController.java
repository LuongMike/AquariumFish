package controller;

import dao.FishDAO;
import dto.FishDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "CartController", urlPatterns = {"/CartController"})
public class CartController extends HttpServlet {
    private static final String CART_PAGE = "/cart.jsp"; // Thêm "/"
    private static final String CHECKOUT_PAGE = "/checkout.jsp"; // Thêm "/"
    private FishDAO fdao = new FishDAO();

    private String processAddToCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fishId = request.getParameter("fishId");
        String url = CART_PAGE;

        HttpSession session = request.getSession();
        List<FishDTO> cart = (List<FishDTO>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        try {
            FishDTO fish = fdao.readbyID(fishId);
            if (fish != null) {
                cart.add(fish);
                session.setAttribute("cart", cart);
                request.setAttribute("message", "Đã thêm " + fish.getFishName() + " vào giỏ hàng!");
            } else {
                request.setAttribute("message", "Không tìm thấy cá với ID: " + fishId);
            }
        } catch (Exception e) {
            request.setAttribute("message", "Lỗi khi thêm vào giỏ hàng: " + e.getMessage());
            log("Error at processAddToCart: " + e.toString());
        }

        return url;
    }
    
    private String processBuyNow(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fishId = request.getParameter("fishId");
        String url = CHECKOUT_PAGE;

        HttpSession session = request.getSession();
        List<FishDTO> cart = (List<FishDTO>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        try {
            FishDTO fish = fdao.readbyID(fishId);
            if (fish != null) {
                cart.add(fish);
                session.setAttribute("cart", cart);
                request.setAttribute("message", "Đã thêm " + fish.getFishName() + " để thanh toán ngay!");
            } else {
                request.setAttribute("message", "Không tìm thấy cá với ID: " + fishId);
                url = CART_PAGE;
            }
        } catch (Exception e) {
            request.setAttribute("message", "Lỗi khi xử lý mua ngay: " + e.getMessage());
            log("Error at processBuyNow: " + e.toString());
            url = CART_PAGE;
        }

        return url;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        String url = CART_PAGE;

        if (action != null) {
            switch (action) {
                case "addToCart":
                    url = processAddToCart(request, response);
                    break;
                case "buyNow":
                    url = processBuyNow(request, response);
                    break;
                default:
                    request.setAttribute("message", "Hành động không hợp lệ!");
                    break;
            }
        } else {
            request.setAttribute("message", "Không có hành động được chỉ định!");
        }

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
