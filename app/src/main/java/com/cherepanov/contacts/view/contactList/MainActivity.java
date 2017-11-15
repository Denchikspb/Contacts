package com.cherepanov.contacts.view.contactList;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cherepanov.contacts.R;
import com.cherepanov.contacts.helper.NetworkUtils;
import com.cherepanov.contacts.model.entity.Contact;
import com.cherepanov.contacts.presenter.ContactsPresenter;
import com.cherepanov.contacts.view.adapter.ContactsAdapter;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends MvpActivity<IMainActivityView, ContactsPresenter> implements IMainActivityView {

    @Bind(R.id.progress_layout)
    LinearLayout mProgressLayout;

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private ContactsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new ContactsAdapter();
        mAdapter.setListener(new ContactsAdapter.ContactListAdapterListener() {
            @Override
            public void onContactClick(Contact contactItem) {
                getPresenter().showDetailContact(contactItem, getApplicationContext());
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        if (NetworkUtils.isNetAvailable(getApplicationContext())) {
            getPresenter().onStartLoad();
        } else {
            getPresenter().getContactsFromCache();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_a_z:
                getPresenter().sortByAZ();
                return true;
            case R.id.action_z_a:
                getPresenter().sortByZA();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        getPresenter().onStop();
    }

    @NonNull
    @Override
    public ContactsPresenter createPresenter() {
        return new ContactsPresenter(getApplicationContext());
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
    public void showInfoMessage(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showData(List<Contact> list) {
        mAdapter.setContactList(list);
    }
}
