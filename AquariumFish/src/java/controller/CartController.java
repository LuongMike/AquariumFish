package controller;

import dto.FishDTO;
import dto.OrderDTO;
import dao.FishDAO;
import dao.InvoiceDAO;
import dao.OrderDAO;
import dao.OrderDetailDAO;
import dao.PaymentDAO;
import dao.UserDAO;
import dto.InvoiceDTO;
import dto.OrderDetailDTO;
import dto.UserDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CartController")
public class CartController extends HttpServlet {

    private static final String INVOICE_PAGE = "/invoice.jsp";
    private static final String CART_PAGE = "/cart.jsp";
    private static final String CHECKOUT_PAGE = "/checkout.jsp";
    private UserDAO udao = new UserDAO();
    private FishDAO fdao = new FishDAO();
    private OrderDAO odao = new OrderDAO();
    private OrderDetailDAO oddao = new OrderDetailDAO();
    private InvoiceDAO idao = new InvoiceDAO();
    private PaymentDAO pdao = new PaymentDAO(); // Thêm PaymentDAO

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String url = CART_PAGE;

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId"); // Giả định userId lưu trong session khi đăng nhập

        if (userId == null) {
            request.setAttribute("message", "Vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng!");
            url = "/login.jsp"; // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
        } else if (action != null) {
            switch (action) {
                case "add":
                    url = processAddToCart(request, response, userId);
                    break;
                case "buyNow":
                    url = processBuyNow(request, response, userId);
                    break;
                case "remove":
                    url = processRemoveFromCart(request, response, userId);
                    break;
                case "checkout":
                    url = processCheckout(request, response, userId);
                    break;
                case "pay":
                    url = processPayment(request, response, userId);
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

    private String processAddToCart(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException {
        String fishId = request.getParameter("fishId");
        String url = CART_PAGE;

        try {
            FishDTO fish = fdao.readbyID(fishId);
            if (fish != null) {
                // Kiểm tra hoặc tạo đơn hàng pending
                OrderDTO order = odao.getPendingOrderByUserId(userId);
                int orderId;
                if (order == null) {
                    orderId = odao.createPendingOrder(userId);
                } else {
                    orderId = order.getOrderID();
                }

                if (orderId != -1) {
                    // Thêm chi tiết đơn hàng
                    boolean added = oddao.addOrderDetail(orderId, fish.getFishID(), 1, fish.getFishPrice());
                    if (added) {
                        // Cập nhật tổng giá
                        double totalPrice = calculateTotalPrice(orderId);
                        odao.updateTotalPrice(orderId, totalPrice);
                        request.setAttribute("message", "Đã thêm " + fish.getFishName() + " vào giỏ hàng!");
                    } else {
                        request.setAttribute("message", "Không thể thêm sản phẩm vào giỏ hàng!");
                    }
                } else {
                    request.setAttribute("message", "Không thể tạo đơn hàng!");
                }
            } else {
                request.setAttribute("message", "Không tìm thấy cá với ID: " + fishId);
            }
        } catch (Exception e) {
            request.setAttribute("message", "Lỗi khi thêm vào giỏ hàng: " + e.getMessage());
            log("Error at processAddToCart: " + e.toString());
        }

        return url;
    }

    private String processBuyNow(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException {
        String fishId = request.getParameter("fishId");
        String url = INVOICE_PAGE;

        try {
            FishDTO fish = fdao.readbyID(fishId);
            if (fish != null) {
                OrderDTO order = odao.getPendingOrderByUserId(userId);
                int orderId;
                if (order == null) {
                    orderId = odao.createPendingOrder(userId);
                } else {
                    orderId = order.getOrderID();
                }

                if (orderId != -1) {
                    boolean added = oddao.addOrderDetail(orderId, fish.getFishID(), 1, fish.getFishPrice());
                    if (added) {
                        double totalPrice = calculateTotalPrice(orderId);
                        odao.updateTotalPrice(orderId, totalPrice);
                        int invoiceId = idao.createInvoice(orderId, totalPrice);
                        if (invoiceId != -1) {
                            // Không cập nhật status thành 'completed' ở đây
                            InvoiceDTO invoice = idao.getInvoiceByOrderId(orderId);
                            request.setAttribute("invoice", invoice);
                            request.setAttribute("message", "Hóa đơn đã được tạo thành công! Vui lòng thanh toán.");
                        } else {
                            request.setAttribute("message", "Không thể tạo hóa đơn!");
                            url = CART_PAGE;
                        }
                    } else {
                        request.setAttribute("message", "Không thể thêm sản phẩm để thanh toán!");
                        url = CART_PAGE;
                    }
                } else {
                    request.setAttribute("message", "Không thể tạo đơn hàng!");
                    url = CART_PAGE;
                }
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

    private String processRemoveFromCart(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException {
        String orderDetailIdStr = request.getParameter("orderDetailId");
        String url = CART_PAGE;

        try {
            int orderDetailId = Integer.parseInt(orderDetailIdStr);
            if (oddao.removeOrderDetail(orderDetailId)) {
                request.setAttribute("message", "Đã xóa sản phẩm khỏi giỏ hàng!");
                log("Removed order detail " + orderDetailId + " for user " + userId);
            } else {
                request.setAttribute("message", "Không thể xóa sản phẩm!");
                log("Failed to remove order detail " + orderDetailId);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("message", "ID chi tiết đơn hàng không hợp lệ!");
            log("Error at processRemoveFromCart - NumberFormatException: " + e.toString());
        } catch (Exception e) {
            request.setAttribute("message", "Lỗi khi xóa sản phẩm: " + e.getMessage());
            log("Error at processRemoveFromCart: " + e.toString());
        }

        return url;
    }

    private String processCheckout(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException {
        String orderIdStr = request.getParameter("orderId");
        String url = INVOICE_PAGE;
        PaymentDAO pdao = new PaymentDAO(); // Thêm PaymentDAO để kiểm tra

        try {
            int orderId = Integer.parseInt(orderIdStr);
            OrderDTO order = odao.getPendingOrderByUserId(userId);
            if (order != null && order.getOrderID() == orderId) {
                // Kiểm tra xem đơn hàng đã có hóa đơn chưa
                InvoiceDTO existingInvoice = idao.getInvoiceByOrderId(orderId);
                if (existingInvoice != null && !pdao.isInvoicePaid(existingInvoice.getInvoiceID())) {
                    // Xóa hóa đơn cũ chưa thanh toán
                    idao.deleteInvoice(existingInvoice.getInvoiceID());
                    log("Deleted unpaid invoice " + existingInvoice.getInvoiceID() + " for order " + orderId);
                }

                double totalPrice = calculateTotalPrice(orderId);
                int invoiceId = idao.createInvoice(orderId, totalPrice);
                if (invoiceId != -1) {
                    InvoiceDTO invoice = idao.getInvoiceByOrderId(orderId);
                    request.setAttribute("invoice", invoice);
                    request.setAttribute("message", "Hóa đơn đã được tạo thành công! Vui lòng thanh toán.");
                } else {
                    request.setAttribute("message", "Không thể tạo hóa đơn!");
                    url = CART_PAGE;
                }
            } else {
                request.setAttribute("message", "Không tìm thấy đơn hàng hợp lệ!");
                url = CART_PAGE;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("message", "ID đơn hàng không hợp lệ!");
            log("Error at processCheckout - NumberFormatException: " + e.toString());
            url = CART_PAGE;
        } catch (Exception e) {
            request.setAttribute("message", "Lỗi khi tạo hóa đơn: " + e.getMessage());
            log("Error at processCheckout: " + e.toString());
            url = CART_PAGE;
        }

        return url;
    }

    private String processPayment(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException {
        String invoiceIdStr = request.getParameter("invoiceId");
        String url = INVOICE_PAGE;
        PaymentDAO pdao = new PaymentDAO();

        try {
            int invoiceId = Integer.parseInt(invoiceIdStr);
            InvoiceDTO invoice = idao.getInvoiceById(invoiceId);
            if (invoice != null) {
                // Kiểm tra xem hóa đơn đã thanh toán chưa
                if (pdao.isInvoicePaid(invoiceId)) {
                    request.setAttribute("message", "Hóa đơn này đã được thanh toán!");
                    request.setAttribute("invoice", invoice);
                    log("Invoice " + invoiceId + " already paid");
                    return url;
                }

                UserDTO user = (UserDTO) request.getSession().getAttribute("user");
                user = udao.getUserById(userId); // Lấy thông tin mới nhất từ DB
                if (user != null) {
                    double balance = user.getBalance();
                    System.out.println("Balance of user " + userId + ": " + balance);
                    double finalPrice = invoice.getFinalPrice();
                    if (balance >= finalPrice) {
                        double newBalance = balance - finalPrice;
                        // Chỉ thực hiện các thay đổi khi mọi bước đều thành công
                        if (udao.updateBalance(userId, newBalance)) {
                            if (pdao.createPayment(invoiceId, finalPrice)) {
                                // Cập nhật trạng thái đơn hàng thành 'completed'
                                int orderId = invoice.getOrderID();
                                if (odao.updateOrderStatus(orderId, "completed")) {
                                    // Cập nhật UserDTO trong session
                                    user.setBalance(newBalance);
                                    request.getSession().setAttribute("user", user);
                                    request.setAttribute("message", "Thanh toán thành công!");
                                    log("Payment successful for invoice " + invoiceId + ", order " + orderId + " completed, new balance: " + newBalance);
                                } else {
                                    // Hoàn tác nếu cập nhật trạng thái thất bại
                                    udao.updateBalance(userId, balance);
                                    pdao.deletePayment(invoiceId); // Xóa bản ghi thanh toán nếu có
                                    request.setAttribute("message", "Lỗi khi cập nhật trạng thái đơn hàng!");
                                    log("Failed to update order status for order " + orderId);
                                }
                            } else {
                                // Hoàn tác nếu ghi nhận thanh toán thất bại
                                udao.updateBalance(userId, balance);
                                request.setAttribute("message", "Lỗi khi ghi nhận thanh toán!");
                                log("Failed to create payment record for invoice " + invoiceId + ", balance restored");
                            }
                        } else {
                            request.setAttribute("message", "Lỗi khi cập nhật số dư!");
                            log("Failed to update balance for user " + userId);
                        }
                    } else {
                        request.setAttribute("message", "Số dư của quý khách không đủ!");
                        log("Insufficient balance for user " + userId + ": " + balance + " < " + finalPrice);
                    }
                    request.setAttribute("invoice", invoice);
                } else {
                    request.setAttribute("message", "Không tìm thấy thông tin người dùng!");
                    log("User not found for userId: " + userId);
                }
            } else {
                request.setAttribute("message", "Không tìm thấy thông tin hóa đơn!");
                log("Invoice not found for invoiceId: " + invoiceId);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("message", "ID hóa đơn không hợp lệ!");
            log("Error at processPayment - NumberFormatException: " + e.toString());
        } catch (Exception e) {
            request.setAttribute("message", "Lỗi khi xử lý thanh toán: " + e.getMessage());
            log("Error at processPayment: " + e.toString());
        }

        return url;
    }

    private double calculateTotalPrice(int orderId) {
        List<OrderDetailDTO> details = oddao.getOrderDetailsByOrderId(orderId);
        double total = 0.0;
        for (OrderDetailDTO detail : details) {
            total += detail.getQuantity() * detail.getPrice();
        }
        return total;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
