package com.example.travelassistant.food.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.travelassistant.R;
import com.example.travelassistant.food.bean.FoodBean;
import com.example.travelassistant.food.utils.AssetsUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<FoodBean> fbl;


    public OrderAdapter(Context context){
        this.context=context;

    }
    public void setData(List<FoodBean> fbl)
    {
        this.fbl=fbl;
        notifyDataSetChanged();
    }




    @Override
    public int getCount() {
        return fbl==null?0:fbl.size();

    }

    @Override
    public Object getItem(int i) {
        return fbl==null?null:fbl.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if(view==null)
        {
            vh=new ViewHolder();
            view=LayoutInflater.from(context).inflate(R.layout.order_item,null);
            vh.tv_food_name=view.findViewById(R.id.tv_food_name);
            vh.tv_count=view.findViewById(R.id.tv_count);
            vh.tv_money=view.findViewById(R.id.tv_money);
            vh.tv_food_name=view.findViewById(R.id.tv_food_name);
            vh.iv_food_pic=view.findViewById(R.id.iv_food_pic);
            view.setTag(vh);

        }
        else{
            vh= (ViewHolder) view.getTag();
        }
        FoodBean bean= (FoodBean) getItem(i);
        if(bean!=null)
        {
            String foodName = bean.getFoodName();
            vh.tv_food_name.setText(foodName);
            vh.tv_count.setText("x"+bean.getCount());
            vh.tv_money.setText("￥"+bean.getPrice().multiply(BigDecimal.valueOf(bean.getCount())));
//

            Map<String, Bitmap> contentlogoImgMap = AssetsUtils.getContentlogoImgMap();
            Bitmap bitmap = contentlogoImgMap.get(foodName);
            vh.iv_food_pic.setImageBitmap(bitmap);
//
//            String s=bean.getFoodPic();
//            String st=s.substring(21);
//            String str="http://192.168.0.101"+st;
//            Glide.with(context).load(str).error(R.mipmap.ic_launcher).into(vh.iv_food_pic);
        }


        return view;
    }



    class ViewHolder{
        public TextView tv_food_name,tv_count,tv_money;
        public ImageView iv_food_pic;
    }
}
