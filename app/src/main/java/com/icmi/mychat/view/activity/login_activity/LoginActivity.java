package com.icmi.mychat.view.activity.login_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.icmi.mychat.R;
import com.icmi.mychat.networking.LoginUseCase;
import com.icmi.mychat.view.activity.main_activity.MainActivity;
import com.icmi.mychat.view.common.activity.BaseActivity;
import com.icmi.mychat.view.fragments.CreateProfile.CreateProfileFragment;

public class LoginActivity extends BaseActivity implements LoginView.Listener, LoginUseCase.Listener{

    private LoginView mLoginView;
    private LoginUseCase mLoginUseCase;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginView = getCompositionRoot().getViewFactory().newInstance(LoginView.class, null);
        setContentView(mLoginView.getRootView());

        mLoginUseCase = getCompositionRoot().getLoginUseCase();
    }


    @Override
    public void onLoginButtonClicked(String phoneNumber) {
        mLoginUseCase.loginWithPhone(phoneNumber);
    }

    @Override
    public void onLoginWithOtpButtonClicked(String otp) {
        mLoginUseCase.loginWithOtp(otp);
    }


    @Override
    public void loginSuccess() {
        MainActivity.start(this);
        finish();
    }

    @Override
    public void onLoginFailed(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOtpSent(String otp) {
        mLoginView.hideLoading();
    }

    @Override
    public void requestUserProfileCreation() {
        mLoginView.hideLoading();
        showCreateProfileFragment();
    }

    private void showCreateProfileFragment() {
        getSupportFragmentManager().beginTransaction().replace(
                R.id.fragment_container,
                CreateProfileFragment.newInstance()
        ).commit();
    }

    @Override
    public void showLoading() {
        mLoginView.showLoading();
    }

    @Override
    public void hideLoading() {
        mLoginView.hideLoading();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLoginView.registerListener(this);
        mLoginUseCase.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLoginView.removeListener(this);
        mLoginUseCase.removeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginView.removeListener(this);
        mLoginUseCase.removeListener(this);
    }
}