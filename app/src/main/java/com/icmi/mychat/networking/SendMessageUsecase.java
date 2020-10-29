package com.icmi.mychat.networking;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.icmi.mychat.common.BaseObservable;
import com.icmi.mychat.schemas.ChatHistoryModel;
import com.icmi.mychat.schemas.MessageModel;
import com.icmi.mychat.view.common.utils.Constants;
import com.icmi.mychat.view.common.utils.References;

import java.util.ArrayList;
import java.util.UUID;

public class SendMessageUsecase extends BaseObservable<SendMessageUsecase.Listener> {

    public interface Listener {
        void onRecentMessagesLoaded(ArrayList<MessageModel> messageList);
        void onMessageSent();
        void onMessageSendingFailed(String message);
    }

    public void loadAllRecentMessages(String UNIQUE_CHAT_ID) {
        Query recentMessageLoadingQuery = References.chatReference(UNIQUE_CHAT_ID).orderBy("time", Query.Direction.ASCENDING);
        recentMessageLoadingQuery.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (hasAnyRecentMessages(queryDocumentSnapshots))
                notifyAllRecentMessagesLoaded(fetchMessagesFromSnapshots(queryDocumentSnapshots));
            else
                notifyMessageSendingFailed(new Exception("No recent chat found!"));

        }).addOnFailureListener(this::notifyMessageSendingFailed);
    }

    public void sendFirstMessage(String message, String to, String friendName, String myName) {
        final String UNIQURE_CHAT_ID = UUID.randomUUID().toString().substring(0, 10);
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        batch.update(References.myProfileReference(), "chats", FieldValue.arrayUnion(generateFriendChatReference(to, UNIQURE_CHAT_ID, friendName)));
        batch.update(References.profileNodeReference(to), "chats", FieldValue.arrayUnion(generateMyChatReference(UNIQURE_CHAT_ID, myName)));
        batch.set(References.chatReference(UNIQURE_CHAT_ID).document(), generateMessageModel(message));
        batch.commit().addOnSuccessListener((eVoid) -> notifyMessageSentSuccessfully())
                .addOnFailureListener(this::notifyMessageSendingFailed);
    }

    public void sendMessage(String message, String UNIQUE_CHAT_ID) {
        References.chatReference(UNIQUE_CHAT_ID).add(generateMessageModel(message))
                .addOnFailureListener(this::notifyMessageSendingFailed);
    }

    private void notifyAllRecentMessagesLoaded(ArrayList<MessageModel> messageList) {
        for (Listener listener : getListeners())
            listener.onRecentMessagesLoaded(messageList);
    }

    private void notifyMessageSentSuccessfully() {
        for (Listener listener : getListeners())
            listener.onMessageSent();
    }

    private void notifyMessageSendingFailed(Exception e) {
        for (Listener listener : getListeners())
            listener.onMessageSendingFailed(e.getMessage());
    }

    private MessageModel generateMessageModel(String message) {
        return new MessageModel(
                message,
                System.currentTimeMillis(),
                Constants.MSG_SENT_TYPE,
                false,
                Constants.MY_ID
        );
    }

    private ChatHistoryModel generateMyChatReference(String UNIQUE_CHAT_ID, String myName) {
        return new ChatHistoryModel(
                FirebaseAuth.getInstance().getUid(),
                "",
                UNIQUE_CHAT_ID,
                myName
        );
    }

    private ChatHistoryModel generateFriendChatReference(String to, String UNIQUE_CHAT_ID, String mName) {
        return new ChatHistoryModel(
                to,
                "",
                UNIQUE_CHAT_ID,
                mName
        );
    }

    private ArrayList<MessageModel> fetchMessagesFromSnapshots(QuerySnapshot queryDocumentSnapshots) {
        ArrayList<MessageModel> messageList = new ArrayList<>();
        for (DocumentSnapshot ds : queryDocumentSnapshots)
            messageList.add(ds.toObject(MessageModel.class));

        return messageList;
    }

    private boolean hasAnyRecentMessages(QuerySnapshot queryDocumentSnapshots) {
        return queryDocumentSnapshots.size() > 0;
    }

}
