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

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressWarnings("ALL")
public class ChatFragmentImpl extends BaseView<ChatView.Listener> implements ChatView, View.OnClickListener {

    View mParentLayout;
    EditText mInputMessage;
    ImageView mSendMessage, mInputMicrophone;
    MessagesAdapter mAdapter;
    RecyclerView mRecyclerView;
    TextView mName;
    CircleImageView mProfileImage;

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
        mSendMessage.setOnClickListener(this);
        mInputMicrophone.setOnClickListener(this);
        findViewById(R.id.chat_fragment_back_button).setOnClickListener(this);
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
            if (messageIsNotEmpty())
                notifyMessageSendButtonClicked();
            showThisMessageToScreen();
        }
        else if (v.getId() == R.id.chat_fragment_back_button)
            notifyBackButtonClicked();
    }

    private void showThisMessageToScreen() {
        MessageModel localMessage = new MessageModel();
        localMessage.setMessage(mInputMessage.getText().toString());
        localMessage.setSenderId(Constants.MY_ID);
        mAdapter.addNewMessage(localMessage);
    }

    private void notifyBackButtonClicked() {
        for (Listener listener : getListeners())
            listener.onBackButtonClicked();
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
        mName = findViewById(R.id.chat_fragment_name);
        mProfileImage = findViewById(R.id.chat_fragment_profile_image);
    }

    private void setupRecyclerView() {
        mRecyclerView = findViewById(R.id.chat_fragment_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MessagesAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void bindMessage(MessageModel message) {
        mAdapter.addNewMessage(message);
    }

    @Override
    public void showUser(String name, String image) {
        mName.setText(name);
    }


    public static class MessagesAdapter extends RecyclerView.Adapter {

        private ArrayList<MessageModel> mMessageList;

        public MessagesAdapter(ArrayList<MessageModel> messagesList) {
            this.mMessageList = messagesList;
        }

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
            if (viewType == Constants.MSG_SENT_TYPE)
                return new MsgSentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sr_msg_sent_type, parent, false));
            else
                return new MsgRecViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sr_msg_rec_type, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (mMessageList.get(position).getSenderId().equalsIgnoreCase(Constants.MY_ID))
                ((MsgSentViewHolder) holder).message.setText(mMessageList.get(position).getMessage());
            else
                ((MsgRecViewHolder) holder).message.setText(mMessageList.get(position).getMessage());
        }

        @Override
        public int getItemCount() {
            return mMessageList.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (mMessageList.get(position).getSenderId().equalsIgnoreCase(Constants.MY_ID))
                return Constants.MSG_SENT_TYPE;
            else
                return Constants.MSG_REC_TYPE;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void addNewMessage(MessageModel message) {
            mMessageList.add(message);
            notifyDataSetChanged();
        }

    }


}
