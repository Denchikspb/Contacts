package com.cherepanov.contacts.view.contactList;

import com.cherepanov.contacts.model.entity.Contact;

import java.util.List;

/**
 * Created by Денис on 11.11.2017.
 */

public interface IMainActivityView {

    void showLoading();
    void hideLoading();

    void showError(String error);

    void showData(List<Contact> list);
}
