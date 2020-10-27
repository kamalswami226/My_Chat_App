package com.icmi.mychat.view.activity.main_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.icmi.mychat.R;
import com.icmi.mychat.networking.UserProfileUsecase;
import com.icmi.mychat.schemas.ProfileModel;
import com.icmi.mychat.view.activity.login_activity.LoginActivity;
import com.icmi.mychat.view.common.activity.BaseActivity;
import com.icmi.mychat.view.fragments.AddContact.SelectContactFragment;

public class MainActivity extends BaseActivity implements MainActivityView.Listener, SelectContactFragment.Listener, UserProfileUsecase.Listener {

    private MainActivityView mMainView;
    private UserProfileUsecase mProfileUsecase;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainView = getCompositionRoot().getViewFactory().newInstance(MainActivityView.class, null);
        setContentView(mMainView.getRootView());

        mProfileUsecase = getCompositionRoot().getUserProfileUsecase();
        mProfileUsecase.fetchUserProfileInfo();
    }

    @Override
    public void onSelectContactButtonClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_container, SelectContactFragment.newInstance(this))
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            LoginActivity.start(this);
            finish();
        }
        mMainView.registerListener(this);
        mProfileUsecase.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMainView.removeListener(this);
        mProfileUsecase.removeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainView.removeListener(this);
        mProfileUsecase.removeListener(this);
    }

    @Override
    public void onAddNewContact(ProfileModel person) {

    }

    @Override
    public void onProfileLoaded(ProfileModel profile) {
        getCompositionRoot().getProfile().setName(profile.getName());
        getCompositionRoot().getProfile().setContact(profile.getContact());
        getCompositionRoot().getProfile().setProfileUrl(profile.getProfileUrl());
        getCompositionRoot().getProfile().setChats(profile.getChats());
    }

    @Override
    public void onProfileLoadFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}