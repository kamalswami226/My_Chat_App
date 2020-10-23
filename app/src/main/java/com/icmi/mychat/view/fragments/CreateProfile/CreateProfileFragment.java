package com.icmi.mychat.view.fragments.CreateProfile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.icmi.mychat.common.presentation_root.PresentationRoot;
import com.icmi.mychat.networking.LoginUseCase;
import com.icmi.mychat.schemas.ProfileModel;
import com.icmi.mychat.view.activity.login_activity.LoginActivity;
import com.icmi.mychat.view.common.activity.BaseFragment;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import static android.app.Activity.RESULT_OK;

public class CreateProfileFragment extends BaseFragment implements CreateFragmentView.Listener {

    public static CreateProfileFragment newInstance() {
        return new CreateProfileFragment();
    }


    private CreateFragmentView mCreateFragmentView;
    private LoginUseCase mLoginUseCase;

    private PresentationRoot getCompositionRoot() {
        return ((LoginActivity) requireActivity()).getCompositionRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginUseCase = getCompositionRoot().getLoginUseCase();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCreateFragmentView = getCompositionRoot().getViewFactory().newInstance(CreateFragmentView.class, container);

        mCreateFragmentView.registerListener(this);
        return mCreateFragmentView.getRootView();

    }

    @Override
    public void onUploadImageButtonClick() {
        //noinspection ConstantConditions
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(getContext(), this);
    }

    @Override
    public void onContinueButtonClick(ProfileModel profileModel) {
        mLoginUseCase.addNewUser(profileModel.getName(), profileModel.getProfileUrl());
    }

    @Override
    public void onBackButtonClicked() {
        //noinspection ConstantConditions
        getFragmentManager().beginTransaction().remove(CreateProfileFragment.this).commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
                mCreateFragmentView.onImageSelected(result != null ? result.getUri() : null);
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
                Toast.makeText(getContext(), "UnSupported Image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCreateFragmentView.removeListener(this);
    }
}
