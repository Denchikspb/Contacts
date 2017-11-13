package com.cherepanov.contacts.model.repo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cherepanov.contacts.model.repo.db.tables.ContactsDBTable;

public class ContactsDBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "contacts.db";

    private static ContactsDBHelper sDBHelper;

    public static ContactsDBHelper getInstance(Context context) {
        if (sDBHelper == null) {
            sDBHelper = new ContactsDBHelper(context);
        }
        return sDBHelper;
    }

    private ContactsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ContactsDBTable.CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(ContactsDBTable.DROP_CONTACT_TABLE);
        onCreate(sqLiteDatabase);
    }
}
