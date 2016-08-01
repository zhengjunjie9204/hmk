package com.xgx.syzj.event;

import com.xgx.syzj.app.lifecycle.LifeCycleComponentManager;

import de.greenrobot.event.EventBus;

/**
 * @author zajo
 * @created 2015年10月12日 14:30
 */
public class EventCenter {

    private static final EventBus instance = new EventBus();

    private EventCenter() {
    }

    public static final EventBus getInstance() {
        return instance;
    }

    public static SimpleEventHandler bindContainerAndHandler(Object container, SimpleEventHandler handler) {
        LifeCycleComponentManager.tryAddComponentToContainer(handler, container);
        return handler;
    }
}
