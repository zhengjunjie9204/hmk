package com.xgx.syzj.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.xgx.syzj.R;
import com.xgx.syzj.adapter.SaleDetailAdapter;
import com.xgx.syzj.adapter.SaleHistroyAdapter;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.BillItemDetailBean;
import com.xgx.syzj.bean.BillListItemBean;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.SaleListRecordModel;
import com.xgx.syzj.event.BillEventPostData;
import com.xgx.syzj.utils.DateUtil;
import com.xgx.syzj.utils.Utils;
import com.xgx.syzj.widget.ConsumptionPopupWindowUtil;
import com.xgx.syzj.widget.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
public class SaleHistoryActivity extends BaseActivity implements SaleHistoryFragment.ISaleHistoryItemClick {
    private String currentTime,startTime;
    private Date curDate;
    private int selectData =-30;
    private Button btnPayType;

    public static final String SALE_ALL = "ALL";
    public static final String SALE_MEMBER = "MEMBER";
    public static final String SALE_GENERAL = "GENERAL";
    public static final String SALE_BILL_ITEM = "BIllITEMDETAIL";
    private String mFlag = "ALL";
    private SwipeMenuListView lv_data;
    private SaleHistroyAdapter mAdapter;
    private SaleDetailAdapter saleDetailAdapter;
    private List<BillListItemBean> mList = new ArrayList<>();
    private int deleteIndex = -1;
    private SaleHistoryFragment.ISaleHistoryItemClick iSaleHistoryItemClick;
    private LoadMoreListViewContainer loadMoreListViewContainer;
    private SaleListRecordModel mDataModel;
    private String[] CONTENT;
    private PagerSlidingTabStrip indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_history);
        initView();
    }

    private void initView() {
        setTitleText("单据列表");
        setSubmit("筛选");
        CONTENT = new String[]{"待完成", "已完成"};
        btnPayType = (Button) findViewById(R.id.btn_submit);
        FragmentPagerAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(adapter);
        indicator = (PagerSlidingTabStrip) findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        // 设置tab样式
        DisplayMetrics dm = getResources().getDisplayMetrics();
        SetTab(SaleHistoryActivity.this, indicator, dm, 50);

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    private ArrayList<BillItemDetailBean> reSetData(BillListItemBean shy){
        ArrayList<BillItemDetailBean> itemList = new ArrayList<BillItemDetailBean>();
        for(int i = 0;i < shy.getBillDetails().size();i++){
            BillItemDetailBean itemDetailBean = new BillItemDetailBean();
            itemDetailBean.setCustomerType(shy.getCustomerType());
            itemDetailBean.setAssociatorName(shy.getAssociatorName());
            itemDetailBean.setAssociatorId(shy.getAssociatorId());
            itemDetailBean.setBillDatetime(shy.getBillDatetime());
            itemDetailBean.setSellingPrice(shy.getBillDetails().get(i).getSellingPrice());
            itemDetailBean.setCostPrice(shy.getBillDetails().get(i).getCostPrice());
            itemDetailBean.setFlag(shy.getBillDetails().get(i).getFlag());
            itemDetailBean.setBillDetailsId(shy.getBillDetails().get(i).getBillDetailsId());
            itemDetailBean.setBillId(shy.getBillDetails().get(i).getBillId());
            itemDetailBean.setQuantity(shy.getBillDetails().get(i).getQuantity());
            itemDetailBean.setStoreId(shy.getBillDetails().get(i).getStoreId());
            itemDetailBean.setProductName(shy.getBillDetails().get(i).getProductName());
            itemDetailBean.setTotalValue(shy.getBillDetails().get(i).getTotalValue());
            itemDetailBean.setReturnReason(shy.getBillDetails().get(i).getReturnReason());
            itemDetailBean.setProductId(shy.getBillDetails().get(i).getProductId());
            itemList.add(itemDetailBean);
        }
        return itemList;
    }


    @Override
    protected void submit() {
        super.submit();
        new ConsumptionPopupWindowUtil<Object>(this, ipopCallListener)
                .showActionWindow(btnPayType);
    }



    private ConsumptionPopupWindowUtil.IPopupWindowCallListener ipopCallListener = new ConsumptionPopupWindowUtil.IPopupWindowCallListener() {

        @Override
        public void onItemClick(int index) {
            if (index == 1) {
                btnPayType.setText("30天内");
                selectData = -30;
                selectBillpay();
            } else if(index == 2) {
                btnPayType.setText("3个月内");
                selectData = -90;
                selectBillpay();
            } else if(index == 3) {
                btnPayType.setText("1年内");
                selectData = -365;
                selectBillpay();
            }
        }
    };

    @Override
    public void onItemClick(BillListItemBean shy) {
        if(shy == null) return;
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(SALE_BILL_ITEM,reSetData(shy));
        gotoActivityForResult(SaleDetailActivity.class,bundle,0x01);
    }

    class MyFragmentAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> mList = new ArrayList<>();

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
            mList.add(new SaleHistoryFragment(false));
            mList.add(new SaleHistoryFragment(true));
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            super.destroyItem(container, position, object);
        }
    }

    private void selectBillpay(){
        curDate = new Date(System.currentTimeMillis());
        currentTime = DateUtil.getStringByOffset(curDate,DateUtil.dateFormatYMDHMS, Calendar.DATE,0);
        startTime = DateUtil.getStringByOffset(curDate,DateUtil.dateFormatYMDHMS, Calendar.DATE,-30);
        startTime = DateUtil.getStringByOffset(curDate,DateUtil.dateFormatYMDHMS, Calendar.DATE,selectData);
        Bundle bundle = new Bundle();
        bundle.putString("currentTime",currentTime);
        bundle.putString("startTime",startTime);
        EventBus.getDefault().post(bundle);
    }
    private void SetTab(Context context, PagerSlidingTabStrip indicator, DisplayMetrics dm, int padsize) {

        indicator.setIndicatorleftrightpadsize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 0, dm));
        // 设置Tab是自动填充满屏幕的
        indicator.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        indicator.setDividerColor(Color.TRANSPARENT);
//        indicator.setDividerColor(R.color.gray);
//        indicator.setDividerPadding(1);
        // 设置Tab底部线的高度
        indicator.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 0, dm));
        // 设置Tab Indicator的高度
        indicator.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // 设置Tab标题文字的大小
        indicator.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 13, dm));
        indicator.setTabPaddingLeftRight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 0, dm));
        indicator.setTabPadding((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 24, dm));
        // 设置Tab Indicator的颜色
        indicator.setIndicatorColor(context.getResources().getColor(R.color.top_bar_color));
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
        indicator.setSelectedTextColor(context.getResources().getColor(R.color.top_bar_color));
        // 取消点击Tab时的背景色
        indicator.setTabBackground(0);
    }



//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (resultCode){
//            case 0x01:
//
//                break;
//            default:
//                break;
//        }
//    }

}
