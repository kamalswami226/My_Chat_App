package com.icmi.mychat.view.activity.main_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.icmi.mychat.R;
import com.icmi.mychat.schemas.ProfileModel;
import com.icmi.mychat.view.activity.login_activity.LoginActivity;
import com.icmi.mychat.view.common.activity.BaseActivity;
import com.icmi.mychat.view.fragments.AddContact.SelectContactFragment;
import com.icmi.mychat.view.fragments.Chat.ChatFragment;

public class MainActivity extends BaseActivity implements MainActivityView.Listener, SelectContactFragment.Listener{

    private MainActivityView mMainView;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainView = getCompositionRoot().getViewFactory().newInstance(MainActivityView.class, null);
        setContentView(mMainView.getRootView());

        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, ChatFragment.newInstance()).commit();

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
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMainView.removeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainView.removeListener(this);
    }

    @Override
    public void onAddNewContact(ProfileModel person) {
        Toast.makeText(this, person.getName(), Toast.LENGTH_SHORT).show();
    }
}