package com.icmi.mychat.view.activity.login_activity;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.icmi.mychat.R;
import com.icmi.mychat.view.common.view.BaseView;

public class LoginViewImpl extends BaseView<LoginView.Listener> implements LoginView, View.OnClickListener {

    private View mPhoneLayout, mOtpLayout;
    private EditText mOtpEditText, mPhoneEditText;
    private ImageButton mContinueButton, mLoginButton;
    private View mLoading;


    public LoginViewImpl(LayoutInflater mLayoutInflater, ViewGroup container) {
        setRootView(mLayoutInflater.inflate(R.layout.activity_login, container, false));
        initViews();
        setupClickListeners();
    }

    @Override
    public void onOtpSent(String otp) {

    }

    @Override
    public void showLoading() {
        mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mLoading.setVisibility(View.GONE);
    }

    public void showPhoneLayout() {
        mPhoneLayout.setVisibility(View.VISIBLE);
        mOtpLayout.setVisibility(View.GONE);
    }

    public void showOtpLayout() {
        mPhoneLayout.setVisibility(View.GONE);
        mOtpLayout.setVisibility(View.VISIBLE);
    }


    private void setupClickListeners() {
        mContinueButton.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
    }

    private void initViews() {
        mPhoneLayout = findViewById(R.id.loginActivityPhoneLayout);
        mOtpLayout = findViewById(R.id.loginActivityOtpLayout);
        mPhoneEditText = findViewById(R.id.loginActivityPhoneNumber);
        mOtpEditText = findViewById(R.id.loginActivityOtpNumber);
        mContinueButton = findViewById(R.id.loginActivityContinueButton);
        mLoginButton = findViewById(R.id.loginActivityLoginButton);
        mLoading = findViewById(R.id.dimbackgroundLoadingLayout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginActivityContinueButton:
                if (!TextUtils.isEmpty(getEnteredPhoneNumber())) {
                    sendPhoneAuthRequest();
                }
                break;
            case R.id.loginActivityLoginButton:
                if (!TextUtils.isEmpty(getEnteredOtpNumber())) {
                    sendOtpAuthRequest();
                }
                break;
            default:
        }
    }

    private String getEnteredOtpNumber() {
        if (!TextUtils.isEmpty(mOtpEditText.getText().toString())) {
            return mOtpEditText.getText().toString().trim();
        }
        else {
            mOtpEditText.requestFocus();
            mOtpEditText.setError("Enter Your Otp Number");
            return "";
        }
    }

    private String getEnteredPhoneNumber() {
        if (!TextUtils.isEmpty(mPhoneEditText.getText().toString())) {
            return mPhoneEditText.getText().toString().trim();
        }
        else {
            mPhoneEditText.requestFocus();
            mPhoneEditText.setError("Enter Your Phone Number");
            return "";
        }
    }

    private void sendOtpAuthRequest() {
        for (Listener listener : getListeners())
            listener.onLoginWithOtpButtonClicked(getEnteredOtpNumber());
    }

    private void sendPhoneAuthRequest() {
        showOtpLayout();
        for (Listener listener : getListeners())
            listener.onLoginButtonClicked(getEnteredPhoneNumber());
    }
}
