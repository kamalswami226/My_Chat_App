package com.icmi.mychat.common.presentation_root;

import android.view.LayoutInflater;
import androidx.fragment.app.FragmentActivity;
import com.icmi.mychat.common.ViewFactory;
import com.icmi.mychat.common.composition_root.CompositionRoot;
import com.icmi.mychat.common.permissions.Permissionhelper;
import com.icmi.mychat.networking.FetchContactsUseCase;
import com.icmi.mychat.networking.FetchRecentChatsUseCase;
import com.icmi.mychat.networking.LoginUseCase;
import com.icmi.mychat.networking.SendMessageUsecase;
import com.icmi.mychat.networking.UserProfileUsecase;
import com.icmi.mychat.schemas.ProfileModel;

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

    public FetchRecentChatsUseCase getRecentChatUseCase() {
        return new FetchRecentChatsUseCase();
    }

    public LoginUseCase getLoginUseCase() {
        return mCompositionRoot.getLoginUseCase();
    }

    public ViewFactory getViewFactory() {
        return new ViewFactory(mLayoutInflater);
    }

    public UserProfileUsecase getUserProfileUsecase() {
        return new UserProfileUsecase();
    }

    public SendMessageUsecase getSendMessageUseCase() {
        return new SendMessageUsecase();
    }

    public ProfileModel getProfile() {
        return mCompositionRoot.getProfile();
    }
}
