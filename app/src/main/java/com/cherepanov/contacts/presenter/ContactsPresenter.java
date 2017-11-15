package com.cherepanov.contacts.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cherepanov.contacts.R;
import com.cherepanov.contacts.model.entity.Contact;
import com.cherepanov.contacts.model.repo.db.ContactsDBHelper;
import com.cherepanov.contacts.model.repo.db.tables.ContactsDBTable;
import com.cherepanov.contacts.model.repo.service.IModel;
import com.cherepanov.contacts.model.repo.service.ModelImpl;
import com.cherepanov.contacts.view.contactList.IMainActivityView;
import com.cherepanov.contacts.view.detailContact.DetailContactActivity;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class ContactsPresenter extends MvpBasePresenter<IMainActivityView> {

    private static final String LOG_TAG = ContactsPresenter.class.getSimpleName();
    private ContactsDBHelper mDBHelper;

    private IModel mModel = new ModelImpl();
    private Subscription mSubscription = Subscriptions.empty();
    private List<Contact> mCurrentContactList;

    public ContactsPresenter(Context context) {
        mDBHelper = ContactsDBHelper.getInstance(context);
    }

    /**
     * start load list contacts from internet or db
     */
    public void onStartLoad() {
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }

        if (isViewAttached()) {
            getView().showLoading();
            if (mCurrentContactList != null && !mCurrentContactList.isEmpty()){
                getView().hideLoading();
                getView().showData(mCurrentContactList);
            } else {
                mSubscription = mModel.getUserContacts()
                        .subscribe(new Observer<List<Contact>>() {
                            @Override
                            public void onCompleted() {
                                getView().hideLoading();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(LOG_TAG, e.getMessage());
                                getView().showError(R.string.no_internet_connection);
                                getContactsFromCache();
                            }

                            @Override
                            public void onNext(List<Contact> contacts) {
                                ContactsDBTable.clearTable(mDBHelper);
                                for (Contact c : contacts) {
                                    Log.i("TestTAG", c.getName());
                                    ContactsDBTable.addContact(c, mDBHelper);
                                }
                                mCurrentContactList = contacts;
                                if (!contacts.isEmpty()) {
                                    getView().showData(contacts);
                                } else {
                                    getView().showError(R.string.no_data);
                                }
                            }
                        });
            }
        }
    }

    /**
     * stop load and unsubscribe
     */
    public void onStop() {
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    /**
     * show detail contact activity
     *
     * @param contact - contact pojo
     * @param context - context
     */
    public void showDetailContact(Contact contact, Context context) {
        Intent intent = new Intent(context, DetailContactActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(DetailContactActivity.NAME, contact.getName());
        intent.putExtra(DetailContactActivity.USERNAME, contact.getUsername());
        intent.putExtra(DetailContactActivity.PHONE, contact.getPhone());
        intent.putExtra(DetailContactActivity.WEBSITE, contact.getWebsite());
        intent.putExtra(DetailContactActivity.ADDRESS, getAddress(contact));
        intent.putExtra(DetailContactActivity.COMPANY, getCompany(contact));

        context.startActivity(intent);
    }

    /**
     * sort by a-z
     */
    public void sortByAZ() {
        if(isViewAttached()) {
            if (mCurrentContactList != null && !mCurrentContactList.isEmpty()) {
                Collections.sort(mCurrentContactList, new Comparator<Contact>() {
                    @Override
                    public int compare(Contact contact, Contact t1) {
                        return contact.getName().compareTo(t1.getName());
                    }
                });
                getView().showInfoMessage("sort A-Z");
                getView().showData(mCurrentContactList);
            } else {
                getView().showError(R.string.no_data);
            }
        }
    }

    /**
     * sort by z-a
     */
    public void sortByZA() {
        if(isViewAttached()) {
            if (mCurrentContactList != null && !mCurrentContactList.isEmpty()) {
                Collections.sort(mCurrentContactList, new Comparator<Contact>() {
                    @Override
                    public int compare(Contact contact, Contact t1) {
                        return t1.getName().compareTo(contact.getName());
                    }
                });
                getView().showInfoMessage("sort Z-A");
                getView().showData(mCurrentContactList);
            } else {
                getView().showError(R.string.no_data);
            }
        }
    }

    /**
     * get contact list from db
     */
    public void getContactsFromCache() {
        if (isViewAttached()) {
            getView().showLoading();
            mCurrentContactList = ContactsDBTable.getContactList(mDBHelper);
            getView().hideLoading();
            if (mCurrentContactList.isEmpty()) {
                getView().showError(R.string.no_data);
            }
            getView().showData(mCurrentContactList);
        }
    }

    /**
     * make address text
     *
     * @param contact - contact
     * @return - address
     */
    private String getAddress(Contact contact) {
        return contact.getAddress().getSuite()
                + ", " + contact.getAddress().getStreet()
                + ", " + contact.getAddress().getCity()
                + ", " + contact.getAddress().getZipcode();
    }

    /**
     * make company description text
     *
     * @param contact - contact
     * @return - company description
     */
    private String getCompany(Contact contact) {
        return contact.getCompany().getName()
                + "\n" + contact.getCompany().getCatchPhrase()
                + "\n" + contact.getCompany().getBs();
    }
}