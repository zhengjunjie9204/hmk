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
import com.xgx.syzj.ui.RevenuseSellFinishActivity;
import com.xgx.syzj.utils.Utils;
import com.xgx.syzj.widget.CustomAlertDialog;

import java.util.ArrayList;
import java.util.List;


public class ProjectListAdapter extends BaseAdapter{

    private Context mContext;
    private List<Project> mList = new ArrayList<>();
    private Handler mHandler;
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
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        Project project = mList.get(position);
        hold.tv_name.setText(project.getName());
        hold.et_time.setText(project.getLaborTime() + "");
        hold.tv_money.setText("" + project.getPrice() * project.getLaborTime());
        if (mHandler == null) {
            hold.et_time.setOnClickListener(new MyClickListener(project));
        }
        return convertView;
    }

    class MyClickListener implements View.OnClickListener {
        private Project project;
        MyClickListener(Project project){
            this.project = project;
        }
        @Override
        public void onClick(View v)
        {
            String time = String.valueOf(project.getLaborTime());
            if (Utils.isInteger(time)) {
                CustomAlertDialog.editTextDialog(mContext,time,"请输入工时",new MyIAlertDialogListener(project));
            }else{
                CustomAlertDialog.editTextDialog(mContext, "1","请输入工时", new MyIAlertDialogListener(project));
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
    }
}
