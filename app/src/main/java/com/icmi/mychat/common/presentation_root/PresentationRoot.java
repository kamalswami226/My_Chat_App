package com.icmi.mychat.common.presentation_root;

import android.view.LayoutInflater;
import androidx.fragment.app.FragmentActivity;
import com.icmi.mychat.common.ViewFactory;
import com.icmi.mychat.common.composition_root.CompositionRoot;
import com.icmi.mychat.common.permissions.Permissionhelper;
import com.icmi.mychat.networking.FetchContactsUseCase;
import com.icmi.mychat.networking.LoginUseCase;

public class PresentationRoot {

    private final CompositionRoot mCompositionRoot;
    private final FragmentActivity mActivity;
    private final LayoutInflater mLayoutInflater;

    private Permissionhelper mPermissionHelper;

    public PresentationRoot(CompositionRoot mCompositionRoot, FragmentActivity mActivity, LayoutInflater mLayoutInflater) {
        this.mCompositionRoot = mCompositionRoot;
        this.mActivity = mActivity;
        this.mLayoutInflater = mLayoutInflater;
    }

    public FragmentActivity getActivity() {
        return mActivity;
    }

    public Permissionhelper getPermissionHelper() {
        if (mPermissionHelper == null)
            mPermissionHelper = new Permissionhelper(getActivity());

        return mPermissionHelper;
    }

    public FetchContactsUseCase getFetchContactUseCase() {
        return new FetchContactsUseCase(mActivity);
    }

    public LoginUseCase getLoginUseCase() {
        return mCompositionRoot.getLoginUseCase();
    }

    public ViewFactory getViewFactory() {
        return new ViewFactory(mLayoutInflater);
    }

}
