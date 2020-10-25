package com.icmi.mychat.networking;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.icmi.mychat.common.BaseObservable;
import com.icmi.mychat.schemas.ProfileModel;
import com.icmi.mychat.view.common.utils.References;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class LoginUseCase extends BaseObservable<LoginUseCase.Listener> {

    private String mPhoneNumber, mVerificationId;

    public interface Listener {
        void loginSuccess();

        void onLoginFailed(String errorMessage);

        void onOtpSent(String otp);

        void requestUserProfileCreation();

        void showLoading();

        void hideLoading();
    }

    public void loginWithPhone(String phoneNumber) {
        showLoading();
        mPhoneNumber = phoneNumber;
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + phoneNumber, 60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD, callbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            if (phoneAuthCredential.getSmsCode() != null)
                loginWithOtp(phoneAuthCredential.getSmsCode());
            else
                loginWithCredentials(phoneAuthCredential);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            hideLoading();
            notifyFailure(e);
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
            hideLoading();
            notifySmsSent(s);
        }
    };

    public void loginWithOtp(String smsCode) {
        showLoading();
        loginWithCredentials(PhoneAuthProvider.getCredential(mVerificationId, smsCode));
    }

    private void loginWithCredentials(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnSuccessListener(authResult -> checkUserExistance())
                .addOnFailureListener(this::notifyFailure);
    }

    @SuppressWarnings("SpellCheckingInspection")
    private void checkUserExistance() {
        if (isAuthenticated()) {
            References.myProfileReference().get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    hideLoading();
                    notifySuccess();
                } else {
                    hideLoading();
                    requestProfile();
                }
            }).addOnFailureListener(this::notifyFailure);
        }
    }

    public void addNewUser(String name, String profileUrl) {
        showLoading();
        References.myProfileReference().set(generateUserProfileModel(name, profileUrl))
                .addOnSuccessListener(aVoid -> {
                    hideLoading();
                    notifySuccess();
                }).addOnFailureListener(this::notifyFailure);
    }

    private ProfileModel generateUserProfileModel(String name, String profileUrl) {
        return new ProfileModel(
                name,
                profileUrl,
                mPhoneNumber,
                new ArrayList<>()
        );
    }

    private boolean isAuthenticated() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    private void notifySmsSent(String sms) {
        for (Listener listener : getListeners())
            listener.onOtpSent(sms);
    }

    private void showLoading() {
        for (Listener listener : getListeners())
            listener.showLoading();
    }

    private void hideLoading() {
        for (Listener listener : getListeners())
            listener.hideLoading();
    }

    private void requestProfile() {
        for (Listener listener : getListeners())
            listener.requestUserProfileCreation();
    }

    private void notifySuccess() {
        for (Listener listener : getListeners())
            listener.loginSuccess();
    }

    private void notifyFailure(Exception message) {
        hideLoading();
        for (Listener listener : getListeners())
            listener.onLoginFailed(message.getMessage());
    }

}
