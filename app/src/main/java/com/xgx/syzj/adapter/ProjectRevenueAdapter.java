package com.xgx.syzj.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.Project;
import com.xgx.syzj.utils.ArithUtils;
import com.xgx.syzj.widget.CircleImageView;
import com.xgx.syzj.widget.PhotoViewPagerDialog;

import java.util.ArrayList;
import java.util.List;


public class ProjectRevenueAdapter extends BaseAdapter {

    private Context mContext;
    private List<Project> mList = new ArrayList<>();
    private Handler mHandler;
    private PhotoViewPagerDialog mDialog;

    public ProjectRevenueAdapter(Context context, List<Project> list, Handler mHandler) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_revenue_project, null);
            hold.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            hold.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            hold.et_time = (TextView) convertView.findViewById(R.id.et_time);
            hold.mImg = (CircleImageView) convertView.findViewById(R.id.item_img);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        Project project = mList.get(position);
//        Picasso.with(mContext).load("").centerCrop().into(hold.mImg);
        double time;
        double laborTime = project.getLaborTime();
        double mylaborTime = project.getMylaborTime();
        if (mylaborTime > 0) {
            time = mylaborTime;
        } else {
            time = laborTime;
        }
        hold.tv_name.setText(project.getName());
        hold.et_time.setText(time + "");
        if (project.isFinish()) {
            hold.tv_money.setText(ArithUtils.showString(project.getTotalPrice()));
        } else {
            hold.tv_money.setText(ArithUtils.showString(project.getPrice()));
        }
        if (mHandler == null) {
            hold.mImg.setOnClickListener(new MyClickListener(project, 1));
        }
        return convertView;
    }

    class MyClickListener implements View.OnClickListener {
        private Project project;
        private int isDialog;//0不需要弹出view框 1是需要弹框

        MyClickListener(Project project, int isDialog) {
            this.project = project;
            this.isDialog = isDialog;
        }

        @Override
        public void onClick(View v) {
            if (isDialog == 1) {
                if (mDialog == null) {
                    List<String> list = new ArrayList<>();
                    list.add("http://img15.3lian.com/2015/a1/10/d/111.jpg");
                    list.add("http://img3.duitang.com/uploads/item/201606/17/20160617110849_njxzd.thumb.700_0.jpeg");
                    list.add("http://img3.duitang.com/uploads/item/201510/04/20151004220605_kL48J.jpeg");
                    list.add("http://img4q.duitang.com/uploads/item/201505/28/20150528214507_MBUXF.jpeg");
                    list.add("http://www.itotii.com/wp-content/uploads/2016/07/29/1469781945_LplPyMeA.jpg");
                    mDialog = new PhotoViewPagerDialog(mContext, list);
                }
                mDialog.show();
            }
        }
    }


    class HoldClass {
        TextView tv_name, tv_money;
        TextView et_time;
        CircleImageView mImg;
    }
}
