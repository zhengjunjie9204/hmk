package com.xgx.syzj.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xgx.syzj.R;

/**
 * @author zajo
 * @created 2015年10月09日 14:22
 */
public class UploadPictureView extends RelativeLayout {

    private Context mContext;
    private View contentView;

    private ImageView iv_pic, iv_add,iv_del;
    private UploadPictureDeleteListener deleteListener;

    public UploadPictureView(Context context) {
        this(context, null);
    }

    public UploadPictureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UploadPictureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        getAttr(attrs);
    }

    private void initView() {
        contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_upload_picture, this, true);
        iv_pic = (ImageView) contentView.findViewById(R.id.iv_pic);
        iv_add = (ImageView) contentView.findViewById(R.id.iv_add);
        iv_del = (ImageView) contentView.findViewById(R.id.iv_del);
        iv_del.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteListener != null){
                    deleteListener.onPicDeleteClick(UploadPictureView.this);
                }
            }
        });
    }
    private void getAttr(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs,
                R.styleable.UploadPictureView);
        int addBg = typedArray.getResourceId(R.styleable.UploadPictureView_addBackgroup, R.mipmap.add_picture);
        boolean clickable = typedArray.getBoolean(R.styleable.UploadPictureView_addClickable, false);

        typedArray.recycle();

        iv_add.setBackgroundResource(addBg);
        iv_add.setClickable(clickable);
    }

    public void setImageViewPic(Bitmap bitmap){
        iv_pic.setImageBitmap(bitmap);
//        iv_del.setVisibility(View.VISIBLE);
        iv_add.setVisibility(View.GONE);
    }

    public void setAddViewClickable(boolean clickable){
        iv_add.setClickable(clickable);
    }

    public void setAddViewBackground(int resId){
        iv_add.setBackgroundResource(resId);
    }

    public void setDeleteListener(UploadPictureDeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    /**
     * 恢复view
     */
    public void recoverView(){

    }

    public interface UploadPictureDeleteListener{
        void onPicDeleteClick(View v);
    }
}
