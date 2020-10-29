package com.icmi.mychat.networking;

import com.icmi.mychat.common.BaseObservable;
import com.icmi.mychat.schemas.ChatHistoryModel;
import com.icmi.mychat.schemas.ProfileModel;
import com.icmi.mychat.view.common.utils.References;

import java.security.cert.Extension;
import java.util.ArrayList;

public class FetchRecentChatsUseCase extends BaseObservable<FetchRecentChatsUseCase.Listener> {

    public interface Listener {
        void onRecentChatsLoaded(ArrayList<ChatHistoryModel> recentChatList);
        void onNoChatFound();
        void onError(String errorMessage);
    }

    public void loadRecentChats() {
        References.myProfileReference().get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                getRecentChatListAndNotifySuccess(documentSnapshot.toObject(ProfileModel.class).getChats());
            }
            else {
                notifyFailure(new Exception("Profile not found"));
            }
        }).addOnFailureListener(this::notifyFailure);
    }

    private void getRecentChatListAndNotifySuccess(ArrayList<ChatHistoryModel> chats) {
        if (chats.size() > 0) {
            notifyRecentChatListLoaded(chats);
        }
        else {
            notifyNoChatFound();
        }
    }

    private void notifyRecentChatListLoaded(ArrayList<ChatHistoryModel> chats) {
        for (Listener listener : getListeners())
            listener.onRecentChatsLoaded(chats);
    }

    private void notifyNoChatFound() {
        for (Listener listener : getListeners())
            listener.onNoChatFound();
    }

    private void notifyFailure(Exception e) {
        for (Listener listener : getListeners())
            listener.onError(e.getMessage());
    }

}
