package com.icmi.mychat.networking;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import androidx.fragment.app.FragmentActivity;

import com.icmi.mychat.common.BaseObservable;
import com.icmi.mychat.schemas.ProfileModel;

import java.util.ArrayList;

public class FetchContactsUseCase extends BaseObservable<FetchContactsUseCase.Listener> {

    private FragmentActivity mActivity;

    private ArrayList<ProfileModel> mProfileList;

    public interface Listener {
        void onContactListLoaded(ArrayList<ProfileModel> profileList);
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
                            cursor.getString(numberIndex)));

                }
            } finally {
                cursor.close();
            }
        }
        if (mProfileList.size() > 0)
            notifyAllContactsLoaded();
        else
            notifyNoContactsFound();

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
