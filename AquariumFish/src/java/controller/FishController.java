package controller;

import dao.FishDAO;
import dto.FishDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Comparator;

@WebServlet(name = "FishController", urlPatterns = {"/FishController"})
public class FishController extends HttpServlet {

    private FishDAO fdao = new FishDAO();
    private static final String MAIN_PAGE = "mainPage.jsp";

    private String processGetFish(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String searchTerm = request.getParameter("searchTerm");
        System.out.println("Search term received: " + searchTerm); // Debug
        List<FishDTO> list;

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            list = fdao.searchByType(searchTerm);
        } else {
            list = fdao.readAll();
        }
//        if (searchTerm == null) {
//            searchTerm="";
//        }
//        list = fdao.searchByType(searchTerm);
//        request.setAttribute("searchTerm", searchTerm);
        list.sort(Comparator.comparing(FishDTO::getFishType));

        request.setAttribute("fishList", list);
        return "product.jsp";
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = MAIN_PAGE;
        try {
            String action = request.getParameter("action");
            if (action == null) {
                url = MAIN_PAGE;
            } else if (action.equals("viewProducts")) {
                url = processGetFish(request, response);
            }
        } catch (Exception e) {
            log("Error at MainController: " + e.toString());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            if (!url.equals("MainController")) {
                rd.forward(request, response);
            }
        }
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
