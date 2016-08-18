package com.xgx.syzj.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.Project;
import com.xgx.syzj.ui.RevenuseSellFinishActivity;
import com.xgx.syzj.utils.Utils;
import com.xgx.syzj.widget.CustomAlertDialog;
import com.xgx.syzj.widget.PhotoViewPagerDialog;

import java.util.ArrayList;
import java.util.List;


public class ProjectListAdapter extends BaseAdapter{

    private Context mContext;
    private List<Project> mList = new ArrayList<>();
    private Handler mHandler;
    private PhotoViewPagerDialog mDialog;
    public ProjectListAdapter(Context context, List<Project> list,Handler mHandler) {
        this.mContext = context;
        this.mList = list;
        this.mHandler = mHandler;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HoldClass hold;
        if (convertView == null) {
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_revenue_goods_sell, null);
            hold.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            hold.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            hold.et_time = (TextView) convertView.findViewById(R.id.et_time);
            hold.mImg = (ImageView) convertView.findViewById(R.id.item_img);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        Project project = mList.get(position);
//        Picasso.with(mContext).load("").centerCrop().into(hold.mImg);
        hold.tv_name.setText(project.getName());
        hold.et_time.setText(project.getLaborTime() + "");
        hold.tv_money.setText("" + project.getPrice() * project.getLaborTime());
        if (mHandler == null) {
            hold.et_time.setOnClickListener(new MyClickListener(project,0));
            hold.mImg.setOnClickListener(new MyClickListener(project,1));
        }
        return convertView;
    }

    class MyClickListener implements View.OnClickListener {
        private Project project;
        private int isDialog;//0不需要弹出view框 1是需要弹框
        MyClickListener(Project project,int isDialog){
            this.project = project;
            this.isDialog = isDialog;
        }
        @Override
        public void onClick(View v)
        {
            if (isDialog == 0) {
                String time = String.valueOf(project.getLaborTime());
                if (Utils.isInteger(time)) {
                    CustomAlertDialog.editTextDialog(mContext,time,"请输入工时",new MyIAlertDialogListener(project));
                }else{
                    CustomAlertDialog.editTextDialog(mContext, "1","请输入工时", new MyIAlertDialogListener(project));
                }
            }else if(isDialog == 1){
                if(mDialog == null){
                    List<String> list = new ArrayList<>();
                    list.add("http://img15.3lian.com/2015/a1/10/d/111.jpg");
                    list.add("http://img3.duitang.com/uploads/item/201606/17/20160617110849_njxzd.thumb.700_0.jpeg");
                    list.add("http://img3.duitang.com/uploads/item/201510/04/20151004220605_kL48J.jpeg");
                    list.add("http://img4q.duitang.com/uploads/item/201505/28/20150528214507_MBUXF.jpeg");
                    list.add("http://www.itotii.com/wp-content/uploads/2016/07/29/1469781945_LplPyMeA.jpg");
//                    for (int i = 0; i < mList.size(); i++) {
//                    }
                    mDialog = new PhotoViewPagerDialog(mContext,list);
                }
                mDialog.show();
            }
        }
    }

    class MyIAlertDialogListener implements CustomAlertDialog.IAlertDialogListener {
        private Project project;
        MyIAlertDialogListener(Project project){
            this.project = project;
        }
        @Override
        public void onSure(Object obj)
        {
            project.setLaborTime((int) obj);
            if(null != mHandler){
                mHandler.sendEmptyMessage(RevenuseSellFinishActivity.HANDLER_MONEY);
            }
            notifyDataSetChanged();
        }
    }

    class HoldClass {
        TextView tv_name, tv_money;
        TextView et_time;
        ImageView mImg;
    }
}
