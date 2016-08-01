package com.xgx.syzj.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
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
public class SaleHistoryActivity extends BaseActivity{
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
    private ISaleHistoryItemClick iSaleHistoryItemClick;
    private LoadMoreListViewContainer loadMoreListViewContainer;
    private SaleListRecordModel mDataModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sale_history);
        initView();
    }

    private void initView() {
        setTitleText("单据列表");
        setSubmit("筛选");
        btnPayType = (Button) findViewById(R.id.btn_submit);
        lv_data = (SwipeMenuListView) findViewById(R.id.lv_data);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
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
        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (iSaleHistoryItemClick != null)
                    iSaleHistoryItemClick.onItemClick(mList.get(position));
            }
        });
        lv_data.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                deleteItemBill(position);
                return false;
            }
        });
        mDataModel = new SaleListRecordModel(Constants.LOAD_COUNT,mFlag);
        mDataModel.setTime(DateUtil.getStringByFormat(System.currentTimeMillis()-864000001, "yyyy-MM-dd"),
                DateUtil.getStringByFormat(System.currentTimeMillis(), "yyyy-MM-dd"));

        loadMoreListViewContainer = (LoadMoreListViewContainer)
                findViewById(R.id.load_more_list_view_container);
        loadMoreListViewContainer.useDefaultFooter();
        loadMoreListViewContainer.setShowLoadingForFirstPage(true);
        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                mDataModel.queryNextPage();
            }
        });

        for(int i=0;i<10;i++){
            BillListItemBean shy=new BillListItemBean();
            List<BillListItemBean.BillDetailsEntity> list=new ArrayList<>();
            for(int j=0;j<3;j++){
                BillListItemBean.BillDetailsEntity bde=new BillListItemBean.BillDetailsEntity();
                bde.setProductName(mFlag+"水壶"+i);
                bde.setQuantity(j);
                bde.setTotalValue(j+0.2);
                bde.setStoreId(j);
                bde.setBillDetailsId(j);
                bde.setBillId(j);
                bde.setSellingPrice(2.3+j);
                list.add(bde);
            }
            shy.setBillDetails(list);
            shy.setCustomerType(1);
            shy.setBillDatetime(System.currentTimeMillis());
            shy.setAssociatorId(1);
            shy.setFlag(1);
            shy.setBillId(i);
            shy.setModeOfPay(2);
            shy.setAssociatorName("这样"+i);
            shy.setPaidValue(20.2+i);
            shy.setReceivableValue(i);
            shy.setDescription("sd"+i);
            shy.setReturnValue(i);
            shy.setStoreId(i);
            mList.add(shy);
        }

        mAdapter = new SaleHistroyAdapter(getActivity(),mFlag,mList);
        saleDetailAdapter = new SaleDetailAdapter(getActivity());
        lv_data.setAdapter(mAdapter);
        lv_data.setOnItemClickListener(itemClickListener);
        mDataModel.queryNextPage();
    }

    public void onEventMainThread(BillEventPostData<BillListItemBean> billData){

        if(billData.str.equals(mFlag)){
            loadMoreListViewContainer.loadMoreFinish(mDataModel.getListPageInfo().isEmpty(),
                    mDataModel.getListPageInfo().hasMore());
            if(mList.size() > 0 ){
                if(mList.get(0).getBillDatetime() != billData.dataList.get(0).getBillDatetime()){
                    mAdapter.appendList(mList);
                }
            }else {
                mAdapter.appendList(billData.dataList);
            }
            mAdapter.notifyDataSetChanged();
        }

    }

    private AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            BillListItemBean shy=mList.get(position);
            if(shy == null) return;
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(SALE_BILL_ITEM,reSetData(shy));
            gotoActivityForResult(SaleDetailActivity.class,bundle,0x01);
        }
    };

    public void onEventMainThread(Bundle bundle){
        if(bundle != null){
            mList.clear();
            mDataModel.setCustomerType(mFlag);
            mDataModel.setTime(bundle.getString("startTime"),bundle.getString("currentTime"));
            mDataModel.queryNextPage();
            mAdapter.notifyDataSetChanged();
        }
    }



    public void onEventMainThread(Map map){
        if(map != null)
            for(BillListItemBean bim : mList){
                for (int i = 0 ;i<bim.getBillDetails().size();i++){
                    int reId = Integer.parseInt(map.get("returnId").toString());
                    if(reId == bim.getBillDetails().get(i).getBillDetailsId()){
                        bim.getBillDetails().remove(bim.getBillDetails().get(i));
                    }
                }
                if(bim.getBillDetails().size() == 0){
                    mList.remove(bim);
                }
            }
        mAdapter.notifyDataSetChanged();
    }


    public void onEventMainThread(Result result){
        if(result.geteCode() == mDataModel.DELETE_SALE_RECORD){
            mList.remove(mList.get(deleteIndex));
            showShortToast("取消该订单成功");
        }
        mAdapter.notifyDataSetChanged();
    }


    private void deleteItemBill(int position) {
        deleteIndex = position;
        SaleListRecordModel.doRequst(mList.get(position).getBillId());
    }
//
//    @Override
//    public void notifyChangeData(int id) {
//
//    }

    public interface ISaleHistoryItemClick {
        void onItemClick(BillListItemBean shy);
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
