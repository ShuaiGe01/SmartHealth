package com.example.travelassistant.message.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.travelassistant.R;
import com.hyphenate.easeui.constants.EaseConstant;
import com.hyphenate.easeui.modules.chat.EaseChatFragment;
import com.hyphenate.easeui.widget.EaseTitleBar;

public class ChatActivity extends FragmentActivity {

    private String mHxid;
    private EaseChatFragment chatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initData();

        initView();
    }

    private void initView() {
        //设置标题栏
        EaseTitleBar titleBar =findViewById(R.id.chat_title_bar);
        //设置标题为当前点击的用户
        Intent intent=getIntent();
        titleBar.setTitle(intent.getStringExtra(EaseConstant.EXTRA_CONVERSATION_ID));
        //设置右侧图标
        titleBar.setRightImageResource(R.drawable.chat_user_info);
        //设置左侧图标
        titleBar.setLeftImageResource(com.hyphenate.easeui.R.drawable.ease_mm_title_back);

    }

    private void initData() {
        //创建一个会话的Fragment
        chatFragment = new EaseChatFragment();

        mHxid = getIntent().getStringExtra(EaseConstant.EXTRA_CONVERSATION_ID);

        chatFragment.setArguments(getIntent().getExtras());

        //替换fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_chat, chatFragment).commit();

       /* //获取到聊天列表控件
        EaseChatMessageListLayout messageListLayout= chatFragment.chatLayout.getChatMessageListLayout();
        //设置聊天背景
        messageListLayout.setBackground(new ColorDrawable(Color.parseColor("#DA5A4D")));*/
    }
}