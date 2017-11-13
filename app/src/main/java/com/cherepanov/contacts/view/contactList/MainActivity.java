package com.cherepanov.contacts.view.contactList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cherepanov.contacts.R;
import com.cherepanov.contacts.model.entity.Contact;
import com.cherepanov.contacts.presenter.ContactsPresenter;
import com.cherepanov.contacts.presenter.IContactsPresenter;
import com.cherepanov.contacts.view.adapter.ContactsAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMainActivityView {

    @Bind(R.id.progress_layout)
    LinearLayout mProgressLayout;

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private ContactsAdapter mAdapter;

//    @InjectPresenter
    IContactsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        if (mPresenter == null){
            mPresenter = new ContactsPresenter(this, getApplicationContext());
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new ContactsAdapter();
        mAdapter.setListener(new ContactsAdapter.ContactListAdapterListener() {
            @Override
            public void onContactClick(Contact contactItem) {
                if (mPresenter != null){
                    mPresenter.showDetailContact(contactItem, getApplicationContext());
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.onStartLoad();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mPresenter != null){
            mPresenter.onStop();
        }
    }

    @Override
    public void showLoading() {
        mProgressLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressLayout.setVisibility(View.GONE);
    }

    @Override
    public void showError(int resourceId) {
        Toast.makeText(getApplicationContext(), getApplicationContext().getString(resourceId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showData(List<Contact> list) {
        mAdapter.setContactList(list);
    }
}
