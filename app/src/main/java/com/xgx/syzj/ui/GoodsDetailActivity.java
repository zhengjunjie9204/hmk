package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xgx.syzj.R;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.GoodsDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.widget.CustomAlertDialog;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 商品详情
 *
 * @author zajo
 * @created 2015年08月24日 16:17
 */
public class GoodsDetailActivity extends BaseActivity {

    private Button btn_modify;
    private ImageView iv_one;
    private Goods goods;
    private TextView tv_code,tv_name,tv_type,tv_guige,tv_money,tv_sell,tv_unit,tv_brand;
    private TextView tv_vip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        setTitleText(getString(R.string.goods_detail_title));
        setSubmit(getString(R.string.app_button_modify));
        iv_one = (ImageView) findViewById(R.id.iv_one);
        tv_code = ((TextView) findViewById(R.id.tv_code));
        tv_name = ((TextView) findViewById(R.id.tv_name));
        tv_type = ((TextView) findViewById(R.id.tv_type));
        tv_guige = ((TextView) findViewById(R.id.tv_guige));

        tv_money = ((TextView) findViewById(R.id.tv_input_money));
        tv_sell = ((TextView) findViewById(R.id.tv_sell_money));
        tv_vip = ((TextView) findViewById(R.id.tv_vip));
        tv_unit = ((TextView) findViewById(R.id.tv_unit));
        tv_brand= (TextView) findViewById(R.id.tv_brand);

        EventCenter.bindContainerAndHandler(this, eventHandler);
        EventBus.getDefault().registerSticky(eventHandler);

        Bundle bundle = getIntent().getExtras();
        goods = (Goods) bundle.get("goods");
        if (goods == null){
            defaultFinish();
            return;
        }
        initData();
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        EventBus.getDefault().registerSticky(eventHandler);
//    }

//    @Override
//    public void onStop() {
//        EventBus.getDefault().unregister(eventHandler);
//        super.onStop();
//    }

    private void initData(){
        if (goods == null) return;
        tv_code.setText(goods.getBarcode());
        tv_name.setText(goods.getProductName());
        tv_type.setText(goods.getCategoryId());
        tv_guige.setText(goods.getSpecification());
        tv_money.setText("￥" + goods.getInputPrice());
        tv_sell.setText("￥" + goods.getSellingPrice());
        tv_vip.setText(goods.getVipPrice() + "");
        tv_unit.setText(goods.getUnit());

        tv_brand.setText(goods.getBrand());
        iv_one.setTag(1);
        if(goods.getImages()!=null){
            List<Goods.ImagesBean> images = goods.getImages();
            for (Goods.ImagesBean image : images) {
                String image1 = image.getImage();
                Picasso.with(this).load(image1).fit().into(iv_one);
            }
        }
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler(){
        public void onEvent(Result result){
            hideLoadingDialog();
            showShortToast("删除成功");
            Intent intent = new Intent();
            intent.putExtra("goods", goods);
            setResult(RESULT_OK, intent);
            defaultFinish();
        }

        public void onEvent(String error){
            hideLoadingDialog();
            showShortToast(error);
        }

        public void onEvent(Goods g){
            goods = g;
            initData();
        }
    };

    public void onGoodsAddSell(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("goods", goods);
        gotoActivity(GoodsInOutActivity.class, bundle);
    }

    public void onGoodsDelete(View view) {
        if(SYZJApplication.getInstance().getSpUtil().getInt(Constants.SharedPreferencesClass.SP_ROLES)==2&&goods.getQuantity()==0) {
            CustomAlertDialog.showRemindDialog(this, "提醒", " 确定要删除该商品吗？", new CustomAlertDialog.IAlertDialogListener() {
                @Override
                public void onSure(Object obj) {
                    showLoadingDialog(R.string.loading_delete_goods);
                    GoodsDataModel.deleteGoods( goods.getUid());

                }
            });
        }else{
            showShortToast("权限不足或商品库存不为0，无法删除");
        }
    }

    @Override
    protected void submit() {
        super.submit();
        Bundle bundle = new Bundle();
        bundle.putParcelable("goods", goods);
        gotoActivity(GoodsModifyActivity.class, bundle);
    }

    @Override
    public void onBackPressed() {
        gotoActivity(GoodsListActivity.class);
        finish();
        super.onBackPressed();
    }
}
