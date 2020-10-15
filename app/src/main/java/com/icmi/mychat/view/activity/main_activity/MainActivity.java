package com.icmi.mychat.view.activity.main_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.icmi.mychat.R;
import com.icmi.mychat.view.activity.login_activity.LoginActivity;
import com.icmi.mychat.view.common.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            LoginActivity.start(this);
            finish();
        }
    }
}