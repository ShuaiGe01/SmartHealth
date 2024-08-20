package com.example.travelassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.travelassistant.Service.ServiceFragment;
import com.example.travelassistant.db.BlogBean;
import com.example.travelassistant.db.DBManager;
import com.example.travelassistant.home.HomeFragment;
import com.example.travelassistant.message.MessageFragment;
import com.example.travelassistant.myself.MyselfFragment;
import com.example.travelassistant.submit.SubmitFragment;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    RadioGroup mainRg;
    //声明四个按钮对应的Fragment对象
    Fragment homeFrag,messageFrag,serviceFrag,submitFrag,myselfFrag;
    private FragmentManager manager;
    TextView titletv;
    private List<BlogBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainRg=findViewById(R.id.main_rg);
        titletv=findViewById(R.id.main_tv_title);
        mainRg.setOnCheckedChangeListener(this);


        homeFrag=new HomeFragment();
        messageFrag=new MessageFragment();
        serviceFrag=new ServiceFragment();
        submitFrag=new SubmitFragment();
        myselfFrag=new MyselfFragment();
        //将四个Fragment进行动态加载，一起加载到布局当中
        addFragment();

        list = DBManager.queryAll();
        if(list.size()==0)
        {
            DBManager.addBlogInfo(this);
            list=DBManager.queryAll();

        }


        Bundle bundle = new Bundle();
        bundle.putSerializable("info", (Serializable) list);
        homeFrag.setArguments(bundle);
    }

    /*
    *
    * 将主页当中的碎片一起加载进入布局 有用的显示 暂时无用的隐藏*/
    private void addFragment() {
//        1.创建碎片管理者对象
        manager = getSupportFragmentManager();
//        2.创建碎片处理事务的对象
        FragmentTransaction transaction = manager.beginTransaction();
//        3.将5个fragment添加到布局
        transaction.add(R.id.main_layout_center,homeFrag);
        transaction.add(R.id.main_layout_center,messageFrag);
        transaction.add(R.id.main_layout_center,serviceFrag);
        transaction.add(R.id.main_layout_center,submitFrag);
        transaction.add(R.id.main_layout_center,myselfFrag);
        //4.隐藏后面的4个显示第一个
        transaction.hide(messageFrag);
        transaction.hide(serviceFrag);
        transaction.hide(submitFrag);
        transaction.hide(myselfFrag);
//        5.提交碎片改变后的事务
        transaction.commit();
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        FragmentTransaction transaction = manager.beginTransaction();
        switch (i){
            case R.id.main_rb_homepage:
                titletv.setText("首 页");
                transaction.hide(messageFrag);
                transaction.hide(serviceFrag);
                transaction.hide(submitFrag);
                transaction.hide(myselfFrag);

                transaction.show(homeFrag);

                list = DBManager.queryAll();
                Bundle bundle = new Bundle();
                bundle.putSerializable("info", (Serializable) list);
                homeFrag.setArguments(bundle);





                break;
            case R.id.main_rb_message:
                titletv.setText("消 息");
                transaction.show(messageFrag);
                transaction.hide(serviceFrag);
                transaction.hide(submitFrag);
                transaction.hide(myselfFrag);

                transaction.hide(homeFrag);



                break;
            case R.id.main_rb_service:
                titletv.setText("远程互联");
                transaction.hide(messageFrag);
                transaction.show(serviceFrag);
                transaction.hide(submitFrag);
                transaction.hide(myselfFrag);

                transaction.hide(homeFrag);

                break;
            case R.id.main_rb_submit:
                titletv.setText("发 帖");
                transaction.hide(messageFrag);
                transaction.hide(serviceFrag);
                transaction.show(submitFrag);
                transaction.hide(myselfFrag);

                transaction.hide(homeFrag);

                break;
            case R.id.main_rb_myself:
                titletv.setText("个人中心");
                transaction.hide(messageFrag);
                transaction.hide(serviceFrag);
                transaction.hide(submitFrag);
                transaction.show(myselfFrag);

                transaction.hide(homeFrag);

                break;
        }
        transaction.commit();

    }
}