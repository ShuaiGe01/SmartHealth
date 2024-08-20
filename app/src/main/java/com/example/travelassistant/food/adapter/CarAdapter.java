package com.example.travelassistant.food.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.travelassistant.R;
import com.example.travelassistant.food.bean.FoodBean;

import java.math.BigDecimal;
import java.util.List;

public class CarAdapter extends BaseAdapter {
    private Context mContext;
    private List<FoodBean> fbl;
    private OnSelectListener onSelectListener;
    public CarAdapter(Context context,OnSelectListener onSelectListener)
    {
        this.mContext=context;
        this.onSelectListener=onSelectListener;
    }
    public void setData(List<FoodBean> fbl)
    {
        this.fbl=fbl;
        notifyDataSetChanged();
    }






    @Override
    public int getCount() {
        return fbl==null ? 0:fbl.size();
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
        final ViewHolder viewHolder;
        if(view==null)
        {
            viewHolder=new ViewHolder();
            view= LayoutInflater.from(mContext).inflate(R.layout.car_item,null);
            viewHolder.tv_food_name=(TextView) view.findViewById(R.id.tv_food_name);
            viewHolder.tv_food_count=(TextView) view.findViewById(R.id.tv_food_count);
            viewHolder.tv_food_price=(TextView) view.findViewById(R.id.tv_food_price);
            viewHolder.iv_add=view.findViewById(R.id.iv_add);
            viewHolder.iv_minus=view.findViewById(R.id.iv_minus);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) view.getTag();
        }
        final FoodBean bean= (FoodBean) getItem(i);
        if(bean!=null)
        {

            viewHolder.tv_food_name.setText(bean.getFoodName());
            viewHolder.tv_food_count.setText(bean.getCount()+"");
            BigDecimal count=BigDecimal.valueOf(bean.getCount());
            viewHolder.tv_food_price.setText("￥"+bean.getPrice().multiply(count));
        }
        viewHolder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectListener.onSelectAdd(i,viewHolder.tv_food_count,viewHolder.tv_food_price);

            }
        });
        viewHolder.iv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectListener.onSelectMis(i,viewHolder.tv_food_count,viewHolder.tv_food_price);

            }
        });



        return view;
    }

    class ViewHolder{
        public TextView tv_food_name,tv_food_count,tv_food_price;
        public ImageView iv_add,iv_minus;
    }
    public interface OnSelectListener{
        void onSelectAdd(int position,TextView tv_food_price,TextView tv_food_count);
        void onSelectMis(int position,TextView tv_food_price,TextView tv_food_count);

    }

}
