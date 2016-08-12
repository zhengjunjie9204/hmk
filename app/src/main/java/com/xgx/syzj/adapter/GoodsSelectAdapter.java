package com.xgx.syzj.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.bean.Goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品列表适配器
 */
public class GoodsSelectAdapter extends BaseAdapter {

    private Context mContext;
    private List<Goods> mList = new ArrayList<Goods>();
    private Map<Integer, Goods> selected = new HashMap<>();//选中的List集合
    private IGoodsItemCheck onGoodsitemCheck;

    public GoodsSelectAdapter(Context context, List<Goods> list, IGoodsItemCheck onGoodsitemCheck)
    {
        this.mContext = context;
        this.mList = list;
        this.onGoodsitemCheck = onGoodsitemCheck;
    }

    @Override
    public int getCount()
    {
        return mList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final Goods goods = mList.get(position);
        HoldClass hold;
        if (convertView == null) {
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_goods_list, null);
            hold.iv_goods = (ImageView) convertView.findViewById(R.id.iv_good);
            hold.cb = (CheckBox) convertView.findViewById(R.id.cb);
            hold.rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
            hold.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            hold.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            hold.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        hold.cb.setOnCheckedChangeListener(cbListener);
        hold.cb.setTag(position);
        hold.iv_goods.setTag(position);
        if (!TextUtils.isEmpty(goods.getImage())) {
            String path = goods.getImage().replace("\\", "");
            Api.loadBitmap(hold.iv_goods, path, position);
        }
        hold.cb.setVisibility(View.VISIBLE);
        hold.tv_name.setText(goods.getProductName());
        hold.tv_count.setText("库存：" + goods.getQuantity() + "件");
        hold.tv_money.setText("¥ " + goods.getSellingPrice());
        return convertView;
    }

    static class HoldClass {
        private ImageView iv_goods;
        private CheckBox cb;
        private TextView tv_name, tv_count, tv_money;
        private RelativeLayout rl_item;
        private TextView item_cancel;
    }

    private CompoundButton.OnCheckedChangeListener cbListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            int index = (Integer) buttonView.getTag();
            Goods g = mList.get(index);
            if (!isChecked) {
                selected.remove(index);
            }
            if (isChecked) {
                selected.put(index, g);
            }
            if (onGoodsitemCheck != null) {
                onGoodsitemCheck.onItemCheck(selected, index);
            }
        }
    };

    public Map<Integer, Goods> getSelected()
    {
        return selected;
    }


    public void appendList(List<Goods> list)
    {
        if (list == null || list.size() == 0) return;
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public interface IGoodsItemCheck {
        void onItemCheck(Map<Integer, Goods> list, int position);
    }

    private String getKey(Goods goods)
    {
        return goods.getProductName() + goods.getProductId();
    }

}
