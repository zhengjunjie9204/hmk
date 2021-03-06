package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.adapter.RevenueGoodListAdapter;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.datamodel.GoodsDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.CacheUtil;
import com.xgx.syzj.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;

/**
 * 请选择商品列表
 *
 * @author ding
 * @create 2016/7/31 17:15
 */
public class RevenueGoodsListActivity extends BaseActivity{
    private LoadMoreListViewContainer loadMoreListViewContainer;
    private RevenueGoodListAdapter mAdapter;
    private ArrayList<Goods> mList = new ArrayList<>();
    private EditText et_text;
    private TextView tv_count;
    private GoodsDataModel mDataModel;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_goods_list);
        setSubmit("确定");
        initView();
        initListener();

        mDataModel.getProductsList();
    }

    private void initView() {
        setTitleText(getString(R.string.goods_list));
        et_text = (EditText) findViewById(R.id.et_text);
        tv_count = (TextView) findViewById(R.id.tv_count);
        et_text.setOnEditorActionListener(onEditorActionListener);
        mListView = (ListView) findViewById(R.id.lv_goods);
        mAdapter = new RevenueGoodListAdapter(this, mList, null);
        mListView.setAdapter(mAdapter);
    }

    private void initListener() {
        EventCenter.bindContainerAndHandler(this, eventHandler);
        EventBus.getDefault().registerSticky(eventHandler);
        mDataModel = new GoodsDataModel(Constants.LOAD_COUNT);
        mDataModel.setStoreId(CacheUtil.getmInstance().getUser().getStoreId());
        loadMoreListViewContainer = (LoadMoreListViewContainer) findViewById(R.id.load_more_list_view_container);
        loadMoreListViewContainer.useDefaultFooter();
        loadMoreListViewContainer.setShowLoadingForFirstPage(true);
        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                mDataModel.queryNextPage();
            }
        });
    }


    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(List<Goods> list) {
            loadMoreListViewContainer.loadMoreFinish(mDataModel.getListPageInfo().isEmpty(), mDataModel.getListPageInfo().hasMore());
            mList.addAll(list);
            mAdapter.notifyDataSetChanged();
        }

        public void onEvent(String error) {
            showShortToast(error);
            loadMoreListViewContainer.loadMoreError(0, error);
        }
    };

    private TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mList.clear();
                mAdapter.notifyDataSetChanged();
                String text = et_text.getText().toString().trim();
                mDataModel.setKey(text);
                mDataModel.queryNextPage();
                Utils.hideSoftInput(RevenueGoodsListActivity.this);
            }
            return false;
        }
    };

    @Override
    public void onSubmit(View view) {
        super.onSubmit(view);
        List<Goods> list=mAdapter.getSellList();
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra("goodsList", (ArrayList<? extends Parcelable>) list);
            setResult(RESULT_OK, intent);
            defaultFinish();
        } else {
            showShortToast("请选择商品");
        }

    }
}
