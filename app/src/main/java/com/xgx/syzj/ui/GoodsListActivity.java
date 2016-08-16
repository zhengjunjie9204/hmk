package com.xgx.syzj.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.xgx.syzj.R;
import com.xgx.syzj.adapter.GoodsListAdapter;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.AddGoods;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.bean.GoodsCategory;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.GoodsDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.CacheUtil;
import com.xgx.syzj.utils.Utils;
import com.xgx.syzj.widget.CustomAlertDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;

/**
 * 商品列表
 *
 * @author zajo
 * @created 2015年08月19日 17:44
 */
public class GoodsListActivity extends BaseActivity  {

    public static final String FLAG = "FLAG";
    public static final String FLAG_EXC_JIFEN = "FLAG_EXC_JIFEN";//兑换积分调用

    private SwipeMenuListView lv_goods;
    private LoadMoreListViewContainer loadMoreListViewContainer;
    private GoodsListAdapter mAdapter;
    private ArrayList<Goods> goods = new ArrayList<>();
    private GoodsDataModel mDataModel;
    private EditText et_text;
    private TextView tv_count;
    private String flag;
    private int deleteIndex = -1;
    private int storeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_goods_list);

        setTitleText(getString(R.string.main_manage_goods));
        setSubmit(getString(R.string.goods_list_add));


        Bundle bundle=getIntent().getExtras();
        //获取商品信息
//        String type=bundle.getString("type");
        et_text = (EditText) findViewById(R.id.et_text);
        if(CacheUtil.getmInstance().getUser().getRoles()==1){
            et_text.setVisibility(View.GONE);
        }
        tv_count = (TextView) findViewById(R.id.tv_count);
        et_text.setOnEditorActionListener(onEditorActionListener);
        lv_goods = (SwipeMenuListView) findViewById(R.id.lv_goods);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(GoodsListActivity.this);
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                deleteItem.setWidth(Utils.dp2px(GoodsListActivity.this, 90));
                deleteItem.setIcon(R.mipmap.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        };
        lv_goods.setMenuCreator(creator);
        lv_goods.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                deleteItem(position);
                return false;
            }
        });
        EventCenter.bindContainerAndHandler(this, eventHandler);
        EventBus.getDefault().registerSticky(eventHandler);
        mDataModel = new GoodsDataModel(Constants.LOAD_COUNT);

        loadMoreListViewContainer = (LoadMoreListViewContainer) findViewById(R.id.load_more_list_view_container);
        loadMoreListViewContainer.useDefaultFooter();
        loadMoreListViewContainer.setShowLoadingForFirstPage(true);
        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                mDataModel.queryNextPage();
            }
        });
        storeId = CacheUtil.getmInstance().getUser().getStoreId();
        mDataModel.setStoreId(storeId);
        mAdapter = new GoodsListAdapter(this, this.goods);
        lv_goods.setAdapter(mAdapter);
        lv_goods.setOnItemClickListener(onItemClickListener);

        mDataModel.queryNextPage();


        flag = getIntent().getStringExtra(FLAG);
        if(CacheUtil.getmInstance().getUser().getRoles()==1){
            mDataModel.getAllProduct();
        }else {
            mDataModel.getProductsList();
        }
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(List<Goods> list) {
            loadMoreListViewContainer.loadMoreFinish(mDataModel.getListPageInfo().isEmpty(), mDataModel.getListPageInfo().hasMore());
            mAdapter.appendList(list);
            mAdapter.notifyDataSetChanged();
            tv_count.setText("共"+mAdapter.getCount()+"种商品");
        }

        public void onEvent(Result result) {
            hideLoadingDialog();
            if (result.geteCode() == GoodsDataModel.DELETE_SUCCESS){
                showShortToast("删除成功");
                Goods g = goods.get(deleteIndex);
                goods.remove(g);
                deleteIndex = -1;
                mAdapter.notifyDataSetChanged();
                tv_count.setText("共"+mAdapter.getCount()+"种商品");
            }else if(result.geteCode() == GoodsDataModel.DELETE_SUCCESS_TWO){
                showShortToast("删除成功");
                Goods g = goods.get(deleteIndex);
                goods.remove(g);
                mAdapter.notifyDataSetChanged();
                tv_count.setText("共"+mAdapter.getCount()+"种商品");
            }


        }

        public void onEvent(String error) {
            showShortToast(error);
            loadMoreListViewContainer.loadMoreError(0, error);
        }

        public void onEvent(Goods g) {
            //修改商品详情
            for (Goods gd : goods) {
                if (gd.getProductId() == g.getProductId()) {
                    gd.setProductName(g.getProductName());
                    gd.setQuantity(g.getQuantity());
                    gd.setSellingPrice(g.getSellingPrice());
                    gd.setBarcode(g.getBarcode());
                    gd.setCategoryId(g.getCategoryId());
                    gd.setCategoryName(g.getCategoryName());
                    gd.setInputPrice(g.getInputPrice());
                    gd.setSpecification(g.getSpecification());
                    gd.setSupplier(g.getSupplier());
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    };

    private TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                goods.clear();
                mAdapter.notifyDataSetChanged();
                String text = et_text.getText().toString().trim();
                //根据key
                mDataModel.setKey(text);
                //根据品牌
                int storeId = CacheUtil.getmInstance().getUser().getStoreId();
                mDataModel.setStoreId(storeId);
                mDataModel.setBrand(text);
                mDataModel.getProductsList();
                mDataModel.queryNextPage();
                mDataModel.getProductsList();
                Utils.hideSoftInput(GoodsListActivity.this);
            }
            return false;
        }
    };

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if (!TextUtils.isEmpty(flag) && flag.equals(FLAG_EXC_JIFEN)) {
                Intent dataIntent = new Intent();
                dataIntent.putExtra("goods", (Goods) mAdapter.getItem(position));
                setResult(RESULT_OK, dataIntent);
                defaultFinish();
            } else {
                Bundle bundle = new Bundle();
                bundle.putParcelable("goods", (Goods) mAdapter.getItem(position));
                gotoActivityForResult(GoodsDetailActivity.class, bundle, 2001);
            }
        }
    };

    @Override
    protected void submit() {
        if(CacheUtil.getmInstance().getUser().getRoles()==1) {
            gotoActivity(GoodsAddActivity.class);
            finish();
        }else{
            gotoActivity(GoodsSelectActivity.class);
            finish();
        }
    }


    private void deleteItem(final int position) {
        CustomAlertDialog.showRemindDialog(this, "提醒", " 确定要删除该商品吗？", new CustomAlertDialog.IAlertDialogListener() {
            @Override
            public void onSure(Object obj) {
                deleteIndex = position;
                Goods g = goods.get(deleteIndex);
                showLoadingDialog(R.string.loading_delete_goods);
                GoodsDataModel.deleteGoods(g.getUid());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == 2000) {
            GoodsCategory category = data.getParcelableExtra("type");
            if (category == null) return;
            goods.clear();
            mAdapter.notifyDataSetChanged();
            //mDataModel.setCategoryId(category.getCategoryId());
            mDataModel.queryNextPage();
        } else if (requestCode == 2001) {
            Goods g = data.getParcelableExtra("goods");
            if (g == null) return;
            for (Goods source : goods) {
                if (source.getCategoryId() == g.getCategoryId()) {
                    goods.remove(source);
                    break;
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }

}
