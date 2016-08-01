package com.xgx.syzj.event;

import com.xgx.syzj.bean.Attendance;

import java.util.ArrayList;

/**
 * @author zajo
 * @created 2015年10月12日 15:17
 */
public class AttendanceListDataEvent {
    public boolean hasMore;
    public ArrayList<Attendance> dataList;
}
