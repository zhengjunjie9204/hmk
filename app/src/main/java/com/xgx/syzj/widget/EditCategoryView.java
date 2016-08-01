package com.xgx.syzj.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xgx.syzj.R;

/**
 * 编辑和删除商品类型控件
 *
 * @author zajo
 * @created 2015年10月20日 14:34
 */
public class EditCategoryView extends LinearLayout implements View.OnClickListener {

    private Context mContext;
    private View contentView;

    private EditText et;
    private ImageView ed,de;

    private IEditDeleteListener listener;

    public void setListener(IEditDeleteListener listener) {
        this.listener = listener;
    }

    public EditCategoryView(Context context) {
        this(context, null);
    }

    public EditCategoryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditCategoryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView(){
        contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_goods_type_delete_item,this,true);
        et = (EditText)contentView.findViewById(R.id.et);
        ed = (ImageView)contentView.findViewById(R.id.ed);
        de = (ImageView)contentView.findViewById(R.id.de);

        ed.setOnClickListener(this);
        de.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ed:
                if (listener != null)
                    listener.onEditClick(this);
                break;
            case R.id.de:
                if (listener != null)
                    listener.onDeleteClick(this);
                break;
        }
    }

    public void setEditEnabled(boolean enabled){
        this.et.setEnabled(enabled);
        this.et.setFocusable(true);
        this.et.setFocusableInTouchMode(true);
        this.et.requestFocus();
    }

    public String getEditText(){
        return this.et.getText().toString().trim();
    }

    public void setEditText(String text){
        this.et.setText(text);
    }

    public interface IEditDeleteListener {
        void onEditClick(View view);
        void onDeleteClick(View view);
    }
}
