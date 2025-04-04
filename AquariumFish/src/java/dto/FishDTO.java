/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author PC
 */
public class FishDTO {
    private int fishID;
    private String fishType;
    private String fishName;
    private double fishPrice;
    private int fishQuantity;
    private String fishDescription;
    private String fishImg;
    private int categoryID;
    private String categoryName;

    public FishDTO() {
    }

    public FishDTO(int fishID, String fishType, String fishName, double fishPrice, int fishQuantity, String fishDescription, String fishImg, int categoryID) {
        this.fishID = fishID;
        this.fishType = fishType;
        this.fishName = fishName;
        this.fishPrice = fishPrice;
        this.fishQuantity = fishQuantity;
        this.fishDescription = fishDescription;
        this.fishImg = fishImg;
        this.categoryID = categoryID;
    }
    
        public FishDTO(String fishType, String fishName, double fishPrice, int fishQuantity, String fishDescription, String fishImg, int categoryID) {
        this.fishType = fishType;
        this.fishName = fishName;
        this.fishPrice = fishPrice;
        this.fishQuantity = fishQuantity;
        this.fishDescription = fishDescription;
        this.fishImg = fishImg;
        this.categoryID = categoryID;
    }

    public FishDTO(int fishID, String fishType, String fishName, double fishPrice, int fishQuantity, String fishDescription, String fishImg, int categoryID, String categoryName) {
        this.fishID = fishID;
        this.fishType = fishType;
        this.fishName = fishName;
        this.fishPrice = fishPrice;
        this.fishQuantity = fishQuantity;
        this.fishDescription = fishDescription;
        this.fishImg = fishImg;
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
   

    public int getFishID() {
        return fishID;
    }

    public void setFishID(int fishID) {
        this.fishID = fishID;
    }

    public String getFishType() {
        return fishType;
    }

    public void setFishType(String fishType) {
        this.fishType = fishType;
    }

    public String getFishName() {
        return fishName;
    }

    public void setFishName(String fishName) {
        this.fishName = fishName;
    }

    public double getFishPrice() {
        return fishPrice;
    }

    public void setFishPrice(double fishPrice) {
        this.fishPrice = fishPrice;
    }

    public int getFishQuantity() {
        return fishQuantity;
    }

    public void setFishQuantity(int fishQuantity) {
        this.fishQuantity = fishQuantity;
    }

    public String getFishDescription() {
        return fishDescription;
    }

    public void setFishDescription(String fishDescription) {
        this.fishDescription = fishDescription;
    }

    public String getFishImg() {
        return fishImg;
    }

    public void setFishImg(String fishImg) {
        this.fishImg = fishImg;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }



    @Override
    public String toString() {
        return "FishDTO{" + "fishID=" + fishID + ", fishType=" + fishType + ", fishName=" + fishName + ", fishPrice=" + fishPrice + ", fishQuantity=" + fishQuantity + ", fishDescription=" + fishDescription + ", fishImg=" + fishImg + ", categoriesID=" + categoryID + '}';
    }
    
    
}
