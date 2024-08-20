package com.example.travelassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelassistant.db.BlogBean;
import com.example.travelassistant.message.model.Model;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.io.Serializable;

import de.hdodenhof.circleimageview.CircleImageView;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView backiv,introiv,attentiontv;
    CircleImageView iconiv;
    TextView titletv,idtv,introtv;
    BlogBean blogBean;
    int att=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        Intent intent = getIntent();
        blogBean = (BlogBean) intent.getSerializableExtra("blog");
        initView();
        initDatas(blogBean);
    }

    private void initDatas(BlogBean blogBean) {
        idtv.setText(blogBean.getNickname());
        introtv.setText(blogBean.getIntroduce());
        titletv.setText(blogBean.getName());


        String content = new String( blogBean.getContent());
        if(content.equals("美丽景色欢迎您"))
        {
            introiv.setImageResource(R.drawable.great);
        }
        else{
//            byte[] decode = Base64.decode(content, Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
//            introiv.setImageBitmap(bitmap);
            Bitmap bitmap=BitmapFactory.decodeFile(blogBean.getContent());
            introiv.setImageBitmap(bitmap);

        }


    }

    //初始化控件
    private void initView() {
        titletv= findViewById(R.id.intro_tv_title);
        idtv= findViewById(R.id.intro_blog_id);
        introtv= findViewById(R.id.intro_tv_intro);
        backiv= findViewById(R.id.intro_iv_back);
        introiv= findViewById(R.id.intro_blog_iv);
        attentiontv= findViewById(R.id.tv_attention);
        iconiv=findViewById(R.id.intro_blog_icon);

        backiv.setOnClickListener(this);
        attentiontv.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.intro_iv_back:
                finish();
                break;
            case R.id.tv_attention:
                if(att==0){
                    attentiontv.setImageResource(R.drawable.attentioned);
                    Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                        @Override
                        public void run() {
                            //去环信服务器添加好友
                            try {
                                EMClient.getInstance().contactManager().addContact(blogBean.getNum(), "添加好友");

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(IntroActivity.this, "发送添加好友邀请成功", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(IntroActivity.this, "发送添加好友邀请失败" + e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });

                    att=1;

                }
                else{
                    attentiontv.setImageResource(R.drawable.attention);
                    att=0;

                }

        }

    }
}