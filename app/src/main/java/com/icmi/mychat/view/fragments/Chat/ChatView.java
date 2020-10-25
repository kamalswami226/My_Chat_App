package com.icmi.mychat.view.fragments.Chat;

import com.icmi.mychat.schemas.MessageModel;
import com.icmi.mychat.view.common.view.ViewObservable;

public interface ChatView extends ViewObservable<ChatView.Listener> {

    interface Listener {
        void onBackButtonClicked();
        void onSendMessageButtonClicked(String message);
    }

    //void attachListenerToMessages(String UNIQUE_CHAT_ID);
    void bindMessage(MessageModel message);

}
