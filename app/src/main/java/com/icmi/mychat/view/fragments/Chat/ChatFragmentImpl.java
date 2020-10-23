package com.icmi.mychat.view.fragments.Chat;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.icmi.mychat.R;
import com.icmi.mychat.schemas.MessageModel;
import com.icmi.mychat.view.common.utils.Constants;
import com.icmi.mychat.view.common.view.BaseView;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class ChatFragmentImpl extends BaseView<ChatView.Listener> implements ChatView, View.OnClickListener {

    View mParentLayout;
    EditText mInputMessage;
    ImageView mSendMessage, mInputMicrophone;
    ChatAdapter mAdapter;
    RecyclerView mRecyclerView;

    public ChatFragmentImpl(LayoutInflater layoutInflater, ViewGroup container) {
        setRootView(layoutInflater.inflate(R.layout.fragment_chat, container, false));

        initViews();
        attachViewListeners();
        setupRecyclerView();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    private void attachViewListeners() {
        mInputMessage.addTextChangedListener(messageTextWatcher);
    }

    TextWatcher messageTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(mInputMessage.getText().toString())) {
                hideSendButtonAndShowMicButton();
            } else {
                hideMicButtonAndShowSendButton();
            }
        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.input_send) {
            if(messageIsNotEmpty())
                notifyMessageSendButtonClicked();
        }
    }

    private void notifyMessageSendButtonClicked() {
        for (Listener listener : getListeners())
            listener.onSendMessageButtonClicked(mInputMessage.getText().toString());
    }

    private boolean messageIsNotEmpty() {
        return !TextUtils.isEmpty(mInputMessage.getText().toString());
    }

    private void hideSendButtonAndShowMicButton() {
        mSendMessage.setVisibility(View.GONE);
        mInputMicrophone.setVisibility(View.VISIBLE);
    }

    private void hideMicButtonAndShowSendButton() {
        mSendMessage.setVisibility(View.VISIBLE);
        mInputMicrophone.setVisibility(View.GONE);
    }

    private void initViews() {
        mParentLayout = findViewById(R.id.chat_fragment_input);
        mInputMessage = mParentLayout.findViewById(R.id.input_text);
        mSendMessage = mParentLayout.findViewById(R.id.input_send);
        mInputMicrophone = mParentLayout.findViewById(R.id.input_audio);
    }

    private void setupRecyclerView() {
        mRecyclerView = findViewById(R.id.chat_fragment_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ChatAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    public static class ChatAdapter extends RecyclerView.Adapter {

        private ArrayList<MessageModel> mMessageList = new ArrayList<>();

        public static class MsgSentViewHolder extends RecyclerView.ViewHolder {

            TextView message;

            public MsgSentViewHolder(@NonNull View itemView) {
                super(itemView);
                message = itemView.findViewById(R.id.sr_msg_sent);
            }
        }

        public static class MsgRecViewHolder extends RecyclerView.ViewHolder {

            TextView message;

            public MsgRecViewHolder(@NonNull View itemView) {
                super(itemView);
                message = itemView.findViewById(R.id.sr_msg_received);
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case Constants.MSG_SENT_TYPE:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sr_msg_sent_type, parent, false);
                    return new MsgSentViewHolder(view);
                case Constants.MSG_REC_TYPE:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sr_msg_rec_type, parent, false);
                    return new MsgRecViewHolder(view);
            }
            return null;
        }

        @Override
        public int getItemViewType(int position) {
            switch (mMessageList.get(position).getType()) {
                case Constants.MSG_SENT_TYPE:
                    return Constants.MSG_SENT_TYPE;
                case Constants.MSG_REC_TYPE:
                    return Constants.MSG_REC_TYPE;
                default:
                    return -1;
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            switch (mMessageList.get(position).getType()) {
                case Constants.MSG_SENT_TYPE:
                    ((MsgSentViewHolder) holder).message.setText(mMessageList.get(position).getMessage());
                case Constants.MSG_REC_TYPE:
                    ((MsgRecViewHolder) holder).message.setText(mMessageList.get(position).getMessage());
            }
        }

        @Override
        public int getItemCount() {
            return mMessageList.size();
        }

    }

}
