package com.cherepanov.contacts.presenter;

import android.content.Context;

import com.cherepanov.contacts.model.entity.Contact;

/**
 * Created by Денис on 11.11.2017.
 */

public interface IContactsPresenter {

    void onStartLoad();
    void onStop();

    void showDetailContact(Contact contact, Context context);
    void getContactsFromCache();

    void sortByAZ();
    void sortByZA();
}
