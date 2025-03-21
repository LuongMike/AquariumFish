/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.sql.Date;

/**
 *
 * @author PC
 */
public class DiscountDTO {
    private int discoutID;
    private String code;
    private double discount_percentage;
    private double discount_amount;
    private Date start_date;
    private Date end_date;
    private String status;

    public DiscountDTO() {
    }

    public DiscountDTO(int discoutID, String code, double discount_percentage, double discount_amount, Date start_date, Date end_date, String status) {
        this.discoutID = discoutID;
        this.code = code;
        this.discount_percentage = discount_percentage;
        this.discount_amount = discount_amount;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
    }

    public DiscountDTO(String code, double discount_percentage, double discount_amount, Date start_date, Date end_date, String status) {
        this.code = code;
        this.discount_percentage = discount_percentage;
        this.discount_amount = discount_amount;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
    }

    public int getDiscoutID() {
        return discoutID;
    }

    public void setDiscoutID(int discoutID) {
        this.discoutID = discoutID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getDiscount_percentage() {
        return discount_percentage;
    }

    public void setDiscount_percentage(double discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    public double getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(double discount_amount) {
        this.discount_amount = discount_amount;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DiscountDTO{" + "discoutID=" + discoutID + ", code=" + code + ", discount_percentage=" + discount_percentage + ", discount_amount=" + discount_amount + ", start_date=" + start_date + ", end_date=" + end_date + ", status=" + status + '}';
    }
    
    
    
}
