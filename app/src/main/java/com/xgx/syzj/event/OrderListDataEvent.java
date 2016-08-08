package com.xgx.syzj.event;

import com.xgx.syzj.bean.FastOrder;

import java.util.List;

/**
 * @author zajo
 * @created 2015年10月21日 17:57
 */
public class OrderListDataEvent {

    public boolean hasMore;
    public List<FastOrder> dataList;

}
