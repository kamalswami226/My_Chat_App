package com.icmi.mychat.view.common.utils;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class References {

    @SuppressWarnings("ConstantConditions")
    @NonNull
    public static final String USER_ID = FirebaseAuth.getInstance().getUid();

    public static DocumentReference myProfileReference() {
        return FirebaseFirestore.getInstance().collection("USERS")
                .document(USER_ID);
    }

    public static CollectionReference profileNodeReference() {
        return FirebaseFirestore.getInstance().collection("USERS");
    }


}
