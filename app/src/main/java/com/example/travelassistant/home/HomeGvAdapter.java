package com.example.travelassistant.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelassistant.R;
import com.example.travelassistant.db.BlogBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeGvAdapter extends BaseAdapter {
    Context context;
    List<BlogBean> list;

    public HomeGvAdapter(Context context, List<BlogBean> list) {
        this.context = context;
        this.list = list;
    }

    public void updateData( List<BlogBean> newData) {

        this.list = newData;
        notifyDataSetChanged(); // 或者使用更具体的通知方法，如notifyItemRangeInserted()
    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_home_gv, null);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        //获取指定位置的数据
        BlogBean blogBean = list.get(i);
        holder.titletv.setText(blogBean.getName());
        holder.nicknametv.setText(blogBean.getNickname());


        String content = new String( blogBean.getContent());
        if(content.equals("美丽景色欢迎您"))
        {
            holder.iv.setImageResource(R.drawable.great);
        }
        else{
            Bitmap bitmap=BitmapFactory.decodeFile(blogBean.getContent());
//            byte[] decode = Base64.decode(content, Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
            holder.iv.setImageBitmap(bitmap);

        }


//        if(content!="美丽景色欢迎您"){
//            byte[] decode = Base64.decode(content, Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
//            holder.iv.setImageBitmap(bitmap);
//
//        }



        return view;
    }


    //包含item中的控件声明和舒适化
    class ViewHolder{
        CircleImageView Cv;
        ImageView iv;
        TextView titletv;
        TextView nicknametv;
        public ViewHolder(View view){
            iv=view.findViewById(R.id.item_iv_blog);
            titletv=view.findViewById(R.id.item_tv_title);
            nicknametv=view.findViewById(R.id.item_blog_id);
            Cv=view.findViewById(R.id.item_blog_icon);
        }



    }
}
