package com.example.travelassistant.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ShopListView extends ListView {

    public ShopListView(Context context) {
        super(context);
    }

    public ShopListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShopListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);//view的大小是用30位进行存储 给30位的最大值
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
