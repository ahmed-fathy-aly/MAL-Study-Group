package com.enterprises.wayne.yugicards;

import android.provider.BaseColumns;

/**
 * defines table and coloumn names for the Card table
 */
public class CardContract
{
    public static final class CardEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "Card";

        public static final String COLOUMN_DESCRIPTION= "description";
        public static final String COLOUMN_TYPE= "type";
        public static final String COLOUMN_IMAGE_URL= "image_url";
    }
}
