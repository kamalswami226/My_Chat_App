package com.icmi.mychat.view.activity.main_activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.icmi.mychat.R;
import com.icmi.mychat.schemas.PersonModel;
import com.icmi.mychat.view.common.view.BaseView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivityViewImpl extends BaseView<MainActivityView.Listener> implements MainActivityView, View.OnClickListener {

    private RecyclerView mRecyclerView;
    private ChatListAdapter mAdapter;

    public MainActivityViewImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.activity_main, container, false));

        initViews();

    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    private void initViews() {
        mRecyclerView = findViewById(R.id.mainActivityRecyclerView);
    }


    public static class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

        ArrayList<PersonModel> mPersonList = new ArrayList<>();

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_person, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.personName.setText(mPersonList.get(position).getName());
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return mPersonList.size();
        }

        public void addContact(PersonModel person) {
            mPersonList.add(person);
            notifyDataSetChanged();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            TextView personName, lastOnline;
            CircleImageView personProfilePic;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                personName = itemView.findViewById(R.id.singlerow_person_name);

            }
        }

    }

}