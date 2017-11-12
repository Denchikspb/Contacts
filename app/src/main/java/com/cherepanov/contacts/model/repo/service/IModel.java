package com.cherepanov.contacts.model.repo.service;

import com.cherepanov.contacts.model.entity.Contact;

import java.util.List;

import rx.Observable;

public interface IModel {

    Observable<List<Contact>> getUserContacts();
}
