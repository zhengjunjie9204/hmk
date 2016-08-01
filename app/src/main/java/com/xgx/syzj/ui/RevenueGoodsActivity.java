package com.xgx.syzj.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.adapter.RevenueGoodsDataAdapter;
import com.xgx.syzj.adapter.RevenueGoodsTypeAdapter;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.bean.GoodsCategory;
import com.xgx.syzj.bean.Member;
import com.xgx.syzj.datamodel.GoodsDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;
import com.xgx.syzj.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreGridViewContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;

/**
 * 销售记账选择商品
 *
 * @author zajo
 * @created 2015年09月23日 14:43
 */
public class RevenueGoodsActivity extends BaseActivity implements View.OnClickListener {

    private ListView lv_type;
    private GridView gv_data;
    private TextView tv_count,tv_money;
    private EditText et_text;
    private Button btn_js;
    private RevenueGoodsTypeAdapter mTypeAdapter;
    private RevenueGoodsDataAdapter mDataAdapter;
    private List<GoodsCategory> types = new ArrayList<>();
    //    private LinkedHashMap<String, List<Goods>> data = new LinkedHashMap<>();
    private List<Goods> currentList = new ArrayList<>();
    private ArrayList<Goods> selectList = new ArrayList<>();
    private String curType;//当前选择类型名
    private Map<String, Integer> typeItems = new HashMap<>();//选中类型中物品个数
    private Map<String, Integer> goodsItems = new HashMap<>();//选中商品数量
    private GoodsDataModel mDataModel;
    private LoadMoreGridViewContainer loadMoreGridViewContainer;
    private String[] images;

    private Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_revenue_goods);
        setTitleText("选择商品");
        getIntentData();
        lv_type = (ListView) findViewById(R.id.lv_type);
        gv_data = (GridView) findViewById(R.id.gv_data);
        tv_count = (TextView) findViewById(R.id.tv_count);
        tv_money = (TextView) findViewById(R.id.tv_money);
        et_text = (EditText) findViewById(R.id.et_text);
        et_text.setOnEditorActionListener(onEditorActionListener);
        btn_js = (Button) findViewById(R.id.btn_js);
        btn_js.setOnClickListener(this);

        mTypeAdapter = new RevenueGoodsTypeAdapter(this, types, typeItems);
        lv_type.setAdapter(mTypeAdapter);
        lv_type.setOnItemClickListener(onTypeItemClickListener);

        mDataModel = new GoodsDataModel(Constants.LOAD_COUNT);
        loadMoreGridViewContainer = (LoadMoreGridViewContainer) findViewById(R.id.load_more_list_view_container);
        loadMoreGridViewContainer.useDefaultFooter();
        loadMoreGridViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                mDataModel.queryNextPage();
            }
        });
        mDataAdapter = new RevenueGoodsDataAdapter(this, goodsItems, onItemDeleteClick);
        gv_data.setAdapter(mDataAdapter);
        gv_data.setOnItemClickListener(onDataItemClickListener);

        EventCenter.bindContainerAndHandler(this, eventHandler);
        showLoadingDialog(R.string.loading_goods_category);

        images = getResources().getStringArray(R.array.imageUrls);
    }
    private void getIntentData(){
        member = getIntent().getParcelableExtra("member");
    }
    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(List<Goods> list) {
            loadMoreGridViewContainer.loadMoreFinish(mDataModel.getListPageInfo().isEmpty(), mDataModel.getListPageInfo().hasMore());
            Random random = new Random();
            for (int i = 0; i < list.size(); i++) {
                int j = random.nextInt(images.length);
                list.get(i).setImage(images[j]);
            }
            currentList = list;
            mDataAdapter.appendList(list);
//            data.put(curType, list);
        }

        public void onEvent(String error) {
            hideLoadingDialog();
            showShortToast(error);
            loadMoreGridViewContainer.loadMoreError(0, error);
        }
    };

    private TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mDataAdapter.cleanData();
                currentList.clear();
                String text = et_text.getText().toString().trim();
                mDataModel.setKey(text);
                mDataModel.setBrand(text);
                mDataModel.queryNextPage();
                Utils.hideSoftInput(RevenueGoodsActivity.this);
            }
            return false;
        }
    };

    private AdapterView.OnItemClickListener onTypeItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mTypeAdapter.setIndex(position);
            mDataAdapter.cleanData();
            currentList.clear();
            GoodsCategory gc = types.get(position);
            curType = gc.getCategoryName();
//            mDataModel.setCategoryId(gc.getCategoryId());
            mDataModel.queryNextPage();
        }
    };

    private AdapterView.OnItemClickListener onDataItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            addSelete(currentList.get(position));
            addGoodsCount(currentList.get(position));
            addTypeCount();
            computeData();
        }
    };

    private void addSelete(Goods g) {
        if (selectList.contains(g)) {
            Goods good = selectList.get(selectList.indexOf(g));
            good.setRevenueCount(good.getRevenueCount()+1);
        } else {
            g.setRevenueCount(1);
            selectList.add(g);
        }
    }

    private void addGoodsCount(Goods goods) {
        //使用物品名称和id确定唯一一个商品
        String key = goods.getProductName() + goods.getProductId();
        if (goodsItems.get(key) != null) {
            goodsItems.put(key, goodsItems.get(key) + 1);
        } else {
            goodsItems.put(key, 1);
        }
        mDataAdapter.notifyDataSetChanged();
    }

    private void addTypeCount() {
        //当前类型数量+1
        int count = 0;
        if (typeItems.containsKey(curType)) {
            count = typeItems.get(curType) + 1;
        } else {
            count += 1;
        }
        typeItems.put(curType, count);
        mTypeAdapter.notifyDataSetChanged();
    }

    private void computeData() {
        double money = 0;
        int count = 0;
        for (int i = 0; i < selectList.size(); i++) {
            count += selectList.get(i).getRevenueCount();
            money += (selectList.get(i).getSellingPrice() * selectList.get(i).getRevenueCount());
        }
        tv_count.setText(count+"");
        if (money > 0) {
            DecimalFormat df = new DecimalFormat("#.00");
            tv_money.setText("¥ "+df.format(money));
        } else {
            tv_money.setText("¥ 0");
        }
    }

    private RevenueGoodsDataAdapter.OnItemDeleteClick onItemDeleteClick = new RevenueGoodsDataAdapter.OnItemDeleteClick() {
        @Override
        public void onClick(Goods goods) {
            //使用物品名称和id确定唯一一个商品
            String key = goods.getProductName() + goods.getProductId();
            for(Goods g : selectList) {
                if ((g.getProductName()+g.getProductId()).equals(key)) {
                    goods = g;
                    break;
                }
            }
            if (goodsItems.get(key) != null) {
                goodsItems.put(key, goodsItems.get(key) - 1);
            }
            mDataAdapter.notifyDataSetChanged();
            //当前类型数量-1
            typeItems.put(curType, typeItems.get(curType) - 1);
            mTypeAdapter.notifyDataSetChanged();
            if(goods.getRevenueCount() == 1)
                selectList.remove(goods);
            else {
                goods.setRevenueCount(goods.getRevenueCount() - 1);
            }
            computeData();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_js:
                if (selectList.size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("goods", selectList);
                    if (member != null){
                        bundle.putParcelable("member", member);
                    }
                    gotoActivity(RevenueDetailActivity.class, bundle);
                } else {
                    showShortToast("请选择商品");
                }
                break;
        }
    }
}
