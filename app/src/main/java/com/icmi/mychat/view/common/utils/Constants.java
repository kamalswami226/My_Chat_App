package com.icmi.mychat.view.common.utils;

import com.google.firebase.auth.FirebaseAuth;

public class Constants {

    public static final int MSG_SENT_TYPE = 0;
    public static final int MSG_REC_TYPE = 1;
    public static final int MSG_IMG_TYPE = 2;
    public static final int MSG_AUD_TYPE = 3;

    public static final String MY_ID = FirebaseAuth.getInstance().getUid();

}
