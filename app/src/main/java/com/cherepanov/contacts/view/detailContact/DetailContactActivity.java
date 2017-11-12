package com.cherepanov.contacts.view.detailContact;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.cherepanov.contacts.R;
import com.cherepanov.contacts.model.entity.Contact;
import com.cherepanov.contacts.model.repo.db.tables.ContactsDBTable;
import com.cherepanov.contacts.presenter.ContactsPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailContactActivity extends AppCompatActivity {

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

        String id = "";
        if (getIntent() != null) {
            id = getIntent().getStringExtra("ID");
        }
        Contact contact = ContactsDBTable.getContactById(id, ContactsPresenter.mDBHelper);
        mUsername.setText(contact.getUsername());
        mPhone.setText(contact.getPhone());
        mAddress.setText("" + contact.getAddress().getSuite()
                + ", " + contact.getAddress().getStreet()
                + ", " + contact.getAddress().getCity()
                + ", " + contact.getAddress().getZipcode());

        mWebsite.setText(contact.getWebsite());

        mCompany.setText("" + contact.getCompany().getName()
                + "\n" + contact.getCompany().getCatchPhrase()
                + "\n" + contact.getCompany().getBs());

    }
}