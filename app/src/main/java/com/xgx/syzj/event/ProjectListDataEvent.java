package com.xgx.syzj.event;

import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.bean.Project;

import java.util.List;

/**
 * @author zajo
 * @created 2015年10月21日 17:57
 */
public class ProjectListDataEvent {

    public boolean hasMore;
    public List<Project> dataList;

}
