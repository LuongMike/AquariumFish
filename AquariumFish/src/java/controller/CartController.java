package controller;

import dao.DiscountDAO;
import dto.FishDTO;
import dto.OrderDTO;
import dao.FishDAO;
import dao.InvoiceDAO;
import dao.OrderDAO;
import dao.OrderDetailDAO;
import dao.PaymentDAO;
import dao.UserDAO;
import dto.DiscountDTO;
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
import utils.EmailUtils;

@WebServlet("/CartController")
public class CartController extends HttpServlet {

    private static final String INVOICE_PAGE = "invoice.jsp";
    private static final String CART_PAGE = "cart.jsp";
    private static final String CHECKOUT_PAGE = "checkout.jsp";
    private UserDAO udao = new UserDAO();
    private FishDAO fdao = new FishDAO();
    private OrderDAO odao = new OrderDAO();
    private OrderDetailDAO oddao = new OrderDetailDAO();
    private InvoiceDAO idao = new InvoiceDAO();
    private PaymentDAO pdao = new PaymentDAO();
    private DiscountDAO ddao = new DiscountDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            // Redirect đến trang đăng nhập nếu chưa đăng nhập
            response.sendRedirect("login.jsp");
            return;
        }

        if (action != null) {
            switch (action) {
                case "add":
                    processAddToCart(request, response, userId);
                    return; // Dừng xử lý sau redirect
                case "buyNow":
                    processBuyNow(request, response, userId);
                    return;
                case "remove":
                    processRemoveFromCart(request, response, userId);
                    return;
                case "checkout":
                    processCheckout(request, response, userId);
                    return;
                case "applyDiscount":
                    applyDiscount(request, response, userId);
                    return;
                case "pay":
                    processPayment(request, response, userId);
                    return;
                default:
                    request.setAttribute("message", "Invalid action!");
                    break;
            }
        } else {
            request.setAttribute("message", "No action specified!");
        }

        // Forward nếu không có redirect
        getServletContext().getRequestDispatcher(CART_PAGE).forward(request, response);
    }

    private void processAddToCart(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException {
        String fishId = request.getParameter("fishId");

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
                        // Lưu thông báo vào session để hiển thị sau khi redirect
                        request.getSession().setAttribute("message", "Added " + fish.getFishName() + " to cart!");
                    } else {
                        request.getSession().setAttribute("message", "Failed to add item to cart!");
                    }
                } else {
                    request.getSession().setAttribute("message", "Failed to create order!");
                }
            } else {
                request.getSession().setAttribute("message", "Fish not found with ID: " + fishId);
            }
        } catch (Exception e) {
            request.getSession().setAttribute("message", "Error adding to cart: " + e.getMessage());
            log("Error at processAddToCart: " + e.toString());
        }

        // Redirect đến trang giỏ hàng
        response.sendRedirect(CART_PAGE);
    }

    private void processBuyNow(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException {
        String fishId = request.getParameter("fishId");

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
                            // Lưu thông tin hóa đơn vào session để hiển thị sau khi redirect
                            request.getSession().setAttribute("invoiceId", invoiceId);
                            request.getSession().setAttribute("message", "Invoice created successfully! Please proceed to payment.");
                        } else {
                            request.getSession().setAttribute("message", "Failed to create invoice!");
                        }
                    } else {
                        request.getSession().setAttribute("message", "Failed to add item for payment!");
                    }
                } else {
                    request.getSession().setAttribute("message", "Failed to create order!");
                }
            } else {
                request.getSession().setAttribute("message", "Fish not found with ID: " + fishId);
            }
        } catch (Exception e) {
            request.getSession().setAttribute("message", "Error processing buy now: " + e.getMessage());
            log("Error at processBuyNow: " + e.toString());
        }

        // Redirect đến trang hóa đơn
        response.sendRedirect(INVOICE_PAGE);
    }

    private void processRemoveFromCart(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException {
        String orderDetailIdStr = request.getParameter("orderDetailId");

        try {
            int orderDetailId = Integer.parseInt(orderDetailIdStr);
            if (oddao.removeOrderDetail(orderDetailId)) {
                request.getSession().setAttribute("message", "Item removed from cart!");
                log("Removed order detail " + orderDetailId + " for user " + userId);
            } else {
                request.getSession().setAttribute("message", "Failed to remove item!");
                log("Failed to remove order detail " + orderDetailId);
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("message", "Invalid order detail ID!");
            log("Error at processRemoveFromCart - NumberFormatException: " + e.toString());
        } catch (Exception e) {
            request.getSession().setAttribute("message", "Error removing item: " + e.getMessage());
            log("Error at processRemoveFromCart: " + e.toString());
        }

        // Redirect đến trang giỏ hàng
        response.sendRedirect(CART_PAGE);
    }

    private void processCheckout(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException {
        String orderIdStr = request.getParameter("orderId");
        PaymentDAO pdao = new PaymentDAO();

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
                    request.getSession().setAttribute("invoiceId", invoiceId);
                    request.getSession().setAttribute("message", "Invoice created successfully! Please proceed to payment.");
                } else {
                    request.getSession().setAttribute("message", "Failed to create invoice!");
                }
            } else {
                request.getSession().setAttribute("message", "No valid order found!");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("message", "Invalid order ID!");
            log("Error at processCheckout - NumberFormatException: " + e.toString());
        } catch (Exception e) {
            request.getSession().setAttribute("message", "Error creating invoice: " + e.getMessage());
            log("Error at processCheckout: " + e.toString());
        }

        // Redirect đến trang hóa đơn
        response.sendRedirect(INVOICE_PAGE);
    }

    private void applyDiscount(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException {
        String invoiceIdStr = request.getParameter("invoiceId");
        String discountCode = request.getParameter("discountCode");

        try {
            int invoiceId = Integer.parseInt(invoiceIdStr);
            InvoiceDTO invoice = idao.getInvoiceById(invoiceId);
            if (invoice != null) {
                double finalPrice = invoice.getFinalPrice();
                double discountAmount = 0.0;
                Integer discountID = null;

                if (discountCode != null && !discountCode.trim().isEmpty()) {
                    DiscountDTO discount = ddao.getDiscountByCode(discountCode);
                    if (discount != null) {
                        java.util.Date today = new java.util.Date();
                        java.util.Date startDate = new java.util.Date(discount.getStart_date().getTime());
                        java.util.Date endDate = new java.util.Date(discount.getEnd_date().getTime());
                        System.out.println("Today: " + today + ", Start Date: " + startDate + ", End Date: " + endDate);

                        if (discount.getStatus().equals("active") && today.after(startDate) && today.before(endDate)) {
                            double discountPercentage = discount.getDiscount_percentage();
                            double maxDiscount = discount.getDiscount_amount();
                            discountAmount = (discountPercentage / 100) * finalPrice;
                            System.out.println("Initial discount amount: " + discountAmount + ", Max discount: " + maxDiscount);
                            if (discountAmount > maxDiscount) {
                                discountAmount = maxDiscount;
                                System.out.println("Discount amount capped at max: " + discountAmount);
                            }
                            finalPrice -= discountAmount;
                            discountID = discount.getDiscoutID();
                            System.out.println("Applying discount: invoiceId=" + invoiceId + ", discountID=" + discountID + ", discountAmount=" + discountAmount + ", finalPrice=" + finalPrice);
                            if (idao.updateDiscount(invoiceId, discountID, discountAmount, finalPrice)) {
                                request.getSession().setAttribute("discountMessage", "Discount applied successfully! Discount: " + discountAmount + " VND");
                                request.getSession().setAttribute("appliedDiscountCode", discountCode);
                            } else {
                                request.getSession().setAttribute("discountMessage", "Error applying discount!");
                                System.out.println("Failed to apply discount for invoiceId=" + invoiceId);
                            }
                        } else {
                            request.getSession().setAttribute("discountMessage", "Discount code has expired or is invalid!");
                        }
                    } else {
                        request.getSession().setAttribute("discountMessage", "Discount code does not exist!");
                    }
                }
                request.getSession().setAttribute("invoiceId", invoiceId);
            } else {
                request.getSession().setAttribute("message", "Invoice information not found!");
                log("Invoice not found for invoiceId: " + invoiceId);
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("message", "Invalid invoice ID!");
            log("Error at applyDiscount - NumberFormatException: " + e.toString());
        } catch (Exception e) {
            request.getSession().setAttribute("message", "Error applying discount: " + e.getMessage());
            log("Error at applyDiscount: " + e.toString());
        }

        // Redirect đến trang hóa đơn
        response.sendRedirect(INVOICE_PAGE);
    }

    private void processPayment(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException {
        String invoiceIdStr = request.getParameter("invoiceId");
        boolean isPaymentSuccessful = false; // Theo dõi trạng thái thanh toán

        try {
            int invoiceId = Integer.parseInt(invoiceIdStr);
            InvoiceDTO invoice = idao.getInvoiceById(invoiceId);
            if (invoice == null) {
                request.getSession().setAttribute("message", "Invoice information not found!");
                log("Invoice not found for invoiceId: " + invoiceId);
                response.sendRedirect(INVOICE_PAGE);
                return;
            }

            if (pdao.isInvoicePaid(invoiceId)) {
                request.getSession().setAttribute("message", "This invoice has already been paid!");
                request.getSession().setAttribute("invoiceId", invoiceId);
                log("Invoice " + invoiceId + " already paid");
                response.sendRedirect(INVOICE_PAGE);
                return;
            }

            UserDTO user = udao.getUserById(userId);
            if (user == null) {
                request.getSession().setAttribute("message", "User information not found!");
                log("User not found for userId: " + userId);
                response.sendRedirect(INVOICE_PAGE);
                return;
            }

            double balance = user.getBalance();
            System.out.println("Balance of user " + userId + ": " + balance);
            double finalPrice = invoice.getFinalPrice();

            if (balance < finalPrice) {
                request.getSession().setAttribute("message", "Insufficient balance!");
                log("Insufficient balance for user " + userId + ": " + balance + " < " + finalPrice);
                response.sendRedirect(INVOICE_PAGE);
                return;
            }

            double newBalance = balance - finalPrice;
            if (!udao.updateBalance(userId, newBalance)) {
                request.getSession().setAttribute("message", "Error updating balance!");
                log("Failed to update balance for user " + userId);
                response.sendRedirect(INVOICE_PAGE);
                return;
            }

            if (!pdao.createPayment(invoiceId, finalPrice)) {
                udao.updateBalance(userId, balance); // Hoàn lại số dư
                request.getSession().setAttribute("message", "Error recording payment!");
                log("Failed to create payment record for invoice " + invoiceId + ", balance restored");
                response.sendRedirect(INVOICE_PAGE);
                return;
            }

            int orderId = invoice.getOrderID();
            if (!odao.updateOrderStatus(orderId, "completed")) {
                udao.updateBalance(userId, balance); // Hoàn lại số dư
                pdao.deletePayment(invoiceId); // Xóa bản ghi thanh toán
                request.getSession().setAttribute("message", "Error updating order status!");
                log("Failed to update order status for order " + orderId);
                response.sendRedirect(INVOICE_PAGE);
                return;
            }

            // Cập nhật payment_method thành "balance"
            if (!odao.updateOrderPayment(orderId)) {
                log("Failed to update payment method for order " + orderId);
            } else {
                log("Payment method updated to 'balance' for order " + orderId);
            }

            // Thanh toán thành công
            user.setBalance(newBalance);
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("message", "Payment successful!");
            request.getSession().setAttribute("invoiceId", invoiceId);
            log("Payment successful for invoice " + invoiceId + ", order " + orderId + " completed, new balance: " + newBalance);

            // Đánh dấu thanh toán thành công
            isPaymentSuccessful = true;

            // Chuyển hướng sau khi tất cả các bước hoàn tất
            response.sendRedirect(INVOICE_PAGE);

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("message", "Invalid invoice ID!");
            log("Error at processPayment - NumberFormatException: " + e.toString());
            response.sendRedirect(INVOICE_PAGE);
        } catch (Exception e) {
            String errorMessage = e.getMessage() != null ? e.getMessage() : "Unknown error";
            log("Error at processPayment: " + errorMessage, e);
            request.getSession().setAttribute("message", "Error processing payment: " + errorMessage);
            response.sendRedirect(INVOICE_PAGE);
        }

        // Gửi email xác nhận thanh toán sau khi tất cả các bước hoàn tất
        if (isPaymentSuccessful) {
            try {
                int invoiceId = Integer.parseInt(invoiceIdStr); // Lấy lại invoiceId
                InvoiceDTO invoice = idao.getInvoiceById(invoiceId);
                if (invoice != null) {
                    int orderId = invoice.getOrderID();
                    OrderDTO order = odao.getOrderById(orderId);
                    System.out.println(order);
                    if (order == null) {
                        log("Order not found for orderId: " + orderId + " when sending email");
                    } else {
                        List<OrderDetailDTO> details = oddao.getOrderDetailsByOrderId(orderId);
                        if (details == null) {
                            log("Order details not found (null) for orderId: " + orderId + " when sending email");
                        } else if (details.isEmpty()) {
                            log("Order details are empty for orderId: " + orderId + " when sending email");
                        } else {
                            UserDTO user = udao.getUserById(userId);
                            if (user == null) {
                                log("User not found for userId: " + userId + " when sending email");
                            } else {
                                String userName = user.getUserName() != null ? user.getUserName() : "Customer";
                                String userEmail = user.getEmail();
                                if (userEmail == null || userEmail.trim().isEmpty()) {
                                    log("User email is null or empty for userId: " + userId + " when sending email");
                                    //userEmail = "luongdz2vnvt@gmai.com"; // Thay bằng email của bạn để kiểm tra
                                    log("Using default email for testing: " + userEmail);
                                }
                                boolean emailSent = EmailUtils.sendPaymentConfirmationEmail(order, details, userName, userEmail);
                                if (emailSent) {
                                    log("Payment confirmation email sent to " + userEmail);
                                } else {
                                    log("Failed to send payment confirmation email to " + userEmail);
                                }
                            }
                        }
                    }
                } else {
                    log("Invoice not found for invoiceId: " + invoiceId + " when sending email");
                }
            } catch (Exception e) {
                log("Error sending payment confirmation email: " + e.getMessage(), e);
            }
        }
    }

    private double calculateTotalPrice(int orderId) throws ClassNotFoundException {
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
