package com.xgx.syzj.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.xgx.syzj.R;
import com.xgx.syzj.adapter.ConsumeHistoryAdapter;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.ConsumeHistory;
import com.xgx.syzj.bean.Member;
import com.xgx.syzj.datamodel.BillListRecordModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.DateUtil;
import com.xgx.syzj.utils.Utils;
import com.xgx.syzj.widget.ConsumptionPopupWindowUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;

/**
 * 会员消费记录
 *
 * @author zajo
 * @created 2015年09月21日 14:20
 */
public class MemberConsumptionActivity extends BaseActivity {
    private Button btnPayType;
    private SwipeMenuListView lv_bill_record;
    private int deleteIndex = -1, customerType = 1, flag = 0, selectData = -30;
    private ConsumeHistoryAdapter mAdapter;
    private LoadMoreListViewContainer loadMoreListViewContainer;
    private BillListRecordModel billListRecordModel;
    private Member member;
    private String currentTime, startTime;
    private Date curDate;
    private TextView tv_pay_count, tv_nopay_count;
    private List<ConsumeHistory> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_consumption);
        setData();
        initView();

    }

    private void initView()
    {
        setTitleText(getString(R.string.member_consumption_title));
//        setSubmit(getString(R.string.member_consumption_pay_type));
        btnPayType = (Button) findViewById(R.id.btn_submit);
        lv_bill_record = (SwipeMenuListView) findViewById(R.id.lv_data);
        loadMoreListViewContainer = (LoadMoreListViewContainer) findViewById(R.id.load_more_list_view_container);
        tv_pay_count = (TextView) findViewById(R.id.tv_pay_count);
        tv_nopay_count = (TextView) findViewById(R.id.tv_nopay_count);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu)
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(MemberConsumptionActivity.this);
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                deleteItem.setWidth(Utils.dp2px(MemberConsumptionActivity.this, 90));
                deleteItem.setIcon(R.mipmap.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        };

        loadMoreListViewContainer.useDefaultFooter();
        loadMoreListViewContainer.setShowLoadingForFirstPage(true);
        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer)
            {
                billListRecordModel.queryNextPage();
            }
        });

        mAdapter = new ConsumeHistoryAdapter(MemberConsumptionActivity.this, mList);
        lv_bill_record.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void setData()
    {
        member = getIntent().getParcelableExtra("member");
        if (member == null) {
            defaultFinish();
        }
        EventCenter.bindContainerAndHandler(this, simpleEventHandler);
        EventBus.getDefault().registerSticky(simpleEventHandler);
        billListRecordModel = new BillListRecordModel(Constants.LOAD_COUNT, member.getId());
//        billListRecordModel.getMemberPayRecord(member.getId(), startTime, currentTime, customerType, flag);
        billListRecordModel.queryFirstPage();
    }

    private SimpleEventHandler simpleEventHandler = new SimpleEventHandler() {

        public void onEvent(List<ConsumeHistory> list)
        {
            loadMoreListViewContainer.loadMoreFinish(billListRecordModel.getListPageInfo().isEmpty(), billListRecordModel.getListPageInfo().hasMore());
            mAdapter.appendList(list);
            mAdapter.notifyDataSetChanged();
        }

        public void onEvent(JSONObject object)
        {
            double consumeMoney = object.getDoubleValue("consumeMoney");
            double consumeTimes = object.getDoubleValue("consumeTimes");
            tv_pay_count.setText("￥"+consumeMoney);
            tv_nopay_count.setText(""+consumeTimes);
        }

        public void onEvent(String error)
        {
            showShortToast(error);
            loadMoreListViewContainer.loadMoreError(0, error);
        }
    };

    private ConsumptionPopupWindowUtil.IPopupWindowCallListener ipopCallListener = new ConsumptionPopupWindowUtil.IPopupWindowCallListener() {

        @Override
        public void onItemClick(int index)
        {
            if (index == 1) {
                btnPayType.setText("30天内");
                selectData = -30;
                selectBillpay();
            } else if (index == 2) {
                btnPayType.setText("3个月内");
                selectData = -90;
                selectBillpay();
            } else if (index == 3) {
                btnPayType.setText("1年内");
                selectData = -365;
                selectBillpay();
            }
        }
    };

    private void selectBillpay()
    {
        startTime = DateUtil.getStringByOffset(curDate, DateUtil.dateFormatYMDHMS, Calendar.DATE, selectData);
        billListRecordModel.setTime(startTime);
        mList.clear();
        mAdapter.notifyDataSetChanged();
        billListRecordModel.queryNextPage();
    }

    @Override
    protected void submit()
    {
        super.submit();
        new ConsumptionPopupWindowUtil<Object>(this, ipopCallListener)
                .showActionWindow(btnPayType);
    }
}
