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
            response.sendRedirect("login.jsp");
            return;
        }

        if (action != null) {
            switch (action) {
                case "add":
                    processAddToCart(request, response, userId);
                    return;
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

        getServletContext().getRequestDispatcher(CART_PAGE).forward(request, response);
    }

    private void processAddToCart(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException {
        String fishId = request.getParameter("fishId");
        String quantityStr = request.getParameter("quantity");

        try {
            FishDTO fish = fdao.readbyID(fishId);
            if (fish != null) {
                int quantity = 1;
                try {
                    quantity = Integer.parseInt(quantityStr);
                    if (quantity <= 0) {
                        request.getSession().setAttribute("message", "Quantity must be greater than 0!");
                        response.sendRedirect(CART_PAGE);
                        return;
                    }
                    if (quantity > fish.getFishQuantity()) {
                        request.getSession().setAttribute("message", "Requested quantity exceeds available stock (" + fish.getFishQuantity() + ")!");
                        response.sendRedirect(CART_PAGE);
                        return;
                    }
                } catch (NumberFormatException e) {
                    request.getSession().setAttribute("message", "Invalid quantity!");
                    response.sendRedirect(CART_PAGE);
                    return;
                }

                OrderDTO order = odao.getPendingOrderByUserId(userId);
                int orderId;
                if (order == null) {
                    orderId = odao.createPendingOrder(userId);
                } else {
                    orderId = order.getOrderID();
                }

                if (orderId != -1) {
                    OrderDetailDTO existingDetail = oddao.getOrderDetailByOrderIdAndFishId(orderId, fish.getFishID());
                    if (existingDetail != null) {
                        int newQuantity = existingDetail.getQuantity() + quantity;
                        if (newQuantity > fish.getFishQuantity()) {
                            request.getSession().setAttribute("message", "Total quantity exceeds available stock (" + fish.getFishQuantity() + ")!");
                            response.sendRedirect(CART_PAGE);
                            return;
                        }
                        oddao.updateOrderDetailQuantity(existingDetail.getOrderDetailID(), newQuantity);
                    } else {
                        boolean added = oddao.addOrderDetail(orderId, fish.getFishID(), quantity, fish.getFishPrice());
                        if (!added) {
                            request.getSession().setAttribute("message", "Failed to add item to cart!");
                            response.sendRedirect(CART_PAGE);
                            return;
                        }
                    }

                    double totalPrice = calculateTotalPrice(orderId);
                    odao.updateTotalPrice(orderId, totalPrice);
                    request.getSession().setAttribute("message", "Added " + quantity + " " + fish.getFishName() + " to cart!");
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

        response.sendRedirect(INVOICE_PAGE);
    }

    private void processRemoveFromCart(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException {
        String orderDetailIdStr = request.getParameter("orderDetailId");

        try {
            int orderDetailId = Integer.parseInt(orderDetailIdStr);
            OrderDetailDTO detail = oddao.getOrderDetailById(orderDetailId);
            if (detail == null) {
                request.getSession().setAttribute("message", "Order detail not found!");
                response.sendRedirect(CART_PAGE);
                return;
            }

            int orderId = detail.getOrderID();
            if (oddao.removeOrderDetail(orderDetailId)) {
                double totalPrice = calculateTotalPrice(orderId);
                odao.updateTotalPrice(orderId, totalPrice);
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

        response.sendRedirect(CART_PAGE);
    }

    private void processCheckout(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException {
        String orderIdStr = request.getParameter("orderId");

        try {
            int orderId = Integer.parseInt(orderIdStr);
            OrderDTO order = odao.getPendingOrderByUserId(userId);
            if (order != null && order.getOrderID() == orderId) {
                InvoiceDTO existingInvoice = idao.getInvoiceByOrderId(orderId);
                if (existingInvoice != null && !pdao.isInvoicePaid(existingInvoice.getInvoiceID())) {
                    idao.deleteInvoice(existingInvoice.getInvoiceID());
                    log("Deleted unpaid invoice " + existingInvoice.getInvoiceID() + " for order " + orderId);
                }

                double totalPrice = calculateTotalPrice(orderId);
                odao.updateTotalPrice(orderId, totalPrice);
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
                                request.getSession().setAttribute("discountAmount", discountAmount);
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

        response.sendRedirect(INVOICE_PAGE);
    }

   private void processPayment(HttpServletRequest request, HttpServletResponse response, int userId)
        throws ServletException, IOException {
    String invoiceIdStr = request.getParameter("invoiceId");
    boolean isPaymentSuccessful = false;

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

        // Lấy danh sách OrderDetailDTO để kiểm tra số lượng sản phẩm
        int orderId = invoice.getOrderID();
        List<OrderDetailDTO> orderDetails = oddao.getOrderDetailsByOrderId(orderId);
        if (orderDetails == null || orderDetails.isEmpty()) {
            request.getSession().setAttribute("message", "No items found in the order!");
            log("No order details found for order " + orderId);
            response.sendRedirect(INVOICE_PAGE);
            return;
        }

        // Kiểm tra số lượng sản phẩm trước khi thanh toán
        for (OrderDetailDTO detail : orderDetails) {
            int fishId = detail.getFishID();
            int quantityOrdered = detail.getQuantity();
            int currentQuantity = fdao.getFishQuantity(fishId);
            if (currentQuantity == -1) {
                request.getSession().setAttribute("message", "Product with ID " + fishId + " not found!");
                log("Fish not found for fishId: " + fishId);
                response.sendRedirect(INVOICE_PAGE);
                return;
            }
            if (currentQuantity < quantityOrdered) {
                request.getSession().setAttribute("message", "Insufficient stock for product with ID " + fishId + "! Available: " + currentQuantity + ", Ordered: " + quantityOrdered);
                log("Insufficient stock for fish " + fishId + ": available=" + currentQuantity + ", ordered=" + quantityOrdered);
                response.sendRedirect(INVOICE_PAGE);
                return;
            }
        }

        // Trừ số dư người dùng
        double newBalance = balance - finalPrice;
        if (!udao.updateBalance(userId, newBalance)) {
            request.getSession().setAttribute("message", "Error updating balance!");
            log("Failed to update balance for user " + userId);
            response.sendRedirect(INVOICE_PAGE);
            return;
        }

        // Ghi lại thanh toán
        if (!pdao.createPayment(invoiceId, finalPrice)) {
            udao.updateBalance(userId, balance); // Hoàn tác số dư
            request.getSession().setAttribute("message", "Error recording payment!");
            log("Failed to create payment record for invoice " + invoiceId + ", balance restored");
            response.sendRedirect(INVOICE_PAGE);
            return;
        }

        // Cập nhật trạng thái đơn hàng
        if (!odao.updateOrderStatus(orderId, "completed")) {
            udao.updateBalance(userId, balance); // Hoàn tác số dư
            pdao.deletePayment(invoiceId); // Hoàn tác thanh toán
            request.getSession().setAttribute("message", "Error updating order status!");
            log("Failed to update order status for order " + orderId);
            response.sendRedirect(INVOICE_PAGE);
            return;
        }

        // Cập nhật phương thức thanh toán
        if (!odao.updateOrderPayment(orderId)) {
            log("Failed to update payment method for order " + orderId);
        } else {
            log("Payment method updated to 'balance' for order " + orderId);
        }

        // Trừ số lượng sản phẩm trong bảng tblFish
        boolean stockUpdated = true;
        for (OrderDetailDTO detail : orderDetails) {
            int fishId = detail.getFishID();
            int quantityOrdered = detail.getQuantity();
            int currentQuantity = fdao.getFishQuantity(fishId);
            int newQuantity = currentQuantity - quantityOrdered;
            if (!fdao.updateFishQuantity(fishId, newQuantity)) {
                stockUpdated = false;
                log("Failed to update stock for fish " + fishId + ": new quantity=" + newQuantity);
                break;
            }
        }

        if (!stockUpdated) {
            // Hoàn tác các thay đổi nếu cập nhật số lượng thất bại
            udao.updateBalance(userId, balance); // Hoàn tác số dư
            pdao.deletePayment(invoiceId); // Hoàn tác thanh toán
            odao.updateOrderStatus(orderId, "pending"); // Hoàn tác trạng thái đơn hàng
            request.getSession().setAttribute("message", "Error updating product stock!");
            log("Failed to update product stock, transaction rolled back for invoice " + invoiceId);
            response.sendRedirect(INVOICE_PAGE);
            return;
        }

        // Cập nhật thông tin người dùng và thông báo thành công
        user.setBalance(newBalance);
        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("message", "Payment successful!");
        request.getSession().setAttribute("invoiceId", invoiceId);
        log("Payment successful for invoice " + invoiceId + ", order " + orderId + " completed, new balance: " + newBalance);

        isPaymentSuccessful = true;

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

    if (isPaymentSuccessful) {
        try {
            int invoiceId = Integer.parseInt(invoiceIdStr);
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
        if (details != null) {
            for (OrderDetailDTO detail : details) {
                total += detail.getQuantity() * detail.getPrice();
            }
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