package com.icmi.mychat.view.activity.main_activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.icmi.mychat.R;
import com.icmi.mychat.schemas.ChatHistoryModel;
import com.icmi.mychat.view.common.view.BaseView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivityViewImpl extends BaseView<MainActivityView.Listener> implements MainActivityView, View.OnClickListener {

    private RecyclerView mRecyclerView;
    private ChatListAdapter mAdapter;
    private DrawerLayout mDrawerLayout;


    public MainActivityViewImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.activity_main, container, false));

        initViews();
        setupClickListeners();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.mainActivityFloatingActionButton) {
            notifyFloatingActionButtonClicked();
        } else if (v.getId() == R.id.mainActivityNavigationButton) {
            openDrawer();
        }
    }

    private void openDrawer() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.openDrawer(GravityCompat.START);
    }

    private void closeDrawer() {
        if (!mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    private void notifyLogoutButtonClicked() {
        for (Listener listener : getListeners())
            listener.onLogoutButtonClicked();
    }

    private void notifyFloatingActionButtonClicked() {
        for (Listener listener : getListeners())
            listener.onSelectContactButtonClicked();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    private void initViews() {
        mRecyclerView = findViewById(R.id.mainActivityRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ChatListAdapter(this::notifyPersonClicked);
        mRecyclerView.setAdapter(mAdapter);
        mDrawerLayout = findViewById(R.id.mainActivityDrawerLayout);
    }

    private void notifyPersonClicked(ChatHistoryModel person) {
        for (Listener listener : getListeners())
            listener.onPersonClicked(person);
    }

    private void setupClickListeners() {
        findViewById(R.id.mainActivityFloatingActionButton).setOnClickListener(this);
        findViewById(R.id.mainActivityNavigationButton).setOnClickListener(this);
        ((NavigationView) findViewById(R.id.navigation_view)).setNavigationItemSelectedListener(navigationItemClickListener);
    }

    NavigationView.OnNavigationItemSelectedListener navigationItemClickListener = item -> {
        if (item.getItemId() == R.id.menu_profile) {

        } else if (item.getItemId() == R.id.menu_calls) {

        } else if (item.getItemId() == R.id.menu_contacts) {

        } else if (item.getItemId() == R.id.menu_group) {

        } else if (item.getItemId() == R.id.menu_logout) {
            notifyLogoutButtonClicked();
        }
        return false;
    };

    @Override
    public void bindRecentChat(ChatHistoryModel chat) {
        mAdapter.addContact(chat);
    }

    public static class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

        private final ArrayList<ChatHistoryModel> mPersonList = new ArrayList<>();

        public ChatListAdapter(Listener mListener) {
            this.mListener = mListener;
        }

        public interface Listener {
            void onPersonClicked(ChatHistoryModel person);
        }

        private final Listener mListener;


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_person, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.personName.setText(mPersonList.get(position).getName());

            holder.itemView.setOnClickListener(v -> mListener.onPersonClicked(mPersonList.get(position)));
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

        public void addContact(ChatHistoryModel person) {
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