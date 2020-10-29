package com.icmi.mychat.view.activity.main_activity;

import com.icmi.mychat.common.BaseObservable;
import com.icmi.mychat.schemas.ChatHistoryModel;
import com.icmi.mychat.view.common.view.ViewObservable;

public interface MainActivityView extends ViewObservable<MainActivityView.Listener> {

    interface Listener {
        void onSelectContactButtonClicked();
        void onLogoutButtonClicked();
        void onPersonClicked(ChatHistoryModel person);
    }

    void bindRecentChat(ChatHistoryModel chat);

}
