package com.xgx.syzj.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Attendance;
import com.xgx.syzj.datamodel.AttendanceListDataModel;
import com.xgx.syzj.event.AttendanceListDataEvent;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.viewholders.AttendanceListItemViewHolder;
import com.xgx.syzj.widget.list.PagedListViewDataAdapter;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 考勤管理
 *
 * @author zajo
 * @created 2015年09月25日 11:56
 */
public class AttendanceActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener {

    private ListView lv_data;
    private PtrFrameLayout mPtrFrame;
    private AttendanceListDataModel mDataModel;
    private PagedListViewDataAdapter<Attendance> adapter;
//    private AttendanceAdapter mAdapter;
    private List<Attendance> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        setTitleText("考勤管理");
        mDataModel = new AttendanceListDataModel("a1","a2");
        adapter = new PagedListViewDataAdapter<>();
        adapter.setViewHolderClass(this, AttendanceListItemViewHolder.class);
        adapter.setListPageInfo(mDataModel.getListPageInfo());

        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_frame);
        mPtrFrame.setLoadingMinTime(1000);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mDataModel.queryFirstPage();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, lv_data, header);
            }
        });

        lv_data = (ListView) findViewById(R.id.lv_data);

        final LoadMoreListViewContainer loadMoreListViewContainer = (LoadMoreListViewContainer)findViewById(R.id.load_more_list_view_container);
//        loadMoreListViewContainer.setAutoLoadMore(false);
        loadMoreListViewContainer.useDefaultFooter();

        lv_data.setAdapter(adapter);

        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                mDataModel.queryNextPage();
            }
        });

        EventCenter.bindContainerAndHandler(this, new SimpleEventHandler() {
            public void onEvent(AttendanceListDataEvent event) {
                // ptr
                mPtrFrame.refreshComplete();
                // load more
                loadMoreListViewContainer.loadMoreFinish(mDataModel.getListPageInfo().isEmpty(), mDataModel.getListPageInfo().hasMore());
                adapter.notifyDataSetChanged();
            }

            public void onEvent(String msg){
                loadMoreListViewContainer.loadMoreError(0, msg);
            }
        });

        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh(false);
            }
        }, 150);

        lv_data.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        gotoActivity(AttendanceDetailActivity.class);
    }
}
