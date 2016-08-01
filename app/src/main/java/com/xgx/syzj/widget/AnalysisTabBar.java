package com.xgx.syzj.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xgx.syzj.R;

/**
 * @author zajo
 * @created 2015年09月29日 17:02
 */
public class AnalysisTabBar extends LinearLayout {

    private Context mContext;
    private View contentView;

    private TextView title;
    private View line;

    public AnalysisTabBar(Context context) {
        this(context, null);
    }

    public AnalysisTabBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnalysisTabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        getAttr(attrs);
    }

    private void getAttr(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs,
                R.styleable.AnalysisTabBar);
        String text = typedArray.getString(R.styleable.AnalysisTabBar_atbText);
        int textColor = typedArray.getColor(R.styleable.AnalysisTabBar_atbTextColor, getResources().getColor(R.color.gray));
        int lineColor = typedArray.getColor(R.styleable.AnalysisTabBar_atbLineColor, getResources().getColor(R.color.gray));

        typedArray.recycle();

        title.setText(text);
        title.setTextColor(textColor);
        line.setBackgroundColor(lineColor);
    }

    private void initView() {
        contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_analysis_bar, this, true);
        title = (TextView) contentView.findViewById(R.id.tv_title);
        line = contentView.findViewById(R.id.line);
        setClickable(true);
    }

    /**
     * 设置前景色
     * @param color
     */
    public void setStateColor(int color){
        title.setTextColor(getResources().getColor(color));
        line.setBackgroundColor(getResources().getColor(color));
    }
}
