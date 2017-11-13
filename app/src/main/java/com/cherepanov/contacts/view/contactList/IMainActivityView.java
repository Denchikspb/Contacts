package com.cherepanov.contacts.view.contactList;

import com.cherepanov.contacts.model.entity.Contact;

import java.util.List;

public interface IMainActivityView {

    void showLoading();
    void hideLoading();

    void showError(int resourceId);
    void showInfoMessage(String text);

    void showData(List<Contact> list);
}
