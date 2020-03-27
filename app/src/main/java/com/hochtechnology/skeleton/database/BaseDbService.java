package com.hochtechnology.skeleton.database;

import android.content.Context;


class BaseDbService {
    static DatabaseHelper dbHelper = null;

    BaseDbService(Context context) {
        if (dbHelper == null) {
            dbHelper = new DatabaseHelper(context);
        }
    }
}
