package com.icmi.mychat.view.fragments.CreateProfile;


import android.net.Uri;

import com.icmi.mychat.schemas.ProfileModel;
import com.icmi.mychat.view.common.view.ViewObservable;

public interface CreateFragmentView extends ViewObservable<CreateFragmentView.Listener> {

    interface Listener {
        void onUploadImageButtonClick();
        void onContinueButtonClick(ProfileModel profileModel);
        void onBackButtonClicked();
    }

    void onImageSelected(Uri imageUri);

}
