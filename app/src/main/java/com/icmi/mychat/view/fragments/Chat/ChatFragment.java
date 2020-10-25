package com.icmi.mychat.view.fragments.Chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.icmi.mychat.common.presentation_root.PresentationRoot;
import com.icmi.mychat.networking.SendMessageUsecase;
import com.icmi.mychat.schemas.ChatHistoryModel;
import com.icmi.mychat.schemas.MessageModel;
import com.icmi.mychat.view.activity.main_activity.MainActivity;
import com.icmi.mychat.view.common.activity.BaseFragment;

import java.util.ArrayList;

public class ChatFragment extends BaseFragment implements ChatView.Listener, SendMessageUsecase.Listener {

    private ChatView mChatView;
    private SendMessageUsecase mSendMessageUseCase;
    private String LOCAL_UNIQUE_ID = "";
    private String LOCAL_FRIEND_ID = "";
    private boolean isNewChat = false;

    //public static final String ARGS_UNIQUE_ID = "ARGS_UNIQUE_ID";
    public static final String ARGS_FRIEND_ID = "ARGS_FRIEND_ID";

    public static Fragment newInstance(String UNIQUE_FRIEND_ID) {
        Bundle args = new Bundle(1);
        args.putString(ARGS_FRIEND_ID, UNIQUE_FRIEND_ID);
        Fragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSendMessageUseCase = getCompositionRoot().getSendMessageUseCase();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mChatView = getCompositionRoot().getViewFactory().newInstance(ChatView.class, container);
        //noinspection ConstantConditions
        LOCAL_FRIEND_ID = getArguments().getString(ARGS_FRIEND_ID);
        checkIfNewChat();
        return mChatView.getRootView();
    }

    @Override
    public void onBackButtonClicked() {
        //noinspection ConstantConditions
        getFragmentManager().beginTransaction().remove(ChatFragment.this).commit();
    }

    @Override
    public void onSendMessageButtonClicked(String message) {
        if (isNewChat)
            mSendMessageUseCase.sendFirstMessage(message, LOCAL_FRIEND_ID);
        else
            mSendMessageUseCase.sendMessage(message, LOCAL_UNIQUE_ID);
    }

    private PresentationRoot getCompositionRoot() {
        return ((MainActivity) requireActivity()).getCompositionRoot();
    }

    private void checkIfNewChat() {
        isNewChat = true;
        ArrayList<ChatHistoryModel> chatList = getCompositionRoot().getProfile().getChats();
        for (ChatHistoryModel chat : chatList) {
            if (chat.getFriendId().equalsIgnoreCase(LOCAL_FRIEND_ID)) {
                isNewChat = false;
                LOCAL_UNIQUE_ID = chat.getChatReference();
                break;
            }
        }
        if (!isNewChat)
            showAllRecentMessagestoUser();
    }

    private void showAllRecentMessagestoUser() {
        mSendMessageUseCase.loadAllRecentMessages(LOCAL_UNIQUE_ID);
    }

    @Override
    public void onStart() {
        super.onStart();
        mChatView.registerListener(this);
        mSendMessageUseCase.registerListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mChatView.removeListener(this);
        mSendMessageUseCase.removeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mChatView.removeListener(this);
        mSendMessageUseCase.removeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mChatView.removeListener(this);
        mSendMessageUseCase.removeListener(this);
    }

    @Override
    public void onRecentMessagesLoaded(ArrayList<MessageModel> messageList) {
        for (MessageModel message : messageList)
            mChatView.bindMessage(message);
    }

    @Override
    public void onMessageSent() {
        isNewChat = false;
    }

    @Override
    public void onMessageSendingFailed(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
