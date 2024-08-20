package com.example.travelassistant.message;



import static com.hyphenate.easeui.widget.EaseImageView.ShapeType.RECTANGLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.travelassistant.R;
import com.example.travelassistant.message.activity.ChatActivity;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.constants.EaseConstant;
import com.hyphenate.easeui.modules.conversation.EaseConversationListFragment;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationInfo;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.EaseTitleBar;


public class MessageFragment extends EaseConversationListFragment {
    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addSearchView();

        //设置默认头像
        conversationListLayout.setAvatarDefaultSrc(ContextCompat.getDrawable(mContext, com.hyphenate.easeui.R.drawable.toux));
        //设置头像样式：0为默认，1为圆形，2为方形(设置方形时，需要配合设置avatarRadius，默认的avatarRadius为50dp)
        conversationListLayout.setAvatarShapeType(RECTANGLE);
        //设置圆角半径
        conversationListLayout.setAvatarRadius((int) EaseCommonUtils.dip2px(mContext, 5));

    }
    private void addSearchView() {
        //添加搜索会话布局
        View searchView = LayoutInflater.from(mContext).inflate(R.layout.contract_search, null);
        llRoot.addView(searchView,0);

//        //获取控件
//        EaseTitleBar titleBar=findViewById(R.id.fragment_title_bar);
//
//        //设置标题
//        titleBar.setTitle("会话列表");
//
//        //添加右侧图标
//        titleBar.setRightImageResource(R.drawable.em_contact_menu_add);
    }

    /**
     * 逻辑：点击第position个条目触发该条目的监听器
     *
     * @param view
     * @param position 该参数是条目的位置，从上往下，从零开始计数
     */

    //设置条目的点击事件
    @Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);

        Intent intent = new Intent(getActivity(), ChatActivity.class);

        //获取到该条目的所有数据
        EaseConversationInfo conversationInfo = conversationListLayout.getItem(position);
        //拿到该条目的会话信息
        EMConversation conversation = (EMConversation) conversationInfo.getInfo();

        //传递参数   会话信息id =hxid
        intent.putExtra(EaseConstant.EXTRA_CONVERSATION_ID, conversation.conversationId());

        //会话类型,是否是群聊
        if (conversationInfo.isGroup()) {
            intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
        }

        startActivity(intent);
    }
}