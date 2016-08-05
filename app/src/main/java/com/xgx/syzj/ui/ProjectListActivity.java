package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.adapter.ProjectListAdapter;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Goods;
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
public class ProjectListActivity extends BaseActivity implements AdapterView.OnItemClickListener{


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

        mAdapter = new ProjectListAdapter(this, mList,deleteItemCount,textChange);
        lv_project.setAdapter(mAdapter);
        lv_project.setOnItemClickListener(this);

        mDataModel.queryNextPage();


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
        //资金流水和报表加上选门店
    };


    private ProjectListAdapter.IDeleteItemCount deleteItemCount = new ProjectListAdapter.IDeleteItemCount() {
        @Override
        public void onItemDelete(int position) {
            Project project = mList.get(position);
            double count = project.getLaborTime();
            if (count == 0.5) {
                mList.remove(position);
            } else {
                project.setLaborTime(count - 0.5);
            }
            mAdapter.notifyDataSetChanged();
        }
    };

    private ProjectListAdapter.ITextChange textChange = new ProjectListAdapter.ITextChange() {
        @Override
        public void onTextChange(int position, String s) {
            if (!TextUtils.isEmpty(s)) {
                Project project = mList.get(position);
                double count = Double.parseDouble(s);
                project.setLaborTime(count);
            }
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Project project=mList.get(position);
        Intent intent=new Intent();
        intent.putExtra("project",project);
        setResult(RESULT_OK,intent);
        defaultFinish();
    }
}
