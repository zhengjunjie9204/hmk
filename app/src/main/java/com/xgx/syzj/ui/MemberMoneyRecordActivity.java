package com.xgx.syzj.ui;

import android.os.Bundle;
import android.text.Html;
import android.widget.ListView;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.adapter.StoreRecordAdapter;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Member;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.bean.StoreRecordBean;
import com.xgx.syzj.datamodel.RecordsDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;

/**
 * 会员储值记录
 *
 * @author zajo
 * @created 2015年08月31日 15:45
 */

public class   MemberMoneyRecordActivity extends BaseActivity {
    private TextView tv_count, tv_used, tv_add;
    private ListView lv_history;
    private StoreRecordAdapter mAdapter;
    private LoadMoreListViewContainer loadMoreListViewContainer;
    private List<StoreRecordBean> data = new ArrayList<>();
    private RecordsDataModel mDataModel;
    private String count, used, add;
    private double count_money = 0, used_money = 0, add_money = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_money_record);
        initView();
        initListener();
        mDataModel.queryFirstPage();
    }

    private void initView()
    {
        setTitleText(getString(R.string.member_money_title));
        tv_count = (TextView) findViewById(R.id.tv_count);
        tv_used = (TextView) findViewById(R.id.tv_used);
        tv_add = (TextView) findViewById(R.id.tv_add);
        lv_history = (ListView) findViewById(R.id.lv_history);
        loadMoreListViewContainer = (LoadMoreListViewContainer) findViewById(R.id.load_more_list_view_container);
        setTextData();
    }

    private void initListener()
    {
        Member member = getIntent().getParcelableExtra("member");
        if (member == null) {
            defaultFinish();
            return;
        }
        EventCenter.bindContainerAndHandler(this, eventHandler);
        mDataModel = new RecordsDataModel(Constants.LOAD_COUNT, member.getId());
        loadMoreListViewContainer.useDefaultFooter();
        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer)
            {
                mDataModel.queryNextPage();
            }
        });
        mAdapter = new StoreRecordAdapter(this, data);
        lv_history.setAdapter(mAdapter);
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(List<StoreRecordBean> list)
        {
            if (list == null) return;
            data.addAll(list);
            mAdapter.notifyDataSetChanged();
            loadMoreListViewContainer.loadMoreFinish(mDataModel.getListPageInfo().isEmpty(), mDataModel.getListPageInfo().hasMore());
        }

        public void onEvent(Result result)
        {
            try {
                if (result.getStatus() == 200) {
                    JSONObject json = new JSONObject(result.getResult());
                    count_money = json.optDouble("storeAmount", 0);
                    add_money = json.optDouble("storedMoney", 0);
                    used_money = json.optDouble("consumeByChuzhi", 0);
                    setTextData();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void onEvent(String error)
        {
            showShortToast(error);
            loadMoreListViewContainer.loadMoreError(0, error);
        }
    };

    public void setTextData()
    {
        count = getString(R.string.member_money_count) + "<br/><b><big>￥" + "</big></b>";
        tv_count.setText(Html.fromHtml(count) + "" + add_money);
        used = getString(R.string.member_money_used) + "<br/><b><big>￥" + "</big></b>";
        tv_used.setText(Html.fromHtml(used) + "" +  used_money);
        add = getString(R.string.member_money_add) + "<br/><b><big>￥" + "</big></b>";
        tv_add.setText( Html.fromHtml(add) + "" +  count_money );

    }
}
