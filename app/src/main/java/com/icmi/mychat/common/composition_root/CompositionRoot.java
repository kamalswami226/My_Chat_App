package com.icmi.mychat.common.composition_root;

import com.icmi.mychat.networking.LoginUseCase;
import com.icmi.mychat.schemas.ProfileModel;

public class CompositionRoot {

    private LoginUseCase mLoginUseCase;
    private ProfileModel mProfileModel;

    public LoginUseCase getLoginUseCase() {
        if (mLoginUseCase == null)
            mLoginUseCase = new LoginUseCase();
        return mLoginUseCase;
    }


    public ProfileModel getProfile() {
        if (mProfileModel == null)
            mProfileModel = new ProfileModel();
        return mProfileModel;
    }

}
