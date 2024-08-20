package com.example.travelassistant.food.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ShopBean implements Serializable {

    /**
     * id : 1
     * shopName : 蛋糕房
     * saleNum : 996
     * offerPrice : 100
     * distributionCost : 5
     * welfare : 进店可获得一个香草冰淇淋
     * time : 配送约2-5小时
     * shopPic : http://192.168.31.135:3000/order/img/shop/shop1.png
     * shopNotice : 公告：下单后2-5小时送达！请耐心等候
     * foodList : [{"foodId":"1","foodName":"招牌丰收硕果12寸","taste":"水果、奶油、面包、鸡蛋","saleNum":"50","price":198,"count":0,"foodPic":"http://192.168.31.135:3000/order/img/food/food1.png"},{"foodId":"2","foodName":"玫瑰花创意蛋糕","taste":"玫瑰花、奶油、鸡蛋","saleNum":"100","price":148,"count":0,"foodPic":"http://192.168.31.135:3000/order/img/food/food2.png"},{"foodId":"3","foodName":"布朗熊与可妮","taste":"奶油、巧克力、果粒夹层","saleNum":"80","price":90,"count":0,"foodPic":"http://192.168.31.135:3000/order/img/food/food3.png"}]
     */

    private int id;
    private String shopName;
    private int saleNum;
    private BigDecimal offerPrice;
    private int distributionCost;
    private String welfare;
    private String time;
    private String shopPic;
    private String shopNotice;

    public List<FoodBean> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<FoodBean> foodList) {
        this.foodList = foodList;
    }

    private List<FoodBean> foodList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }

    public BigDecimal getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(BigDecimal offerPrice) {
        this.offerPrice = offerPrice;
    }

    public int getDistributionCost() {
        return distributionCost;
    }

    public void setDistributionCost(int distributionCost) {
        this.distributionCost = distributionCost;
    }

    public String getWelfare() {
        return welfare;
    }

    public void setWelfare(String welfare) {
        this.welfare = welfare;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getShopPic() {
        return shopPic;
    }

    public void setShopPic(String shopPic) {
        this.shopPic = shopPic;
    }

    public String getShopNotice() {
        return shopNotice;
    }

    public void setShopNotice(String shopNotice) {
        this.shopNotice = shopNotice;
    }




}
