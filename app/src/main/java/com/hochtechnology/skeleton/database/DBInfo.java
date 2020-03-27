package com.hochtechnology.skeleton.database;

import com.hochtechnology.skeleton.utils.AndyConstants;

public class DBInfo {
    public static final String DB_NAME = "photos_all.db";
    public static final int DB_VERSION = 1;

    public static class Table {
        public static final String TB_VIDEO_NAME = "photo_list";

        public static final String TB_VIDEO_CREATE = "CREATE TABLE IF NOT EXISTS " + TB_VIDEO_NAME + " (" +
                AndyConstants._ID + " LONG PRIMARY KEY NOT NULL," +
                AndyConstants._Author + " TEXT NOT NULL," +
                AndyConstants._Height + " TEXT, " +
                AndyConstants._Width + " TEXT, " +
                AndyConstants._Url + " TEXT, " +
                AndyConstants._DownloadUrl + " TEXT " +
                ");";
        static final String GET_DATA = "SELECT * FROM " + TB_VIDEO_NAME + ";";
    }
}
