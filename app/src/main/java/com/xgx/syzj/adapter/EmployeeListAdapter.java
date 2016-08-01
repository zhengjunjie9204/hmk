package com.xgx.syzj.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.bean.Employeebean;
import com.xgx.syzj.datamodel.EmployeeDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sam on 2016/3/9 15:46.
 */
public class EmployeeListAdapter extends BaseAdapter{
    private  Context mContext;
    private ViewHolder viewHolder = null;
    private List<Employeebean> mList = new ArrayList<>();
    private List<Employeebean> selectedList = new ArrayList<>();
    public IEmployeeItemCheck onIEployeeItemCheck;


    public EmployeeListAdapter(Context context, List<Employeebean> list,IEmployeeItemCheck itemCheck) {
        this.mContext = context;
        this.mList = list;
        onIEployeeItemCheck = itemCheck;
    }

    public void appendList(List<Employeebean> list){
        if(list == null && list.size() == 0)return;
        mList.addAll(list);
        notifyDataSetChanged();
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
        final Employeebean employee = mList.get(position);
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_employee_list,null);
            viewHolder.checkBox = (CheckBox)convertView.findViewById(R.id.cb);
            viewHolder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            viewHolder.tv_phone = (TextView)convertView.findViewById(R.id.tv_phone);
            viewHolder.tv_id = (TextView)convertView.findViewById(R.id.tv_id);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.checkBox.setOnCheckedChangeListener(checkListener);
        viewHolder.checkBox.setTag(position);
 /*       if(selectedList.contains(employee)){
            viewHolder.checkBox.setClickable(true);
        }else {
            viewHolder.checkBox.setClickable(false);
        }*/
        viewHolder.tv_name.setText(employee.getUserName());
        viewHolder.tv_phone.setText(employee.getMobilePhone());
        viewHolder.tv_id.setText(employee.getAccountId());

        return convertView;
    }

    public static  class ViewHolder{
        CheckBox checkBox;
        TextView tvLetter,tv_name,tv_phone,tv_id;
    }

    private CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int index = (int) buttonView.getTag();
            Employeebean m = mList.get(index);
            if (!isChecked && selectedList.contains(m)) {
                //取消选择
                selectedList.remove(m);
            } else if(isChecked && !selectedList.contains(m)) {
                //选中
                selectedList.add(m);
            }
            if (onIEployeeItemCheck != null)
                onIEployeeItemCheck.onItemCheck(selectedList);
        }
    };

    public interface IEmployeeItemCheck{
        void onItemCheck(List<Employeebean> list);
    }
}
