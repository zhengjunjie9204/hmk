package com.xgx.syzj.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.bean.Project;

import java.util.ArrayList;
import java.util.List;


public class ProjectListAdapter extends BaseAdapter implements View.OnTouchListener{

    private Context mContext;
    private List<Project> mList = new ArrayList<>();
    private IDeleteItemCount deleteItemCount;
    private ITextChange textChange;

    public ProjectListAdapter(Context context, List<Project> list, IDeleteItemCount deleteItemCount, ITextChange textChange) {
        this.mContext = context;
        this.mList = list;
        this.deleteItemCount = deleteItemCount;
        this.textChange=textChange;
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
        final Project project = mList.get(position);
        final HoldClass hold;
        if (convertView == null) {
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_revenue_goods_sell, null);
            hold.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            hold.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            hold.et_time = (EditText) convertView.findViewById(R.id.et_time);
            hold.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        hold.et_time.setTag(position);
        hold.et_time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Integer.parseInt(hold.et_time.getTag().toString()) == position) {
                    if (textChange != null)
                        textChange.onTextChange(position,s.toString());
                    if(!TextUtils.isEmpty(s.toString())){
                        double count=Double.parseDouble(s.toString());
                        hold.tv_money.setText("" + project.getPrice() * count);
                    }
                }

            }
        });
        hold.tv_name.setText(project.getName());
        hold.et_time.setText(project.getLaborTime() + "");
        hold.tv_money.setText("" + project.getPrice() * project.getLaborTime());
        hold.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteItemCount != null)
                    deleteItemCount.onItemDelete(position);
            }
        });
        convertView.setOnTouchListener(this);
        hold.et_time.setOnTouchListener(this);

        return convertView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if( v instanceof EditText){
            EditText et =  (EditText)v;
            et.setFocusable(true);
            et.setFocusableInTouchMode(true);
        }  else {
            HoldClass holder = (HoldClass) v.getTag();
            holder.et_time.setFocusable(false);
            holder.et_time.setFocusableInTouchMode(false);

        }
        return false;
    }

    class HoldClass {
        TextView tv_name, tv_money;
        EditText et_time;
        ImageView iv_delete;
    }


    public interface IDeleteItemCount {
        void onItemDelete(int position);
    }

    public interface ITextChange{
        void onTextChange(int position,String s);
    }
}
