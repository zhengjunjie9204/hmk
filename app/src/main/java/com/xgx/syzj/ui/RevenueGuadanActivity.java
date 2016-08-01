package com.xgx.syzj.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xgx.syzj.R;
import com.xgx.syzj.adapter.RevenueGuadanAdapter;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Cart;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.bean.GoodsCart;
import com.xgx.syzj.bean.Member;
import com.xgx.syzj.datamodel.CartDataModel;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.event.SimpleEventHandler;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;

/**
 * 挂单列表
 *
 * @author zajo
 * @created 2015年09月22日 15:47
 */
public class RevenueGuadanActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private LoadMoreListViewContainer loadMoreListViewContainer;
    private CartDataModel mDataModel;
    private ListView lv_data;
    private RevenueGuadanAdapter mAdapter;
    private List<Cart> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_revenue_guadan);
        setTitleText("挂单列表");
        lv_data = (ListView) findViewById(R.id.lv_data);
        EventCenter.bindContainerAndHandler(this, eventHandler);
        mDataModel = new CartDataModel(Constants.LOAD_COUNT);
        loadMoreListViewContainer = (LoadMoreListViewContainer) findViewById(R.id.load_more_list_view_container);
        loadMoreListViewContainer.useDefaultFooter();
        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                mDataModel.queryNextPage();
            }
        });
        mAdapter = new RevenueGuadanAdapter(this, mList);
        lv_data.setAdapter(mAdapter);
        lv_data.setOnItemClickListener(this);
        mDataModel.queryNextPage();
    }

    private SimpleEventHandler eventHandler = new SimpleEventHandler() {

        public void onEvent(List<Cart> list) {
            loadMoreListViewContainer.loadMoreFinish(mDataModel.getListPageInfo().isEmpty(), mDataModel.getListPageInfo().hasMore());
            if (list == null || list.size() == 0) return;
            mList.addAll(list);
            mAdapter.notifyDataSetChanged();
        }

        /*public void onEvent(Result result) {
            hideLoadingDialog();
            List<GoodsCart> list = FastJsonUtil.json2List(result.getResult(), GoodsCart.class);
            List<Goods> gList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++){
                GoodsCart gc = list.get(i);
                Goods g = new Goods();
                g.setImage("http://pic31.nipic.com/20130719/3347542_103839221000_2.jpg");
                g.setQuantity(gc.getQuantity());
                g.setSellingPrice(gc.getTotalValue() / g.getQuantity());
                g.setRevenueCount(gc.getQuantity());
                g.setProductId(gc.getProductId());
                g.setProductName(gc.getProductName());
                gList.add(g);
            }
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("goods", (ArrayList)gList);
            gotoActivity(RevenueDetailActivity.class, bundle);
        }*/

        public void onEvent(String error) {
            hideLoadingDialog();
            showShortToast(error);
            loadMoreListViewContainer.loadMoreError(0, error);
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        Cart cart = mList.get(position);
        if (cart.getCartDetails() != null && cart.getCartDetails().size() > 0) {
            ArrayList<Goods> list = new ArrayList<>();
            for (int i = 0; i < cart.getCartDetails().size(); i++) {
                Goods g = new Goods();
                GoodsCart gc = cart.getCartDetails().get(i);
                g.setProductId(gc.getProductId());
                g.setProductName(gc.getProductName());
                g.setRevenueCount(gc.getQuantity());
                g.setSellingPrice(gc.getTotalValue() / gc.getQuantity());
                list.add(g);
            }
            bundle.putParcelableArrayList("goods", list);
        }
        if (cart.getAssociatorId() != 0 && !TextUtils.isEmpty(cart.getAssociatorName())){
            Member member = new Member();
            member.setId(cart.getAssociatorId());
            member.setName(cart.getAssociatorName());
//            member.setCardId(Integer.parseInt(cart.getCardId()));
//            member.setCardIntegral(cart.getCardIntegral());
//            member.setCardValue(cart.getCardValue());
            bundle.putParcelable("member", member);
        }
        bundle.putInt("cartId", cart.getCartId());
        gotoActivity(RevenueDetailActivity.class, bundle);
    }
}
