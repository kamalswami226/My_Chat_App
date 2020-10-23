package com.icmi.mychat.view.fragments.Chat;

import com.icmi.mychat.view.common.view.ViewObservable;

public interface ChatView extends ViewObservable<ChatView.Listener> {

    public interface Listener {
        void onSendMessageButtonClicked(String message);
    }

}
