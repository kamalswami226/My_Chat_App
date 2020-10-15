package com.icmi.mychat.view.activity.login_activity;

import com.icmi.mychat.view.common.view.ViewObservable;

public interface LoginView extends ViewObservable<LoginView.Listener> {

    interface Listener {
        void onLoginButtonClicked(String phoneNumber);
        void onLoginWithOtpButtonClicked(String otp);
    }

    void onOtpSent(String otp);

}
