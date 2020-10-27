package com.icmi.mychat.view.fragments.AddContact;

import com.icmi.mychat.schemas.ProfileModel;
import com.icmi.mychat.view.common.view.ViewObservable;

public interface SelectContactView extends ViewObservable<SelectContactView.Listener> {

    interface Listener {
        void onBackButtonClicked();
        void onPersonClicked(ProfileModel person);
    }

    void bindItem(ProfileModel profile);

}
