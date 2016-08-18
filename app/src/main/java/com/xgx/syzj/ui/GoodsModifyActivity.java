package com.xgx.syzj.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xgx.syzj.R;
import com.xgx.syzj.app.AppManager;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.GoodsDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.BitmapUtil;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.utils.photos.CropOption;
import com.xgx.syzj.utils.photos.PhotoReadyHandler;
import com.xgx.syzj.utils.photos.SelectPhotoManager;
import com.xgx.syzj.widget.UploadPictureView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * 商品编辑详情
 *
 * @author zajo
 * @created 2015年08月25日 11:08
 */
public class GoodsModifyActivity extends BaseActivity implements UploadPictureView.UploadPictureDeleteListener {

    private final static int SCANNIN_GREQUEST_CODE = 1001;
    private final static int GOODSTYPE_GREQUEST_CODE = 1002;

    private UploadPictureView upv_one, upv_two, upv_three;
    private TextView tv_code;
    private EditText et_name, et_brand, et_type, et_input_money, et_sell_money, et_input_count, et_guige, et_unit;
    private String strCode, strName, strType, strInputMoney, strSellMoney, strInputCount, strGuige, strBrand, strUnit;
    private String image;
    private int index = 0;
    private Map<String, String> paths = new HashMap<>();
    //    private GoodsCategory type;
    private boolean cancel = false;
    private Goods goods;
    private List images=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_modify);
        goods = getIntent().getParcelableExtra("goods");
        initView();
        initData();
        EventCenter.bindContainerAndHandler(this, eventHandler);
    }

    private void initView() {
        setTitleText(getString(R.string.goods_detail_title));
        tv_code = (TextView) findViewById(R.id.tv_code);
        et_type = (EditText) findViewById(R.id.et_type);
        et_name = (EditText) findViewById(R.id.et_name);
        et_brand = (EditText) findViewById(R.id.et_brand);
        et_input_money = (EditText) findViewById(R.id.et_input_money);
        et_sell_money = (EditText) findViewById(R.id.et_sell_money);
        et_input_count = (EditText) findViewById(R.id.et_input_count);
        et_guige = (EditText) findViewById(R.id.et_guige);
        et_unit = (EditText) findViewById(R.id.et_unit);
        upv_one = (UploadPictureView) findViewById(R.id.upv_one);
        upv_two = (UploadPictureView) findViewById(R.id.upv_two);
        upv_three = (UploadPictureView) findViewById(R.id.upv_three);
        upv_one.setDeleteListener(this);
        upv_two.setDeleteListener(this);
        upv_three.setDeleteListener(this);
    }

    private void initData() {
        tv_code.setText(goods.getBarcode());
        et_type.setText(goods.getCategoryId());
        et_name.setText(goods.getProductName());
        et_brand.setText(goods.getBrand());
        et_input_money.setText(goods.getInputPrice() + "");
        et_sell_money.setText(goods.getSellingPrice() + "");
        et_input_count.setText(goods.getVip_price()+ "");
        et_guige.setText(goods.getSpecification());
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result) {
            hideLoadingDialog();
            if (result.getStatus() == 200) {
                showShortToast("修改成功");
                JSONObject obj = JSON.parseObject(result.getResult());
                goods = FastJsonUtil.json2Bean(obj.toJSONString(), Goods.class);
                EventBus.getDefault().postSticky(goods);
               gotoActivity(GoodsListActivity.class);
            } else {
                showShortToast(result.getMessage());
            }
        }

        public void onEvent(String error) {
            hideLoadingDialog();
            showShortToast(error);
        }

    };

    public void onAddPic(View view) {
        SelectPhotoManager.getInstance().setPhotoReadyHandler(new PhotoReadyHandler() {
            @Override
            public void onPhotoReady(int from, String imgPath) {
                paths.put(index + "", imgPath);
                Bitmap bitmap = BitmapUtil.scaleBitmap(GoodsModifyActivity.this, imgPath, 200);
                image = BitmapUtil.convertIconToString(bitmap);
                if (index == 0) {
                    upv_one.setImageViewPic(bitmap);
                    upv_one.setAddViewClickable(false);
                    upv_two.setAddViewClickable(true);
                    upv_two.setAddViewBackground(R.mipmap.add_picture);
                } else if (index == 1) {
                    upv_two.setImageViewPic(bitmap);
                    upv_two.setAddViewClickable(false);
                    upv_three.setAddViewClickable(true);
                    upv_three.setAddViewBackground(R.mipmap.add_picture);
                } else if (index == 2) {

                    upv_three.setImageViewPic(bitmap);
                    upv_three.setAddViewClickable(false);
                }
                index++;


            }
        });
        images.add(paths.get(index));
        CropOption cropOption = new CropOption();
        cropOption.outputX = 200;
        cropOption.outputY = 200;
        SelectPhotoManager.getInstance().setCropOption(cropOption);
        SelectPhotoManager.getInstance().start(this);
    }

    public void onAddCode(View view) {
        Intent intent = new Intent();
        intent.setClass(GoodsModifyActivity.this, MipcaActivityCapture.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
    }

    public void onSave(View view) {
        if (checkInput()) {
            showLoadingDialog(R.string.loading_modify_goods);
            GoodsDataModel.modifyGoods(goods.getProductId(), strCode,
                    strName, strType, strInputMoney, strSellMoney, strInputCount, strGuige, strBrand, "1", images);
        }
    }

    private boolean checkInput() {
        boolean flag = false;
        String msg = "";
        strCode = tv_code.getText().toString();
        strName = et_name.getText().toString();
        strType = et_type.getText().toString();
        strInputMoney = et_input_money.getText().toString();
        strSellMoney = et_sell_money.getText().toString();
        strInputCount = et_input_count.getText().toString();
        strGuige = et_guige.getText().toString();
        strBrand = et_brand.getText().toString();
        strUnit = et_unit.getText().toString();
        if (TextUtils.isEmpty(strCode)) {
            msg = "货号";
        } else if (TextUtils.isEmpty(strName)) {
            msg = "名称";
        }
//        else if (type == null) {
//            msg = "分类";
//        }
//        else if (TextUtils.isEmpty(strSupply)) {
//            msg = "供应商";
//        }
        else if (TextUtils.isEmpty(strInputMoney)) {
            msg = "进价";
        } else if (TextUtils.isEmpty(strSellMoney)) {
            msg = "售价";
        }
//        else if (TextUtils.isEmpty(strInputCount)) {
//            msg = "数量";
//        } else if (TextUtils.isEmpty(strGuige)) {
//            msg = "规格";
//        }
        else {
            flag = true;
        }
        if (!flag) {
            showShortToast(String.format("商品%s不能为空", msg));
            return flag;
        }
        if (strType.equals(goods.getCategoryId())&&strCode.equals(goods.getBarcode()) && strName.equals(goods.getProductName())
                && strInputMoney.equals(goods.getInputPrice() + "") && strSellMoney.equals(goods.getSellingPrice() + "")
                && strInputCount.equals(goods.getVip_price() + "") && strGuige.equals(goods.getSpecification())) {
            showShortToast("未做修改");
            return false;
        }
        return flag;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;
        SelectPhotoManager.getInstance().onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                Bundle bundle = data.getExtras();
                //显示扫描到的内容
                tv_code.setText(bundle.getString("result"));
                break;
            default:
                break;
        }
    }

    @Override
    public void onPicDeleteClick(View v) {
        switch (v.getId()) {
            case R.id.upv_one:
                break;
        }
    }
}
