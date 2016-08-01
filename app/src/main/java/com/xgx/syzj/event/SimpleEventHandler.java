package com.xgx.syzj.event;

import com.xgx.syzj.app.lifecycle.LifeCycleComponent;

/**
 * @author zajo
 * @created 2015年10月12日 14:31
 */
public class SimpleEventHandler implements LifeCycleComponent {

    private boolean mRegistered = false;

    public SimpleEventHandler register() {
        if (!mRegistered) {
            mRegistered = true;
            EventCenter.getInstance().register(this);
        }

        return this;
    }

    public synchronized SimpleEventHandler tryToUnregister() {
        if (mRegistered) {
            mRegistered = false;
            EventCenter.getInstance().unregister(this);
        }

        return this;
    }

    public synchronized SimpleEventHandler tryToRegisterIfNot() {
        register();
        return this;
    }

    @Override
    public void onBecomesPartiallyInvisible() {
        tryToUnregister();
    }

    @Override
    public void onBecomesVisible() {
        register();
    }

    @Override
    public void onBecomesTotallyInvisible() {
    }

    @Override
    public void onBecomesVisibleFromTotallyInvisible() {
    }

    @Override
    public void onDestroy() {
        tryToUnregister();
    }
}
