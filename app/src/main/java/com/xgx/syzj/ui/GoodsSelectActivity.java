package com.xgx.syzj.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xgx.syzj.R;
import com.xgx.syzj.adapter.GoodsListAdapter;
import com.xgx.syzj.adapter.GoodsSelectAdapter;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.GoodAddModel;
import com.xgx.syzj.datamodel.GoodsDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;

/**
 * 商品门店管理
 */
public class GoodsSelectActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    private ListView lv_products;
    private GoodsSelectAdapter mAdapter = null;
    private LoadMoreListViewContainer loadMoreListViewContainer;
    private ArrayList<Goods> products = new ArrayList<>();
    private EditText et_text;
    private Button btn_sure;
    private GoodAddModel mDataModel;;
    private List  mList=new ArrayList<Goods>();;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        setTitleText(getString(R.string.main_manage_goods));
        initView();
        EventCenter.bindContainerAndHandler(this, eventHandler);
        EventBus.getDefault().registerSticky(eventHandler);
        mDataModel = new GoodAddModel(20);
        loadMoreListViewContainer = (LoadMoreListViewContainer) findViewById(R.id.load_more_list_view_container);
        loadMoreListViewContainer.useDefaultFooter();
        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                mDataModel.queryNextPage();
            }
        });

        mDataModel.queryNextPage();
        dialog.show();
        mDataModel.addProductByStore(new Goods().getStoreId());
        mAdapter = new GoodsSelectAdapter(GoodsSelectActivity.this, products, onItemCheck);
        lv_products.setAdapter(mAdapter);
        lv_products.setOnItemClickListener(this);
        setListener();
        btn_sure.setOnClickListener(this);
        Log.v("zjj","123444444444444444");
    }

    private void initView() {
        et_text = (EditText) findViewById(R.id.et_text);
        btn_sure = (Button) findViewById(R.id.btn_sure);
        lv_products = (ListView) findViewById(R.id.lv_goods);
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(List<Goods> list) {
            loadMoreListViewContainer.loadMoreFinish(mDataModel.getListPageInfo().isEmpty(), mDataModel.getListPageInfo().hasMore());
//            mAdapter.appendList(list);
              products.addAll(list);
              hideLoadingDialog();
        }

        public void onEvent(String error) {
            hideLoadingDialog();
            showShortToast(error);
            loadMoreListViewContainer.loadMoreError(0, error);
        }

    };

    private TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                products.clear();
                mAdapter.notifyDataSetChanged();
                String text = et_text.getText().toString().trim();
                mDataModel.setKey(text);
                mDataModel.setBrand(text);
                mDataModel.queryNextPage();
                Utils.hideSoftInput(GoodsSelectActivity.this);
            }
            return false;
        }
    };

    private void setListener() {
        et_text.setOnEditorActionListener(onEditorActionListener);


    }



    private GoodsSelectAdapter.IGoodsItemCheck onItemCheck = new GoodsSelectAdapter.IGoodsItemCheck() {
        @Override
        public void onItemCheck(Map<Integer,Goods> list, int position) {
            if (list != null && list.size() >= 1) {
                btn_sure.setVisibility(View.VISIBLE);

            } else {
                btn_sure.setVisibility(View.GONE);
            }
        }
    };


    @Override
    public void onClick(View view) {
        Map<Integer, Goods> selected = mAdapter.getSelected();
        ArrayList<Goods> data = new ArrayList();
        for (Goods value : selected.values()){
            data.add(value);
        }
        mList.addAll(data);
        GoodAddModel.addProduct(mList);
        mList.clear();
        gotoActivity(GoodsListActivity.class);
        finish();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        CheckBox cb = (CheckBox) view.findViewById(R.id.cb);
        cb.setChecked(!cb.isChecked());

    }
}
