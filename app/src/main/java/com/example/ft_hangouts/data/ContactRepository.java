package com.example.ft_hangouts.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ft_hangouts.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactRepository {

    private final DBHelper dbHelper;

    public ContactRepository(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    // Transform a contact (java object) into a SQLite line.
    public long insertContact(Contact contact) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_FIRST_NAME, contact.getFirstName());
        values.put(DBHelper.COLUMN_LAST_NAME, contact.getLastName());
        values.put(DBHelper.COLUMN_PHONE_NUMBER, contact.getPhoneNumber());
        values.put(DBHelper.COLUMN_EMAIL, contact.getEmail());
        values.put(DBHelper.COLUMN_NICKNAME, contact.getNickname());

        long newId = db.insert(DBHelper.TABLE_CONTACTS, null, values);
        db.close();

        return newId;
    }

    // Read the table and build a list of Contact objects.
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DBHelper.TABLE_CONTACTS,
                null,
                null,
                null,
                null,
                null,
                DBHelper.COLUMN_LAST_NAME + " ASC, " + DBHelper.COLUMN_FIRST_NAME + " ASC"
        );

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact(
                        cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_FIRST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_LAST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PHONE_NUMBER)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NICKNAME))
                );

                contacts.add(contact);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return contacts;
    }
}