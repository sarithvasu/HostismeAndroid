package com.effone.hostismeandroid.sync;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.effone.hostismeandroid.database.SelectionBuilder;

/**
 * Created by sarith.vasu on 26-04-2017.
 */

public class SyncProvider extends ContentProvider {

    private static final String AUTHORITY = RestaurantSyncContractor.CONTENT_AUTHORITY;

    public static final int ROUTE_RESTURANTS = 1;

    /**
     * URI ID for route: /entries/{ID}
     */


    /**
     * UriMatcher, used to decode incoming URIs.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(AUTHORITY, "entries", ROUTE_RESTURANTS);

    }

    private HostIsMEDatabase mDatabaseHelper;


    @Override
    public boolean onCreate() {
        mDatabaseHelper = new HostIsMEDatabase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                         String sortOrder) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        SelectionBuilder builder = new SelectionBuilder();
        int uriMatch = sUriMatcher.match(uri);
        switch (uriMatch) {

            case ROUTE_RESTURANTS:
                // Return all known entries.
                builder.table(RestaurantSyncContractor.Restaurant.TABLE_NAME)
                        .where(selection, selectionArgs);
                Cursor c = builder.query(db, projection, sortOrder);
                // Note: Notification URI must be manually set here for loaders to correctly
                // register ContentObservers.
                Context ctx = getContext();
                assert ctx != null;
                c.setNotificationUri(ctx.getContentResolver(), uri);
                return c;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ROUTE_RESTURANTS:
                return RestaurantSyncContractor.Restaurant.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        assert db != null;
        final int match = sUriMatcher.match(uri);
        Uri result;
        switch (match) {
            case ROUTE_RESTURANTS:
                long id = db.insertOrThrow(RestaurantSyncContractor.Restaurant.TABLE_NAME, null, contentValues);
                result = Uri.parse(RestaurantSyncContractor.Restaurant.CONTENT_URI + "/" + id);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Send broadcast to registered ContentObservers, to refresh UI.
        Context ctx = getContext();
        assert ctx != null;
        ctx.getContentResolver().notifyChange(uri, null, false);
        return result;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SelectionBuilder builder = new SelectionBuilder();
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int count;
        switch (match) {
            case ROUTE_RESTURANTS:
                count = builder.table(RestaurantSyncContractor.Restaurant.TABLE_NAME)
                        .where(selection, selectionArgs)
                        .delete(db);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Send broadcast to registered ContentObservers, to refresh UI.
        Context ctx = getContext();
        assert ctx != null;
        ctx.getContentResolver().notifyChange(uri, null, false);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SelectionBuilder builder = new SelectionBuilder();
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int count;
        switch (match) {
            case ROUTE_RESTURANTS:
                count = builder.table(RestaurantSyncContractor.Restaurant.TABLE_NAME)
                        .where(selection, selectionArgs)
                        .update(db, values);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        Context ctx = getContext();
        assert ctx != null;
        ctx.getContentResolver().notifyChange(uri, null, false);
        return count;
    }
    static class HostIsMEDatabase extends SQLiteOpenHelper {
        /** Schema version. */
        public static final int DATABASE_VERSION = 1;
        /** Filename for SQLite file. */
        public static final String DATABASE_NAME = "Hostisme.db";

        private static final String TYPE_TEXT = " TEXT";
        private static final String TYPE_INTEGER = " INTEGER";
        private static final String COMMA_SEP = ",";
        /** SQL statement to create "entry" table. */
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + RestaurantSyncContractor.Restaurant.TABLE_NAME + " (" +
                        RestaurantSyncContractor.Restaurant._ID + " INTEGER PRIMARY KEY," +
                        RestaurantSyncContractor.Restaurant.COLUMN_NAME_REST_ID + TYPE_INTEGER + COMMA_SEP +
                        RestaurantSyncContractor.Restaurant.COLUMN_NAME_REST_NAME    + TYPE_TEXT + COMMA_SEP +
                        RestaurantSyncContractor.Restaurant.COLUMN_NAME_REST_ADDRESS + TYPE_TEXT + COMMA_SEP +
                        RestaurantSyncContractor.Restaurant.COLUMN_NAME_CITY  + TYPE_TEXT + COMMA_SEP +
                        RestaurantSyncContractor.Restaurant.COLUMN_NAME_COUNTRY  + TYPE_TEXT +")";

        /** SQL statement to drop "entry" table. */
        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " +RestaurantSyncContractor.Restaurant.TABLE_NAME;

        public HostIsMEDatabase(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
    }
}
