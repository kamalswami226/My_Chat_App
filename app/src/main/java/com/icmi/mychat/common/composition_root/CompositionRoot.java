package com.icmi.mychat.common.composition_root;

import com.icmi.mychat.networking.LoginUseCase;

public class CompositionRoot {

    private LoginUseCase mLoginUseCase;

    public LoginUseCase getLoginUseCase() {
        if (mLoginUseCase == null)
            mLoginUseCase = new LoginUseCase();

        return mLoginUseCase;
    }

}
