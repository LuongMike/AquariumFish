package utils;

import dao.FishDAO;
import dto.FishDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Thư viện: https://mvnrepository.com/artifact/com.sun.mail/javax.mail
 * Tạo mật khẩu ứng dụng: https://myaccount.google.com/apppasswords
 */
public class EmailUtils {

    private static final Logger LOGGER = Logger.getLogger(EmailUtils.class.getName());

    // Thông tin tài khoản email dùng để gửi (thay đổi thông tin này)
    private static final String EMAIL_USERNAME = "luongdz10vnvt@gmail.com";
    private static final String EMAIL_PASSWORD = "kdyzprlwhvnuairx";

    // Cấu hình SMTP server
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";

    /**
     * Sends a registration success email to the user
     *
     * @param toEmail The recipient's email address
     * @param userName The user's name
     * @param account The user's account name
     * @return true if the email is sent successfully, false if there is an error
     */
    
    public static boolean sendPaymentConfirmationEmail(OrderDTO order, List<OrderDetailDTO> orderDetails, String userName, String email) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.port", SMTP_PORT);

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Payment Confirmation - Order #" + order.getOrderID());

            String htmlContent = createPaymentConfirmationEmailContent(order, orderDetails, userName);
            message.setContent(htmlContent, "text/html; charset=utf-8");

            Transport.send(message);
            LOGGER.log(Level.INFO, "Payment confirmation email sent successfully to {0}", email);
            return true;
        } catch (MessagingException e) {
            LOGGER.log(Level.SEVERE, "Failed to send payment confirmation email to {0}. Error: {1}", new Object[]{email, e.getMessage()});
            return false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error when sending payment confirmation email to {0}. Error: {1}", new Object[]{email, e.getMessage()});
            return false;
        }
    }

    private static String createPaymentConfirmationEmailContent(OrderDTO order, List<OrderDetailDTO> orderDetails, String userName) {
        FishDAO fishDAO = new FishDAO();
        StringBuilder productListHtml = new StringBuilder();
        for (OrderDetailDTO detail : orderDetails) {
            String fishName = "Unknown Fish";
            try {
                FishDTO fish = fishDAO.readbyID(String.valueOf(detail.getFishID()));
                if (fish != null) {
                    fishName = fish.getFishName();
                }
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Error retrieving fish name for fishID " + detail.getFishID() + ": " + e.getMessage(), e);
            }
            productListHtml.append("<tr>")
                    .append("<td style=\"padding: 8px; border-bottom: 1px solid #ddd;\">" + fishName + "</td>")
                    .append("<td style=\"padding: 8px; border-bottom: 1px solid #ddd; text-align: center;\">" + detail.getQuantity() + "</td>")
                    .append("<td style=\"padding: 8px; border-bottom: 1px solid #ddd; text-align: right;\">$" + String.format("%.2f", detail.getPrice()) + "</td>")
                    .append("<td style=\"padding: 8px; border-bottom: 1px solid #ddd; text-align: right;\">$" + String.format("%.2f", detail.getPrice() * detail.getQuantity()) + "</td>")
                    .append("</tr>");
        }

        return "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <title>Payment Confirmation</title>\n"
                + "    <style>\n"
                + "        body {\n"
                + "            font-family: Arial, sans-serif;\n"
                + "            line-height: 1.6;\n"
                + "            color: #333;\n"
                + "            margin: 0;\n"
                + "            padding: 0;\n"
                + "            background-color: #f0f8ff;\n"
                + "        }\n"
                + "        .container {\n"
                + "            max-width: 600px;\n"
                + "            margin: 20px auto;\n"
                + "            padding: 20px;\n"
                + "            background-color: #e0f7fa;\n"
                + "            border-radius: 10px;\n"
                + "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n"
                + "        }\n"
                + "        .header {\n"
                + "            background-color: #0077cc;\n"
                + "            color: white;\n"
                + "            padding: 15px;\n"
                + "            text-align: center;\n"
                + "            border-top-left-radius: 10px;\n"
                + "            border-top-right-radius: 10px;\n"
                + "        }\n"
                + "        .content {\n"
                + "            padding: 20px;\n"
                + "            background-color: white;\n"
                + "            border-bottom-left-radius: 10px;\n"
                + "            border-bottom-right-radius: 10px;\n"
                + "        }\n"
                + "        .button {\n"
                + "            display: inline-block;\n"
                + "            padding: 10px 20px;\n"
                + "            background-color: #0077cc;\n"
                + "            color: white;\n"
                + "            text-decoration: none;\n"
                + "            border-radius: 5px;\n"
                + "            margin: 20px 0;\n"
                + "        }\n"
                + "        .button:hover {\n"
                + "            background-color: #005fa3;\n"
                + "        }\n"
                + "        .footer {\n"
                + "            text-align: center;\n"
                + "            margin-top: 20px;\n"
                + "            font-size: 12px;\n"
                + "            color: #666;\n"
                + "        }\n"
                + "        table {\n"
                + "            width: 100%;\n"
                + "            border-collapse: collapse;\n"
                + "            margin: 20px 0;\n"
                + "        }\n"
                + "        th, td {\n"
                + "            padding: 8px;\n"
                + "            text-align: left;\n"
                + "            border-bottom: 1px solid #ddd;\n"
                + "        }\n"
                + "        th {\n"
                + "            background-color: #f2f2f2;\n"
                + "        }\n"
                + "        .total {\n"
                + "            font-weight: bold;\n"
                + "            text-align: right;\n"
                + "            margin-top: 10px;\n"
                + "        }\n"
                + "    </style>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <div class=\"container\">\n"
                + "        <div class=\"header\">\n"
                + "            <h1>Payment Confirmation</h1>\n"
                + "        </div>\n"
                + "        <div class=\"content\">\n"
                + "            <h2>Hello, " + userName + "!</h2>\n"
                + "            <p>We are pleased to inform you that your payment for Order #" + order.getOrderID() + " has been successfully processed.</p>\n"
                + "            <h3>Order Details</h3>\n"
                + "            <table>\n"
                + "                <thead>\n"
                + "                    <tr>\n"
                + "                        <th>Product</th>\n"
                + "                        <th>Quantity</th>\n"
                + "                        <th>Price</th>\n"
                + "                        <th>Total</th>\n"
                + "                    </tr>\n"
                + "                </thead>\n"
                + "                <tbody>\n"
                +                    productListHtml.toString() + "\n"
                + "                </tbody>\n"
                + "            </table>\n"
                + "            <p class=\"total\">Total Amount: $" + String.format("%.2f", order.getTotalPrice()) + "</p>\n"
                + "            <h3>Payment Information</h3>\n"
                + "            <p><strong>Payment Method:</strong> " + (order.getPaymentMethod() != null ? order.getPaymentMethod() : "N/A") + "</p>\n"
                + "            <p><strong>Payment Date:</strong> " + order.getCreatedAt() + "</p>\n"
                + "            <p>We will process your order soon and notify you once it has been shipped.</p>\n"
                + "            <a href=\"http://localhost:8080/YourApp/order-details?orderId=" + order.getOrderID() + "\" class=\"button\">View Order Details</a>\n"
                + "            <p>If you have any questions or need assistance, please feel free to contact our support team at: <a href=\"mailto:support@yourapp.com\">support@yourapp.com</a>.</p>\n"
                + "            <p>Best regards,<br>The YourApp Team</p>\n"
                + "        </div>\n"
                + "        <div class=\"footer\">\n"
                + "            <p>This is an automated email, please do not reply to this email.</p>\n"
                + "            <p>© 2025 YourApp. All rights reserved.</p>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "</body>\n"
                + "</html>";
    }

    
    public static boolean sendRegistrationEmail(String toEmail, String userName, String account) {
        try {
            // Thiết lập các thuộc tính
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.port", SMTP_PORT);

            // Tạo phiên xác thực
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
                }
            });

            // Tạo message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Welcome to Our System!");

            // Xây dựng nội dung HTML email
            String htmlContent = createRegistrationEmailContent(userName, account);
            message.setContent(htmlContent, "text/html; charset=utf-8");

            // Gửi email
            Transport.send(message);
            LOGGER.log(Level.INFO, "Registration email sent successfully to {0}", toEmail);
            return true;
        } catch (MessagingException e) {
            LOGGER.log(Level.SEVERE, "Failed to send registration email to {0}. Error: {1}", new Object[]{toEmail, e.getMessage()});
            return false;
        }
    }

    /**
     * Builds the HTML content for the registration success email
     *
     * @param userName The user's name
     * @param account The user's account name
     * @return The complete HTML string for the email content
     */
    private static String createRegistrationEmailContent(String userName, String account) {
        return "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <title>Registration Successful</title>\n"
                + "    <style>\n"
                + "        body {\n"
                + "            font-family: Arial, sans-serif;\n"
                + "            line-height: 1.6;\n"
                + "            color: #333;\n"
                + "            margin: 0;\n"
                + "            padding: 0;\n"
                + "            background-color: #f0f8ff;\n"
                + "        }\n"
                + "        .container {\n"
                + "            max-width: 600px;\n"
                + "            margin: 20px auto;\n"
                + "            padding: 20px;\n"
                + "            background-color: #e0f7fa;\n"
                + "            border-radius: 10px;\n"
                + "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n"
                + "        }\n"
                + "        .header {\n"
                + "            background-color: #0077cc;\n"
                + "            color: white;\n"
                + "            padding: 15px;\n"
                + "            text-align: center;\n"
                + "            border-top-left-radius: 10px;\n"
                + "            border-top-right-radius: 10px;\n"
                + "        }\n"
                + "        .content {\n"
                + "            padding: 20px;\n"
                + "            background-color: white;\n"
                + "            border-bottom-left-radius: 10px;\n"
                + "            border-bottom-right-radius: 10px;\n"
                + "        }\n"
                + "        .button {\n"
                + "            display: inline-block;\n"
                + "            padding: 10px 20px;\n"
                + "            background-color: #0077cc;\n"
                + "            color: white;\n"
                + "            text-decoration: none;\n"
                + "            border-radius: 5px;\n"
                + "            margin: 20px 0;\n"
                + "        }\n"
                + "        .button:hover {\n"
                + "            background-color: #005fa3;\n"
                + "        }\n"
                + "        .footer {\n"
                + "            text-align: center;\n"
                + "            margin-top: 20px;\n"
                + "            font-size: 12px;\n"
                + "            color: #666;\n"
                + "        }\n"
                + "    </style>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <div class=\"container\">\n"
                + "        <div class=\"header\">\n"
                + "            <h1>Welcome!</h1>\n"
                + "        </div>\n"
                + "        <div class=\"content\">\n"
                + "            <h2>Hello, " + userName + "!</h2>\n"
                + "            <p>Thank you for registering an account with our system. Your account has been successfully created.</p>\n"
                + "            <p><strong>Your login information:</strong></p>\n"
                + "            <p>Account: <strong>" + account + "</strong></p>\n"
                + "            <p>You can now log in to start exploring our services.</p>\n"
                + "            <a href=\"http://localhost:8080/YourApp/login.jsp\" class=\"button\">Log In Now</a>\n"
                + "            <p>If you have any questions or need assistance, please feel free to contact our support team at: <a href=\"mailto:support@yourapp.com\">support@yourapp.com</a>.</p>\n"
                + "            <p>Best regards,<br>The YourApp Team</p>\n"
                + "        </div>\n"
                + "        <div class=\"footer\">\n"
                + "            <p>This is an automated email, please do not reply to this email.</p>\n"
                + "            <p>© 2025 YourApp. All rights reserved.</p>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "</body>\n"
                + "</html>";
    }

    /**
     * Sends a verification email with a token to the user
     *
     * @param toEmail The recipient's email address
     * @param userName The user's name
     * @param token The verification token
     * @return true if the email is sent successfully, false if there is an error
     */
    public static boolean sendVerificationEmail(String toEmail, String userName, String token) {
        try {
            // Thiết lập các thuộc tính
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.port", SMTP_PORT);

            // Tạo phiên xác thực
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
                }
            });

            // Tạo message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Verify Your Account");

            // Xây dựng nội dung HTML email
            String verificationLink = "http://localhost:8080/YourApp/VerifyController?token=" + token;
            String htmlContent = createVerificationEmailContent(userName, verificationLink);
            message.setContent(htmlContent, "text/html; charset=utf-8");

            // Gửi email
            Transport.send(message);
            LOGGER.log(Level.INFO, "Verification email sent successfully to {0}", toEmail);
            return true;
        } catch (MessagingException e) {
            LOGGER.log(Level.SEVERE, "Failed to send verification email to {0}. Error: {1}", new Object[]{toEmail, e.getMessage()});
            return false;
        }
    }

    /**
     * Builds the HTML content for the account verification email
     *
     * @param userName The user's name
     * @param verificationLink The verification link
     * @return The complete HTML string for the email content
     */
    private static String createVerificationEmailContent(String userName, String verificationLink) {
        return "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <title>Verify Your Account</title>\n"
                + "    <style>\n"
                + "        body {\n"
                + "            font-family: Arial, sans-serif;\n"
                + "            line-height: 1.6;\n"
                + "            color: #333;\n"
                + "            margin: 0;\n"
                + "            padding: 0;\n"
                + "            background-color: #f0f8ff;\n"
                + "        }\n"
                + "        .container {\n"
                + "            max-width: 600px;\n"
                + "            margin: 20px auto;\n"
                + "            padding: 20px;\n"
                + "            background-color: #e0f7fa;\n"
                + "            border-radius: 10px;\n"
                + "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n"
                + "        }\n"
                + "        .header {\n"
                + "            background-color: #0077cc;\n"
                + "            color: white;\n"
                + "            padding: 15px;\n"
                + "            text-align: center;\n"
                + "            border-top-left-radius: 10px;\n"
                + "            border-top-right-radius: 10px;\n"
                + "        }\n"
                + "        .content {\n"
                + "            padding: 20px;\n"
                + "            background-color: white;\n"
                + "            border-bottom-left-radius: 10px;\n"
                + "            border-bottom-right-radius: 10px;\n"
                + "        }\n"
                + "        .button {\n"
                + "            display: inline-block;\n"
                + "            padding: 10px 20px;\n"
                + "            background-color: #0077cc;\n"
                + "            color: white;\n"
                + "            text-decoration: none;\n"
                + "            border-radius: 5px;\n"
                + "            margin: 20px 0;\n"
                + "        }\n"
                + "        .button:hover {\n"
                + "            background-color: #005fa3;\n"
                + "        }\n"
                + "        .footer {\n"
                + "            text-align: center;\n"
                + "            margin-top: 20px;\n"
                + "            font-size: 12px;\n"
                + "            color: #666;\n"
                + "        }\n"
                + "    </style>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <div class=\"container\">\n"
                + "        <div class=\"header\">\n"
                + "            <h1>Verify Your Account</h1>\n"
                + "        </div>\n"
                + "        <div class=\"content\">\n"
                + "            <h2>Hello, " + userName + "!</h2>\n"
                + "            <p>Thank you for registering with our system. To complete your registration, please verify your email address by clicking the button below:</p>\n"
                + "            <a href=\"" + verificationLink + "\" class=\"button\">Verify Your Account</a>\n"
                + "            <p>If the button doesn't work, you can copy and paste the following link into your browser:</p>\n"
                + "            <p><a href=\"" + verificationLink + "\">" + verificationLink + "</a></p>\n"
                + "            <p>This verification link will expire in 24 hours.</p>\n"
                + "            <p>If you did not register for this account, please ignore this email.</p>\n"
                + "            <p>Best regards,<br>The YourApp Team</p>\n"
                + "        </div>\n"
                + "        <div class=\"footer\">\n"
                + "            <p>This is an automated email, please do not reply to this email.</p>\n"
                + "            <p>© 2025 YourApp. All rights reserved.</p>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "</body>\n"
                + "</html>";
    }

   public static void main(String[] args) {
        // Tạo dữ liệu mẫu cho OrderDTO
        OrderDTO order = new OrderDTO();
        order.setOrderID(1);
        order.setUserID(1);
        order.setTotalPrice(150.50);
        order.setStatus("completed");
        order.setPaymentMethod("balance");
        order.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        // Tạo dữ liệu mẫu cho danh sách OrderDetailDTO
        List<OrderDetailDTO> orderDetails = new ArrayList<>();
        OrderDetailDTO detail1 = new OrderDetailDTO(1, 1, 101, 2, 50.25);
        OrderDetailDTO detail2 = new OrderDetailDTO(2, 1, 102, 1, 50.0);
        orderDetails.add(detail1);
        orderDetails.add(detail2);

        // Thông tin người nhận
        String userName = "Test User";
        String userEmail = "luongdz2vnvt@gmail.com"; // Thay bằng email của bạn để nhận email kiểm tra

        // Gọi phương thức gửi email
        boolean emailSent = sendPaymentConfirmationEmail(order, orderDetails, userName, userEmail);
        if (emailSent) {
            System.out.println("Email sent successfully to " + userEmail);
        } else {
            System.out.println("Failed to send email to " + userEmail);
        }
    }
}