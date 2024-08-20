package com.example.travelassistant.food.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelassistant.R;
import com.example.travelassistant.food.ShopDetailActivity;
import com.example.travelassistant.food.bean.ShopBean;
import com.example.travelassistant.food.utils.AssetsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShopAdapter extends BaseAdapter {
    private Context context;
    private List<ShopBean> data=new ArrayList<>();
    Map<String, Bitmap> logoMap;


    public ShopAdapter(Context context) {

        this.context = context;
        logoMap= AssetsUtils.getLogoImgMap();
    }


    public void setData(List<ShopBean> data) {
        this.data.clear();
        this.data.addAll(data);//我们不能够改变this.data的地址，否则，会导致无法刷新数据
        //数据有变化，要让我们的数据进行刷新
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return   data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null)
        {
            view= LayoutInflater.from(context).inflate(R.layout.shop_item,null);
            viewHolder=new ViewHolder();
            viewHolder.tvOfferPrice=view.findViewById(R.id.tv_offer_price);
            viewHolder.tvShopName=view.findViewById(R.id.tv_shop_name);
            viewHolder.tvSaleNum=view.findViewById(R.id.tv_sale_num);
            viewHolder.tvTime=view.findViewById(R.id.tv_time);
            viewHolder.tvWelfare=view.findViewById(R.id.tv_welfare);
            viewHolder.ivShopPic=view.findViewById(R.id.iv_shop_pic);
            view.setTag(viewHolder);

        }else
        {
            viewHolder=(ViewHolder) view.getTag();
        }
       final ShopBean shopBean=data.get(position);
//        String s=shopBean.getShopPic();
//        String st=s.substring(21);
//        String str="http://192.168.0.101"+st;
//       Glide.with(context).load(str).error(R.mipmap.ic_launcher).into(viewHolder.ivShopPic);


        String shopName = shopBean.getShopName();

        Bitmap bitmap=logoMap.get(shopName);


        viewHolder.ivShopPic.setImageBitmap(bitmap);

//        Glide.with(context).load(shopBean.getShopPic()).error(R.mipmap.ic_launcher).into(viewHolder.ivShopPic);

        viewHolder.tvTime.setText(shopBean.getWelfare());
        viewHolder.tvWelfare.setText(shopBean.getWelfare());

        viewHolder.tvSaleNum.setText("月售"+shopBean.getSaleNum());
        viewHolder.tvShopName.setText(shopBean.getShopName());
        viewHolder.tvOfferPrice.setText("起送￥"+shopBean.getOfferPrice()+"配送￥"+shopBean.getDistributionCost());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  Intent intent=new Intent(context, ShopDetailActivity.class);
                  intent.putExtra("shop", shopBean);
                  context.startActivity(intent);
//                Toast.makeText(context,shopBean.getShopName(),Toast.LENGTH_SHORT).show();

            }
        });
        return view ;
    }



    class ViewHolder{
        TextView tvShopName,tvSaleNum,tvOfferPrice,tvWelfare,tvTime;
        ImageView ivShopPic;
    }
}
