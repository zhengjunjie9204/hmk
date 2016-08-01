package com.xgx.syzj.ui;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.xgx.syzj.R;
import com.xgx.syzj.adapter.MemberMoneyRecordAdapter;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.GetDataInfo;
import com.xgx.syzj.bean.Member;
import com.xgx.syzj.bean.MoneyRecordCard;
import com.xgx.syzj.bean.Recharge;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.CardDataModel;
import com.xgx.syzj.datamodel.RechargeDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;

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
public class MemberMoneyRecordActivity extends BaseActivity {

    private MemberMoneyRecordAdapter mAdapter;
    private ListView lv_history;
    private LoadMoreListViewContainer loadMoreListViewContainer;
    private RechargeDataModel mDataModel;
    private List<Recharge> data = new ArrayList<>();
    private String count,used,add;
    private double count_money = 0,used_money = 0,add_money = 0;
    private TextView tv_count,tv_used,tv_add;
    private ListView mLv_record_History;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_money_record);
        setTitleText(getString(R.string.member_money_title));
//        tv_count = (TextView) findViewById(R.id.tv_count);
//        tv_used = (TextView) findViewById(R.id.tv_used);
//        tv_add = (TextView) findViewById(R.id.tv_add);

        Member member = getIntent().getParcelableExtra("member");
        if (member == null) {
            defaultFinish();
            return;
        }
//        EventCenter.bindContainerAndHandler(this, eventHandler);
        mDataModel = new RechargeDataModel(Constants.LOAD_COUNT, member.getId());
//        CardDataModel.getCardDetail(member.getId());
//        loadMoreListViewContainer = (LoadMoreListViewContainer) findViewById(R.id.load_more_list_view_container);
//        loadMoreListViewContainer.useDefaultFooter();
//        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
//            @Override
//            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
//                mDataModel.queryNextPage();
//            }
//        });
        data.add(new Recharge());
        data.add(new Recharge());
        data.add(new Recharge());
        data.add(new Recharge());
        mLv_record_History = (ListView)findViewById(R.id.lv_record_History);
        mAdapter = new MemberMoneyRecordAdapter(this, data);
        mLv_record_History.setAdapter(mAdapter);
        mDataModel.queryFirstPage();
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(List<Recharge> list) {
            loadMoreListViewContainer.loadMoreFinish(mDataModel.getListPageInfo().isEmpty(), mDataModel.getListPageInfo().hasMore());
            if (list == null) return;
            data.addAll(list);
            mAdapter.notifyDataSetChanged();
        }

        public void onEvent(Result result) {
            if(result.geteCode() == CardDataModel.DETAIL_CARD){
                GetDataInfo info  = JSON.parseObject(result.getResult(), GetDataInfo.class);
                MoneyRecordCard  moneyRecordCard = JSON.parseObject(info.getInfo(), MoneyRecordCard.class);

//                count_money = moneyRecordCard.getCardValue();
//                add_money = moneyRecordCard.getCumulativeRechargeAmount();
//                used_money = moneyRecordCard.getCumulativeUsedValue();
//                setTextData();
            }
        }
        public void onEvent(String error) {
            showShortToast(error);
            loadMoreListViewContainer.loadMoreError(0, error);
        }
    };


}
