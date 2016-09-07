package com.xgx.syzj.bean;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xgx.syzj.R;


/**
 * @author zajo
 * @created 2015年09月22日 17:52
 */
public class TextItemView extends LinearLayout {

    private Context mContext;
    private View contentView;

    private ImageView iv_icon;
    private TextView tv_title;
    private TextView tv_desc;
    private ImageView right_arrow;

    public TextItemView(Context context) {
        this(context, null);
    }

    public TextItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        getAttr(attrs);
    }

    private void getAttr(AttributeSet attrs){
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs,
                R.styleable.TextItemView);

        String titleText = typedArray.getString(R.styleable.TextItemView_titleTexts);
        String descText = typedArray.getString(R.styleable.TextItemView_descTexts);
        int titleColor = typedArray.getColor(R.styleable.TextItemView_titleColor, getResources().getColor(R.color.gray));
        int descColor = typedArray.getColor(R.styleable.TextItemView_descColor, getResources().getColor(R.color.gray));
        int bgResource = typedArray.getResourceId(R.styleable.TextItemView_bgResource, R.drawable.custom_text_item_bg);
        boolean arrow = typedArray.getBoolean(R.styleable.TextItemView_rightArrow, false);
        int leftIcon = typedArray.getResourceId(R.styleable.TextItemView_iconLeft, 0);
        int titleMarginLeft = typedArray.getDimensionPixelOffset(R.styleable.TextItemView_titleMarginLeft, getResources().getDimensionPixelOffset(R.dimen.TextItemView_titleMarginLeft));

        typedArray.recycle();

        setTitle(titleText);
        setDesc(descText);
        tv_title.setTextColor(titleColor);
        LayoutParams params = (LayoutParams) tv_title.getLayoutParams();
        params.setMargins(titleMarginLeft, 0, 0, 0);// 通过自定义坐标来放置你的控件left, top, right, bottom
        tv_title .setLayoutParams(params);//
        tv_desc.setTextColor(descColor);
        right_arrow.setVisibility(arrow ? View.VISIBLE : View.GONE);
        if (leftIcon == 0) iv_icon.setVisibility(View.GONE);
        else iv_icon.setBackgroundResource(leftIcon);
        setBackgroundResource(bgResource);
    }

    private void initView(){
        contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_text_item_view,this,true);
        tv_title = (TextView)contentView.findViewById(R.id.tv_title);
        tv_desc = (TextView)contentView.findViewById(R.id.tv_desc);
        iv_icon = (ImageView)contentView.findViewById(R.id.iv_icon);
        right_arrow = (ImageView)contentView.findViewById(R.id.right_arrow);

        setClickable(true);
    }

    public void setTextSize(float size){
        tv_desc.setTextSize(size);
        tv_title.setTextSize(size);
    }

    public String getTitle() {
        return tv_title.getText().toString();
    }

    public void setTitle(String title) {
        this.tv_title.setText(title);
    }

    public void setDesc(String desc) {
        this.tv_desc.setVisibility(View.VISIBLE);
        this.tv_desc.setText(desc);
    }
}
