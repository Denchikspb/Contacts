package com.cherepanov.contacts.presenter;

import android.content.Context;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.cherepanov.contacts.model.entity.Contact;
import com.cherepanov.contacts.model.repo.db.ContactsDBHelper;
import com.cherepanov.contacts.model.repo.db.tables.ContactsDBTable;
import com.cherepanov.contacts.model.repo.service.IModel;
import com.cherepanov.contacts.model.repo.service.ModelImpl;
import com.cherepanov.contacts.view.contactList.IMainActivityView;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

//@InjectViewState
public class ContactsPresenter implements IContactsPresenter {

    private IModel mModel = new ModelImpl();

    private IMainActivityView mMainActivityView;
    private Subscription mSubscription = Subscriptions.empty();
    public static ContactsDBHelper mDBHelper;

    public ContactsPresenter(IMainActivityView mainActivityView, Context context) {
        mMainActivityView = mainActivityView;
        mDBHelper = new ContactsDBHelper(context);
    }

    @Override
    public void onStartLoad() {
        mMainActivityView.showLoading();
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }

        mSubscription = mModel.getUserContacts()
                .subscribe(new Observer<List<Contact>>() {
                    @Override
                    public void onCompleted() {
                        mMainActivityView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMainActivityView.showError(e.getMessage());
                        mMainActivityView.showData(getContactsFromCache());
                    }

                    @Override
                    public void onNext(List<Contact> contacts) {
                        ContactsDBTable.clearTable(mDBHelper);
                        for (Contact c: contacts){
                            Log.i("TestTAG", c.getName());
                            ContactsDBTable.addContact(c, mDBHelper);
                        }
                        if (!contacts.isEmpty()){
                            mMainActivityView.showData(contacts);
                        } else {
                            mMainActivityView.showError("No data");
                        }
                    }
                });
    }

    @Override
    public void onStop() {
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    private List<Contact> getContactsFromCache(){
        return ContactsDBTable.getContactList(mDBHelper);
    }
}
