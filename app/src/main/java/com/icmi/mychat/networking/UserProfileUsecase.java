package com.icmi.mychat.networking;

import com.icmi.mychat.common.BaseObservable;
import com.icmi.mychat.schemas.ProfileModel;
import com.icmi.mychat.view.common.utils.References;

public class UserProfileUsecase extends BaseObservable<UserProfileUsecase.Listener> {

    public interface Listener {
        void onProfileLoaded(ProfileModel profile);

        void onProfileLoadFailed(String message);
    }

    public void fetchUserProfileInfo() {
        References.myProfileReference().get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists())
                notifyProfileLoaded(documentSnapshot.toObject(ProfileModel.class));
            else
                notifyProfileNotFound(new Exception("Profile not found"));
        }).addOnFailureListener(this::notifyProfileNotFound);
    }

    private void notifyProfileLoaded(ProfileModel profile) {
        for (Listener listener : getListeners())
            listener.onProfileLoaded(profile);
    }

    private void notifyProfileNotFound(Exception e) {
        for (Listener listener : getListeners())
            listener.onProfileLoadFailed(e.getMessage());
    }

}
