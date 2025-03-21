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
public class InvoiceDTO {
    private int invoiceID;
    private int orderID;
    private double totalPrice;
    private double tax;
    private double finalPrice;
    private Timestamp issuedAt;
    private int discountID;
    private double discount_amount;

    public InvoiceDTO() {
    }

    public InvoiceDTO(int invoiceID, int orderID, double totalPrice, double tax, double finalPrice, Timestamp issuedAt) {
        this.invoiceID = invoiceID;
        this.orderID = orderID;
        this.totalPrice = totalPrice;
        this.tax = tax;
        this.finalPrice = finalPrice;
        this.issuedAt = issuedAt;
    }

    public InvoiceDTO(int invoiceID, int orderID, double totalPrice, double tax, double finalPrice, Timestamp issuedAt, int discountID, double discount_amount) {
        this.invoiceID = invoiceID;
        this.orderID = orderID;
        this.totalPrice = totalPrice;
        this.tax = tax;
        this.finalPrice = finalPrice;
        this.issuedAt = issuedAt;
        this.discountID = discountID;
        this.discount_amount = discount_amount;
    }

    public int getDiscountID() {
        return discountID;
    }

    public void setDiscountID(int discountID) {
        this.discountID = discountID;
    }

    public double getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(double discount_amount) {
        this.discount_amount = discount_amount;
    }

    
    
   
    
    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Timestamp getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Timestamp issuedAt) {
        this.issuedAt = issuedAt;
    }

    @Override
    public String toString() {
        return "InvoiceDTO{" + "invoiceID=" + invoiceID + ", orderID=" + orderID + ", totalPrice=" + totalPrice + ", tax=" + tax + ", finalPrice=" + finalPrice + ", issuedAt=" + issuedAt + '}';
    }
    
    
}
