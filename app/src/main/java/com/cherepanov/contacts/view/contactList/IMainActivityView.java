package com.cherepanov.contacts.view.contactList;

import com.cherepanov.contacts.model.entity.Contact;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

public interface IMainActivityView extends MvpView{

    void showLoading();
    void hideLoading();

    void showError(int resourceId);
    void showInfoMessage(String text);

    void showData(List<Contact> list);
}
