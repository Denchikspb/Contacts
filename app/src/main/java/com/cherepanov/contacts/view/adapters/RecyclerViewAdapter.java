package com.cherepanov.contacts.view.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cherepanov.contacts.R;
import com.cherepanov.contacts.model.entity.Contact;
import com.cherepanov.contacts.view.contactList.IMainActivityView;
import com.cherepanov.contacts.view.detailContact.DetailContactActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Contact> mContactList = new ArrayList<>();

    public void setContactList(List<Contact> list) {
        this.mContactList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Contact contact = mContactList.get(position);
        holder.mUsername.setText(contact.getName());
        holder.mEmail.setText(contact.getEmail());
        holder.id = contact.getId();
    }

    @Override
    public int getItemCount() {
        return mContactList == null ? 0 : mContactList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.username_tv)
        TextView mUsername;

        @Bind(R.id.email_tv)
        TextView mEmail;

        private String id;

        ViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), DetailContactActivity.class);
                    intent.putExtra("ID", id);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}