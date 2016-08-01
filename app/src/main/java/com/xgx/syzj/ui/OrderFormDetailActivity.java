package com.xgx.syzj.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.bean.OrderForm;
import com.xgx.syzj.utils.StrUtil;
import com.xgx.syzj.widget.TextItemView;

import java.util.List;

/**
 * 订单详情
 *
 * @author zajo
 * @created 2015年11月20日 15:23
 */
public class OrderFormDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextItemView tiv_num, tiv_userName, tiv_orderTime, tiv_reservationTime, tiv_goods;
    private LinearLayout container;
    private OrderForm of;
    private Button btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        of = getIntent().getParcelableExtra("order");
        if (of == null) {
            defaultFinish();
            return;
        }
        initView();
        initData();
    }

    private void initView() {
        setTitleText("订单详情");
        setSubmit("客户到店");
        tiv_num = (TextItemView) findViewById(R.id.tiv_num);
        tiv_userName = (TextItemView) findViewById(R.id.tiv_userName);
        tiv_orderTime = (TextItemView) findViewById(R.id.tiv_orderTime);
        tiv_reservationTime = (TextItemView) findViewById(R.id.tiv_reservationTime);
        tiv_goods = (TextItemView) findViewById(R.id.tiv_goods);
        container = (LinearLayout) findViewById(R.id.container);
        btn_delete = (Button) findViewById(R.id.btn_delete);
    }

    private void initData() {
        tiv_num.setDesc(of.getOrderId());
        tiv_userName.setDesc(of.getUserName() + "(" + of.getUserPhone() + ")");
        tiv_orderTime.setDesc(StrUtil.getFriendlyTime(of.getOrderTime(), "yyyy-MM-dd HH:mm:ss"));
        tiv_reservationTime.setDesc(StrUtil.getFriendlyTime(of.getReservationTime(), "yyyy-MM-dd HH:mm:ss"));
        List<Goods> goods = of.getProducts();
        if (goods == null) return;
        for (int i = 0; i < goods.size(); i++) {
            Goods g = goods.get(i);
            View view = LayoutInflater.from(this).inflate(R.layout.item_order_form_detail, null);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_guige = (TextView) view.findViewById(R.id.tv_guige);
            TextView tv_sell_money = (TextView) view.findViewById(R.id.tv_sell_money);
            TextView tv_count = (TextView) view.findViewById(R.id.tv_count);
            tv_name.setText(g.getProductName());
            tv_guige.setText("规格：" + g.getSpecification());
            tv_sell_money.setText("售价：" + g.getStrSellingPrice());
            tv_count.setText("数量：" + g.getQuantity());
            container.addView(view);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete:
                break;
        }
    }
}
