package com.cherepanov.contacts.model.repo.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cherepanov.contacts.model.entity.Address;
import com.cherepanov.contacts.model.entity.Company;
import com.cherepanov.contacts.model.entity.Contact;
import com.cherepanov.contacts.model.entity.Geo;
import com.cherepanov.contacts.model.repo.db.ContactsDBHelper;

import java.util.ArrayList;
import java.util.List;

public class ContactsDBTable {

    private static final String LOG_TAG = ContactsDBTable.class.getSimpleName();

    private static final String TABLE_NAME = "contacts";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String STREET = "street";
    private static final String SUITE = "suite";
    private static final String CITY = "city";
    private static final String ZIPCODE = "zipcode";
    private static final String LAT = "lat";
    private static final String LNG = "lng";
    private static final String PHONE = "phone";
    private static final String WEBSITE = "website";
    private static final String COMPANY_NAME = "company_name";
    private static final String CATCH_PHRASE = "catch_phrase";
    private static final String BS = "bs";


    public static final String CREATE_CONTACT_TABLE = "create table " + TABLE_NAME + "(" +
            "_id integer primary key autoincrement, " +
            ID + "," +
            NAME + "," +
            USERNAME + "," +
            EMAIL + "," +
            STREET + "," +
            SUITE + "," +
            CITY + "," +
            ZIPCODE + "," +
            LAT + "," +
            LNG + "," +
            PHONE + "," +
            WEBSITE + "," +
            COMPANY_NAME + "," +
            CATCH_PHRASE + "," +
            BS + ")";

    public static final String DROP_CONTACT_TABLE = "drop table if exists" + TABLE_NAME;

    /**
     *  add new contact to table
     *
     * @param contact - current contact
     * @param helper - db-helper
     */
    public static void addContact(Contact contact, ContactsDBHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID, contact.getId());
        values.put(NAME, contact.getName());
        values.put(USERNAME, contact.getUsername());
        values.put(EMAIL, contact.getEmail());
        values.put(STREET, contact.getAddress().getStreet());
        values.put(SUITE, contact.getAddress().getSuite());
        values.put(CITY, contact.getAddress().getCity());
        values.put(ZIPCODE, contact.getAddress().getZipcode());
        values.put(LAT, contact.getAddress().getGeo().getLat());
        values.put(LNG, contact.getAddress().getGeo().getLng());
        values.put(PHONE, contact.getPhone());
        values.put(WEBSITE, contact.getWebsite());
        values.put(COMPANY_NAME, contact.getCompany().getName());
        values.put(CATCH_PHRASE, contact.getCompany().getCatchPhrase());
        values.put(BS, contact.getCompany().getBs());

        try {
            db.insert(TABLE_NAME, null, values);
        } catch (SQLException e) {
            Log.d(LOG_TAG, e.getMessage());
        }

        db.close();
    }

    /**
     *  get all contact list from db
     *
     * @param helper - db-helper
     * @return contact list
     */
    public static List<Contact> getContactList(ContactsDBHelper helper) {
        List<Contact> contacts = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            contacts.add(getContact(cursor));
                        } while (cursor.moveToNext());
                    }
                }
                cursor.close();
            }
        } catch (SQLException e) {
            Log.d(LOG_TAG, e.getMessage());
        }
        return contacts;
    }

    /**
     *  clear all contacts
     *
     * @param dbHelper - db-helper
     */
    public static void clearTable(ContactsDBHelper dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
    }

    /**
     *  mapper from cursor to contact pojo
     *
     * @param cursor - cursor from db
     * @return - contact pojo
     */
    private static Contact getContact(Cursor cursor) {
        Contact contact = new Contact();
        contact.setId(cursor.getString(cursor.getColumnIndex(ID)));
        contact.setName(cursor.getString(cursor.getColumnIndex(NAME)));
        contact.setUsername(cursor.getString(cursor.getColumnIndex(USERNAME)));
        contact.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
        contact.setPhone(cursor.getString(cursor.getColumnIndex(PHONE)));
        contact.setWebsite(cursor.getString(cursor.getColumnIndex(WEBSITE)));

        Company company = new Company();
        company.setName(cursor.getString(cursor.getColumnIndex(COMPANY_NAME)));
        company.setCatchPhrase(cursor.getString(cursor.getColumnIndex(CATCH_PHRASE)));
        company.setBs(cursor.getString(cursor.getColumnIndex(BS)));

        Address address = new Address();
        address.setStreet(cursor.getString(cursor.getColumnIndex(STREET)));
        address.setCity(cursor.getString(cursor.getColumnIndex(CITY)));
        address.setSuite(cursor.getString(cursor.getColumnIndex(SUITE)));
        address.setZipcode(cursor.getString(cursor.getColumnIndex(ZIPCODE)));

        Geo geo = new Geo();
        geo.setLat(cursor.getString(cursor.getColumnIndex(LAT)));
        geo.setLng(cursor.getString(cursor.getColumnIndex(LNG)));

        address.setGeo(geo);
        contact.setCompany(company);
        contact.setAddress(address);

        return contact;
    }
}