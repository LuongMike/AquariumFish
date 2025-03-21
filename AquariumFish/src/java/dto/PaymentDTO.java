/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.sql.Timestamp;

/**
 *
 * @author PC
 */
public class PaymentDTO {
    private int paymentID;
    private int invoiceID;
    private String transactionId;
    private double amount;
    private String status;
    private String paymentMethod;
    private Timestamp paidAt;

    public PaymentDTO() {
    }

    public PaymentDTO(int paymentID, int invoiceID, String transactionId, double amount, String status, String paymentMethod, Timestamp paidAt) {
        this.paymentID = paymentID;
        this.invoiceID = invoiceID;
        this.transactionId = transactionId;
        this.amount = amount;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.paidAt = paidAt;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Timestamp getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(Timestamp paidAt) {
        this.paidAt = paidAt;
    }

    @Override
    public String toString() {
        return "PaymentDTO{" + "paymentID=" + paymentID + ", invoiceID=" + invoiceID + ", transactionId=" + transactionId + ", amount=" + amount + ", status=" + status + ", paymentMethod=" + paymentMethod + ", paidAt=" + paidAt + '}';
    }
    
    
}
