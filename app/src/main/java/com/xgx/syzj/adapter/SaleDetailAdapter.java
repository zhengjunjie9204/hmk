package com.xgx.syzj.adapter;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.bean.BillItemDetailBean;
import com.xgx.syzj.datamodel.BillGoodsReturnModel;
import com.xgx.syzj.widget.CustomAlertDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.greenrobot.event.EventBus;

/**
 * 销售详情适配器
 *
 * @author zajo
 * @created 2015年09月24日 18:07
 */
public class SaleDetailAdapter extends BaseAdapter {
    private Context mContext;
    private List<BillItemDetailBean> mList = new ArrayList<>();
    private HoldClass hold;
    private  String[] images;
    private int returnId = -1,removeItemId = -1;
    private String radio_button_text;


    public SaleDetailAdapter(Context context){
        this.mContext = context;
    }

    public void appendList(List<BillItemDetailBean> list){
        if (list == null || list.size() == 0) return;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final BillItemDetailBean goods = mList.get(position);
        removeItemId = position;
        returnId = goods.getBillDetailsId();
        images =mContext.getResources().getStringArray(R.array.imageUrls);
        Random random = new Random();
        String image_url = "";
        if (convertView == null) {
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sale_detail, null);
            hold.iv_goods = (ImageView) convertView.findViewById(R.id.iv_goods);
            hold.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            hold.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            hold.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            hold.btn_tui = (Button) convertView.findViewById(R.id.btn_tui);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        for (int i = 0; i < mList.size(); i++) {
            int j = random.nextInt(images.length);
            image_url = images[j];
        }
        Api.getImageLoader().get(image_url, hold.iv_goods);
        hold.tv_name.setText(goods.getProductName());
        hold.tv_money.setText("售价：￥"+goods.getSellingPrice());
        hold.tv_count.setText("数量："+goods.getQuantity()+"件");
        hold.btn_tui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAlertDialog.showSaleTuiDialog(mContext, new CustomAlertDialog.IAlertDialogListener() {
                    @Override
                    public void onSure(Object obj) {
                        Message msg = new Message();
                        msg.what = 0x01;
                        msg.obj = obj;
                        mHandler.sendMessage(msg);
                    }
                });
            }
        });
        return convertView;
    }

    static class HoldClass{
        private ImageView iv_goods;
        private TextView tv_name,tv_money,tv_count;
        private Button btn_tui;
    }

    public  Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x01:
                    String getStr =(String) msg.obj;
                    if(!TextUtils.isEmpty(getStr)){
                        BillGoodsReturnModel.doRequest(returnId,1);
                        mList.remove(mList.get(removeItemId));
                        Map<String,Integer> map = new HashMap<String,Integer>();
                        map.put("returnId",returnId);
                        EventBus.getDefault().post(map);
//                        saleHistoryInterfaces.notifyChangeData(removeItemId);
                    }
                    break;
                default:
                    break;
            }
        }
    };
}
