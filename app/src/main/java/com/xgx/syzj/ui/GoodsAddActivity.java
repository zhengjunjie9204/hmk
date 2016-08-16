package com.xgx.syzj.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.AddGoods;
import com.xgx.syzj.bean.GoodsCategory;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.GoodsDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.BitmapUtil;
import com.xgx.syzj.utils.photos.CropOption;
import com.xgx.syzj.utils.photos.PhotoReadyHandler;
import com.xgx.syzj.utils.photos.SelectPhotoManager;
import com.xgx.syzj.widget.UploadPictureView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 添加商品
 *
 * @author zajo
 * @created 2015年08月17日 14:30o
 */
public class GoodsAddActivity extends BaseActivity implements UploadPictureView.UploadPictureDeleteListener {

    private final static int SCANNIN_GREQUEST_CODE = 1001;


    private UploadPictureView upv_one, upv_two;
    private TextView tv_code;
    private EditText et_name, et_brand, et_type, et_input_money, et_sell_money, et_guige, et_unit;
    private String strCode, strName, strType, strBrand, strInputMoney, strSellMoney, strGuige, strUnit;
    private String image;
    private Map<String, String> paths = new HashMap<>();
    private GoodsCategory type;
    private boolean cancel = false;
    private EditText et_vip;
    private String Strvip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_add);

        initView();
        initData();
    }

    private void initView() {
        setTitleText(getString(R.string.goods_add_title));
        tv_code = (TextView) findViewById(R.id.tv_code);
        et_type = (EditText) findViewById(R.id.et_type);
        et_name = (EditText) findViewById(R.id.et_name);
        et_brand = (EditText) findViewById(R.id.et_brand);
        et_input_money = (EditText) findViewById(R.id.et_input_money);
        et_sell_money = (EditText) findViewById(R.id.et_sell_money);
        et_vip = (EditText) findViewById(R.id.et_vip);
        et_guige = (EditText) findViewById(R.id.et_guige);
        et_unit = (EditText) findViewById(R.id.et_unit);
        upv_one = (UploadPictureView) findViewById(R.id.upv_one);
        upv_two = (UploadPictureView) findViewById(R.id.upv_two);
        upv_one.setDeleteListener(this);
        upv_two.setDeleteListener(this);
    }

    private void initData() {
        EventCenter.bindContainerAndHandler(this, new SimpleEventHandler() {

            public void onEvent(Result result) {
                hideLoadingDialog();
                if (result.getStatus() == 200) {
                    showShortToast("添加成功");
                    if (cancel) {
                        gotoActivity(GoodsListActivity.class);
                        finish();
                    } else {
                        tv_code.setText("");
                        et_name.setText("");
                        et_brand.setText("");
                        et_input_money.setText("");
                        et_sell_money.setText("");
                        et_vip.setText("");
                        et_guige.setText("");
                    }
                } else {
                    showShortToast(result.getMessage());
                }
            }

            public void onEvent(String error) {
                showShortToast(error);
                hideLoadingDialog();
            }
        });
    }

    public void onAddPic(View view) {
        SelectPhotoManager.getInstance().setPhotoReadyHandler(new PhotoReadyHandler() {
            @Override
            public void onPhotoReady(int from, String imgPath) {
                paths.put(0 + "", imgPath);
                Bitmap bitmap = BitmapUtil.scaleBitmap(GoodsAddActivity.this, imgPath, 200);
                image = BitmapUtil.convertIconToString(bitmap);

                upv_one.setImageViewPic(bitmap);
                upv_one.setAddViewClickable(false);
                upv_two.setAddViewClickable(true);
                upv_two.setAddViewBackground(R.mipmap.add_picture);

            }
        });
        CropOption cropOption = new CropOption();
        cropOption.outputX = 200;
        cropOption.outputY = 200;
        SelectPhotoManager.getInstance().setCropOption(cropOption);
        SelectPhotoManager.getInstance().start(this);
    }

    public void onAddCode(View view) {
        Intent intent = new Intent();
        intent.setClass(GoodsAddActivity.this, MipcaActivityCapture.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
    }

    public void onAddCancel(View view) {
        if (checkInput()) {
            showLoadingDialog(R.string.loading_add_goods);
            GoodsDataModel.addGoods(strCode, strName, strType, strInputMoney, strSellMoney, Strvip, strGuige, strBrand, "1", image);
        }
    }

    public void onAddSure(View view) {
        if (checkInput()) {
            cancel = true;
            showLoadingDialog(R.string.loading_add_goods);
            GoodsDataModel.addGoods(strCode, strName, strType, strInputMoney, strSellMoney, Strvip, strGuige, strBrand, "1", image);
        }
    }

    private boolean checkInput() {
        boolean flag = false;
        String msg = "";
        strCode = tv_code.getText().toString().trim();
        strName = et_name.getText().toString().trim();
        strBrand = et_brand.getText().toString().trim();
        strType = et_type.getText().toString().trim();
        strInputMoney = et_input_money.getText().toString().trim();
        strSellMoney = et_sell_money.getText().toString().trim();
        Strvip = et_vip.getText().toString().trim();
        strUnit = et_unit.getText().toString().trim();
//        if (TextUtils.isEmpty(strInputCount))
//            strInputCount = "0";
        if (type == null) {
            strType = "1";
        }
        strGuige = et_guige.getText().toString().trim();
        if (TextUtils.isEmpty(strCode)) {
            msg = "货号";
        } else if (TextUtils.isEmpty(strName)) {
            msg = "名称";
        }
//        else if (type == null) {
//            msg = "分类";
//        }
//        else if (TextUtils.isEmpty(strBrand)) {
//            msg = "供应商";
//        }
        else if (TextUtils.isEmpty(strInputMoney)) {
            msg = "进价";
        } else if (TextUtils.isEmpty(strSellMoney)) {
            msg = "售价";
        } else if (TextUtils.isEmpty(Strvip)) {
            msg = "Vip价";
        }
//        else if (TextUtils.isEmpty(strGuige)) {
//            msg = "规格";
//       }
        else {
            flag = true;
        }
        if (!flag)
            showShortToast(String.format("商品%s不能为空", msg));
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
