package com.example.travelassistant.food;
        import android.app.Activity;
        import android.app.Dialog;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.FragmentActivity;

        import com.example.travelassistant.R;
        import com.example.travelassistant.food.adapter.CarAdapter;
        import com.example.travelassistant.food.adapter.MenuAdapter;
        import com.example.travelassistant.food.bean.FoodBean;
        import com.example.travelassistant.food.bean.ShopBean;
        import com.example.travelassistant.food.utils.AssetsUtils;

        import java.io.Serializable;
        import java.math.BigDecimal;
        import java.util.ArrayList;
        import java.util.Iterator;
        import java.util.List;
        import java.util.Map;

public class ShopDetailActivity extends Activity implements View.OnClickListener{

    private TextView tvShopName,tvTime,tvNotice,tvTitle,tv_back,tv_settle_accounts,tv_count,tv_money,
            tv_distribution_cost,tv_not_enough,tv_clear;
    private ImageView ivShopPic,ivBack,iv_shop_car;
    private ListView lvList,lv_car;
    public static final int MSG_count_OK=1;
    private MHandler mHandler;
    private CarAdapter carAdapter;
    private RelativeLayout rl_car_list;
    private int totalCount=0;
    private BigDecimal totalMoney;
    private MenuAdapter adapter;
    private ShopBean bean;
    private List<FoodBean> carFoodList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定布局文件 把布局文件当做activity的界面
        setContentView(R.layout.activity_shop_detail);

        bean= (ShopBean) getIntent().getSerializableExtra("shop");
        if(bean==null)
        {
            return;
        }

        mHandler=new MHandler();
        totalMoney=new BigDecimal(0.0);
        carFoodList=new ArrayList<>();
        initView ();
        initAdapter();
        setData();
    }

    private void initAdapter() {
        carAdapter=new CarAdapter(this, new CarAdapter.OnSelectListener() {
            @Override
            public void onSelectAdd(int position, TextView tv_food_price, TextView tv_food_count) {
                FoodBean bean=carFoodList.get(position);
                tv_food_count.setText(bean.getCount()+1+"");
                BigDecimal count=BigDecimal.valueOf(bean.getCount()+1);
                tv_food_price.setText("￥"+bean.getPrice().multiply(count));
                bean.setCount(bean.getCount()+1);
                Iterator<FoodBean> iterator=carFoodList.iterator();
                while(iterator.hasNext())
                {
                    FoodBean food=iterator.next();
                    if(food.getFoodId()==bean.getFoodId()){
                        iterator.remove();
                    }
                }
                carFoodList.add(position,bean);
                totalCount=totalCount+1;
                totalMoney=totalMoney.add(bean.getPrice());
                carDataMsg();
            }
            @Override
            public void onSelectMis(int position, TextView tv_food_price, TextView tv_food_count) {
                FoodBean bean=carFoodList.get(position);
                tv_food_count.setText(bean.getCount()-1+"");
                BigDecimal count=BigDecimal.valueOf(bean.getCount()-1);
                tv_food_price.setText("￥"+bean.getPrice().multiply(count));
                minusCarData(bean,position);
            }
        });
        adapter=new MenuAdapter(this, new MenuAdapter.OnSelectListener() {
            @Override
            public void onselectAddCar(int position) {
                FoodBean fb=bean.getFoodList().get(position);
                fb.setCount(fb.getCount()+1);
                Iterator<FoodBean> iterator=carFoodList.iterator();
                while(iterator.hasNext()){
                    FoodBean food=iterator.next();
                    if(food.getFoodId()==fb.getFoodId())
                    {
                        iterator.remove();
                    }

                }
                carFoodList.add(fb);
                totalCount=totalCount+1;
                totalMoney=totalMoney.add(fb.getPrice());
                carDataMsg();

            }
        });
        lvList.setAdapter(adapter);

    }
    private void carDataMsg(){
        Message msg=Message.obtain();
        msg.what=MSG_count_OK;
        Bundle bundle=new Bundle();
        bundle.putString("totalCount",totalCount+"");
        bundle.putString("totalMoney",totalMoney+"");
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }



    private void minusCarData(FoodBean bean,int position)
    {
        int count=bean.getCount()-1;
        bean.setCount(count);
        Iterator<FoodBean> iterator=carFoodList.iterator();
        while(iterator.hasNext())
        {
            FoodBean food=iterator.next();
            if(food.getFoodId()==bean.getFoodId())
            {
                iterator.remove();
            }
        }
        if(count>0) carFoodList.add(position,bean);
        else carAdapter.notifyDataSetChanged();
        totalCount=totalCount-1;
        totalMoney=totalMoney.subtract(bean.getPrice());
        carDataMsg();


    }



    class MHandler extends Handler {
        @Override
        public void dispatchMessage(@NonNull Message msg) {
            super.dispatchMessage(msg);
            switch(msg.what){
                case MSG_count_OK:
                    Bundle bundle=msg.getData();
                    String count=bundle.getString("totalCount","");
                    String money=bundle.getString("totalMoney","");
                    if(bundle!=null)
                    {
                        if(Integer.parseInt(count)>0){
                            iv_shop_car.setImageResource(R.drawable.shop_car);
                            tv_count.setVisibility(View.VISIBLE);
                            tv_money.setTextColor(Color.parseColor("#ffffff"));
                            tv_money.getPaint().setFakeBoldText(true);
                            tv_money.setText("￥"+money);
                            tv_count.setText(count);
                            tv_distribution_cost.setText("另需配送费￥"+bean.getDistributionCost());
                            BigDecimal bdm=new BigDecimal(money);
                            int result=bdm.compareTo(bean.getOfferPrice());
                            if(-1==result)
                            {
                                tv_settle_accounts.setVisibility(View.GONE);
                                tv_not_enough.setVisibility(View.VISIBLE);
                                BigDecimal m=bean.getOfferPrice().subtract(bdm);
                                tv_not_enough.setText("还差￥"+m+"起送");
                            }else{
                                tv_settle_accounts.setVisibility(View.VISIBLE);
                                tv_not_enough.setVisibility(View.GONE);

                            }
                        }
                        else
                        {
                            if(rl_car_list.getVisibility()==View.VISIBLE)
                            {
                                rl_car_list.setVisibility(View.GONE);

                            }else{
                                rl_car_list.setVisibility(View.VISIBLE);
                            }
                            iv_shop_car.setImageResource(R.drawable.shop_car_empty);
                            tv_settle_accounts.setVisibility(View.GONE);
                            tv_not_enough.setVisibility(View.VISIBLE);
                            tv_not_enough.setText("￥"+bean.getOfferPrice()+"起送");
                            tv_count.setVisibility(View.GONE);
                            tv_distribution_cost.setVisibility(View.GONE);
                            tv_money.setTextColor(getResources().getColor(R.color.light_gray));
                            tv_money.setText("未选购商品");
                        }
                    }

            }
        }
    }





    private void initView()
    {
        tvShopName=findViewById(R.id.tv_shop_name);
        tvTime=findViewById(R.id.tv_time);
        tvNotice=findViewById(R.id.tv_notice);
        tvTitle=findViewById(R.id.tv_title);
        ivShopPic=findViewById(R.id.iv_shop_pic);
        ivBack=findViewById(R.id.iv_back);
        tvTitle.setText("店铺详情");
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(this);
        lvList=findViewById(R.id.lv_list);

        tv_settle_accounts=findViewById(R.id.tv_settle_accounts);
        tv_distribution_cost=findViewById(R.id.tv_distribution_cost);
        tv_count=findViewById(R.id.tv_count);
        iv_shop_car=findViewById(R.id.iv_shop_car);
        tv_money=findViewById(R.id.tv_money);
        tv_not_enough=findViewById(R.id.tv_not_enough);
        tv_clear=findViewById(R.id.tv_clear);
        lv_car=findViewById(R.id.lv_car);
        rl_car_list=findViewById(R.id.rl_car_list);
        rl_car_list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(rl_car_list.getVisibility()==View.VISIBLE)
                {
                    rl_car_list.setVisibility(View.GONE);
                }
                return false;
            }
        });

        //tv_back.setOnClickListener(this);
        tv_settle_accounts.setOnClickListener(this);
        iv_shop_car.setOnClickListener(this);
        tv_clear.setOnClickListener(this);

    }
    private void setData(){

        tvShopName.setText(bean.getShopName());
        tvTime.setText(bean.getTime());
        tvNotice.setText(bean.getShopNotice());
        String shopName = bean.getShopName();
        Map<String, Bitmap> logoImgMap = AssetsUtils.getLogoImgMap();
        Bitmap bitmap = logoImgMap.get(shopName);
        ivShopPic.setImageBitmap(bitmap);
//        String s=bean.getShopPic();
//        String st=s.substring(21);
//        String str="http://10.114.30.98"+st;
//        Glide.with(this).load(str).error(R.mipmap.ic_launcher).into(ivShopPic);
        adapter.setData(bean.getFoodList());

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.iv_back:
                finish();//当前页面结束
                break;
            case R.id.tv_settle_accounts:
                if(totalCount>0)
                {
                    Intent intent=new Intent(ShopDetailActivity.this,OrderActivity.class);
                    intent.putExtra("carFoodList",(Serializable) carFoodList);
                    intent.putExtra("totalMoney",totalMoney+"");
                    intent.putExtra("distributionCost",bean.getDistributionCost()+"");
                    startActivity(intent);
                }
                break;
            case R.id.iv_shop_car:
                if(totalCount<=0)
                    return;
                if(rl_car_list!=null)
                {
                    if(rl_car_list.getVisibility()==View.VISIBLE)
                    {
                        rl_car_list.setVisibility(View.GONE);
                    }else
                    {
                        rl_car_list.setVisibility(View.VISIBLE);
                        Animation animation= AnimationUtils.loadAnimation(ShopDetailActivity.this,R.anim.slide_bottom_to_top);
                        rl_car_list.startAnimation(animation);
                    }

                }
                carAdapter.setData(carFoodList);
                lv_car.setAdapter(carAdapter);
                break;
            case R.id.tv_clear:
                dialogClear();
                break;
        }

    }

    private void dialogClear() {
        final Dialog dialog=new Dialog(ShopDetailActivity.this,R.style.Dialog_Style);
        dialog.setContentView(R.layout.dialog_clear);
        dialog.show();
        TextView tv_clear=dialog.findViewById(R.id.tv_clear);
        TextView tv_cancel=dialog.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(carFoodList==null) return;
                for(FoodBean bean:carFoodList){
                    bean.setCount(0);
                }
                carFoodList.clear();
                carAdapter.notifyDataSetChanged();
                totalCount=0;
                totalMoney=BigDecimal.valueOf(0.0);
                carDataMsg();
                dialog.dismiss();
            }
        });


    }
//        TextView textView=new TextView(this);
//        textView.setText(bean.getShopName());
//        textView.setTextSize(50);
//        setContentView(textView);
}
