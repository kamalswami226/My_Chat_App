package com.icmi.mychat.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.icmi.mychat.view.activity.login_activity.LoginView;
import com.icmi.mychat.view.activity.login_activity.LoginViewImpl;
import com.icmi.mychat.view.activity.main_activity.MainActivityView;
import com.icmi.mychat.view.activity.main_activity.MainActivityViewImpl;
import com.icmi.mychat.view.common.view.ViewMVC;
import com.icmi.mychat.view.fragments.AddContact.SelectContactFragment;
import com.icmi.mychat.view.fragments.AddContact.SelectContactView;
import com.icmi.mychat.view.fragments.AddContact.SelectContactViewImpl;
import com.icmi.mychat.view.fragments.Chat.ChatFragmentImpl;
import com.icmi.mychat.view.fragments.Chat.ChatView;
import com.icmi.mychat.view.fragments.CreateProfile.CreateFragmentView;
import com.icmi.mychat.view.fragments.CreateProfile.CreateFragmentViewImpl;

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
        else if (viewClass == MainActivityView.class) {
            view = new MainActivityViewImpl(mLayoutInflater, container);
        }
        else if (viewClass == CreateFragmentView.class) {
            view = new CreateFragmentViewImpl(mLayoutInflater, container);
        }
        else if (viewClass == SelectContactView.class) {
            view = new SelectContactViewImpl(mLayoutInflater, container);
        }
        else if (viewClass == ChatView.class) {
            view = new ChatFragmentImpl(mLayoutInflater, container);
        }
        else {
            throw new IllegalArgumentException("Unsupported class " + viewClass);
        }
        //noinspection unchecked
        return (T) view;
    }

}
