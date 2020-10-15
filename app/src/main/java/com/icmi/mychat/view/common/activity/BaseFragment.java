package com.icmi.mychat.view.common.activity;

import androidx.fragment.app.Fragment;

import com.icmi.mychat.common.presentation_root.PresentationRoot;
import com.icmi.mychat.view.activity.login_activity.LoginActivity;

public class BaseFragment extends Fragment {

    protected PresentationRoot getCompositionRoot() {
        return ((LoginActivity)requireActivity()).getCompositionRoot();
    }



}
