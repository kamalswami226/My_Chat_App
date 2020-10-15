package com.icmi.mychat.view.common.view;

import android.content.Context;
import android.view.View;

import androidx.annotation.IdRes;

import com.icmi.mychat.common.BaseObservable;

public abstract class BaseView<LISTENER_TYPE> extends BaseObservable<LISTENER_TYPE> implements ViewObservable<LISTENER_TYPE>{

    private View mRootView;

    protected Context getContext() {
        return mRootView.getContext();
    }

    protected <T extends View> T findViewById(@IdRes int id) {
        //noinspection unchecked
        return (T) mRootView.findViewById(id);
    }

    protected void setRootView(View rootView) {
        mRootView = rootView;
    }

    @Override
    public View getRootView() {
        return mRootView;
    }
}
