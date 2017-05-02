package com.effone.hostismeandroid.sync;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by sarith.vasu on 27-04-2017.
 */

public class RestaurantSyncContractor {

    public static final String CONTENT_AUTHORITY = "com.example.android.basicsyncadapter";

    /**
     * Base URI. (content://com.example.android.basicsyncadapter)
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_RESTAURANTS= "restaurants";
    public static class Restaurant implements BaseColumns {
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.hostismeandroid.restaurants";
        /**
         * MIME type for individual entries.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.hostismeandroid.restaurant";

        /**
         * Fully qualified URI for "entry" resources.
         */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RESTAURANTS).build();

        /**
         * Table name where records are stored for "entry" resources.
         */
        public static final String TABLE_NAME = "restaurant";
        /**
         * Atom ID. (Note: Not to be confused with the database primary key, which is _ID.
         */
        public static final String COLUMN_NAME_REST_ID = "rest_id";
        /**
         * Article title
         */
        public static final String COLUMN_NAME_REST_NAME = "rest_name";
        /**
         * Article hyperlink. Corresponds to the rel="alternate" link in the
         * Atom spec.
         */
        public static final String COLUMN_NAME_REST_ADDRESS = "rest_address";
        /**
         * Date article was published.
         */
        public static final String COLUMN_NAME_CITY = "city";

        public static final String COLUMN_NAME_COUNTRY = "country";
    }
}
