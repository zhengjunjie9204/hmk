package com.xgx.syzj.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xgx.syzj.R;
import com.xgx.syzj.adapter.CardListAdapter;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.CardType;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.CardDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.FastJsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员体系
 *
 * @author zajo
 * @created 2015年10月21日 14:41
 */
public class CardListActivity extends BaseActivity {

    private ListView lv_data;
    private ArrayList<CardType> datas = new ArrayList<>();
    private CardListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_card_list);
        initView();

        showLoadingDialog(R.string.loading_load_cards);
        CardDataModel.getList();
        registerReceiver();
    }

    private void initView() {
        setTitleText("会员卡列表");
        setSubmit(getString(R.string.app_button_new));
        lv_data = (ListView) findViewById(R.id.lv_data);
        EventCenter.bindContainerAndHandler(this, eventHandler);
        mAdapter = new CardListAdapter(this, datas);
        lv_data.setAdapter(mAdapter);
        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CardType type = (CardType) mAdapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("cardType", type);
                gotoActivity(CardEditActivity.class, bundle);
            }
        });
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(Result result) {
            hideLoadingDialog();
            List<CardType> list = FastJsonUtil.json2List(result.getResult(), CardType.class);
            mAdapter.appendList(list);
        }

        public void onEvent(String error) {
            hideLoadingDialog();
            showShortToast(error);
        }

    };

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.Broadcast.RECEIVER_DELETE_CARD);
        intentFilter.addAction(Constants.Broadcast.RECEIVER_UPDATE_CARD);
        registerReceiver(myReceiver, intentFilter);
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constants.Broadcast.RECEIVER_DELETE_CARD)) {
                //删除会员卡
                CardType data = intent.getParcelableExtra("cardType");
                for (CardType type : datas) {
                    if (type.getCardTypeId() == data.getCardTypeId()){
                        datas.remove(type);
                        mAdapter.notifyDataSetChanged();
                        return;
                    }
                }
            } else if(action.equals(Constants.Broadcast.RECEIVER_UPDATE_CARD)) {
                //更新会员卡
                CardType data = intent.getParcelableExtra("cardType");
                for (CardType type : datas) {
                    if (type.getCardTypeId() == data.getCardTypeId()){
                        type.setCardTypeName(data.getCardTypeName());
                        type.setSupportIntegral(data.isSupportIntegral());
                        type.setSupportValue(data.isSupportValue());
                        type.setSupportCount(data.isSupportCount());
                        type.setNeedPassord(data.isNeedPassord());
                        type.setDiscount(data.getDiscount());
                        type.setIntegralRatio(data.getIntegralRatio());
                        mAdapter.notifyDataSetChanged();
                        return;
                    }
                }
            }
        }
    };

    @Override
    protected void submit() {
        super.submit();
        gotoActivityForResult(CardCreateActivity.class, null, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == 1000) {
            CardType type = data.getParcelableExtra("type");
            mAdapter.insertHead(type);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }
}
