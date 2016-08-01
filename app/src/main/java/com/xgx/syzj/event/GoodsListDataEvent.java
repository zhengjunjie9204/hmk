package com.xgx.syzj.event;

import com.xgx.syzj.bean.Goods;

import java.util.List;

/**
 * @author zajo
 * @created 2015年10月21日 17:57
 */
public class GoodsListDataEvent {

    public boolean hasMore;
    public List<Goods> dataList;

}
