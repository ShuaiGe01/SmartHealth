package com.example.travelassistant.myself;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelassistant.LoginActivity;
import com.example.travelassistant.R;
import com.example.travelassistant.db.BlogBean;
import com.example.travelassistant.db.DBManager;
import com.example.travelassistant.food.FoodActivity;
import com.example.travelassistant.home.HomeGvAdapter;
import com.example.travelassistant.message.model.Model;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.util.List;


public class MyselfFragment extends Fragment implements View.OnClickListener{
    private Button bt_setting_logout;

    String currentUser;
    private TextView nickName_view,cacheTv,versionTv,shareTv;
    private TextView friends_act,my_work;
    private GridView Gv_work;
    private int down=0;
    private List<BlogBean> mdatas;
    private HomeGvAdapter homeGvAdapter;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what==1){
                String currentUser = EMClient.getInstance().getCurrentUser();
                mdatas= DBManager.queryBlogformtb(currentUser);
                if(mdatas.size()>0){
                    homeGvAdapter = new HomeGvAdapter(getContext(), mdatas);
                    Gv_work.setAdapter(homeGvAdapter);
                    handler.sendEmptyMessageDelayed(1,5000);
                }

            }
        }
    };


    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_myself, null);

        initView(view);
        initData();


        return view;
    }

    private void initView(View view) {

        nickName_view = view.findViewById(R.id.tv_nickName);
        bt_setting_logout = view.findViewById(R.id.bt_setting_logout);
        cacheTv=view.findViewById(R.id.more_tv_cache);
        versionTv=view.findViewById(R.id.more_tv_version);
        shareTv=view.findViewById(R.id.more_tv_share);
        friends_act=view.findViewById(R.id.tv_friend);
        my_work = view.findViewById(R.id.more_tv_mywork);
        Gv_work=view.findViewById(R.id.myself_frag_gv);
        Gv_work.setVisibility(View.GONE);


        cacheTv.setOnClickListener(this);
        shareTv.setOnClickListener(this);
        friends_act.setOnClickListener(this);
        my_work.setOnClickListener(this);

        String versionName=getVersionName();
        versionTv.setText("当前版本：V"+versionName);

    }
    private String getVersionName() {
//        获取应用的版本名称
        PackageManager manager=getActivity().getPackageManager();
        String versionName=null;
        try {
            PackageInfo packageInfo = manager.getPackageInfo(getActivity().getPackageName(), 0);
            versionName=packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }




    @SuppressLint("SetTextI18n")
    private void initData() {
        //在button上显示当前用户名称
        //bt_setting_logout.setText("退出登录("+ EMClient.getInstance().getCurrentUser()+")");
        //在TextView上显示当前用户名称
         currentUser = EMClient.getInstance().getCurrentUser();
        handler.sendEmptyMessageDelayed(1,5000);
        mdatas= DBManager.queryBlogformtb(currentUser);
        if(mdatas.size()>0){
            homeGvAdapter = new HomeGvAdapter(getContext(), mdatas);
            Gv_work.setAdapter(homeGvAdapter);
        }

        System.out.println("当前用户"+currentUser);
        nickName_view.setText("账号：" +currentUser);

        //退出登录的逻辑处理
        bt_setting_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        //登录环信服务器退出登录
                        EMClient.getInstance().logout(false, new EMCallBack() {
                            @Override
                            public void onSuccess() {

                                //关闭DBHelper
                                //Model.getInstance().getDbManager().close();

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //更新ui显示
                                        Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_SHORT).show();

                                        //回到登录页面
                                        Intent intent = new Intent(getActivity(), LoginActivity.class);

                                        startActivity(intent);


                                    }
                                });
                            }

                            @Override
                            public void onError(int code, String error) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), "退出失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onProgress(int progress, String status) {

                            }
                        });
                    }
                });
            }
        });
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.more_tv_cache:
//                清除缓存=删除数据库当中的信息
                //clearcache();
                break;
            case R.id.more_tv_share:
                shareSoftwareMsg("快来下载吧");
                break;
            case R.id.tv_friend:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frag_myself,new FriendsFragment())
                        .addToBackStack(null)
                        .commit();

                break;
            case R.id.more_tv_mywork:
                if(down == 0) {
                    my_work.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.right), null, null, null);
                    down=1;
                    mdatas= DBManager.queryBlogformtb(currentUser);
                    if(mdatas.size()>0){
                        homeGvAdapter = new HomeGvAdapter(getContext(), mdatas);
                        Gv_work.setAdapter(homeGvAdapter);
                    }
                    Gv_work.setVisibility(View.VISIBLE);
                }
                else
                {
                    my_work.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.down), null, null, null);
                    down=0;
                    Gv_work.setVisibility(View.GONE);
                }
                break;

        }

    }
    private void shareSoftwareMsg(String s) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,s);
        startActivity(Intent.createChooser(intent,"旅小布APP"));
    }
    @Override
    public void onSaveInstanceState(Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.clear();
    }

    //    private void clearcache() {
////        清除缓存的函数
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("提示信息").setMessage("确定要清除所有缓存吗？");
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                DBManager.deleteAllInfo();
//                Toast.makeText(MoreActivity.this,"已清除全部缓存！",Toast.LENGTH_SHORT).show();
////                跳转到主界面
//                Intent intent = new Intent(MoreActivity.this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//        }).setNegativeButton("取消",null);
//        builder.create().show();
//
//
//
//    }
}