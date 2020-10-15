package com.icmi.mychat.view.common.activity;

import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.icmi.mychat.MyApplication;
import com.icmi.mychat.common.composition_root.CompositionRoot;
import com.icmi.mychat.common.presentation_root.PresentationRoot;

public class BaseActivity extends AppCompatActivity {

    private PresentationRoot mPresentationRoot;

    protected PresentationRoot getCompositionRoot() {
        if (mPresentationRoot == null)
            mPresentationRoot = new PresentationRoot(
                    getAppCompositionRoot(),
                    getSupportFragmentManager(),
                    LayoutInflater.from(this)
            );

        return mPresentationRoot;
    }

    private CompositionRoot getAppCompositionRoot() {
        return ((MyApplication) getApplication()).getAppCompositionRoot();
    }


}
