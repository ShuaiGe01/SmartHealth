package com.example.travelassistant.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.travelassistant.IntroActivity;
import com.example.travelassistant.db.DBManager;
import com.example.travelassistant.food.FoodActivity;
import com.example.travelassistant.R;
import com.example.travelassistant.db.BlogBean;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener{
    private SwipeRefreshLayout swipeRefreshLayout;
    ViewPager homeVp;
    GridView homeGv;
    LinearLayout pointLayout;
    TextView foodtv,movietv,hoteltv,beautytv;
    private List<BlogBean> list;
    List<BlogBean> info;




    //声明图片数组
    int[]imgIds={R.mipmap.ppt1,R.mipmap.ppt2};
    List<ImageView>ivList;
    //声明管理指示器小圆点的集合
    List<ImageView>pointList;
    private HomePagerAdapter homePagerAdapter;



    //完成定时装置 实现自动滑动的效果
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what==1) {
//                获取当前viewpager显示的页面
                int currentItem = homeVp.getCurrentItem();

                //判断是否为最后一张 ，如果是最后一张 回到第一张
                if(currentItem==ivList.size()-1){
                    homeVp.setCurrentItem(0);
                }else{
                    currentItem++;
                    homeVp.setCurrentItem(1);
                }
                //形成循环--在接收消息的同时也要进行消息发送
                handler.sendEmptyMessageDelayed(1,5000);
            }

        }
    };
    private HomeGvAdapter homeGvAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);

        /*
        *
        * 获取主界面传来的数据
        * */
        initPager();
        setVPListener();
        //延迟多长时间发送一个编号为1 的 消息 通知可以切换viewpager的图片了
        handler.sendEmptyMessageDelayed(1,5000);
        Bundle bundle = getArguments();

        info=(List<BlogBean>) bundle.getSerializable("info");
        list=new ArrayList<>();
        //创建适配器
        homeGvAdapter = new HomeGvAdapter(getContext(), list);
        homeGv.setAdapter(homeGvAdapter);
        addDataTolist();


        setGVListener();


        return view;
    }

    private void addDataTolist() {
        for(int i=0;i<info.size();i++){
            list.add(info.get(i));
        }
        homeGvAdapter.notifyDataSetChanged();

    }

    private void setGVListener() {
        homeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BlogBean blogBean = list.get(i);
                Intent intent=new Intent(getContext(), IntroActivity.class);
                intent.putExtra("blog",blogBean);
                startActivity(intent);
            }
        });
    }

    //设置viewpager的监听器函数
    private void setVPListener() {
        homeVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                for(int i=0;i<pointList.size();i++)
                {
                    pointList.get(i).setImageResource(R.mipmap.point_normal);
                }
                pointList.get(position).setImageResource(R.mipmap.point_focus);

            }
        });
    }

    //设置viewPager显示的页面
    private void initPager() {
        ivList=new ArrayList<>();
        pointList=new ArrayList<>();
        for(int i=0;i<imgIds.length;i++){
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(imgIds[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //设置图片view的宽高
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(layoutParams);
            //将图片view加载到集合当中
            ivList.add(imageView);
            //创建图片对应的指示器小圆点
            ImageView piv = new ImageView(getContext());
            piv.setImageResource(R.mipmap.point_normal);
            LinearLayout.LayoutParams plp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            plp.setMargins(20,0,0,0);
            piv.setLayoutParams(plp);
            //将小圆点添加到布局当中
            pointLayout.addView(piv);
            pointList.add(piv);

        }
        //默认第一个小圆点是获取焦点的状态
        pointList.get(0).setImageResource(R.mipmap.point_focus);
        homePagerAdapter = new HomePagerAdapter(getContext(), ivList);
        homeVp.setAdapter(homePagerAdapter);
    }

    //初始化控件的操作
    private void initView(View view) {
        homeVp=view.findViewById(R.id.homefrag_vp_show);
        homeGv=view.findViewById(R.id.homefrag_gv);
        pointLayout=view.findViewById(R.id.homefrag_layout);

        beautytv=view.findViewById(R.id.frag_tv_beauty);
        foodtv=view.findViewById(R.id.frag_tv_food);
        hoteltv=view.findViewById(R.id.frag_tv_hotel);
        movietv=view.findViewById(R.id.frag_tv_movie);

        swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeResources(R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                resetData();



            }
        });
        //添加点击监听事件
        foodtv.setOnClickListener(this);
        hoteltv.setOnClickListener(this);
        movietv.setOnClickListener(this);
        beautytv.setOnClickListener(this);


    }

    private void resetData() {
        List<BlogBean> list=DBManager.queryAll();
        homeGvAdapter.updateData(list);
        homeGv.setAdapter(homeGvAdapter);
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onSaveInstanceState(Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.clear();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.frag_tv_beauty:
                break;
            case R.id.frag_tv_food:

                Intent intent=new Intent(getContext(),FoodActivity.class);
                startActivity(intent);
                break;
            case R.id.frag_tv_movie:
                break;
            case R.id.frag_tv_hotel:
                break;
        }
    }



}