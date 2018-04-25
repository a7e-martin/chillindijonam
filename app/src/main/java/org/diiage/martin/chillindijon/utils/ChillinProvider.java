package org.diiage.martin.chillindijon.utils;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public class ChillinProvider extends ContentProvider{

    private DatabaseHelper dbHelper;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        /*
         * The calls to addURI() go here, for all of the content URI patterns that the provider
         * should recognize. For this snippet, only the calls for table 3 are shown.
         */

        /*
         * Sets the integer value for multiple rows in table 3 to 1. Notice that no wildcard is used
         * in the path
         */
        sUriMatcher.addURI("org.diiage.martin.chillindijon.provider", "bookmarks", 1);

        /*
         * Sets the code for a single row to 2. In this case, the "#" wildcard is
         * used. "content://com.example.app.provider/table3/3" matches, but
         * "content://com.example.app.provider/table3 doesn't.
         */
        sUriMatcher.addURI("org.diiage.martin.chillindijon.provider", "bookmarks/#", 2);
    }


    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch(sUriMatcher.match(uri)){
            case 1:
                if(TextUtils.isEmpty(sortOrder)){
                    sortOrder = "id ASC";
                }
                break;
            case 2:
                selection = selection + "id" + uri.getLastPathSegment();
                break;
        }

        Cursor q = dbHelper.getWritableDatabase().query("bookmarks", projection, selection, selectionArgs, "", "", sortOrder);
        return q;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Uri.Builder b = new Uri.Builder();
        Uri u = b.scheme("content")
                .authority("org.diiage.martin.chillindijon.provider")
                .appendPath("bookmarks")
                .build();

        switch(sUriMatcher.match(uri)){
            case 1:
                long bookmarkId = dbHelper.getWritableDatabase().insert("bookmarks", null, contentValues);
                ContentUris.withAppendedId(u, bookmarkId);
                return u;
        }


        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch(sUriMatcher.match(uri)){
            case 2:
                int nbRowsAffected = dbHelper.getWritableDatabase().delete("bookmarks", selection, selectionArgs);
                return nbRowsAffected;
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch(sUriMatcher.match(uri)){
            case 1:
                int nbRowsAffected = dbHelper.getWritableDatabase().update("bookmarks", contentValues, selection, selectionArgs);
                return nbRowsAffected;
        }

        return 0;
    }
}
