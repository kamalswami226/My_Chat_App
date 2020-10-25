package com.icmi.mychat.view.fragments.CreateProfile;

import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.icmi.mychat.R;
import com.icmi.mychat.schemas.ProfileModel;
import com.icmi.mychat.view.common.view.BaseView;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateFragmentViewImpl extends BaseView<CreateFragmentView.Listener> implements CreateFragmentView, View.OnClickListener {

    private EditText mFirstName, mLastName;
    private Button mContinueButton;
    private ImageView mBackButton;
    private CircleImageView mProfileImage;

    public CreateFragmentViewImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.fragment_create_profile, container, false));
        initViews();
        addClickListeners();
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onImageSelected(Uri imageUri) {
        mProfileImage.setImageURI(imageUri);
    }

    private void initViews() {
        mFirstName = findViewById(R.id.createProfileInputFirstName);
        mLastName = findViewById(R.id.createProfileInputLastName);
        mContinueButton = findViewById(R.id.createProfileContinueButton);
        mProfileImage = findViewById(R.id.createProfileImage);
        mBackButton = findViewById(R.id.createProfileBackButton);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.createProfileImage) {
            notifyProfleButtonClicked();
        }
        else if (v.getId() == R.id.createProfileContinueButton) {
            if (isAllInputsValid()) {
                notifyContinueButtonClicked(generateModelFromInputData());
            }
        }
        else if (v.getId() == R.id.createProfileBackButton) {
            notifyBackButtonClicked();
        }
    }

    private boolean isAllInputsValid() {
        if (TextUtils.isEmpty(mFirstName.getText().toString())) {
            mFirstName.requestFocus();
            mFirstName.setError("Enter Your First Name");
            return false;
        }
        else if (TextUtils.isEmpty(mFirstName.getText().toString())) {
            mLastName.requestFocus();
            mLastName.setError("Enter Your Last Name");
            return false;
        }
        else {
            return true;
        }
    }

    private ProfileModel generateModelFromInputData() {
        return new ProfileModel(
          mFirstName.getText().toString() + mLastName.getText().toString(),
          "",
          "",
                new ArrayList<>()
        );
    }

    private void addClickListeners() {
        mProfileImage.setOnClickListener(this);
        mContinueButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
    }

    private void notifyBackButtonClicked() {
        for (Listener listener : getListeners()) {
            listener.onBackButtonClicked();
        }
    }

    private void notifyContinueButtonClicked(ProfileModel profileModel) {
        for (Listener listener : getListeners())
            listener.onContinueButtonClick(profileModel);
    }

    private void notifyProfleButtonClicked() {
        for (Listener listener : getListeners())
            listener.onUploadImageButtonClick();
    }
}
