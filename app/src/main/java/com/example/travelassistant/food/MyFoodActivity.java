package com.example.travelassistant.food;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelassistant.R;
import com.example.travelassistant.food.bean.FoodBean;
import com.example.travelassistant.food.utils.AssetsUtils;

import java.util.Map;

public class MyFoodActivity extends AppCompatActivity {
    FoodBean foodBean;
    private TextView tv_food_name;
    private TextView tv_sale_num;
    private TextView tv_food_price;
    private ImageView iv_food_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_food);
        foodBean= (FoodBean) getIntent().getSerializableExtra("food");
        initView();
        setData();
    }
    /* 设置界面数据*/

    private void setData() {
        if(foodBean==null) return;
        tv_food_name.setText(foodBean.getFoodName());






        String s=foodBean.getFoodName();
        Map<String, Bitmap> contentlogoImgMap = AssetsUtils.getContentlogoImgMap();
        Bitmap bitmap1 = contentlogoImgMap.get(s);



        tv_sale_num.setText("月售:"+foodBean.getSaleNum());
        tv_food_price.setText("￥"+foodBean.getPrice());
        iv_food_pic.setImageBitmap(bitmap1);


//        String str="http://192.168.0.101"+st;
//        System.out.println(str);
//        Glide.with(this).load(str).error(R.mipmap.ic_launcher).into(iv_food_pic);





    }

    private void initView() {
        tv_food_name=findViewById(R.id.tv_food_name);
        tv_sale_num=findViewById(R.id.tv_sale_num);
        tv_food_price=findViewById(R.id.tv_price);
        iv_food_pic=findViewById(R.id.iv_food_pic);

    }
}