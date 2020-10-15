package com.icmi.mychat;

import android.app.Application;

import com.icmi.mychat.common.composition_root.CompositionRoot;

public class MyApplication extends Application {

    private CompositionRoot mCompositionRoot;

    public CompositionRoot getAppCompositionRoot() {
        if (mCompositionRoot == null)
            return new CompositionRoot();
        else
            return mCompositionRoot;
    }

}
