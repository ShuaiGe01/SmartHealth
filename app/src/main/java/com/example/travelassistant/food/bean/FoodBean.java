package com.example.travelassistant.food.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class FoodBean implements Serializable {
    /**
     * foodId : 1
     * foodName : 招牌丰收硕果12寸
     * taste : 水果、奶油、面包、鸡蛋
     * saleNum : 50
     * price : 198
     * count : 0
     * foodPic : http://192.168.31.135:3000/order/img/food/food1.png
     */

    private String foodId;
    private String foodName;
    private String taste;
    private String saleNum;
    private BigDecimal price;
    private int count;
    private String foodPic;

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public String getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(String saleNum) {
        this.saleNum = saleNum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getFoodPic() {
        return foodPic;
    }

    public void setFoodPic(String foodPic) {
        this.foodPic = foodPic;
    }
}
