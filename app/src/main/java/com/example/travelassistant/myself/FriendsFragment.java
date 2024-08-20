package com.example.travelassistant.myself;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.travelassistant.AddContactActivity;
import com.example.travelassistant.InviteActivity;
import com.example.travelassistant.R;
import com.example.travelassistant.message.activity.ChatActivity;
import com.example.travelassistant.message.model.Model;
import com.example.travelassistant.message.utils.Constant;
import com.example.travelassistant.message.utils.SpUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.constants.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.interfaces.OnItemClickListener;
import com.hyphenate.easeui.modules.contact.EaseContactListFragment;
import com.hyphenate.easeui.modules.contact.EaseContactListLayout;
import com.hyphenate.easeui.modules.contact.model.EaseContactCustomBean;
import com.hyphenate.easeui.modules.menu.EasePopupMenuHelper;
import com.hyphenate.easeui.widget.EaseSearchTextView;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.hyphenate.exceptions.HyphenateException;



public class FriendsFragment extends EaseContactListFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{
    private EaseSearchTextView tvSearch;
    private EaseContactListLayout contactList;//联系人列表控件
    private LocalBroadcastManager mLBM;


    private BroadcastReceiver ContactInviteChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //更新红点显示
            //.setVisibility(View.VISIBLE);
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);

        }
    };
    private BroadcastReceiver ContactChangeChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //刷新页面
        }
    };

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        //添加搜索布局和标题栏
        addSearchView();

        //添加头布局
        addHeader();

        //注册广播
        mLBM = LocalBroadcastManager.getInstance(getActivity());
        mLBM.registerReceiver(ContactInviteChangeReceiver, new IntentFilter(Constant.CONTACT_INVITE_CHANGED));
        //mLBM.registerReceiver(ContactChangeChangeReceiver,new IntentFilter(Constant.CONTACT_CHANGED));

        //从环信服务器获取所有联系人的信息(自带了 ，缺广播)
        //getContactFromHxServer();

        //绑定一下listView和contactMenu
        //registerForContextMenu();
    }

    @Override
    public void onMenuPreShow(EasePopupMenuHelper menuHelper, int position) {
        super.onMenuPreShow(menuHelper, position);
        menuHelper.addItemMenu(1, com.hyphenate.easeui.R.id.action_friend_delete, 1, getString(com.hyphenate.easeui.R.string.ease_friends_delete_the_contact));
    }

    @Override
    public boolean onMenuItemClick(MenuItem item, int position) {
        EaseUser user = contactLayout.getContactList().getItem(position);
        switch (item.getItemId()) {
            //执行删除选中的联系人操作
//            case com.hyphenate.easeui.R.id.action_friend_delete:
//                deleteContact(user);
//                return true;
            case 1000029:
                deleteContact(user);
                return true;
        }
        return super.onMenuItemClick(item, position);
    }
    //执行删除选中的联系人操作
    private void deleteContact(EaseUser user) {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().deleteContact(user.getUsername());

                    //本地数据库的更新
                    Model.getInstance().getDbManager().getContactTableDao().deleteContactByHxId(user.getUsername());

                    if (getActivity() == null) {
                        return;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //提示
                            Toast.makeText(getActivity(), "删除" + user.getUsername() + "成功", Toast.LENGTH_SHORT).show();

                            //刷新页面
                            //
                        }
                    });

                } catch (HyphenateException e) {
                    e.printStackTrace();

                    if (getActivity() == null) {
                        return;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "删除" + user.getUsername() + "失败", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
    @SuppressLint("ResourceType")
    private void addSearchView() {
        //添加搜索会话布局
        View searchView = LayoutInflater.from(mContext).inflate(R.layout.contract_search, null);
        llRoot.addView(searchView, 0);
        tvSearch = searchView.findViewById(R.id.tv_search);
        tvSearch.setHint("搜索");

        //获取控件
        EaseTitleBar titleBar = findViewById(R.id.fragment_title_bar);



        //设置标题
        //titleBar.setTitle("联系人列表");
        titleBar.setLeftImageResource(com.hyphenate.easeui.R.drawable.ease_mm_title_back);
        titleBar.setOnBackPressListener(new EaseTitleBar.OnBackPressListener() {
            @Override
            public void onBackPress(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });









        //添加右侧图标
        titleBar.setRightImageResource(R.drawable.em_contact_menu_add);
        //设置右侧菜单图标点击事件

        titleBar.setOnRightClickListener(new EaseTitleBar.OnRightClickListener() {
            @Override
            public void onRightClick(View view) {
                Intent intent = new Intent(getActivity(), AddContactActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addHeader() {
        contactLayout.getContactList().addCustomItem(R.id.contact_header_item_new_chat, R.drawable.em_friends_new_chat, "添加好友");
        contactLayout.getContactList().addCustomItem(R.id.contact_header_item_chat_room_list, R.drawable.em_friends_chat_room, "好友申请");
        contactLayout.getContactList().addCustomItem(R.id.contact_header_item_group_list, R.drawable.em_friends_group_chat, "群聊");

        /*View headView = View.inflate(getActivity(), R.layout.header_fragment_contact, null);
        llRoot.addView(headView,2);*/
    }

    @Override
    public void initListener() {
        super.initListener();
        //刷新联系人
        contactLayout.getSwipeRefreshLayout().setOnRefreshListener(this);
        tvSearch.setOnClickListener(this);
        contactLayout.getContactList().setOnCustomItemClickListener(new OnItemClickListener() {

            private Intent intent;

            @Override
            public void onItemClick(View view, int position) {
                EaseContactCustomBean item = contactLayout.getContactList().getCustomAdapter().getItem(position);
                switch (item.getId()) {
                    //添加好友
                    case R.id.contact_header_item_new_chat:

                        intent = new Intent(getActivity(), AddContactActivity.class);
                        startActivity(intent);
                        break;
                    //邀请信息条目的点击事件
                    case R.id.contact_header_item_chat_room_list:
                        //红点处理
                        //.setVisibility(View.GONE);
                        SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, false);

                        //跳转到邀请信息列表页面
                        intent = new Intent(getActivity(), InviteActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);
        EaseUser item = contactLayout.getContactList().getItem(position);

        Intent intent = new Intent(getActivity(), ChatActivity.class);

        //传递参数
        intent.putExtra(EaseConstant.EXTRA_CONVERSATION_ID, item.getUsername());

        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //关闭广播
        mLBM.unregisterReceiver(ContactInviteChangeReceiver);
        mLBM.unregisterReceiver(ContactChangeChangeReceiver);
    }
}