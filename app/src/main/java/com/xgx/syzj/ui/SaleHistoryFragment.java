package com.xgx.syzj.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.xgx.syzj.adapter.OrderListAdapter;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseFragment;
import com.xgx.syzj.bean.OrderList;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.SaleListRecordModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.utils.Utils;
import com.xgx.syzj.widget.CustomAlertDialog;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;

/**
 * 收银记录
 *
 * @author sam
 * @created 2015年09月24日 14:22
 */
public class SaleHistoryFragment extends BaseFragment {
    public static final String SALE_MEMBER = "MEMBER";
    public static final String SALE_BILL_ITEM = "BIllITEMDETAIL";
    private LoadMoreListViewContainer loadMoreListViewContainer;
    private SwipeMenuListView lv_data;
    private SaleListRecordModel mDataModel;
    private OrderListAdapter mAdapter;
    private List<OrderList> mDataList;
    private int cancelPosition;
    private EditText mSearch;
    private int type;

    public SaleHistoryFragment(int type){
        this.type=type;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sale_history, null);
        initView(view);
        initListener();
        EventCenter.bindContainerAndHandler(this, eventHandler);
        mDataModel = new SaleListRecordModel(Constants.LOAD_COUNT);
        mDataModel.setKey(null, type,null, null, null, null);
        mDataModel.queryFirstPage();
        return view;
    }

    private void initView(View view)
    {
        mDataList = new ArrayList<>();
        mSearch = (EditText)view.findViewById(R.id.et_text);
        mSearch.setOnEditorActionListener(onEditorActionListener);
        lv_data = (SwipeMenuListView) view.findViewById(R.id.lv_data);
        loadMoreListViewContainer = (LoadMoreListViewContainer) view.findViewById(R.id.load_more_list_view_container);
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
        mAdapter = new OrderListAdapter(getActivity(), mDataList);
        lv_data.setAdapter(mAdapter);
    }

    private void initListener()
    {
        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getActivity(),SaleDetailActivity.class);
                intent.putExtra("order",mDataList.get(position));
                startActivity(intent);

            }
        });
        lv_data.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                CustomAlertDialog.showRemindDialog(getActivity(), "温馨提示", "是否删除", new CustomAlertDialog.IAlertDialogListener() {
                    @Override
                    public void onSure(Object obj) {

                        showLoadingDialog(getActivity(), R.string.loading_date);
                        cancelPosition = position;
                        mDataModel.setOrderCancel(String.valueOf(mDataList.get(position).getId()));

                    }
                });
                return true;
            }
        });
        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
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
                if(SaleListRecordModel.DELETE_SALE_RECORD==result.geteCode()) {
                    if (mDataList.size() > cancelPosition) {
                        mDataList.remove(cancelPosition);
                        mAdapter.notifyDataSetChanged();
                    }
                }
                if(SaleListRecordModel.ORDER_LIST_SUCCESS==result.geteCode()&&type==result.getOrderType()){
                    JSONObject object = JSON.parseObject(result.getResult());
                    List<OrderList> list = FastJsonUtil.json2List(object.getString("payOrders"), OrderList.class);
                    mDataList.addAll(list);
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


    private TextView.OnEditorActionListener onEditorActionListener= new TextView.OnEditorActionListener(){

        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String text = mSearch.getText().toString().trim();
                mDataModel.setKey(text,type ,null, null, null, null);
                mDataList.clear();
                mAdapter.notifyDataSetChanged();
                mDataModel.queryFirstPage();
                Utils.hideSoftInput(getActivity());
            }
            return false;
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == 2003) {
            mDataList.clear();
            mAdapter.notifyDataSetChanged();
            String maxTime = data.getStringExtra("maxTime");
            String minTime = data.getStringExtra("minTime");
            String minmoney = data.getStringExtra("minmoney");
            String maxmoney = data.getStringExtra("maxmoney");
            mDataModel.payOrder("",type,minmoney,maxmoney,minTime,maxTime);
            mAdapter.notifyDataSetChanged();
        }
    }


}
