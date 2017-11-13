package com.cherepanov.contacts.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cherepanov.contacts.R;
import com.cherepanov.contacts.model.entity.Contact;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    public interface ContactListAdapterListener {
        void onContactClick(Contact contactItem);
    }

    private ContactListAdapterListener mListener;
    private List<Contact> mContactList = new ArrayList<>();

    public void setListener(ContactListAdapterListener listener) {
        mListener = listener;
    }

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
    }

    @Override
    public int getItemCount() {
        return mContactList == null ? 0 : mContactList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.username_tv)
        TextView mUsername;

        @Bind(R.id.email_tv)
        TextView mEmail;

        ViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        mListener.onContactClick(mContactList.get(position));
                    }
                }
            });
        }
    }
}