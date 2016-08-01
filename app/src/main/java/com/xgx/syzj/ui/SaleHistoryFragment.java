package com.xgx.syzj.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.xgx.syzj.R;
import com.xgx.syzj.adapter.SaleDetailAdapter;
import com.xgx.syzj.adapter.SaleHistroyAdapter;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.bean.BillListItemBean;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.SaleListRecordModel;
import com.xgx.syzj.event.BillEventPostData;
import com.xgx.syzj.utils.DateUtil;
import com.xgx.syzj.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;

/**
 * @author zajo
 * @created 2015年11月24日 15:49
 */
public class SaleHistoryFragment extends Fragment/* implements SaleHistoryInterfaces*/{


    private String mFlag = "";
    private SwipeMenuListView lv_data;
    private SaleHistroyAdapter mAdapter;
    private SaleDetailAdapter saleDetailAdapter;
    private List<BillListItemBean> mList = new ArrayList<>();
    private int deleteIndex = -1;
    private ISaleHistoryItemClick iSaleHistoryItemClick;
    private LoadMoreListViewContainer loadMoreListViewContainer;
    private SaleListRecordModel mDataModel;
    private int returnId = -1;
    private Context mContext;

    public SaleHistoryFragment() {
        super();
    }

    @SuppressLint("ValidFragment")
    public SaleHistoryFragment(Context context ,String flag) {
        super();
        this.mContext = context;
        this.mFlag = flag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onAttach(Activity activity) {
        if (!(activity instanceof ISaleHistoryItemClick)){
            throw new ClassCastException();
        }
        iSaleHistoryItemClick = (ISaleHistoryItemClick) activity;
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale_history, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        lv_data = (SwipeMenuListView) view.findViewById(R.id.lv_data);
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
                view.findViewById(R.id.load_more_list_view_container);
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
         Toast.makeText(mContext,"取消该订单成功",Toast.LENGTH_SHORT);
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
}
