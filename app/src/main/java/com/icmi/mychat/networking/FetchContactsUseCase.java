package com.icmi.mychat.networking;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import androidx.fragment.app.FragmentActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.icmi.mychat.common.BaseObservable;
import com.icmi.mychat.schemas.ProfileModel;
import com.icmi.mychat.view.common.utils.References;
import java.util.ArrayList;

import javax.annotation.Nullable;

public class FetchContactsUseCase extends BaseObservable<FetchContactsUseCase.Listener> {

    private final FragmentActivity mActivity;

    private ArrayList<ProfileModel> mProfileList;

    public interface Listener {
        void onContactListLoaded(ArrayList<ProfileModel> profileList);

        void onAppActiveUsers(@Nullable ProfileModel profileList);

        void onNoActiveUserFound(String message);

        void onError(String errorMessage);
    }

    public FetchContactsUseCase(FragmentActivity mActivity) {
        this.mActivity = mActivity;
    }

    private static final String[] PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };

    public void getAllContactsFromPhone() {
        mProfileList = new ArrayList<>();
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, null);
        if (cursor != null) {
            try {
                final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                while (cursor.moveToNext()) {
                    mProfileList.add(new ProfileModel(cursor.getString(nameIndex),
                            "",
                            cursor.getString(numberIndex),
                            null));

                }
            } finally {
                cursor.close();
            }
        }
        if (mProfileList.size() > 0) {
            notifyAllContactsLoaded();
            getActiveContactsOnApp();
        } else
            notifyNoContactsFound();

    }

    public void getActiveContactsOnApp() {
        for (ProfileModel profile : mProfileList)
            References.profileReference().whereEqualTo("contact", profile.getContact().trim()).get()
                    .addOnSuccessListener(queryDocumentSnapshots -> notifyListOfActiveUsersFound(getUserProfileFromSnapshot(queryDocumentSnapshots))
                    ).addOnFailureListener(this::notifyNoActiveUserFound);
    }

    private ProfileModel getUserProfileFromSnapshot(QuerySnapshot queryDocumentSnapshots) {
        for (DocumentSnapshot ds : queryDocumentSnapshots) {
            ProfileModel profile = ds.toObject(ProfileModel.class);
            profile.setId(ds.getId());
            return profile;
        }
        return null;
    }


    private void notifyListOfActiveUsersFound(ProfileModel userModelsFromObjectList) {
        for (Listener listener : getListeners())
            listener.onAppActiveUsers(userModelsFromObjectList);
    }

    private void notifyNoActiveUserFound(Exception e) {
        for (Listener listener : getListeners())
            listener.onNoActiveUserFound(e.getMessage());
    }

    private void notifyAllContactsLoaded() {
        for (Listener listener : getListeners())
            listener.onContactListLoaded(mProfileList);
    }

    private void notifyNoContactsFound() {
        for (Listener listener : getListeners())
            listener.onError("No Contacts Found");
    }

    private ContentResolver getContentResolver() {
        return mActivity.getContentResolver();
    }

}
