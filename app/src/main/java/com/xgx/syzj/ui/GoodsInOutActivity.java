package com.xgx.syzj.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.adapter.OutInHistoryAdapter;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.bean.stockRecordHistory;
import com.xgx.syzj.datamodel.GoodsDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 入库出库
 *
 * @author zajo
 * @created 2015年08月27日 18:04
 */
public class GoodsInOutActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_count, tv_in, tv_out;
    private LinearLayout ll_in, ll_out;
    private View v_in, v_out;
    private Button btn_add, btn_cut;
    private EditText et_count;
    private int flag = 0;//0入库、1出库
    private Goods goods;
    private ListView lv_data;
    private List<stockRecordHistory> mDataList = new ArrayList<>();
    private OutInHistoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_in_out);
        Bundle bundle = getIntent().getExtras();
        goods = (Goods) bundle.get("goods");
        if (goods == null) {
            defaultFinish();
            return;
        }
        initView();
        initListener();
        tv_count.setText(goods.getQuantity() + "");
        EventCenter.bindContainerAndHandler(this, eventHandler);
        GoodsDataModel.queryhistoryData(goods.getProductId());
    }

    private void initView() {
        setTitleText(getString(R.string.goods_kucun_title));
        setSubmit(getString(R.string.app_button_sure));
        lv_data = (ListView) findViewById(R.id.lv_data);
        tv_count = (TextView) findViewById(R.id.tv_count);
        ll_in = (LinearLayout) findViewById(R.id.ll_in);
        ll_out = (LinearLayout) findViewById(R.id.ll_out);
        tv_in = (TextView) findViewById(R.id.tv_in);
        tv_out = (TextView) findViewById(R.id.tv_out);
        v_in = findViewById(R.id.v_in);
        v_out = findViewById(R.id.v_out);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_cut = (Button) findViewById(R.id.btn_cut);
        et_count = (EditText) findViewById(R.id.et_count);
        mAdapter = new OutInHistoryAdapter(GoodsInOutActivity.this, mDataList);
        lv_data.setAdapter(mAdapter);
    }

    private void initListener()
    {
        ll_in.setOnClickListener(this);
        ll_out.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_cut.setOnClickListener(this);
        et_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                String str = s.toString().trim();
                if (str.equals("0")) {
                    et_count.setText("");
                    return;
                }
                if (flag == 1 && !TextUtils.isEmpty(str)) {
                    int num = Integer.parseInt(str);
                    if (num > goods.getQuantity()) {
                        et_count.setText(goods.getQuantity() + "");
                    }
                }
            }
        });
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(List<stockRecordHistory> list){
            mDataList.clear();
            mDataList.addAll(list);
            mAdapter.notifyDataSetChanged();
        }

        public void onEvent(Result result) {
            hideLoadingDialog();
            String strNum = et_count.getText().toString();
            int num = Integer.parseInt(strNum);
            et_count.setText("");
            if (flag == 0) {
                showShortToast("入库成功");
                goods.setQuantity(goods.getQuantity() + num);
            } else {
                showShortToast("出库成功");
                goods.setQuantity(goods.getQuantity() - num);
            }
            tv_count.setText(goods.getQuantity() + "");
            EventBus.getDefault().postSticky(goods);
            GoodsDataModel.queryhistoryData(goods.getProductId());
        }

        public void onEvent(String error) {
            hideLoadingDialog();
            showShortToast(error);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                computer(true);
                break;
            case R.id.btn_cut:
                computer(false);
                break;
            case R.id.ll_in:
                setButtonBackground(0);
                break;
            case R.id.ll_out:
                setButtonBackground(1);
                break;
        }
    }

    private void setButtonBackground(int flag) {
        this.flag = flag;
        if (flag == 0) {
            tv_in.setTextColor(getResources().getColor(R.color.top_bar_color));
            v_in.setBackgroundColor(getResources().getColor(R.color.top_bar_color));
            ViewGroup.LayoutParams vParams = v_in.getLayoutParams();
            vParams.height = Utils.getDIP(this, 2);
            v_in.setLayoutParams(vParams);
            v_in.invalidate();
            tv_out.setTextColor(getResources().getColor(R.color.title_3_color));
            v_out.setBackgroundColor(getResources().getColor(R.color.edite_color));
            vParams = v_out.getLayoutParams();
            vParams.height = Utils.getDIP(this, 1);
            v_out.setLayoutParams(vParams);
            v_out.invalidate();
            et_count.setHint(getString(R.string.goods_kucun_in_hint));
            et_count.setSelection(0);
        } else {
            tv_in.setTextColor(getResources().getColor(R.color.title_3_color));
            v_in.setBackgroundColor(getResources().getColor(R.color.edite_color));
            ViewGroup.LayoutParams vParams = v_in.getLayoutParams();
            vParams.height = Utils.getDIP(this, 1);
            v_in.setLayoutParams(vParams);
            v_in.invalidate();
            tv_out.setTextColor(getResources().getColor(R.color.top_bar_color));
            v_out.setBackgroundColor(getResources().getColor(R.color.top_bar_color));
            vParams = v_out.getLayoutParams();
            vParams.height = Utils.getDIP(this, 2);
            v_out.setLayoutParams(vParams);
            v_out.invalidate();
            et_count.setHint(getString(R.string.goods_kucun_out_hint));
            et_count.setSelection(0);
            String strNum = et_count.getText().toString().trim();
            if (TextUtils.isEmpty(strNum)) return;
            int num = Integer.parseInt(strNum);
            if (num > goods.getQuantity()) {
                et_count.setText(goods.getQuantity() + "");
            }
        }

    }

    private void computer(boolean isAdd) {
        String strNum = et_count.getText().toString().trim();
        int num = 0;
        if (!TextUtils.isEmpty(strNum)) {
            num = Integer.parseInt(strNum);
        }

        if (!isAdd) {
            //减
            if (num == 0) return;
            else {
                num -= 1;
                et_count.setText(num + "");
            }
        } else {
            //加
            if (flag == 1) {
                if (num >= goods.getQuantity()) {
                    et_count.setText(goods.getQuantity() + "");
                } else {
                    et_count.setText(num + 1 + "");
                }
            } else {
                et_count.setText(num + 1 + "");
            }
        }
    }

    @Override
    protected void submit() {
        super.submit();
        String strNum = et_count.getText().toString();
        if (TextUtils.isEmpty(strNum)) {
            if (flag == 0)
                showShortToast("入库数量不能为空...");
            else
                showShortToast("出库数量不能为空...");
            return;
        }
        int num = Integer.parseInt(strNum);
        if (flag == 1)
            num = 0 - num;
        showLoadingDialog(R.string.loading_date);
        //TODO:出入库
        GoodsDataModel.inAndOutGoods(flag, goods.getProductId(), num, "描述");
    }
}
