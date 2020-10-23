package com.icmi.mychat.view.fragments.Chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.icmi.mychat.R;
import com.icmi.mychat.common.presentation_root.PresentationRoot;
import com.icmi.mychat.view.activity.main_activity.MainActivity;
import com.icmi.mychat.view.common.activity.BaseFragment;

public class ChatFragment extends BaseFragment {

    private ChatView mChatView;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mChatView = getCompositionRoot().getViewFactory().newInstance(ChatView.class, container);
        return mChatView.getRootView();
    }

    private PresentationRoot getCompositionRoot() {
        return ((MainActivity) requireActivity()).getCompositionRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
