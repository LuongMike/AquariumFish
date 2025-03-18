package controller;

import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
import dao.FishDAO;
import dto.FishDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "FishController", urlPatterns = {"/FishController"})
public class FishController extends HttpServlet {

    private FishDAO fdao = new FishDAO();
    final int FISH_PER_PAGE = 10; // Số cá mỗi trang
    private static final String MAIN_PAGE = "index.jsp";
    private static final String PRODUCT_PAGE = "product.jsp";
    private static final String LIST_FISH_PAGE = "listFish.jsp";

    
    private String processSeacrhFish(HttpServletRequest request, HttpServletResponse response)
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
        list.sort(Comparator.comparing(FishDTO::getFishType));
        request.setAttribute("fish", list);
        return PRODUCT_PAGE;
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
                url = processSeacrhFish(request, response);
            }
            List<FishDTO> fish = fdao.readAll();
            request.setAttribute("fish", fish);
        } catch (Exception e) {
            log("Error at FishController: " + e.toString());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            if (!url.equals("FishController")) {
                rd.forward(request, response);
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