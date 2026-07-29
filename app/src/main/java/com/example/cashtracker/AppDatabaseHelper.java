package com.example.cashtracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cashtracker.db";
    private static final int DATABASE_VERSION = 1;

    public AppDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("CREATE TABLE goals (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "target_amount REAL, " +
                "saved_amount REAL)");


        db.execSQL("CREATE TABLE expenses (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "category TEXT, " +
                "amount REAL)");


        db.execSQL("CREATE TABLE reminders (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "date TEXT)");


        db.execSQL("CREATE TABLE subscriptions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "amount REAL, " +
                "frequency TEXT, " +
                "next_billing_date TEXT)");


        db.execSQL("CREATE TABLE spending_tracker (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date TEXT, " +
                "amount REAL)");


        db.execSQL("CREATE TABLE transfers (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "sender TEXT, " +
                "recipient TEXT, " +
                "amount REAL, " +
                "transfer_date TEXT)");

        
        db.execSQL("CREATE TABLE user_profile (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, " +
                "password TEXT, " +
                "email TEXT, " +
                "date_of_birth TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS goals");
        db.execSQL("DROP TABLE IF EXISTS expenses");
        db.execSQL("DROP TABLE IF EXISTS reminders");
        db.execSQL("DROP TABLE IF EXISTS subscriptions");
        db.execSQL("DROP TABLE IF EXISTS spending_tracker");
        db.execSQL("DROP TABLE IF EXISTS transfers");
        db.execSQL("DROP TABLE IF EXISTS user_profile");
        onCreate(db);
    }
}
