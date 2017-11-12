package com.cherepanov.contacts.model.repo.service;

import com.cherepanov.contacts.model.api.ApiModule;
import com.cherepanov.contacts.model.api.ContactsApiService;
import com.cherepanov.contacts.model.entity.Contact;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ModelImpl implements IModel {

    ContactsApiService mService = ApiModule.getApiService();

    @Override
    public Observable<List<Contact>> getUserContacts() {
        return mService.getUserContacts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
