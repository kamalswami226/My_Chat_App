package com.icmi.mychat.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.icmi.mychat.view.activity.login_activity.LoginView;
import com.icmi.mychat.view.activity.login_activity.LoginViewImpl;
import com.icmi.mychat.view.common.view.ViewMVC;
import com.icmi.mychat.view.fragments.CreateFragmentView;
import com.icmi.mychat.view.fragments.CreateFragmentViewImpl;

public class ViewFactory {

    public final LayoutInflater mLayoutInflater;

    public ViewFactory(LayoutInflater mLayoutInflater) {
        this.mLayoutInflater = mLayoutInflater;
    }

    public <T extends ViewMVC> T newInstance(Class<T> viewClass, @Nullable ViewGroup container) {
        ViewMVC view;

        if (viewClass == LoginView.class) {
            view = new LoginViewImpl(mLayoutInflater, container);
        }
        else if (viewClass == CreateFragmentView.class) {
            view = new CreateFragmentViewImpl(mLayoutInflater, container);
        }
        else {
            throw new IllegalArgumentException("Unsupported class " + viewClass);
        }
        //noinspection unchecked
        return (T) view;
    }

}
