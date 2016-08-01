package com.xgx.syzj.event;

import java.util.List;

/**
 * @author zajo
 * @created 2015年10月26日 14:56
 */
public class BaseDataEvent<T> {

    public boolean hasMore;
    public List<T> dataList;
}
