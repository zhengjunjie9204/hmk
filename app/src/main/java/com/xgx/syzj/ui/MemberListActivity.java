package com.xgx.syzj.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.xgx.syzj.R;
import com.xgx.syzj.adapter.MemberListAdapter;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Member;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.datamodel.MemberDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.CacheUtil;
import com.xgx.syzj.utils.Utils;
import com.xgx.syzj.widget.CustomAlertDialog;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;

/**
 * 会员管理
 *
 * @author zajo
 * @created 2015年08月28日 14:18
 */
public class MemberListActivity extends BaseActivity  {

    private EditText et_text;
    private TextView tv_count;
    private SwipeMenuListView sortListView;
    private MemberListAdapter adapter;
    private List<Member> sourceDateList = new ArrayList<>();
    private CheckBox cb_all;
    private Button btn_filter, btn_all;

    private LoadMoreListViewContainer loadMoreListViewContainer;
    private MemberDataModel mDataModel;
    private int deleteIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);

        setTitleText(getString(R.string.member_manage_title));

        tv_count = (TextView) findViewById(R.id.tv_count);
        cb_all = (CheckBox) findViewById(R.id.cb_all);
        btn_all = (Button) findViewById(R.id.btn_all);
        sortListView = (SwipeMenuListView) findViewById(R.id.listView);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(MemberListActivity.this);
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                deleteItem.setWidth(Utils.dp2px(MemberListActivity.this, 90));
                deleteItem.setIcon(R.mipmap.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        };
        sortListView.setMenuCreator(creator);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Member member = (Member) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("member", member);
                gotoActivity(MemberDetailActivity.class, bundle);
            }
        });
        sortListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                CustomAlertDialog.showRemindDialog(MemberListActivity.this, "温馨提示", "是否删除", new CustomAlertDialog.IAlertDialogListener() {

                    @Override
                    public void onSure(Object obj) {
                        if(CacheUtil.getmInstance().getUser().getRoles()==3){
                            showShortToast("您的权限不足");
                            return;
                        }
                        deleteItem(position);
                    }
                });

                return false;
            }
        });
        et_text = (EditText) findViewById(R.id.et_text);
        et_text.setOnEditorActionListener(onEditorActionListener);
//        et_text.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count) {
//                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
//                filterData(s.toString());
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
        EventCenter.bindContainerAndHandler(this, eventHandler);
        mDataModel = new MemberDataModel(Constants.LOAD_COUNT);
        loadMoreListViewContainer = (LoadMoreListViewContainer) findViewById(R.id.load_more_list_view_container);
        loadMoreListViewContainer.useDefaultFooter();
        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                mDataModel.queryNextPage();
            }
        });
        System.out.println(sourceDateList);
        adapter = new MemberListAdapter(MemberListActivity.this, sourceDateList);
        sortListView.setAdapter(adapter);
        mDataModel.queryFirstPage();
        registerReceiver();

        //MemberDataModel.getMemberCount();
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.Broadcast.RECEIVER_ADD_RECHARGE);
        intentFilter.addAction(Constants.Broadcast.RECEIVER_DELETE_MEMBER);
        intentFilter.addAction(Constants.Broadcast.RECEIVER_UPDATE_MEMBER);
        registerReceiver(myReceiver, intentFilter);
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constants.Broadcast.RECEIVER_ADD_RECHARGE)) {
                Member addMember = intent.getParcelableExtra("member");
                for (Member m : sourceDateList) {
                    if (m.getId() == addMember.getId()) {

                        adapter.notifyDataSetChanged();
                        return;
                    }
                }
            } else if (action.equals(Constants.Broadcast.RECEIVER_DELETE_MEMBER)) {
                Member delMember = intent.getParcelableExtra("member");
                for (Member m : sourceDateList) {
                    if (m.getId() == delMember.getId()) {
                        sourceDateList.remove(m);
                        adapter.notifyDataSetChanged();
                        return;
                    }
                }
            } else if (action.equals(Constants.Broadcast.RECEIVER_UPDATE_MEMBER)) {
                //更新会员信息
                Member updateMember = intent.getParcelableExtra("member");
                for (Member m : sourceDateList) {
                    if (m.getId() == updateMember.getId()) {
                        int index = sourceDateList.indexOf(m);
                        sourceDateList.remove(m);
                        sourceDateList.add(index, updateMember);
                        adapter.notifyDataSetChanged();
                        return;
                    }
                }
            }
        }
    };

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(List<Member> list) {
            loadMoreListViewContainer.loadMoreFinish(mDataModel.getListPageInfo().isEmpty(), mDataModel.getListPageInfo().hasMore());
            if (list == null || list.size() == 0) return;
            sourceDateList.addAll(list);
            adapter.notifyDataSetChanged();
            tv_count.setText("共"+adapter.getCount()+"位会员");
        }

        public void onEvent(Result result) {
            hideLoadingDialog();
            if (result.geteCode() == MemberDataModel.DELETE_MEMBER) {
                showShortToast("删除会员成功");
                if (deleteIndex == -1) return;
                sourceDateList.remove(deleteIndex);
                adapter.notifyDataSetChanged();
                tv_count.setText("共"+adapter.getCount()+"位会员");
                deleteIndex = -1;
            }
        }

        public void onEvent(String error) {
            hideLoadingDialog();
            showShortToast(error);
            loadMoreListViewContainer.loadMoreError(0, error);
        }
    };

    private TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                sourceDateList.clear();
                adapter.notifyDataSetChanged();
                String text = et_text.getText().toString().trim();
                mDataModel.setKey(text);
                mDataModel.queryNextPage();
                Utils.hideSoftInput(MemberListActivity.this);
            }
            return false;
        }
    };

    private void deleteItem(int position) {
        deleteIndex = position;
        Member member = sourceDateList.get(deleteIndex);
        showLoadingDialog(R.string.loading_delete_member);
        MemberDataModel.deleteMember(member.getId());
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case 1000:
                Member m = data.getParcelableExtra("member");
                if (m != null) {
                    for (Member member : sourceDateList) {
                        if (member.getAssociatorId() == m.getAssociatorId()) {
                            sourceDateList.remove(member);
                            break;
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }*/

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        int huiyuan = intent.getIntExtra("huiyuan", 1);
        if(huiyuan==1){
            gotoActivity(MainActivity.class);
        }
        super.onBackPressed();

    }
}
