package com.cherepanov.contacts.view.detailContact;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.cherepanov.contacts.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailContactActivity extends AppCompatActivity {

    public static final String NAME = "name";
    public static final String USERNAME = "username";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";
    public static final String WEBSITE = "website";
    public static final String COMPANY = "company";

    @Bind(R.id.detail_toolbar)
    Toolbar mToolbar;

    @Bind(R.id.contact_username_tv)
    TextView mUsername;

    @Bind(R.id.contact_phone_tv)
    TextView mPhone;

    @Bind(R.id.contact_address_tv)
    TextView mAddress;

    @Bind(R.id.contact_website_tv)
    TextView mWebsite;

    @Bind(R.id.contact_company_tv)
    TextView mCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String name = "";
        if (getIntent() != null) {
            name = getIntent().getStringExtra(NAME);
        }
        getSupportActionBar().setTitle(name);
        setData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setData() {
        String username = "";
        String phone = "";
        String address = "";
        String website = "";
        String company = "";
        if (getIntent() != null) {
            username = getIntent().getStringExtra(USERNAME);
            phone = getIntent().getStringExtra(PHONE);
            address = getIntent().getStringExtra(ADDRESS);
            website = getIntent().getStringExtra(WEBSITE);
            company = getIntent().getStringExtra(COMPANY);
        }
        mUsername.setText(username);
        mPhone.setText(phone);
        mAddress.setText(address);
        mWebsite.setText(website);
        mCompany.setText(company);
    }

}