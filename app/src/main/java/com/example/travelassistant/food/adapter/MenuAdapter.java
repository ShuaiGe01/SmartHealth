package com.example.travelassistant.food.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.travelassistant.R;
import com.example.travelassistant.food.MyFoodActivity;
import com.example.travelassistant.food.bean.FoodBean;
import com.example.travelassistant.food.utils.AssetsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuAdapter extends BaseAdapter {
    private Context context;
    private List<FoodBean> beans=new ArrayList<>();
    private OnSelectListener onSelectListener;
    public MenuAdapter(Context context,OnSelectListener onSelectListener)
    {
        this.context=context;
        this.onSelectListener=onSelectListener;
    }
    public void setData(List<FoodBean> beans)
    {
        this.beans.clear();
        this.beans.addAll(beans);
        notifyDataSetChanged();//通知listview刷新数据
    }




    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public Object getItem(int i) {
        return beans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh=null;
        if(view==null)
        {
            view= LayoutInflater.from(context).inflate(R.layout.menu_item,null);
            vh=new ViewHolder();
             vh.tvFoodName=view.findViewById(R.id.tv_food_name);
            vh.tvTaste=view.findViewById(R.id.tv_taste);
            vh.tvSaleNum=view.findViewById(R.id.tv_sale_num);
            vh.tvPrice=view.findViewById(R.id.tv_price);
            vh.btnAddCar=view.findViewById(R.id.btn_add_car);
            vh.tvFoodPic=view.findViewById(R.id.tv_food_pic);
            view.setTag(vh);



        }
        else
        {
            vh=(ViewHolder) view.getTag();
        }
        FoodBean foodBean=beans.get(i);
        if(foodBean!=null)
        {
            String foodName = foodBean.getFoodName();
            vh.tvFoodName.setText(foodName);

            Map<String, Bitmap> contentlogoImgMap = AssetsUtils.getContentlogoImgMap();
            Bitmap bitmap = contentlogoImgMap.get(foodName);
            vh.tvFoodPic.setImageBitmap(bitmap);

//            String s=foodBean.getFoodPic();
//            String st=s.substring(21);
//            String str="http://192.168.0.101"+st;
//            Glide.with(context).load(str).error(R.mipmap.ic_launcher).into(vh.tvFoodPic);
            vh.tvPrice.setText("￥"+foodBean.getPrice());
            vh.tvSaleNum.setText("月售"+foodBean.getSaleNum());
            vh.tvTaste.setText(foodBean.getTaste());
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(foodBean==null)
                    return;
                Intent intent=new Intent(context, MyFoodActivity.class);
                intent.putExtra("food",foodBean);
                context.startActivity(intent);

            }
        });
        vh.btnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectListener.onselectAddCar(i);
            }
        });


        return view;
    }


    class ViewHolder{
        public TextView tvFoodName,tvTaste,tvSaleNum,tvPrice;
        public Button btnAddCar;
        public ImageView tvFoodPic;
    }
    public interface OnSelectListener{
        void onselectAddCar(int position);
    }

}
