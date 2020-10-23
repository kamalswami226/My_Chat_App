package com.icmi.mychat.view.fragments.AddContact;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.icmi.mychat.common.permissions.MyPermission;
import com.icmi.mychat.common.permissions.Permissionhelper;
import com.icmi.mychat.common.presentation_root.PresentationRoot;
import com.icmi.mychat.networking.FetchContactsUseCase;
import com.icmi.mychat.schemas.ProfileModel;
import com.icmi.mychat.view.activity.main_activity.MainActivity;
import com.icmi.mychat.view.common.activity.BaseFragment;

import java.util.ArrayList;

public class SelectContactFragment extends BaseFragment implements Permissionhelper.Listener, FetchContactsUseCase.Listener, SelectContactView.Listener {

    Permissionhelper mPermissionHelper;
    SelectContactView mSelectContactView;
    FetchContactsUseCase mFetchContactUsecase;
    private Listener mListener;

    public static final int READ_CONTACT_CODE = 100;

    public SelectContactFragment(Listener listener) {
        mListener = listener;
    }

    public interface Listener {
        void onAddNewContact(ProfileModel person);
    }

    public static SelectContactFragment newInstance(Listener listener) {
        return new SelectContactFragment(listener);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPermissionHelper = getCompositionRoot().getPermissionHelper();
        mFetchContactUsecase = getCompositionRoot().getFetchContactUseCase();
    }

    private PresentationRoot getCompositionRoot() {
        return ((MainActivity) requireActivity()).getCompositionRoot();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSelectContactView = getCompositionRoot().getViewFactory().newInstance(SelectContactView.class, container);
        return mSelectContactView.getRootView();
    }

    private void requestContactPermission() {
        if (!mPermissionHelper.hasPermission(MyPermission.READ_CONTACT)) {
            mPermissionHelper.requestPermission(MyPermission.READ_CONTACT, READ_CONTACT_CODE);
        }
    }

    @Override
    public void onPermissionGranted(String permission, int requestCode) {
        if (requestCode == READ_CONTACT_CODE)
            Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionDeclined(String permission, int requestCode) {
        Toast.makeText(getContext(), "Permission Declined", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionDeclinedDontAskAgain(String permission, int requestCode) {
        Toast.makeText(getContext(), "Permission Declined! Never ask again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onContactListLoaded(ArrayList<ProfileModel> profileList) {
        for (ProfileModel profile : profileList)
            mSelectContactView.bindItem(profile);
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPermissionHelper.registerListener(this);
        mSelectContactView.registerListener(this);
        mFetchContactUsecase.registerListener(this);
        mFetchContactUsecase.getAllContactsFromPhone();
        requestContactPermission();

    }

    @Override
    public void onPause() {
        super.onPause();
        mPermissionHelper.removeListener(this);
        mFetchContactUsecase.removeListener(this);
        mSelectContactView.removeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPermissionHelper.removeListener(this);
        mFetchContactUsecase.removeListener(this);
        mSelectContactView.removeListener(this);
    }

    @Override
    public void onPersonClicked(ProfileModel person) {
        mListener.onAddNewContact(person);
        //noinspection ConstantConditions
        getFragmentManager().beginTransaction().remove(SelectContactFragment.this).commit();
    }
}
