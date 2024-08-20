package com.example.travelassistant.food;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.travelassistant.R;
import com.example.travelassistant.food.adapter.ShopAdapter;
import com.example.travelassistant.food.bean.ShopBean;
import com.example.travelassistant.food.utils.AssetsUtils;
import com.example.travelassistant.food.utils.JsonParse;

import java.util.List;

public class FoodActivity extends AppCompatActivity {
    private RelativeLayout rlTitleBar;
    private TextView tvTitle;
    private ListView lvShopList;
    private ShopAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        initView();
    }
    private void initView(){
        rlTitleBar=findViewById(R.id.rl_title_bar);
        tvTitle=findViewById(R.id.tv_title);
        lvShopList=findViewById(R.id.lv_shop_list);
        String json= AssetsUtils.getJsonFromAssets(this,"shop_list_data.json");
        List<ShopBean> list= JsonParse.getInstance().getShopList(json);
        AssetsUtils.saveBitmapFromAssets(this,list);
        adapter=new ShopAdapter(this);
        adapter.setData(list);
        lvShopList.setAdapter(adapter);
        tvTitle.setText("美食小吃");
        rlTitleBar.setBackgroundColor(getResources().getColor(R.color.blue_color));

    }
}