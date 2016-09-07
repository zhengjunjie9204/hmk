package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.adapter.GoodsSelectAdapter;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.GoodAddModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.CacheUtil;
import com.xgx.syzj.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
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
    private GoodAddModel mDataModel;
    private List<Goods> mList=new ArrayList<>();
    private int mProductId;
    private HashMap<Integer, String> goodsMap;
    private TextView tv_count;
    private ArrayList<Long> ProductIdList;
    private ArrayList<Long> ProductId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        setTitleText(getString(R.string.main_manage_goods));
        initView();
        Intent intent = getIntent();
        goodsMap= new HashMap<>();
        ArrayList<Goods> Goodss = intent.getParcelableArrayListExtra("goodsArray");
        if(Goodss.size() > 0){
            for (Goods goods  : Goodss) {
                mProductId = goods.getProductId();
                goodsMap.put(mProductId,"");
            }
        }
        EventCenter.bindContainerAndHandler(this, eventHandler);
        EventBus.getDefault().registerSticky(eventHandler);
        mDataModel = new GoodAddModel(Constants.LOAD_COUNT);
        loadMoreListViewContainer = (LoadMoreListViewContainer) findViewById(R.id.load_more_list_view_container);
        loadMoreListViewContainer.useDefaultFooter();
        loadMoreListViewContainer.setShowLoadingForFirstPage(true);
        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                mDataModel.queryNextPage();
            }
        });
        mDataModel.queryFirstPage();
        dialog.show();
        int storeId = CacheUtil.getmInstance().getUser().getStoreId();
        mAdapter = new GoodsSelectAdapter(GoodsSelectActivity.this, products, onItemCheck);
        lv_products.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        lv_products.setOnItemClickListener(this);
        setListener();
        btn_sure.setOnClickListener(this);

    }

    private void initView() {
        et_text = (EditText) findViewById(R.id.et_text);
        btn_sure = (Button) findViewById(R.id.btn_sure);
        lv_products = (ListView) findViewById(R.id.lv_goods);
        tv_count =(TextView)findViewById(R.id.tv_count);
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(List<Goods> list) {
            hideLoadingDialog();
            loadMoreListViewContainer.loadMoreFinish(mDataModel.getListPageInfo().prepareForNextPage(), mDataModel.getListPageInfo().hasMore());
            for ( Goods goods : list) {
                    if(!(goodsMap.containsKey(goods.getProductId()))){
                    products.add(goods);
                }
                }
            tv_count.setText("共"+products.size()+"种商品");
            mAdapter.notifyDataSetChanged();
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
        int storeid = SYZJApplication.getInstance().getSpUtil().getInt("SP_STORE_ID");
        mList.addAll(data);
        ProductIdList = new ArrayList<>();
        ProductId=new ArrayList<>();
        for (Goods g : mList) {
            int productId = g.getProductId();

            ProductId.add((long)productId);
        }
        ProductIdList.addAll(ProductId);
        Api.addProductToStore(storeid,ProductIdList,new BaseRequest.OnRequestListener(){

            @Override
            public void onSuccess(Result result) {
                if(result.getStatus()==200){
                    mList.clear();
                    Intent intent = new Intent(GoodsSelectActivity.this, GoodsListActivity.class);
                    intent.putExtra("增加商品",1000);
                    startActivity(intent);
                    finish();
                }
                showShortToast(result.getMessage());
            }

            @Override
            public void onError(String errorCode, String message) {
                showShortToast(message);
            }
        });

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        CheckBox cb = (CheckBox) view.findViewById(R.id.cb);
        cb.setChecked(!cb.isChecked());

    }
}
