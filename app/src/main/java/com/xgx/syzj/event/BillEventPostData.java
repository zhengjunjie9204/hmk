package com.xgx.syzj.event;

import java.util.List;

/**
 * Created by sam on 2016/3/16 13:48.
 */
public class BillEventPostData<T> {
    public String str;
    public boolean hasMore;
    public List<T> dataList;
}
