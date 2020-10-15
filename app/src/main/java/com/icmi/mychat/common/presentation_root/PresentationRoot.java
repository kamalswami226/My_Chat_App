package com.icmi.mychat.common.presentation_root;

import android.view.LayoutInflater;

import androidx.fragment.app.FragmentManager;

import com.icmi.mychat.common.ViewFactory;
import com.icmi.mychat.common.composition_root.CompositionRoot;
import com.icmi.mychat.networking.LoginUseCase;

public class PresentationRoot {

    private final CompositionRoot mCompositionRoot;
    private final FragmentManager mFragmentManager;
    private final LayoutInflater mLayoutInflater;

    public PresentationRoot(CompositionRoot mCompositionRoot, FragmentManager mFragmentManager, LayoutInflater mLayoutInflater) {
        this.mCompositionRoot = mCompositionRoot;
        this.mFragmentManager = mFragmentManager;
        this.mLayoutInflater = mLayoutInflater;
    }

    public LoginUseCase getLoginUseCase() {
        return mCompositionRoot.getLoginUseCase();
    }

    public ViewFactory getViewFactory() {
        return new ViewFactory(mLayoutInflater);
    }

}
