package com.xgx.syzj.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.xgx.syzj.R;
import com.xgx.syzj.adapter.OrderListAdapter;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.OrderList;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.SaleListRecordModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.Utils;
import com.xgx.syzj.widget.CustomAlertDialog;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;

/**
 * 收银记录
 *
 * @author sam
 * @created 2015年09月24日 14:22
 */
public class SaleHistoryActivity extends BaseActivity {
    public static final String SALE_MEMBER = "MEMBER";
    public static final String SALE_BILL_ITEM = "BIllITEMDETAIL";
    private LoadMoreListViewContainer loadMoreListViewContainer;
    private SwipeMenuListView lv_data;
    private SaleListRecordModel mDataModel;
    private OrderListAdapter mAdapter;
    private List<OrderList> mDataList;
    private int cancelPosition;
    private EditText mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_history);
        initView();
        initListener();
        EventCenter.bindContainerAndHandler(this, eventHandler);
        mDataModel = new SaleListRecordModel(Constants.LOAD_COUNT);
        mDataModel.payOrder("","","","","");
        mDataModel.queryFirstPage();
    }

    private void initView()
    {
        setTitleText("单据列表");
        setSubmit("筛选");
        mDataList = new ArrayList<>();
        mSearch = (EditText)findViewById(R.id.et_text);
        mSearch.setOnEditorActionListener(onEditorActionListener);
        lv_data = (SwipeMenuListView) findViewById(R.id.lv_data);
        loadMoreListViewContainer = (LoadMoreListViewContainer) findViewById(R.id.load_more_list_view_container);
        loadMoreListViewContainer.useDefaultFooter();
        loadMoreListViewContainer.setShowLoadingForFirstPage(true);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu)
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                deleteItem.setWidth(Utils.dp2px(getActivity(), 90));
                deleteItem.setTitle("作废");
                deleteItem.setTitleColor(getResources().getColor(R.color.white));
                deleteItem.setTitleSize(18);
                menu.addMenuItem(deleteItem);
            }
        };
        lv_data.setMenuCreator(creator);
        mAdapter = new OrderListAdapter(this, mDataList);
        lv_data.setAdapter(mAdapter);
    }

    private void initListener()
    {
        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(SaleHistoryActivity.this,SaleDetailActivity.class);
                intent.putExtra("order",mDataList.get(position));
                startActivity(intent);
            }
        });
        lv_data.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index)
            {
                CustomAlertDialog.showRemindDialog(SaleHistoryActivity.this, "温馨提示", "是否删除", new CustomAlertDialog.IAlertDialogListener() {
                    @Override
                    public void onSure(Object obj) {

                        showLoadingDialog(R.string.loading_date);
                        cancelPosition = position;
                        mDataModel.setOrderCancel(String.valueOf(mDataList.get(position).getId()));

                    }
                });
                return true;
            }
        });
        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer)
            {
                mDataModel.queryNextPage();
            }
        });
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(List<OrderList> list)
        {
            mDataList.addAll(list);
            mAdapter.notifyDataSetChanged();
        }

        public void onEvent(Result result)
        {
            hideLoadingDialog();
            if (result.getStatus() == 200) {
                if (mDataList.size() > cancelPosition) {
                    mDataList.remove(cancelPosition);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }

        public void onEvent(String error)
        {
            hideLoadingDialog();
            showShortToast(error);
            loadMoreListViewContainer.loadMoreError(0, error);
        }
    };

    @Override
    public void onSubmit(View view) {
        super.onSubmit(view);
        Bundle bundle = new Bundle();
        gotoActivityForResult(SaleHistoryFilterActivity.class,bundle,2003);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    private TextView.OnEditorActionListener onEditorActionListener= new TextView.OnEditorActionListener(){

        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mDataList.clear();
                mAdapter.notifyDataSetChanged();
                String text = mSearch.getText().toString().trim();
                //根据key
                mDataModel.payOrder(text,"","","","");
                mAdapter.notifyDataSetChanged();
                Utils.hideSoftInput(SaleHistoryActivity.this);
            }


            return false;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == 2003) {
            mDataList.clear();
            mAdapter.notifyDataSetChanged();
            String maxTime = data.getStringExtra("maxTime");
            String minTime = data.getStringExtra("minTime");
            String minmoney = data.getStringExtra("minmoney");
            String maxmoney = data.getStringExtra("maxmoney");
            mDataModel.payOrder("",minmoney,maxmoney,minTime,maxTime);
            mAdapter.notifyDataSetChanged();
        }
    }
}
