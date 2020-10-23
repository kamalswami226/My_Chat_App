package com.icmi.mychat.view.fragments.AddContact;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.icmi.mychat.R;
import com.icmi.mychat.schemas.ProfileModel;
import com.icmi.mychat.view.common.view.BaseView;
import java.util.ArrayList;



public class SelectContactViewImpl extends BaseView<SelectContactView.Listener> implements SelectContactView {

    private SelectContactAdapter mAdapter;
   // private ImageView mBackButton, mSearchButton;

    public SelectContactViewImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.fragment_select_contact, container, false));
        initViews();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void bindItem(ProfileModel profile) {
        mAdapter.addNewPerson(profile);
    }

    private void initViews() {
        RecyclerView mRecyclerView = findViewById(R.id.selectContactRecyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SelectContactAdapter(this::notifyOnPersonClicked);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void notifyOnPersonClicked(ProfileModel person) {
        for (Listener listener : getListeners())
            listener.onPersonClicked(person);
    }


    public static class SelectContactAdapter extends RecyclerView.Adapter<SelectContactAdapter.ViewHolder> {

        private ArrayList<ProfileModel> mPersonList = new ArrayList<>();

        public SelectContactAdapter(Listener listener) {
            mListener = listener;
        }

        public interface Listener {
            void onItemClicked(ProfileModel person);
        }

        private Listener mListener;

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_select_contact, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.personName.setText(mPersonList.get(position).getName());
            /*if (!TextUtils.isEmpty(mPersonList.get(position).getProfileUrl())) {
                //TODO : LOAD USER IMAGE
            }*/

            holder.itemView.setOnClickListener(v -> mListener.onItemClicked(mPersonList.get(position)));

        }

        @Override
        public int getItemCount() {
            return mPersonList.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        public void addNewPerson(ProfileModel person) {
            mPersonList.add(person);
            notifyDataSetChanged();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            TextView personName;
           // CircleImageView personImage;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                personName = itemView.findViewById(R.id.singlerow_select_contact_name);
            }
        }

    }

}
