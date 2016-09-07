package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.adapter.ProjectListAdapter;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Project;
import com.xgx.syzj.datamodel.ProjectDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;

/**
 * 商品列表
 *
 * @author zajo
 * @created 2015年08月19日 17:44
 */
public class ProjectListActivity extends BaseActivity{
    private LoadMoreListViewContainer loadMoreListViewContainer;
    private ProjectListAdapter mAdapter;
    private ArrayList<Project> mList = new ArrayList<>();
    private ProjectDataModel mDataModel;
    private EditText et_text;
    private TextView tv_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
        setTitleText(getString(R.string.project_list));
        setSubmit(getString(R.string.app_button_sure));
        et_text = (EditText) findViewById(R.id.et_text);
        tv_count = (TextView) findViewById(R.id.tv_count);
        et_text.setOnEditorActionListener(onEditorActionListener);
        ListView lv_project = (ListView) findViewById(R.id.lv_goods);

        EventCenter.bindContainerAndHandler(this, eventHandler);
        EventBus.getDefault().registerSticky(eventHandler);
        mDataModel = new ProjectDataModel(Constants.LOAD_COUNT);

        loadMoreListViewContainer = (LoadMoreListViewContainer) findViewById(R.id.load_more_list_view_container);
        loadMoreListViewContainer.useDefaultFooter();
        loadMoreListViewContainer.setShowLoadingForFirstPage(true);
        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                mDataModel.queryNextPage();
            }
        });
        mAdapter = new ProjectListAdapter(this, mList,null);
        lv_project.setAdapter(mAdapter);
        mDataModel.queryFirstPage();
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(List<Project> list) {
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
                Utils.hideSoftInput(ProjectListActivity.this);
            }
            return false;
        }
    };

    @Override
    protected void submit() {
        List<Project> list=mAdapter.getSellList();
        if (list.size()>0){
            Intent intent=new Intent();
            Bundle bundle=new Bundle();
            for (Project project:list){
                double time;
                double laborTime = project.getLaborTime();
                double mylaborTime = project.getMylaborTime();
                if(mylaborTime>0){
                    time=mylaborTime;
                }else{
                    time=laborTime;
                }
                if(project.isFinish()){
                    project.setPrice(project.getTotalPrice() );
                }else {
                    if(project.isChangePrice()){
                        project.setPrice(project.getPrice());
                    }else {
                        project.setPrice((project.getPrice() / project.getLaborTime() )* time );
                    }
                }
            }
            bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
            intent.putExtras(bundle);
            setResult(RESULT_OK,intent);
            defaultFinish();
        }
    }
}
