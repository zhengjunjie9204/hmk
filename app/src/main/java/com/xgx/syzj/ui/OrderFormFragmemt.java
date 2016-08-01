package com.xgx.syzj.ui;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.xgx.syzj.R;
import com.xgx.syzj.adapter.OrderFormAdapter;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.bean.OrderForm;
import com.xgx.syzj.bean.OrderFormList;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView;

/**
 * 待完成/已完成订单
 *
 * @author zajo
 * @created 2015年11月20日 11:13
 */
public class OrderFormFragmemt extends Fragment {

    private ExpandableStickyListHeadersListView mListView;
    private List<OrderFormList> mList = new ArrayList<>();
    private OrderFormAdapter mAdapter;
    private WeakHashMap<View, Integer> mOriginalViewHeightPool = new WeakHashMap<>();
    private boolean complete;//标示是否完成订单
    private IOrderFormItemClick iOrderFormItemClick;

    public OrderFormFragmemt() {
        super();
    }

    @SuppressLint("ValidFragment")
    public OrderFormFragmemt(boolean flag) {
        super();
        this.complete = flag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public void onAttach(Activity activity) {
        if (!(activity instanceof IOrderFormItemClick)){
            throw new ClassCastException();
        }
        iOrderFormItemClick = (IOrderFormItemClick) activity;
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_form, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mListView = (ExpandableStickyListHeadersListView) view.findViewById(R.id.list);
        mListView.setAnimExecutor(new AnimationExecutor());
        mAdapter = new OrderFormAdapter(getActivity());
        mAdapter.appendList(mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                iOrderFormItemClick.onItemClick((OrderForm) mAdapter.getItem(position));
            }
        });
    }

    private void initData() {
        OrderFormList ofl;
        OrderForm of;
        Goods g;
        for (int i = 0; i < 10; i++) {
            ofl = new OrderFormList();
            ofl.setData(System.currentTimeMillis() - i * 100000000);
            List<OrderForm> list = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                of = new OrderForm();
                of.setId(j + i);
                of.setOrderId("N." + 1000 * (i + j));
                of.setOrderTime(ofl.getData() - 20000000);
                of.setReservationTime(System.currentTimeMillis());
                if (!complete) {
                    if (j%(i+1) == 0) {
                        of.setStatus(OrderForm.STATUS.REACH);
                    } else if(i%(j+1) == 0) {
                        of.setStatus(OrderForm.STATUS.INVALID);
                    }
                }
                of.setUserName("雷锋" + i + j);
                of.setUserPhone(("132514658"+j)+i);
                List<Goods> gs = new ArrayList<>();
                for (int k = 0; k < 4; k++) {
                    g = new Goods();
                    g.setProductName("棉花糖" + k);
                    g.setSellingPrice(i * j + 1000);
                    g.setSpecification("20个/包");
                    g.setQuantity(2000);
                    gs.add(g);
                }
                of.setProducts(gs);
                list.add(of);
            }
            ofl.setList(list);
            mList.add(ofl);
        }
    }

    class AnimationExecutor implements ExpandableStickyListHeadersListView.IAnimationExecutor {

        @Override
        public void executeAnim(final View target, final int animType) {
            if (ExpandableStickyListHeadersListView.ANIMATION_EXPAND == animType && target.getVisibility() == View.VISIBLE) {
                return;
            }
            if (ExpandableStickyListHeadersListView.ANIMATION_COLLAPSE == animType && target.getVisibility() != View.VISIBLE) {
                return;
            }
            if (mOriginalViewHeightPool.get(target) == null) {
                mOriginalViewHeightPool.put(target, target.getHeight());
            }
            final int viewHeight = mOriginalViewHeightPool.get(target);
            float animStartY = animType == ExpandableStickyListHeadersListView.ANIMATION_EXPAND ? 0f : viewHeight;
            float animEndY = animType == ExpandableStickyListHeadersListView.ANIMATION_EXPAND ? viewHeight : 0f;
            final ViewGroup.LayoutParams lp = target.getLayoutParams();
            ValueAnimator animator = ValueAnimator.ofFloat(animStartY, animEndY);
            animator.setDuration(200);
            target.setVisibility(View.VISIBLE);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if (animType == ExpandableStickyListHeadersListView.ANIMATION_EXPAND) {
                        target.setVisibility(View.VISIBLE);
                    } else {
                        target.setVisibility(View.GONE);
                    }
                    target.getLayoutParams().height = viewHeight;
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    lp.height = ((Float) valueAnimator.getAnimatedValue()).intValue();
                    target.setLayoutParams(lp);
                    target.requestLayout();
                }
            });
            animator.start();

        }
    }

    public interface IOrderFormItemClick{
        void onItemClick(OrderForm of);
    }
}
