package com.xgx.syzj.base;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.xgx.syzj.app.lifecycle.IComponentContainer;
import com.xgx.syzj.app.lifecycle.LifeCycleComponent;
import com.xgx.syzj.app.lifecycle.LifeCycleComponentManager;
import com.xgx.syzj.widget.CustomProgressDialog;


public class BaseFragment extends Fragment implements IComponentContainer {

    private LifeCycleComponentManager mComponentContainer = new LifeCycleComponentManager();
    protected CustomProgressDialog dialog;

    @Override
    public void addComponent(LifeCycleComponent component) {
        mComponentContainer.addComponent(component);
    }

    protected void showLoadingDialog(Activity activity){
        dialog = CustomProgressDialog.createDialog(activity).setMessage("加载中...");
        dialog.show();
    }

    protected void showLoadingDialog(Activity activity,int res_id) {
        dialog = CustomProgressDialog.createDialog(activity).setMessage("加载中...");
        dialog.setMessage(getString(res_id));
        dialog.show();
    }

    protected void hideLoadingDialog(){
        dialog.dismiss();
    }


    @Override
    public void onStart() {
        super.onStart();
        mComponentContainer.onBecomesVisibleFromTotallyInvisible();
    }

    @Override
    public void onPause() {
        super.onPause();
        mComponentContainer.onBecomesPartiallyInvisible();
    }

    @Override
    public void onResume() {
        super.onResume();
        mComponentContainer.onBecomesVisibleFromPartiallyInvisible();
    }

    @Override
    public void onStop() {
        super.onStop();
        mComponentContainer.onBecomesTotallyInvisible();
    }

    @Override
    public void onDestroy() { //销毁时activity出栈
        super.onDestroy();
        mComponentContainer.onDestroy();
    }

    public void showShortToast(int id) {
        Toast.makeText(getActivity(), getString(id), Toast.LENGTH_SHORT).show();
    }

    public void showShortToast(String content) {
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
    }


}
