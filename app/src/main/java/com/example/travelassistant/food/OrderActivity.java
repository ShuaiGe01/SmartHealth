package com.example.travelassistant.food;



        import android.app.Activity;
        import android.app.Dialog;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.ListView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

        import androidx.annotation.Nullable;


        import com.example.travelassistant.R;
        import com.example.travelassistant.food.adapter.OrderAdapter;
        import com.example.travelassistant.food.bean.FoodBean;

        import java.math.BigDecimal;
        import java.util.List;

public class OrderActivity extends Activity {
    private List<FoodBean> carFoodList;
    private BigDecimal money;
    private BigDecimal distributionCost;
    private TextView tv_title,tv_back,tv_distribution_cost,tv_total_cost,tv_cost,tv_payment;
    private RelativeLayout rl_title_bar;
    private ListView lv_order;
    private OrderAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        carFoodList = (List<FoodBean>) getIntent().getSerializableExtra("carFoodList");
        money=new BigDecimal(getIntent().getStringExtra("totalMoney"));
        distributionCost=new BigDecimal(getIntent().getStringExtra("distributionCost"));
        initView();
        setData();

    }
    private void initView(){
        tv_title=findViewById(R.id.tv_title);
        tv_title.setText("订单");
        rl_title_bar=findViewById(R.id.rl_title_bar);
        rl_title_bar.setBackgroundColor(getResources().getColor(R.color.blue_color));
        //tv_back=findViewById(R.id.tv_back)
        lv_order=findViewById(R.id.lv_order);
        tv_distribution_cost=findViewById(R.id.tv_distribution_cost);
        tv_total_cost=findViewById(R.id.tv_total_cost);
        tv_cost=findViewById(R.id.tv_cost);
        tv_payment=findViewById(R.id.tv_payment);
//        tv_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
        tv_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(OrderActivity.this,R.style.Dialog_Style);
                dialog.setContentView(R.layout.qr_code);
                dialog.show();
            }
        });




    }


    private void setData(){
        adapter=new OrderAdapter(this);
        lv_order.setAdapter(adapter);
        adapter.setData(carFoodList);
        tv_cost.setText("￥"+money);
        tv_distribution_cost.setText("￥"+distributionCost);
        tv_total_cost.setText("￥"+(money.add(distributionCost)));
    }

}
