package com.xgx.syzj.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员列表适配器
 */
public class MemberListAdapter extends BaseAdapter/* implements SectionIndexer */{
	private List<Member> mList = new ArrayList<>();
	private Context mContext;
	private ViewHolder viewHolder = null;
	private List<Member> selected = new ArrayList<>();//选中的List集合

	public MemberListAdapter(Context mContext, List<Member> list) {
		this.mContext = mContext;
		mList = list;
	}

	public void updateListView(List<Member> list){
		if (list == null || list.size() == 0)
			return;
		mList = list;
		notifyDataSetChanged();
	}

	public void selectAll(boolean flag){
		if (flag){
			selected.clear();
			selected.addAll(mList);
		} else{
			selected.clear();
		}
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.mList.size();
	}

	public Object getItem(int position) {
		return mList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		final Member member = mList.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.item_member_list,null);
			viewHolder.tv_name = (TextView) view.findViewById(R.id.tv_name);
			viewHolder.tv_phone = (TextView) view.findViewById(R.id.tv_phone);
			viewHolder.tv_jifen = (TextView) view.findViewById(R.id.tv_jifen);
			viewHolder.tv_money = (TextView) view.findViewById(R.id.tv_money);
			viewHolder.tv_carNum= (TextView) view.findViewById(R.id.tv_carNum);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.tv_name.setText(member.getName());
		viewHolder.tv_phone.setText(member.getPhone());

		viewHolder.tv_jifen.setText(member.getConsumeRecord()+"");
		viewHolder.tv_money.setText(member.getStoredMoney()+"");
		viewHolder.tv_carNum.setText(member.getCarNumber());

		return view;
	}

	final static class ViewHolder {
		TextView tvLetter,tv_name,tv_phone,tv_jifen,tv_money,tv_carNum;
	}
}
