package com.icmi.mychat.common.permissions;


import android.Manifest;

public enum  MyPermission {

    READ_CONTACT(Manifest.permission.READ_CONTACTS);

    private final String mCode;

    MyPermission(String code) {
        mCode = code;
    }

    public String getCode() {
        return mCode;
    }
}
