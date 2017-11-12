package com.cherepanov.contacts.model.api;

import com.cherepanov.contacts.model.entity.Contact;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface ContactsApiService {

    @GET("users")
    Observable<List<Contact>> getUserContacts();
}
